package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Core.db.db_mensuration;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.M_File;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by wfrea_000 on 12/03/2016.
 */
public class Patient {

    private long id;

    private String nom;
    private String adresse;
    private String ville;
    private String CP;
    private String commentaire;
    private String remarque;
    private String telephone;
    private boolean genre;  //False: Homme     True: Femme
    private boolean JA;     //True: Oui        False: Non
    private Date DDN;
    private int age;
    private String passe;
    private String medical;
    private String TTT;
    private String projet;
    private String visite;
    private String plan_alimentaire;
    private String T_a_faire;

    private File m_file;
    private File m_file_zip;


    //les mensurations
    private Mensuration mensuration;

    /**
     * Constructeur vide
     */
    public Patient(){}

    /**
     * Constructeur a partir de l'id et les autres infos vont ce completer à partir de la BDD
     * @param p_id
     */
    public Patient(long p_id) {
        Patient get = Core_main.getInstance().core_bdd.get_Patient_By_ID(p_id);
        this.id = p_id;
        this.nom = get.get_nom();
        this.adresse = get.adresse;
        this.commentaire = get.commentaire;
        this.remarque = get.remarque;

        m_file = new File(System.getProperty("user.dir") + "/Data/U_Patients/" + nom + "." + id);
        m_file_zip = new File(System.getProperty("user.dir") + "/Data/Patients/" + nom + "." + id + ".zip");

        build_mensuration();
    }

    /**
     * Constructeur de Patient à partir des infos du patient
     * @param p_id
     * @param p_nom
     * @param p_adresse
     * @param p_commentaire
     * @param p_remarque
     */
    public Patient(long p_id, String p_nom, String p_adresse, String p_commentaire, String p_remarque)
    {
        this.id = p_id;
        this.nom = p_nom;
        this.adresse = p_adresse;
        this.commentaire = p_commentaire;
        this.remarque = p_remarque;

        m_file = new File(System.getProperty("user.dir") + "/Data/U_Patients/" + nom + "." + id);
        m_file_zip = new File(System.getProperty("user.dir") + "/Data/Patients/" + nom + "." + id + ".zip");

        build_mensuration();
    }

    /**
     * Constructeur avec tout les params
     * @param p_nom
     * @param p_adresse
     * @param p_commentaire
     * @param p_remarque
     * @param p_telephone
     * @param p_genre
     * @param p_JA
     * @param p_DDN
     * @param p_passe
     * @param p_medical
     * @param p_TTT
     * @param p_projet
     * @param p_visite
     * @param p_plan_alimentaire
     * @param p_T_a_faire
     */
    public Patient(String p_nom, String p_adresse, String p_ville, String p_CP, String p_commentaire, String p_remarque, String p_telephone, boolean p_genre, boolean p_JA, Date p_DDN,
                    String p_passe, String p_medical, String p_TTT, String p_projet, String p_visite, String p_plan_alimentaire, String p_T_a_faire)
    {
        this.nom = p_nom;
        this.adresse = p_adresse;
        this.ville = p_ville;
        this.CP = p_CP;
        this.commentaire = p_commentaire;
        this.remarque = p_remarque;
        this.telephone=p_telephone;
        this.genre=p_genre;
        this.JA = p_JA;
        this.DDN = p_DDN;
        this.passe = p_passe;
        this.medical = p_medical;
        this.TTT = p_TTT;
        this.projet = p_projet;
        this.visite = p_visite;
        this.plan_alimentaire = p_plan_alimentaire;
        this.T_a_faire = p_T_a_faire;

        m_file = new File(System.getProperty("user.dir") + "/Data/U_Patients/" + nom + "." + id);
        m_file_zip = new File(System.getProperty("user.dir") + "/Data/Patients/" + nom + "." + id + ".zip");

        calcul_age();

        build_mensuration();
    }

    public void build_mensuration()
    {
        mensuration = new Mensuration(db_mensuration.get_id_mens_from_id_pat(id));
    }

    private void calcul_age()
    {
        Date d = new Date();
        long difference = d.getTime()-getDDN().getTime();
        d.setTime(difference);
        String date = Util.get_sdf_date().format(d);
        this.age = Integer.valueOf(date.substring(date.length()-4, date.length()));
    }

    /**
     * Décompresse et ouvre le dossier du patient dans l'explorateur
     */
    public void open_folder()
    {
        try{
            M_File.un_compress(m_file_zip, m_file);
            Desktop.getDesktop().open(m_file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void un_compresse_folder()
    {
        M_File.un_compress(m_file_zip, m_file);
    }

    public File getM_file(){return m_file;}

    /**
     * Renvoie le patient pour du debug
     * @return
     */
    public String toString()
    {
        return "Patient id: " + id + " Nom: " + nom + " Adresse: " + adresse + " Commentaire: " + commentaire + " Remarque: " + remarque;
    }

    public String get_nom() { return nom; }
    public long get_id() {return id;}

    /**
     * Compresse le dossier du patient
     */
    public void save() {
        try {
            M_File.compresse(m_file, m_file_zip);
            M_File.deleteDir(m_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console_debug.getInstance().m_debug("Patient id: " + String.valueOf(id) + " est sauvé");
    }

    /**
     * GETTER && SETTER
     */

    /**
     * Renvoie le commentaire
     * @return
     */
    public String getCommentaire(){ return this.commentaire; }

    /**
     * Renvoie le remarque
     * @return
     */
    public String getRemarque() { return this.remarque; }

    /**
     * Renvoie l'adresse
     * @return
     */
    public String getAdresse() { return this.adresse; }

    /**
     * Set le commentaire
     * @param commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Set la remarque
     * @param remarque
     */
    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    /**
     * Renvoie les mensurations du patient
     * @return
     */
    public Mensuration getMensuration() {
        return mensuration;
    }

    public void setMensuration(Mensuration mensuration) {
        this.mensuration = mensuration;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public boolean isJA() {
        return JA;
    }

    public void setJA(boolean JA) {
        this.JA = JA;
    }

    public Date getDDN() {
        return DDN;
    }

    public void setDDN(Date DDN) {
        this.DDN = DDN;
    }

    public String getPasse() {
        return passe;
    }

    public void setPasse(String passe) {
        this.passe = passe;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getTTT() {
        return TTT;
    }

    public void setTTT(String TTT) {
        this.TTT = TTT;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getVisite() {
        return visite;
    }

    public void setVisite(String visite) {
        this.visite = visite;
    }

    public String getPlan_alimentaire() {
        return plan_alimentaire;
    }

    public void setPlan_alimentaire(String plan_alimentaire) {
        this.plan_alimentaire = plan_alimentaire;
    }

    public String getT_a_faire() {
        return T_a_faire;
    }

    public void setT_a_faire(String t_a_faire) {
        T_a_faire = t_a_faire;
    }

    public int getAge() {
        calcul_age();
        return age;
    }
}