package com.m2lifeapps.challenge.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.m2lifeapps.challenge.core.ext.observeEvent
import com.m2lifeapps.challenge.core.firebase.FirebaseEventParam
import com.m2lifeapps.challenge.core.firebase.FirebaseHelper
import com.m2lifeapps.challenge.core.platform.GlobalApplication.Companion.firebaseAnalytics

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }
    lateinit var binding: DB

    abstract fun getScreenKey(): String
    abstract fun onDataBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        onDataBinding()
        getScreenKey()
        observeEvent(viewModel.baseEvent, ::onViewEvent)

        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        logEventFirebase(
            FirebaseEventParam.Event.SCREEN_VIEW,
            FirebaseHelper.generateFirebaseEventParams(name = getScreenKey())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    fun logEventFirebase(eventName: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.ErrorMessage -> {
                Toast.makeText(requireContext(), event.e?.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onResume() {
        firebaseAnalytics.setAnalyticsCollectionEnabled(false)
        super.onResume()
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
    }

}
