package br.com.redcode.easyrestful.library.extensions

import okhttp3.MultipartBody

/*
    CREATED BY @PEDROFSN IN 11/03/20 15:43
*/

fun String.toMultipart(partName: String) = MultipartBody.Part.createFormData(partName, this)