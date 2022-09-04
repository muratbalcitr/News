package com.m2lifeapps.challenge.ui.main.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.m2lifeapps.challenge.R
import com.m2lifeapps.challenge.core.common.PageName
import com.m2lifeapps.challenge.core.ext.observe
import com.m2lifeapps.challenge.core.ext.observeEvent
import com.m2lifeapps.challenge.core.firebase.FirebaseEventParam
import com.m2lifeapps.challenge.core.firebase.FirebaseHelper
import com.m2lifeapps.challenge.core.platform.BaseFragment
import com.m2lifeapps.challenge.databinding.FragmentMainBinding
import com.m2lifeapps.challenge.ui.main.domain.MainListViewEvent
import com.m2lifeapps.challenge.ui.main.domain.MainListViewModel
import com.m2lifeapps.challenge.ui.main.presentation.adapter.MainListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainListFragment : BaseFragment<FragmentMainBinding, MainListViewModel>(
    layoutId = R.layout.fragment_main,
    viewModelClass = MainListViewModel::class.java
) {

    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var lastItemPosition: Int = 0
    var page = 1
    var pageSize = 25


    lateinit var mainAdapter: MainListAdapter
    override fun getScreenKey(): String {
        return PageName.mainList
    }

    override fun onDataBinding() {
        viewModel.fetchNews(page, pageSize)
        observeEvent(viewModel.event, ::onViewEvent)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        binding.recyclerView.setHasFixedSize(true)
        observe(viewModel.newsResponse) { response ->

            if (page < 2)
                mainAdapter = MainListAdapter(viewModel, response?.articles as ArrayList)
            else mainAdapter.updateList(response?.articles)

            binding.recyclerView.adapter = mainAdapter


            binding.recyclerView.apply {
                if (response?.articles?.isNotEmpty() == true) {
                    scrollToPosition(lastItemPosition)
                    smoothScrollToPosition(lastItemPosition)
                }
                binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    if (scrollY == (v as NestedScrollView).getChildAt(0).measuredHeight - v.getMeasuredHeight()) {
                        visibleItemCount = this.layoutManager?.childCount!!
                        totalItemCount = this.layoutManager?.itemCount!!
                        pastVisiblesItems =
                            (this.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()!!

                        page += 1
                        viewModel.fetchNews(
                            page, pageSize
                        )
                    }
                }
            }
        }
        binding.swipeRefreshLayout.apply {

            setOnRefreshListener {
                isRefreshing = !isRefreshing
                viewModel.fetchNews(page, pageSize)
            }
        }

    }

    private fun onViewEvent(event: MainListViewEvent) {
        when (event) {
            is MainListViewEvent.NavigateToDetails -> {
                val news =
                    Gson().toJson(event.item)
                val bundle = Bundle()
                bundle.putString("news", news)
                findNavController().navigate(R.id.action_mainListFragment_to_detailFragment, bundle)
            }
            is MainListViewEvent.AddFavorite -> {
                logEventFirebase(
                    FirebaseEventParam.Event.FAVORITE,
                    FirebaseHelper.generateFirebaseEventParams(title = event.item.title)
                )
                mainAdapter.changeIcon(event.item, event.empty, event.position)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_favorite -> {
                findNavController().navigate(R.id.action_mainListFragment_to_favoritedFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}