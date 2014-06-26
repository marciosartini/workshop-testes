package test.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

	public static MyUserDetails getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof MyUserDetails)) {
			return null;
		}

		return (MyUserDetails) principal;
	}

	public static boolean isUserLogged() {
		return getUser() != null;
	}
}
