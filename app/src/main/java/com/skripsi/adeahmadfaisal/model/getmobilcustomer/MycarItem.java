package com.skripsi.adeahmadfaisal.model.getmobilcustomer;


import com.google.gson.annotations.SerializedName;

public class MycarItem{

	@SerializedName("typecar")
	private String typecar;

	@SerializedName("mycarid")
	private String mycarid;

	@SerializedName("carid")
	private String carid;

	public void setMycarid(String mycarid){
		this.mycarid = mycarid;
	}

	public String getMycarid(){
		return mycarid;
	}

	public void setTypecar(String typecar){
		this.typecar = typecar;
	}

	public String getTypecar(){
		return typecar;
	}

	public void setCarid(String carid){
		this.carid = carid;
	}

	public String getCarid(){
		return carid;
	}

	@Override
 	public String toString(){
		return 
			"MycarItem{" + 
			"typecar = '" + typecar + '\'' + 
			",carid = '" + carid + '\'' + 
			"}";
		}
}