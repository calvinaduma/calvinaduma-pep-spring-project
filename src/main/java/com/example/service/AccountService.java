package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.repository.AccountRepository;

@Service
@Transactional( rollbackFor = CustomException.class )
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService( AccountRepository accountRepository ){
        this.accountRepository = accountRepository;
    }

    /**
     * 
     * @param entity Account object
     * @return Success: 200, registered Account
     *         Fail: 409 - Conflict
     *         Fail: 400 - Bad Request
     * @throws CustomException
     */
    public ResponseEntity<Account> createNewUser( Account entity ) throws CustomException {
        // validation
        if( entity.getUsername().isEmpty() || 
            entity.getPassword().length() < 4 )
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( null );
        if( accountRepository.findByUsername( entity.getUsername() ) != null ) 
            return ResponseEntity.status( HttpStatus.CONFLICT ).body( null );
           
        Account created_account = accountRepository.save( entity );
        
        return ResponseEntity.status( HttpStatus.OK ).body( created_account );
    }

    /**
     * 
     * @param account Account object
     * @return Success: 200, logged in Account
     *         Fail: 401 - Unauthorized
     * @throws CustomException
     */
    public ResponseEntity<Account> userLogin( Account account ) throws CustomException {
        Account logged_in_account = accountRepository.findByUsernameAndPassword( account.getUsername(), account.getPassword() );

        // login fail
        if( logged_in_account == null )
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( null );

        return ResponseEntity.status( HttpStatus.OK ).body( logged_in_account );
    }
}
