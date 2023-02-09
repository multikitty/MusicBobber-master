package com.cleveroad.audiowidget.example;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter for list of tracks.
 */
class MusicFilter extends BaseFilter<MusicItem> {

    public MusicFilter(int highlightColor) throws AssertionError {
        super(highlightColor);
    }

    @NonNull
    @Override
    protected FilterResults performFilteringImpl(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (TextUtils.isEmpty(constraint) || TextUtils.isEmpty(constraint.toString().trim())) {
            results.count = -1;
            return results;
        }
        String str = constraint.toString().trim();
        List<MusicItem> result = new ArrayList<>();
        int size = getNonFilteredCount();
        for (int i = 0; i < size; i++) {
            MusicItem item = getNonFilteredItem(i);
            if (
                    check(str, item.title())
                            || check(str, item.album())
                            || check(str, item.artist())
                    ) {
                result.add(item);
            }
        }
        results.count = result.size();
        results.values = result;
        return results;
    }

    private boolean check(@NonNull String what, @Nullable String where) {
        if (TextUtils.isEmpty(where))
            return false;
        where = where.toLowerCase();
        what = what.toLowerCase();
        return where.contains(what);
    }
}
