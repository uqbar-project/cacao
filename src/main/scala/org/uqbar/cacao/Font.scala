package org.uqbar.cacao

import java.lang.Math._
import org.uqbar.math.spaces.R2._
import org.uqbar.math.spaces.Axis 

trait Font {
	def sizeOf(target: Char): Vector
	def sizeOf(target: String): Vector = target.foldLeft(Origin){ (acum, char) =>
		val size = sizeOf(char)
		(acum(X) + size(X), max(acum(Y), size(Y)))
	}
}

trait FontModifier
object FontModifier {
	object Bold extends FontModifier
	object Italic extends FontModifier
}