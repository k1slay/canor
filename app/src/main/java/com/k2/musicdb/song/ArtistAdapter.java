package com.k2.musicdb.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.k2.musicdb.R;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;
import com.k2.musicdb.data.models.Artist;

import java.util.List;

import androidx.annotation.NonNull;
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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private final List<Artist> artists;
    private final ArtistClickListener artistClickListener;

    public ArtistAdapter(List<Artist> artists, ArtistClickListener artistClickListener) {
        this.artists = artists;
        this.artistClickListener = artistClickListener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_artist,
                parent,
                false
        );
        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.title.setText(artist.getName());
        String imageUrl = artist.getImageUrl();
        if (Utils.isImageDefaultAvatar(imageUrl))
            ImageUtils.loadImageIntoView(holder.thumbnail, R.drawable.ic_person);
        else
            ImageUtils.loadImageIntoView(holder.thumbnail, imageUrl, R.drawable.ic_person);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.artist_name)
        TextView title;

        @BindView(R.id.artist_art)
        ImageView thumbnail;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.artist_art)
        void onCLick() {
            if (artistClickListener != null) {
                artistClickListener.onArtistClick(artists.get(getAdapterPosition()));
            }
        }
    }

}
