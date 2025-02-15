package com.rure.deepmedi.presentation.model

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.ChipAttention
import com.rure.deepmedi.ui.theme.ChipDanger
import com.rure.deepmedi.ui.theme.ChipHealthy
import com.rure.deepmedi.ui.theme.ChipNormal
import com.rure.deepmedi.ui.theme.ChipWarning
import com.rure.deepmedi.ui.theme.White

enum class Degree(
    val tag: String,
    val color: Color,
    val textColor: Color
) {
    Danger("위험", ChipDanger, White),
    Warning("경고", ChipWarning, Black),
    Attention("주의", ChipAttention, Black),
    Normal("정상", ChipNormal, Black),
    Healthy("건강", ChipHealthy, White)
}