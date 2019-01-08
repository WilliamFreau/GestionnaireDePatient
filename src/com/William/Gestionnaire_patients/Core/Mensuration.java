package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Core.db.db_mensuration;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;


import java.sql.SQLException;

/**
 * Created by wfrea_000 on 20/04/2016.
 */
public class Mensuration {

    private long[] l_id;

    private double[][] valeur;      //double[nb de record][0->13]

    public Mensuration(long[] liste_id)
    {
        l_id = liste_id;
        if(liste_id.length!=0) {
            Console_debug.getInstance().m_debug("Il y a des mensuration enregistré nombre: " + liste_id.length);
            valeur = new double[liste_id.length][14];
            fill_valeur();
        }
    }

    private void fill_valeur()
    {
        int i = 0;
        for(long el:l_id)
        {
            double[] get = db_mensuration.get_mens_from_id(el);
            for(int j = 0 ; j < get.length ; j++)
            {
                valeur[i][j] = get[j];
            }
            i++;
        }
    }

    public double[] get_valeur(int position)
    {
        return valeur[position];
    }

    public void set_valeur(double[] value, int emp)
    {
        for(int i = 0 ; i < 14 ; i++)
        {
            valeur[emp][i] = value[i];
        }
    }

    public int get_nb_record()
    {
        return l_id.length;
    }

    public long get_id(int emp)
    {
        return l_id[emp];
    }

    public String toString()
    {
        String returned = "";
        for(double[] a : valeur)
        {
            for(double b:a)
            {
                returned += String.valueOf(b) + ",";
            }
            returned += "\n";
        }
        return returned;
    }
}