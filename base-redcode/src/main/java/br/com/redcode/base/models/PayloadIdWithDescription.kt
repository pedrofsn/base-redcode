package br.com.redcode.base.models

import br.com.redcode.base.extensions.extract
import br.com.redcode.base.interfaces.Payload
import br.com.redcode.spinnable.library.model.Spinnable

data class PayloadIdWithDescription(
        val id: String?,
        val descricao: String?,
        val selecionado: Boolean?
) : Payload<Spinnable> {

    override fun toModel() = Spinnable(
            id = extract safe id,
            description = extract safe descricao,
            selected = extract safe selecionado
    )

}