package com.k2.musicdb.album;

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
import com.k2.musicdb.common.BaseFragment;
import com.k2.musicdb.common.Constants;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;
import com.k2.musicdb.dagger.ActivityScoped;
import com.k2.musicdb.data.models.Album;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.source.DataRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@ActivityScoped
public class AlbumFragment extends BaseFragment<AlbumViewModel, Album> {

    private static final String TAG = "AlbumFragment";
    public static final String KEY_ALBUM_ID = "album_id";

    public AlbumFragment() {
        // Required empty public constructor
    }

    private Unbinder unbinder;
    private String albumId;

    @BindView(R.id.album_data)
    ScrollView albumContainer;
    @BindView(R.id.error_container)
    View errorContainer;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_retry)
    Button errorRetry;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.artist_name)
    TextView artist;
    @BindView(R.id.released_on)
    TextView releasedOn;
    @BindView(R.id.artist_container)
    View artistContainer;
    @BindView(R.id.album_art)
    ImageView albumArt;
    @BindView(R.id.artist_image)
    ImageView artistImage;

    @Inject
    public DataRepository dataRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel(AlbumViewModel.class, dataRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        if (args != null) {
            albumId = args.getString(KEY_ALBUM_ID);
            String backdropUrl = args.getString(Constants.KEY_BACKDROP_URL);
            if (backdropUrl != null) {
                MainActivity mainActivity = getParentActivity();
                if (mainActivity != null)
                    mainActivity.setBackdrop(backdropUrl);
            }
        }

        if (data == null || !String.valueOf(data.getId()).equals(albumId))
            fetchData(albumId);
        else
            showData(data);

        getViewModel().getDataObserver().observe(this, album -> {
            this.data = album;
            showData(album);
        });

        getViewModel().getErrorObserver().observe(this, throwable -> {
            showError(throwable);
        });

        return rootView;
    }

    @Override
    public String getTagId() {
        return TAG;
    }

    @Override
    public void fetchData(String id) {
        getViewModel().fetchAlbum(id);
        errorContainer.setVisibility(View.INVISIBLE);
        albumContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(Album album) {
        errorContainer.setVisibility(View.INVISIBLE);
        albumContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        title.setText(album.getName());
        ImageUtils.loadImageIntoView(albumArt, album.getCoverArtUrl(), R.drawable.ic_album);
        Artist artistData = album.getArtist();
        if (artistData != null) {
            artist.setText(artistData.getName());
            ImageUtils.loadImageIntoView(artistImage, artistData.getImageUrl(), R.drawable.ic_person);
        } else {
            artistContainer.setVisibility(View.GONE);
        }

        if (album.getReleaseDate() != null)
            releasedOn.setText(Utils.dateToLocalString(album.getReleaseDate(), Utils.getLongDateFormat(getContext())));
        else
            releasedOn.setText(getString(R.string.not_available_placeholder));
    }

    @Override
    public void showError(Throwable error) {
        errorContainer.setVisibility(View.VISIBLE);
        albumContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        errorText.setText(error.getMessage());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static AlbumFragment getInstance(@NonNull String albumId, @Nullable String backdropUrl) {
        final AlbumFragment albumFragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(AlbumFragment.KEY_ALBUM_ID, albumId);
        args.putString(Constants.KEY_BACKDROP_URL, backdropUrl);
        albumFragment.setArguments(args);
        return albumFragment;
    }

}
