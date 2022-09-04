package com.m2lifeapps.challenge.ui.favorited.presentation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.m2lifeapps.challenge.R
import com.m2lifeapps.challenge.core.common.PageName
import com.m2lifeapps.challenge.core.ext.observe
import com.m2lifeapps.challenge.core.ext.observeEvent
import com.m2lifeapps.challenge.core.platform.BaseFragment
import com.m2lifeapps.challenge.databinding.FragmentFavoritedBinding
import com.m2lifeapps.challenge.ui.favorited.domain.FavoritedViewEvent
import com.m2lifeapps.challenge.ui.favorited.domain.FavoritedViewModel
import com.m2lifeapps.challenge.ui.favorited.presentation.adapter.FavoritedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritedFragment : BaseFragment<FragmentFavoritedBinding, FavoritedViewModel>(
    layoutId = R.layout.fragment_favorited,
    viewModelClass = FavoritedViewModel::class.java
) {

    lateinit var favoritedAdapter: FavoritedAdapter

    override fun getScreenKey(): String {
        return PageName.favorite
    }

    override fun onDataBinding() {
        binding.viewModel = viewModel
        observeEvent(viewModel.event, ::onViewEvent)
        observe(viewModel.favorited) {
            favoritedAdapter = FavoritedAdapter(viewModel, it)
            binding.recyclerView.apply {
                adapter = favoritedAdapter
            }
        }
    }

    private fun onViewEvent(event: FavoritedViewEvent) {
        when (event) {
            FavoritedViewEvent.RemoveFavorited -> {
                favoritedAdapter.notifyDataSetChanged()
                viewModel.getFavoritedList()

            }
            is FavoritedViewEvent.NavigateToDetail -> {

                val news =
                    Gson().toJson(event.article)
                val bundle = Bundle()
                bundle.putString("news", news)
                findNavController().navigate(R.id.action_favoritedFragment_to_detailFragment, bundle)
            }
        }
    }
}