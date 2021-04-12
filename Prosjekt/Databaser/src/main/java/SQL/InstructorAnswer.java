package SQL;

/**
 * Class representing the SQL Database table InstructorAnswer.
 * This is an answer to a post, done by an Instructor. 
 * Contains a PostID the Answer is connected to.
 * A userID of the user that created it.
 * The content of the answer.
 * The number of likes the answer has.
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class InstructorAnswer {
    int postID, userID, likes;
    String content;
    
    SQLConnector connector = new SQLConnector();

    /**
     * Initializes an InstructorAnswer with the provided variables.
     * Likes is set to 0.
     * 
     * @param postID
     * @param userID
     * @param content
     */
    public InstructorAnswer(int postID, int userID, String content){
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        this.likes = 0;
    }

    /**
     * Initializes an InstructorAnswer with the provided variables
     * @param postID
     * @param userID
     * @param content
     * @param likes
     */
    public InstructorAnswer(int postID, int userID, String content, int likes){
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        this.likes = likes;
    }

    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues(){
        return "INSERT INTO InstructorAnswer (PostID, UserID, content, Likes) "+
        "VALUES ('" + this.postID +"', '" + this.userID + "', '" + this.content + 
        "', '" + this.likes + "') ON DUPLICATE KEY UPDATE content = '" + this.content + "'";
    }

    /** 
     * @return printabel String
     */
    public String toString() {
        return "PostID: " + this.postID + " UserID: " + this.userID + 
        " Content: " + this.content +
        " Likes: " + this.likes;
    }

    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert() {
        this.connector.insert(this.getValues());
    }

    /**
     * returns the userID of the poster
     * @return userID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * returns the content of the answer
     * @return content
     */
    public String getContent() {
        return this.content;
    }

    public static void main(String[] args) {
        InstructorAnswer sa = new InstructorAnswer(1,1, "svar på nesten alt");
        sa.insert();
        System.out.println(sa.toString());
    }

    
}
