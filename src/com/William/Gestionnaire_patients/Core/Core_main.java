package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Fenetre.Fermeture;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class Core_main {

    /**
     * Les cores
     */
    protected Core_bdd core_bdd;
    protected Core_patient core_patient;

    /**
     * Instances
     */
    private static Core_main instance;

    //@TODO Fusioner les CORES ensemble pour en faire plus qu'un (moin d'internal call ==> moins grande compléxité)

    public Core_main() {
        core_bdd = new Core_bdd();
        core_patient = new Core_patient();
        instance = this;
        init();
    }

    public void init() {
        core_bdd.init();
        core_patient.init();
    }

    public long create_patient(String p_nom, String p_adresse, String p_comment, String p_rmq)
    {
        long id = core_bdd.add_patient(p_nom, p_adresse, p_comment, p_rmq);
        core_patient.add_patient(id, p_nom);
        return id;
    }

    public static Core_main getInstance() {return instance;}

    public Core_bdd get_core_bdd() {return core_bdd;}
    public Core_patient get_core_patient() {return core_patient;}

    public String[] get_Pat_str_list() {
        return core_patient.get_Pat_Liste_str();
    }

    public void save_patient_in_bdd(Patient patient)
    {
        this.core_bdd.save_patient(patient);
    }

    /**
     * Appeller lorsque on ferme la fenetre acceuil
     */
    public void close(Fermeture p_fermeture) {
        //ça créer et ça lance le thread de sauvegarde des patients
        p_fermeture.create_Thread(this.get_core_patient().get_pat_list(),p_fermeture);
        core_bdd.close();
    }
}
