package test.builder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import test.model.MyUser;

@WebAppConfiguration
public abstract class AbstractWebTest extends AbstractTest {

	@Autowired
	private WebApplicationContext wac;

	@Resource
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	private MockHttpSession session;

	@Before
	public void setUp() {
		super.setUp();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.addFilter(springSecurityFilterChain)
				.alwaysDo(print())
				.build();
	}

	public ResultActions perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
		if (session != null) {
			requestBuilder.session(session);
		}
		requestBuilder.contentType(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	@Override
	protected void signIn(UserBuilder userBuilder) throws Exception {
		if (session != null && !session.isInvalid()) {
			session.invalidate();
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		MyUser user = userBuilder.get();
		String name = user.getEmail();
		String password = "1";
		session = (MockHttpSession) mockMvc.perform(post("/api/login")
				.param("j_username", name)
				.param("j_password", password))
				.andExpect(status().isOk())
				.andReturn().getRequest().getSession();
	}

	protected void signOut() {
		if (session != null) {
			session.invalidate();
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}
}
