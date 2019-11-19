package edu.utep.cs.cs4330.utepnews;

public class Post implements Comparable<Post> {
    public int added;
    public String author, description, file_hash, heading;

    public Post () {}

    @Override
    public int compareTo(Post p) {
        return Integer.compare(added, p.added);
    }
}
