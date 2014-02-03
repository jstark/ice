package ice.intermediate

/**
 * Created by john on 2/2/14.
 */
object ICodeNodeType extends Enumeration {
  type ICodeNodeType = Value
  // Program structure
  val PROGRAM, PROCEDURE, FUNCTION,
  // Statements
  COMPOUND, ASSIGN, LOOP, TEST, CALL, PARAMETERS,
  IF, SELECT, SELECT_BRANCH, SELECT_CONSTANTS, NO_OP,
  // Relational operators
  EQ, NE, LT, LE, GT, GE, NOT,
  // Additive operators
  ADD, SUBTRACT, OR, NEGATE,
  // Multiplicative operators
  MULTIPLY, INTEGER_DIVIDE, FLOAT_DIVIDE, MOD, AND,
  // Operands
  VARIABLE, SUBSCRIPTS, FIELD,
  INTEGER_CONSTANT, REAL_CONSTANT,
  STRING_CONSTANT, BOOLEAN_CONSTANT = Value
}
