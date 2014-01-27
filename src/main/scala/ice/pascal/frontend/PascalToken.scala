package ice.pascal.frontend

import ice.frontend.{Source, Token}
import scala.collection.mutable.StringBuilder
import scala.collection.mutable
import scala.math._

class PascalToken(source: Source) extends Token(source)

class PascalErrorToken(
    source: Source, 
    errorCode: PascalErrorCode, 
    text: String) extends PascalToken(source) {
  
  lexeme_ = text
  ttype_ = ERROR
  tvalue_ = errorCode
  
  protected override def extract() {}
  
}


class PascalWordToken(source: Source) extends PascalToken(source) {
  
  protected override def extract() {
    val textBuffer = new StringBuilder()
    var current = source.currentChar()
    // Get the word characters (letter or digit). The scanner has
    // already determined that the first character is a letter.
    while (current.isLetterOrDigit) {
      textBuffer += current
      current = source.nextChar()
    }
    
    lexeme_ = textBuffer.toString()
    
    // is it a reserved word or an identifier ?
    ttype_ = if (ReservedToken.contains(lexeme_.toLowerCase())) {
      ReservedToken.valueOf(lexeme_.toUpperCase())
    } else IDENTIFIER
  }
}

class PascalStringToken(source: Source) extends PascalToken(source) {
  
  protected override def extract() {
    val textBuffer = new StringBuilder()
    val valueBuffer= new StringBuilder()
    
    var current = source.nextChar() // consume initial quote
    textBuffer += '\''
     
    // get string characters
    do {
      // replace any whitespace character with a blank.
      if (current.isWhitespace) {
        current = ' '
      }
      
      if (current != '\'' && current != Source.EndOfFile) {
        textBuffer += current
        valueBuffer+= current
        current = source.nextChar() // consume character
      }
      
      // quote? each pair of adjacent quotes represent a single-quote
      if (current == '\'') {
        while (current == '\'' && source.peekChar() == '\'') {
          textBuffer.append("''")
          valueBuffer += current // append single quote
          current = source.nextChar() // consume pair of quotes
          current = source.nextChar()
        }
      }
    } while (current != '\'' && current != Source.EndOfFile)
    
    if (current == '\'') {
      source.nextChar() // consume final quote
      textBuffer += '\''
      ttype_ = STRING
      tvalue_ = valueBuffer.toString()
    } else {
      ttype_ = ERROR
      tvalue_ = error.UNEXPECTED_EOF
    }
    lexeme_ = textBuffer.toString()
  }
}

class PascalSpecialSymbolToken(source: Source) extends PascalToken(source) {
  
  protected override def extract() {
    val textBuffer = new StringBuilder()
    val single = "+-*/,;'=()[]{}^".toList
    var current = source.currentChar()
    textBuffer += current
    ttype_ = null
    
    if (single.contains(current)) {
      source.nextChar()
    } else if (current == ':') {
      current = source.nextChar() // consume ':'
      if (current == '=') {
        textBuffer += current
        source.nextChar() // consume '='
      }
    } else if (current == '<') {
      current = source.nextChar() // consume '<'
      if (current == '=') {
        textBuffer += current
        source.nextChar() // consume '='
      } else if (current == '>') {
        textBuffer += current
        source.nextChar() // consume '>'
      } 
    } else if (current == '>') {
      current = source.nextChar() // consume '>'
      if (current == '=') {
        textBuffer += current
        source.nextChar() // consume '='
      }
    } else {
      source.nextChar() // consume bad character
      ttype_ = ERROR
      tvalue_ = error.INVALID_CHARACTER
    }
    
    lexeme_ = textBuffer.toString()
    // set the type if it wasn't an error.
    if (ttype_ == null) {
      ttype_ = SpecialToken.valueOf(lexeme_)
    }
  }
}

class PascalNumberToken(source: Source) extends PascalToken(source) {
  
  protected override def extract() {
    val textBuffer = new StringBuilder()
    extractNumber(textBuffer)
    lexeme_ = textBuffer.toString()
  }
  
