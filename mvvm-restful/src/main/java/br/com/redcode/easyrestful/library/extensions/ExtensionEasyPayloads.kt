package br.com.redcode.easyrestful.library.extensions

import br.com.redcode.easyreftrofit.library.Payload
import br.com.redcode.spinnable.library.model.Spinnable

fun <T> List<Payload<T>>?.toModel(): List<T>? = this?.map { obj -> obj.toModel() }
fun List<Payload<Spinnable>>?.toModel2(): List<Spinnable>? = this?.map { obj -> obj.toModel() }

object extractPayload {
    infix fun <T> safe(list: List<Payload<T>>?): List<T> = list?.toModel()
        ?: emptyList()

    infix fun safeSpinnable(list: List<Payload<Spinnable>>?): List<Spinnable> = list?.toModel2()
        ?: emptyList()

    infix fun <T> safe(obj: Payload<T>?): T? = obj?.toModel()

}