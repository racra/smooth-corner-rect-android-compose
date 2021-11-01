package racra.compose.smooth_corner_rect_library

import org.junit.Assert
import org.junit.Test

class SmoothCornerTest {
    @Test
    fun shouldThrowExceptionWithNegativeSmoothness() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            SmoothCorner(50f, -50, 50f)
        }
    }
}