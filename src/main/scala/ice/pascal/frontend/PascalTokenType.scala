package ice.pascal.frontend

import scala.collection.mutable.{Set => MutableSet}
import ice.frontend.TokenType

object PascalTokenType extends Enumeration with TokenType {
  // reserved
  val AND, ARRAY, BEGIN, CASE, CONST, DIV, DO, DOWNTO, ELSE, END,
  FILE, FOR, FUNCTION, GOTO, IF, IN, LABEL, MOD, NIL, NOT, OF, OR,
  PACKED, PROCEDURE, PROGRAM, RECORD, REPEAT, SET, THEN, TO, TYPE,
  UNTIL, VAR, WHILE, WITH = Value
  // special
  val PLUS = Value("+")
  val MINUS= Value("-")
  val STAR = Value("*")
  val SLASH= Value("/")
  val COLON_EQUALS = Value(":=")
  val DOT  = Value(".")
  val COMMA= Value(",")
  val SEMICOLON = Value(";")
  val COLON= Value(":")
  val QUOTE= Value("'")
  val EQUALS = Value("=")
  val NOT_EQUALS = Value("<>")
  val LESS_THAN = Value("<")
  val LESS_EQUALS = Value("<=")
  val GREATER_EQUALS = Value(">=")
  val GREATER_THAN = Value(">")
  val LEFT_PAREN = Value("(")
  val RIGHT_PAREN = Value(")")
  val LEFT_BRACKET = Value("[")
  val RIGHT_BRACKET = Value("]")
  val LEFT_BRACE = Value("{")
  val RIGHT_BRACE= Value("}")
  val UP_ARROW = Value("^")
  val DOT_DOT = Value("..")
  // other
  val IDENTIFIER, INTEGER, REAL, STRING, ERROR, END_OF_FILE = Value
  
  // helpful calculations
  val FIRST_SPECIAL_INDEX  = PLUS.id
  val LAST_SPECIAL_INDEX   = DOT_DOT.id
  
  // lower-cased Pascal reserved word text strings
  val RESERVED = values.filter(v => v.id >= AND.id && v.id <= WITH.id).map(v => v.toString.toLowerCase)
  val SPECIAL = values.filter(v => v.id >= PLUS.id && v.id <= DOT_DOT.id).map(v => (v.toString, v)).toMap
}