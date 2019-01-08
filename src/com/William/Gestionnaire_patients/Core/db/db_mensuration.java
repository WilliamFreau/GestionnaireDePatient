package com.William.Gestionnaire_patients.Core.db;

import com.William.Gestionnaire_patients.Core.Mensuration;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Intern_Message;
import com.William.Gestionnaire_patients.Util_fonctions.Key;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by william on 18/05/16.
 */
public class db_mensuration extends Database{




    public static long[] get_id_mens_from_id_pat(long id_pat)
    {
        Vector<Long> retur = new Vector<Long>();
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT " + COL_MENS_ID
                    + " FROM " + Database.TABLE_MENSURATION_NAME+ " WHERE " + Database.COL_MENS_PAT_ID + "=" + id_pat + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            ResultSet rs =  stmt.executeQuery(sql_sentence);
            while(rs.next())
            {
                retur.add(rs.getLong(COL_MENS_ID));
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        long[] returned = new long[retur.size()];
        int i = 0;
        for(long el:retur)
        {
            returned[i]=el;
            i++;
        }
        return returned;
    }

    /**
     * Renvoie l'objet mensuration d'un patient avec son id
     * @param id du patient
     * @return
     */
    public static double[] get_mens_from_id(long id) {
        Connection c = null;
        Statement stmt = null;

        double[] returned = new double[14];

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT " + COL_MENS_POID + ", " + COL_MENS_TAILLE + ", " + COL_MENS_IMC
                    + ", " + COL_MENS_COU + ", " + COL_MENS_POITRINE + ", " + COL_MENS_BRAS + ", " + COL_MENS_ESTOMAC
                    + ", " + COL_MENS_VENTRE + ", " + COL_MENS_HANCHE + ", " + COL_MENS_CUISSE + ", " + COL_MENS_MOLLET
                    + ", " + COL_MENS_MG + ", " + COL_MENS_M + ", " + COL_MENS_EAU
                    + " FROM " + TABLE_MENSURATION_NAME + " WHERE " + COL_MENS_ID + "=" + id + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            ResultSet rs =  stmt.executeQuery(sql_sentence);


            for(int i = 0 ; i < 14 ; i++)
            {
                returned[i] = rs.getDouble(i+1);
            }

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return returned;
    }

    public static void update_mens(long id, double[] valeur) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "UPDATE " + TABLE_MENSURATION_NAME
                    + " SET " + COL_MENS_POID + "=" + valeur[0] + "," + COL_MENS_TAILLE + "=" + valeur[1]
                    + "," + COL_MENS_IMC + "=" + valeur[2] + "," + COL_MENS_COU + "=" + valeur[3]
                    + "," + COL_MENS_POITRINE + "=" + valeur[4] + "," + COL_MENS_BRAS + "=" + valeur[5]
                    + "," + COL_MENS_ESTOMAC + "=" + valeur[6] + "," + COL_MENS_VENTRE + "=" + valeur[7]
                    + "," + COL_MENS_HANCHE + "=" + valeur[8] + "," + COL_MENS_CUISSE + "=" + valeur[9]
                    + "," + COL_MENS_MOLLET + "=" + valeur[10] + "," + COL_MENS_MG + "=" + valeur[11]
                    + "," + COL_MENS_M + "=" + valeur[12] + "," + COL_MENS_EAU + "=" + valeur[13]
                    + " WHERE (" + COL_MENS_ID + "=" + id +");";

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

    public static void add_mens(int pat_id, double[] valeur) {
        Connection c = null;
        Statement stmt = null;


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "INSERT INTO " + TABLE_MENSURATION_NAME
                    + " (" + COL_MENS_PAT_ID + "," + COL_MENS_POID + ","
                    + COL_MENS_TAILLE + "," + COL_MENS_IMC + ","
                    + COL_MENS_COU +  "," + COL_MENS_POITRINE + ","
                    + COL_MENS_BRAS + "," + COL_MENS_ESTOMAC + ","
                    + COL_MENS_VENTRE + "," + COL_MENS_HANCHE + ","
                    + COL_MENS_CUISSE + "," + COL_MENS_MOLLET + ","
                    + COL_MENS_MG + "," + COL_MENS_M + ","
                    + COL_MENS_EAU + ")"
                    + " VALUES (" + pat_id + "," + valeur[0] + "," + valeur[1]
                    + "," + valeur[2] + "," + valeur[3] + "," + valeur[4]
                    + "," + valeur[5] + "," + valeur[6] + "," + valeur[7]
                    + "," + valeur[8] + "," + valeur[9] + "," + valeur[10]
                    + "," + valeur[11] + "," + valeur[12] + "," + valeur[13]
                    + ");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);


            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            Intern_Message.add_message( Key.ERR_ON_ADD_MENS, e.getStackTrace().toString());
            System.exit(1);
        }
    }
}