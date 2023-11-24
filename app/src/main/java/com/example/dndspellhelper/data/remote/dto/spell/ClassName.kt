package com.example.dndspellhelper.data.remote.dto.spell

data class ClassName(
    val index: String,
    val name: String,
    val url: String
) {
    override fun toString(): String {
        return this.name
    }
}