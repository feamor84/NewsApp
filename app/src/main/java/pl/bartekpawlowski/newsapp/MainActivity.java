package pl.bartekpawlowski.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    // constants
    private final static int LOADER_ID = 1;
    private final static String LOG_TAG = "Main Activity";

    // ArrayList members
    @BindView(R.id.list)
    ListView mNewsListView;
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
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader started");
        return new NewsLoader(this, "world");
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        Log.i(LOG_TAG, "onLoadFinished started");
        mNewsListAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsListAdapter.clear();
    }
}
