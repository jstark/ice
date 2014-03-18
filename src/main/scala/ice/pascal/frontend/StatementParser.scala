package ice.pascal.frontend

import ice.message.{MessageListener, MessageProducer}
import ice.intermediate._
import ice.frontend.{EofToken, TokenType, Token}

/**
 * Created by john on 3/18/14.
 */
class StatementParser private(scanner: PascalScanner, symtabstack: SymTabStack) extends MessageProducer {
  def parse(token: Token): ICodeNode = {
    val node = token.ttype match {
      case tokens.BEGIN =>
        val compound_parser = new CompoundStatementParser
        compound_parser.parse(token)
      case tokens.IDENTIFIER =>
        val assignment_parser = new AssignmentParser
        assignment_parser.parse(token)
      case _ => ICode.createICodeNode(ICodeNodeType.NO_OP)
    }

    // set the current line number as an attribute
    setLineNumber(node, token.lineNumber)
    node
  }

  def parseList(token: Token, parent: ICodeNode, terminator: TokenType, err: PascalErrorCode) {
    var tok = token
    while (!tok.isInstanceOf[EofToken] && tok.ttype != terminator) {
      val stmnt_node = parse(token)
      parent.addChild(stmnt_node)
      tok = scanner.currentToken

      // look for the semicolon between the statements
      if (tok.ttype == tokens.SEMICOLON) {
        tok = scanner.nextToken()
      } else if (tok.ttype == tokens.IDENTIFIER) {
        PascalErrorHandler.flag(tok, error.MISSING_SEMICOLON, this)
        tok = scanner.nextToken()
      }
    }

    // look for the terminator token
    if (tok.ttype == terminator) {
      tok = scanner.nextToken()
    } else {
      PascalErrorHandler.flag(tok, err, this)
    }
  }

  private def setLineNumber(node: ICodeNode, line: Int) {
    node.setAttribute(ICodeKey.LINE, new Integer(line))
  }
}

object StatementParser {
  def apply(scanner: PascalScanner, stack: SymTabStack, listeners: Seq[MessageListener]) = {
    val sp = new StatementParser(scanner, stack)
    listeners foreach (sp addMessageListener _)
    sp
  }
}
