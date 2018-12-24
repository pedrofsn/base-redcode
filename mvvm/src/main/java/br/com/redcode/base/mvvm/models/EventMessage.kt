package br.com.redcode.base.mvvm.models

data class EventMessage(
        val event: String,
        val obj: Any? = null
)