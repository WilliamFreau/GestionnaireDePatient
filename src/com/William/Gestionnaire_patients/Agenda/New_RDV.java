package com.William.Gestionnaire_patients.Agenda;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Core.db.db_rdv;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import com.William.Gestionnaire_patients.Util_fonctions.Util;
import org.freixas.jcalendar.JCalendarCombo;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by william on 30/05/16.
 */
public class New_RDV extends My_Fen implements ActionListener {
    private JPanel root_pan;

    private JComboBox cmb_pat;
    private JComboBox cmb_etat;

    private JButton Validate;

    private JFormattedTextField h_deb;
    private JFormattedTextField h_fin;

    private JCalendarCombo J_cal_date;

    private JEditorPane edit_motif;
    private JEditorPane edit_remarque;

    private Agenda_JP parent_;

    public New_RDV(Agenda_JP p_parent)
    {
        this.parent_=p_parent;

        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Ajouter un RDV");
        this.setResizable(true);

        Validate.addActionListener(this);



        //Masque des dates
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##:##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormatter.setValidCharacters("0123456789");
        dateFormatter.setPlaceholderCharacter('_');
        dateFormatter.setValueClass(String.class);
        DefaultFormatterFactory dateFormatterFactory = new
                DefaultFormatterFactory(dateFormatter);
        h_deb.setFormatterFactory(dateFormatterFactory);
        h_fin.setFormatterFactory(dateFormatterFactory);

        //Remplissage du combo
        Util.filler_with_patient(cmb_pat);
        Util.filler_cmb_etat(cmb_etat);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Validate)
        {
            int index = cmb_pat.getSelectedIndex();
            Patient actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];

            //Recup de la datet
            Date date_select = J_cal_date.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            Date d_h_deb = new Date();
            Date d_h_fin = new Date();

            try {
                d_h_deb = sdf.parse(h_deb.getText());
                d_h_fin = sdf.parse(h_fin.getText());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            RDV rdv = new RDV(actu_patient.get_id(), cmb_etat.getSelectedIndex(), date_select, d_h_deb, d_h_fin, edit_remarque.getText(), edit_motif.getText());
            db_rdv.add_rdv(rdv);
            this.dispose();
            parent_.update_ui();
        }
    }
}