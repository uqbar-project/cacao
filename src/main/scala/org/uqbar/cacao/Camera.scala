package org.uqbar.cacao

import org.uqbar.math.spaces.R2._

object Camera {
	def apply(translation: Vector = (0, 0), screenPosition: Vector = (0, 0), screenSize: Vector = (Int.MaxValue, Int.MaxValue), zoom: Vector = (1, 1)) = {
		val instance = new Camera
    //TODO: Mutable
		instance.translation = translation
		instance.screenPosition = screenPosition
		instance.screenSize = screenSize
		instance.zoom = zoom
		instance
	}
}

class Camera(var translation: Vector = (0, 0), var screenPosition: Vector = (0, 0), var screenSize: Vector = (Int.MaxValue, Int.MaxValue), var zoom: Vector = (1, 1))