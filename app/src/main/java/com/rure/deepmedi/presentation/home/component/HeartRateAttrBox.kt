package com.rure.deepmedi.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rure.deepmedi.R
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.presentation.model.Attribute
import com.rure.deepmedi.presentation.model.Degree
import com.rure.deepmedi.presentation.model.HeartRateAttr
import com.rure.deepmedi.ui.theme.BackgroundBlurColor
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.TextGray
import com.rure.deepmedi.ui.theme.Typography
import com.rure.deepmedi.ui.theme.White
import com.rure.deepmedi.utils.dropShadow

@Composable
fun HeartRateAttrBox(
    heartRateAttr: HeartRateAttr
) {
    val itemBackgroundShape = remember { RoundedCornerShape(30.dp) }
    val backgroundGradient = remember {  Brush.verticalGradient(listOf(White, White.copy(alpha = 0f))) }
    val borderGradient = remember { Brush.verticalGradient(listOf(White, White.copy(alpha = 0f), White.copy(alpha = 0.77f))) }

    Column(
        modifier = Modifier
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.heart_rate_text),
                style = Typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = TextGray,
            )

            DegreeChip(heartRateAttr.hrDegree)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(R.string.heart_rate_value_text, heartRateAttr.value),
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                )
                Text(
                    text = stringResource(R.string.per_minute_text),
                    style = Typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    lineHeight = 60.sp
                )
            }



            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.hr_normal_text),
                style = Typography.labelLarge,
                fontWeight = FontWeight(500),
                color = TextGray,
            )
        }
    }
}