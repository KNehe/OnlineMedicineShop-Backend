package nehe.demo.controllers;


import com.google.gson.Gson;

import nehe.demo.Modals.ChangePasswordModel;
import nehe.demo.Modals.LoginViewModel;
import nehe.demo.Modals.User;
import nehe.demo.Services.LoginViewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    private static final Gson gson = new Gson();
    
    private LoginViewModelService loginViewModelService;

    @Autowired
    public  LoginController(LoginViewModelService loginViewModelService)
    {
        this.loginViewModelService =loginViewModelService;
    }



    @GetMapping("/getDetails")
    public LoginViewModel login(HttpServletRequest request, Principal principal)
    {
        if(request.isUserInRole("ADMIN"))
        {
            //principal.getName() returns email
            return new LoginViewModel(loginViewModelService.getUserFirstName(principal.getName()),
                                      loginViewModelService.getUserId(principal.getName()),
                                     "ADMIN");
        }

        return new LoginViewModel(loginViewModelService.getUserFirstName(principal.getName()),
                loginViewModelService.getUserId(principal.getName()),
                "USER");
    }

    //change password
    @PostMapping(value="/changePassword")
    public ResponseEntity<String> postMethodName(@RequestBody ChangePasswordModel changePasswordModel) {
    
    if(loginViewModelService.changePassword(changePasswordModel.getNewPassword(), changePasswordModel.getUserId() ))
    {
      return ResponseEntity.ok(gson.toJson("Password changed successfully"));
    }
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(gson.toJson("Password not changed"));
       
    }

    @GetMapping(value = "/getOneUser")
    public User getOneUser(@RequestParam(required = true) int userId)
    {
        //error handled in service using custom UserNotFoundException
        //throw 404 to client
        User user = loginViewModelService.getOneUser(userId);
        user.setPassword("Couldn't Allow you see this");
       return  user;
    }

    @PostMapping(value = "/updateUser")
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
