package ice.intermediate

import scala.collection.mutable.Stack
import scala.collection.mutable

/**
 * Created by john on 1/31/14.
 */
trait SymTabStack {
  /**
   * Getter
   * @return the current nesting level
   */
  def nestingLevel: Int

  /**
   * Return the local symbol table which is at the top of the stack
   * @return the local symbol table
   */
  def localSymTab(): SymTab

  /**
   * Create and enter a new entry into the local symbol table
   * @param name the name of the entry
   * @return the new entry
   */
  def enterLocal(name: String): SymTabEntry

  /**
   * Lookup an existing symbol table entry in the local symbol table.
   * @param name the name of the entry
   * @return the entry, or null if it does not exist.
   */
  def lookupLocal(name: String): SymTabEntry

  /**
   * Lookup an existing symbol table entry throughout the stack.
   * @param name the name of the entry.
   * @return the entry, or null if it does not exist.
   */
  def lookup(name: String): SymTabEntry
}

private class SymTabStackImpl extends SymTabStack {

  val symbolTables = new mutable.Stack[SymTab]()
  /**
   * Getter
   * @return the current nesting level
   */
  override def nestingLevel: Int = 0

  /**
   * Create and enter a new entry into the local symbol table
   * @param name the name of the entry
   * @return the new entry
   */
  override def enterLocal(name: String): SymTabEntry = symbolTables(nestingLevel).enter(name)

  /**
   * Return the local symbol table which is at the top of the stack
   * @return the local symbol table
   */
  override def localSymTab(): SymTab = symbolTables(nestingLevel)

  /**
   * Lookup an existing symbol table entry in the local symbol table.
   * @param name the name of the entry
   * @return the entry, or null if it does not exist.
   */
  override def lookupLocal(name: String): SymTabEntry = symbolTables(nestingLevel).lookup(name)

  /**
   * Lookup an existing symbol table entry throughout the stack.
   * @param name the name of the entry.
   * @return the entry, or null if it does not exist.
   */
  override def lookup(name: String): SymTabEntry = lookupLocal(name)
}
