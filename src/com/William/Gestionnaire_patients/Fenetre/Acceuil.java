package com.William.Gestionnaire_patients.Fenetre;

import com.William.Gestionnaire_patients.Acceuil.Acceuil_JP;
import com.William.Gestionnaire_patients.Achat_Vente.Ach_vent_JP;
import com.William.Gestionnaire_patients.Agenda.Agenda_JP;
import com.William.Gestionnaire_patients.Comptabilité.Compta_JP;
import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Patients.Patient_JP;
import javafx.beans.property.adapter.JavaBeanObjectProperty;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * Created by wfrea_000 on 29/01/2016.
 */
public class Acceuil extends My_Fen implements ChangeListener {
    /**
     * Main panel
     */
    private JPanel panel1;


    /**
     * Tab panel
     */
    private JTabbedPane pane_Acceuil;
    private JPanel Acceuil_Panel;
    private JPanel Patient_Panel;
    private JPanel Agenda_Panel;
    private JPanel Ach_Vent_Panel;
    private JPanel Compta_Panel;

    private Acceuil_JP acceuil_JP;
    private Patient_JP patient_JP;
    private Agenda_JP agenda_JP;
    private Ach_vent_JP ach_vent_JP;
    private Compta_JP compta_JP;


    private JButton pat_new_patient;


    public Acceuil() {
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setName("Gestionnaire de patient");
        this.setTitle("Gestionnaire de patient");
        this.setResizable(true);

        acceuil_JP.setParent_(this);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Êtes vous sure de vouloir quitter?",
                        "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    Fermeture ferm = new Fermeture();
                    Core_main.getInstance().close(ferm);
                    dispose();
                }
            }
        };
        this.addWindowListener(exitListener);

        this.setSize(800, 600);


        this.pane_Acceuil.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == pane_Acceuil)
        {
            int index = pane_Acceuil.getSelectedIndex();
            switch (index)
            {
                case 0:
                    this.acceuil_JP.update_ui();
                    break;
                case 1:
                    patient_JP.update_aff();
                    break;
                case 2:
                    agenda_JP.update_ui();
                    break;
                case 3:
                    ach_vent_JP.update_ui();
                    break;
                case 4:
                    compta_JP.update_ui();
                    break;
            }
        }
    }

    public void open_pat(long pat_id) {
        patient_JP.set_patient(pat_id);
        this.pane_Acceuil.setSelectedIndex(1);
    }
}
