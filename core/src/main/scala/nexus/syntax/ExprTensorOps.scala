package nexus.syntax

import nexus._
import nexus.ops._
import nexus.tensor._

/**
 * @author Tongfei Chen
 */
trait ExprRealTensorMixin {

  implicit class ExprRealTensorOps[T[_], R, A](val x: Symbolic[T[A]])(implicit T: IsRealTensorK[T, R]) {

    def +(y: Symbolic[T[A]]): Symbolic[T[A]] = Add(x, y)
    def -(y: Symbolic[T[A]]): Symbolic[T[A]] = Sub(x, y)

    def |*|(y: Symbolic[T[A]]): Symbolic[T[A]] = Mul(x, y)
    def ⊙(y: Symbolic[T[A]]): Symbolic[T[A]] = Mul(x, y)

    def |/|(y: Symbolic[T[A]]): Symbolic[T[A]] = Div(x, y)

  }

}
