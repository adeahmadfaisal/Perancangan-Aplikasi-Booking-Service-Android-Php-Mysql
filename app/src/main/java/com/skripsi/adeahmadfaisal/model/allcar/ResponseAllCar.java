package com.skripsi.adeahmadfaisal.model.allcar;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAllCar{

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("allcar")
	private List<AllcarItem> allcar;

	@SerializedName("error")
	private boolean error;

	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg(){
		return errorMsg;
	}

	public void setAllcar(List<AllcarItem> allcar){
		this.allcar = allcar;
	}

	public List<AllcarItem> getAllcar(){
		return allcar;
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
			"ResponseAllCar{" + 
			"error_msg = '" + errorMsg + '\'' + 
			",allcar = '" + allcar + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}