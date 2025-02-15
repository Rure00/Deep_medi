package com.rure.deepmedi.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rure.deepmedi.utils.designDp
import com.rure.deepmedi.presentation.model.Degree
import com.rure.deepmedi.ui.theme.Typography

@Composable
fun DegreeChip(
    degree: Degree
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(35.designDp()))
            .background(color = degree.color)
            .padding(horizontal = 22.designDp(), vertical = 8.designDp()),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = degree.tag,
            style = Typography.labelSmall,
            color = degree.textColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
        )
    }
}