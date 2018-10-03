package com.my.shishir.demoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.shishir.demoapp.model.MainData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.my.shishir.demoapp.utility.Utility.imageRounder;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private final MainData newsListData;
    private Picasso picasso;
    private NewsListContract.MainPresenter mainPresenter;

    NewsListAdapter(MainData mainData, MainPresenterImpl mainPresenter) {
        this.newsListData = mainData;
        this.mainPresenter = mainPresenter;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        ButterKnife.bind(this, view);

        // This is here because we do not want to use context from views or activity instead we can
        // get it from the view
        if (picasso == null) {
            picasso = new Picasso.Builder(parent.getContext()).build();
        }
        return new NewsViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        final Context context = holder.newsLayout.getContext();
        final int adapterPosition = holder.getAdapterPosition();

        final String title = newsListData.getResults().get(adapterPosition).getTitle();
        final String description = newsListData.getResults().get(adapterPosition).getAbstract();

        Object imageUrl = newsListData.getResults()
                .get(adapterPosition).getMedia().get(0).getMediaMetadata().get(0).getUrl();
        imageUrl = imageUrl != null ? imageUrl : "";

        holder.titleTextView.setText(!TextUtils.isEmpty(title) ? title
                : context.getString(R.string.no_title));
        holder.dateTextView.setText(newsListData.getResults()
                .get(adapterPosition).getPublishedDate());
        holder.text_description.setText(!TextUtils.isEmpty(description) ? description
                : context.getString(R.string.no_description));
        holder.newsImage.setImageDrawable(context
                .getResources().getDrawable(R.drawable.ic_launcher_background, context.getTheme()));

        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.onClick(newsListData.getResults().get(adapterPosition).getUrl());
            }
        });

        // This is the default visibility to avoid flickering of images or may be miss positioning
        // of them while loading or scrolling.
        holder.newsImage.setVisibility(View.GONE);

        final Object finalImageUrl = imageUrl;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty((String) finalImageUrl)) {
                    holder.newsImage.setVisibility(View.GONE);
                } else {
                    loadImage((String) finalImageUrl, holder.newsImage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Object rows = newsListData.getResults();
        return rows == null ? 0 : ((List) rows).size();
    }

    // Picasso load images only when the view is visible, So it can be like
    // lazy loading or loading when required
    private void loadImage(String finalImageUrl, final ImageView imageView) {
        picasso.load(finalImageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                        imageRounder(((BitmapDrawable) imageView.getDrawable()).getBitmap(),
                                imageView.getResources(),
                                new ProcessedBitmapListener() {
                                    @Override
                                    public void onProcessDone(RoundedBitmapDrawable roundedBitmapDrawable) {
                                        imageView.setImageDrawable(roundedBitmapDrawable);
                                    }
                                });
                    }

                    @Override
                    public void onError(Exception e) {
                        imageView.setVisibility(View.GONE);
                    }
                });
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_news_item)
        CardView newsLayout;

        @BindView(R.id.text_title)
        TextView titleTextView;

        @BindView(R.id.text_description)
        TextView text_description;

        @BindView(R.id.image_news)
        ImageView newsImage;

        @BindView(R.id.text_date)
        TextView dateTextView;

        NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface ProcessedBitmapListener {
        void onProcessDone(RoundedBitmapDrawable roundedBitmapDrawable);
    }
}