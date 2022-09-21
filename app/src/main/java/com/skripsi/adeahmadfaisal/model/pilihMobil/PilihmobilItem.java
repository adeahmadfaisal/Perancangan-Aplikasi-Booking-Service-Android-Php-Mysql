package com.skripsi.adeahmadfaisal.model.pilihMobil;

import com.google.gson.annotations.SerializedName;

public class PilihmobilItem{

	@SerializedName("mycarid")
	private String mycarid;

	@SerializedName("cutomer_id")
	private String cutomerId;

	@SerializedName("no_machine")
	private String noMachine;

	@SerializedName("type_car")
	private String typeCar;

	@SerializedName("no_plat")
	private String noPlat;

	public void setMycarid(String mycarid){
		this.mycarid = mycarid;
	}

	public String getMycarid(){
		return mycarid;
	}

	public void setCutomerId(String cutomerId){
		this.cutomerId = cutomerId;
	}

	public String getCutomerId(){
		return cutomerId;
	}

	public void setTypeCar(String typeCar){
		this.typeCar = typeCar;
	}

	public String getTypeCar(){
		return typeCar;
	}

	public void setNoPlat(String noPlat){
		this.noPlat = noPlat;
	}

	public String getNoPlat(){
		return noPlat;
	}

	public void setNoMachine(String noMachine){
		this.noMachine = noMachine;
	}

	public String getNoMachine(){
		return noMachine;
	}

	@Override
 	public String toString(){
		return 
			"PilihmobilItem{" + 
			"mycarid = '" + mycarid + '\'' + 
			",cutomer_id = '" + cutomerId + '\'' +
			",no_machine = '" + noMachine + '\'' +
			",type_car = '" + typeCar + '\'' +
			",no_plat = '" + noPlat + '\'' + 
			"}";
		}
}