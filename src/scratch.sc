import ice.frontend.Scanner

/**
 * Created by john on 3/12/14.
 */
class ICode
class SymbolTable
class Token
class Scanner
class Parser {
  def parse() = {
    var s = new SymbolTable
    var i = null
    if (true) {
      val p =  StatementParser(null, s)
      val x = p(new Token)
      i = x._1
      s = x._2
    }
  }
}
object StatementParser {
  def apply(s: Scanner, st: SymbolTable) = {
    def parse(t: Token) = {
      (null, null)
    }
    parse _
  }
}
