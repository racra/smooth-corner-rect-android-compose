package racra.compose.smooth_corner_rect_library

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class AbsoluteSmoothCornerShapeTest {
    @Test
    fun shouldProduceSamePathAsRoundedCornerShapeWhenSmoothnessIsZero() {
        for (radius in 0..100 step 10) {
            assertEquals(
                AbsoluteSmoothCornerShape(Dp(radius.toFloat()), 0)
                    .createOutline(
                        Size(100f, 100f),
                        LayoutDirection.Ltr,
                        Density(1f)
                    ),
                RoundedCornerShape(Dp(radius.toFloat()))
                    .createOutline(
                        Size(100f, 100f),
                        LayoutDirection.Ltr,
                        Density(1f)
                    )
            )
        }
    }
}
