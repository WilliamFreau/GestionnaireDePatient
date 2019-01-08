package com.William.Gestionnaire_patients.Util_fonctions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by wfrea_000 on 30/03/2016.
 */
public class Log {


    private static Log instance = null;

    private boolean activate = true;
    private boolean logAll = false;

    private File log_file = null;

    public Log(boolean activation)
    {
        activate = activation;
        this.instance = this;
        init();
    }

    private void init() {
        log_file = new File(System.getProperty("user.dir") + "/Data/Log.log");
        if(!log_file.exists())
        {
            try {
                log_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add_log(String log_str)
    {
        //Creation d'un texte dans un dossier Log
        if(activate) {
            try {
                FileWriter fw = new FileWriter( log_file, true );

                fw.append( log_str );
                fw.append( "\r\n" );

                fw.close();
            } catch (IOException exception) {
                System.out.println( "Erreur lors de la lecture : " + exception.getMessage() );
            }
        }
    }

    public static Log getInstance() {return instance;}

    public void setLogAll(boolean logAll) {
        this.logAll = logAll;
    }

    public void setActivate(boolean activation) {this.activate=activation;}

    public boolean getLogAll() {return logAll;}

    public void add_EnfOfSession() {
        this.add_log( "*****************************" );
        this.add_log( "*      FIN DE SESSIONS      *" );
        this.add_log( "*****************************" );
    }

    public void add_StartOfSessions()
    {
        this.add_log( "*****************************" );
        this.add_log( "*     DEBUT DE SESSIONS     *" );
        this.add_log( "*****************************" );
    }
}
