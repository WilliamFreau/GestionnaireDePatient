package com.William.Gestionnaire_patients.Facture;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Patient;
import com.William.Gestionnaire_patients.Core.Transaction;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by william on 12/06/16.
 */
public class Edit_Facture {
    Patient pat;
    public Edit_Facture(Patient patient) {
        pat = patient;
        try {
            pat.un_compresse_folder();
            File f = new File(pat.getM_file().toString() + "/Facture");
            if (!f.exists()) {
                f.mkdir();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void create_facture(Transaction trans) throws IOException, DocumentException {
        String filename = this.get_name();
        int number = this.get_nb();

        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        //Init des tailles et marges
        document.setPageSize(PageSize.A4);

        //Ajout de l'écriture
        //Partie emeteur
        document.add(new Paragraph("\r\nA'voir sa diet à domicile\r\nJulia FREAU\r\n8 rue Auguste Rodin\r\n91120 Palaiseau\r\nN° Adeli:"));


        //partie recepteur
        Patient pat = Core_main.getInstance().get_core_patient().get_patient_from_id(trans.getId_patient());
        Paragraph par = new Paragraph(pat.get_nom() + "\r\n" + pat.getAdresse() + "\r\n\r\n");
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);

        //intitulé
        document.add(new Paragraph("Facture N°" + String.valueOf(number)));
        document.add(new Paragraph("Objet: ici un objet de facture"));
        document.add(new Paragraph("\r\n"));
        document.add(new Paragraph("\r\n"));

        float[] col_width = {5,1};

        PdfPTable table = new PdfPTable(col_width);

        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(false);

        Font f = new Font(Font.FontFamily.HELVETICA, 13,Font.NORMAL, GrayColor.GRAYWHITE);
        PdfPCell cell = new PdfPCell(new Phrase("This is header", f));

        cell.setBackgroundColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);


        //table.addCell(cell);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
        //l'Entête du tableau
        for(int i = 0 ; i < 1 ; i++)
        {
            table.addCell("Nom");
            table.addCell("Prix");
        }

        table.setHeaderRows(0);
        table.setFooterRows(2);

        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT);

        double somme = 0;

        //Le contenue du tableau
        table.addCell(trans.get_element());
        String montant = String.valueOf(trans.getMontant());
        if(montant.endsWith(".0"))
        {
            montant += "0";
        }
        table.addCell(montant + "€");
        somme += trans.getMontant();


        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.95f));
        table.getDefaultCell().setBorder(Rectangle.TOP | Rectangle.RIGHT);
        table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        table.addCell("  ");

        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.95f));
        String somme_str = String.valueOf(somme);
        if(somme_str.endsWith(".0"))
        {
            somme_str += "0";
        }
        table.addCell("Totale: " + somme_str + "€");


        document.add(table);

        switch (trans.getMode_regler())
        {
            case 0:
                document.add(new Paragraph("\r\nMoyen de réglement: Espéces"));
                break;
            case 1:
                document.add(new Paragraph("\r\nMoyen de réglement: Chéques"));
                break;
        }

        document.close();
    }


    public void create_facture_many(Transaction[] trans) throws IOException, DocumentException {
        String filename = this.get_name();
        int number = this.get_nb();

        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        //Init des tailles et marges
        document.setPageSize(PageSize.A4);

        //Ajout de l'écriture
        //Partie emeteur
        document.add(new Paragraph("\r\nA'voir sa diet à domicile\r\nJulia FREAU\r\n8 rue Auguste Rodin\r\n91120 Palaiseau"));


        //partie recepteur
        Patient pat = Core_main.getInstance().get_core_patient().get_patient_from_id(trans[0].getId_patient());
        Paragraph par = new Paragraph(pat.get_nom() + "\r\n" + pat.getAdresse() + "\r\n\r\n");
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);

        //intitulé
        document.add(new Paragraph("Facture N°" + number));
        document.add(new Paragraph("\r\n"));
        document.add(new Paragraph("\r\n"));

        float[] col_width = {5,1};

        PdfPTable table = new PdfPTable(col_width);

        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(false);

        Font f = new Font(Font.FontFamily.HELVETICA, 13,Font.NORMAL, GrayColor.GRAYWHITE);
        PdfPCell cell = new PdfPCell(new Phrase("This is header", f));

        cell.setBackgroundColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);


        //table.addCell(cell);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
        //l'Entête du tableau
        for(int i = 0 ; i < 1 ; i++)
        {
            table.addCell("Nom");
            table.addCell("Prix");
        }

        table.setHeaderRows(0);
        table.setFooterRows(2);

        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT);

        double somme = 0;

        //Le contenue du tableau
        for (Transaction tran : trans) {
            String moyen = "";
            switch (tran.getMode_regler())
            {
                case 0:
                    moyen = "Espéces";
                    break;
                case 1:
                    moyen = "Chéque";
                    break;
            }
            table.addCell(tran.get_element() + "    Moyen: " + moyen);
            String montant = String.valueOf(tran.getMontant());
            if(montant.endsWith(".0"))
            {
                montant += "0";
            }
            table.addCell(montant + "€");
            somme += tran.getMontant();
        }


        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.95f));
        table.getDefaultCell().setBorder(Rectangle.TOP | Rectangle.RIGHT);
        table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        table.addCell("  ");

        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.95f));
        String montant = String.valueOf(somme);
        if(montant.endsWith(".0"))
        {
            montant += "0";
        }

        table.addCell("Totale: " + montant + "€");


        document.add(table);

        document.close();
    }

    private String get_name()
    {
        File f = new File(pat.getM_file().toString() + "/Facture");
        return f.toString() + "/" + pat.get_nom() + "." + f.listFiles().length + ".pdf";
    }

    private int get_nb()
    {
        return new File(pat.getM_file().toString() + "/Facture").listFiles().length;
    }
}
