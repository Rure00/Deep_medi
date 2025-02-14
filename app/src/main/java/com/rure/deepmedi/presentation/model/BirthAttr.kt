package com.rure.deepmedi.presentation.model

import com.rure.deepmedi.data.entity.AttributeTag
import java.time.LocalDate

class BirthAttr(
    valueStr: String,
    override val tag: AttributeTag,
    override val lastUpdateTs: Long
): Attribute<LocalDate>() {
    override val value: LocalDate = LocalDate.parse(valueStr)
}