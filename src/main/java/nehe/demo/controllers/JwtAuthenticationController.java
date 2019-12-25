package nehe.demo.controllers;

import nehe.demo.Modals.JwtRequest;
import nehe.demo.Modals.JwtResponse;
import nehe.demo.Modals.User;
import nehe.demo.Services.LoginViewModelService;
import nehe.demo.Services.UserDetailsService;
import nehe.demo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import com.google.gson.Gson;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	private AuthenticationManager authenticationManager;
	private LoginViewModelService loginViewModelService;

	private JwtTokenUtil jwtTokenUtil;

	private static final Gson gson = new Gson();


	private UserDetailsService jwtInMemoryUserDetailsService;

    
	@Autowired
	public JwtAuthenticationController(AuthenticationManager authenticationManager,
									   JwtTokenUtil jwtTokenUtil,
										UserDetailsService jwtInMemoryUserDetailsServiee,
										LoginViewModelService loginViewModelService
										)
	{
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsServiee;
		this.loginViewModelService = loginViewModelService;
	}
	
	
	//used to login in
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}


	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception
    {
		Objects.requireNonNull(user);
		
		if(loginViewModelService.checkIfEmailExists(user.getEmail()))
		{
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(gson.toJson("User with Email exists"));
		}

		final String password = user.getPassword();
		final String email = user.getEmail();
		
		//set role of user
		if(user.getRole().equals("Buy"))
		{
			user.setRole("USER");
		}else if(user.getRole().equals("Sell"))
		{
			user.setRole("ADMIN");
		}
		String result = loginViewModelService.registerUser(user);
		
		if (result.equals("User saved"))
		{   
			
			authenticate(email, password);

			final UserDetails userDetails = jwtInMemoryUserDetailsService
					.loadUserByUsername(user.getEmail());

			final String token = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new JwtResponse(token));

		}

		//An error occurred
		return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result);
	}
	
	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
