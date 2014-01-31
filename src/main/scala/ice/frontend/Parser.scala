package ice.frontend

import ice.message.MessageProducer
import ice.intermediate._

abstract class Parser(scanner: Scanner) extends MessageProducer {
  val symTabStack = SymTab.createStack()
  def parse(): (ICode, SymTab)
  def errorCount: Int
  def currentToken = scanner.currentToken
  def nextToken() = scanner.nextToken
}