package nehe.demo.controllers;


import com.google.gson.Gson;

import nehe.demo.Modals.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class AuthController {

    private static final Gson gson = new Gson();
    
    private LoginViewModelService loginViewModelService;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    public AuthController(LoginViewModelService loginViewModelService,AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserDetailsService jwtInMemoryUserDetailsService
                          )
    {
        this.loginViewModelService =loginViewModelService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
    {  System.out.println("user is"+ user);
        Objects.requireNonNull(user);

        if(loginViewModelService.checkIfEmailExists(user.getEmail()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(gson.toJson("User with Email exists"));
        }

        final String password = user.getPassword();
        final String email = user.getEmail();

        user.setRole("USER");

        String result = loginViewModelService.registerUser(user);

        if (result.equals("User saved"))
        {
            authenticate(email, password);

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(user.getEmail());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));

        }

        return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result);
    }

    private void authenticate(String username, String password) throws Exception {
        System.out.println("user data : "+ username + password );

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

    @GetMapping("/user-details")
    public LoginViewModel login(HttpServletRequest request, Principal principal)
    {
        if(request.isUserInRole("ADMIN"))
        {
            return new LoginViewModel(loginViewModelService.getUserFirstName(principal.getName()),
                                      loginViewModelService.getUserId(principal.getName()),
                                     "ADMIN");
        }

        return new LoginViewModel(loginViewModelService.getUserFirstName(principal.getName()),
                loginViewModelService.getUserId(principal.getName()),
                "USER");
    }

    @PostMapping(value="/change-password")
    public ResponseEntity<String> postMethodName(@RequestBody ChangePasswordModel changePasswordModel) {
    
    if(loginViewModelService.changePassword(changePasswordModel.getNewPassword(), changePasswordModel.getUserId() ))
    {
      return ResponseEntity.ok(gson.toJson("Password changed successfully"));
    }
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(gson.toJson("Password not changed"));
       
    }

    @GetMapping(value = "/get-one-user")
    public User getOneUser(@RequestParam(required = true) int userId)
    {

        User user = loginViewModelService.getOneUser(userId);
        user.setPassword("");
       return  user;
    }

    @PostMapping(value = "/update-user")
    public ResponseEntity<String> getOneUser(@Valid @RequestBody User user)
    {
        if(loginViewModelService.updateUser(user))
        {
            return ResponseEntity.ok(gson.toJson("Changes Saved"));
        }else {
            return ResponseEntity.status( HttpStatus.EXPECTATION_FAILED).body(gson.toJson("An error occurred") );
        }
    }


}
