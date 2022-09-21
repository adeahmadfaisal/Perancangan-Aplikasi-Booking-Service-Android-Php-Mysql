package com.skripsi.adeahmadfaisal.model.pilihMobil;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePilihmobil{

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	@SerializedName("pilihmobil")
	private List<PilihmobilItem> pilihmobil;

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

	public void setPilihmobil(List<PilihmobilItem> pilihmobil){
		this.pilihmobil = pilihmobil;
	}

	public List<PilihmobilItem> getPilihmobil(){
		return pilihmobil;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePilihmobil{" + 
			"error_msg = '" + errorMsg + '\'' + 
			",error = '" + error + '\'' + 
			",pilihmobil = '" + pilihmobil + '\'' + 
			"}";
		}
}