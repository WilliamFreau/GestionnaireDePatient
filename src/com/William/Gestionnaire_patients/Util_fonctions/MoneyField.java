package com.William.Gestionnaire_patients.Util_fonctions;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;

/**
 * Created by william on 08/06/16.
 */
public class MoneyField extends JTextField {

    public MoneyField() {
        setColumns(7);
        setHorizontalAlignment(RIGHT);
        ((AbstractDocument) getDocument()).setDocumentFilter(new Filter());
    }

    public double getValue() {

        String text = getText();
        if (!text.contains(".")) {
            text = "0." + text;
        }

        return Double.parseDouble(text);

    }

    protected class Filter extends DocumentFilter {

        protected String getNumbers(String text) {
            StringBuilder sb = new StringBuilder(text.length());
            for (char c : text.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (length > 0) {
                fb.remove(offset, length);
            }
            insertString(fb, offset, text, attrs);
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            text = getNumbers(text);
            if (text.length() > 0) {
                int docLength = fb.getDocument().getLength();
                if (docLength == 6) {
                    text = "." + text;
                }
                if (docLength + text.length() < 6) {
                    super.insertString(fb, offset, text, attr);
                }
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset == 5) {
                offset = 2;
                length = 5;
            }
            super.remove(fb, offset, length);
        }
    }
}