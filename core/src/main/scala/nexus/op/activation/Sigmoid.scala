package nexus.op.activation

import nexus._
import nexus.algebra._
import nexus.op._

/**
 * Sigmoid (a.k.a. logistic) activation function that maps any real output to the interval (0, 1).
 *
 * Input: any tensor 「bb"x"」.
 *
 * Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends PolyDOp1[SigmoidF]

@implicitNotFound("Cannot apply Sigmoid to ${X}.")
trait SigmoidF[X, Y] extends DOp1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: TypedRealTensorOps[T, R]) = new SigmoidF[T[A], T[A]] {
    import T._
    def gradOps = T.ground[A]
    def forward(x: T[A]) = sigmoid(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y |*| addS(-y, R.one)
  }

}
