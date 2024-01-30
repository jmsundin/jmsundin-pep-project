package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }
    
    public boolean messageIsValid(int posted_by, String message_text) {
        if (message_text.length() == 0 || message_text.length() > 255) return false;
        else if (accountDAO.findAccountByAccountId(posted_by) == null) return false;
        else return true;
    }
    
    public Message insertMessage(int posted_by, String message_text, long time_posted_epoch) {
        return messageDAO.insertMessage(posted_by, message_text, time_posted_epoch);
    }
    
}
