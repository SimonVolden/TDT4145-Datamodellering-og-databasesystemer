package SQL;


/**
 * Class representing the SQL Database table PostRead. 
 * This table tells the system is the user has read the post
 * Contains a UserID and a PostID.
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class PostRead {
    int userID, postID;
    SQLConnector connector = new SQLConnector();

    /**
     * Initializes an PostRead with the provided variables
     */
    public PostRead(int userID, int postID) {
        this.userID = userID;
        this.postID = postID;
    }
    
    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues(){
        return "INSERT INTO PostRead (UserID, PostID) VALUES ('" +
        this.userID + "', '" + this.postID + "')";
    }

    /** 
     * @return printabel String
     */
    public String toString(){
        return "UserID: " + this.userID + " PostID: " + this.postID;
    }

    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert(){
        this.connector.insert(this.getValues());
    }
    public static void main(String[] args) {
        PostRead read = new PostRead(1, 1);
        read.insert();
        System.out.println(read.toString());
    }
}
