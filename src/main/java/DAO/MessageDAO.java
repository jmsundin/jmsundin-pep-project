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

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message;";
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message();

                int message_id = rs.getInt(1);
                int posted_by = rs.getInt(2);
                String message_text = rs.getString(3);
                long time_posted_epoch = rs.getLong(4);

                message.setMessage_id(message_id);
                message.setPosted_by(posted_by);
                message.setMessage_text(message_text);
                message.setTime_posted_epoch(time_posted_epoch);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message getMessageById(int message_id) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                int posted_by = rs.getInt(2);
                String message_text = rs.getString(3);
                long time_posted_epoch = rs.getLong(4);

                message.setMessage_id(message_id);
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
