package ice.backend

import ice.message._
import ice.intermediate.SymbolTable
import ice.intermediate.ICode

abstract class Backend extends MessageProducer {
  def process(icode: ICode, symbolTable: SymbolTable)
}

/*
 * compiler
 */
class CodeGenerator extends Backend {
  def process(icode: ICode, symbolTable: SymbolTable) {
    val start = System.currentTimeMillis
    val elapsedTime = (System.currentTimeMillis - start)/1000.0f
    val instructionCount = 0
    
    val m = new Message(MessageType.COMPILER_SUMMARY, (instructionCount, elapsedTime))
    sendMessage(m)
  }
}

/*
 * interpreter
 */
class Executor extends Backend {
  def process(icode: ICode, symbolTable: SymbolTable) {
    val start = System.currentTimeMillis
    val elapsedTime = (System.currentTimeMillis - start)/1000.0f
    val executionCount = 0
    val runtimeErrors = 0
    
    val m = new Message(MessageType.INTERPRETER_SUMMARY, (executionCount, runtimeErrors, elapsedTime))
    sendMessage(m)
  }
}