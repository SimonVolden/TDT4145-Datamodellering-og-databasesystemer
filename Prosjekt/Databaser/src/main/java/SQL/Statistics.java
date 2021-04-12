package SQL;

/**
 * Statistics of how many posts a user has read and created. this is a helperclass to find statistics of all the users. 
 * This class is used in SQLConnector.getPostsReadStatistics()
 */
public class Statistics {
    String username;
    int postsRead;
    int postsCreated;

    /**
     * Initializes a Statistics object from the provided variables
     * @param username
     * @param postsRead
     * @param postsCreated
     */
    public Statistics(String username, int postsRead, int postsCreated) {
        this.username = username;
        this.postsRead = postsRead;
        this.postsCreated = postsCreated;
    }

    /**
     * returns the username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * returns the number of posts read
     * @return postRead
     */
    public int getPostsRead() {
        return this.postsRead;
    }

    /**
     * returns the number of posts created 
     * @return postCreated
     */
    public int getPostsCreated() {
        return this.postsCreated;
    }

    /** 
     * @return printabel String
     */
    public String toString() {
        return "" + this.username + " " + this.postsRead + " " + this.postsCreated;
    }
}
