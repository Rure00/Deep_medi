package com.rure.deepmedi.presentation.model

class HeartPressure(
    valueStr: String,
    override val lastUpdateTs: Long
): Attribute<Pair<Int, Int>>() {
    override val value = valueStr.split(",")[0].toInt() to valueStr.split(",")[1].toInt()

    val sys = value.first
    val dis = value.second

    val hpDegree = when {
        (sys in 0..30 && dis in 60..70) -> Degree.Danger
        (sys in 31..60 && dis in 71..80) -> Degree.Warning
        (sys in 61..80 && dis in 81..90) -> Degree.Attention
        (sys in 81..90 && dis in 91..100) -> Degree.Normal
        (sys in 91..110 && dis in 101..110) -> Degree.Healthy
        else -> Degree.Danger
    }
}