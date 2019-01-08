package com.William.Gestionnaire_patients.Core.db;

import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Intern_Message;
import com.William.Gestionnaire_patients.Util_fonctions.Key;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class db_rdv extends Database{

    /**
     * Renvoie le RDV à l'id RDV
     * @param r_id
     * @return
     */
    public static RDV get_RDV_By_Id(long r_id) {
        Connection c = null;
        Statement stmt = null;

        RDV rdv = new RDV();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + Database.TABLE_RDV_NAME
                    + " WHERE " + Database.COL_RDV_RDV_ID + "=" + r_id + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next())
            {
                Console_debug.getInstance().m_debug("RDV id: " + r_id + " n'existe pas!");
            }
            else
            {
                long id_rdv = rs.getLong(Database.COL_RDV_PAT_ID);
                long id_pat = rs.getLong(Database.COL_RDV_PAT_ID);
                int etat = rs.getInt(Database.COL_RDV_ETAT);
                Date date = rs.getDate(Database.COL_RDV_DATE);
                Date h_deb = rs.getDate(Database.COL_RDV_HEURE_DEB);
                Date h_fin = rs.getDate(Database.COL_RDV_HEURE_FIN);
                String remarque = rs.getString(Database.COL_RDV_REMARQUE);
                String motif = rs.getString(Database.COL_RDV_MOTIF);

                rdv = new RDV(id_rdv, id_pat, etat, date, h_deb, h_fin, remarque, motif);

                Console_debug.getInstance().m_debug(rdv.toString());
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rdv;
    }

    /**
     * Ajoute le RDV à la table
     * @param rdv
     * @return
     *
     */
    public static RDV add_rdv(RDV rdv) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            Date date = new Date();
            DateFormat format_h = new SimpleDateFormat("HH:mm");
            DateFormat format_d = new SimpleDateFormat("dd/MM/yyyy");

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "INSERT INTO " + TABLE_RDV_NAME + "("
                                + COL_RDV_PAT_ID + "," + COL_RDV_ETAT + ","
                                + COL_RDV_DATE + "," + COL_RDV_HEURE_DEB + ","
                                + COL_RDV_HEURE_FIN + "," + COL_RDV_REMARQUE + ","
                                + COL_RDV_MOTIF
                                + ") VALUES ("
                                + (int)rdv.get_pat_id() + "," + rdv.get_etat() + ","
                                + "\"" + format_d.format(rdv.get_date()) + "\",\"" + format_h.format(rdv.get_h_deb()) + "\",\""
                                + format_h.format(rdv.get_h_fin()) + "\",\"" + rdv.get_remarque() + "\",\"" + rdv.get_motif()
                                + "\")"
                    + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rdv;
    }


    /**
     * Renvoie les rdv d'une date par ordre d'heyre (plutot au plutard)
     * @param p_date La date
     * @return Liste des rdv
     */
    public static RDV[] get_RDV_From_Day(Date p_date) {
        Connection c = null;
        Statement stmt = null;

        Vector<RDV> rdv = new Vector<>();

        DateFormat format_d = Util.get_sdf_date();

        SimpleDateFormat sdf = Util.get_sdf_date_heure();


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + TABLE_RDV_NAME
                    + " WHERE " + COL_RDV_DATE + "=\"" + format_d.format(p_date) + "\" ORDER BY " + COL_RDV_HEURE_DEB + " ASC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = c.createStatement().executeQuery(sql_sentence);

            int i = 0;
            while(rs.next())
            {
                long id_rdv = rs.getLong(COL_RDV_RDV_ID);
                long id_pat = rs.getLong(COL_RDV_PAT_ID);
                int etat = rs.getInt(COL_RDV_ETAT);
                String date = rs.getString(COL_RDV_DATE);
                String h_deb = rs.getString(COL_RDV_HEURE_DEB);
                String h_fin = rs.getString(COL_RDV_HEURE_FIN);
                String remarque = rs.getString(COL_RDV_REMARQUE);
                String motif = rs.getString(COL_RDV_MOTIF);

                rdv.add(new RDV(id_rdv, id_pat, etat, format_d.parse(date), sdf.parse(h_deb), sdf.parse(h_fin), remarque, motif));

                i += 1;
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(1);
        }

        RDV[] returned = new RDV[rdv.size()];
        for(int i = 0; i< rdv.size() ; i++)
        {
            returned[i]=rdv.get(i);
        }
        return returned;
    }


    /**
     * Update le rdv dans la bdd
     * @param rdv le rdv à update
     */
    public static void update(RDV rdv) {
        Connection c = null;
        Statement stmt = null;

        long id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "UPDATE " + TABLE_RDV_NAME
                    + " SET " + COL_RDV_PAT_ID + "=" + rdv.get_pat_id() + "," + COL_RDV_DATE + "=\"" + Util.get_sdf_date().format(rdv.get_date())
                    + "\", " + COL_RDV_HEURE_DEB + "=\"" + Util.get_sdf_date_heure().format(rdv.get_h_deb()) + "\", "
                    + COL_RDV_HEURE_FIN + "=\"" + Util.get_sdf_date_heure().format(rdv.get_h_fin()) + "\", "
                    + COL_RDV_MOTIF + "=\"" + rdv.get_motif() + "\", " + COL_RDV_REMARQUE + "=\"" + rdv.get_remarque()
                    + "\", " + COL_RDV_ETAT + "=\"" + rdv.get_etat() + "\""
                    + " WHERE (" + Database.COL_RDV_RDV_ID + "=" + rdv.get_id() +");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Intern_Message.add_message( Key.ERR_ON_PAT_CREATE, e.getStackTrace().toString());
            //System.exit(1);
        }
    }

    /**
     * Renvoie le prochain RDV de la journée
     * @return le RDV ou null si plus de RDV
     */
    public static RDV get_next_rdv()
    {
        Connection c = null;
        Statement stmt = null;

        RDV rdv = new RDV();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + Database.TABLE_RDV_NAME
                + " WHERE " + COL_RDV_DATE +  "=\"" + Util.get_sdf_date().format(new Date()) + "\" AND " +
                    COL_RDV_HEURE_DEB + ">=\"" + Util.get_sdf_date_heure().format(new Date()) + "\" ORDER BY " + COL_RDV_HEURE_DEB + " ASC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next() && !rs.next())
            {
                Console_debug.getInstance().m_debug("Plus de RDV");
                stmt.close();
                c.commit();
                c.close();
                return null;
            }
            else
            {
                long id_rdv = rs.getLong(Database.COL_RDV_PAT_ID);
                long id_pat = rs.getLong(Database.COL_RDV_PAT_ID);
                int etat = rs.getInt(Database.COL_RDV_ETAT);
                Date date = Util.get_sdf_date().parse(rs.getString(Database.COL_RDV_DATE));
                Date h_deb = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_DEB));
                Date h_fin = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_FIN));
                String remarque = rs.getString(Database.COL_RDV_REMARQUE);
                String motif = rs.getString(Database.COL_RDV_MOTIF);

                rdv = new RDV(id_rdv, id_pat, etat, date, h_deb, h_fin, remarque, motif);

                Console_debug.getInstance().m_debug(rdv.toString());
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rdv;
    }

    public static RDV get_actu_rdv()
    {

        Connection c = null;
        Statement stmt = null;

        RDV rdv = new RDV();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + Database.TABLE_RDV_NAME
                    + " WHERE " + COL_RDV_DATE +  "=\"" + Util.get_sdf_date().format(new Date()) + "\" AND " +
                    COL_RDV_HEURE_DEB + "<=\"" + Util.get_sdf_date_heure().format(new Date()) + "\" AND "+
                    COL_RDV_HEURE_FIN + ">=\"" + Util.get_sdf_date_heure().format(new Date()) +
                    "\" ORDER BY " + COL_RDV_HEURE_DEB + " ASC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next())
            {
                Console_debug.getInstance().m_debug("Plus de RDV");
                stmt.close();
                c.commit();
                c.close();
                return null;
            }
            else
            {
                long id_rdv = rs.getLong(Database.COL_RDV_PAT_ID);
                long id_pat = rs.getLong(Database.COL_RDV_PAT_ID);
                int etat = rs.getInt(Database.COL_RDV_ETAT);
                Date date = Util.get_sdf_date().parse(rs.getString(Database.COL_RDV_DATE));
                Date h_deb = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_DEB));
                Date h_fin = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_FIN));
                String remarque = rs.getString(Database.COL_RDV_REMARQUE);
                String motif = rs.getString(Database.COL_RDV_MOTIF);

                rdv = new RDV(id_rdv, id_pat, etat, date, h_deb, h_fin, remarque, motif);

                Console_debug.getInstance().m_debug(rdv.toString());
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rdv;
    }

    /**
     * Renvoi le nombre de RDV restant
     * @return
     */
    public static int get_nb_restant() {
        Connection c = null;
        Statement stmt = null;

        int i=0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT COUNT(" + COL_RDV_RDV_ID + ") AS NB FROM " + Database.TABLE_RDV_NAME
                    + " WHERE " + COL_RDV_DATE +  "=\"" + Util.get_sdf_date().format(new Date()) + "\" AND " +
                    COL_RDV_HEURE_DEB + ">=\"" + Util.get_sdf_date_heure().format(new Date()) + "\" ORDER BY " + COL_RDV_HEURE_DEB + " ASC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next())
            {
                stmt.close();
                c.commit();
                c.close();
                return -1;
            }
            else
            {
                i = rs.getInt("NB");
                Console_debug.getInstance().m_debug(String.valueOf("Reste " + i + " RDV aujourd'hui"));
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return i;
    }

    public static RDV[] get_RDV_By_pat_Id(long p_id) {
        Connection c = null;
        Statement stmt = null;

        Vector<RDV> rdv_vect = new Vector();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + Database.TABLE_RDV_NAME
                    + " WHERE " + COL_RDV_PAT_ID + "=" + p_id + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            while(rs.next())
            {
                long id_rdv = rs.getLong(Database.COL_RDV_PAT_ID);
                long id_pat = rs.getLong(Database.COL_RDV_PAT_ID);
                int etat = rs.getInt(Database.COL_RDV_ETAT);
                Date date = Util.get_sdf_date().parse(rs.getString(Database.COL_RDV_DATE));
                Date h_deb = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_DEB));
                Date h_fin = Util.get_sdf_date_heure().parse(rs.getString(Database.COL_RDV_HEURE_FIN));
                String remarque = rs.getString(Database.COL_RDV_REMARQUE);
                String motif = rs.getString(Database.COL_RDV_MOTIF);

                rdv_vect.add(new RDV(id_rdv, id_pat, etat, date, h_deb, h_fin, remarque, motif));
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        RDV[] returned = new RDV[rdv_vect.size()];
        int i = 0;
        for(RDV r : rdv_vect)
        {
            returned[i] = r;
            i++;
        }
        return returned;
    }
}
