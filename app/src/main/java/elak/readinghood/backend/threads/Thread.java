package elak.readinghood.backend.threads;

import elak.readinghood.backend.posts.Post;
import elak.readinghood.backend.posts.Posts;
import elak.readinghood.backend.server.ServerRequest;
import elak.readinghood.backend.hashTags.HashTags;
import elak.readinghood.backend.profiles.Profile;
import elak.readinghood.backend.server.ServerUpdate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Spiros
 */
public class Thread implements Serializable {
    private int id, views;
    private String title;
    private HashTags hashTags;
    private Post questionPost;
    private Posts answerPosts;

    /**
     * Trivial constructor
     *
     * @param id           is the id of the thread
     * @param title        is the title of the thread
     * @param views        are the amount of views of the thread
     * @param hashTags     are the hashTags of the thread
     * @param questionPost the question post of the thread
     * @param answerPosts  are the answerPosts of the thread
     */
    public Thread(int id, String title, int views, HashTags hashTags, Post questionPost, Posts answerPosts) {
        this.id = id;
        this.views = views;
        this.title = title;
        this.hashTags = hashTags;
        this.questionPost = questionPost;
        this.answerPosts = answerPosts;
    }

    /**
     * @return the user that created the thread
     */
    public Profile getThreadCreatorProfile() {
        return questionPost.getAuthor();
    }

    /**
     * YOU NEVER USE THIS FUNCTION as front end developer.
     *
     * @return the id of the thread
     */
    public int getId() {
        return id;
    }

    /**
     * @return the views of the thread
     */
    public int getViews() {
        return views;
    }

    /**
     * @return the title of the thread
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the hashTags of the thread
     */
    public HashTags getHashTags() {
        return hashTags;
    }

    /**
     * @return the question post of the creator the thread
     */
    public Post getQuestionPost() {
        return questionPost;
    }

    /**
     * @return the answerPosts of the thread
     */
    public Posts getAnswerPosts() {
        return answerPosts;
    }

    /**
     * This function tells if you can answer a thread or not.
     * According to the requirements, if the number of votes a question <= -50 then you can not answer it
     * cause it is counted as "blocked".
     *
     * @return a boolean value which indicated if you answer a thread or not, or in other words if a thread is "blocked"
     */
    public boolean canYouAnswerThisThread() {
        return questionPost.getNumberOfVotes() > -50;
    }

    /**
     * This function answers a thread and returns an error text.
     * <p>
     * Error0 = "Success"
     * Error1 = "Fill the fields"
     *
     * @param text is the text of the post that you are gonna write
     * @return an error text
     * @throws IOException Can not Connect to server
     */
    public String answerThreadWithAPost(String text) throws IOException {
        boolean textFullOfSpaces = text.replaceAll("\\s+", "").isEmpty();
        if (text.isEmpty() || textFullOfSpaces) {
            return "Fill the fields";
        }

        ServerUpdate.answerThread(id, text);
        answerPosts.addPost(ServerRequest.getLatestPostOfAThread(id));
        return "Success";
    }

    /**
     * NEVER USE THIS FUNCTION.
     * This function increase the views of a thread.
     */
    public void increaseViews() {
        views++;
    }

    /**
     * You ONLY USE THIS FUNCTION IF YOU HAVE ADDED A POST
     *
     * @return the latest added answer post
     */
    public Post getTheLatestAddedPost() {
        if (answerPosts.size() == 1) {
            return answerPosts.getPost(0);
        } else {
            return answerPosts.getPost(answerPosts.size() - 1);
        }
    }

    /**
     * @return the list of posts as an arrayList
     */
    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> allPosts = new ArrayList<>();
        allPosts.add(questionPost);
        allPosts.addAll(answerPosts.getListOfPosts());
        return allPosts;
    }
}