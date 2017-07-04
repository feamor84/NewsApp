package pl.bartekpawlowski.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
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

    // errors handler
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindString(R.string.list_is_empty)
    String listEmpty;
    @BindString(R.string.no_internet_connection)
    String noInternetConnection;

    // internet connection
    private boolean isNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views with ButterKnife
        ButterKnife.bind(this);

        isNetwork = isInternetAvailable(this);
        if (!isNetwork) {
            mErrorTextView.setText(noInternetConnection);
        }

        mNewsListView.setEmptyView(mErrorTextView);

        mNewsListAdapter = new NewsAdapter(this, new ArrayList<News>());
        mNewsListView.setAdapter(mNewsListAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // show progress bar
        if (mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (!isInternetAvailable(this)) {
            mErrorTextView.setText(noInternetConnection);
            mProgressBar.setVisibility(View.GONE);
            return null;
        }

        Log.i(LOG_TAG, "onCreateLoader started");
        return new NewsLoader(this, "");
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        // hide progress bar
        mProgressBar.setVisibility(View.GONE);

        // handle empty list
        if (data.isEmpty()) {
            mErrorTextView.setText(listEmpty);
        }

        Log.i(LOG_TAG, "onLoadFinished started");
        mNewsListAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsListAdapter.clear();
    }

    private static boolean isInternetAvailable(Context cxt) {

        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            Log.i("NetworkStatus :", "Network connection available.");
            return true;
        }

        return false;
    }
}
