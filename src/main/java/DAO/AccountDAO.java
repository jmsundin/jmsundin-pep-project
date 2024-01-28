package DAO;

import Model.Account;

import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

public class AccountDAO {

    public Account insertAccount(String username, String password){
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "INSERT INTO account (username, password_) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            while (pkeyResultSet.next()) {
                Account account = new Account(username, password);
                account.setAccount_id((int) (pkeyResultSet.getLong(1)));
                return account;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account findAccountByUsername(String username) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT account_id, username, password_ "
                        + "FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String usernameFromDB = rs.getString(2);
                String password = rs.getString(3);
                return new Account(usernameFromDB, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString(2);
                String password = rs.getString(3);
                Account account = new Account(username, password);
                accounts.add(account);
                System.out.println(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
