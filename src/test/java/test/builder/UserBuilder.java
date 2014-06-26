package test.builder;

import test.model.MyUser;
import aleph.AbstractBuilder;

public class UserBuilder extends AbstractBuilder<MyUser> {
	public UserBuilder name(String value) {
		return set("name", value);
	}

	public UserBuilder email(String value) {
		return set("email", value);
	}

	public UserBuilder password(String value) {
		return set("password", value);
	}

	public static UserBuilder user(String name) {
		return new UserBuilder()
				.name(name)
				.email(name.toLowerCase())
				.password("1");
	}
}
