package pl.bartekpawlowski.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // ArrayList members
    private ArrayList<News> mNewsList;
    private @BindView(R.id.list) ListView mNewsListView;
    private NewsAdapter mNewsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views with ButterKnife
        ButterKnife.bind(this);

        mNewsListAdapter = new NewsAdapter(this, new ArrayList<News>());

    }
}
