package com.m2lifeapps.challenge.core.firebase

import android.os.Bundle

object FirebaseHelper {

    fun generateFirebaseEventParams(
        name: String? = null,
        title: String? = null,
    ): Bundle {
        val bundle = Bundle()
        if (name != null)
            bundle.putString(FirebaseEventParam.Params.SCREEN_NAME, name)
        if (title != null)
            bundle.putString(FirebaseEventParam.Params.TITLE, title)
        return bundle
    }
}
