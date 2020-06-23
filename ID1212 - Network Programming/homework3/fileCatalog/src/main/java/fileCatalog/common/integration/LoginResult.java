package fileCatalog.common.integration;

public class LoginResult {
	private int sessionId;
	private boolean loginSuccess;
	public LoginResult(int sessionId, boolean loginSuccess) {
		this.sessionId = sessionId;
		this.loginSuccess = loginSuccess;
	}
	
	public int getSessionId() {
		return this.sessionId;
	}
	public boolean loginSuccessful() {
		return this.loginSuccess;
	}
}
