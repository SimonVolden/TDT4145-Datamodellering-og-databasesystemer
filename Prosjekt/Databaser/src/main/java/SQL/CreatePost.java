package SQL;


/**
 * Class representing the SQL Database table Post. 
 * Contains a PostID, UserID of the post creator, a Title, the content, likes 
 * and if the creator wants to be anonymous.
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class CreatePost {
    int postID;
    int userID;
    String title;
    String content;
    int likes;
    int anonymous;
    SQLConnector connector = new SQLConnector();


    /**
     * Initialize a Post with the provided variables. 
     * anonymous is converted to int depending on the input.
     * likes is set to 0.
     * 
     * @param postID int, unique ID for the post
     * @param userID int, unique ID for the creator
     * @param title String, the post title
     * @param content String, the content of the post
     * @param anonymous boolean, if the poster is anonymous or not 
     */
    public CreatePost(int postID, int userID, String title, String content, boolean anonymous) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.likes = 0;
        if (anonymous)
            this.anonymous = 1;
        else
            this.anonymous = 0;
        
    }


    /**
     * Initialize a Post with the provided variables. anonymous is converted to int depending on the input.
     * PostID is not set
     * @param userID
     * @param title
     * @param content
     * @param anonymous
     */
    public CreatePost(int userID, String title, String content, boolean anonymous) {
        this.postID = this.connector.getNextID("PostID", "Post");
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.likes = 0;
        if (anonymous)
            this.anonymous = 1;
        else
            this.anonymous = 0;
        
    }


    /**
     * Gets the PostID of the current post
     * @return int postID
     */
    public int getPostID() {
        return this.postID;
    }


    /**
     * Gets the UserID of the current post
     * @return int UserID
     */
    public int getUserID() {
        return this.userID;
    }


    /**
     * Gets the Title of the post
     * @return String Title
     */
    public String getTitle() {
        return this.title;
    }


    /**
     * Gets the content of the post
     * @return String content
     */
    public String getContent() {
        return this.content;
    }


    /**
     * Gets the boolean anonymous value of the post, this is converted from the int variable Anonymous.
     * @return Boolean 
     */
    public Boolean getAnonymous() {
        if (this.anonymous == 1) {
            return true;
        }
        return false;
    }


    /**
     * Gets the titleString used in PiazzaSceneController @see Prosjekt.PiazzaSceneController
     * 
     * @return titleString
     */
    public String titleString() {
        return "" + this.postID + ": " + this.title;
    }


    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert() {
        this.connector.insert(this.getValues());
    }


    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues() {
        return "INSERT INTO POST (PostID, UserID, Title, Content, Likes, Anonymous) " + "VALUES " + "(" + "'"
                + this.postID + "', " + "'" + this.userID + "', " + "'" + this.title + "', " + "'" + this.content
                + "', " + "'" + this.likes + "', " + "'" + this.anonymous + "')";
    }

    /** 
     * @return printabel String
     */
    public String toString() {
        return "PostID: " + this.postID + " UserID: " + this.userID + " Title: " + this.title + " Content: "
                + this.content + " Likes: " + this.likes + " Anonymous: " + this.anonymous;
    }

    public static void main(String[] args) {
        CreatePost post = new CreatePost(2, "Test5", "testcontent5", true);
        post.insert();

    }
}
