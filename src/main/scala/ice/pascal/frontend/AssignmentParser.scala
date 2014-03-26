package ice.pascal.frontend

import ice.message.{MessageListener, MessageProducer}
import ice.intermediate._
import ice.frontend.Token

/**
 * Created by john on 3/19/14.
 */
class AssignmentParser(scanner: PascalScanner, symtabstack: SymTabStack) extends MessageProducer {
  def parse(token: Token): ICodeNode = {
    var tok = token
    // create the ASSIGN node
    val assign = ICode.createICodeNode(ICodeNodeType.ASSIGN)

    // lookup the target identifier in the symbol table stack.
    // enter the identifier into the table if it's not found.
    val target_name = tok.lexeme.toLowerCase()
    var target_id = symtabstack.lookupLocal(target_name)
    if (target_id == null) {
      target_id = symtabstack.enterLocal(target_name)
    }
    target_id.appendLineNumber(token.lineNumber)

    require(tok.ttype == tokens.IDENTIFIER)

    tok = scanner.nextToken() // consume the IDENTIFIER token

    // create the variable node and set its name attribute
    val variable = ICode.createICodeNode(ICodeNodeType.VARIABLE)
    variable.setAttribute(ICodeKey.ID, target_id)

    // the assign node adopts the variable node as its first child.
    assign.addChild(variable)

    // look for the := token
    if (tok.ttype == tokens.COLON_EQUALS) {
      tok = scanner.nextToken()
    } else {
      PascalErrorHandler.flag(tok, error.MISSING_COLON_EQUALS, this)
    }
    assign
  }
}

object AssignmentParser {
  def apply(scanner: PascalScanner, symtabstack: SymTabStack, listeners: Seq[MessageListener]) = {
    val ap = new AssignmentParser(scanner, symtabstack)
    ap.listeners ++= listeners
    ap
  }
}
