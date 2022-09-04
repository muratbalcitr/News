package com.m2lifeapps.challenge.ui.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.m2lifeapps.challenge.R
import com.m2lifeapps.data.remote.response.NewsResponse
 import com.m2lifeapps.challenge.core.bindings.loadImage
import com.m2lifeapps.challenge.core.common.DateHelper
import com.m2lifeapps.challenge.core.platform.BaseListAdapter
import com.m2lifeapps.challenge.core.platform.BaseViewHolder
import com.m2lifeapps.challenge.databinding.ViewholderUpcomingBinding
import com.m2lifeapps.challenge.ui.main.domain.MainListViewModel

class MainListAdapter(
    val viewModel: MainListViewModel,
    val resultList: ArrayList<NewsResponse.Article>
) : BaseListAdapter<NewsResponse.Article>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    lateinit var holder: PopularListViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return PopularListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularListViewHolder -> {
                this.holder = holder
                resultList.get(position).let { holder.bind(viewModel, it, position) }
            }

        }
    }

    fun updateList(list: List<NewsResponse.Article>?) {
        list?.let { resultList.addAll(it) }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return resultList.size ?: 0
    }

    fun changeIcon(items: NewsResponse.Article, empty: Boolean, position: Int) {
        resultList.get(position).isFavorite = !resultList.get(position).isFavorite
        notifyItemChanged(position)
        notifyDataSetChanged()


    }
}

class PopularListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    BaseViewHolder<ViewholderUpcomingBinding>(
        binding = ViewholderUpcomingBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        viewModels: MainListViewModel,
        items: NewsResponse.Article,
        position: Int
    ) {

        binding.apply {
            viewModel = viewModels
            item = items
            dateTextView.text = DateHelper.formatDateTime(items.publishedAt)
            items.urlToImage?.let { roundedImageView.loadImage(it) }
            favoriteImageView.setImageResource(
                if (items.isFavorite)
                    R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
            descriptionTextView.text =
                HtmlCompat.fromHtml(items.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            favoriteImageView.setOnClickListener {
                viewModels.changeFavorite(items, position)
            }
            executePendingBindings()
        }
    }
}