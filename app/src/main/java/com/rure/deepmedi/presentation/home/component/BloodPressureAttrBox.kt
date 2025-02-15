package com.rure.deepmedi.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.rure.deepmedi.utils.designDp
import com.rure.deepmedi.R
import com.rure.deepmedi.presentation.model.BloodPressureAttr
import com.rure.deepmedi.ui.theme.BackgroundBlurColor
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.TextGray
import com.rure.deepmedi.ui.theme.Typography
import com.rure.deepmedi.ui.theme.White
import com.rure.deepmedi.utils.designSp
import com.rure.deepmedi.utils.dropShadow

@Composable
fun BloodPressureAttrBox(
    bloodPressureAttr: BloodPressureAttr
) {
    val itemBackgroundShape = remember { RoundedCornerShape(30.designDp()) }
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
                width = 1.designDp(),
                brush = borderGradient,
                shape = itemBackgroundShape
            )
            .dropShadow(
                shape = itemBackgroundShape,
                blur = 4.designDp(),
                color = BackgroundBlurColor,
                alpha = 0.07f
            )
            .padding(vertical = 13.designDp(), horizontal = 22.designDp()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.blood_pressure_text),
                style = Typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = TextGray,
            )
            Spacer(modifier = Modifier.width(15.designDp()))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.edit_icon),
                contentDescription = null,
                modifier = Modifier.size(36.designDp())
            )

            Spacer(modifier = Modifier.weight(1f))

            DegreeChip(bloodPressureAttr.hpDegree)
        }

        Spacer(modifier = Modifier.height(4.designDp()))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = bloodPressureAttr.sys.toString(),
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                )
                Text(
                    text = stringResource(R.string.sys_text),
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 60.designSp(),
                    color = Black,
                )
                Spacer(modifier = Modifier.width(30.designDp()))

                Text(
                    text = bloodPressureAttr.dia.toString(),
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                )
                Text(
                    text = stringResource(R.string.dia_text),
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 60.designSp(),
                    color = Black,
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