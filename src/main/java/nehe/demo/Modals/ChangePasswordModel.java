package nehe.demo.Modals;


public class ChangePasswordModel {

    private String newPassword;
    private String confirmPassword;
    private int userId;
    
    public ChangePasswordModel() {
    }
    
    public ChangePasswordModel(String newPassword, String confirmPassword, int userId) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.userId = userId;
    }
    

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    

    
    

}
  