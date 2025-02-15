package com.rure.deepmedi.presentation.model

import com.rure.deepmedi.data.entity.AttributeTag

class BloodPressureAttr(
    valueStr: String,
    override val tag: AttributeTag,
    override val lastUpdateTs: Long
): Attribute<Pair<Int, Int>>() {
    override val value = valueStr.split(",")[0].toInt() to valueStr.split(",")[1].toInt()

    val sys = value.first
    val dia = value.second

    val hpDegree = when {
        (sys in 0..30 && dia in 60..70) -> Degree.Danger
        (sys in 31..60 && dia in 71..80) -> Degree.Warning
        (sys in 61..80 && dia in 81..90) -> Degree.Attention
        (sys in 81..90 && dia in 91..100) -> Degree.Normal
        (sys in 91..110 && dia in 101..110) -> Degree.Healthy
        else -> Degree.Danger
    }
}