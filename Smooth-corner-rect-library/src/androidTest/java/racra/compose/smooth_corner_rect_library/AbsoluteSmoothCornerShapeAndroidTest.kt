package racra.compose.smooth_corner_rect_library

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import org.junit.Assert
import org.junit.Test

class AbsoluteSmoothCornerShapeAndroidTest {

    @Test
    fun shouldProduceAccurateSmoothCornerShapePaths() {
        data class InputToExpectedHash(
            val radius: Dp,
            val smoothnessAsPercent: Int,
            val expectedHashCode: Int
        )

        // The expected hashcode values where generated when the shape was first implemented,
        // they verify that future changes to the implementation do not deviate from the initial
        // path outcome. If future implementations call different path methods to implement the
        // shape then this test should be updated to use screenshot comparison.
        val inputToExpectedHashList = listOf(
            InputToExpectedHash(Dp(10f), 10, 61009965),
            InputToExpectedHash(Dp(10f), 50, 22817122),
            InputToExpectedHash(Dp(10f), 100, 212183795),
            InputToExpectedHash(Dp(50f), 10, 127009712),
            InputToExpectedHash(Dp(50f), 50, 7037481),
            InputToExpectedHash(Dp(50f), 100, 173504942),
        )

        for (item in inputToExpectedHashList) {
            Assert.assertEquals(
                AbsoluteSmoothCornerShape(item.radius, item.smoothnessAsPercent)
                    .createOutline(
                        Size(100f, 100f),
                        LayoutDirection.Ltr,
                        Density(1f)
                    ).hashCode(),
                item.expectedHashCode
            )
        }
    }

}