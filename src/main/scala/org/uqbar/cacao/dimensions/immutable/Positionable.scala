package org.uqbar.cacao.dimensions.immutable

import org.uqbar.math.spaces.R2._
import scala.Double.NaN

trait Positionable[T <: Positionable[T]] { self: T =>

	def position: Vector

	def moved(x: Double, y: Double): T
	def moved(delta: Vector): T = moved(delta(X), delta(Y))

	def movedTo(position: Vector): T = moved(position - this.position)

	def movedToAlign(x: T ⇒ Double, y: T ⇒ Double)(target: Vector): T = movedToAlign(x(this), y(this))(target)
	def movedToAlign(what: Vector)(target: Vector): T = movedToAlign(what(X), what(Y))(target)
	def movedToAlign(x: Double = NaN, y: Double = NaN)(target: Vector): T = {
		require(!x.isNaN || !y.isNaN, "At least one argument must be supplied.")

		moved(if (x.isNaN) 0 else target(X) - x, if (y.isNaN) 0 else target(Y) - y)
	}
}