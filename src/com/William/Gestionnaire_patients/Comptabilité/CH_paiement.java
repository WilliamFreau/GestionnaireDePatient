package com.William.Gestionnaire_patients.Comptabilité;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Transaction;
import com.William.Gestionnaire_patients.Core.db.db_transaction;
import com.William.Gestionnaire_patients.Util_fonctions.Util;
import org.freixas.jcalendar.JCalendarCombo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by william on 09/06/16.
 */
public class CH_paiement extends JFrame implements ActionListener {

    private JComboBox cmb_patient;

    private JButton Validation;

    private JCalendarCombo selection_date;

    private JComboBox cmb_mode_reglement;

    private JFormattedTextField Money_txt_field;

    private JPanel root_pan;
    private JButton But_supp;
    private JTextField txt_element;

    private Compta_JP parent_;

    private Transaction trans;

    public CH_paiement(Compta_JP p_parent_, Transaction p_t)
    {
        this.parent_ = p_parent_;
        this.trans = p_t;

        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Modifier une transaction");
        this.setResizable(true);

        //add des listners
        Validation.addActionListener(this);
        But_supp.addActionListener(this);

        update_ui();
    }

    void update_ui(){
        Util.filler_with_patient(cmb_patient);

        cmb_mode_reglement.addItem("Espéces");
        cmb_mode_reglement.addItem("Chéques");

        //Remplissage des champs
        //Selection du patient
        cmb_patient.setSelectedIndex(Core_main.getInstance().get_core_patient().get_rang_in_cmb(trans.getId_patient()));
        Money_txt_field.setText(String.valueOf(trans.getMontant()));
        cmb_mode_reglement.setSelectedIndex(trans.getMode_regler());
        selection_date.setDate(trans.getD_regler());
        txt_element.setText(trans.get_element());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Validation)
        {
            // Il valide les modifications
            trans.setid_patient((int) Core_main.getInstance().get_core_patient().get_pat_list()[cmb_patient.getSelectedIndex()].get_id());
            trans.setMode_Regler(cmb_mode_reglement.getSelectedIndex());
            trans.setD_regler(selection_date.getDate());
            trans.setmontant(Double.valueOf(Money_txt_field.getText()));
            trans.setElement(txt_element.getText());

            db_transaction.update_trans(trans);

            this.dispose();
            parent_.update_ui();
        }

        if(e.getSource() == But_supp)
        {
            //Suppression du paiement
            db_transaction.delete_transaction(trans);
            this.dispose();
            parent_.update_ui();
        }
    }
}
