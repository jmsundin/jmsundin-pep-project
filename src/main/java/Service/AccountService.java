package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        return accountDAO.insertAccount(username, password);
    }

    public boolean isValidCredentials(String username, String password) {
        if (accountIsRegistered(username)) return false;
        else if (username.isEmpty()) return false;
        if (password.length() < 4) return false;
        return true;
    }

    public boolean accountIsRegistered(String username) {
        if (accountDAO.findAccountByUsername(username) == null) return false;
        return true;
    }

    public Account findAccountByAccountId(int account_id) {
        return accountDAO.findAccountByAccountId(account_id);
    }

    public Account login(String username, String password) {
        Account registeredAccount = accountDAO.findAccountByUsername(username);
        if (registeredAccount == null) return null;
        if (!registeredAccount.getPassword().equals(password)) return null;
        return registeredAccount;
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
}
