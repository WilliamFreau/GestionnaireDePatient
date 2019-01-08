package com.William.Gestionnaire_patients.Patients;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wfrea_000 on 06/03/2016.
 */
public class Patient_JP extends JPanel implements ActionListener {
    private JComboBox pat_cmb_box;

    private JButton pat_open_patient;
    private JButton pat_new_patient;

    private JPanel root_pan;

    private JLabel aff_Adresse;

    private JLabel aff_Telephone;

    private JRadioButton JA_oui;
    private JRadioButton JA_non;

    private JLabel aff_ddn;

    private JEditorPane edit_comment;
    private JEditorPane edit_rmq;
    private JEditorPane edit_passe;
    private JEditorPane edit_medical;
    private JEditorPane edit_TTT;
    private JEditorPane edit_projet;
    private JEditorPane edit_visite;
    private JEditorPane edit_PA;
    private JEditorPane edit_T_a_faire;


    private JButton mensurationButton;
    private JButton pat_rdv_pat_select;
    private JButton ficheRécapitulativeButton;


    private Patient actu_patient = null;


    //@TODO Finir l'affichage des nouvelles information du patient

    public Patient_JP() {
        init();
    }

    private void init() {
        pat_new_patient.addActionListener( this );
        pat_open_patient.addActionListener( this );
        pat_cmb_box.addActionListener( this );
        mensurationButton.addActionListener( this );
        pat_rdv_pat_select.addActionListener( this );

        //Ajout des item dans le combo
        update_combo();

        //Update de l'afichage
        show_patient();
    }

    private void createUIComponents() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pat_new_patient)
        {
            //Nouveau patient
            New_Patient n_pat = new New_Patient(this);
        }
        if(e.getSource() == pat_open_patient)
        {
            Core_main.getInstance().get_core_patient().open_pat_folder(pat_cmb_box.getSelectedIndex(),pat_cmb_box.getSelectedItem().toString());
        }
        if(e.getSource() == pat_cmb_box)
        {
            show_patient();
        }
        if(e.getSource() == mensurationButton)
        {
            Mensuration_fen mens = new Mensuration_fen(this, actu_patient);
        }

        if(e.getSource() == pat_rdv_pat_select)
        {
            Patient pat = Core_main.getInstance().get_core_patient().get_pat_list()[pat_cmb_box.getSelectedIndex()];
            Patient_rdv fen = new Patient_rdv(this, pat);
        }
    }

    public void update_combo()
    {
        Util.filler_with_patient(pat_cmb_box);
    }

    /**
     * Actualise le patient affiché
     */
    public void show_patient()
    {
        if(actu_patient != null)
        {
            //Save du patient commentaire et remarque
            actu_patient.setCommentaire(edit_comment.getText());
            actu_patient.setRemarque(edit_rmq.getText());
            actu_patient.setPasse(edit_passe.getText());
            actu_patient.setMedical(edit_medical.getText());
            actu_patient.setTTT(edit_TTT.getText());
            actu_patient.setProjet(edit_projet.getText());
            actu_patient.setVisite(edit_visite.getText());
            actu_patient.setPlan_alimentaire(edit_PA.getText());
            actu_patient.setT_a_faire(edit_T_a_faire.getText());

            Console_debug.getInstance().m_debug(String.valueOf(actu_patient.isJA()));


            if(actu_patient.isJA())
            {
                JA_oui.setSelected(true);
            }
            else
            {
                JA_non.setSelected(true);
            }

            Core_main.getInstance().save_patient_in_bdd( actu_patient );
        }

        try {
            int index = pat_cmb_box.getSelectedIndex();
            actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];

            //Update de l'affichage
            aff_Adresse.setText( actu_patient.getAdresse() +" " + actu_patient.getVille() + " " + actu_patient.getCP() );

            if(actu_patient.isJA())
            {
                JA_oui.setSelected(true);
            }
            else
            {
                JA_non.setSelected(true);
            }

            aff_Telephone.setText(actu_patient.getTelephone());
            aff_ddn.setText(Util.get_sdf_date().format(actu_patient.getDDN()));

            edit_comment.setText( actu_patient.getCommentaire() );
            edit_rmq.setText( actu_patient.getRemarque() );
            edit_passe.setText( actu_patient.getPasse() );
            edit_medical.setText( actu_patient.getMedical() );
            edit_TTT.setText( actu_patient.getTTT() );
            edit_projet.setText( actu_patient.getProjet() );
            edit_visite.setText( actu_patient.getVisite() );
            edit_PA.setText( actu_patient.getPlan_alimentaire() );
            edit_T_a_faire.setText( actu_patient.getT_a_faire() );


        }catch(Exception e){}
    }

    public void update_aff()
    {
        update_combo();
    }

    public void set_patient(long pat_id) {
        Patient pat = Core_main.getInstance().get_core_patient().get_patient_from_id((int) pat_id);
        pat.open_folder();

        int i = 0;
        for(Patient p:Core_main.getInstance().get_core_patient().get_pat_list())
        {
            if(p.get_id()==pat.get_id())
            {
                pat_cmb_box.setSelectedIndex(i);
                break;
            }
            i++;
        }
    }
}