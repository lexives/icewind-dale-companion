package com.delarax.icewindDale.companion.extensions

import com.delarax.icewindDale.companion.models.Date

fun Date?.toStringOrNull(): String? = this.toString().let {
    if (it == "null") null else it
}

fun Date?.toStringOrEmpty(): String = this.toString().let {
    if (it == "null") "" else it
}