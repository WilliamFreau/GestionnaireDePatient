package com.William.Gestionnaire_patients.Fenetre;

import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Util_fonctions.Log;

import javax.swing.*;

/**
 * Created by wfrea_000 on 29/03/2016.
 */
public class Fermeture extends JFrame{
    private JPanel root_p;
    private JLabel nom_pat;

    private Thread_fermeture t_ferm;

    public Fermeture()
    {
        this.setSize( 300,50 );
        this.setContentPane(root_p);
        this.setName("Gestionnaire de patient");
        this.setResizable(false);
    }

    public void update() {
        Patient actu = t_ferm.getCurr_patient();
        nom_pat.setText(actu.get_nom() + "." + String.valueOf(actu.get_id()));

    }

    public void create_Thread(Patient[] pat_list, Fermeture p_fermeture) {
        t_ferm = new Thread_fermeture( pat_list , p_fermeture );
        this.setVisible( true );
        t_ferm.start();
    }
}

/**
 * Thread qui permet de save les patient en fond
 * dans le même code que fermeture car seulement fermeture s'en sert
 */
class Thread_fermeture extends Thread {

    private Patient[] list;

    private Patient curr_patient;

    private Fermeture _parent;

    public Thread_fermeture(Patient[] p_list, Fermeture p_parent)
    {
        list=p_list;
        _parent = p_parent;
    }

    public void run()
    {
        for(Patient pat:list)
        {
            setCurr_patient(pat);
            pat.save();
            _parent.update();
            try {
                sleep( 50 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Ajout d'un message dans les logs de fin de session
        Log.getInstance().add_EnfOfSession();

        System.exit(0);
    }

    public void setCurr_patient(Patient pat)
    {
        curr_patient = pat;
    }

    public Patient getCurr_patient() {return curr_patient;}
}
