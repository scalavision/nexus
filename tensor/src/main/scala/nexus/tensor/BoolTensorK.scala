package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait BoolTensorK[T[_], B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]

  val B: IsBool[B]
  def elementType = B

  def falseBy[A](x: T[A]): T[A]

  def trueBy[A](x: T[A]): T[A]

  def eNot[A](x: T[A]): T[A]

  def eAnd[A](x: T[A]): T[A]

  def eOr[A](x: T[A]): T[A]

  override def ground[A] = ???

}
