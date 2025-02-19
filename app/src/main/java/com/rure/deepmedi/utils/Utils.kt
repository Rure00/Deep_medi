package com.rure.deepmedi.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.util.Log
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.Period

private const val DESIGN_DPI = 320f
private const val DESIGN_WIDTH = 720
private const val DESIGN_HEIGHT = 1280

fun Int.toDesignDp(): Dp = (this * (160f / DESIGN_DPI)).dp
fun Int.toDesignSp(): TextUnit = (this * (160f / DESIGN_DPI)).sp

fun Int.matchRatioToWidth(context: Context): Dp {
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    return ((this * screenWidth) / DESIGN_WIDTH).dp
}
fun Int.matchRatioToHeight(context: Context): Dp {
    val displayMetrics = context.resources.displayMetrics
    val screenHeight = displayMetrics.heightPixels / displayMetrics.density

    return ((this * screenHeight) / DESIGN_HEIGHT).dp
}


fun LocalDate.calculateAge(): Int {
    val currentDate = LocalDate.now()
    return Period.between(this, currentDate).years
}

// https://citytexi.tistory.com/87
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
    alpha: Float = 1f
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
        this.alpha = alpha
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}