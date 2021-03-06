package SQL;


/**
 * Class representing the SQL Database table Course. 
 * Contains a CourseNumber, CourseID, CourseName, Term and Year.
 *  
 * Also has its own SQLConnector @see SQLConnector
 */
public class CreateCourse {
    int CourseNumber;
    String CourseID;
    String CourseName;
    String Term;
    int Year;
    SQLConnector connector = new SQLConnector();


    /**
     * Initialize a Course with the provided variables. 
     * CourseNumber is generated by the next available Number from the database.
     * This is done by the SQLConnector.
     * The Course is added to the database with the SQLConnector, this is also printed
     * 
     * @param CourseID String of the CourseID, f.eks. "TDT4145"
     * @param CourseName String of the Course name, f.eks. "Datamodellering og databasesystemer"
     * @param Term String of the term, f.eks. "spring"
     * @param Year int of the year, f.eks. 2020
     */
    public CreateCourse(String CourseID, String CourseName, String Term, int Year) {
        this.CourseNumber = connector.getNextID("CourseNumber", "Course");
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Term = Term;
        this.Year = Year;

        this.connector.insert("INSERT INTO Course (CourseNumber, CourseID, CourseName, Term, Year) " + "VALUES " + "("
                + "'" + this.CourseNumber + "'," + "'" + this.CourseID + "', " + "'" + this.CourseName + "', " + "'"
                + this.Term + "', " + "'" + this.Year + "')");

        System.out.println("Course has been created: " + this.CourseID + " " + this.CourseName + " " + this.Term + " "
                + this.Year);

    }

    public static void main(String[] args) {
        CreateCourse course = new CreateCourse("TDT4100", "OOP", "Autumn", 2020);
    }

}
