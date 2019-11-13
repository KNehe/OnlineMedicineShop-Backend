package nehe.demo.controllers;


import com.google.gson.Gson;

import nehe.demo.Modals.ChangePasswordModel;
import nehe.demo.Modals.LoginViewModel;
import nehe.demo.Modals.User;
import nehe.demo.Services.LoginViewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user)
    {

      String result = loginViewModelService.registerUser(user);
      
      if (result.equals("User saved"))
      {
          return  ResponseEntity.ok(gson.toJson(result));

      }

      //An error occurred
      return  ResponseEntity.ok(result);


    }//posting

    //change password
    @PostMapping(value="/changePassword")
    public ResponseEntity<String> postMethodName(@RequestBody ChangePasswordModel changePasswordModel) {
    
    if(loginViewModelService.changePassword(changePasswordModel.getNewPassword(), changePasswordModel.getUserId() ))
    {
      return ResponseEntity.ok(gson.toJson("Password changed successfully"));
    }
    return ResponseEntity.ok(gson.toJson("Password not changed"));
       
    }
    
    
}
