package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message insertMessage(int posted_by, String message_text, long time_posted_epoch) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) "
                        + "VALUES (?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, posted_by);
            ps.setString(2, message_text);
            ps.setLong(3, time_posted_epoch);

            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            while (pkeyResultSet.next()) {
                Message message = new Message();
                message.setMessage_id(pkeyResultSet.getInt(1));
                message.setPosted_by(posted_by);
                message.setMessage_text(message_text);
                message.setTime_posted_epoch(time_posted_epoch);
                return message;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
