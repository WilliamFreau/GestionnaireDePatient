package com.William.Gestionnaire_patients.Comptabilité;

import com.William.Gestionnaire_patients.Core.db.db_transaction;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by william on 08/06/16.
 */
public class Compta_Glob extends JFrame implements ChangeListener {
    private JPanel root_pan;

    private JSpinner Spin_annee;

    private JEditorPane Affichage;




    public Compta_Glob()
    {
        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Comptabilité Globale");
        this.setResizable(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Spin_annee.setValue(Integer.valueOf(sdf.format(new Date())));

        Spin_annee.addChangeListener(this);

        try {
            update_ui();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        update_ui();
    }

    private void update_ui() {
        Affichage.setText("");
        String afficheur = "";
        double[] valeur = db_transaction.get_Transaction_From_Annee((int)Spin_annee.getValue());

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

        System.out.println(valeur.length);

        double somme_tout = 0;
        double somme_espece = 0;
        double somme_cheque = 0;

        for(int i = 0 ; i < valeur.length/3 ; i+=1)
        {
            afficheur += getMonth((int)valeur[3*i+1]);
            afficheur += ": ";
            afficheur += valeur[3*i];
            afficheur += " €";

            double s_espece = db_transaction.get_Transaction_From_Annee_Mois_Mode((int)valeur[3*i+2], (int)valeur[3*i+1], 0);
            double s_cheque = db_transaction.get_Transaction_From_Annee_Mois_Mode((int)valeur[3*i+2], (int)valeur[3*i+1], 1);

            afficheur += " Espéces: " + s_espece + " €";
            afficheur += " Chéques: " + s_cheque + " €";

            afficheur += "\r\n";

            somme_tout += valeur[3*i];
            somme_cheque += s_cheque;
            somme_espece += s_espece;
        }

        afficheur += "\r\n\r\n";
        afficheur += "Totale espéces: " + somme_espece + " €";
        afficheur += "\r\n";
        afficheur += "Totale chéques: " + somme_cheque + " €";
        afficheur += "\r\n";
        afficheur += "Totale: " + somme_tout + " €";



        Affichage.setText(afficheur);
    }

    String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

}
