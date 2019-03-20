package com.k2.musicdb.song;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.k2.musicdb.MainActivity;
import com.k2.musicdb.R;
import com.k2.musicdb.artist.ArtistFragment;
import com.k2.musicdb.common.BaseFragment;
import com.k2.musicdb.common.Constants;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;
import com.k2.musicdb.dagger.ActivityScoped;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.models.Media;
import com.k2.musicdb.data.models.Song;
import com.k2.musicdb.data.source.DataRepository;
import com.k2.musicdb.views.DescriptionView;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@ActivityScoped
public class SongFragment extends BaseFragment<SongViewModel, Song> implements ArtistClickListener {

    private static final String TAG = "SongFragment";
    public static final String KEY_SONG_ID = "song_id";

    private String songId;
    private Unbinder unbinder;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.artist)
    TextView artist;
    @BindView(R.id.description)
    DescriptionView description;
    @BindView(R.id.released_on)
    TextView releasedOn;
    @BindView(R.id.song_art)
    ImageView songArt;
    @BindView(R.id.album_title)
    TextView albumTitle;
    @BindView(R.id.album_artist)
    TextView albumArtist;
    @BindView(R.id.album_art)
    ImageView albumArt;
    @BindView(R.id.artist_list)
    RecyclerView artistList;

    @BindView(R.id.spotify)
    ImageView spotify;
    @BindView(R.id.youtube)
    ImageView youtube;

    @BindView(R.id.song_data)
    ScrollView songContainer;
    @BindView(R.id.about_container)
    View aboutContainer;
    @BindView(R.id.album_container)
    View albumContainer;
    @BindView(R.id.error_container)
    View errorContainer;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_retry)
    Button errorRetry;

    private LinearLayoutManager nestedLayoutManager;
    private Resources resources;

    @Inject
    public DataRepository dataRepository;

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel(SongViewModel.class, dataRepository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_song, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        resources = getResources();
        nestedLayoutManager = new LinearLayoutManager(
                rootView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                int totWidth = getWidth() - (getPaddingLeft() + getPaddingRight() - Utils.dpToPixels(resources, 6));
                lp.width = ((40 * totWidth) / 100);
                return true;
            }
        };

        Bundle args = getArguments();
        if (args != null) {
            songId = args.getString(KEY_SONG_ID);
            String backdropUrl = args.getString(Constants.KEY_BACKDROP_URL);
            if (backdropUrl != null) {
                MainActivity mainActivity = getParentActivity();
                if (mainActivity != null)
                    mainActivity.setBackdrop(backdropUrl);
            }
        }

        if (data == null || !String.valueOf(data.getId()).equals(songId))
            fetchData(songId);
        else
            showData(data);

        getViewModel().getDataObserver().observe(this, song -> {
            this.data = song;
            showData(song);
        });
        getViewModel().getErrorObserver().observe(this, this::showError);

        return rootView;
    }

    @Override
    public void showData(Song song) {
        errorContainer.setVisibility(View.INVISIBLE);
        songContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        title.setText(song.getTitle());
        artist.setText(song.getPrimaryArtist().getName());
        String songArtUrl = song.getSongArtImageThumbnailUrl();
        if (Utils.isImageDefaultAvatar(songArtUrl))
            ImageUtils.loadImageIntoView(songArt, R.drawable.ic_album);
        else
            ImageUtils.loadImageIntoView(songArt, songArtUrl, R.drawable.ic_album);

        // Album
        if (song.getAlbum() != null) {
            albumTitle.setText(song.getAlbum().getName());
            albumArtist.setText(song.getAlbum().getArtist().getName());
            String albumArtUrl = song.getAlbum().getCoverArtUrl();
            if (Utils.isImageDefaultAvatar(albumArtUrl))
                ImageUtils.loadImageIntoView(albumArt, R.drawable.ic_album);
            else
                ImageUtils.loadImageIntoView(albumArt, albumArtUrl, R.drawable.ic_album);
        } else {
            albumContainer.setVisibility(View.GONE);
        }

        // Artists
        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(song.getPrimaryArtist());
        artists.addAll(song.getFeaturedArtists());

        ArtistAdapter artistAdapter = new ArtistAdapter(artists, this);
        if (artistList.getLayoutManager() == null)
            artistList.setLayoutManager(nestedLayoutManager);
        artistList.setAdapter(artistAdapter);

        // About
        String descriptionText = song.getDescription();
        if (descriptionText != null && descriptionText.length() > 1)
            description.setDescription(descriptionText);
        else
            description.setVisibility(View.GONE);

        if (song.getReleaseDate() != null)
            releasedOn.setText(Utils.dateToLocalString(song.getReleaseDate(), Utils.getLongDateFormat(getContext())));
        else
            releasedOn.setText(getString(R.string.not_available_placeholder));

        if (description.getVisibility() == View.GONE && song.getReleaseDate() == null)
            aboutContainer.setVisibility(View.GONE);

        // Media
        youtube.setEnabled(song.getMediaByProvider(Constants.MEDIA_YOUTUBE) != null);
        spotify.setEnabled(song.getMediaByProvider(Constants.MEDIA_SPOTIFY) != null);
    }

    @Override
    public void showError(Throwable error) {
        errorContainer.setVisibility(View.VISIBLE);
        songContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        errorText.setText(error.getMessage());
    }

    @OnClick(R.id.spotify)
    void openSpotify() {
        Media media = data.getMediaByProvider(Constants.MEDIA_SPOTIFY);
        if (media != null) {
            Utils.openLink(getContext(), media.getUrl());
        }
    }

    @OnClick(R.id.youtube)
    void openYoutube() {
        Media media = data.getMediaByProvider(Constants.MEDIA_YOUTUBE);
        if (media != null) {
            Utils.openLink(getContext(), media.getUrl());
        }
    }

    @OnClick(R.id.genius)
    void openGenius() {
        Utils.openLink(getContext(), data.getUrl(), true);
    }

    @OnClick(R.id.error_retry)
    void retry() {
        fetchData(songId);
    }

    @Override
    public String getTagId() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        nestedLayoutManager = null;
        resources = null;
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData(String id) {
        getViewModel().fetchSong(id);
        errorContainer.setVisibility(View.INVISIBLE);
        songContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onArtistClick(Artist artist) {
        String backdrop = artist.getHeaderImageUrl();
        if (backdrop == null)
            backdrop = artist.getImageUrl();
        Navigation.findNavController(songContainer).navigate(
                R.id.action_open_artist,
                ArtistFragment.makeBundle(String.valueOf(artist.getId()), backdrop)
        );
    }

    public static Bundle makeBundle(@NonNull String songId, @Nullable String backdropUrl) {
        Bundle args = new Bundle();
        args.putString(SongFragment.KEY_SONG_ID, songId);
        args.putString(Constants.KEY_BACKDROP_URL, backdropUrl);
        return args;
    }

}
