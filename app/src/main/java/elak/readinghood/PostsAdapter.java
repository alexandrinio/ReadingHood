package elak.readinghood;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import elak.readinghood.backend.api.AppManager;
import elak.readinghood.backend.posts.Post;
import elak.readinghood.backend.threads.Thread;


/**
 * Created by user on 26/10/2017.
 */

public class PostsAdapter extends ArrayAdapter<Post> {

    Thread currentThread;

    public PostsAdapter(Activity context, ArrayList<Post> posts, Thread currentThread) {
        super(context, R.layout.expanded_list, posts);
        this.currentThread = currentThread;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.expanded_item, parent, false);
        }

        if (position == 0) { // This is a question

            TextView t1 = (TextView) listItemView.findViewById(R.id.title1);
            t1.setText(currentThread.getTitle());
            t1.setVisibility(View.VISIBLE);

            TextView t4 = (TextView) listItemView.findViewById(R.id.tags1);
            t4.setText(currentThread.getHashTags().toString());
            t1.setVisibility(View.VISIBLE);

        }

        TextView t2 = (TextView) listItemView.findViewById(R.id.username1);
        t2.setText(post.getAuthor().getUsername());

        TextView t3 = (TextView) listItemView.findViewById(R.id.query1);
        t3.setText(post.getText());

        final TextView t5 = (TextView) listItemView.findViewById(R.id.votes1);
        t5.setText(post.getNumberOfVotes());


        ImageView upvoteView = (ImageView) listItemView.findViewById(R.id.upvote);
        upvoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    post.upVoteThisPost();
                    t5.setText(Integer.parseInt(t5.getText().toString()) + 1);
                } catch (IOException e) {
                    //TOST
                }
            }
        });

        ImageView downvoteView = (ImageView) listItemView.findViewById(R.id.downvote);
        downvoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    post.downVoteThisPost();
                    t5.setText(Integer.parseInt(t5.getText().toString()) - 1);
                } catch (IOException e) {
                    //TOST
                }
            }
        });
        return listItemView;


    }
}