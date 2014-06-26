package test.builder;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import test.model.MyUser;
import aleph.TestPersistentContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public abstract class AbstractTest {

	@Autowired
	private TransactionTemplate template;

	@Autowired
	private DaoAuthenticationProvider authenticationProvider;

	@Before
	public void setUp() {
		template.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestPersistentContext.get().clear();
			}
		});
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	protected void saveAll() {
		template.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestPersistentContext.get().saveAll();
			}
		});
	}

	@Transactional
	protected void signIn(UserBuilder userBuilder) throws Exception {
		SecurityContextHolder.getContext().setAuthentication(null);
		MyUser user = userBuilder.get();
		String password = "1";
		Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	protected void inTransaction(TransactionCallback<Object> action) {
		template.execute(action);
	}
}
