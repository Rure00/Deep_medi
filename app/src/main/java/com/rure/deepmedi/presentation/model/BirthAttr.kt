package com.rure.deepmedi.presentation.model

import java.time.LocalDate

class BirthAttr(
    valueStr: String,
    override val lastUpdateTs: Long
): Attribute<LocalDate>() {
    override val value: LocalDate = LocalDate.parse(valueStr)
}