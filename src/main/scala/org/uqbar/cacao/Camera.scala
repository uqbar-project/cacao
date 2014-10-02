package org.uqbar.cacao

import org.uqbar.math.vectors._

object Camera {
	def apply(translation: Vector = (0, 0), screenPosition: Vector = (0, 0), screenSize: Vector = (Int.MaxValue, Int.MaxValue), zoom: Vector = (1, 1)) = {
		val instance = new Camera
		instance.translation.set(translation)
		instance.screenPosition.set(screenPosition)
		instance.screenSize.set(screenSize)
		instance.zoom.set(zoom)
		instance
	}
}

class Camera(var translation: MutableVector = (0, 0), var screenPosition: MutableVector = (0, 0), var screenSize: MutableVector = (Int.MaxValue, Int.MaxValue), var zoom: MutableVector = (1, 1))