package ice.frontend

trait TokenType

class Token(source: Source) {
	val lineNumber = source.currentLine
	val position = source.currentPosition
	
	val (lexeme, mtype, value) = extract() /* implement in subclasses */
	
	protected def extract(): (String, TokenType, AnyRef) = {
	  val text = source.currentChar().toString
	  source.nextChar()
	  (text, null, null)
	}
}

class EofToken(source: Source) extends Token(source) {
  protected override def extract() = ("eof", null, null)
}
