package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Util_fonctions.Util;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by william on 27/05/16.
 */
public class RDV {

    private long id;
    private Date date;
    private long pat_id;
    private Date h_deb;
    private Date h_fin;
    private String remarque;
    private String motif;
    private int etat;


    /**
     * Constructeur vide
     */
    public RDV() {}

    /**
     * Creer un RDV à partir d'un ID
     * @param p_id l'id du RDV dans la table
     */
    public RDV(long p_id)
    {

    }

    /**
     * Creer un rdv à partir des informations
     * @param p_id_rdv  l'id du rdv dans la table
     * @param p_id_patient  l'id du patient
     * @param p_etat    l'Etat du RDV
     * @param p_date    la date du RDV
     * @param p_h_deb   l'heure de début du RDV
     * @param p_h_fin   l'heure de fin du RDV
     * @param p_remarque Remarque du RDV
     * @param p_motif   Motif du RDV
     */
    public RDV(long p_id_rdv, long p_id_patient, int p_etat, Date p_date,
               Date p_h_deb, Date p_h_fin, String p_remarque, String p_motif)
    {
        this.id = p_id_rdv;
        this.pat_id = p_id_patient;
        this.etat = p_etat;
        this.date = p_date;
        this.h_deb = p_h_deb;
        this.h_fin = p_h_fin;
        this.remarque = p_remarque;
        this.motif = p_motif;
    }

    /**
     * Creer un rdv à partir des informations
     * @param p_id_patient  l'id du patient
     * @param p_etat    l'Etat du RDV
     * @param p_date    la date du RDV
     * @param p_h_deb   l'heure de début du RDV
     * @param p_h_fin   l'heure de fin du RDV
     * @param p_remarque Remarque du RDV
     * @param p_motif   Motif du RDV
     */
    public RDV(long p_id_patient, int p_etat, Date p_date,
               Date p_h_deb, Date p_h_fin, String p_remarque, String p_motif)
    {
        this.pat_id = p_id_patient;
        this.etat = p_etat;
        this.date = p_date;
        this.h_deb = p_h_deb;
        this.h_fin = p_h_fin;
        this.remarque = p_remarque;
        this.motif = p_motif;


    }

    /**
     * GETTER && SETTER
     */
    public long get_id(){return id;}

    public long get_pat_id(){return pat_id;}

    public int get_etat(){return etat;}

    public Date get_date(){return date;}

    public Date get_h_deb(){return h_deb;}

    public Date get_h_fin(){return h_fin;}

    public String get_remarque(){return remarque;}

    public String get_motif(){return motif;}

    public String to_list_str(){
        return "Début: " + Util.get_sdf_date_heure().format(this.get_h_deb()) + " Fin: " + Util.get_sdf_date_heure().format(this.get_h_fin())
                + " Patient: " + Core_main.getInstance().core_patient.get_pat_name_from_id(this.pat_id)
                + " Motif: " + this.get_motif() + " Remarque: " + this.get_remarque();
    }

    public String to_list_patient_rdv()
    {
        return "Date: " + Util.get_sdf_date().format(this.get_date()) + "Début: " + Util.get_sdf_date_heure().format(this.get_h_deb()) + " Fin: " + Util.get_sdf_date_heure().format(this.get_h_fin())
                + " Patient: " + Core_main.getInstance().core_patient.get_pat_name_from_id(this.pat_id)
                + " Motif: " + this.get_motif() + " Remarque: " + this.get_remarque();
    }

    public String to_acc_aff()
    {
        DateFormat format_h = new SimpleDateFormat("HH:mm");
        return "<html>Début: " + format_h.format(this.get_h_deb()) + " Fin: " + format_h.format(this.get_h_fin())
                + " Patient: " + Core_main.getInstance().core_patient.get_pat_name_from_id(this.pat_id)
                + "<br/> Motif: " + this.get_motif() + " Remarque: " + this.get_remarque() + "</html>";
    }

    public String toString(){return to_list_str();}

    public void setDate(Date date) {
        this.date = date;
    }

    public void set_h_Deb(Date _h_Deb) {
        this.h_deb = _h_Deb;
    }

    public void set_h_fin(Date _h_fin) {
        this.h_fin = _h_fin;
    }

    public void set_pat_id(long _pat_id) {
        this.pat_id = _pat_id;
    }

    public void set_Remarque(String _Remarque) {
        this.remarque = _Remarque;
    }

    public void set_Motif(String _Motif) {
        this.motif = _Motif;
    }

    public void setetat(int etat) {
        this.etat = etat;
    }

    public Color getColor() {
        switch (etat%10) {
            case 1: //Décaller
                return new Color(86, 255, 74);
            case 2: //Important
                return new Color(255, 57, 57);
            case 3: //Exceptionelle
                return new Color(241,58,255);
            default:    //Par defaut
                return new Color(255, 255, 255);
        }
    }
}
