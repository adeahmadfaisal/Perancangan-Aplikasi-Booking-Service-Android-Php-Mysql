package com.skripsi.adeahmadfaisal.model.sparepart;

import com.google.gson.annotations.SerializedName;

public class SparepartItem{

	@SerializedName("harga")
	private String harga;

	@SerializedName("id_sparepart")
	private String idSparepart;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("car_id")
	private String carId;

	@SerializedName("name_sparepart")
	private String nameSparepart;

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setIdSparepart(String idSparepart){
		this.idSparepart = idSparepart;
	}

	public String getIdSparepart(){
		return idSparepart;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setCarId(String carId){
		this.carId = carId;
	}

	public String getCarId(){
		return carId;
	}

	public void setNameSparepart(String nameSparepart){
		this.nameSparepart = nameSparepart;
	}

	public String getNameSparepart(){
		return nameSparepart;
	}

	@Override
 	public String toString(){
		return 
			"SparepartItem{" + 
			"harga = '" + harga + '\'' + 
			",id_sparepart = '" + idSparepart + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",car_id = '" + carId + '\'' + 
			",name_sparepart = '" + nameSparepart + '\'' + 
			"}";
		}
}