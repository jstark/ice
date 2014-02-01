package ice.pascal.frontend

import ice.frontend.{Token, Parser, EofToken}
import ice.message.{Message, MessageType}

class PascalParser(scanner: PascalScanner) extends Parser(scanner) {

  tokens.init()
  
  def parse() = {
    try
    {
      val start = System.currentTimeMillis
      var token = nextToken()
      while (!token.isInstanceOf[EofToken]) {
        if (token.ttype == tokens.IDENTIFIER) {
          val name = token.lexeme.toLowerCase()

          // if it's not already in the symbol table,
          // create and enter a new entry for the identifier.
          var entry = symTabStack.lookup(name)
          if (entry == null) {
            entry = symTabStack.enterLocal(name)
          }

          // append the current line number to the entry
          entry.appendLineNumber(token.lineNumber)
        } else if (token.ttype == tokens.ERROR) {
          PascalErrorHandler.flag(token, token.tvalue.asInstanceOf[PascalErrorCode], this)
        }
        token = nextToken()
      }

      val elapsedTime = (System.currentTimeMillis - start)/1000.0f
      val m = new Message(MessageType.PARSER_SUMMARY, (token.lineNumber, errorCount, elapsedTime))
      sendMessage(m)

    } catch {
      case _: Throwable => PascalErrorHandler.abortTranslation(error.IO_ERROR, this)
    }
    (null, symTabStack)
  }
  
  def errorCount = 0
}