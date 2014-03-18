package ice.pascal.frontend

import ice.frontend.{Token, Parser}
import ice.message._

object PascalErrorHandler {
  private val MAX_ERRORS = 25
  private var errorCount_ = 0 // syntax errors

  def errorCount = errorCount_
  
  def flag(token: Token, errorCode: PascalErrorCode, mp: MessageProducer) {
    val m = new Message(MessageType.SYNTAX_ERROR, (token.lineNumber, token.position, token.lexeme, errorCode.toString))
    mp.sendMessage(m)
    
    // abort if too many errors
    errorCount_ += 1
    if (errorCount_ > MAX_ERRORS) {
      import error.TOO_MANY_ERRORS
      abortTranslation(TOO_MANY_ERRORS, mp)
    }
  }
  
  def abortTranslation(errorCode: PascalErrorCode, mp: MessageProducer) {
    val fatalText = "FATAL ERROR: " + errorCode.toString()
    mp.sendMessage(new Message(MessageType.SYNTAX_ERROR, (0, 0, "", fatalText)))
    System.exit(errorCode.status)
  }
}
