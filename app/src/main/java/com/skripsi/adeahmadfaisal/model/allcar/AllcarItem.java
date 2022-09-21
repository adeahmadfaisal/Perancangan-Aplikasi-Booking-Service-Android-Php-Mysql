package com.skripsi.adeahmadfaisal.model.allcar;

import com.google.gson.annotations.SerializedName;

public class AllcarItem{

	@SerializedName("type_engine")
	private String typeEngine;

	@SerializedName("price")
	private String price;

	@SerializedName("typecar")
	private String typecar;

	@SerializedName("carid")
	private String carid;

	public void setTypeEngine(String typeEngine){
		this.typeEngine = typeEngine;
	}

	public String getTypeEngine(){
		return typeEngine;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
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
			"AllcarItem{" + 
			"type_engine = '" + typeEngine + '\'' + 
			",price = '" + price + '\'' + 
			",typecar = '" + typecar + '\'' + 
			",carid = '" + carid + '\'' + 
			"}";
		}
}