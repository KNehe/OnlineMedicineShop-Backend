package nehe.demo.Modals;

import java.util.Optional;

public class LoginViewModel  {

    private String firstName;
    private String lastName;
    private Optional<Integer> id;
    private String role;

    public LoginViewModel() {
    }

    public LoginViewModel(String firstName, String lastName, Optional<Integer> id, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
