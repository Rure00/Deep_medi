package com.rure.deepmedi.presentation.model

abstract class Attribute<T>{
    abstract val value: T
    abstract val lastUpdateTs: Long
}