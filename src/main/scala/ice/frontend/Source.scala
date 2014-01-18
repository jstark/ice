package ice.frontend

import scala.io.BufferedSource
import ice.message._

/**
 * Created by john on 1/18/14.
 */
class Source(reader: BufferedSource) extends MessageProducer {
  private val READ_FIRST_LINE = -2
  private val READ_NEXT_LINE = -1
  
  private var currentPosition_ = READ_FIRST_LINE
  private var currentLine_ = 0
  private var lineText_ = ""
  private val lineIterator_ = reader.getLines
  
  def currentLine = currentLine_
  def currentPosition = currentPosition_
  
  def currentChar(): Char = {
    if (currentPosition_ == READ_FIRST_LINE) {
      readNextLine()
      nextChar()
    } else if (lineText_ == null) {
      Source.EndOfFile
    } else if (currentPosition_ == READ_NEXT_LINE || currentPosition_ == lineText_.size) {
      Source.EndOfLine
    } else if (currentPosition_ > lineText_.size) {
      readNextLine()
      nextChar()
    } else {
      lineText_.charAt(currentPosition_)
    }
  }
  
  def nextChar() = {
    currentPosition_ += 1
    currentChar()
  }
  
  def peekChar() = {
    currentChar()
    if (lineText_ == null) Source.EndOfFile
    
    val nextPos = currentPosition_ + 1
    if (nextPos < lineText_.size) lineText_.charAt(nextPos) else Source.EndOfLine 
    
  }
  
  private def readNextLine() {
    lineText_ = if (lineIterator_.hasNext) lineIterator_.next else null
    currentPosition_ += 1
    if (lineText_ != null) {
      currentLine_ += 1
      
      val m = new Message(MessageType.SOURCE_LINE, (currentLine_, lineText_))
      sendMessage(m)
    }
  }
}

object Source {
  val EndOfLine = '\n'
  val EndOfFile = '\0'
}
