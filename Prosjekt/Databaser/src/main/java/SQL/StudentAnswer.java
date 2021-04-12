package SQL;


/**
 * Class representing the SQL Database table StudentAnswer.
 * This is an answer to a post, done by a Student. 
 * Contains a PostID the Answer is connected to.
 * A userID of the user that created it.
 * The content of the answer.
 * Th number of likes the answer has.
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class StudentAnswer {
    int postID, userID, likes, anonymous;
    String content;
    
    SQLConnector connector = new SQLConnector();

    /**
     * Initializes a StudentAnswer with the provided variables.
     * Likes is set to 0.
     * 
     * @param postID
     * @param userID
     * @param content
     * @param anonymous
     */
    public StudentAnswer(int postID, int userID, String content, Boolean anonymous){
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        if (anonymous) {
            this.anonymous = 1;
        }
        else {
            this.anonymous = 0;
        }
        this.likes = 0;
    }

    /**
     * Initializes a StudentAnswer with the provided variables.
     * @param postID
     * @param userID
     * @param content
     * @param anonymous
     * @param likes
     */
    public StudentAnswer(int postID, int userID, String content, Boolean anonymous, int likes){
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        if (anonymous) {
            this.anonymous = 1;
        }
        else {
            this.anonymous = 0;
        }
        this.likes = likes;
    }


    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues(){
        return "INSERT INTO StudentAnswer (PostID, UserID, content, Anonymous, Likes) "+
        "VALUES ('" + this.postID +"', '" + this.userID + "', '" + this.content
        + "', '" + this.anonymous + "', '" + this.likes + "') ON DUPLICATE KEY UPDATE content = '" + this.content + "', anonymous = " + this.anonymous;
    }
    
    /** 
     * @return printabel String
     */
    public String toString() {
        return "PostID: " + this.postID + " UserID: " + this.userID + 
        " Content: " + this.content + " Anonymous: " + this.anonymous +
        " Likes: " + this.likes;
    }
    
    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert() {
        this.connector.insert(this.getValues());
    }

    public String getContent() {
        return this.content;
    }

    public int getUserID() {
        return this.userID;
    }

    public boolean getAnonymous() {
        if (this.anonymous == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StudentAnswer sa = new StudentAnswer(1,1, "svar på alt", false);
        sa.insert();
        System.out.println(sa.toString());
    }
}
