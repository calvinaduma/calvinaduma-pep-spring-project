package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository< Message, Integer > {
    /**
     * 
     * @param posted_by account_id from Messages FK
     * @return List of messages by accout_id = posted_by
     */
    @Query( value="SELECT * FROM message m WHERE m.posted_by = :posted_by", nativeQuery=true )
    List<Message> findMessagesByPostedBy( @Param("posted_by") Integer posted_by );
}
