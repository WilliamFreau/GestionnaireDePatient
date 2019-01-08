package com.William.Gestionnaire_patients.Patients;

import com.William.Gestionnaire_patients.Core.Mensuration;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.db.db_mensuration;
import com.William.Gestionnaire_patients.Fenetre.My_Fen;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * Created by william on 30/07/16.
 */
public class Mensuration_fen extends My_Fen implements ActionListener {
    private JPanel root;

    private JTabbedPane tabbedPane1;

    private JButton previous;
    private JButton next;

    private JLabel aff_compteur;

    private Courbes courbes_aff;
    private Courbes courbes_new;

    private JButton aff_save;
    private JButton add_save;
    private JButton courbesButton;

    private JComboBox combo_choix;

    private Patient_JP parent_;

    private Mensuration Mens;

    private int actu_aff = 0;

    private Patient pat;

    public Mensuration_fen(Patient_JP p_parent, Patient p_pat) {
        setTitle("Mensurations");
        setVisible(true);
        setSize(750,500);
        setContentPane(root);

        parent_ = p_parent;
        pat = p_pat;

        init();
    }

    private void init() {
        aff_save.addActionListener(this);
        add_save.addActionListener(this);

        previous.addActionListener(this);
        next.addActionListener(this);

        courbesButton.addActionListener(this);

        setMensuration(pat.getMensuration());

        //remplissage du combo box
        combo_choix.addItem("Poid");
        combo_choix.addItem("Taille");
        combo_choix.addItem("IMC");
        combo_choix.addItem("Cou");
        combo_choix.addItem("Poitrine");
        combo_choix.addItem("Bras");
        combo_choix.addItem("Estomac");
        combo_choix.addItem("Ventre");
        combo_choix.addItem("Hanche");
        combo_choix.addItem("Cuisse");
        combo_choix.addItem("Mollet");
        combo_choix.addItem("%Mg");
        combo_choix.addItem("Eau");
        combo_choix.addItem("M");
    }

    protected void setMensuration(Mensuration Mens)
    {
        this.Mens = Mens;
        if(Mens.get_nb_record()!=0)
        {
            actu_aff=Mens.get_nb_record()-1;
            courbes_aff.aff_relever(Mens.get_valeur(actu_aff));
            actu_compteur();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == aff_save)
        {
            db_mensuration.update_mens(Mens.get_id(actu_aff), courbes_aff.get_valeur());
            pat.build_mensuration();
        }
        if(e.getSource() == add_save)
        {
            db_mensuration.add_mens((int)pat.get_id(), courbes_new.get_valeur());
            courbes_new.RAZ_form();
            pat.build_mensuration();
            setMensuration(pat.getMensuration());
            actu_compteur();
        }

        if(e.getSource() == previous)
        {
            if(actu_aff-1!=-1)
            {
                actu_aff--;
                courbes_aff.aff_relever(Mens.get_valeur(actu_aff));
                actu_compteur();
            }
        }

        if(e.getSource() == next)
        {
            if(actu_aff+1!=Mens.get_nb_record())
            {
                actu_aff++;
                courbes_aff.aff_relever(Mens.get_valeur(actu_aff));
                actu_compteur();
            }
        }

        if(e.getSource() == courbesButton)
        {
            open_graphique(combo_choix.getSelectedIndex());
        }
    }

    private void actu_compteur()
    {
        if(Mens.get_nb_record()!=0) {
            aff_compteur.setText(String.valueOf(actu_aff + 1 + "/" + Mens.get_nb_record()));
        }
        else
        {
            aff_compteur.setText("Aucun");
        }
    }

    public long getPat_id() {
        return pat.get_id();
    }

    private void open_graphique(int type)
    {
        //0=Poid
        //1=Taille
        //2=IMC
        //3=Cou
        //4=Poitrine
        //5=Bras
        //6=Estomac
        //7=ventre
        //8=hanche
        //9=cuisse
        //10=mollet
        //11=mg
        //12=eau
        //13=m
        Chart2D chart = new Chart2D();
        // Create an ITrace:
        ITrace2D trace = new Trace2DSimple();
        // Add the trace to the chart. This has to be done before adding points (deadlock prevention):
        chart.addTrace(trace);
        // Add all points, as it is static:
        double[] valeur = new double[Mens.get_nb_record()];
        for(int i = 0 ; i < valeur.length ; i++)
        {
            valeur[i] = Mens.get_valeur(i)[type];
        }
        valeur = mise_en_forme_valeur(valeur);
        Random random = new Random();
        for(int i = valeur.length-1 ; i >= 0 ; i--)
        {
            trace.addPoint(i,valeur[i]+i);
        }
        // Make it visible:
        // Create a frame.
        String titre;
        switch(type)
        {
            case 0:
                titre="Poid";
                break;
            case 1:
                titre="Taille";
                break;
            case 2:
                titre="IMC";
                break;
            case 3:
                titre="Cou";
                break;
            case 4:
                titre="Poitrine";
                break;
            case 5:
                titre="Bras";
                break;
            case 6:
                titre="Estomac";
                break;
            case 7:
                titre = "Ventre";
                break;
            case 8:
                titre = "Hanche";
                break;
            case 9:
                titre = "Cuisse";
                break;
            case 10:
                titre = "Mollet";
                break;
            case 11:
                titre = "%MG";
                break;
            case 12:
                titre = "%Eau";
                break;
            case 13:
                titre = "%M";
                break;
            default:
                titre = "Inconnue";
        }

        JFrame frame = new JFrame(titre);
        // add the chart to the frame:
        frame.getContentPane().add(chart);
        frame.setSize(400,300);
        // Enable the termination button [cross on the upper right edge]:
        frame.setVisible(true);
    }

    private double[] mise_en_forme_valeur(double[] valeur)
    {
        double[] returned = new double[valeur.length];

        for(int i = 0 ; i < valeur.length ; i++)
        {
            if(valeur[i]==-1)
            {
                double somme = 0;
                int k = 0;
                try{somme += valeur[i-1]; k += 1;}catch(Exception e){}
                try{somme += valeur[i+1]; k += 1;}catch(Exception e){}
                if(k==0) {k=1;}
                returned[i]=somme/k;
            }
            else
            {
                returned[i]=valeur[i];
            }
        }


        return returned;
    }
}
