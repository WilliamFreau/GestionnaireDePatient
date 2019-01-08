package com.William.Gestionnaire_patients.Core.db;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public abstract class Database {

    public Database() {
    }

    static String url_connection = "jdbc:sqlite:Data/BDD.db";

    /**
     * Table Patients
     */
    static final String TABLE_PAT_NAME = "Patients";
    static final String COL_PAT_PAT_ID = "Pat_Id";
    static final String COL_PAT_NOM = "Nom";
    static final String COL_PAT_ADRESSE = "Adresse";
    static final String COL_PAT_VILLE = "Ville";
    static final String COL_PAT_CP = "CP";
    static final String COL_PAT_COMMENTAIRE = "Commentaire";
    static final String COL_PAT_REMARQUE = "Remarque";
    static final String COL_PAT_TELEPHONE = "Telephone";
    static final String COL_PAT_GENRE = "Genre";
    static final String COL_PAT_DDN = "DDN";
    static final String COL_PAT_AGE = "Age";
    static final String COL_PAT_PASSE = "Passe";
    static final String COL_PAT_MEDICAL = "Medical";
    static final String COL_PAT_TTT = "TTT";
    static final String COL_PAT_PROJET = "Projet";
    static final String COL_PAT_VISITE = "Visite";
    static final String COL_PAT_PLAN_ALIMENTAIRE = "Plan_alimentaire";
    static final String COL_PAT_JA = "JA";
    static final String COL_PAT_TRAVAIL_A_FAIRE = "Travail_a_faire";


    /**
     * Table Mensuration
     */
    static final String TABLE_MENSURATION_NAME = "Mensuration";
    static final String COL_MENS_ID = "Id";
    static final String COL_MENS_PAT_ID = "Id_Pat";
    static final String COL_MENS_POID = "Poid";
    static final String COL_MENS_TAILLE = "Taille";
    static final String COL_MENS_IMC = "IMC";
    static final String COL_MENS_COU = "Cou";
    static final String COL_MENS_POITRINE = "Poitrine";
    static final String COL_MENS_BRAS = "Bras";
    static final String COL_MENS_ESTOMAC = "Estomac";
    static final String COL_MENS_VENTRE = "Ventre";
    static final String COL_MENS_HANCHE = "Hanche";
    static final String COL_MENS_CUISSE = "Cuisse";
    static final String COL_MENS_MOLLET = "Mollet";
    static final String COL_MENS_MG = "MG";
    static final String COL_MENS_M = "M";
    static final String COL_MENS_EAU = "eau";



    /**
     * Table Produit
     */
    static final String TABLE_PROD_NAME = "Produits";
    static final String COL_PROD_PROD_ID = "Prod_Id";
    static final String COL_PROD_NOM = "Nom";
    static final String COL_PROD_MONTANT = "Montant";
    static final String COL_PROD_HISTO_MONTANT = "Histo_Montant";
    static final String COL_PROD_QUE_HAVE = "Qte_Have";


    /**
     * Table RDV
     */
    static final String TABLE_RDV_NAME = "RDV";
    static final String COL_RDV_RDV_ID = "RDV_Id";
    static final String COL_RDV_PAT_ID = "Pat_Id";
    static final String COL_RDV_ETAT = "Etat";
    static final String COL_RDV_DATE = "Date";
    static final String COL_RDV_HEURE_DEB = "Heure_deb";
    static final String COL_RDV_HEURE_FIN = "Heure_fin";
    static final String COL_RDV_REMARQUE = "Remarque";
    static final String COL_RDV_MOTIF = "Motif";


    /**
     * Table Transaction
     */
    static final String TABLE_TRANS_NAME = "Paiement";
    static final String COL_TRANS_TRANS_ID = "Tran_id";
    static final String COL_TRANS_PAT_ID = "Pat_Id";
    static final String COL_TRANS_PROD_ID = "Prod_Id";
    static final String COL_TRANS_QTE_AMNT = "Qte_amnt";
    static final String COL_TRANS_PRICE_EACH = "Price_each";
    static final String COL_TRANS_TOTALE = "Totale";
    static final String COL_TRANS_MODE_REGLER = "Mode_Regler";
    static final String COL_TRANS_DATE_TRANS = "Date_Trans";
    static final String COL_TRANS_DATE_REGLER = "Date_Regler";
    static final String COL_TRANS_ELEMENT = "Element";


}
