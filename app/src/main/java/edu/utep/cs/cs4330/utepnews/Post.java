package edu.utep.cs.cs4330.utepnews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class Post implements Comparable<Post> {
    public int added;
    public String author, description, file_hash, heading;

    public Post () {}

    @Override
    public int compareTo(Post p) {
        return Integer.compare(added, p.added);
    }

    public String getDate() {
        Date date = new Date((long)added*1000);
        DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        return f.format(date);
    }
}
