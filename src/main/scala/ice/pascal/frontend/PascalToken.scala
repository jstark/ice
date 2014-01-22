package ice.pascal.frontend

import ice.frontend.{Source, Token}
import scala.collection.mutable.StringBuilder

class PascalStringToken(source: Source) extends Token(source) {
  protected override def extract() = {
    require(source.currentChar() == '\'')
    val textBuffer = new StringBuilder
    val valueBuffer= new StringBuilder

    var current = source.nextChar() // consume quote
    textBuffer += '\''

    val space = ' '
    // get string characters
    do {
      if (current.isWhitespace) {
        current = space;
      }

      if (current != '\'' && current != Source.EndOfFile) { 
        textBuffer += current
        valueBuffer+= current
        current = source.nextChar()
      }

      // quote? each pair of adjacent quotes represents a single-quote
      if (current == '\'') {
        while (current == '\'' && source.peekChar() == '\'') {
          textBuffer.append("''")
          valueBuffer += current // append single-quote
          // consume pair of quotes
          source.nextChar()
          source.nextChar()
        }
      }
    } while (current != '\'' && current != Source.EndOfFile)

    val (tt, tv) = if (current == '\'') {
      source.nextChar() // consume final quote
      textBuffer += '\''
      (STRING, valueBuffer.toString)
    } else (ERROR, error.UNEXPECTED_EOF)
    (textBuffer.toString, tt, tv)
  }
}
