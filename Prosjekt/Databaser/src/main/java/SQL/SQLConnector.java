package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import SQL.CreateUser;

/**
 * SQLConnector
 * This class contains much redundant code and is used to access the database "piazzadatabase"
 * Each method contains a Try-Catch-Finally block that is used to run an SQL statement.
 * 
 */
public class SQLConnector {


    /**
     * insert is used to insert a statement into the database. 
     * The statements are usually generate by the different classes that uses this method.
     * 
     * @param insertStatement the statement that will be run on the database.
     */
    public void insert(String insertStatement) {
        Statement stmt = null;
        ResultSet rs = null;

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            stmt.execute(insertStatement);
            // stmt.execute("INSERT INTO USER (UserID, Username, Email, Password) VALUES (2,
            // 'olanordmann', 'olanordmann@stud.ntnu.no', 'passord123')");
            // rs = stmt.executeQuery("select * from User");

        } catch (SQLException ex) {
            // handle any errors
            if (ex.getSQLState().equals("23000")) {
            } else {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }

    
    /**
     * Returns the number of the next ID from the given table and the name of column the IDs are placed
     * The return value is 1 more than the current biggest ID.
     * @param ID
     * @param Table
     * @return int the next available ID
     */
    public int getNextID(String ID, String Table) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT max(" + ID + ") as " + ID + " FROM " + Table);
            if (rs.next() != false) {
                int NextID = rs.getInt(ID);
                return NextID + 1;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return (Integer) null;
    }

    /**
     * Checks if the provided email and password is in the database.
     * Used in PrimaryController
     * @param email
     * @param password
     * @return true if it matches a row in the database
     */
    public Boolean loginCheck(String email, String password) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT Email, Password from User where Email = '" + email + "' and Password = '" + password + "'");
            if (rs.next() != false) {
                return true;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return false;
    }

    /**
     * Checks if the prodived email and password is not used in the database.
     * used in SignupController
     * @param email
     * @return false if it matches a row in the database
     */
    public Boolean signupCheck(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Email from User where Email = '" + email + "'");
            if (rs.next() != false) {
                return false;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return true;
    }

    /**
     * Returns the username from the provided UserID, as long as it is in the database.
     * @param userID
     * @return username of userID, null if it does not exist
     */
    public String getUserNameFromUserID(int userID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT username from User where userID = " + userID);
            if (rs.next() != false) {
                String username = rs.getString("username");
                return username;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return null;
    }

    /**
     * Returns an ArrayList that contains all the posts in the database
     * @return Arraylist of Posts
     */
    public ArrayList<CreatePost> getPosts() {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<CreatePost> posts = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from Post");
            while (rs.next()) {
                CreatePost post = new CreatePost(rs.getInt("postID"), rs.getInt("UserID"), rs.getString("Title"),
                        rs.getString("Content"), rs.getBoolean("Anonymous"));
                posts.add(post);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return posts;
    }

    /**
     * Returns an Arraylist that containts all the posts in the database that has the keyword filterString in it.
     * @param filterString the search keyword
     * @return Arraylist of Posts
     */
    public ArrayList<CreatePost> getFilteredPosts(String filterString) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<CreatePost> posts = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from Post where Title like '%" + filterString + "%' or Content like '%"
                    + filterString + "%'");
            while (rs.next()) {
                CreatePost post = new CreatePost(rs.getInt("postID"), rs.getInt("UserID"), rs.getString("Title"),
                        rs.getString("Content"), rs.getBoolean("Anonymous"));
                posts.add(post);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return posts;
    }

    /**
     * Returns the User that is logged in. Contains all the user info from the database table User
     * @param email
     * @param password
     * @return User that is logged in
     */
    public CreateUser userLoggedIn(String email, String password) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt
                    .executeQuery("SELECT * from User where Email = '" + email + "' and Password = '" + password + "'");
            if (rs.next() != false) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Username");
                boolean instructor = this.userIsInstructor(userID);
                CreateUser user = new CreateUser(userID, username, email, password, instructor);
                return user;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return null;
    }

    /**
     * Returns if the user is an instructor or not
     * @param userID
     * @return True if the user is an instructor
     */
    public boolean userIsInstructor(int userID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from Instructor where UserID = '" + userID + "'");
            if (rs.next() != false) {
                return true;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return false;
    }

    /**
     * Returns the Statistics of the Users in the database.
     * Contains an Arraylist of Arraylists.
     * The First Arraylist has all users.
     * The Second Arraylist has the number of posts the users has read
     * The Third Arraylist has the number of posts the users have created
     * @return 
     */
    public ArrayList<Statistics> getPostsReadStatistics() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Statistics> statistics = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select User.username, " + "count(distinct postread.postID) as posts, "
                    + "count(distinct post.postID) as postscreated " + "from User " + "LEFT JOIN postread "
                    + "on (postread.userID = user.userID) " + "LEFT JOIN post " + "on (post.userID = user.userID) "
                    + "group by username " + "order by posts desc");

            while (rs.next() != false) {
                String username = rs.getString("username");
                int postsRead = rs.getInt("posts");
                int postsCreated = rs.getInt("postscreated");
                Statistics stats = new Statistics(username, postsRead, postsCreated);
                statistics.add(stats);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
        return statistics;
    }

    /**
     * Returns an arraylist of all the folders in the database
     * @return folders
     */
    public ArrayList<CreateFolder> getFolders() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<CreateFolder> folders = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from Folder");
            while (rs.next()) {
                CreateFolder folder = new CreateFolder(rs.getInt("folderID"), rs.getString("Title"),
                        rs.getInt("courseNumber"), rs.getInt("parentFolderID"));
                folders.add(folder);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return folders;
    }

    /**
     * returns the studentAnswer from the given postID
     * @param postID
     * @return studentAnswer
     */
    public StudentAnswer getStudentAnswerFromPostID(int postID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from studentAnswer where postID = " + postID);
            if (rs.next() != false) {
                int postID1 = rs.getInt("PostID");
                int userID = rs.getInt("UserID");
                String content = rs.getString("content");
                boolean anonymous = rs.getBoolean("Anonymous");
                int likes = rs.getInt("likes");
                StudentAnswer sa = new StudentAnswer(postID1, userID, content, anonymous);
                return sa;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return null;
    }

    /**
     * returns the instructorAnswer from the given postID
     * @param postID
     * @return instructorAnswer
     */
    public InstructorAnswer getInstructorAnswerFromPostID(int postID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from instructorAnswer where postID = " + postID);
            if (rs.next() != false) {
                int postID1 = rs.getInt("PostID");
                int userID = rs.getInt("UserID");
                String content = rs.getString("content");
                int likes = rs.getInt("likes");
                InstructorAnswer ia = new InstructorAnswer(postID1, userID, content, likes);
                return ia;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return null;
    }

    /**
     * returns an arraylist of all the tags in the database
     * @return tags
     */
    public ArrayList<Tags> getTags() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Tags> tags = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from Tags");
            while (rs.next()) {
                Tags tag = new Tags(rs.getInt("TagID"), rs.getString("Tag"));
                tags.add(tag);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return tags;
    }

    /**
     * returns an arraylist of all the postsInFolder in the database that has the given FolderIDs and a keyword filterstring.
     * @param selectedFolderIDs
     * @param filterString
     * @return filtered list of posts
     */
    public ArrayList<CreatePost> getPostsInFolder(ArrayList<String> selectedFolderIDs, String filterString) {

        if (selectedFolderIDs.size() > 0) {
            Statement stmt = null;
            ResultSet rs = null;
            Connection conn = null;
            ArrayList<CreatePost> posts = new ArrayList<>();
            String selectedFolderIDString = "";

            Iterator<String> folderIterator = selectedFolderIDs.iterator();
            while (folderIterator.hasNext()) {
                String temp = folderIterator.next();
                selectedFolderIDString = selectedFolderIDString + temp + ", ";
            }
            selectedFolderIDString = selectedFolderIDString.substring(0, selectedFolderIDString.length() - 2);

            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                        "databaser124");

                stmt = conn.createStatement();
                rs = stmt.executeQuery("select distinct PostID, UserID, Title, Content, Anonymous, Likes from post "
                        + "natural join postinfolder " + "where postinfolder.folderID in (" + selectedFolderIDString
                        + ")" + "and ( post.Title like '%" + filterString + "%' or Content like '%" + filterString
                        + "%' )" + "group by PostID");
                while (rs.next()) {
                    CreatePost post = new CreatePost(rs.getInt("postID"), rs.getInt("UserID"), rs.getString("Title"),
                            rs.getString("Content"), rs.getBoolean("Anonymous"));
                    posts.add(post);
                }

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());

            } finally {

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }

            }
            return posts;
        } else {
            return null;
        }

    }

