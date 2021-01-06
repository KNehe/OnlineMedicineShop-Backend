package nehe.demo.controllers;


import com.google.gson.Gson;

import nehe.demo.Modals.*;
import nehe.demo.Services.UserService;
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
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class AuthController {


    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserDetailsService jwtInMemoryUserDetailsService
                          )
    {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        final User user = userService.findUserByEmail(authenticationRequest.getEmail());

        return ResponseEntity.ok(new JwtResponse("Success",token,new LoginViewModel(user.getFirstName(),user.getLastName(), Optional.of(user.getId()),user.getRole())));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception
    {
        Objects.requireNonNull(user);

        if(userService.checkIfEmailExists(user.getEmail()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new GeneralResponse("Fail", "User with Email exists"));
        }

        final String password = user.getPassword();
        final String email = user.getEmail();

        user.setRole("USER");

        String result = userService.registerUser(user);

        if (result.equals("User saved"))
        {
            authenticate(email, password);

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(user.getEmail());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse("Success",token,new LoginViewModel(user.getFirstName(),user.getLastName(), Optional.of(user.getId()),user.getRole())));

        }

        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GeneralResponse("Fail", "An error occurred while saving user"));
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

    @GetMapping("/user-details")
    public LoginViewModel getUserDetails(HttpServletRequest request, Principal principal)
    {
        final User user = userService.findUserByEmail(principal.getName());

        if(request.isUserInRole("ADMIN"))
        {
            return new LoginViewModel(user.getFirstName(),
                                      user.getLastName(),
                                     Optional.of(user.getId()),
                                     "ADMIN");
        }

        return new LoginViewModel(user.getFirstName(),
                user.getLastName(),
                Optional.of(user.getId()),
                "USER");
    }

    @PostMapping(value="/change-password")
    public ResponseEntity<?> postMethodName(@RequestBody ChangePasswordModel changePasswordModel) {
    
    if(userService.changePassword(changePasswordModel.getNewPassword(), changePasswordModel.getUserId() ))
    {
      return ResponseEntity.ok(new GeneralResponse("Success", "Password changed successfully"));
    }
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new GeneralResponse("Fail", "Password not changed"));
       
    }

    @GetMapping(value = "/get-one-user")
    public User getOneUser(@RequestParam(required = true) int userId)
    {

        User user = userService.getOneUser(userId);
        user.setPassword("");
       return  user;
    }

    @PostMapping(value = "/update-user")
    public ResponseEntity<?> getOneUser(@Valid @RequestBody User user)
    {
        if(userService.updateUser(user))
        {
            return ResponseEntity.ok(new GeneralResponse("Success","Changes Saved"));
        }else {
            return ResponseEntity.status( HttpStatus.EXPECTATION_FAILED).body( new GeneralResponse("Fail", "An error occurred") );
        }
    }


}
