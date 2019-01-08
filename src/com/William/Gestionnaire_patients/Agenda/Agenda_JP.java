package com.William.Gestionnaire_patients.Agenda;

import com.William.Gestionnaire_patients.Core.RDV;
import com.William.Gestionnaire_patients.Core.db.db_rdv;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendar;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wfrea_000 on 06/03/2016.
 */
public class Agenda_JP extends JPanel implements ActionListener, DateListener {
    private JSplitPane split_Agenda;

    private JCalendar agenda_calendar;

    private JPanel pan_aff_agenda;
    private JPanel root_pan;

    private JList<RDV> agenda_list;

    private JButton N_rdv_buton;
    private JButton Button_modif;

    private DefaultListModel list_model;

    private RDV[] l_rdv;

    public Agenda_JP () {
        /**
         * Init des varibales
         */
        list_model = new DefaultListModel();
        agenda_list.setModel(list_model);
        agenda_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        agenda_list.setSelectedIndex(0);

        agenda_list.setCellRenderer(new MyCellRenderer());

        agenda_calendar.addDateListener(this);

        N_rdv_buton.addActionListener(this);
        Button_modif.addActionListener(this);

        //Call du update aff
        update_ui();

    }

    private void createUIComponents() {}

    public void update_ui()
    {
        list_model.clear();
        l_rdv = db_rdv.get_RDV_From_Day(agenda_calendar.getDate());
        for(RDV rdv:l_rdv)
        {
            if(rdv != null) {
                list_model.addElement(rdv);
                Console_debug.getInstance().m_debug(rdv.to_list_str());
            }
        }

        list_model.setSize(l_rdv.length);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == N_rdv_buton)
        {
            New_RDV n_rdv = new New_RDV(this);
        }

        if(e.getSource() == Button_modif)
        {
            //Modifier un RDV
            int selected = agenda_list.getSelectedIndex();
            Console_debug.getInstance().m_debug(String.valueOf("Index selectionnée dans la liste: " + selected));
            Modifier_RDV m_rdv = new Modifier_RDV(this, l_rdv[selected]);
        }
    }

    @Override
    public void dateChanged(DateEvent dateEvent) {
        this.update_ui();
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