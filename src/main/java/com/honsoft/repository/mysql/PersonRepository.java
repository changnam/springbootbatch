package com.honsoft.repository.mysql;

import org.springframework.data.repository.CrudRepository;

import com.honsoft.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

}
