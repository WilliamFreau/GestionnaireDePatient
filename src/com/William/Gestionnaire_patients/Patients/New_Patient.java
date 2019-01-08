package com.William.Gestionnaire_patients.Patients;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Core_patient;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.db.db_patient;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import com.William.Gestionnaire_patients.Fenetre_alert.Aff_erreur;
import com.William.Gestionnaire_patients.Util_fonctions.Intern_Message;
import com.William.Gestionnaire_patients.Util_fonctions.Key;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class New_Patient extends My_Fen implements ActionListener {

    private JTextField txt_nom;
    private JTextField txt_adresse;

    private JEditorPane edit_comment;
    private JEditorPane edit_rmq;

    private JPanel n_pat_pan;

    private JButton validate;

    private JTextField txt_ville;

    private JFormattedTextField txt_CP;

    private JRadioButton H_genre;
    private JRadioButton F_genre;
    private JRadioButton JA_oui;
    private JRadioButton JA_non;

    private JFormattedTextField txt_tel;
    private JFormattedTextField txt_DDN;

    private JEditorPane edit_passe;
    private JEditorPane edit_medical;
    private JEditorPane edit_TTT;
    private JEditorPane edit_projet;
    private JEditorPane edit_visite;
    private JEditorPane edit_PA;
    private JEditorPane edit_T_a_faire;

    private Patient_JP parent_;

    public New_Patient(Patient_JP p_parent) {
        setTitle("Crée un nouveau patient");
        setVisible(true);
        setSize(750,530);
        setContentPane(n_pat_pan);

        parent_ = p_parent;

        init();
    }

    private void init() {

        //Creation des mask pour les formatted text field
        MaskFormatter CP_format = createFormatter("## ###");
        CP_format.install(txt_CP);
        MaskFormatter Tel_format = createFormatter("## ## ## ## ##");
        Tel_format.install(txt_tel);
        MaskFormatter DDN_format = createFormatter("##/##/####");
        DDN_format.install(txt_DDN);

        validate.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==validate)
        {
            long id = -1;

            boolean genre=false;
            boolean JA = false;

            if(H_genre.isValid())
            {
                genre = false;
            }
            if(F_genre.isValid())
            {
                genre = true;
            }
            if(JA_oui.isValid())
            {
                JA=true;
            }
            if(JA_non.isValid()) {
                JA = false;
            }

            try {
                Patient pat = new Patient(txt_nom.getText(), txt_adresse.getText(), txt_ville.getText(), txt_CP.getText(), edit_comment.getText(), edit_rmq.getText()
                    , txt_tel.getText(), genre, JA, Util.get_sdf_date().parse(txt_DDN.getText()), edit_passe.getText(), edit_medical.getText(), edit_TTT.getText()
                    , edit_projet.getText(), edit_visite.getText(), edit_PA.getText(), edit_T_a_faire.getText());
                db_patient.add_patient(pat);
                Core_main.getInstance().get_core_patient().add_patient((long)db_patient.get_last_id(), pat.get_nom());
            } catch (ParseException e1) {
                //Erreur, patient non creer
                String erreur_mes = "Une erreur inconnue est survenue, le patient n'a pas été créer";
                String f_inter = Intern_Message.get_message( Key.ERR_ON_PAT_CREATE);
                if(f_inter.equals(""))
                {
                    Aff_erreur p = new Aff_erreur(erreur_mes);
                }
                else
                {
                    Aff_erreur p = new Aff_erreur(erreur_mes + "\n" + "La trace a été trouvé merci de l'envoyer par mail au dèvellopeur: \n" + f_inter);
                }
            }


            Core_main.getInstance().get_core_patient().add_last();
            parent_.update_aff();
            setVisible(false);
            dispose();
        }
    }


    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
}