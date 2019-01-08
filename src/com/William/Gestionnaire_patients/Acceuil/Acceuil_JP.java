package com.William.Gestionnaire_patients.Acceuil;

import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Core.db.db_rdv;
import com.William.Gestionnaire_patients.Fenetre.Acceuil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wfrea_000 on 06/03/2016.
 */
public class Acceuil_JP extends JPanel implements ActionListener {
    private JLabel acc_af_next_rdv;
    private JLabel acc_aff_actu_rdv;
    private JLabel aff_nb_rdv;

    private JButton acc_open_actu_patient;
    private JButton acc_valider_rdv;

    private JPanel root_p;

    private RDV actu;

    private Acceuil parent_;

    public Acceuil_JP() {
        update_ui();

        acc_open_actu_patient.addActionListener(this);
        acc_valider_rdv.addActionListener(this);
    }

    private void createUIComponents() {}

    public void update_ui() {
        RDV rd = db_rdv.get_actu_rdv();
        actu=rd;
        RDV next = db_rdv.get_next_rdv();

        if(rd == null)
        {
            aff_nb_rdv.setText("");
            acc_aff_actu_rdv.setText("Pas de RDV");
        }
        else
        {
            acc_aff_actu_rdv.setText(rd.to_acc_aff());
        }

        int nb_restant = db_rdv.get_nb_restant();
        if(nb_restant == -1 || nb_restant==0)
        {
            aff_nb_rdv.setText("Plus de RDV");
        }
        else
        {
            aff_nb_rdv.setText(String.valueOf(nb_restant));
        }

        if(next == null)
        {
            acc_af_next_rdv.setText("Pas de prochain RDV");
        }
        else
        {
            acc_af_next_rdv.setText(next.to_acc_aff());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == acc_open_actu_patient)
        {
            if(actu != null)
            {
                parent_.open_pat(actu.get_pat_id());
            }
        }
    }

    public void setParent_(Acceuil parent_) {
        this.parent_ = parent_;
    }
}
