package com.skripsi.adeahmadfaisal.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBooking{

	@SerializedName("uid")
	private String uid;

	@SerializedName("booking")
	private List<Booking> booking;

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setBooking(List<Booking> booking){
		this.booking = booking;
	}

	public List<Booking> getBooking(){
		return booking;
	}

	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg(){
		return errorMsg;
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
			"ResponseBooking{" + 
			"uid = '" + uid + '\'' + 
			",booking = '" + booking + '\'' + 
			",error_msg = '" + errorMsg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}