package nexus.op

import nexus._
import nexus.algebra.Type
import nexus.impl.jvm._
import nexus.exec._
import nexus.impl.jvm.FloatTensor
import nexus.op._

/**
 * @author Tongfei Chen
 */
object MapTest extends App {

  object f extends Op1[Float, Float] {
    def tag(tx: Type[Float32]) = tx
    def differentiable = true
    def name = "Sin"
    def forward(x: Float) = math.sin(x).toFloat
    def backward(dy: Float, y: Float, x: Float) = dy * math.cos(x).toFloat
  }

  val a = Input[FloatTensor[$]]()

  val b = a |> Map(f)

  val bp = 0

}
