package ice.utils

import java.io.PrintStream
import ice.intermediate.{SymTabEntry, ICodeNode, ICode}

/**
 * Created by john on 2/2/14.
 */
class ParseTreePrinter(ps: PrintStream) {
  private val INDENT_WIDTH = 4
  private val LINE_WIDTH = 80

  private var length_ = 0
  private var indentation_ = ""
  private var indent_ = (1 to INDENT_WIDTH) map ( _ => " ") mkString
  private val line_ = new StringBuilder()


  private def print(icode: ICode) {
    ps.println("\n===== INTERMEDIATE CODE =====\n")

    printNode(icode.root)
    printLine()
  }

  private def printNode(node: ICodeNode) {
    // opening tag.
    append(indentation_); append("<" + node.toString)

    printAttributes(node)
    printTypeSpec(node)
  }

  private def printAttributes(node: ICodeNode) {
    val saveIndentation = indentation_
    indentation_ += indent_

    node.foreach_attribute( attr => printAttribute(attr._1.toString, attr._2) )

    indentation_ = saveIndentation
  }

  private def printAttribute(keyString: String, value: AnyRef) {
    val isSymTabEntry = value.isInstanceOf[SymTabEntry]
    val valueString = if (isSymTabEntry) {
      val entry = value.asInstanceOf[SymTabEntry]
      entry.name
    } else value.toString()

    val text = keyString.toLowerCase() + "=\"" + valueString + "\""
    append(" "); append(text);

    // include and identifier's nesting level.
    if (isSymTabEntry) {
      val entry = value.asInstanceOf[SymTabEntry]
      val level = entry.symbolTable.nestingLevel
      printAttribute("LEVEL", new Integer(level))
    }
  }

  private def printChildNodes(childNodes: List[ICodeNode]) {
    val saveIndentation = indentation_
    indentation_ += indent_

    for (child <- childNodes) {
      printNode(child)
    }

    indentation_ = saveIndentation
  }

  private def printTypeSpec(node: ICodeNode) {

  }

  private def append(text: String) {
    val textLength = text.length
    var lineBreak = false

    // wrap lines that are too long.
    if (length_ + textLength > LINE_WIDTH) {
      printLine()
      line_.append(indentation_)
      length_ = indentation_.length
      lineBreak = true
    }

    // append the text.
    if (!lineBreak && text.equals(" ")) {
      line_.append(text)
      length_ += textLength
    }
  }

  private def printLine() {
    if (length_ > 0) {
      ps.println(line_)
      line_.setLength(0)
      length_ = 0
    }
  }
}
