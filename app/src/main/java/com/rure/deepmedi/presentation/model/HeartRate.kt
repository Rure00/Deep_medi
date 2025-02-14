package com.rure.deepmedi.presentation.model

class HeartRate(
    valueStr: String,
    override val lastUpdateTs: Long
): Attribute<Int>() {
    override val value: Int = valueStr.toInt()
    val hrDegree = when(value) {
        in 0..40 -> Degree.Danger
        in 41..60 -> Degree.Warning
        in 61..80 -> Degree.Attention
        in 81..100 -> Degree.Normal
        in 101..120 -> Degree.Healthy
        else -> throw Exception("A Wrong Argument.")
    }
}

