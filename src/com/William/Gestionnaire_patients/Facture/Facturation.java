package com.William.Gestionnaire_patients.Facture;

import com.William.Gestionnaire_patients.Comptabilité.Compta_JP;
import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.Transaction;
import com.William.Gestionnaire_patients.Core.db.db_transaction;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import com.William.Gestionnaire_patients.Util_fonctions.CheckBoxList.CheckboxListItem;
import com.William.Gestionnaire_patients.Util_fonctions.CheckBoxList.CheckboxListRenderer;
import com.William.Gestionnaire_patients.Util_fonctions.Util;
import com.itextpdf.text.DocumentException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by william on 12/06/16.
 */
public class Facturation extends My_Fen implements ActionListener {
    private JPanel root_pan;

    private JComboBox cmb_patient;

    private JList<CheckboxListItem> list1;
    private JButton create_facture;

    Compta_JP parent_;

    private Transaction[] l_transac;

    private CheckboxListItem[] l_item;


    public Facturation(Compta_JP p_parent_)
    {
        this.parent_ = p_parent_;

        this.setVisible(true);
        this.setSize(500,300);
        this.setContentPane(root_pan);
        this.setTitle("Ajouter une transaction");
        this.setResizable(true);

        init();
    }

    private void init()
    {
        /*list1.setListData(new CheckboxListItem[] { new CheckboxListItem("apple"),
                        new CheckboxListItem("orange"),
                        new CheckboxListItem("mango"),
                        new CheckboxListItem("paw paw"),
                        new CheckboxListItem("banana") });
                        Work ok
                        */
        Util.filler_with_patient(cmb_patient);

        cmb_patient.addActionListener(this);
        create_facture.addActionListener(this);


        list1.setCellRenderer(new CheckboxListRenderer());
        list1.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent event) {
            JList<CheckboxListItem> list =
                    (JList<CheckboxListItem>) event.getSource();

            // Get index of item clicked
            int index = list.locationToIndex(event.getPoint());
            CheckboxListItem item = (CheckboxListItem) list.getModel()
                    .getElementAt(index);

            // Toggle selected state
            item.setSelected(!item.isSelected());

            // Repaint cell
            list.repaint(list.getCellBounds(index, index));
        }
        });

        update_ui();
    }

    public void update_ui() {
        int index = cmb_patient.getSelectedIndex();
        Patient actu_patient = Core_main.getInstance().get_core_patient().get_pat_list()[index];
        l_transac = db_transaction.get_Transaction_From_id_patient((int) actu_patient.get_id());

        CheckboxListItem[] listItems = new CheckboxListItem[l_transac.length];
        int i=0;
        for (Transaction t : l_transac) {
            listItems[i] = new CheckboxListItem(t.toString());
            i++;
        }

        list1.setListData(listItems);
        l_item=listItems;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cmb_patient)
        {
            update_ui();
        }

        if(e.getSource() == create_facture)
        {

            Vector<Transaction> trans = new Vector<>();
            for(int i = 0 ; i < list1.getModel().getSize() ; i++)
            {
                if(list1.getModel().getElementAt(i).isSelected())
                {
                    trans.add(l_transac[i]);
                }
            }
            Transaction[] transactions = new Transaction[trans.size()];
            int i = 0;
            for(Transaction t : trans)
            {
                transactions[i] = t;
                i++;
            }
            int index = cmb_patient.getSelectedIndex();
            Patient actu = Core_main.getInstance().get_core_patient().get_pat_list()[index];

            try {
                new Edit_Facture(actu).create_facture_many(transactions);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }
    }
}
