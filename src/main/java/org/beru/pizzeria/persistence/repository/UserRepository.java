package org.beru.pizzeria.persistence.repository;

import org.beru.pizzeria.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String>{

}
