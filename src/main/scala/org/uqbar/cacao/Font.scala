package org.uqbar.cacao

import java.lang.Math._
import org.uqbar.math.vectors._

trait Font {
	def sizeOf(target: Char): Vector
	def sizeOf(target: String): Vector = target.foldLeft(Origin){ (acum, char) =>
		val size = sizeOf(char)
		(acum.x + size.x, max(acum.y, size.y))
	}
}

trait FontModifier
object FontModifier {
	object Bold extends FontModifier
	object Italic extends FontModifier
}