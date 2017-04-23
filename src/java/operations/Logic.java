package operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.DBConnect;
import objects.User;

/**
 *
 * @author Mato
 */
public class Logic {

    PreparedStatement ps;
    Connection connection = null;
    User u = new User();
    boolean isAuthenticated;
    byte[] expectedHash;

    public Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return connection = DriverManager.getConnection(DBConnect.URL, DBConnect.ID, DBConnect.PW);
        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }

    public String isAuthenticated() {
        if(isAuthenticated==true){
            return "success.html";
        }else{
            return "fail.html";
        }
    }

    public boolean notAuthenticated() {
        return false;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public byte[] getUserByUsername(String userName) throws SQLException, ClassNotFoundException {
        ps = getConnection().prepareStatement("SELECT password FROM users WHERE userName = ?");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            expectedHash = SaltingPasswords.hash(rs.getString("password").toCharArray(),
                    SaltingPasswords.getNextSalt());
            
            return expectedHash;

        }
        connection.close();
        return null;

    }

    /**
     * comparing hashed input from user with hashed DB value
     *
     * @param password input from user
	 *
     */
    public void authenticate(String password) {
        setAuthenticated(SaltingPasswords.isExpectedPassword(password.toCharArray(), SaltingPasswords.getNextSalt(),
                expectedHash));

    }

    // hashing, salting & inserting to DB in background
    public void prepareNewUser(String userName, String password) throws ClassNotFoundException, SQLException {
        u.setUserName(userName);
        u.setPassword(password);
        insertUser(u.getUserName(), u.getPassword());

    }

    public void insertUser(String userName, String password) throws SQLException, ClassNotFoundException {
        ps = getConnection().prepareStatement("INSERT INTO users(userName, password) VALUES(?,?)");
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.executeUpdate();
        connection.close();
    }

}
