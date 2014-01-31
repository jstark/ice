package ice.intermediate

/**
 * Created by john on 1/31/14.
 */
object SymTabKey extends Enumeration {
  type SymTabKey = Value
  // Constant
  val ConstantValue,
  // Procedure or function
  RoutineCode, RoutineSymTab, RoutineICode,
  RoutineParms, Routine_Routines,
  // variable or record field value
  DataValue = Value
}