package com.William.Gestionnaire_patients.Util_fonctions;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Core_patient;

import javax.swing.*;
import java.text.SimpleDateFormat;

/**
 * Created by william on 07/05/16.
 */
public class Util {
    /**
     * Renvoie l'imc en fonction du poid et de la taille
     *
     * poid/(taille*taille)
     *
     * @param poid
     * @param taille
     * @return
     */
    public static double imc(double poid, double taille)
    {
        taille /= 100;
        return poid/(taille*taille);
    }

    public static SimpleDateFormat get_sdf_date_heure(){return new SimpleDateFormat("HH:mm");}

    public static SimpleDateFormat get_sdf_date(){return new SimpleDateFormat("dd/MM/yyyy");}

    public static SimpleDateFormat get_format_for_SQL(){return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");}

    public static JComboBox filler_with_patient(JComboBox a_fill)
    {
        String[] n_pat = Core_main.getInstance().get_Pat_str_list();

        a_fill.removeAllItems();

        for(String nom:n_pat)
        {
            a_fill.addItem(nom);
            Console_debug.getInstance().m_debug("Patient: " + nom);
        }

        return a_fill;
    }

    public static JComboBox filler_cmb_etat(JComboBox a_fill)
    {
        a_fill.removeAllItems();
        String[] etat = {"Normale", "Décaller", "Important", "Exceptionelle"};
        for(String str:etat)
        {
            a_fill.addItem(str);
        }

        return a_fill;
    }
}
