package com.William.Gestionnaire_patients.Util_fonctions.CheckBoxList;

import javax.swing.*;
import java.awt.*;

/**
 * Created by william on 07/08/16.
 */
// Handles rendering cells in the list using a check box

public class CheckboxListRenderer extends JCheckBox implements
        ListCellRenderer<CheckboxListItem> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends CheckboxListItem> list, CheckboxListItem value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}