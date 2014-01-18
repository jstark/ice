package ice.frontend

import ice.message.MessageProducer
import ice.intermediate._

abstract class Parser(scanner: Scanner) extends MessageProducer {
  def parse(): (ICode, SymbolTable)
  def errorCount: Int
  def currentToken = scanner.currentToken
  def nextToken() = scanner.nextToken
}