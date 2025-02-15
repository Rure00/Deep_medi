package com.rure.deepmedi.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.rure.deepmedi.ui.theme.BackgroundBlurColor
import com.rure.deepmedi.ui.theme.White
import com.rure.deepmedi.utils.dropShadow
import com.rure.deepmedi.utils.toDesignDp

@Composable
fun AttributeSkeletonBox() {
    val itemBackgroundShape = remember { RoundedCornerShape(30.toDesignDp()) }
    val backgroundGradient = remember {  Brush.verticalGradient(listOf(White, White.copy(alpha = 0f))) }
    val borderGradient = remember { Brush.verticalGradient(listOf(White, White.copy(alpha = 0f), White.copy(alpha = 0.77f))) }

    Box(
        modifier = Modifier
            .fillMaxWidth().height(100.dp)
            .clip(itemBackgroundShape)
            .background(
                brush = backgroundGradient,
                shape =  itemBackgroundShape
            )
            .border(
                width = 1.dp,
                brush = borderGradient,
                shape = itemBackgroundShape
            )
            .dropShadow(
                shape = itemBackgroundShape,
                blur = 4.dp,
                color = BackgroundBlurColor,
                alpha = 0.07f
            )
            .padding(vertical = 13.dp, horizontal = 22.dp),
        contentAlignment = Alignment.Center
    ) {

    }
}