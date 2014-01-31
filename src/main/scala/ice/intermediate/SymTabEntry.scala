package ice.intermediate

import SymTabKey._
import scala.collection.mutable.{HashMap, ArrayBuffer}

/**
 * Created by john on 1/31/14.
 */
trait SymTabEntry {
  /**
   * the name of the entry
   */
  val name: String

  /**
   * the symbol table that contains this entry.
   */
  val symbolTable: SymTab

  /**
   * Append a source line number to the entry.
   * @param line the line number to append.
   */
  def appendLineNumber(line: Int)

  /**
   * Getter.
   * @return the list of source line numbers.
   */
  def lineNumbers: List[Int]

  /**
   * Set an attribute of the entry.
   * @param key the attribute key.
   * @param value the attribute value.
   */
  def setAttribute(key: SymTabKey, value: AnyRef)

  /**
   * Get the value of an attribute of the entry.
   * @param key the attribute key.
   * @return the attribute key.
   */
  def attribute(key: SymTabKey): AnyRef
}

/**
 * Symbol table entry implementation.
 * @param name the name of the entry.
 * @param symbolTable the symbol table that contains the entry.
 */
private class SymTabEntryImpl(override val name: String, override val symbolTable: SymTab) extends SymTabEntry {
  val entries = new HashMap[SymTabKey, AnyRef]()
  val lineNumbers_ = new ArrayBuffer[Int]()

  /**
   * Getter.
   * @return the list of source line numbers.
   */
  def lineNumbers = lineNumbers_.toList

  /**
   * Append a source line number to the entry.
   * @param line the line number to append.
   */
  override def appendLineNumber(line: Int) = lineNumbers_ += line

  /**
   * Set an attribute of the entry.
   * @param key the attribute key.
   * @param value the attribute value.
   */
  override def setAttribute(key: SymTabKey, value: AnyRef) = entries += (key -> value)

  /**
   * Get the value of an attribute of the entry.
   * @param key the attribute key.
   * @return the attribute key.
   */
  override def attribute(key: SymTabKey): AnyRef = entries(key)
}
