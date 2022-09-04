package com.m2lifeapps.challenge.ui.favorited.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.core.bindings.loadImage
import com.m2lifeapps.challenge.core.common.DateHelper
import com.m2lifeapps.challenge.core.local.entity.FavoriteEntity
import com.m2lifeapps.challenge.core.platform.BaseListAdapter
import com.m2lifeapps.challenge.core.platform.BaseViewHolder
import com.m2lifeapps.challenge.databinding.ViewholderFavoritedBinding
import com.m2lifeapps.challenge.ui.favorited.domain.FavoritedViewModel

class FavoritedAdapter(
    val viewModel: FavoritedViewModel,
    val resultList: List<FavoriteEntity>
) : BaseListAdapter<FavoriteEntity>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    lateinit var holder: FavoritedViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return FavoritedViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoritedViewHolder -> {
                this.holder = holder
                resultList.get(position).let { holder.bind(viewModel, it, position) }
            }

        }
    }

    override fun getItemCount(): Int {
        return resultList.size ?: 0
    }

    fun changeIcon(items: NewsResponse.Article, empty: Boolean, position: Int) {
        if (position == holder.absoluteAdapterPosition) {

            notifyItemChanged(position)
            notifyDataSetChanged()
         }

    }
}

class FavoritedViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    BaseViewHolder<ViewholderFavoritedBinding>(
        binding = ViewholderFavoritedBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        viewModels: FavoritedViewModel,
        items: FavoriteEntity,
        position: Int
    ) {

        binding.apply {

            viewModel = viewModels
            item = items
            dateTextView.text = DateHelper.formatDateTime(items.publishedAt.toString())
            items.urlToImage?.let { roundedImageView.loadImage(it) }
            descriptionTextView.text =
                HtmlCompat.fromHtml(items.description.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)

            executePendingBindings()
        }
    }
}
