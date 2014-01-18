package ice.frontend

abstract class Scanner(source: Source) {
  private var currentToken_ : Token = _
  
  def currentToken = currentToken_
  
  def nextToken() = {
    currentToken_ = extractToken()
    currentToken
  }
  
  def currentChar() = source.currentChar()
  def nextChar() = source.nextChar()
  
  protected def extractToken(): Token
}