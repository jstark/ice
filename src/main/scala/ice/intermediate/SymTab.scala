package ice.intermediate

import scala.collection.mutable.HashMap

trait SymTab {
  /**
   * Getter.
   * @return the scope nesting level of this entry.
   */
  def nestingLevel: Int

  /**
   * Create and enter a new entry into the symbol table.
   * @param name the name of the entry.
   * @return the new entry
   */
  def enter(name: String): SymTabEntry

  /**
   * Lookup an existing symbol table entry.
   * @param name the name of the entry.
   * @return the entry, or null if it does not exist.
   */
  def lookup(name: String): SymTabEntry

  /**
   * @return a list of symbol table entries sorted by name.
   */
  def sortedEntries: List[SymTabEntry]
}

private class SymTabImpl(override val nestingLevel: Int) extends SymTab {
  val entries = new HashMap[String, SymTabEntry]
  /**
   * Create and enter a new entry into the symbol table.
   * @param name the name of the entry.
   * @return the new entry
   */
  override def enter(name: String): SymTabEntry = {
    val entry = SymTab.createEntry(name, this)
    entries += (name -> entry)
    entry
  }

  /**
   * Lookup an existing symbol table entry.
   * @param name the name of the entry.
   * @return the entry, or null if it does not exist.
   */
  override def lookup(name: String): SymTabEntry = entries.getOrElse(name, null)

  /**
   * @return a list of symbol table entries sorted by name.
   */
  override def sortedEntries: List[SymTabEntry] = entries.values.toList.sortBy(s => s.name)
}

object SymTab {
  /**
   * Create a new entry in a symbol table.
   * @param name the name of the entry.
   * @param table the table into which an entry is created
   * @return the new entry.
   */
  def createEntry(name: String, table: SymTab): SymTabEntry = new SymTabEntryImpl(name, table)

  /**
   * Create a new symbol table.
   * @param nestingLevel the table's nesting level.
   * @return the created symbol table.
   */
  def createTable(nestingLevel: Int): SymTab = new SymTabImpl(nestingLevel)

  /**
   * Create a new symbol table stack.
   * @return The new symbol table stack.
   */
  def createStack(): SymTabStack = new SymTabStackImpl()
}