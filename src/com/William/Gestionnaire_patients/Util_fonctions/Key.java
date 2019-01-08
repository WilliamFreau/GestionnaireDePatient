package com.William.Gestionnaire_patients.Util_fonctions;

/**
 * Created by wfrea_000 on 12/04/2016.
 */
public enum Key {
    CORE_PAT_UPDATE("Update_p_list"),
    CORE_PAT_ADD_L("Add_l_plist"),
    ERR_ON_PAT_CREATE("Err_On_Pat_Create"),
    ERR_ON_ADD_MENS("Err_on_add_mens");

    private String value = "";
    Key(String value)
    {
        this.value = value;
    }

    public String toString(){return value;}
}
