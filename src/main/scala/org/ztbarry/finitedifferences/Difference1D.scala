package org.ztbarry.finitedifferences

trait Difference1D {

  def fun: Double => Double

  def getMeshValues(mesh: Vector[Double]): Vector[Double] = {
    mesh.map(fun)
  }

}