package ice.frontend

trait TokenType

class Token(source: Source) {
	val lineNumber = source.currentLine
	val position = source.currentPosition
	
	var lexeme_ : String    = _
	var ttype_  : TokenType = _
	var tvalue_ : AnyRef    = _
	
	def lexeme = lexeme_
	def ttype = ttype_
	def tvalue = tvalue_
	
	extract() /* implement in subclasses */
	
	protected def extract() {
	  lexeme_ = source.currentChar().toString
	  source.nextChar()
	}
}

class EofToken(source: Source) extends Token(source) {
  protected override def extract() = ("eof", null, null)
}
