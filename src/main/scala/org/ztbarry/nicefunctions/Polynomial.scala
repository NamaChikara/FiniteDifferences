package org.ztbarry.nicefunctions

class Polynomial(coef: Vector[Double]) {

  def this(coef: Array[Double]) = this(coef.toVector)

  override def toString(): String = {

    coef
      .zipWithIndex
      .map({ case(c, p) => s"${c}x^$p"})
      .mkString(" + ")


  }

  def eval(x: Double): Double = {

    coef
      .zipWithIndex
      .map({ case(c, p) => c * scala.math.pow(x, p) })
      .sum

  }

}
