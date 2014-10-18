package org.uqbar.cacao.dimensions.immutable

import org.uqbar.math.vectors._

trait Rotable[T <: Rotable[T]] { self: T =>

	def center: Vector

	def rotated(radians: Double, center: Vector = this.center): T

	def rotatedToPoint(point: Vector, center: Vector = this.center)(target: Vector): T = rotated(center - point angleTo (center - target))
}