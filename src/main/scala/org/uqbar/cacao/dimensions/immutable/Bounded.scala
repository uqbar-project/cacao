package org.uqbar.cacao.dimensions.immutable

import org.uqbar.math.vectors._
import org.uqbar.math.vectors.Vector

object Bounded {
	def apply(position: Vector)(aSize: Vector) = new Bounded {
		def top = position.y
		def left = position.x
		def size = aSize
	}
}
trait Bounded {
	def top: Double
	def left: Double
	def size: Vector

	lazy val bottom = top + size.y
	lazy val right = left + size.x
	lazy val half = left + size.x / 2
	lazy val middle = top + size.y / 2

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