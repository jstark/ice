package ice.pascal.frontend

import ice.frontend.{Token, Parser, EofToken}
import ice.message.{Message, MessageType}

class PascalParser(scanner: PascalScanner) extends Parser(scanner) {
  
  def parse() = {
    try
    {
    val start = System.currentTimeMillis 
    var token: Token = null
    do {
      token = nextToken()
      
      if (token.ttype != ERROR) {
        val args = (token.lineNumber, token.position, token.ttype, token.lexeme, token.tvalue)
        val m = new Message(MessageType.TOKEN, args)
        sendMessage(m)
      } else {
        PascalErrorHandler.flag(token, token.tvalue.asInstanceOf[PascalErrorCode], this)
      }
    } while (!token.isInstanceOf[EofToken])
    
    val elapsedTime = (System.currentTimeMillis - start)/1000.0f
    val m = new Message(MessageType.PARSER_SUMMARY, (token.lineNumber, errorCount, elapsedTime))
    sendMessage(m)
    } catch {
      case _: Throwable => PascalErrorHandler.abortTranslation(error.IO_ERROR, this)
    }
    (null, null)
  }
  
  def errorCount = 0
}