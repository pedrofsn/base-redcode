package br.com.redcode.base.interfaces

interface Payload<T> {
    fun toModel(): T
}