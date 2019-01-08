package com.William.Gestionnaire_patients.Comptabilité;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.Transaction;
import com.William.Gestionnaire_patients.Core.db.db_transaction;
import com.William.Gestionnaire_patients.Facture.Edit_Facture;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import com.William.Gestionnaire_patients.Util_fonctions.Util;
import com.itextpdf.text.DocumentException;
import org.freixas.jcalendar.JCalendarCombo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by wfrea_000 on 10/03/2016.
 */
public class N_paiement extends My_Fen implements ActionListener {
    private JComboBox cmb_patient;

    private JButton Validation;

    private JCalendarCombo selection_date;

    private JPanel root_pan;
    private JComboBox cmb_mode_reglement;
    private JFormattedTextField Money_txt_field;
    private JTextField txt_element;

    private Compta_JP parent_;

    public N_paiement(Compta_JP p_parent_)
    {
        this.parent_ = p_parent_;

        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Ajouter une transaction");
        this.setResizable(true);

        Validation.addActionListener(this);



        //Masque de l'argent


        //Remplissage du combo
        Util.filler_with_patient(cmb_patient);

        cmb_mode_reglement.addItem("Espéces");
        cmb_mode_reglement.addItem("Chéques");

        //Document listner
        DocumentListener doc_lis = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                //warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            void warn() {

            }
        };


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Validation)
        {
            //Clic sur Valider
            int index = cmb_patient.getSelectedIndex();
            Patient actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];
            Transaction trans = new Transaction((int)actu_patient.get_id(),
                    Double.valueOf(Money_txt_field.getText()), cmb_mode_reglement.getSelectedIndex(),
                    selection_date.getDate(), txt_element.getText());

            db_transaction.add_transaction(trans);
            parent_.update_ui();


            int confirm = JOptionPane.showOptionDialog(
                    null, "Editer la facture?",
                    "Edition de facture", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                Edit_Facture edit_facture = new Edit_Facture(actu_patient);
                try {
                    edit_facture.create_facture(trans);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (DocumentException e1) {
                    e1.printStackTrace();
                }
            }

            this.dispose();
        }
    }
}
