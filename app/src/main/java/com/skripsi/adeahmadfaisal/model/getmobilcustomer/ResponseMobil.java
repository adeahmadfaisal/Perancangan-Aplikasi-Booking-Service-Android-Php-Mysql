package com.skripsi.adeahmadfaisal.model.getmobilcustomer;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseMobil{

	@SerializedName("mycar")
	private List<MycarItem> mycar;

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	public void setMycar(List<MycarItem> mycar){
		this.mycar = mycar;
	}

	public List<MycarItem> getMycar(){
		return mycar;
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
			"ResponseMobil{" + 
			"mycar = '" + mycar + '\'' + 
			",error_msg = '" + errorMsg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}