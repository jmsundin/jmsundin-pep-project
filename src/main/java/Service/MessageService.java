package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

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
    
    public boolean messageIsValid(String message_text) {
        if (message_text.length() == 0 || message_text.length() > 255) return false;
        else return true;
    }
    
    public Message insertMessage(int posted_by, String message_text, long time_posted_epoch) {
        return messageDAO.insertMessage(posted_by, message_text, time_posted_epoch);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message updateMessageById(int message_id, String updated_message_text) {
        return messageDAO.updateMessageById(message_id, updated_message_text);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }
    
}
