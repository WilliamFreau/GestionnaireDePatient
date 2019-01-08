package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Util_fonctions.M_File;
import com.William.Gestionnaire_patients.Util_fonctions.Util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by william on 14/06/16.
 */
public class Sauvegardeur {

    String date = Util.get_sdf_date().format(new Date()).replace("/","_");

    public Sauvegardeur() {
        verif_tempo();

        if(!already_done())
        {
            //Si ce n'est pas deja fait
            do_save();
        }

        verif_tempo();

    }

    private void verif_tempo(){
        File tempo = new File(System.getProperty("user.dir") + "/Data/Tempo");
        if(tempo.listFiles().length!=0)
        {
            M_File.deleteDir(tempo);
        }
        if(!tempo.exists()){
            tempo.mkdir();
        }
    }


    private boolean already_done()
    {
        File file = new File(System.getProperty("user.dir") + "/Data/Sauvegarde");
        for (File f:file.listFiles())
        {
            if(f.getName().equals(date))
            {
                return true;
            }
        }
        return false;
    }

    private void do_save()
    {
        try {
            M_File.copy_fold(new File(System.getProperty("user.dir") + "/Data/Patients"), new File(System.getProperty("user.dir") + "/Data/Tempo"));
            M_File.copyFile(new File(System.getProperty("user.dir") + "/Data/BDD.db"), new File(System.getProperty("user.dir") + "/Data/Tempo/BDD.db"));

            //début de la compression en zip
            M_File.compresse(new File(System.getProperty("user.dir") + "/Data/Tempo"),new File(System.getProperty("user.dir") + "/Data/Sauvegarde/" + date+ ".zip"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
