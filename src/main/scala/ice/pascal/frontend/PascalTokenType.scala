package ice.pascal.frontend

import scala.collection.mutable.{ArrayBuffer, HashSet, HashMap}
import ice.frontend.TokenType
import scala.collection.mutable

trait  Reserved { self: TokenType =>
  val name: String
  ReservedToken.append(name, this)
}

object ReservedToken {
  private var reserved_ = new mutable.HashMap[String, TokenType]
  def append(tokenName: String, tokenType: TokenType) = {
    reserved_ += (tokenName -> tokenType)
  }
  def contains(tokenName: String) = reserved_.contains(tokenName)
  def valueOf(tokenName: String) = reserved_(tokenName)
}

trait Special { self: TokenType => 
  val special: String
  SpecialToken.append(special, this)
}

object SpecialToken {
    private var special_ = new mutable.HashMap[String, TokenType]
    def append(tokenName: String, tokenType: TokenType) = {
      special_ += (tokenName -> tokenType)
    }
    def contains(tokenName: String) = special_.contains(tokenName)
    def valueOf(tokenName: String) = special_(tokenName)
}

class PascalTokenType1(val name: String) extends TokenType {
  override def toString = name
}

class PascalTokenType2(val name: String, val special: String) extends TokenType {
  override def toString = name
}

package object tokens {
  def init() {}
  val AND 		= new PascalTokenType1("AND") with Reserved
  val ARRAY 	= new PascalTokenType1("ARRAY") with Reserved
  val BEGIN 	= new PascalTokenType1("BEGIN") with Reserved
  val CASE 		= new PascalTokenType1("CASE") with Reserved
  val CONST 	= new PascalTokenType1("CONST") with Reserved
  val DIV	 	= new PascalTokenType1("DIV") with Reserved
  val DO 		= new PascalTokenType1("DO") with Reserved
  val DOWNTO 	= new PascalTokenType1("DOWNTO") with Reserved
  val ELSE 		= new PascalTokenType1("ELSE") with Reserved
  val END 		= new PascalTokenType1("END") with Reserved
  val FILE 		= new PascalTokenType1("FILE") with Reserved
  val FOR 		= new PascalTokenType1("FOR") with Reserved
  val FUNCTION 	= new PascalTokenType1("FUNCTION") with Reserved
  val GOTO 		= new PascalTokenType1("GOTO") with Reserved
  val IF 		= new PascalTokenType1("IF") with Reserved
  val IN 		= new PascalTokenType1("IN") with Reserved
  val LABEL 	= new PascalTokenType1("LABEL") with Reserved
  val MOD	 	= new PascalTokenType1("MOD") with Reserved
  val NIL 		= new PascalTokenType1("NIL") with Reserved
  val NOT 		= new PascalTokenType1("NOT") with Reserved
  val OF 		= new PascalTokenType1("OF") with Reserved
  val OR 		= new PascalTokenType1("OR") with Reserved
  val PACKED 	= new PascalTokenType1("PACKED") with Reserved
  val PROCEDURE = new PascalTokenType1("PROCEDURE") with Reserved
  val PROGRAM 	= new PascalTokenType1("PROGRAM") with Reserved
  val RECORD 	= new PascalTokenType1("RECORD") with Reserved
  val REPEAT 	= new PascalTokenType1("REPEAT") with Reserved
  val SET 		= new PascalTokenType1("SET") with Reserved
  val THEN 		= new PascalTokenType1("THEN") with Reserved
  val TO 		= new PascalTokenType1("TO") with Reserved
  val TYPE 		= new PascalTokenType1("TYPE") with Reserved
  val UNTIL 	= new PascalTokenType1("UNTIL") with Reserved
  val VAR 		= new PascalTokenType1("VAR") with Reserved
  val WHILE 	= new PascalTokenType1("WHILE") with Reserved
  val WITH 		= new PascalTokenType1("WITH") with Reserved

// special
  val PLUS 			 = new PascalTokenType2("PLUS", "+") with Special
  val MINUS 		 = new PascalTokenType2("MINUS", "-") with Special
  val STAR 			 = new PascalTokenType2("STAR", "*") with Special
  val SLASH 		 = new PascalTokenType2("SLASH", "/") with Special
  val COLON_EQUALS 	 = new PascalTokenType2("COLON_EQUALS", ":=") with Special
  val DOT 			 = new PascalTokenType2("DOT", ".") with Special
  val COMMA 		 = new PascalTokenType2("COMMA", ",") with Special
  val SEMICOLON 	 = new PascalTokenType2("SEMICOLON", ";") with Special
  val COLON 		 = new PascalTokenType2("COLON", ":") with Special
  val QUOTE 		 = new PascalTokenType2("QUOTE", "'") with Special
  val EQUALS 		 = new PascalTokenType2("EQUALS", "=") with Special
  val NOT_EQUALS 	 = new PascalTokenType2("NOT_EQUALS", "<>") with Special
  val LESS_THAN 	 = new PascalTokenType2("LESS_THAN", "<") with Special
  val LESS_EQUALS 	 = new PascalTokenType2("LESS_EQUALS", "<=") with Special
  val GREATER_THAN 	 = new PascalTokenType2("GREATER_THAN", ">=") with Special
  val GREATER_EQUALS = new  PascalTokenType2("GREATER_EQUALS", ">") with Special
  val LEFT_PAREN 	 = new PascalTokenType2("LEFT_PAREN", "(") with Special
  val RIGHT_PAREN 	 = new PascalTokenType2("RIGHT_PAREN", ")") with Special
  val LEFT_BRACKET 	 = new PascalTokenType2("LEFT_BRACKET", "[") with Special
  val RIGHT_BRACKET  = new PascalTokenType2("RIGHT_BRACKET", "]") with Special
  val LEFT_BRACE 	 = new PascalTokenType2("LEFT_BRACE", "{") with Special
  val RIGHT_BRACE 	 = new PascalTokenType2("RIGHT_BRACE", "}") with Special
  val UP_ARROW 		 = new PascalTokenType2("UP_ARROW", "^") with Special
  val DOT_DOT 		 = new PascalTokenType2("DOT_DOT", "..") with Special

// other
  val IDENTIFIER 	= new PascalTokenType1("IDENTIFIER")
  val INTEGER 		= new PascalTokenType1("INTEGER")
  val REAL 			= new PascalTokenType1("REAL")
  val STRING 		= new PascalTokenType1("STRING")
  val ERROR 		= new PascalTokenType1("ERROR")
  val END_OF_FILE 	= new PascalTokenType1("END_OF_FILE")
}

