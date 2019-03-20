package com.k2.musicdb.search;

import android.app.Service;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.k2.musicdb.MainActivity;
import com.k2.musicdb.R;
import com.k2.musicdb.common.BaseFragment;
import com.k2.musicdb.dagger.ActivityScoped;
import com.k2.musicdb.data.models.SearchResponse;
import com.k2.musicdb.data.models.SearchResult;
import com.k2.musicdb.data.source.DataRepository;
import com.k2.musicdb.song.SongFragment;

import javax.inject.Inject;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

@ActivityScoped
public class SearchFragment extends BaseFragment<SearchViewModel, SearchResponse> implements SearchResultClickListener {

    private static final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.search_box)
    EditText searchBox;

    @BindView(R.id.search_constraint_parent)
    ConstraintLayout constraintLayout;

    @BindView((R.id.search_list))
    RecyclerView searchResultList;

    @BindView((R.id.error_text))
    TextView errorText;

    @BindView((R.id.error_retry))
    Button errorRetry;

    @BindView((R.id.search_box_clear))
    View clearSearch;

    @BindView((R.id.search_box_icon))
    View searchIcon;

    @BindView((R.id.search_box_frame))
    View searchFrame;

    private View rootView;
    private InputMethodManager inputMethodManager;
    boolean pristine = true;
    private Unbinder unbinder;
    private Context context;

    @Inject
    public DataRepository dataRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel(SearchViewModel.class, dataRepository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        context = requireContext();
        inputMethodManager = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        unbinder = ButterKnife.bind(this, rootView);
        searchResultList.setLayoutManager(new LinearLayoutManager(context));
        if (!pristine)
            makeViewsStale();

        // Hacky way to check when keyboard is dismissed /\(-_-)/\
        // https://stackoverflow.com/a/38505869/3106978
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        getViewModel().getDataObserver().observe(this, searchResponse -> {
            this.data = searchResponse;
            showData(searchResponse);
        });

        getViewModel().getErrorObserver().observe(this, throwable -> {
            showError(throwable);
        });

        return rootView;
    }

    @OnFocusChange(R.id.search_box)
    void onFocusChange(boolean focused) {
        @ColorRes int hintColor = focused ? android.R.color.transparent : R.color.colorAccent;
        int clearActionVisibility = focused && !getSearchQuery().isEmpty() ? View.VISIBLE : View.INVISIBLE;
        searchBox.setHintTextColor(ContextCompat.getColor(context, hintColor));
        clearSearch.setVisibility(clearActionVisibility);
    }

    @OnTextChanged(R.id.search_box)
    void onSearchTextChange(CharSequence searchText) {
        int clearActionVisibility = searchText.length() <= 0 ? View.INVISIBLE : View.VISIBLE;
        clearSearch.setVisibility(clearActionVisibility);
    }

    @OnEditorAction(R.id.search_box)
    boolean onSearchAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            pristine = false;
            String searchString = getSearchQuery();
            if (TextUtils.isEmpty(searchString)) {
                Animation shake = AnimationUtils.loadAnimation(context, R.anim.error_shake);
                searchFrame.startAnimation(shake);
                return true;
            }
            fetchData(searchString);
            return true;
        }
        return false;
    }

    private String getSearchQuery() {
        return searchBox.getText().toString().trim();
    }

    @OnClick(R.id.error_retry)
    void onErrorRetry() {
        onSearchAction(EditorInfo.IME_ACTION_SEARCH);
    }

    @Override
    public void fetchData(String searchString) {
        makeViewsStale();
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
        ConstraintSet searching = new ConstraintSet();
        searching.clone(context, R.layout.fragment_search_searching);
        TransitionManager.beginDelayedTransition(constraintLayout);
        searching.applyTo(constraintLayout);
        getViewModel().performSearch(searchString);
    }

    private void makeViewsStale() {
        MainActivity mainActivity = getParentActivity();
        if (mainActivity != null)
            mainActivity.setBackdrop(R.drawable.bg_default);
        searchBox.setGravity(Gravity.START);
        searchIcon.setVisibility(View.VISIBLE);
        if (!searchBox.hasFocus()) {
            clearSearch.setVisibility(View.INVISIBLE);
        }
    }

    private void showSearchResults(SearchResponse searchResponse) {
        makeViewsStale();
        SearchAdapter adapter = new SearchAdapter(searchResponse.getHits(), this);
        searchResultList.setAdapter(adapter);
        ConstraintSet searching = new ConstraintSet();
        searching.clone(context, R.layout.fragment_search_results);
        TransitionManager.beginDelayedTransition(constraintLayout);
        searching.applyTo(constraintLayout);
    }

    @Override
    public void showError(Throwable error) {
        ConstraintSet searching = new ConstraintSet();
        String errorString = error.getMessage();
        errorText.setText(errorString);
        searching.clone(context, R.layout.fragment_search_error);
        TransitionManager.beginDelayedTransition(constraintLayout);
        searching.applyTo(constraintLayout);
    }

    @OnClick(R.id.search_box_clear)
    void onSearchClear() {
        searchBox.setText("");
    }

    @Override
    public String getTagId() {
        return TAG;
    }

    @Override
    public void showData(SearchResponse searchResponse) {
        if (searchResponse.getHits().isEmpty()) {
            // No search results
            String errorString = String.format(
                    getString(R.string.error_no_results), getSearchQuery()
            );
            showError(new Throwable(errorString));
        } else {
            showSearchResults(searchResponse);
        }
    }

    @Override
    public void onResultClick(SearchResult searchResult) {
        Navigation.findNavController(rootView).navigate(
                R.id.action_open_song,
                SongFragment.makeBundle(searchResult.getId(), searchResult.getHeaderImageUrl())
        );
    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int heightDiff = rootView.getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff <= 500) {
                // Keyboard is gone X_X
                searchBox.clearFocus();
            }
        }
    };

    @Override
    public void onDestroyView() {
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        rootView = null;
        inputMethodManager = null;
        super.onDestroyView();
        unbinder.unbind();
    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

}
