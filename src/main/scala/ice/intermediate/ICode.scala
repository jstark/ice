package ice.intermediate

import ICodeNodeType._

trait ICode {
  /**
   * Set and return the root node.
   * @param node the node to set as root.
   * @return the root node.
   */
  def setRoot(node: ICodeNode): ICodeNode

  /**
   * The root node.
   * @return the root node.
   */
  val root: ICodeNode
}

private class ICodeImpl extends ICode {
  private var root_ : ICodeNode = _
  /**
   * Set and return the root node.
   * @param node the node to set as root.
   * @return the root node.
   */
  override def setRoot(node: ICodeNode): ICodeNode = {
    root_ = node
    return root_
  }

  /**
   * The root node.
   */
  override val root = root_
}

object ICode {
  def createICodeNode(nodeType: ICodeNodeType): ICodeNode = new ICodeNodeImpl(nodeType)
}