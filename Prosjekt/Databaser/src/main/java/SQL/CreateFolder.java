package SQL;


/**
 * Class representing the SQL Database table Folder. 
 * Contains a FolderID, Title, CourseNumber, ParentFolderID
 * CourseNumber is a reference to the Course the folder is in.
 * ParentFolderID is a reference to the CreateFolder class that this folder may be a subfolder in.
 * 
 * Also has its own SQLConnector @see SQLConnector
 * 
 */
public class CreateFolder {
    int folderID, parentFolderID, courseNumber;
    String title;
    SQLConnector connector = new SQLConnector();

    /**
     * Initialize a Folder with the provided variables. FolderID is given by the next folderID available in the database
     * 
     * @param Title String, the name of the Folder
     * @param CourseNumber int, the Course the folder is placed into
     * @param ParentFolderID int, if not null, this will be the ParentFolder of the current folder.
     */
    public CreateFolder(String title, int courseNumber, int parentFolderID){
        this.title = title;
        this.courseNumber = courseNumber;
        this.parentFolderID = parentFolderID;
        this.folderID = this.connector.getNextID("FolderID", "Folder");
        this.insert();
        
    }


    /**
     * Initialize a Folder with the provided variables.
     *
     * @param FolderID int
     * @param Title String, the name of the Folder
     * @param CourseNumber int, the Course the folder is placed into
     * @param ParentFolderID int, if not null, this will be the ParentFolder of the current folder.
     */
    public CreateFolder(int folderID, String title, int courseNumber, int parentFolderID){
        this.folderID = folderID;
        this.title = title;
        this.courseNumber = courseNumber;
        this.parentFolderID = parentFolderID;
    }

    /**
     * uses the SQLConnector to insert the class into the database.
     */
    public void insert() {
        this.connector.insert(this.getValues());
    }

    /**
     * returns the title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * returns the folderID
     * @return folderID
     */
    public int getFolderID() {
        return this.folderID;
    }

    /** 
     * @return a String that is an insertStatement of the class variables.
    */
    public String getValues() {
        return "INSERT INTO FOLDER (FolderID, Title, courseNumber, ParentFolderID) "+
        "VALUES ('" + this.folderID + "', '" + this.title + "', '" + this.courseNumber + "', '" + this.parentFolderID + "')";
    }

    /** 
     * @return printabel String
     */
    public String toString(){
        return "FolderID: " + this.folderID + " Title: " + this.title + " courseNumber " + this.courseNumber + " ParentFolderID: " + this.parentFolderID;
    }

    public static void main(String[] args) {
        CreateFolder folder = new CreateFolder("Øving 1", 1 , 0);
        CreateFolder folder1 = new CreateFolder("Øving 2", 1 , 0);
        CreateFolder folder2 = new CreateFolder("Øving 3", 1 , 0);
        CreateFolder folder3 = new CreateFolder("Øving 4", 1 , 0);
        CreateFolder folder4 = new CreateFolder("Øving 5", 1 , 0);
        CreateFolder folder5 = new CreateFolder("Prosjekt", 1 , 0);
        CreateFolder folder6 = new CreateFolder("Eksamen", 1 , 0);
        CreateFolder folder7 = new CreateFolder("Other", 1 , 0);
        
        System.out.println(folder.toString());
    }
}
