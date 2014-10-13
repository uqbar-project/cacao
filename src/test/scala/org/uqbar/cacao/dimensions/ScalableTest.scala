package org.uqbar.cacao.dimensions

import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfter
import org.uqbar.math.vectors._
import java.security.InvalidParameterException

class ScalableTest extends FreeSpec with Matchers with BeforeAndAfter {

	var s: S = _

	before {
		s = S(12, 10)
	}

	"A Scalable" - {
		"scaled by" - {
			"a ratio in (1, ∞] should grow proportionally" in { s.scaledBy(2).size should be (s.size * 2) }
			"a ratio = 1 should stay the same" in { s.scaledBy(1).size should be (s.size) }
			"a ratio in (0, 1) should shrink proportionally" in { s.scaledBy(0.5).size should be (s.size / 2) }
			"a ratio = 0 should raise an exception" in { an[InvalidParameterException] should be thrownBy s.scaledBy(0) }
			"a ratio in (-1, 0) should shrink proportionally and change it's size's sign" in { s.scaledBy(-0.5).size should be (-s.size / 2) }
			"a ratio = -1 should change it's size's sign" in { s.scaledBy(-1).size should be (-s.size) }
			"a ratio in [-∞,-1) should grow proportionally and change it's size's sign" in { s.scaledBy(-1).size should be (-s.size) }
		}

		"scaled" in {
			val ratios = List(-2, -1, -0.5, -Double.MinPositiveValue, Double.MinPositiveValue, 0.5, 1, 2)

			for (n <- ratios) {
				s.scaledBy(n, n).size should be (s.scaledBy(n).size)
				s.scaledBy(x = n).size should be (s.scaledBy(n, 1).size)
				s.scaledBy(y = n).size should be (s.scaledBy(1, n).size)
				s.scaledBy(x = n).scaledBy(y = n).size should be (s.scaledBy(n, n).size)
				s.scaledBy(x = n).scaledBy(y = n).size should be (s.scaledBy(n).size)
			}

			an[InvalidParameterException] should be thrownBy s.scaledTo(1, 0)
			an[InvalidParameterException] should be thrownBy s.scaledTo(0, 1)
			an[InvalidParameterException] should be thrownBy s.scaledTo(0, 0)
			an[InvalidParameterException] should be thrownBy s.scaledTo(Origin)
			an[InvalidParameterException] should be thrownBy s.scaledHorizontallyTo(0)
			an[InvalidParameterException] should be thrownBy s.scaledVerticallyTo(0)
			an[InvalidParameterException] should be thrownBy s.scaledToFit(1, 0)
			an[InvalidParameterException] should be thrownBy s.scaledToFit(0, 1)
			an[InvalidParameterException] should be thrownBy s.scaledToFit(0, 0)
			an[InvalidParameterException] should be thrownBy s.scaledToFit(Origin)
			an[InvalidParameterException] should be thrownBy s.scaledToCover(1, 0)
			//			an[InvalidParameterException] should be thrownBy s.scaledToCover(0, 1)
			//			an[InvalidParameterException] should be thrownBy s.scaledToCover(0, 0)
			//			an[InvalidParameterException] should be thrownBy s.scaledToCover(Origin)

			s.scaledTo(24, 5).size should be((24, 5): Vector)
			s.scaledTo(24, 20).size should be((24, 20): Vector)
			s.scaledTo(width = 24).size should be((24, 20): Vector)
			s.scaledTo(height = 20).size should be((24, 20): Vector)

			s.scaledHorizontallyTo(24).size should be((24, 10): Vector)
			s.scaledVerticallyTo(20).size should be((12, 20): Vector)

			s.scaledToFit(24, 100).size should be((24, 20): Vector)
			s.scaledToFit(120, 20).size should be((24, 20): Vector)
			s.scaledToFit(6, 10).size should be((6, 5): Vector)
			s.scaledToFit(12, 5).size should be((6, 5): Vector)

			s.scaledToCover(24, 100).size should be((120, 100): Vector)
			s.scaledToCover(120, 20).size should be((120, 100): Vector)
			s.scaledToCover(6, 10).size should be((12, 10): Vector)
			s.scaledToCover(12, 5).size should be((12, 10): Vector)
			s.scaledToCover(6, 5).size should be((6, 5): Vector)
		}
	}

}

case class S(var x: Double, var y: Double) extends Scalable[S] {
	def size = (x, y)

	def scaledBy(x: Double, y: Double) = if (x == 0 || y == 0) throw new InvalidParameterException else S(x * this.x, y * this.y)

	def scaleBy(x: Double, y: Double) {
		if (x == 0 || y == 0) throw new InvalidParameterException else {
			this.x *= x
			this.y *= y
		}
	}
}