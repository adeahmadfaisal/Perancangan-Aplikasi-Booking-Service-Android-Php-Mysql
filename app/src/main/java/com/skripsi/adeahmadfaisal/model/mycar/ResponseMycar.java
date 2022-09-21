package com.skripsi.adeahmadfaisal.model.mycar;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMycar{

	@SerializedName("acmycar")
	private List<AcmycarItem> acmycar;

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	public void setAcmycar(List<AcmycarItem> acmycar){
		this.acmycar = acmycar;
	}

	public List<AcmycarItem> getAcmycar(){
		return acmycar;
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
			"ResponseMycar{" + 
			"acmycar = '" + acmycar + '\'' + 
			",error_msg = '" + errorMsg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}