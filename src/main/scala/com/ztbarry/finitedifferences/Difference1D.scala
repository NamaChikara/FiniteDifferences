package com.ztbarry.finitedifferences
import scala.math.log10

/** Methods for estimating integrals on a regular partition.
 *
 * The following must be specified by classes that mix in Difference1D:
 * @param numericalMethod  1-dimensional finite difference method
 * @param fun              function to apply the numerical method to
 *
 * @note The following classes mix in Difference1D:
 *       TrapezoidalRule
 */

trait Difference1D extends Mesh1D {

  def numericalMethod(meshValues: Vector[Double]): Double

  def fun: Double => Double

  /**
   * Calculates log base 2 of the input.
   */
  private val log2 = (x: Double) => log10(x)/log10(2.0)

  /**
   * Evaluates fun over a regular partition of an interval.
   *
   * @param  n  the number of partitions.
   * @return an ordered vector containing the values of fun on the points of the partition, including endpoints.
   */
  private def getMeshValues(n: Int): Either[String, Vector[Double]] = {
    for {
      myMesh <- mesh(n)
    } yield {
      myMesh.map(fun)
    }
  }

  /**
   * Approximates the integral of fun using numericalMethod on a partitioned interval.
   *
   * @param  n  the number of partitions.
   * @return the approximation of the integral.
   */
  def approxIntegral(n: Int): Either[String, Double] = {
    for {
      meshValues <- getMeshValues(n)
    } yield {
      numericalMethod(meshValues)
    }
  }

  /**
   * Calculates the ratio of differences between integral approximations with n and n-2 partitions and n-2 and n-4.
   *
   * @param  num  the difference in integral approximations between n and n-2 partitions
   * @param  den  the difference in integral approximations between n-2 and n-4 partitions
   * @param  n    the number of partitions in the coarsest grid.
   * @return the ratio of the difference in approximations.
   */
  private def getDifferenceRatio(num: Double, den: Double, n: Int): Either[String, Double] = {
    if (num == 0) {
      Left(f"Grid approximation of integral identical for n = $n and n = ${n * 2}.")
    } else if (den == 0) {
      Left(f"Grid approximation of integral identical for n = ${n * 2} and n = ${n * 4}.")
    } else {
      val ratio = num / den
      if (ratio < 0) {
        Left(f"Ratio of errors is less than 0.")
      } else {
        Right(log2(ratio))
      }
    }
  }

  // http://www.csc.kth.se/utbildning/kth/kurser/DN2255/ndiff13/ConvRate.pdf
  /**
   * Approximates the convergence order of numericalMethod for fun's integral via didactic mesh refinement.
   *
   * @param  n  the number of partitions.
   * @return convergence order approximation.
   */
  def approxConvergenceOrder(n: Int): Either[String, Double] = {
    val differences = for {
      nApprox  <- approxIntegral(n)
      n2Approx <- approxIntegral(n * 2)
      n4Approx <- approxIntegral(n * 4)
    } yield {
      Vector(nApprox - n2Approx, n2Approx - n4Approx)
    }

    differences.fold(
      l => Left(l),
      r => getDifferenceRatio(r(0), r(1), n)
    )
  }

  /**
   * Attempts to find the convergence order of numericalMethod for fun's integral via didactic mesh refinement.
   *
   * @param  nStart         the number of partitions of the coarsest mesh.
   * @param  cutoff         order is returned once the relative difference between mesh refinements falls beneath cutoff
   * @param  maxIterations  the number of times to refine the partition before aborting the algorithm
   * @return an ordered vector containing the points of the partition, including endpoints.
   */
  def findConvergenceOrder(nStart: Int = 10, cutoff: Double = 0.01, maxIterations: Int = 10): Either[String, Double] = {
    if (maxIterations < 1) {
      Left("maxIterations must be at least 1.")
    } else {
      val percentChange = for {
        nApprox  <- approxConvergenceOrder(nStart)
        n2Approx <- approxConvergenceOrder(nStart * 2)
      } yield {
        (nApprox - n2Approx) / nApprox
      }

      percentChange.fold(
        l => Left(l),
        r => {
          if (r < cutoff) {
            approxConvergenceOrder(nStart * 2)
          } else if (maxIterations == 1) {
            Left("Could not converge to order estimate in provided number of iterations.")
          } else {
            findConvergenceOrder(nStart * 2, maxIterations = maxIterations - 1)
          }
        }
      )
    }
  }
}
