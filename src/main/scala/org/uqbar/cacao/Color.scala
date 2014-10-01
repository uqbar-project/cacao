package org.uqbar.cacao

import java.lang.Math._
import scala.language.implicitConversions
import scala.util.{ Random => ScalaRandom }

object Color {
	val White = Color(255, 255, 255)
	val Gray = Color(128, 128, 128)
	val Black = Color(0, 0, 0)
	val Red = Color(255, 0, 0)
	val Pink = Color(255, 175, 175)
	val Orange = Color(255, 200, 0)
	val Yellow = Color(255, 255, 0)
	val Green = Color(0, 255, 0)
	val Magenta = Color(255, 0, 255)
	val Cyan = Color(0, 255, 255)
	val Blue = Color(0, 0, 255)
	def Random = Color(ScalaRandom.nextInt(256), ScalaRandom.nextInt(256), ScalaRandom.nextInt(256))
}

case class Color(red: Int, green: Int, blue: Int, alpha: Int = 255) {
	implicit def Double_to_Int(d: Double) = d.toInt

	def brighter(factor: Double = 0.7) = {
		val i: Int = 1.0 / (1.0 - factor)

		def adjust(channel: Int) = min((if (channel > 0 && channel < i) i else channel) / factor, 255)

		if (this == Color.Black) Color(i, i, i, alpha)
		else Color(adjust(red), adjust(green), adjust(blue), alpha)
	}

	def darker(factor: Double = 0.7) = Color(max(red * factor, 0), max(green * factor, 0), max(blue * factor, 0), alpha)
}