package edu.swarthmore.cs.cs71.shelved.shelved;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookInfoFragment extends Fragment {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String imageUrl;
    private int pages;
    private ImageButton addBook;
    private SimpleBook simpleBook;

    List<SimpleBook> recommendedBooks;

    ListView recList;
    BookListAdapter bookListAdapter;

    public static BookInfoFragment newInstance(SimpleBook simpleBook) {
        Log.d("AUTHOR IN INFO FRAG", simpleBook.getAuthor().getAuthorName());
        Bundle bundle = new Bundle();
        bundle.putString("author", simpleBook.getAuthor().getAuthorName());
        bundle.putString("title", simpleBook.getTitle().getTitle());
        bundle.putString("genre", simpleBook.getGenre().getGenre());
        bundle.putString("image_url", simpleBook.getImageUrl());
        bundle.putString("publisher", simpleBook.getPublisher().getPublisher());
        bundle.putInt("pages", simpleBook.getPages());
        bundle.putString("isbn", simpleBook.getISBN().getISBN());
        bundle.putString("simpleBook", new Gson().toJson(simpleBook));
        BookInfoFragment fragment = new BookInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setArguments(Bundle bundle) {
        this.author = bundle.getString("author");
        this.title = bundle.getString("title");
        this.genre = bundle.getString("genre");
        this.publisher = bundle.getString("publisher");
        this.imageUrl = bundle.getString("image_url");
        this.pages = bundle.getInt("pages");
        this.isbn = bundle.getString("isbn");
        this.simpleBook = new Gson().fromJson(bundle.getString("simpleBook"), SimpleBook.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_info, container, false);
        //Log.d("BOOK INFO", book.getAuthor().getAuthorName());
        addBook = (ImageButton)rootView.findViewById(R.id.add_book);
        setFieldsFromBook(rootView);
        Log.d("ABOUT TO SET REC LIST", "");
        recList = (ListView) rootView.findViewById(android.R.id.list);
        return rootView;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_main, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //do a string request for rec books
        Continuation<List<SimpleBook>> continuationRecs = new Continuation<List<SimpleBook>>() {
            @Override
            public void run(List<SimpleBook> books) {
                //TODO it should have a similar format to what's below, but not exactly because this old code is dealing with search view fragment.
                Log.d("Continuation",books.toString());

                recommendedBooks = books;
                recList.setAdapter(new BookListAdapter(getContext(), recommendedBooks));
            }
        };

        recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SimpleBook book = (SimpleBook)adapterView.getItemAtPosition(position);
                //setFieldsFromBook(book, view);
                Log.d("AUTHOR Book info FRAG", book.getAuthor().getAuthorName());
                Fragment fragment = BookInfoFragment.newInstance(book);
                replaceFragment(fragment);
            }
        });



        AppSingleton.getInstance(getContext()).getModel(getContext()).getRecs(getContext(),this.isbn, continuationRecs);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show AddBookDialog
                //AddBookDialog alert = new AddBookDialog(getContext());
                // TODO: Intent
                Log.d("Book info fragment", "show add book dialog");
                Log.d("Book info fragment", "called newInstance");
                AddBookAnywhereDialog alert = new AddBookAnywhereDialog(getContext(),simpleBook);
                alert.show();
            }
        });
    }

    public void setFieldsFromBook(View view) {
        ImageView coverImage = (ImageView) view.findViewById(R.id.book_cover);
        Ion.with(coverImage).placeholder(R.mipmap.logo).error(R.mipmap.logo).load(imageUrl);

        TextView titleText = (TextView) view.findViewById(R.id.book_title);
        titleText.setText(this.title);

        TextView authorText = (TextView) view.findViewById(R.id.book_author);
        authorText.setText(this.author);

        TextView genreText = (TextView) view.findViewById(R.id.book_genre);
        String genreDisplayText = "Genre: " + this.genre;
        genreText.setText(genreDisplayText);

        TextView publisherText = (TextView) view.findViewById(R.id.book_publisher);
        String pubDisplayText = "Publisher: " + this.publisher;
        publisherText.setText(pubDisplayText);

        TextView pagesText = (TextView) view.findViewById(R.id.book_pages);
        String pageDisplayText = "Pages: " + Integer.toString(this.pages);
        pagesText.setText(pageDisplayText);

        TextView isbnText = (TextView) view.findViewById(R.id.book_isbn);
        String isbnDisplayText = "ISBN: " + this.isbn;
        isbnText.setText(isbnDisplayText);
    }
}
