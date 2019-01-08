package com.William.Gestionnaire_patients.Agenda;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Core.db.db_rdv;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Util;
import org.freixas.jcalendar.JCalendarCombo;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by william on 04/06/16.
 */
public class Modifier_RDV extends My_Fen implements ActionListener {
    private JComboBox cmb_pat;

    private JCalendarCombo J_cal_date;

    private JFormattedTextField h_deb;
    private JFormattedTextField h_fin;

    private JComboBox cmb_etat;

    private JEditorPane edit_motif;
    private JEditorPane edit_remarque;

    private JButton Validate;
    private JPanel root_pan;

    private Agenda_JP parent_=null;

    private RDV rdv;

    public Modifier_RDV(Agenda_JP p_parent_, RDV p_rdv)
    {
        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Modifier un RDV");
        this.setResizable(true);

        this.parent_ = p_parent_;

        Util.filler_with_patient(cmb_pat);

        //les SDF
        DateFormat format_d = Util.get_sdf_date();

        SimpleDateFormat sdf = Util.get_sdf_date_heure();

        rdv = p_rdv;

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

        //Init des listeners
        Validate.addActionListener(this);

        //Remplissage des champs
        h_deb.setText(sdf.format(rdv.get_h_deb()));
        h_fin.setText(sdf.format(rdv.get_h_fin()));
        edit_motif.setText(rdv.get_motif());
        edit_remarque.setText(rdv.get_remarque());


        J_cal_date.setDate(rdv.get_date());

        System.out.println("Index selectioné: " + Core_main.getInstance().get_core_patient().get_rang_in_cmb((int)rdv.get_pat_id()));
        cmb_pat.setSelectedIndex(Core_main.getInstance().get_core_patient().get_rang_in_cmb((int)rdv.get_pat_id()));

        Util.filler_cmb_etat(cmb_etat);

        cmb_etat.setSelectedIndex(rdv.get_etat());
    }

    public Modifier_RDV(RDV p_rdv)
    {
        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Modifier un RDV");
        this.setResizable(true);

        Util.filler_with_patient(cmb_pat);

        //les SDF
        DateFormat format_d = Util.get_sdf_date();

        SimpleDateFormat sdf = Util.get_sdf_date_heure();

        rdv = p_rdv;

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

        //Init des listeners
        Validate.addActionListener(this);

        //Remplissage des champs
        h_deb.setText(sdf.format(rdv.get_h_deb()));
        h_fin.setText(sdf.format(rdv.get_h_fin()));
        edit_motif.setText(rdv.get_motif());
        edit_remarque.setText(rdv.get_remarque());


        J_cal_date.setDate(rdv.get_date());

        System.out.println("Index selectioné: " + Core_main.getInstance().get_core_patient().get_rang_in_cmb((int)rdv.get_pat_id()));
        cmb_pat.setSelectedIndex(Core_main.getInstance().get_core_patient().get_rang_in_cmb((int)rdv.get_pat_id()));

        Util.filler_cmb_etat(cmb_etat);

        cmb_etat.setSelectedIndex(rdv.get_etat());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Validate)
        {
            rdv.setDate(J_cal_date.getDate());
            try {
                rdv.set_pat_id(Core_main.getInstance().get_core_patient().get_pat_list()[cmb_pat.getSelectedIndex()].get_id());
                rdv.set_h_Deb(Util.get_sdf_date_heure().parse(h_deb.getText()));
                rdv.set_h_fin(Util.get_sdf_date_heure().parse(h_fin.getText()));
                rdv.set_Motif(this.edit_motif.getText());
                rdv.set_Remarque(this.edit_remarque.getText());
                rdv.setetat(this.cmb_etat.getSelectedIndex());
            }catch (ParseException p_exception)
            {
                Console_debug.getInstance().m_debug(p_exception.toString());
            }
                db_rdv.update(rdv);
            if(parent_!=null)
                parent_.update_ui();

            this.dispose();
        }
    }


}
