package ice.pascal.frontend

class PascalErrorCode(val message: String, val status: Int)

object PascalErrorCode {
  private var status_ = 0
  
  def nextStatus = { status_ += 1; status_ }
  
  def apply(message: String, id: Int = 0) = {
    val status = if (id == 0) nextStatus else id
    new PascalErrorCode(message, status)
  }
}

package object error {
  val ALREADY_FORMATTED    = PascalErrorCode("Already specified in FORWARD")
  val ALREADY_REDEFINED    = PascalErrorCode("Redefined identifier")
  val IDENTIFIER_UNDEFINED = PascalErrorCode("Undefined identifier")
  val INCOMPATIBLE_ASSIGNMENT = PascalErrorCode("Incompatible assignment")
  val INCOMPATIBLE_TYPES   = PascalErrorCode("Incompatible types")
  val INVALID_ASSIGNMENT   = PascalErrorCode("Invalid assignment statement")
  val INVALID_CHARACTER    = PascalErrorCode("Invalid character")
  val INVALID_CONSTANT     = PascalErrorCode("Invalid constant")
  //
  val INVALID_EXPONENT     = PascalErrorCode("Invalid exponent")
  val INVALID_EXPRESSION   = PascalErrorCode("Invalid expression")
  val INVALID_FIELD        = PascalErrorCode("Invalid field")
  val INVALID_FRACTION     = PascalErrorCode("Invalid fraction")
  val INVALID_IDENTIFIER_USAGE = PascalErrorCode("Invalid identifier usage")
  val INVALID_INDEX_TYPE   = PascalErrorCode("Invalid index type")
  val INVALID_NUMBER       = PascalErrorCode("Invalid number")
  val INVALID_STATEMENT    = PascalErrorCode("Invalid statement")
  val INVALID_SUBRANGE_TYPE= PascalErrorCode("Invalid subrange type")
  val INVALID_TARGET       = PascalErrorCode("Invalid assignment target")
  val INVALID_TYPE         = PascalErrorCode("Invalid type")
  val INVALID_VAR_PARM     = PascalErrorCode("Invalid VAR parameter")
  val MIN_GT_MAX           = PascalErrorCode("Min limit greater than max limit")
  val MISSING_BEGIN        = PascalErrorCode("Missing BEGIN")
  val MISSING_COLON        = PascalErrorCode("Missing :")
  val MISSING_COLON_EQUALS = PascalErrorCode("Missing :=")
  val MISSING_COMMA        = PascalErrorCode("Missing ,")
  val MISSING_CONSTANT     = PascalErrorCode("Missing constant")
  val MISSING_DO           = PascalErrorCode("Missing DO")
  val MISSING_DOT_DOT      = PascalErrorCode("Missing ..")
  val MISSING_END          = PascalErrorCode("Missing END")
  val MISSING_EQUALS       = PascalErrorCode("Missing =")
  val MISSING_FOR_CONTROL  = PascalErrorCode("Invalid FOR control variable")
  val MISSING_IDENTIFIER   = PascalErrorCode("Missing identifier")
  val MISSING_LEFT_BRACKET = PascalErrorCode("Missing [")
  val MISSING_OF           = PascalErrorCode("Missing OF")
  val MISSING_PERIOD       = PascalErrorCode("Missing .")
  val MISSING_PROGRAM      = PascalErrorCode("Missing PROGRAM")
  val MISSING_RIGHT_BRACKET= PascalErrorCode("Missing ]")
  val MISSING_RIGHT_PAREN  = PascalErrorCode("Missing )")
  //
  val MISSING_SEMICOLON    = PascalErrorCode("Missing ;")
  val MISSING_THEN         = PascalErrorCode("Missing THEN")
  val MISSING_TO_DOWNTO    = PascalErrorCode("Missing TO or DOWNTO")
  val MISSING_UNTIL        = PascalErrorCode("Missing UNTIL")
  val MISSING_VARIABLE     = PascalErrorCode("Missing variable")
  val CASE_CONSTANT_REUSED = PascalErrorCode("CASE constant reused")
  val NOT_CONSTANT_IDENTIFIER = PascalErrorCode("Not a constant identifier")
  val NOT_RECORD_VARIABLE  = PascalErrorCode("Not a record variable")
  val NOT_TYPE_IDENTIFIER  = PascalErrorCode("Not a type identifier")
  val RANGE_INTEGER        = PascalErrorCode("Integer literal out of range")
  val RANGE_REAL           = PascalErrorCode("Real literal out of range")
  val STACK_OVERFLOW       = PascalErrorCode("Stack overflow")
  val TOO_MANY_LEVELS      = PascalErrorCode("Nesting level too deep")
  val TOO_MANY_SUBSCRIPTS  = PascalErrorCode("Too many subscripts")
  val UNEXPECTED_EOF       = PascalErrorCode("Unexpected end of file")
  val UNEXPECTED_TOKEN     = PascalErrorCode("Unexpected token")
  val UNIMPLEMENTED        = PascalErrorCode("Unimplemented feature")
  val UNRECOGNIZABLE       = PascalErrorCode("Unrecognizable input")
  val WRONG_NUMBER_OF_PARMS= PascalErrorCode("Wrong number of actual parameters")
  //
  val IO_ERROR             = PascalErrorCode("Object I/O error", -101)
  val TOO_MANY_ERRORS      = PascalErrorCode("Too many syntax errors", -102)
}