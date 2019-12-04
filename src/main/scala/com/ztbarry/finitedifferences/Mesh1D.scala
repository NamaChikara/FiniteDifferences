package com.ztbarry.finitedifferences

/** Representation of a regular partition of a single interval.
 *
 * The following must be specified by classes that mix in Mesh1D:
 * @param a  the beginning of the interval.
 * @param b  the end of the interval
 *
 * @note The following classes mix in Mesh1D:
 *       Difference1D
 */

trait Mesh1D {

  val a: Double
  val b: Double

  require(a < b, f"Error: $a is not less than $b")

  /**
   * Creates a regular partition of an interval [a, b].
   *
   * @param  n  the number of partitions.
   * @return an ordered vector containing the points of the partition, including endpoints.
   */
  def mesh(n: Int): Vector[Double] = {

    for (i <- Vector.range(0, n + 1)) yield {
      a + i * (b - a) / n
    }

  }

}