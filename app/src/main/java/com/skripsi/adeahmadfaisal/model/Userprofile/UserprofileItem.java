package com.skripsi.adeahmadfaisal.model.Userprofile;

import com.google.gson.annotations.SerializedName;

public class UserprofileItem{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("password")
	private String password;

	@SerializedName("gender")
	private String gender;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("addres")
	private String addres;

	@SerializedName("email")
	private String email;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAddres(String addres){
		this.addres = addres;
	}

	public String getAddres(){
		return addres;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"UserprofileItem{" + 
			"birthday = '" + birthday + '\'' + 
			",password = '" + password + '\'' + 
			",gender = '" + gender + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",addres = '" + addres + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}