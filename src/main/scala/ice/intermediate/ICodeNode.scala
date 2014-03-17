package ice.intermediate

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import ICodeNodeType._
import ICodeKey._
import scala.collection.mutable

/**
 * Created by john on 2/2/14.
 */
trait ICodeNode {
  /**
   * The node's type
   */
  val nodeType: ICodeNodeType

  /**
   * The node's parent
   */
  var parent: ICodeNode

  /**
   * Add a child node.
   * @param node the node to add.
   * @return the child node.
   */
  def addChild(node: ICodeNode): ICodeNode

  /**
   * The node's children.
   * @return the node's children.
   */
  def children: List[ICodeNode]

  /**
   * Set a node attribute.
   * @param key the attribute key.
   * @param value the attribute value.
   */
  def setAttribute(key: ICodeKey, value: AnyRef): Unit

  /**
   * Get the value of a node attribute.
   * @param key the attribute key.
   * @return the attribute value.
   */
  def attribute(key: ICodeKey): AnyRef

  /**
   * Call a function for each attribute.
   * @param f the function to run.
   */
  def foreach_attribute(f: ((ICodeKey, AnyRef)) => Unit)

  /**
   * Make a copy of this node.
   * @return a copy of this node.
   */
  def copy(): ICodeNode
}

private class ICodeNodeImpl(override val nodeType: ICodeNodeType) extends ICodeNode {

  private val children_ = new ArrayBuffer[ICodeNode]()
  private val attributes_ = new mutable.HashMap[ICodeKey, AnyRef]()

  override var parent: ICodeNode = _

  /**
   * Add a child node.
   * @param node the node to add.
   * @return the child node.
   */
  override def addChild(node: ICodeNode): ICodeNode = {
    if (node != null) {
      children_ += node
      node.parent = this
    }
    return node
  }

  /**
   * The node's children.
   * @return the node's children.
   */
  override def children: List[ICodeNode] = children_.toList

  /**
   * Set a node attribute.
   * @param key the attribute key.
   * @param value the attribute value.
   */
  override def setAttribute(key: ICodeKey, value: AnyRef): Unit = {
    attributes_ += (key -> value)
  }

  /**
   * Get the value of a node attribute.
   * @param key the attribute key.
   * @return the attribute value.
   */
  override def attribute(key: ICodeKey): AnyRef = attributes_(key)

  /**
   * Make a copy of this node.
   * @return a copy of this node.
   */
  override def copy(): ICodeNode = {
    val cp = ICode.createICodeNode(nodeType)
    val impl = cp.asInstanceOf[ICodeNodeImpl]
    impl.attributes_ ++= attributes_
    return cp
  }

  override def toString = nodeType.toString

  /**
   * Call a function for each attribute.
   * @param f the function to run.
   */
  override def foreach_attribute(f: ((ICodeKey.ICodeKey, AnyRef)) => Unit) {
    attributes_.foreach[Unit](f)
  }
}