    /**
     * returns the next sequence number from followupDiscussion.
     * the sequencenumber must be unique from the given postID
     * @param postID
     * @return nextSequenceNumber
     */
    public int getNextSequenceNumber(int postID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT max(SequenceNumber + 1) as 'next' FROM followupdiscussion WHERE postID = '" + postID + "'");
            if (rs.next() != false) {
                int NextID = rs.getInt("next");
                return NextID;
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return 0;
    }

    /**
     * returns an arraylist of the followupDiscussions from the given postID
     * @param postID
     * @return followupDiscussions
     */
    public ArrayList<FollowupDiscussion> getFollowupDiscussionsFromPostID(int postID) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<FollowupDiscussion> fudList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/piazzadatabase", "databaser124",
                    "databaser124");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM followupdiscussion WHERE postID = '" + postID + "'");
            while (rs.next() != false) {
                int SequenceNumber = rs.getInt("SequenceNumber");
                int userID = rs.getInt("UserID");
                String content = rs.getString("Content");
                boolean anonymous = rs.getBoolean("Anonymous");
                int likes = rs.getInt("likes");
                FollowupDiscussion fud = new FollowupDiscussion(postID, SequenceNumber, userID, content, anonymous, likes);
                fudList.add(fud);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
        return fudList;
    }
    public static void main(String[] args) {

        SQLConnector testConnector = new SQLConnector();
        System.out.println(testConnector.getNextID("InstructorID", "Instructor"));
        System.out.println(testConnector.getPostsReadStatistics());

    }
}
