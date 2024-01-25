import Controller.SocialMediaController;
import io.javalin.Javalin;

import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// import java.util.Scanner;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        databaseSetup();
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }

    public static void databaseSetup(){
        try {
            Connection conn = ConnectionUtil.getConnection();
            String createAccountTable = "CREATE TABLE Account ("
                                        + "account_id INTEGER PRIMARY KEY AUTO_INCREMENT"
                                        + "username VARCHAR(255) UNIQUE,"
                                        + "password VARCHAR(255)";
            PreparedStatement ps1 = conn.prepareStatement(createAccountTable);
            ps1.executeUpdate();

            String createMessageTable = "CREATE TABLE Message ("
                                        + "message_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                                        + "posted_by INTEGER,"
                                        + "message_text VARCHAR(255),"
                                        + "time_posted_epoch LONG,"
                                        + "FOREIGN KEY (posted_by) REFERENCES Account(account_id)"
                                        + ")";
            PreparedStatement ps2 = conn.prepareStatement(createMessageTable);
            ps2.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
