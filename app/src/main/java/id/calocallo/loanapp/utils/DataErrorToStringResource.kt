package id.calocallo.loanapp.utils

import id.calocallo.loanapp.R
import id.calocallo.mybookpedia.core.domain.DataError

fun DataError.toErrorText(): String {
    return when (this) {
        DataError.Local.DISK_FULL -> "Oops, it seems like your disk is full." // R.string.error_disk_full
        DataError.Local.UNKNOWN -> "Oops, something went wrong." // R.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> "The request timed out." //R.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUEST -> "Your quota seems to be exceeded." // R.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> "Couldn't reach server, please check your internet connection." //R.string.error_no_internet
        DataError.Remote.SERVER -> "Oops, something went wrong1." // R.string.error_unknown
        DataError.Remote.SERIALIZATION -> "Couldn't parse data." //R.string.error_serialization
        DataError.Remote.UNKNOWN -> "Oops, something went wrong2." //R.string.error_unknown
    }
}