package nehe.demo.Services;

import nehe.demo.Exception.UserNotFoundException;
import nehe.demo.Modals.User;
import nehe.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    //check if email exists
    public boolean checkIfEmailExists(String email)
    {
        if(userRepository.findEmail(email) != null)
        {
            return true;
        }
        return false;
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

    public User getOneUser(int userId)
    {
     return  userRepository.findById(userId)
             .orElseThrow( ()-> new UserNotFoundException("User with Id: "+ userId +"does not exist"));
    }

    public boolean updateUser(User user)
    {

        int result1 = userRepository.changeFirstName(user.getFirstName(),user.getId());
        int result2 = userRepository.changeLastName(user.getLastName(),user.getId());
        int result3 = userRepository.changePhone(user.getPhone(),user.getId());
        int result4 = userRepository.changeEmail(user.getEmail(),user.getId());

        if (result1 == 1 && result2 == 1 && result3 == 1 && result4 == 1)
        {
            return  true;
        }else
        {
            return false;
        }
    }

    @Bean
    PasswordEncoder myPasswordEncoder ()
    {
        return new BCryptPasswordEncoder();
    }


}
