package org.uqbar.cacao

import org.uqbar.math.vectors.Vector

trait Shape

case class Line(from: Vector, to: Vector) extends Shape
case class Rect(m: Double, b: Double) extends Shape
case class Rectangle(from: Vector, size: Vector) extends Shape
case class Circle(at: Vector, radius: Double) extends Shape
case class RegularPoligon(sides: Int) extends Shape
case class Poligon(corners: Vector*) extends Shape
case class BesierCurve() extends Shape
case class ComposedShape(shapes: Shape*) extends Shape