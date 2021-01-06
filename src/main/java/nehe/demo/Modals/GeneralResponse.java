package nehe.demo.Modals;

public class GeneralResponse {

    private  final  String status;
    private final String message;

    public GeneralResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
