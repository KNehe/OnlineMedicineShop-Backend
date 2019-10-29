package nehe.demo.Services;

import nehe.demo.Modals.User;
import nehe.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LoginViewModelService {

    private UserRepository userRepository;

    @Autowired
    public LoginViewModelService( UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    //get userId by email
    public Optional<Integer> getUserId(String email)
    {
        return userRepository.findUserId(email);
    }

    //get user first name by email
    public String getUserFirstName(String email)
    {
        return userRepository.findFirstName(email).orElse("First name for "+ email + " not found");
    }

    //registerUser
    public String registerUser(User user)
    {
        try
        {   //encode password
            user.setPassword(myPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }
        catch (Exception e)
        {
            return  "An error occurred User not saved!";
        }

        return "User saved";
    }


    //changePassword
    public Boolean changePassword(String newPassword,int userId)
    {   
        if(userRepository.changePassword( myPasswordEncoder().encode(newPassword) , userId) == 1)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }

    @Bean
    PasswordEncoder myPasswordEncoder ()
    {
        return new BCryptPasswordEncoder();
    }


}
