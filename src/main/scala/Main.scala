import ice.message._
import ice.frontend._
import ice.intermediate._
import ice.backend._
import scopt.OptionParser
import ice.pascal.frontend._

/** scopt config */
case class Config(action: String = "compile", xref: Boolean = true, file: String = "")

/**
 * Created by john on 1/18/14.
 */
object Main extends App {
  val cmdOptions = new OptionParser[Config]("ice") {
    head("ice", "0.1")
    opt[String]('a', "action") action { (x, c) => c.copy(action=x) }
    opt[Unit]('x', "xref") action { (_, c) => c.copy(xref=true)}
    opt[String]('f', "file"  ) action { (x, c) => c.copy(file=x)   } required()
    help("help") text "prints this usage text"
  }
  
  // parser returns Option[C]
  val opts = cmdOptions.parse(args, Config())
  opts map { config => runParser(config) }
  
  def runParser(config: Config) {
    val source = new Source(scala.io.Source.fromFile(config.file))
    source.addMessageListener(SourceMessageListener)
    val scanner = new PascalScanner(source)
    val parser = new PascalParser(scanner)
    parser.addMessageListener(ParserMessageListener)
    val backend = if (config.action == "compile") new CodeGenerator else new Executor
    backend.addMessageListener(BackendMessageListener)
    
    val (icode, symtabstack) = parser.parse()

    if (config.xref) {
      CrossReferencer.print(symtabstack)
    }
    backend.process(icode, symtabstack)
  }
}

object SourceMessageListener extends MessageListener {
  def acceptMessage(message: Message) {
    val mtype = message.mtype
    if (mtype == MessageType.SOURCE_LINE) {
      val (lineNumber, lineText) = message.args.asInstanceOf[(Int, String)]
      println(f"$lineNumber%03d $lineText%s")
    }
  }
}

object ParserMessageListener extends MessageListener {
  def acceptMessage(message: Message) {
    val mtype = message.mtype
    if (mtype == MessageType.PARSER_SUMMARY) {
      val (stmtCount, errors, time) = message.args.asInstanceOf[(Int, Int, Float)]
      println(f"\n$stmtCount%,20d source lines.")
      println(f"$errors%,20d syntax errors.")
      println(f"$time%,20.2f seconds total parsing time.\n")
    } else if (mtype == MessageType.TOKEN) {
      val (ln, pos, tp, txt, vl) = message.args.asInstanceOf[(Int, Int, TokenType, String, AnyRef)]
      println(f">>> $tp%-15s line=$ln%03d, pos=$pos%2d, text=\042$txt%s\042")
      if (vl != null) {
        var tval = vl
        if (tp == tokens.STRING) {
          tval = "\"" + vl + "\""
        }
        println(f">>>                       value=$tval%s")
      }
    } else if (mtype == MessageType.SYNTAX_ERROR) {
      val PREFIX_WIDTH = 5
      val (ln, pos, txt, msg) = message.args.asInstanceOf[(Int, Int, String, String)]
      val spaceCount = PREFIX_WIDTH + pos
      val flagBuffer = new StringBuilder()
      
      // spaces up to the error position
      for (i <- 1 to spaceCount-1) {
        flagBuffer += ' '
      }
      
      // a pointer to the error followed by  the error message
      flagBuffer.append("^\n*** ").append(msg)
      
      // text, if any of the bad token
      if (txt != null) {
        flagBuffer.append(" [at \"").append(txt).append("\"]")
      }
      println(flagBuffer.toString())
    }
  }
}

object BackendMessageListener extends MessageListener {
  def acceptMessage(message: Message) {
    val mtype = message.mtype
    if (mtype == MessageType.INTERPRETER_SUMMARY) {
      val (count, errors, time) = message.args.asInstanceOf[(Int, Int, Float)]
      println(f"\n$count%,20d statements executed.")
      println(f"$errors%,20d runtime errors.")
      println(f"$time%20.2f seconds total execution time.\n")
    } else if (mtype == MessageType.COMPILER_SUMMARY) {
      val (count, time) = message.args.asInstanceOf[(Int, Float)]
      println(f"$count%,20d instructions generated.")
      println(f"$time%,20.2f seconds total code generation time.\n")
    }
  }
}
