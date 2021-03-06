package nexus.modules

import nexus._
import nexus.tensor._
import nexus.util._

/**
 * 1-dimensional convolution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Convolution1D[T[_], R, W <: Dim, X <: Dim, Y <: Dim] private(
                                                                    val window: Int,
                                                                    val stride: Int,
                                                                    val weight: Param[T[(Y, X)]]
                                                                  )
                                                                  (implicit T: IsRealTensorK[T, R])
  extends Module1[T[(W, X)], T[(W, Y)]]
{

  def parameters = Set(weight)

  def apply(x: Symbolic[T[(W, X)]]) = ???
}


object Convolution1D {

  def apply[T[_], R, W <: Dim, X <: Dim, Y <: Dim](
    widthAxis: W,
    inputAxisSize: (X, Int),
    outputAxisSize: (Y, Int),
    window: Int,
    stride: Int = 1,
    name: String = ExprName.nextId("Convolution1D")
  )
  (implicit T: IsRealTensorK[T, R]): Convolution1D[T, R, W, X, Y] = {
    val (inputAxis, inputSize) = inputAxisSize
    val (outputAxis, outputSize) = outputAxisSize
    val key = ExprName.nextId("Convolution1D")
    val weight = Param(T.newTensor[(Y, X)](Array(outputSize, inputSize)), s"$key.weight")
    new Convolution1D[T, R, W, X, Y](window, stride, weight)
  }

}
