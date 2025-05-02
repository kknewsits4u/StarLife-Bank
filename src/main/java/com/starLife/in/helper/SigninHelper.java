package com.starLife.in.helper;


public class SigninHelper {

    private String userId;
    private String cpassword;
    private String email;
    private String capture;
    private Boolean consent;
		private String newOtp;
		private String captureInput;


		
	public String getCaptureInput() {
			return captureInput;
		}
		public void setCaptureInput(String captureInput) {
			this.captureInput = captureInput;
		}



	public SigninHelper() {
		super();
	}


	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCapture() {
		return capture;
	}
	public void setCapture(String capture) {
		this.capture = capture;
	}
	public Boolean getConsent() {
		return consent;
	}
	public void setConsent(Boolean consent) {
		this.consent = consent;
	}
	public String getNewOtp() {
		return newOtp;
	}

	
	

	@Override
	public String toString() {
		return "SigninHelper [userId=" + userId + ", cpassword=" + cpassword + ", email=" + email + ", capture=" + capture
				+ ", consent=" + consent + ", newOtp=" + newOtp + ", captureInput=" + captureInput + "]";
	}



	public SigninHelper(String userId, String cpassword, String email, String capture, Boolean consent, String newOtp,
			String captureInput) {
		this.userId = userId;
		this.cpassword = cpassword;
		this.email = email;
		this.capture = capture;
		this.consent = consent;
		this.newOtp = newOtp;
		this.captureInput = captureInput;
	}



	public void setNewOtp(String newOtp) {
		this.newOtp = newOtp;
	}



}
