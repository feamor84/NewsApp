package pl.bartekpawlowski.newsapp;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Helper class to maintain performance of ListView
 */

public class ViewHolder {
    private @BindView(R.id.newsTitle) TextView mNewsTitle;
    private @BindView(R.id.newsTitle) TextView mNewsSection;
    private @BindView(R.id.newsTitle) TextView mNewsDate;

    public ViewHolder(@NonNull View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getNewsTitle() {
        return mNewsTitle;
    }

    public TextView getNewsSection() {
        return mNewsSection;
    }

    public TextView getNewsDate() {
        return mNewsDate;
    }
}
