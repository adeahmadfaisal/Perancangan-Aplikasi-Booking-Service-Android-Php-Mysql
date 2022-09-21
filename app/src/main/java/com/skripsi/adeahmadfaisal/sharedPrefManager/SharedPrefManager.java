package com.skripsi.adeahmadfaisal.sharedPrefManager;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fariz ramadhan.
 * website : www.farizdotid.com
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */


public class SharedPrefManager {

    public static final String SP_SkripsiApp    = "SP_SkripsiApp";
    public static final String SP_Nama          = "SP_nama";
    public static final String SP_Email         = "SP_email";
    public static final String SP_Uid           = "SP_uid";
    public static final String SP_SudahLogin    = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_SkripsiApp, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){

        return sp.getString(SP_Nama, "");
    }

    public String getSPUid() {
        return  sp.getString(SP_Uid, "");
    }

    public String getSPEmail(){

        return sp.getString(SP_Email, "");
    }

    public Boolean getSPSudahLogin(){

        return sp.getBoolean(SP_SudahLogin, false);
    }
}
