package com.m2lifeapps.challenge.ui.detail.presentation

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.core.text.HtmlCompat
import com.google.gson.Gson
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.R
import com.m2lifeapps.challenge.core.bindings.loadImage
import com.m2lifeapps.challenge.core.common.DateHelper
import com.m2lifeapps.challenge.core.common.PageName
import com.m2lifeapps.challenge.core.ext.observe
import com.m2lifeapps.challenge.core.platform.BaseFragment
import com.m2lifeapps.challenge.databinding.FragmentDetailBinding
import com.m2lifeapps.challenge.ui.detail.domain.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    layoutId = R.layout.fragment_detail,
    viewModelClass = DetailViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getScreenKey(): String {
        return PageName.detail
    }

    override fun onDataBinding() {
        binding.viewModel = viewModel
        arguments?.getString("news", "0")?.let {
            viewModel.newsResponse(Gson().fromJson(it, NewsResponse.Article::class.java))
        }

        observe(viewModel.newsResponse) {
            binding.apply {
                imageView.loadImage(it.urlToImage.toString())
                dateTextView.text = DateHelper.formatDateTime(it.publishedAt)
                titleTextView.text = it.title
                descriptionTextView.text =
                    HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
        }
        binding.navigateNews.setOnClickListener {
            val intent = Intent(ACTION_VIEW)
            intent.data = Uri.parse(viewModel.newsResponse.value?.url.toString())
            startActivity(intent)
        }

    }

}