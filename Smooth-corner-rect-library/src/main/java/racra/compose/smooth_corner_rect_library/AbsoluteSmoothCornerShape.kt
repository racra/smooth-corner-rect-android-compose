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
class AbsoluteSmoothCornerShape(
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

                    val radius = min(topStart, shortestSide / 2)

                    // Distance from the first point of the curvature to the vertex of the corner
                    val curveStartDistance = min(shortestSide / 2, (1 + smoothness) * radius)

                    // Angle at second control point of the bezier curves
                    val angleAlpha: Float
                    // Angle which dictates how much of the curve is going to be a slice of a circle
                    val angleBeta: Float

                    if (radius <= shortestSide / 4) {
                        angleAlpha = toRadians(45.0 * smoothness).toFloat()
                        angleBeta = toRadians(90.0 * (1.0 - smoothness)).toFloat()
                    } else {
                        // This value is used to start interpolating between smooth corners and
                        // round corners
                        val interpolationMultiplier =
                            (radius - shortestSide / 4) / (shortestSide / 4)

                        angleAlpha = toRadians(
                            45.0 * smoothness * (1 - interpolationMultiplier)
                        ).toFloat()
                        angleBeta = toRadians(
                            90.0 * (1 - smoothness * (1 - interpolationMultiplier))
                        ).toFloat()
                    }

                    val angleTheta = ((toRadians(90.0) - angleBeta) / 2.0).toFloat()

                    // Distance from second control point to end of Bezier curves
                    val distanceE = radius * tan(angleTheta / 2)

                    // Distances in the x and y axis used to position end of Bezier
                    // curves relative to it's second control point
                    val distanceC = distanceE * cos(angleAlpha)
                    val distanceD = distanceC * tan(angleAlpha)

                    // Distances used to position second control point of Bezier curves
                    // relative to their first control point
                    val distanceK = sin(angleBeta / 2) * radius
                    val distanceL = (distanceK * sqrt(2.0)).toFloat()
                    val distanceB =
                        ((curveStartDistance - distanceL) - (1 + tan(angleAlpha)) * distanceC) / 3

                    // Distance used to position first control point of Bezier curves
                    // relative to their origin
                    val distanceA = 2 * distanceB

                    // Top Left Corner
                    moveTo(0f, min(curveStartDistance, shortestSide / 2))

                    cubicTo(
                        0f, curveStartDistance - distanceA,
                        0f, curveStartDistance - distanceA - distanceB,
                        distanceD, curveStartDistance - distanceA - distanceB - distanceC
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = 0f,
                            right = radius * 2,
                            bottom = radius * 2
                        ),
                        startAngleRadians = (toRadians(180.0) + angleTheta).toFloat(),
                        sweepAngleRadians = angleBeta,
                        forceMoveTo = false
                    )

                    cubicTo(
                        curveStartDistance - distanceA - distanceB, 0f,
                        curveStartDistance - distanceA, 0f,
                        min(curveStartDistance, shortestSide / 2), 0f
                    )

                    lineTo(max(size.width - curveStartDistance, shortestSide / 2), 0f)

                    // Top Right Corner
                    cubicTo(
                        size.width - curveStartDistance + distanceA, 0f,
                        size.width - curveStartDistance + distanceA + distanceB, 0f,
                        size.width - curveStartDistance + distanceA + distanceB + distanceC,
                        distanceD
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = size.width - radius * 2,
                            right = size.width,
                            bottom = radius * 2
                        ),
                        startAngleRadians = (toRadians(270.0) + angleTheta).toFloat(),
                        sweepAngleRadians = angleBeta,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width, curveStartDistance - distanceA - distanceB,
                        size.width, curveStartDistance - distanceA,
                        size.width, min(curveStartDistance, shortestSide / 2)
                    )

                    lineTo(
                        size.width, max(size.height - curveStartDistance, shortestSide / 2)
                    )

                    // Bottom Right Corner
                    cubicTo(
                        size.width,
                        size.height - curveStartDistance + distanceA,
                        size.width,
                        size.height - curveStartDistance + distanceA + distanceB,
                        size.width - distanceD,
                        size.height - curveStartDistance + distanceA + distanceB + distanceC,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - radius * 2,
                            left = size.width - radius * 2,
                            right = size.width,
                            bottom = size.height
                        ),
                        startAngleRadians = (toRadians(0.0) + angleTheta).toFloat(),
                        sweepAngleRadians = angleBeta,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width - curveStartDistance + distanceA + distanceB,
                        size.height,
                        size.width - curveStartDistance + distanceA,
                        size.height,
                        max(size.width - curveStartDistance, shortestSide / 2),
                        size.height,
                    )

                    lineTo(max(curveStartDistance, shortestSide / 2), size.height)

                    // Bottom Left Corner
                    cubicTo(
                        curveStartDistance - distanceA,
                        size.height,
                        curveStartDistance - distanceA - distanceB,
                        size.height,
                        curveStartDistance - distanceA - distanceB - distanceC,
                        size.height - distanceD,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - radius * 2,
                            left = 0f,
                            right = radius * 2,
                            bottom = size.height
                        ),
                        startAngleRadians = (toRadians(90.0) + angleTheta).toFloat(),
                        sweepAngleRadians = angleBeta,
                        forceMoveTo = false
                    )

                    cubicTo(
                        0f,
                        size.height - curveStartDistance + distanceA + distanceB,
                        0f,
                        size.height - curveStartDistance + distanceA,
                        0f,
                        max(size.height - curveStartDistance, shortestSide / 2),
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