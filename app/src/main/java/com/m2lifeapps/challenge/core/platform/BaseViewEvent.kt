package com.m2lifeapps.challenge.core.platform

import com.m2lifeapps.data.remote.response.ErrorResponse

sealed class BaseViewEvent {
   data  class ErrorMessage(val e: ErrorResponse?) : BaseViewEvent() {

    }

}
