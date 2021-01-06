package nehe.demo.Modals;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private final String status;
	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final LoginViewModel userDetails;

	public JwtResponse(String status,String token,LoginViewModel userDetails) {
		this.status = status;
		this.token = token;
		this.userDetails =userDetails;
	}

	public String getToken() {
		return this.token;
	}

	public String getStatus() {
		return status;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public LoginViewModel getUserDetails() {
		return userDetails;
	}
}