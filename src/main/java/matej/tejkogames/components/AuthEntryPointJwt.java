package matej.tejkogames.components;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import matej.tejkogames.api.services.ExceptionLogService;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Autowired
	ExceptionLogService exceptionLogService;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		exceptionLogService.save(exception);

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Neovlašten pristup");
	}

}