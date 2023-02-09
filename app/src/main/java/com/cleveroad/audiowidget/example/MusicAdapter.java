package com.cleveroad.audiowidget.example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

/**
 * Adapter for list of tracks.
 */
class MusicAdapter extends BaseRecyclerViewAdapter<MusicItem, MusicAdapter.MusicViewHolder> {

    public MusicAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        MusicItem item = getItem(position);
        holder.title.setText(getFilter().highlightFilteredSubstring(item.title()));
        holder.artist.setText(getFilter().highlightFilteredSubstring(item.artist()));
        holder.album.setText(getFilter().highlightFilteredSubstring(item.album()));
        holder.duration.setText(convertDuration(item.duration()));
        Glide.with(getContext())
                .asBitmap()
                .load(item.albumArtUri())
                .apply((new RequestOptions()).circleCrop())
                .placeholder(R.drawable.aw_ic_default_album)
                .error(R.drawable.aw_ic_default_album)
                .into(holder.albumCover);
    }

    private String convertDuration(long durationInMs) {
        long durationInSeconds = durationInMs / 1000;
        long seconds = durationInSeconds % 60;
        long minutes = (durationInSeconds % 3600) / 60;
        long hours = durationInSeconds / 3600;
        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView artist;
        TextView album;
        TextView duration;
        ImageView albumCover;

        public MusicViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            album = itemView.findViewById(R.id.album);
            duration = itemView.findViewById(R.id.duration);
            albumCover = itemView.findViewById(R.id.album_cover);
        }
    }
}
