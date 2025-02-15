package com.rure.deepmedi.data.entity

sealed class AttributeTag(val tag: String) {
    data object Gender: AttributeTag("gender")
    data object Birth: AttributeTag("birth")
    data object HeartRate: AttributeTag("hr")
    data object BloodPressure: AttributeTag("bp")


    companion object {
        private val entries = listOf(Gender, Birth, HeartRate, BloodPressure)
        fun getFromTag(tag: String) = entries.find {
            it.tag == tag
        }
    }
}