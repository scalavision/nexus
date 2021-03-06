package nexus.tensor

import algebra.ring._

import scala.annotation._

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${R} is a real number.")
trait IsReal[@specialized(Float, Double) R] extends Grad[R] {
  def zeroBy(x: R) = zero
  def toFloat(x: R): Float

  def mutable = false

  def zero: R
  def one: R
  def fromDouble(d: Double): R
  def fromFloat(f: Float): R = fromDouble(f)

  def add(x: R, y: R): R
  def sub(x: R, y: R): R
  def neg(x: R): R
  def mul(x: R, y: R): R
  def div(x: R, y: R): R
  def inv(x: R): R

  def exp(x: R): R
  def log(x: R): R
  def expm1(x: R): R
  def log1p(x: R): R

  def abs(x: R): R
  def sgn(x: R): R

  def sin(x: R): R
  def cos(x: R): R
  def tan(x: R): R

  def sqr(x: R): R
  def sqrt(x: R): R

  def eMul(x1: R, x2: R) = mul(x1, x2)
  def eDiv(x1: R, x2: R) = div(x1, x2)
  def eSqrt(x: R) = sqrt(x)
  def scale(x: R, k: Double) = mul(x, fromDouble(k))

}


object IsReal {

  def apply[R](implicit R: IsReal[R]) = R

  implicit def extract[T[_], R](implicit T: IsRealTensorK[T, R]): IsReal[R] = T.R

}
