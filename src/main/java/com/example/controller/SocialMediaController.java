package com.example.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.CustomException;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @Autowired
    private AccountService account_service;
    @Autowired
    private MessageService message_service;

    /**
     * 
     * @param account Account to be registered in database
     * @return registered Account
     * @throws CustomException
     */
    @PostMapping( "register" )
    public ResponseEntity<Account> createNewUserHandler( @RequestBody Account account ) throws CustomException {
        return account_service.createNewUser( account );
    }

    /**
     * 
     * @param account Account to be logged in to database
     * @return logged in Account
     * @throws CustomException
     */
    @PostMapping( "login" )
    public ResponseEntity<Account> userLoginHandler( @RequestBody Account account ) throws CustomException {
        return account_service.userLogin( account );
    }

    /**
     * 
     * @param message Message to be created in database
     * @return created Message
     * @throws CustomException
     */
    @PostMapping( "messages" )
    public ResponseEntity<Message> createMessageHandler( @RequestBody Message message ) throws CustomException {
        return message_service.createNewMessage( message );
    }

    /**
     * 
     * @return all messages in database
     */
    @GetMapping( "messages" )
    @ResponseStatus( HttpStatus.OK )
    public List<Message> getAllMessagesHandler(){
        return message_service.getAllMessages();
    }
 
    /**
     * 
     * @param message_id ID of message to be retrieved from database
     * @return retrieved message
     * @throws CustomException
     */
    @GetMapping( "messages/{message_id}" )
    @ResponseStatus( HttpStatus.OK )
    public Message getMessageByIDHandler( @PathVariable Integer message_id ) throws CustomException {
        return message_service.getMessageByIDHandler( message_id );
    }

    /**
     * 
     * @param message_id ID of message to be deleted from database
     * @return deleted message
     * @throws CustomException
     */
    @DeleteMapping( "messages/{message_id}" )
    @ResponseStatus( HttpStatus.OK )
    public String deleteMessageByIDHandler( @PathVariable Integer message_id ) throws CustomException {
        return message_service.deleteMessageById( message_id );  
    }

    /**
     * 
     * @param message_id ID of message to be updated in database
     * @param message Message content to be placed in updated message in database
     * @return updated message
     * @throws CustomException
     */
    @PatchMapping( "messages/{message_id}" )
    public ResponseEntity<String> updateMessageByIDHandler( @PathVariable Integer message_id, @RequestBody Message message ) throws CustomException {
        return message_service.updateMessageByID( message_id, message );
    }
        
    /**
     * 
     * @param account_id ID of account to retrieve all messages from database
     * @return all messages from account with account_id = account_id
     */
    @GetMapping( "accounts/{account_id}/messages" )
    @ResponseStatus( HttpStatus.OK )
    public List<Message> getAllMessagesFromUserByIDHandler( @PathVariable Integer account_id ) {
        return message_service.getAllMessagesFromUserByID( account_id );
    }
}
