package com.rure.deepmedi.presentation.model

enum class Degree(val tag: String) {
    Danger("위험"),
    Warning("경고"),
    Attention("주의"),
    Normal("정상"),
    Healthy("건강")
}