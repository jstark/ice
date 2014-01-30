package ice.pascal.frontend

import ice.frontend.{Source, Scanner, Token, EofToken}

class PascalScanner(source: Source) extends Scanner(source) {

  def extractToken() = {
    skipWhitespace()
    val current = source.currentChar()
    
    // construct the next token. The current character determines the 
    // token type.
    var token: Token = null
    if (current == Source.EndOfFile) {
      token = new EofToken(source)
    } else if (current.isLetter) {
      token = new PascalWordToken(source)
    } else if (current.isDigit) {
      token = new PascalNumberToken(source)
    } else if (current == '\'') {
      token = new PascalStringToken(source)
    } else if (SpecialToken.contains(current.toString())) {
      token = new PascalSpecialSymbolToken(source)
    } else {
      token = new PascalErrorToken(source, error.INVALID_CHARACTER, current.toString())
      source.nextChar()
    }
    token
  }
  
  private def skipWhitespace() {
    var current = source.currentChar()
    while (current.isWhitespace || current == '{') {
      // start of a comment?
      if (current == '{') {
        do {
          current = source.nextChar() // consume comment
        } while (current != '}' && current != Source.EndOfFile)
          
        // found closing '}' ?
        if (current == '}') {
          current = source.nextChar()
        }
      } else {
        current = source.nextChar()
      }
    }
  }
}