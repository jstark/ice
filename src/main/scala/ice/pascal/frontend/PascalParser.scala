package ice.pascal.frontend

import ice.frontend.{Token, Parser, EofToken}
import ice.message.{Message, MessageType}

class PascalParser(scanner: PascalScanner) extends Parser(scanner) {
  def parse() = {
    val start = System.currentTimeMillis
    
    var token: Token = null
    do {
      token = nextToken()
    } while (!token.isInstanceOf[EofToken])
    
    val elapsedTime = (System.currentTimeMillis - start)/1000.0f
    val m = new Message(MessageType.PARSER_SUMMARY, (token.lineNumber, errorCount, elapsedTime))
    sendMessage(m)
    
    (null, null)
  }
  
  def errorCount = 0
}