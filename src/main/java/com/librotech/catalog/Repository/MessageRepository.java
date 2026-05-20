package com.librotech.catalog.Repository;

import com.librotech.catalog.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message,String> {
}
