package engineer.davidauza.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link NewsAdapter} is a {@link RecyclerView.Adapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link News} objects.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    /**
     * The ArrayList containing the list of news to display.
     */
    private ArrayList<News> mNews;

    /**
     * The context of the MainActivity
     */
    private Context mContext;

    /**
     * Create a new {@link NewsAdapter} object.
     *
     * @param pNews    is the ArrayList containing the list of news to display.
     * @param pContext is the Context of the MainActivity
     */
    public NewsAdapter(ArrayList<News> pNews, Context pContext) {
        mNews = pNews;
        mContext = pContext;
    }

    /**
     * Create new Views (invoked by the layout manager).
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create a new View
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.item_news, viewGroup, false);
        return new MyViewHolder(view);
    }

    /**
     * Replace the contents of a View (invoked by the layout manager).
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Get element from the dataset at this position
        News currentNews = mNews.get(i);

        // Replace the contents of the View with that element
        myViewHolder.mTitleTextView.setText(currentNews.getTitle());
        String date = currentNews.getDate();
        if (date != null) {
            myViewHolder.mDateTextView.setText(date);
        } else {
            myViewHolder.mDateTextView.setText(mContext.getString(R.string.no_date));
        }
        String author = currentNews.getAuthor();
        if (author != null) {
            myViewHolder.mAuthorTextView.setText(author);
        } else {
            myViewHolder.mAuthorTextView.setText(mContext.getString(R.string.no_author));
        }
        myViewHolder.mSectionTextView.setText(currentNews.getSection());

        setOnClickListener(myViewHolder, currentNews);
    }

    /**
     * Return the size of the dataset (invoked by the layout manager).
     */
    @Override
    public int getItemCount() {
        return mNews.size();
    }

    /**
     * This method sets an OnClickListener for the elements of the list.
     */
    private void setOnClickListener(MyViewHolder pViewHolder,
                                    final News pCurrentNews) {
        pViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pCurrentNews.getLink()));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Provide a reference to the Views for each data item.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * The LinearLayout which contains the layout for each list item.
         */
        public View mView;

        /**
         * The TextView which displays the title of the news.
         */
        public TextView mTitleTextView;

        /**
         * The TextView which displays the date of the news.
         */
        public TextView mDateTextView;

        /**
         * The TextView which displays the author of the news.
         */
        public TextView mAuthorTextView;

        /**
         * The TextView which displays the section of the news.
         */
        public TextView mSectionTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTitleTextView = itemView.findViewById(R.id.txt_title);
            mDateTextView = itemView.findViewById(R.id.txt_date);
            mAuthorTextView = itemView.findViewById(R.id.txt_author);
            mSectionTextView = itemView.findViewById(R.id.txt_section);
        }
    }
}
