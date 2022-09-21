package com.skripsi.adeahmadfaisal.model;

import com.google.gson.annotations.SerializedName;

public class Booking{

    @SerializedName("date")
    private String date;

    @SerializedName("mycar")
    private String mycar;

    @SerializedName("problem")
    private String problem;

    @SerializedName("hour")
    private String hour;

    @SerializedName("type_car")
    private String typeCar;

    @SerializedName("idbooking")
    private String idbooking;

    @SerializedName("type_service")
    private String typeService;

    @SerializedName("idmobil")
    private String idmobil;

    @SerializedName("status")
    private String status;

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setMycar(String mycar){
        this.mycar = mycar;
    }

    public String getMycar(){
        return mycar;
    }

    public void setProblem(String problem){
        this.problem = problem;
    }

    public String getProblem(){
        return problem;
    }

    public void setHour(String hour){
        this.hour = hour;
    }

    public String getHour(){
        return hour;
    }

    public void setTypeCar(String typeCar){
        this.typeCar = typeCar;
    }

    public String getTypeCar(){
        return typeCar;
    }

    public void setIdbooking(String idbooking){
        this.idbooking = idbooking;
    }

    public String getIdbooking(){
        return idbooking;
    }

    public void setTypeService(String typeService){
        this.typeService = typeService;
    }

    public String getTypeService(){
        return typeService;
    }

    public void setIdmobil(String idmobil){
        this.idmobil = idmobil;
    }

    public String getIdmobil(){
        return idmobil;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "BookingItem{" +
                        "date = '" + date + '\'' +
                        ",mycar = '" + mycar + '\'' +
                        ",problem = '" + problem + '\'' +
                        ",hour = '" + hour + '\'' +
                        ",type_car = '" + typeCar + '\'' +
                        ",idbooking = '" + idbooking + '\'' +
                        ",type_service = '" + typeService + '\'' +
                        ",idmobil = '" + idmobil + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}