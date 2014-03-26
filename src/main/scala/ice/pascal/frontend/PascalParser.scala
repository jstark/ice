package ice.pascal.frontend

import ice.frontend.{Token, Parser, EofToken}
import ice.message.{Message, MessageType}
import ice.intermediate.{ICodeNode, ICode, SymTab}

class PascalParser(scanner: PascalScanner) extends Parser(scanner) {

  tokens.init()

  def errorCount = PascalErrorHandler.errorCount
  
  def parse() = {
    val symtabstack = SymTab.createStack()
    val icode = ICode.createICode()
    val start = System.currentTimeMillis
    try
    {
      var token = nextToken()
      var root: ICodeNode = null;
      // look for the BEGIN token to parse a compound statement
      if (token.ttype == tokens.BEGIN) {
        val stmnt_parser = StatementParser(scanner, symtabstack, listeners)
        root = stmnt_parser.parse(token)
      } else {
        PascalErrorHandler.flag(token, error.UNEXPECTED_TOKEN, this)
      }

      // look for the final period.
      if (token.ttype != tokens.DOT) {
        PascalErrorHandler.flag(token, error.MISSING_PERIOD, this)
      }

      token = scanner.currentToken
      if (root != null)
      {
        icode.setRoot(root)
      }

      val elapsedTime = (System.currentTimeMillis - start)/1000.0f
      val m = new Message(MessageType.PARSER_SUMMARY, (token.lineNumber, errorCount, elapsedTime))
      sendMessage(m)

    } catch {
      case _: Throwable => PascalErrorHandler.abortTranslation(error.IO_ERROR, this)
    }
    (icode, symTabStack)
  }
}
