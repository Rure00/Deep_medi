package com.rure.deepmedi.presentation.model

import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.data.entity.UserAttribute

class GenderAttr(
    value: Int,
    override val tag: AttributeTag,
    override val lastUpdateTs: Long
): Attribute<String>() {
    override val value: String = if(value == 0) "남성"  else "여성"
}