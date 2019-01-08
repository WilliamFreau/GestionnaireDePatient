package com.William.Gestionnaire_patients.Fenetre_alert;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Fenetre.My_Diag;

import javax.swing.*;
import java.awt.event.*;

public class no_bdd_file extends My_Diag {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public no_bdd_file() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        Core_main.getInstance().get_core_bdd().create_emp_bdd();
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
