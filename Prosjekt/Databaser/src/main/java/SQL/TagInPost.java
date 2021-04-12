package SQL;


/**
 * Class representing the SQL Database table TagInPost.
 * the table contains the posts that has been given a tag.
 * Contains a tagID and a PostID
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class TagInPost {
    int tagID, postID;
    SQLConnector connector = new SQLConnector();


    /**
     * Initializes a TagInPost with the provided TagID and PostID
     * @param tagID
     * @param postID
     */
    public TagInPost(int tagID, int postID){
        this.tagID = tagID;
        this.postID = postID;
    }

    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues() {
        return "INSERT INTO TagInPost (TagID, PostID) VALUES ('"
        + this.tagID + "', '" + this.postID + "')";
    }

    /** 
     * @return printabel String
     */
    public String toString(){
        return "TagID: " + this.tagID + " PostID: " + this.postID;
    }
    
    /**
    * uses the SQLConnector to insert the class into the database.
    */
    public void insert(){
        this.connector.insert(this.getValues());
    }
    
    public static void main(String[] args) {
        TagInPost tagInPost = new TagInPost(1, 1);
        tagInPost.insert();
        System.out.println(tagInPost.toString());
    }
}
