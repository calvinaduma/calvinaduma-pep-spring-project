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
     * @param account
     * @return
     * @throws CustomException
     */
    @PostMapping( "register" )
    public ResponseEntity<Account> createNewUserHandler( @RequestBody Account account ) throws CustomException {
        return account_service.createNewUser( account );
    }

    /**
     * 
     * @param account
     * @return
     * @throws CustomException
     */
    @PostMapping( "login" )
    public ResponseEntity<Account> userLoginHandler( @RequestBody Account account ) throws CustomException {
        return account_service.userLogin( account );
    }

    /**
     * 
     * @param message
     * @return
     * @throws CustomException
     */
    @PostMapping( "messages" )
    public ResponseEntity<Message> createMessageHandler( @RequestBody Message message ) throws CustomException {
        return message_service.createNewMessage( message );
    }

    /**
     * 
     * @return
     */
    @GetMapping( "messages" )
    @ResponseStatus( HttpStatus.OK )
    public List<Message> getAllMessagesHandler(){
        return message_service.getAllMessages();
    }
 
    /**
     * 
     * @param messageID
     * @return
     * @throws CustomException
     */
    @GetMapping( "messages/{message_id}" )
    @ResponseStatus( HttpStatus.OK )
    public Message getMessageByIDHandler( @PathVariable Integer message_id ) throws CustomException {
        return message_service.getMessageByIDHandler( message_id );
    }

    /**
     * 
     * @param messageID
     * @return
     * @throws CustomException
     */
    @DeleteMapping( "messages/{message_id}" )
    @ResponseStatus( HttpStatus.OK )
    public String deleteMessageByIDHandler( @PathVariable Integer message_id ) throws CustomException {
        return message_service.deleteMessageById( message_id );  
    }

    /**
     * 
     * @param messageID
     * @param message
     * @return
     * @throws CustomException
     */
    @PatchMapping( "messages/{message_id}" )
    public ResponseEntity<String> updateMessageByIDHandler( @PathVariable Integer message_id, @RequestBody Message message ) throws CustomException {
        return message_service.updateMessageByID( message_id, message );
    }
        
    /**
     * 
     * @param accountID
     * @return
     */
    @GetMapping( "accounts/{account_id}/messages" )
    @ResponseStatus( HttpStatus.OK )
    public List<Message> getAllMessagesFromUserByIDHandler( @PathVariable Integer account_id ) {
        return message_service.getAllMessagesFromUserByID( account_id );
    }
}
