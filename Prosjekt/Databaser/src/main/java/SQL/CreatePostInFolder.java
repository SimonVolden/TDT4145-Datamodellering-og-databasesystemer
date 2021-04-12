package SQL;

import java.net.ConnectException;



/** 
 * Class representing the SQL Database table PostInFolder. Contains a FolderID and a PostID.
 * This represents a Folder an a Post in that Folder.
 * 
 * Also has its own SQLConnector @see SQLConnector
 */
public class CreatePostInFolder {
    int FolderID, PostID;
    SQLConnector connector = new SQLConnector();

    /**
     * Initialize a PostInFolder with the provided variables.
     * Places the post in the folder.
     * @param FolderID 
     * @param PostID 
     */
    public CreatePostInFolder(int FolderID, int PostID){
        this.FolderID = FolderID;
        this.PostID = PostID;
        
    }
    
    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert(){
        this.connector.insert(this.getValues());
    }

    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues() {
        return "INSERT INTO PostInFolder (FolderID, PostID) VALUES ('" + this.FolderID + "', '" + this.PostID + "')";
    }
    
    /** 
     * @return printabel String
     */
    public String toString(){
        return "FolderID: " + this.FolderID + " PostID: " + this.PostID;
    }

    public static void main(String[] args) {
        CreatePostInFolder postInFolder = new CreatePostInFolder(1, 1);
        postInFolder.insert();
    }
}
