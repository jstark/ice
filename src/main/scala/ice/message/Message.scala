package ice.message

import scala.collection.mutable.ArrayBuffer

object MessageType extends Enumeration {
  val SOURCE_LINE, SYNTAX_ERROR, PARSER_SUMMARY,
  INTERPRETER_SUMMARY, COMPILER_SUMMARY, MISCELLANEOUS, TOKEN,
  ASSIGN, FETCH, BREAKPOINT, RUNTIME_ERROR, CALL, RETURN = Value
}

class Message(val mtype: MessageType.Value, val args: AnyRef)

trait MessageListener {
  def acceptMessage(message: Message)
}

trait MessageProducer {
  private var listeners = ArrayBuffer[MessageListener]()
  
  def addMessageListener(listener: MessageListener) {
    require(!listeners.contains(listener))
    listeners += listener
  }
  
  def removeMessageListener(listener: MessageListener) {
    require(listeners.contains(listener))
    listeners -= listener
  }
  
  def sendMessage(message: Message) {
    for (listener <- listeners) listener.acceptMessage(message)
  }
}