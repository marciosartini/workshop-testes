package test.controller;

import org.junit.Test;

import test.builder.AbstractWebTest;
import test.builder.UserBuilder;

public class TestControllerTest extends AbstractWebTest {

	@Test
	public void teste() throws Exception {
		UserBuilder marcelo = UserBuilder.user("Marcelo");
		saveAll();
		signIn(marcelo);
	}
}
