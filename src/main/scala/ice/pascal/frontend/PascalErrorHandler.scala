package ice.pascal.frontend

import ice.frontend.{Token, Parser}
import ice.message._

object PascalErrorHandler {
  private val MAX_ERRORS = 25
  private var errorCount_ = 0 // syntax errors
  
  def flag(token: Token, errorCode: PascalErrorCode, parser: Parser) {
    val m = new Message(MessageType.SYNTAX_ERROR, (token.lineNumber, token.position, token.lexeme, errorCode.toString))
    parser.sendMessage(m)
    
    // abort if too many errors
    errorCount_ += 1
    if (errorCount_ > MAX_ERRORS) {
      import error.TOO_MANY_ERRORS
      abortTranslation(TOO_MANY_ERRORS, parser)
    }
  }
  
  def abortTranslation(errorCode: PascalErrorCode, parser: Parser) {
    val fatalText = "FATAL ERROR: " + errorCode.toString()
    parser.sendMessage(new Message(MessageType.SYNTAX_ERROR, (0, 0, "", fatalText)))
    System.exit(errorCode.status)
  }
}