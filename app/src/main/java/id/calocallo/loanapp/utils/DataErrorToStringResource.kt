package id.calocallo.loanapp.utils

import id.calocallo.mybookpedia.core.domain.DataError

fun DataError.toErrorText(): String =
    when (this) {
        DataError.Local.DISK_FULL -> "Oops, it seems like your disk is full."
        DataError.Local.UNKNOWN -> "Oops, something went wrong."
        DataError.Remote.REQUEST_TIMEOUT -> "The request timed out."
        DataError.Remote.TOO_MANY_REQUEST -> "Your quota seems to be exceeded."
        DataError.Remote.NO_INTERNET -> "Couldn't reach server, please check your internet connection."
        DataError.Remote.SERVER -> "Oops, something went wrong."
        DataError.Remote.SERIALIZATION -> "Couldn't parse data."
        DataError.Remote.UNKNOWN -> "Oops, something went wrong."
    }
