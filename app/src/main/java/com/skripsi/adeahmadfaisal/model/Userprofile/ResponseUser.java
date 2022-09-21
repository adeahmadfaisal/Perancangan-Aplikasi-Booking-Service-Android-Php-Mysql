package com.skripsi.adeahmadfaisal.model.Userprofile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseUser{

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("userprofile")
	private List<UserprofileItem> userprofile;

	@SerializedName("error")
	private boolean error;

	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg(){
		return errorMsg;
	}

	public void setUserprofile(List<UserprofileItem> userprofile){
		this.userprofile = userprofile;
	}

	public List<UserprofileItem> getUserprofile(){
		return userprofile;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUser{" + 
			"error_msg = '" + errorMsg + '\'' + 
			",userprofile = '" + userprofile + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}


}