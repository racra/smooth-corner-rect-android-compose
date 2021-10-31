package racra.compose.smoothcornerrectdemoapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape

val Shapes = Shapes(
    small = AbsoluteSmoothCornerShape(20.dp, 100),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)