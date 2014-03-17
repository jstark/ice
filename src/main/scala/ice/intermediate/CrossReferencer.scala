package ice.intermediate

/**
 * Created by john on 1/31/14.
 */
object CrossReferencer {
  private val NAME_WIDTH = 16
  private val NAME_FORMAT       = "%-" + NAME_WIDTH + "s"
  private val NUMBERS_LABEL     = " Line numbers     "
  private val NUMBERS_UNDERLINE = " ------------     "
  private val NUMBER_FORMAT     = " %03d"

  private val LABEL_WIDTH   = NUMBERS_LABEL.size
  private val INDENT_WIDTH  = NAME_WIDTH + LABEL_WIDTH

  private val INDENT = (0 until INDENT_WIDTH).map( _ => " ")

  def print(symTabStack: SymTabStack) {
    println("\n===== CROSS-REFERENCE TABLE =====")
    printColumnHeadings()
    printSymTab(symTabStack.localSymTab())
  }

  /**
   * print column headings
   */
  private def printColumnHeadings() {
    println()
    println(NAME_FORMAT.format("Identifier") + NUMBERS_LABEL)
    println(NAME_FORMAT.format("----------") + NUMBERS_UNDERLINE)
  }

  /**
   * print the entries in a symbol table.
   * @param symTab the symbol table.
   */
  private def printSymTab(symTab: SymTab) {
    // Loop over the sorted list of symbol table entries.
    val sorted = symTab.sortedEntries
    for (entry <- sorted) {
      val lineNumbers = entry.lineNumbers
      System.out.print(NAME_FORMAT.format(entry.name))
      for (number <- lineNumbers) System.out.print(NUMBER_FORMAT.format(number))
      println()
    }
  }
}
