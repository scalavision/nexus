package nexus.instances

import nexus._
import nexus.ops._
import nexus.tensor._
import nexus.tensor.instances._

/**
 * @author Tongfei Chen
 */
class RealExprIsReal[R](implicit R: IsReal[R]) extends IsReal[Symbolic[R]] {
  def add(x: Symbolic[R], y: Symbolic[R]) = Add(x, y)
  def sub(x: Symbolic[R], y: Symbolic[R]) = Sub(x, y)
  def neg(x: Symbolic[R]) = Neg(x)
  def mul(x: Symbolic[R], y: Symbolic[R]) = Mul(x, y)
  def div(x: Symbolic[R], y: Symbolic[R]) = Div(x, y)
  def inv(x: Symbolic[R]) = Inv(x)
  def exp(x: Symbolic[R]) = Exp(x)
  def log(x: Symbolic[R]) = Log(x)
  def expm1(x: Symbolic[R]) = Expm1(x)
  def log1p(x: Symbolic[R]) = Log1p(x)
  def abs(x: Symbolic[R]) = Abs(x)
  def sgn(x: Symbolic[R]) = ??? // Sgn(x)
  def sin(x: Symbolic[R]) = Sin(x)
  def cos(x: Symbolic[R]) = Cos(x)
  def tan(x: Symbolic[R]) = ??? //Tan(x)
  def sqr(x: Symbolic[R]) = Sqr(x)
  def sqrt(x: Symbolic[R]) = Sqrt(x)
  def toFloat(x: Symbolic[R]) = ???
  def one = Const(R.one)
  def zero = Const(R.zero)
  def addS(x1: Symbolic[R], x2: Double) = Add(x1, Const(R.fromDouble(x2)))
  def addI(x1: Symbolic[R], x2: Symbolic[R]): Unit = ???
  def fromDouble(d: Double) = Const(R.fromDouble(d))
}


object FloatExprIsReal extends RealExprIsReal()(Float32)
object DoubleExprIsReal extends RealExprIsReal()(Float64)
