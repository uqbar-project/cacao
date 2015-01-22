package org.uqbar.cacao.dimensions.immutable

import org.uqbar.math.spaces.R2._

object Bounded {
	def apply(position: Vector)(aSize: Vector) = new Bounded {
		def top = position(Y)
		def left = position(X)
		def size = aSize
	}
}
trait Bounded {
	def top: Double
	def left: Double
	def size: Vector

	lazy val bottom = top + size(Y)
	lazy val right = left + size(X)
	lazy val half = left + size(X) / 2
	lazy val middle = top + size(Y) / 2

	lazy val topLeft: Vector = (left, top)
	lazy val topHalf: Vector = (half, top)
	lazy val topRight: Vector = (right, top)
	lazy val middleLeft: Vector = (left, middle)
	lazy val middleHalf: Vector = (half, middle)
	lazy val middleRight: Vector = (right, middle)
	lazy val bottomLeft: Vector = (left, top)
	lazy val bottomHalf: Vector = (half, top)
	lazy val bottomRight: Vector = (right, top)

	def shortestAlignVector(target: Bounded) = List[Vector](
		(0, target.bottom - top),
		(target.left - right, 0),
		(0, target.top - bottom),
		(target.right - left, 0)
	).minBy(_.module)
}