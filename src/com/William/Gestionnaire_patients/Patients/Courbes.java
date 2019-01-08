package com.William.Gestionnaire_patients.Patients;

import com.William.Gestionnaire_patients.Core.Mensuration;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by wfrea_000 on 20/04/2016.
 */
public class Courbes extends JPanel {

    private JPanel root_pan;
    private JPanel panel_gauche;
    private JPanel panel_droit;


    private JTextField txt_poid;
    private JTextField txt_taille;
    private JTextField txt_cou;
    private JTextField txt_poitrine;
    private JTextField txt_bras;
    private JTextField txt_estomac;
    private JTextField txt_ventre;
    private JTextField txt_cuisse;
    private JTextField txt_mollet;
    private JTextField txt_MG;
    private JTextField txt_eau;
    private JTextField txt_M;
    private JTextField txt_hanche;

    private JTextField txt_imc;

    private JTextField[] txt_field = {txt_poid, txt_taille, txt_imc, txt_cou, txt_poitrine, txt_bras, txt_estomac,
                                        txt_ventre, txt_hanche, txt_cuisse, txt_mollet, txt_MG, txt_M, txt_eau};

    private void createUIComponents() {}

    public Courbes() {
        init();
    }

    private void init() {
        // Listen for changes in the text
        DocumentListener doc_lis = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                //warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            void warn() {
                if(txt_poid.getText().toString().length() > 0 && txt_taille.getText().toString().length() > 0)
                {txt_imc.setText(String.valueOf(Util.imc(Double.valueOf(txt_poid.getText().toString()), Double.valueOf(txt_taille.getText().toString()))));}
                else
                {txt_imc.setText("");}
            }
        };

        txt_poid.getDocument().addDocumentListener(doc_lis);
        txt_taille.getDocument().addDocumentListener(doc_lis);
    }


    /**
     * Affiche les valeurs en fonctionde actu_aff
     */
    protected void aff_relever(double[] valeur) {
        for(int i  = 0 ; i < 14 ; i++)
        {
            txt_field[i].setText(String.valueOf(valeur[i]));
        }
    }

    protected double[] get_valeur()
    {
        double[] valeur = new double[14];
        for(int i = 0 ; i < 14 ; i++)
        {
            if(txt_field[i].getText().isEmpty())
            {
                txt_field[i].setText("-1");
            }
            valeur[i] = Double.valueOf(txt_field[i].getText());
        }
        return valeur;
    }


    /**
     * RAZ des fileds
     */
    public void RAZ_form(){
        txt_poid.setText("");
        txt_taille.setText("");
        txt_cou.setText("");
        txt_poitrine.setText("");
        txt_bras.setText("");
        txt_estomac.setText("");
        txt_ventre.setText("");
        txt_hanche.setText("");
        txt_cuisse.setText("");
        txt_mollet.setText("");
        txt_MG.setText("");
        txt_eau.setText("");
        txt_M.setText("");
        txt_imc.setText("");
    }
}
