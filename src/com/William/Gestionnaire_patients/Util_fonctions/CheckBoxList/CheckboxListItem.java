package com.William.Gestionnaire_patients.Util_fonctions.CheckBoxList;

import javax.swing.*;
import java.awt.*;

/**
 * Created by william on 07/08/16.
 */
// Represents items in the list that can be selected

public class CheckboxListItem {
    private String label;
    private boolean isSelected = false;

    public CheckboxListItem(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String toString() {
        return label;
    }
}