package com.rure.deepmedi.utils

import java.time.LocalDate
import java.time.Period

fun LocalDate.calculateAge(): Int {
    val currentDate = LocalDate.now()
    return Period.between(this, currentDate).years
}