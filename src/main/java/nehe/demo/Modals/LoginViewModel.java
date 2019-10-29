package nehe.demo.Modals;

import java.util.Optional;

public class LoginViewModel  {

    private String firstname;
    private Optional<Integer> id;
    private String role;

    public LoginViewModel() {
    }

    public LoginViewModel(String firstname, Optional<Integer> id, String role) {
        this.firstname = firstname;
        this.id = id;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
