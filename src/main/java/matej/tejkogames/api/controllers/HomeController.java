package matej.tejkogames.api.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ExceptionLogService;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("")
public class HomeController {

	@Autowired
	ExceptionLogService exceptionLogService;
	
	@GetMapping("/api")
	public void home(HttpServletResponse response) {
		try {
			response.sendRedirect("/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config");
		} catch (IOException exception) {
			exceptionLogService.save(exception);
			exception.printStackTrace();
		}
	}
}
