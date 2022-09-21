package com.skripsi.adeahmadfaisal.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.1.14/skripsi-client/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
