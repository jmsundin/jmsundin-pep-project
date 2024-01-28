package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
// import Model.Message;
import Service.AccountService;
// import Service.MessageService;

import java.util.List;
// import java.util.ArrayList;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController(){
        accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/", this::exampleHandler);
        app.get("/getAccountTable", this::getAccountTable);
        app.post("/register", this::register);
        app.post("/login", this::login);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void register(Context context) throws JsonProcessingException {
        Account accountFromBody = context.bodyAsClass(Account.class);

        if (!accountService.isValidCredentials(accountFromBody.username, accountFromBody.password)) {
            context.result("").status(400);
        } else {
            Account registeredAccount = accountService.addAccount(accountFromBody);
            if (registeredAccount != null) {
                context.json(registeredAccount).status(200);
            } else {
                context.result("").status(400);
            }
        }
    }

    private void login(Context context) {
        Account account = context.bodyAsClass(Account.class);

        if (!accountService.accountIsRegistered(account.username)) {
            context.status(401);
        } else {
            Account accountLoggedIn = accountService.login(account.username, account.password);
            
            if (accountLoggedIn == null) {
                context.status(401);
            } else {
                context.json(accountLoggedIn).status(200);
            }
        }
    }

    private void getAccountTable(Context context) {
        List<Account> accounts = accountService.getAllAccounts();
        context.json(accounts).status(200);
    }

    }
}