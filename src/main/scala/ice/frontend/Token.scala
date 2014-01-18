package ice.frontend

trait TokenType

class Token(source: Source) {
	val lineNumber = source.currentLine
	val position = source.currentPosition
	
	val (lexeme, value) = extract() /* implement in subclasses */
	
	protected def extract() = {
	  val text = source.currentChar().toString
	  source.nextChar()
	  (text, null)
	}
}

class EofToken(source: Source) extends Token(source) {
  protected override def extract() = ("eof", null)
}