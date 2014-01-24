package ice.pascal.frontend

import ice.frontend.{Source, Token}
import scala.collection.mutable.StringBuilder
import scala.collection.mutable

class PascalToken(source: Source) extends Token(source)

class PascalWordToken(source: Source) extends Token(source) {
  /**
   * extract a Pascal word token from the source.
   */
  protected override def extract() = {
    // the current character is already a letter
    val text = source.readWhile(_.isLetterOrDigit).mkString
    val ttype= wordTokenType(text)
    (text, ttype, null)
  }

  private def wordTokenType(text: String) = {
    if (ReservedToken.contains(text.toLowerCase())) {
      ReservedToken.valueOf(text.toUpperCase())
    } else IDENTIFIER
  }
}

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
