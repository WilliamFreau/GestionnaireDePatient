package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import java.awt.*;
import java.util.Date;

/**
 * Created by william on 07/06/16.
 */
public class Transaction {

    private int id_transaction;
    private int id_patient;
    private double montant;
    private int mode_regler;
    private Date d_regler;
    private String element;

    /**
     * Id dans la table transaction, le reste sera completer avec les donnée de la DD si l'id existe
     * @param p_id_transaction
     */
    public Transaction(int p_id_transaction)
    {
        this.id_transaction = p_id_transaction;
    }

    /**
     * Constructeur avec tout les paramétres
     * @param p_id_transaction
     * @param p_id_patient
     * @param p_montant
     * @param p_mode_regler
     */
    public Transaction(int p_id_transaction, int p_id_patient, double p_montant, int p_mode_regler, Date p_d_regler, String p_element)
    {
        this.id_transaction = p_id_transaction;
        this.id_patient = p_id_patient;
        this.montant = p_montant;
        this.mode_regler = p_mode_regler;
        this.d_regler = p_d_regler;
        this.element = p_element;
    }

    /**
     * Constructeur avec les id du patient et le montant
     * @param p_id_patient
     * @param p_montant
     */
    public Transaction(int p_id_patient, double p_montant, int p_mode_regler, Date p_d_regler, String p_element)
    {
        this.id_patient = p_id_patient;
        Console_debug.getInstance().m_debug("Id patient in transaction: " + p_id_patient);
        this.montant = p_montant;
        this.mode_regler = p_mode_regler;
        this.d_regler = p_d_regler;
        this.element = p_element;
    }

    /**
     * Constructeur vide
     */
    public Transaction(){}


    /*
    GETTER && SETTER
     */
    public int getId_patient(){return id_patient;}

    public int getId_transaction(){return id_transaction;}

    public double getMontant(){return montant;}

    public int getMode_regler(){return mode_regler;}

    public Date getD_regler(){return d_regler;}

    public String get_element(){return element;}

    public String toString(){
        return "Patient: " + Core_main.getInstance().get_core_patient().get_pat_name_from_id(id_patient) +
                " Montant: " + montant + "€  Date: " + Util.get_sdf_date().format(d_regler);
    }

    public Color getColor() {
        if(montant>0)
        {
            return new Color(129,255,112);
        }
        else
        {
            return new Color(255,100,100);
        }
    }

    public void setid_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public void setMode_Regler(int mode_Regler) {
        this.mode_regler = mode_Regler;
    }

    public void setD_regler(Date d_regler) {
        this.d_regler = d_regler;
    }

    public void setElement(String p_element) { this.element = p_element; }

    public void setmontant(Double montant) {
        this.montant = montant;
    }
}
