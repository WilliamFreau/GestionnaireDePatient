package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Core.db.db_patient;
import com.William.Gestionnaire_patients.Fenetre_alert.no_bdd_file;
import com.William.Gestionnaire_patients.Util_fonctions.M_File;

import java.io.File;
import java.io.IOException;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class Core_bdd {

    /**
     * Les cores des tables
     */
    private db_patient table_patient = new db_patient();

    public Core_bdd() {

    }

    protected void init()
    {
        if(!bdd_exist())
        {
            //Demande s'il crée une BDD
            no_bdd_file diag_no_bdd = new no_bdd_file();
            diag_no_bdd.pack();
            diag_no_bdd.setVisible(true);
        }
    }

    private boolean bdd_exist() {
        File t_bdd = new File(System.getProperty("user.dir") + "/Data/BDD.db");
        return t_bdd.exists();
    }

    public void create_emp_bdd()
    {
        try {
            M_File.copyFile(new File(System.getProperty("user.dir") + "/Data/BDD_Vide.db"), new File(System.getProperty("user.dir") + "/Data/BDD.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public long add_patient(String p_nom,  String p_adresse, String p_comment, String p_rmq) {
        return db_patient.add_patient(p_nom, p_adresse, p_comment, p_rmq);
    }

    public Patient get_Patient_By_ID(long p_id) {
        return db_patient.get_patient_By_Id(p_id);
    }

    public Patient[] get_Patient() {
        Object[] list_id = db_patient.get_patients_Id();

        Patient[] returned = new Patient[list_id.length];

        for(int i = 0 ; i < list_id.length ; i ++)
        {
            returned[i] = db_patient.get_patient_By_Id(i+1);
        }
        return returned;
    }


    public Patient get_last_patient() {
        int l_id = db_patient.get_last_id();
        return db_patient.get_patient_By_Id( (long)l_id );
    }

    /**
     * Close la bdd et la commit
     */
    public void close() {
    }

    public void save_patient(Patient patient) {
        db_patient.save_patient(patient);
    }

}
