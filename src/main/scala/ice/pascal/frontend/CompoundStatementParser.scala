package ice.pascal.frontend

import ice.message.{MessageListener, MessageProducer}
import ice.frontend.Token
import ice.intermediate.{ICodeNodeType, ICode, SymTabStack, ICodeNode}

/**
 * Created by john on 3/19/14.
 */
class CompoundStatementParser(scanner: PascalScanner, symtabstack: SymTabStack) extends MessageProducer {
  def parse(token: Token): ICodeNode = {
    var tok = token
    require(tok.ttype == tokens.BEGIN)

    // consume BEGIN
    tok = scanner.nextToken()

    // create COMPOUND node.
    val compound = ICode.createICodeNode(ICodeNodeType.COMPOUND)

    // parse the statement list terminated by the END token.
    val statement_parser = StatementParser(scanner, symtabstack, listeners)
    statement_parser.parseList(tok, compound, tokens.END, error.MISSING_END)
    compound
  }
}

object CompoundStatementParser {
  def apply(scanner: PascalScanner, symtabstack: SymTabStack, listeners: Seq[MessageListener]) = {
    val cp = new CompoundStatementParser(scanner, symtabstack)
    cp.listeners ++= listeners
    cp
  }
}
