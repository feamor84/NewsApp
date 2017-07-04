package pl.bartekpawlowski.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newses) {
        super(context, 0, newses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final News currentNews = getItem(position);

        viewHolder.mNewsTitle.setText(currentNews.getTitle());
        viewHolder.mNewsSection.setText(currentNews.getSection());
        viewHolder.mNewsDate.setText(modifyDate(currentNews.getDate()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(currentNews.getUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        return convertView;
    }

    private String modifyDate(String date) {
        StringBuffer stringBuffer = new StringBuffer(date);

        stringBuffer = stringBuffer.replace(stringBuffer.indexOf("T"), stringBuffer.indexOf("T") + 1, ", ");
        stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }
}
