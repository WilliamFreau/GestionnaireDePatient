package com.William.Gestionnaire_patients.Core.db;

import com.William.Gestionnaire_patients.Core.Mensuration;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Intern_Message;
import com.William.Gestionnaire_patients.Util_fonctions.Key;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class db_patient extends Database{

    public db_patient() {}


    /**
     * Ajoute un patient dans le fichier BDD.bd
     * @param p_nom     Nom du patient
     * @param p_adresse Adresse du patient
     * @param p_comment Commentaire sur le patient
     * @param p_rmq     Remarque sur le patient
     */
    public static long add_patient(String p_nom, String p_adresse, String p_comment, String p_rmq) {
        Connection c = null;
        Statement stmt = null;

        long id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "INSERT INTO " + Database.TABLE_PAT_NAME
                    + " (" + Database.COL_PAT_NOM + "," + Database.COL_PAT_ADRESSE + ","
                    + Database.COL_PAT_COMMENTAIRE + "," + Database.COL_PAT_REMARQUE + ") "
                    + "VALUES (\"" + p_nom + "\",\"" + p_adresse + "\",\"" + p_comment + "\",\"" + p_rmq + "\""
                    + ");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);
            stmt.close();


            //recup de l'id
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() FROM " + Database.TABLE_PAT_NAME + ";");
            if (rs.next()) {
                id = rs.getLong(1);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            Intern_Message.add_message( Key.ERR_ON_PAT_CREATE, e.getStackTrace().toString());
            System.exit(1);
        }
        return id;
    }


    /**
     * Creer le patient dans la BDD
     * @param pat
     * @return
     */
    public static long add_patient(Patient pat) {
        Connection c = null;
        Statement stmt = null;

        long id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "INSERT INTO " + Database.TABLE_PAT_NAME
                    + " (" + Database.COL_PAT_NOM + "," + Database.COL_PAT_ADRESSE + "," + COL_PAT_VILLE + "," + COL_PAT_CP + ","
                    + Database.COL_PAT_COMMENTAIRE + "," + Database.COL_PAT_REMARQUE + "," + COL_PAT_TELEPHONE + "," + COL_PAT_GENRE
                    + "," + COL_PAT_JA + "," + COL_PAT_DDN + "," + COL_PAT_AGE + "," + COL_PAT_PASSE + "," + COL_PAT_MEDICAL + "," + COL_PAT_TTT
                    + "," + COL_PAT_PROJET + "," + COL_PAT_VISITE + "," + COL_PAT_PLAN_ALIMENTAIRE + "," + COL_PAT_TRAVAIL_A_FAIRE + ") "
                    + "VALUES (\""
                    + pat.get_nom() + "\",\"" + pat.getAdresse() + "\",\"" + pat.getVille() + "\",\""+ pat.getCP() + "\",\"" + pat.getCommentaire() + "\",\"" + pat.getRemarque() + "\""
                    + ",\"" + pat.getTelephone() + "\",\"" + pat.isGenre() + "\",\"" + pat.isJA() + "\",\"" + Util.get_format_for_SQL().format(pat.getDDN())
                    + "\",\"" + pat.getAge() + "\",\"" + pat.getPasse() + "\",\"" + pat.getMedical() + "\",\"" + pat.getTTT() + "\",\"" + pat.getProjet()
                    + "\",\"" + pat.getVisite() + "\",\"" + pat.getPlan_alimentaire()  + "\",\"" + pat.getT_a_faire()
                    + "\");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);
            stmt.close();


            //recup de l'id
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() FROM " + Database.TABLE_PAT_NAME + ";");
            if (rs.next()) {
                id = rs.getLong(1);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            Intern_Message.add_message( Key.ERR_ON_PAT_CREATE, e.getStackTrace().toString());
            System.exit(1);
        }
        return id;
    }


    /**
     * Renvoie l'id du patient ayant toute les informations demandé
     * @param p_nom
     * @param p_adresse
     * @param p_comment
     * @param p_rmq
     * @return
     */
    private static long get_id_pat(String p_nom, String p_adresse, String p_comment, String p_rmq) {
        Connection c = null;
        Statement stmt = null;

        int id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT " + Database.COL_PAT_PAT_ID + " FROM " + Database.TABLE_PAT_NAME
                                    + " WHERE " + Database.COL_PAT_NOM + "=\"" + p_nom + "\" AND "
                                    + Database.COL_PAT_ADRESSE + "=\"" + p_adresse + "\" AND "
                                    + Database.COL_PAT_COMMENTAIRE + "=\"" + p_comment + "\" AND "
                                    + Database.COL_PAT_REMARQUE + "=\"" + p_rmq + "\";";

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            rs.next();
            id = rs.getInt(Database.COL_PAT_PAT_ID);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return id;
    }


    /**
     * Renvoie le patient a l'ID indiqué
     * @param p_id
     * @return
     */
    public static Patient get_patient_By_Id(long p_id) {
        Connection c = null;
        Statement stmt = null;

        Patient pat = new Patient();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + Database.TABLE_PAT_NAME
                    + " WHERE " + Database.COL_PAT_PAT_ID + "=" + p_id + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next())
            {
                Console_debug.getInstance().m_debug("Patient id: " + p_id + " n'existe pas!");
            }
            else
            {
                long id = rs.getLong(Database.COL_PAT_PAT_ID);
                String nom = rs.getString(Database.COL_PAT_NOM);
                String adresse = rs.getString(Database.COL_PAT_ADRESSE);
                String commentaire = rs.getString(Database.COL_PAT_COMMENTAIRE);
                String remarque = rs.getString(Database.COL_PAT_REMARQUE);

                pat = new Patient(id, nom, adresse, commentaire, remarque);

                pat.setCP(rs.getString(COL_PAT_CP));
                pat.setDDN(Util.get_format_for_SQL().parse(rs.getString(COL_PAT_DDN)));
                pat.setVille(rs.getString(COL_PAT_VILLE));
                pat.setTelephone(rs.getString(COL_PAT_TELEPHONE));
                pat.setGenre(rs.getBoolean(COL_PAT_GENRE));
                pat.setJA(rs.getBoolean(COL_PAT_JA));   //@FIXME Renvoie toujours false
                pat.setMedical(rs.getString(COL_PAT_MEDICAL));
                pat.setPasse(rs.getString(COL_PAT_PASSE));
                pat.setTTT(rs.getString(COL_PAT_TTT));
                pat.setProjet(rs.getString(COL_PAT_PROJET));
                pat.setVisite(rs.getString(COL_PAT_VISITE));
                pat.setPlan_alimentaire(rs.getString(COL_PAT_PLAN_ALIMENTAIRE));
                pat.setT_a_faire(rs.getString(COL_PAT_TRAVAIL_A_FAIRE));

                Console_debug.getInstance().m_debug(pat.toString());
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return pat;
    }

    /**
     * Renvoie la liste des ID des patients
     * @return
     */
    public static Object[] get_patients_Id() {
        Connection c = null;
        Statement stmt = null;

        Vector<Long> l_id = new Vector<Long>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT " + Database.COL_PAT_PAT_ID + " FROM " + Database.TABLE_PAT_NAME + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            while(rs.next())
            {
                l_id.add(rs.getLong(Database.COL_PAT_PAT_ID));
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return l_id.toArray();
    }

    /**
     * Renvoie le dernier id patient creer
     * @return
     */
    public static int get_last_id()
    {
        Connection c = null;
        Statement stmt = null;

        int returned = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT MAX("+ Database.COL_PAT_PAT_ID + ") AS Pat_Id FROM " + Database.TABLE_PAT_NAME +";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recup de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            returned = rs.getInt( Database.COL_PAT_PAT_ID );

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return returned;
    }

    /**
     * Update le patient dans la bdd (modifie le commentaire et les remarques
     * @param patient
     */
    public static void save_patient(Patient patient)
    {
        Connection c = null;
        Statement stmt = null;

        long id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "UPDATE " + Database.TABLE_PAT_NAME
                    + " SET " + Database.COL_PAT_COMMENTAIRE + "=\"" + patient.getCommentaire() + "\"" + "," + Database.COL_PAT_REMARQUE + "=\"" + patient.getRemarque()
                    + "\"," + COL_PAT_JA + "=\"" + patient.isJA() + "\"," + COL_PAT_PASSE + "=\"" + patient.getPasse() + "\"," + COL_PAT_MEDICAL + "=\"" + patient.getMedical()
                    + "\"," + COL_PAT_TTT + "=\"" + patient.getTTT() + "\"," + COL_PAT_PROJET + "=\"" + patient.getProjet() + "\"," + COL_PAT_VISITE + "=\"" + patient.getVisite()
                    + "\"," + COL_PAT_PLAN_ALIMENTAIRE + "=\"" + patient.getPlan_alimentaire() + "\"," + COL_PAT_TRAVAIL_A_FAIRE + "=\"" + patient.getT_a_faire() + "\""
                    + " WHERE (" + Database.COL_PAT_PAT_ID + "=" + patient.get_id() +");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            Intern_Message.add_message( Key.ERR_ON_PAT_CREATE, e.getStackTrace().toString());
            System.exit(1);
        }
    }
}
