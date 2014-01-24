package ice.pascal.frontend

import scala.collection.mutable.{ArrayBuffer, HashSet, HashMap}
import ice.frontend.TokenType

trait  Reserved { self: TokenType =>
  val name: String
  ReservedToken.append(name, this)
}

object ReservedToken {
  private var reserved_ = new HashMap[String, TokenType]
  def append(tokenName: String, tokenType: TokenType) = {
    reserved_ += (tokenName -> tokenType)
  }
  def contains(tokenName: String) = reserved_.contains(tokenName)
  def valueOf(tokenName: String) = reserved_(tokenName)
}

trait Special { self: TokenType => 
  val name: String
  SpecialToken.append(name, this)
}

object SpecialToken {
    private var special_ = new HashMap[String, TokenType]
    def append(tokenName: String, tokenType: TokenType) = 
        special_ += (tokenName -> tokenType)
}

class PascalTokenType1(val name: String) extends TokenType
class PascalTokenType2(val name: String, val special: String) extends TokenType

case object AND extends PascalTokenType1("AND") with Reserved
case object ARRAY extends PascalTokenType1("ARRAY") with Reserved
case object BEGIN extends PascalTokenType1("BEGIN") with Reserved
case object CASE extends PascalTokenType1("CASE") with Reserved
case object CONST extends PascalTokenType1("CONST") with Reserved
case object DIV extends PascalTokenType1("DIV") with Reserved
case object DO extends PascalTokenType1("DO") with Reserved
case object DOWNTO extends PascalTokenType1("DOWNTO") with Reserved
case object ELSE extends PascalTokenType1("ELSE") with Reserved
case object END extends PascalTokenType1("END") with Reserved
case object FILE extends PascalTokenType1("FILE") with Reserved
case object FOR extends PascalTokenType1("FOR") with Reserved
case object FUNCTION extends PascalTokenType1("FUNCTION") with Reserved
case object GOTO extends PascalTokenType1("GOTO") with Reserved
case object IF extends PascalTokenType1("IF") with Reserved
case object IN extends PascalTokenType1("IN") with Reserved
case object LABEL extends PascalTokenType1("LABEL") with Reserved
case object MOD extends PascalTokenType1("MOD") with Reserved
case object NIL extends PascalTokenType1("NIL") with Reserved
case object NOT extends PascalTokenType1("NOT") with Reserved
case object OF extends PascalTokenType1("OF") with Reserved
case object OR extends PascalTokenType1("OR") with Reserved
case object PACKED extends PascalTokenType1("PACKED") with Reserved
case object PROCEDURE extends PascalTokenType1("PROCEDURE") with Reserved
case object PROGRAM extends PascalTokenType1("PROGRAM") with Reserved
case object RECORD extends PascalTokenType1("RECORD") with Reserved
case object REPEAT extends PascalTokenType1("REPEAT") with Reserved
case object SET extends PascalTokenType1("SET") with Reserved
case object THEN extends PascalTokenType1("THEN") with Reserved
case object TO extends PascalTokenType1("TO") with Reserved
case object TYPE extends PascalTokenType1("TYPE") with Reserved
case object UNTIL extends PascalTokenType1("UNTIL") with Reserved
case object VAR extends PascalTokenType1("VAR") with Reserved
case object WHILE extends PascalTokenType1("WHILE") with Reserved
case object WITH extends PascalTokenType1("WITH") with Reserved

// special
case object PLUS extends PascalTokenType2("PLUS", "+") with Special
case object MINUS extends PascalTokenType2("MINUS", "-") with Special
case object STAR extends PascalTokenType2("STAR", "*") with Special
case object SLASH extends PascalTokenType2("SLASH", "/") with Special
case object COLON_EQUALS extends PascalTokenType2("COLON_EQUALS", ":=") with Special
case object DOT extends PascalTokenType2("DOT", ".") with Special
case object COMMA extends PascalTokenType2("COMMA", ",") with Special
case object SEMICOLON extends PascalTokenType2("SEMICOLON", ";") with Special
case object COLON extends PascalTokenType2("COLON", ":") with Special
case object QUOTE extends PascalTokenType2("QUOTE", "'") with Special
case object EQUALS extends PascalTokenType2("EQUALS", "=") with Special
case object NOT_EQUALS extends PascalTokenType2("NOT_EQUALS", "<>") with Special
case object LESS_THAN extends PascalTokenType2("LESS_THAN", "<") with Special
case object LESS_EQUALS extends PascalTokenType2("LESS_EQUALS", "<=") with Special
case object GREATER_THAN extends PascalTokenType2("GREATER_THAN", ">=") with Special
case object GREATER_EQUALS extends PascalTokenType2("GREATER_EQUALS", ">") with Special
case object LEFT_PAREN extends PascalTokenType2("LEFT_PAREN", "(") with Special
case object RIGHT_PAREN extends PascalTokenType2("RIGHT_PAREN", ")") with Special
case object LEFT_BRACKET extends PascalTokenType2("LEFT_BRACKET", "[") with Special
case object RIGHT_BRACKET extends PascalTokenType2("RIGHT_BRACKET", "]") with Special
case object LEFT_BRACE extends PascalTokenType2("LEFT_BRACE", "{") with Special
case object RIGHT_BRACE extends PascalTokenType2("RIGHT_BRACE", "}") with Special
case object UP_ARROW extends PascalTokenType2("UP_ARROW", "^") with Special
case object DOT_DOT extends PascalTokenType2("DOT_DOT", "..") with Special

// other
case object IDENTIFIER extends PascalTokenType1("IDENTIFIER")
case object INTEGER extends PascalTokenType1("INTEGER")
case object REAL extends PascalTokenType1("REAL")
case object STRING extends PascalTokenType1("STRING")
case object ERROR extends PascalTokenType1("ERROR")
case object END_OF_FILE extends PascalTokenType1("END_OF_FILE")

