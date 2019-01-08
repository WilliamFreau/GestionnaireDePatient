package com.William.Gestionnaire_patients.Fenetre;

import javax.swing.*;

/**
 * Created by wfrea_000 on 13/02/2016.
 */
public abstract class My_Diag extends JDialog {

    public My_Diag () {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}
