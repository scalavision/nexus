package nexus

import nexus.op._
import nexus.typelevel._
import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait ExprTensorMixin {

  implicit class ExprTensorOps[T[_, _ <: HList], D, A <: HList](val x: Expr[T[D, A]])(implicit val env: Env[T, D]) {

    def +(y: Expr[T[D, A]]): Expr[T[D, A]] = Add(x, y)
    def -(y: Expr[T[D, A]]): Expr[T[D, A]] = Sub(x, y)

    def |*|(y: Expr[T[D, A]]): Expr[T[D, A]] = EMul(x, y)
    def ⊙(y: Expr[T[D, A]]): Expr[T[D, A]] = EMul(x, y)

    def |/|(y: Expr[T[D, A]]): Expr[T[D, A]] = EDiv(x, y)

  }

}