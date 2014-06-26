package test.repository;

import org.springframework.data.repository.CrudRepository;

import test.model.MyUser;

public interface UserRepository extends CrudRepository<MyUser, Long> {

	MyUser findByEmail(String email);
}
