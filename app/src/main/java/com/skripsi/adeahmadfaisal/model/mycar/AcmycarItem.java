package com.skripsi.adeahmadfaisal.model.mycar;

import com.google.gson.annotations.SerializedName;

public class AcmycarItem{

	@SerializedName("no_body")
	private String noBody;

	@SerializedName("mycarid")
	private String mycarid;

	@SerializedName("no_machine")
	private String noMachine;

	@SerializedName("no_plat")
	private String noPlat;

	@SerializedName("typecar")
	private String typecar;

	@SerializedName("carid")
	private String carid;

	public void setNoBody(String noBody){
		this.noBody = noBody;
	}

	public String getNoBody(){
		return noBody;
	}

	public void setMycarid(String mycarid){
		this.mycarid = mycarid;
	}

	public String getMycarid(){
		return mycarid;
	}

	public void setNoMachine(String noMachine){
		this.noMachine = noMachine;
	}

	public String getNoMachine(){
		return noMachine;
	}

	public void setNoPlat(String noPlat){
		this.noPlat = noPlat;
	}

	public String getNoPlat(){
		return noPlat;
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
			"AcmycarItem{" + 
			"no_body = '" + noBody + '\'' + 
			",mycarid = '" + mycarid + '\'' + 
			",no_machine = '" + noMachine + '\'' + 
			",no_plat = '" + noPlat + '\'' + 
			",typecar = '" + typecar + '\'' + 
			",carid = '" + carid + '\'' + 
			"}";
		}
}