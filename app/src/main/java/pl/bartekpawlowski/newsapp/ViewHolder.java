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
    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsSection)
    TextView mNewsSection;
    @BindView(R.id.newsDate)
    TextView mNewsDate;

    public ViewHolder(@NonNull View view) {
        ButterKnife.bind(this, view);
    }
}
