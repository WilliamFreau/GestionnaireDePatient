package com.William.Gestionnaire_patients.Comptabilité;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.Transaction;
import com.William.Gestionnaire_patients.Core.db.db_transaction;
import com.William.Gestionnaire_patients.Facture.Facturation;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wfrea_000 on 06/03/2016.
 */
public class Compta_JP extends JPanel implements ActionListener {

    private JPanel root_pan;

    private JComboBox cmb_nom_pat;

    private JButton add_paiement;
    private JButton but_compta_glob;

    private JList aff_paiement;
    private JButton modifier_button;
    private JButton factureButton;

    private DefaultListModel list_model;

    private Transaction[] l_transac;


    public Compta_JP() {
        init();
    }

    private void init() {
        cmb_nom_pat.addActionListener(this);

        list_model = new DefaultListModel();
        aff_paiement.setModel(list_model);
        aff_paiement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        aff_paiement.setSelectedIndex(0);
        aff_paiement.setCellRenderer(new MyCellRenderer());

        //Les ajout des actions listners
        add_paiement.addActionListener(this);
        but_compta_glob.addActionListener(this);
        modifier_button.addActionListener(this);
        factureButton.addActionListener(this);


        try {
            update_ui();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void createUIComponents(){};

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cmb_nom_pat)
        {
            update_ui();
        }

        if(e.getSource() == add_paiement)
        {
            //Clic pour ajouter une transaction
            N_paiement n_paiement = new N_paiement(this);
        }

        if(e.getSource() == but_compta_glob)
        {
            //Affiche de la compta global
            Compta_Glob c_glob = new Compta_Glob();
        }

        if(e.getSource() == modifier_button)
        {
            //Modifier un paiement
            new CH_paiement(this, l_transac[aff_paiement.getSelectedIndex()]);
        }

        if(e.getSource() == factureButton)
        {
            int index = cmb_nom_pat.getSelectedIndex();
            Patient actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];
            Facturation f = new Facturation(this);
        }
    }

    public void update_ui() {
        int index = cmb_nom_pat.getSelectedIndex();
        Util.filler_with_patient(cmb_nom_pat);
        cmb_nom_pat.setSelectedIndex(index);
        Patient actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];
        l_transac = db_transaction.get_Transaction_From_id_patient((int) actu_patient.get_id());

        list_model.clear();

        for (Transaction t : l_transac) {
            list_model.addElement(t.toString());
        }
    }

    /**
     * Class du render pour la colorisation des RDV
     */
    class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
        public MyCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            setText(value.toString());

            Color background;
            Color foreground;

            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                    && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index) {

                background = Color.BLUE;
                foreground = Color.WHITE;

                // check if this cell is selected
            } else if (isSelected) {
                background = new Color(230,230,230);
                foreground = Color.BLACK;

                // unselected, and not the DnD drop location
            } else {
                background = l_transac[index].getColor();
                foreground = Color.BLACK;
            };

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }
}
