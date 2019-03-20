package com.k2.musicdb.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.k2.musicdb.R;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;
import com.k2.musicdb.data.models.SearchHit;
import com.k2.musicdb.data.models.SearchResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/9/2019
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final List<SearchHit> hits;
    private final SearchResultClickListener clickListener;

    public SearchAdapter(List<SearchHit> hits, SearchResultClickListener clickListener) {
        this.hits = hits;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_search_result,
                parent,
                false
        );
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResult searchResult = hits.get(position).getResult();
        holder.title.setText(searchResult.getTitleWithFeatured());
        holder.subTitle.setText(searchResult.getPrimaryArtist().getName());
        String imgUrl = getImageUrl(searchResult);
        if (imgUrl == null) {
            ImageUtils.loadImageIntoView(holder.thumbnail, R.drawable.ic_album);
        } else {
            ImageUtils.loadImageIntoView(holder.thumbnail, imgUrl, R.drawable.ic_album);
        }
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_res_title)
        TextView title;

        @BindView(R.id.search_res_subtitle)
        TextView subTitle;

        @BindView(R.id.search_res_thumbnail)
        ImageView thumbnail;

        @BindView(R.id.holder)
        View parent;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.holder)
        void onSearchResultClick() {
            if (clickListener != null) {
                clickListener.onResultClick(hits.get(getAdapterPosition()).getResult());
            }
        }
    }

    @Nullable
    private String getImageUrl(SearchResult searchResult) {
        String url = searchResult.getSongArtImageThumbnailUrl();
        if (!Utils.isImageDefaultAvatar(url))
            return url;
        url = searchResult.getHeaderImageThumbnailUrl();
        if (!Utils.isImageDefaultAvatar(url))
            return url;
        return null;
    }

}
