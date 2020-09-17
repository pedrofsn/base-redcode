package br.com.redcode.base.rest

import br.com.redcode.base.models.ErrorAPI
import br.com.redcode.base.utils.Constants.INVALID_VALUE
import extract
import java.io.Serializable

/**
 * Created by pedrofsn on 21/02/18.
 */

open class PayloadError(
    val erro: Boolean? = false,
    val msg: String? = "",
    private val msg_dev: String? = "",
    val acao: Int? = INVALID_VALUE,
    val id: String? = ""
) : Serializable {
    fun toModel() = ErrorAPI(
        erro = extract safe erro,
        msg = extract safe msg,
        msg_dev = extract safe msg_dev,
        action = extract safe acao,
        id = extract safe id
    )
}