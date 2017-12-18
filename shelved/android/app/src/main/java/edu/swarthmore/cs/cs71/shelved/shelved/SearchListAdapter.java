package edu.swarthmore.cs.cs71.shelved.shelved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.koushikdutta.ion.Ion;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<SimpleBook> {

    public SearchListAdapter(Context context, List<SimpleBook> bookList) {
        super(context, R.layout.search_result_item, bookList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.search_result_item, null);
        }
        SimpleBook book = getItem(position);
        TextView title = (TextView) convertView.findViewById(R.id.title_book);
        TextView author = (TextView) convertView.findViewById(R.id.author_book);
        ImageView cover = (ImageView) convertView.findViewById(R.id.cover);
        ImageButton addButton = (ImageButton) convertView.findViewById(R.id.add_from_search);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
            }
        });

        title.setText(book.getTitle().getTitle());
        author.setText(book.getAuthor().getAuthorName());

        //Ion.with(cover).load(book.getImageUrl());
        Ion.with(cover).placeholder(R.mipmap.logo).error(R.mipmap.logo).load(book.getImageUrl());

        //cover.setImageResource(R.mipmap.logo);
        return convertView;
    }


}