package com.skripsi.adeahmadfaisal.model.sparepart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSparePart{

	@SerializedName("sparepart")
	private List<SparepartItem> sparepart;

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	public void setSparepart(List<SparepartItem> sparepart){
		this.sparepart = sparepart;
	}

	public List<SparepartItem> getSparepart(){
		return sparepart;
	}

	public void setErrorMsg(String errorMsg){

		this.errorMsg = errorMsg;
	}

	public String getErrorMsg(){

		return errorMsg;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}

	public boolean isError(){

		return error;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSparePart{" + 
			"sparepart = '" + sparepart + '\'' + 
			",error_msg = '" + errorMsg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}