  private def extractNumber(textBuffer: StringBuilder) {
    var wholeDigits: String = null
    var fractionDigits: String = null
    var exponentDigits: String = null
    var exponentSign = '+'
    var sawDotDot = false
    ttype_ = INTEGER // assume INTEGER token type for now
    
    // extract the digits of the whole part of the number
    wholeDigits = unsignedIntegerDigits(textBuffer)
    if (ttype_ == ERROR) {
      return
    }
    
    // is there a . ?
    // it could be a decimal point or the start of a .. token
    var current = source.currentChar()
    if (current == '.') {
      if (source.peekChar() == '.') {
        sawDotDot = true // it's a ".." token, so don't consume it
      } else {
        ttype_ = REAL // decimal point, so token is REAL
        textBuffer += current
        current = source.nextChar() // consume decimal point
        
        // collect the digits of the fraction part of the number,
        fractionDigits = unsignedIntegerDigits(textBuffer)
        if (ttype_ == ERROR) {
          return
        }
      }
    }
    
    // is there an exponent part ? 
    // there cannot be an exponent part if we already saw a ".." token
    current = source.currentChar()
    if (!sawDotDot && (current == 'E' || current == 'e')) {
      ttype_ = REAL // exponent, so token type is REAL
      textBuffer += current
      current = source.nextChar() // consume 'E' or 'e'
      
      // exponent sign ?
      if (current == '+' || current == '-') {
        textBuffer += current
        exponentSign = current
        current = source.nextChar() // consume '+' or '-'
      }
      
      // extract the digits of the exponent
      exponentDigits = unsignedIntegerDigits(textBuffer)
    }
    
    // compute the value of an integer number token
    if (ttype_ == INTEGER) {
      val integerValue: java.lang.Integer = computeIntegerValue(wholeDigits)
      if (ttype_ != ERROR) {
        tvalue_ = integerValue
      }
    } else if (ttype_ == REAL) {
      val floatValue: java.lang.Double = computeFloatValue(wholeDigits, fractionDigits, exponentDigits, exponentSign)
      if (ttype_ != ERROR) {
        tvalue_ = floatValue
      }
    }
  }
  
  private def unsignedIntegerDigits(textBuffer: StringBuilder): String = {
    var current = source.currentChar()
    // must have at least one digit
    if (!current.isDigit) {
      ttype_ = ERROR
      tvalue_ = error.INVALID_NUMBER
      return null
    }
    
    // extract the digits
    val digits = new StringBuilder()
    while (current.isDigit) {
      textBuffer += current
      digits += current
      current = source.nextChar() // consume digit
    }
    return digits.toString()
  }
  
  private def computeIntegerValue(digits: String): Int = {
    // return 0 if no digits.
    if (digits == null) {
      return 0
    }
    
    var integerValue = 0
    var prevValue = -1 // overflow occurred if prevValue, integerValue
    var index = 0
    
    // loop over the digits to compute the integer value
    // as long as there is not overflow.
    while (index < digits.size && integerValue >= prevValue) {
      prevValue = integerValue
      integerValue = 10 * integerValue + digits.charAt(index)
      index += 1
    }
    // no overflow: return the integer value.
    if (integerValue >= prevValue) {
      return integerValue
    } else {
      ttype_ = ERROR
      tvalue_ = error.RANGE_INTEGER
      return 0
    }
  }
  
  private def computeFloatValue(wholeDigits: String, fractionDigits: String, exponentDigits: String, exponentSign: Char): Double = {
    var floatValue = 0.0
    var exponentValue = computeIntegerValue(exponentDigits)
    var digits = wholeDigits
    
    // negate the exponent if the sign is '-'
    if (exponentSign == '-') {
      exponentValue = -exponentValue
    }
    
    // if there are any fraction digits, adjust the exponent value
    // and append the fraction digits.
    if (fractionDigits != null) {
      exponentValue -= fractionDigits.size
      digits += fractionDigits
    }
    
    // check for a real number out of range error
    if (abs(exponentValue + wholeDigits.size) > java.lang.Double.MAX_EXPONENT) {
      ttype_ = ERROR
      tvalue_ = error.RANGE_REAL
      return 0.0
    }
    
    // loop over the digits to compute the float value
    var index = 0
    while (index < digits.length()) {
      floatValue = 10 * floatValue + digits.charAt(index)
      index += 1
    }
    
    // adjust the float value based on the exponent value.
    if (exponentValue != 0) {
      floatValue *= pow(10.0, exponentValue)
    }
    return floatValue
  }
}