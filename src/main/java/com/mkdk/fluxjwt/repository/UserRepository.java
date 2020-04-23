package com.mkdk.fluxjwt.repository;

import com.mkdk.fluxjwt.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
