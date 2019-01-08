package com.William.Gestionnaire_patients.Patients;

import com.William.Gestionnaire_patients.Agenda.Agenda_JP;
import com.William.Gestionnaire_patients.Agenda.Modifier_RDV;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Core.db.db_rdv;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by william on 17/08/16.
 */
public class Patient_rdv extends My_Fen implements ActionListener {
    private JPanel root_pan;

    private JList<RDV> list_rdv_pat;
    private DefaultListModel list_model;

    private JButton ajouter_rdv;
    private JButton modifier_rdv;
    private JButton supp_rdv;

    private JLabel aff_nom_patient;

    private Patient_JP parent_;
    private Patient patient;

    private RDV[] l_rdv;

    public Patient_rdv(Patient_JP parent_, Patient patient)
    {
        this.parent_ = parent_;
        this.patient = patient;

        setTitle("RDV du patient");
        setVisible(true);
        setSize(750,500);
        setContentPane(root_pan);

        init();
    }

    private void init()
    {
        aff_nom_patient.setText(this.patient.get_nom());

        ajouter_rdv.addActionListener(this);
        modifier_rdv.addActionListener(this);
        supp_rdv.addActionListener(this);

        l_rdv = db_rdv.get_RDV_By_pat_Id(patient.get_id());

        list_model = new DefaultListModel();
        list_rdv_pat.setModel(list_model);
        list_rdv_pat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list_rdv_pat.setSelectedIndex(0);

        list_rdv_pat.setCellRenderer(new MyCellRenderer());

        update_ui();
    }

    private void update_ui()
    {
        l_rdv = db_rdv.get_RDV_By_pat_Id(patient.get_id());
        list_model.clear();
        for(RDV r:l_rdv)
        {
            list_model.addElement(r);
        }
        list_model.setSize(l_rdv.length);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ajouter_rdv)
        {
            //Add new RDV
        }

        if(e.getSource() == modifier_rdv)
        {
            //Modifier_RDV fen = new Modifier_RDV();
        }

        if(e.getSource() == supp_rdv)
        {
            //Supprimer RDV
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
                background = l_rdv[index].getColor();
                foreground = Color.BLACK;
            };

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }
}
