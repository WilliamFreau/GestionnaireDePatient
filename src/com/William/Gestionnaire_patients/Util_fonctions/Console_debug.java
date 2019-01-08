package com.William.Gestionnaire_patients.Util_fonctions;

/**
 * Created by wfrea_000 on 11/03/2016.
 */
public class Console_debug {

    private boolean debug = false;

    private static Console_debug instance = null;

    private Log log;

    public Console_debug(boolean activation)
    {
        debug = activation;
        instance = this;
    }

    public void m_debug(String message)
    {
        if(debug) {
            System.out.println(message);
        }

        //Ecrit tout dans les logs
        if(Log.getInstance().getLogAll())
        {
            Log.getInstance().add_log( message );
        }
    }

    public static Console_debug getInstance() {return instance;}
}
