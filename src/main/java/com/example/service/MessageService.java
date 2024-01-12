package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.entity.Message;
import com.example.exception.CustomException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional( rollbackFor = CustomException.class )
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService( MessageRepository messageRepository, AccountRepository accountRepository ){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * 
     * @param entity Message to be added to database
     * @return Success: 200, created Message from database
     *          Fail: 400 - Client Error
     * @throws CustomException
     */
    public ResponseEntity<Message> createNewMessage( Message entity ) throws CustomException {
        // validation
        if( entity.getMessage_text().isBlank() ||
            entity.getMessage_text().length() > 255 ||
            accountRepository.findById( entity.getPosted_by() ).orElse(null) == null )
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( null );

        Message created_message = messageRepository.save( entity );

        // check created message
        Message checked_message = messageRepository.findById( created_message.getMessage_id() ).orElse(null);
        if( checked_message == null )
            throw new CustomException( "CREATE MESSAGE: Failed" );

        return ResponseEntity.status( HttpStatus.OK ).body( checked_message );
    }

    /**
     * @return all Messages in database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * 
     * @param message_id ID of message to be retrieved from database
     * @return 200, retrieved message
     * @throws CustomException
     */
    public Message getMessageByIDHandler( Integer message_id ) throws CustomException {
        Message retrieved_message = messageRepository.findById( message_id ).orElse( null );
        return retrieved_message;
    }

    /**
     * 
     * @param message_id ID of message to be deleted from database
     * @return 200, number of Messages deleted
     * @throws CustomException
     */
    public String deleteMessageById( Integer message_id ) throws CustomException {
        Message optional_message = messageRepository.findById( message_id ).orElse(null);

        if( optional_message == null ) 
            return "";

        messageRepository.deleteById( message_id );

        return "1";        
    }

    /**
     * 
     * @param message_id ID of message to be updated in database
     * @param message Message content to update message in database
     * @return  Success: 200, number of Messages updated
     *          Fail: 400 - Client Error
     * @throws CustomException
     */
    public ResponseEntity<String> updateMessageByID( Integer message_id, Message message ) throws CustomException {
        Message updated_message = messageRepository.findById( message_id ).orElse(null);

        // validation
        if( updated_message == null ||
            message.getMessage_text().isBlank() ||
            message.getMessage_text().length() > 255 )
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( null );

        updated_message.setMessage_text( message.getMessage_text() );

        return ResponseEntity.status( HttpStatus.OK ).body( "1" );
    }

    /**
     * @return List of all messages from User
     */
    public List<Message> getAllMessagesFromUserByID( Integer posted_by ) {
        return messageRepository.findMessagesByPostedBy( posted_by );
    }
}
