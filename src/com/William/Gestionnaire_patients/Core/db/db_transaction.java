package com.William.Gestionnaire_patients.Core.db;

import com.William.Gestionnaire_patients.Core.Transaction;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
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
public class db_transaction extends Database{

    /**
     *
     * @param trans
     * @return
     */
    public static void add_transaction(Transaction trans) {
        Connection c = null;
        Statement stmt = null;

        long id = -1;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "INSERT INTO " + TABLE_TRANS_NAME
                    + " (" + COL_TRANS_PAT_ID + "," + COL_TRANS_MODE_REGLER + ","
                    + COL_TRANS_TOTALE + "," + COL_TRANS_DATE_REGLER + "," + COL_TRANS_ELEMENT + ") "
                    + "VALUES (" + trans.getId_patient() + "," + trans.getMode_regler() + "," + trans.getMontant() +
                    ",\"" + Util.get_format_for_SQL().format(trans.getD_regler()) + "\",\"" + trans.get_element() + "\");";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);
            stmt.close();


            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Intern_Message.add_message( Key.ERR_ON_PAT_CREATE, e.getStackTrace().toString());
            System.exit(1);
        }
    }

    public static Transaction get_Transaction_By_Id(long p_id) {
        Connection c = null;
        Statement stmt = null;

        Transaction trans = new Transaction();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + TABLE_TRANS_NAME
                    + " WHERE " + COL_TRANS_TRANS_ID + "=" + p_id + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = stmt.executeQuery(sql_sentence);
            if(!rs.next())
            {
                Console_debug.getInstance().m_debug("Transaction id: " + p_id + " n'existe pas!");
            }
            else
            {
                long id = rs.getLong(COL_TRANS_TRANS_ID);
                int id_patient = rs.getInt(COL_TRANS_PAT_ID);
                int mode_regler = rs.getInt(COL_TRANS_MODE_REGLER);
                double montant = rs.getDouble(COL_TRANS_TOTALE);
                String date_regler = rs.getString(COL_TRANS_DATE_REGLER);
                String element = rs.getString(COL_TRANS_ELEMENT);

                Date d_regler = Util.get_sdf_date().parse(date_regler);

                trans = new Transaction((int)id, id_patient, montant, mode_regler, d_regler, element);
            }
            Console_debug.getInstance().m_debug(trans.toString());
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return trans;
    }


    /**
     * Renvoie l'ensemble des transactions d'une année trier par mois
     * @param ans
     * @return
     */
    public static double[] get_Transaction_From_Annee(int ans) {
        Connection c = null;
        Statement stmt = null;

        Vector<Double> trans = new Vector<>();

        DateFormat format_d = Util.get_sdf_date();

        SimpleDateFormat sdf = Util.get_sdf_date_heure();


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Selection des transaction
            //SELECT SUM(Totale) As Tot, STRFTIME('%m',Date_Regler) As Mois, STRFTIME('%Y', Date_Regler) As Ans FROM Paiement WHERE Ans='2016' GROUP BY Mois;
            stmt = c.createStatement();
            String sql_sentence = "SELECT SUM(" + COL_TRANS_TOTALE + ") AS Tot, "
                    + " STRFTIME('%m'," + COL_TRANS_DATE_REGLER + ") As Mois" + ","
                    + " STRFTIME('%Y'," + COL_TRANS_DATE_REGLER + ") As Ans"
                    + " FROM " + TABLE_TRANS_NAME
                    + " WHERE Ans=\"" + ans + "\""
                    + " GROUP BY Mois ORDER BY Mois ASC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = c.createStatement().executeQuery(sql_sentence);

            while(rs.next())
            {
                trans.add(rs.getDouble("Tot"));
                trans.add(Double.valueOf(rs.getString("Mois")));
                trans.add(rs.getDouble("Ans"));
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        double[] returned = new double[trans.size()];
        for(int i = 0; i< trans.size() ; i++)
        {
            returned[i]=trans.get(i);
        }
        return returned;
    }

    public static double get_Transaction_From_Annee_Mois_Mode(int ans, int mois, int mode) {
        Connection c = null;
        Statement stmt = null;

        double somme = 0;

        String mois_str = "";
        if(mois<10)
        {
            mois_str = "0" + String.valueOf(mois);
        }
        else
        {
            mois_str = String.valueOf(mois);
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Selection des transaction
            //SELECT SUM(Totale) As Tot, STRFTIME('%m', Date_Regler) As Mois FROM Paiement WHERE STRFTIME('%Y',Date_Regler)='2016' AND Mois="06" AND Mode_Regler=0 GROUP BY Mois;
            stmt = c.createStatement();
            String sql_sentence = "SELECT SUM(" + COL_TRANS_TOTALE + ") AS Tot, "
                    + " STRFTIME('%m'," + COL_TRANS_DATE_REGLER + ") As Mois"
                    + " FROM " + TABLE_TRANS_NAME
                    + " WHERE STRFTIME('%Y', " + COL_TRANS_DATE_REGLER + ")=\"" + ans + "\"" + " AND Mois=\"" + mois_str + "\" AND " + COL_TRANS_MODE_REGLER + "=" + mode
                    + " GROUP BY Mois;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = c.createStatement().executeQuery(sql_sentence);

            if(rs.next())
            {
                somme = rs.getDouble("Tot");
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return somme;
    }

    /**
     * Renvoie l'ensemble des transactions d'un patient
     * @param id_patient
     * @return
     */
    public static Transaction[] get_Transaction_From_id_patient(int id_patient) {
        Connection c = null;
        Statement stmt = null;

        Vector<Transaction> trans = new Vector<>();

        DateFormat format_d = Util.get_sdf_date();

        SimpleDateFormat sdf = Util.get_sdf_date_heure();


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Selection des transactions
            stmt = c.createStatement();
            String sql_sentence = "SELECT * FROM " + TABLE_TRANS_NAME
                    + " WHERE " + COL_TRANS_PAT_ID + "=" + id_patient + " ORDER BY " + COL_TRANS_DATE_REGLER + " DESC;";

            Console_debug.getInstance().m_debug(sql_sentence);

            //Recp de l'ID
            ResultSet rs = c.createStatement().executeQuery(sql_sentence);

            while(rs.next())
            {
                long id = rs.getLong(COL_TRANS_TRANS_ID);
                int mode_regler = rs.getInt(COL_TRANS_MODE_REGLER);
                double montant = rs.getDouble(COL_TRANS_TOTALE);
                String date_regler = rs.getString(COL_TRANS_DATE_REGLER);
                String element = rs.getString(COL_TRANS_ELEMENT);

                Date d_regler = Util.get_format_for_SQL().parse(date_regler);

                trans.add(new Transaction((int)id, id_patient, montant, mode_regler, d_regler, element));
            }
            Console_debug.getInstance().m_debug(trans.toString());


            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Transaction[] returned = new Transaction[trans.size()];
        for(int i = 0; i< trans.size() ; i++)
        {
            returned[i]=trans.get(i);
        }
        return returned;
    }

    public static void update_trans(Transaction trans) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();

            String sql_sentence = "UPDATE " + TABLE_TRANS_NAME + " SET "
                    + COL_TRANS_PAT_ID + "=" + trans.getId_patient() + ", "
                    + COL_TRANS_MODE_REGLER + "=" + trans.getMode_regler() + ", "
                    + COL_TRANS_TOTALE + "=" + trans.getMontant() + ", "
                    + COL_TRANS_DATE_REGLER + "=\"" + Util.get_format_for_SQL().format(trans.getD_regler()) + "\"" + ", "
                    + COL_TRANS_ELEMENT + "=\"" + trans.get_element() + "\""
                    + " WHERE " + COL_TRANS_TRANS_ID + "=" + trans.getId_transaction() + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.executeUpdate(sql_sentence);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void delete_transaction(Transaction trans)
    {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url_connection);
            c.setAutoCommit(false);

            //Ajout du patient
            stmt = c.createStatement();

            String sql_sentence = "DELETE FROM " + TABLE_TRANS_NAME
                    + " WHERE " + COL_TRANS_TRANS_ID + "=" + trans.getId_transaction() + ";";

            Console_debug.getInstance().m_debug(sql_sentence);

            stmt.execute(sql_sentence);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}