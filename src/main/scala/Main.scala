import ice.message._
import ice.frontend._
import ice.intermediate._
import ice.backend._
import scopt.OptionParser
import ice.pascal.frontend.{PascalParser, PascalScanner}

/** scopt config */
case class Config(action: String = "compile", file: String = "")

/**
 * Created by john on 1/18/14.
 */
object Main extends App {
  val cmdOptions = new OptionParser[Config]("ice") {
    head("ice", "0.1")
    opt[String]('a', "action") action { (x, c) => c.copy(action=x) }
    opt[String]('f', "file"  ) action { (x, c) => c.copy(file=x)   } required()
    help("help") text("prints this usage text")
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
    
    val (icode, symtab) = parser.parse()
    
    backend.process(icode, symtab)
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
