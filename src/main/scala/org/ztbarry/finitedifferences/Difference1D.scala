package org.ztbarry.finitedifferences

trait Difference1D extends Mesh1D {

  def fun: Double => Double

  def getMeshValues(h: Int): Vector[Double] = {
    mesh(h).map(fun)
  }

}