package com.rure.deepmedi.presentation.model

import com.rure.deepmedi.data.entity.AttributeTag
import java.time.LocalDate

class BirthAttr(
    valueStr: String,
    override val tag: AttributeTag,
    override val lastUpdateTs: Long
): Attribute<LocalDate>() {
    override val value: LocalDate

    init {
        val dateString = "${valueStr.substring(0..3)}-${valueStr.substring(4..5)}-${valueStr.substring(6..7)}"
        value = LocalDate.parse(dateString)
    }

    companion object {
        fun emptyObject() = BirthAttr(
            valueStr = "20000101",
            tag = AttributeTag.Birth,
            lastUpdateTs = 0L
        )
    }
}