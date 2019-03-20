package com.k2.musicdb.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.k2.musicdb.R;

import androidx.annotation.Nullable;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 19/03/19
 */

public class DescriptionView extends LinearLayout {

    private static final int MAX_LINES_COLLAPSED = 5;

    private TextView description;
    private TextView readMore;
    private boolean collapsed = true;

    public DescriptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.expandable_textview, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DescriptionView, 0, 0);

        int gravity = a.getColor(R.styleable.DescriptionView_moreHandleGravity, 0);
        a.recycle();

        description = view.findViewById(R.id.description_text);
        readMore = view.findViewById(R.id.action_read_more);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;

        switch (gravity) {
            case 0:
                params.gravity = Gravity.START;
                break;
            case 1:
                params.gravity = Gravity.END;
                break;
            case 2:
                params.gravity = Gravity.CENTER;
                break;
        }

        readMore.setLayoutParams(params);

        OnClickListener clickListener = v -> {
            updateState(isCollapsed());
        };
        readMore.setOnClickListener(clickListener);
        description.setOnClickListener(clickListener);
    }

    private void updateState(boolean collapsed) {
        collapseText(collapsed);
        readMore.setText(collapsed ? R.string.read_less : R.string.read_more);
    }

    private boolean isCollapsed() {
        return description.getMaxLines() == MAX_LINES_COLLAPSED;
    }

    public void setDescription(String descriptionText) {
        description.setText(descriptionText);
        int maxLines = description.getLineCount();
        if (maxLines == 0 || maxLines > MAX_LINES_COLLAPSED) {
            readMore.setVisibility(VISIBLE);
            collapseText(false);
        } else {
            readMore.setVisibility(GONE);
            collapseText(true);
        }
        updateState(!collapsed);
    }

    private void collapseText(boolean collapsed) {
        description.setMaxLines(collapsed ? Integer.MAX_VALUE : MAX_LINES_COLLAPSED);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.expanded = !isCollapsed();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        updateState(ss.expanded);
        collapsed = !ss.expanded;
    }

    static class SavedState extends BaseSavedState implements Parcelable {

        boolean expanded = false;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            expanded = in.readByte() != 0;
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeByte((byte) (expanded ? 1 : 0));
        }
    }

}
