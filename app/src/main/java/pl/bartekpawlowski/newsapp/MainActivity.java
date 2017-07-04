package pl.bartekpawlowski.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    // constants
    private final static int LOADER_ID = 1;
    @BindView(R.id.list)
    ListView mNewsListView;
    // ArrayList members
    private ArrayList<News> mNewsList;
    private NewsAdapter mNewsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views with ButterKnife
        ButterKnife.bind(this);

        mNewsListAdapter = new NewsAdapter(this, new ArrayList<News>());
        mNewsListView.setAdapter(mNewsListAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public android.content.Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, "");
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> newses) {
        mNewsListAdapter.addAll(newses);
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        mNewsListAdapter.clear();
    }
}
