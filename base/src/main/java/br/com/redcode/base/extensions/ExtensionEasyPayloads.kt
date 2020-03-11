package br.com.redcode.base.extensions

import br.com.redcode.base.interfaces.Payload
import br.com.redcode.base.utils.Constants.EMPTY_STRING
import br.com.redcode.spinnable.library.model.Spinnable

fun <T> List<Payload<T>>?.toModel(): List<T>? = this?.map { obj -> obj.toModel() }
fun List<Payload<Spinnable>>?.toModel2(): List<Spinnable>? = this?.map { obj -> obj.toModel() }

object extract {
    infix fun <T> safe(list: List<Payload<T>>?): List<T> = list?.toModel()
        ?: emptyList()

    infix fun safeSpinnable(list: List<Payload<Spinnable>>?): List<Spinnable> = list?.toModel2()
        ?: emptyList()

    infix fun <T> safe(obj: Payload<T>?): T? = obj?.toModel()
    infix fun safe(value: String?): String = value ?: EMPTY_STRING
    infix fun safe(value: Int?): Int = value ?: -1
    infix fun safe(value: Boolean?): Boolean = value ?: false
    infix fun safe(value: Float?): Float = value ?: 0.0f
    infix fun safe(value: Double?): Double = value ?: 0.0
    infix fun safe(value: Long?): Long = value ?: -1

}