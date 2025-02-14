package com.rure.deepmedi.presentation.model

import com.rure.deepmedi.data.entity.AttributeTag

abstract class Attribute<out T>{
    abstract val tag: AttributeTag
    abstract val value: T
    abstract val lastUpdateTs: Long
}

fun List<Attribute<*>>.find(tag: AttributeTag): Attribute<*>? {
    return this.find { it.tag == tag }
}