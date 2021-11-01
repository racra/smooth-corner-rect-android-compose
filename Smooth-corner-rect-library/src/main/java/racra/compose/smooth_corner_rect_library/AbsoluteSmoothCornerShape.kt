package racra.compose.smooth_corner_rect_library

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.lang.Math.toRadians
import kotlin.math.*

/**
 * A shape describing a rectangle with smooth rounded corners sometimes called a
 * Squircle or Superellipse.
 *
 * This shape will not automatically mirror the corners in [LayoutDirection.Rtl], use
 * TODO [SmoothCornerShape] for the layout direction aware version of this shape.
 *
 * TODO - Create support for different radius and smoothness for each corner
 *
 * @param cornerRadius the size of the corners
 * @param smoothnessAsPercent a percentage representing how smooth the corners will be
 */
data class AbsoluteSmoothCornerShape(
    private val cornerRadius: Dp = 0.dp,
    private val smoothnessAsPercent: Int = 60
) : CornerBasedShape(
    topStart = CornerSize(cornerRadius),
    topEnd = CornerSize(cornerRadius),
    bottomEnd = CornerSize(cornerRadius),
    bottomStart = CornerSize(cornerRadius)
) {
    private val smoothness = smoothnessAsPercent / 100f

    init {
        require(smoothnessAsPercent >= 0) {
            "The value for smoothness can never be negative."
        }
    }

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ) = when {
        topStart + topEnd + bottomEnd + bottomStart == 0.0f -> {
            Outline.Rectangle(size.toRect())
        }
        smoothness == 0f -> {
            Outline.Rounded(
                RoundRect(
                    rect = size.toRect(),
                    topLeft = CornerRadius(topStart),
                    topRight = CornerRadius(topEnd),
                    bottomRight = CornerRadius(bottomEnd),
                    bottomLeft = CornerRadius(bottomStart)
                )
            )
        }
        else -> {
            Outline.Generic(
                Path().apply {
                    val shortestSide = min(size.height, size.width)
                    val smoothCorner = SmoothCorner(
                        topStart,
                        smoothnessAsPercent,
                        shortestSide / 2
                    )

                    // Top Left Corner
                    moveTo(
                        smoothCorner.anchorPoint1.distanceToClosestSide,
                        smoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    cubicTo(
                        smoothCorner.controlPoint1.distanceToClosestSide,
                        smoothCorner.controlPoint1.distanceToFurthestSide,
                        smoothCorner.controlPoint2.distanceToClosestSide,
                        smoothCorner.controlPoint2.distanceToFurthestSide,
                        smoothCorner.anchorPoint2.distanceToClosestSide,
                        smoothCorner.anchorPoint2.distanceToFurthestSide
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = 0f,
                            right = smoothCorner.arcSection.radius * 2,
                            bottom = smoothCorner.arcSection.radius * 2
                        ),
                        startAngleRadians =
                            (toRadians(180.0) + smoothCorner.arcSection.arcStartAngle)
                                .toFloat(),
                        sweepAngleRadians = smoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        smoothCorner.controlPoint2.distanceToFurthestSide,
                        smoothCorner.controlPoint2.distanceToClosestSide,
                        smoothCorner.controlPoint1.distanceToFurthestSide,
                        smoothCorner.controlPoint1.distanceToClosestSide,
                        smoothCorner.anchorPoint1.distanceToFurthestSide,
                        smoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    lineTo(
                        size.width - smoothCorner.anchorPoint1.distanceToFurthestSide,
                        smoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    // Top Right Corner
                    cubicTo(
                        size.width - smoothCorner.controlPoint1.distanceToFurthestSide,
                        smoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - smoothCorner.controlPoint2.distanceToFurthestSide,
                        smoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - smoothCorner.anchorPoint2.distanceToFurthestSide,
                        smoothCorner.anchorPoint2.distanceToClosestSide,
                   )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = size.width - smoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = smoothCorner.arcSection.radius * 2
                        ),
                        startAngleRadians =
                            (toRadians(270.0) + smoothCorner.arcSection.arcStartAngle)
                                .toFloat(),
                        sweepAngleRadians = smoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width - smoothCorner.controlPoint2.distanceToClosestSide,
                        smoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - smoothCorner.controlPoint1.distanceToClosestSide,
                        smoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - smoothCorner.anchorPoint1.distanceToClosestSide,
                        smoothCorner.anchorPoint1.distanceToFurthestSide,
                    )


                    lineTo(
                        size.width - smoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - smoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    // Bottom Right Corner
                    cubicTo(
                        size.width - smoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - smoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - smoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - smoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - smoothCorner.anchorPoint2.distanceToClosestSide,
                        size.height - smoothCorner.anchorPoint2.distanceToFurthestSide
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - smoothCorner.arcSection.radius * 2,
                            left = size.width - smoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = size.height
                        ),
                        startAngleRadians =
                            (toRadians(0.0) + smoothCorner.arcSection.arcStartAngle)
                                .toFloat(),
                        sweepAngleRadians = smoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width - smoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - smoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - smoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - smoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - smoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - smoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    lineTo(
                        smoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - smoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    // Bottom Left Corner
                    cubicTo(
                        smoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - smoothCorner.controlPoint1.distanceToClosestSide,
                        smoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - smoothCorner.controlPoint2.distanceToClosestSide,
                        smoothCorner.anchorPoint2.distanceToFurthestSide,
                        size.height - smoothCorner.anchorPoint2.distanceToClosestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - smoothCorner.arcSection.radius * 2,
                            left = 0f,
                            right = smoothCorner.arcSection.radius * 2,
                            bottom = size.height
                        ),
                        startAngleRadians =
                            (toRadians(90.0) + smoothCorner.arcSection.arcStartAngle)
                                .toFloat(),
                        sweepAngleRadians = smoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        smoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - smoothCorner.controlPoint2.distanceToFurthestSide,
                        smoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - smoothCorner.controlPoint1.distanceToFurthestSide,
                        smoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - smoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    close()

                }
            )
        }
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ) = AbsoluteSmoothCornerShape(cornerRadius, smoothnessAsPercent)
}