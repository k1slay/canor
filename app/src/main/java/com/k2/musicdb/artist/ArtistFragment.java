package com.k2.musicdb.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.k2.musicdb.MainActivity;
import com.k2.musicdb.R;
import com.k2.musicdb.common.BaseFragment;
import com.k2.musicdb.common.Constants;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.source.DataRepository;
import com.k2.musicdb.views.DescriptionView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ArtistFragment extends BaseFragment<ArtistViewModel, Artist> {

    public static final String KEY_ARTIST_ID = "artist_id";
    private static final String TAG = "ArtistFragment";

    public ArtistFragment() {
        // Required empty public constructor
    }

    private Unbinder unbinder;
    private String artistId;

    @BindView(R.id.artist_data)
    ScrollView artistContainer;
    @BindView(R.id.error_container)
    View errorContainer;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_retry)
    Button errorRetry;

    @BindView(R.id.facebook)
    ImageView facebook;
    @BindView(R.id.instagram)
    ImageView instagram;
    @BindView(R.id.twitter)
    ImageView twitter;

    @BindView(R.id.description)
    DescriptionView description;
    @BindView(R.id.title)
    TextView name;
    @BindView(R.id.artist_image)
    ImageView artistImage;

    @BindView(R.id.aka_chips)
    ChipGroup akaChips;

    @Inject
    public DataRepository dataRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel(ArtistViewModel.class, dataRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        if (args != null) {
            artistId = args.getString(KEY_ARTIST_ID);
            String backdropUrl = args.getString(Constants.KEY_BACKDROP_URL);
            if (backdropUrl != null) {
                MainActivity mainActivity = getParentActivity();
                if (mainActivity != null)
                    mainActivity.setBackdrop(backdropUrl);
            }
        }

        if (data == null || !String.valueOf(data.getId()).equals(artistId))
            fetchData(artistId);
        else
            showData(data);

        getViewModel().getDataObserver().observe(this, artist -> {
            this.data = artist;
            showData(artist);
        });

        getViewModel().getErrorObserver().observe(this, this::showError);

        return rootView;
    }

    @Override
    public void showData(Artist artist) {
        errorContainer.setVisibility(View.INVISIBLE);
        artistContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        String image = artist.getImageUrl();
        if (Utils.isImageDefaultAvatar(image))
            ImageUtils.loadImageIntoView(artistImage, R.drawable.ic_person);
        else
            ImageUtils.loadImageIntoView(artistImage, image, R.drawable.ic_person);

        if (artist.getDescription().length() < 3) {
            description.setVisibility(View.GONE);
        } else {
            description.setDescription(artist.getDescription());
        }
        name.setText(artist.getName());

        // Social
        twitter.setEnabled(artist.getTwitterName() != null);
        facebook.setEnabled(artist.getFacebookName() != null);
        instagram.setEnabled(artist.getInstagramName() != null);

        if (!artist.getAlternateNames().isEmpty()) {
            akaChips.setVisibility(View.VISIBLE);
            akaChips.setChipSpacingVertical(25);
            for (String name : artist.getAlternateNames()) {
                Chip chip = new Chip(akaChips.getContext());
                chip.setText(name);
                akaChips.addView(chip);
            }
        } else {
            akaChips.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        errorContainer.setVisibility(View.VISIBLE);
        artistContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        errorText.setText(error.getMessage());
    }

    @Override
    public void fetchData(String artistId) {
        getViewModel().fetchArtist(artistId);
        errorContainer.setVisibility(View.INVISIBLE);
        artistContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.error_retry)
    void retry() {
        fetchData(artistId);
    }

    @OnClick({R.id.facebook, R.id.twitter, R.id.instagram})
    void openSocial(ImageView view) {
        String url = null;
        switch (view.getId()) {
            case R.id.facebook:
                if (data != null && data.getFacebookName() != null) {
                    url = String.format(Constants.FACEBOOK_URL_FORMAT, data.getFacebookName());
                }
                break;
            case R.id.twitter:
                if (data != null && data.getTwitterName() != null) {
                    url = String.format(Constants.TWITTER_URL_FORMAT, data.getTwitterName());
                }
                break;
            case R.id.instagram:
                if (data != null && data.getInstagramName() != null) {
                    url = String.format(Constants.INSTAGRAM_URL_FORMAT, data.getInstagramName());
                }
                break;
        }
        if (url != null)
            Utils.openLink(view.getContext(), url);
    }

    @Override
    public String getTagId() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static Bundle makeBundle(@NonNull String artistId, @Nullable String backdropUrl) {
        Bundle args = new Bundle();
        args.putString(ArtistFragment.KEY_ARTIST_ID, artistId);
        args.putString(Constants.KEY_BACKDROP_URL, backdropUrl);
        return args;
    }

}
