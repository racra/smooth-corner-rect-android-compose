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

/**
 * A class representing the points required to draw the curves in a smooth corner.
 * A smooth corner is made up of an arc surrounded by 2 diagonally symmetrical bezier curves.
 * Documentation on the makeup of a smooth curve:
 *
 * @param cornerRadius the size of the corners
 * @param smoothnessAsPercent a percentage representing how smooth the corners will be
 * @param maximumCurveStartDistanceFromVertex the maximum height/width this curve can have
 */
private class SmoothCorner(
    private val cornerRadius: Float,
    private val smoothnessAsPercent: Int,
    private val maximumCurveStartDistanceFromVertex: Float
) {

    init {
        require(smoothnessAsPercent >= 0) {
            "The value for smoothness can never be negative."
        }
    }

    private val radius = min(cornerRadius, maximumCurveStartDistanceFromVertex)

    private val smoothness = smoothnessAsPercent / 100f

    // Distance from the first point of the curvature to the vertex of the corner
    private val curveStartDistance =
        min(maximumCurveStartDistanceFromVertex, (1 + smoothness) * radius)

    private val shouldCurveInterpolate = radius <= maximumCurveStartDistanceFromVertex / 2

    // This value is used to start interpolating between smooth corners and
    // round corners
    private val interpolationMultiplier =
        (radius - maximumCurveStartDistanceFromVertex / 2) / (maximumCurveStartDistanceFromVertex / 2)

    // Angle at second control point of the bezier curves
    private val angleAlpha =
        if (shouldCurveInterpolate)
            toRadians(45.0 * smoothness).toFloat()
        else
            toRadians(45.0 * smoothness * (1 - interpolationMultiplier)).toFloat()

    // Angle which dictates how much of the curve is going to be a slice of a circle
    private val angleBeta =
        if (shouldCurveInterpolate)
            toRadians(90.0 * (1.0 - smoothness)).toFloat()
        else
            toRadians(90.0 * (1 - smoothness * (1 - interpolationMultiplier))).toFloat()

    private val angleTheta = ((toRadians(90.0) - angleBeta) / 2.0).toFloat()

    // Distance from second control point to end of Bezier curves
    private val distanceE = radius * tan(angleTheta / 2)

    // Distances in the x and y axis used to position end of Bezier
    // curves relative to it's second control point
    private val distanceC = distanceE * cos(angleAlpha)
    private val distanceD = distanceC * tan(angleAlpha)

    // Distances used to position second control point of Bezier curves
    // relative to their first control point
    private val distanceK = sin(angleBeta / 2) * radius
    private val distanceL = (distanceK * sqrt(2.0)).toFloat()
    private val distanceB =
        ((curveStartDistance - distanceL) - (1 + tan(angleAlpha)) * distanceC) / 3

    // Distance used to position first control point of Bezier curves
    // relative to their origin
    private val distanceA = 2 * distanceB

    // Represents the outer anchor points of the smooth curve
    val anchorPoint1 = PointRelativeToVertex(
        min(curveStartDistance, maximumCurveStartDistanceFromVertex),
        0f
    )

    // Represents the control point for point1
    val controlPoint1 = PointRelativeToVertex(
        anchorPoint1.distanceToFurthestSide - distanceA,
        0f
    )

    // Represents the control point for point2
    val controlPoint2 = PointRelativeToVertex(
        controlPoint1.distanceToFurthestSide - distanceB,
        0f
    )

    // Represents the inner anchor points of the smooth curve
    val anchorPoint2 = PointRelativeToVertex(
        controlPoint2.distanceToFurthestSide - distanceC,
        distanceD
    )

    val arcSection = Arc(
        radius = radius,
        arcStartAngle = angleTheta,
        arcSweepAngle = angleBeta
    )
}

/**
 * Represents a point positioned relative to a corner vertex, so that it can be used
 * to calculate a smooth curve independently of which quadrant of the rectangle this
 * curve will be inserted in.
 */
private data class PointRelativeToVertex(
    val distanceToFurthestSide: Float,
    val distanceToClosestSide: Float
)

/**
 * Represents the arc section of a smooth corner curve
 *
 * @param arcStartAngle the start angle of the arc inside the first quadrant of rotation (0º to 90º)
 * @param arcSweepAngle the angle at the center point between the start and end of the arc
 */
private data class Arc(
    val radius: Float,
    val arcStartAngle: Float,
    val arcSweepAngle: Float
)