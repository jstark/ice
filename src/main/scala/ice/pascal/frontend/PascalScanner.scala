package ice.pascal.frontend

import ice.frontend.{Source, Scanner, Token, EofToken}

class PascalScanner(source: Source) extends Scanner(source) {
  def extractToken() = {
    val current = source.currentChar()
    if (current == Source.EndOfFile) new EofToken(source) else new Token(source)
  }
}