package com.William.Gestionnaire_patients;

import com.William.Gestionnaire_patients.Core.Core_main;
import com.William.Gestionnaire_patients.Core.Sauvegardeur;
import com.William.Gestionnaire_patients.Fenetre.Acceuil;
import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Log;

public class Main {
    public static void main(String[] args) {
        Log log = new Log(false);
        Console_debug console_debug = new Console_debug( false );


        if(args.length != 0)
        {
            System.out.println( "Argument: " );
            /**
             * Argument possible:
             * -debug : Active le Console_debug
             * -log : Active le Log (ecrit les erreurs affiché dans le fichié Log.log)
             * -logall: Ecrit tout le Console_Debug et les erreurs affiché dans le fichier Log.log)
             */
            for(String str:args)
            {
                //Pour charque argument
                System.out.println( str );
                switch (str)
                {
                    case "-debug":
                        //Active le printstacktrace dans la console
                        console_debug = new Console_debug( true );
                        break;
                    case "-log":
                        //Desactive l'ecriture du printstacktrace dans un fichier log
                        log.setActivate( true );
                        break;
                    case "-logall":
                        log.setLogAll(true);
                        break;
                }
            }
            System.out.println("Fin arguments");
        }

        log.add_StartOfSessions();

        new Sauvegardeur();

        Core_main core_main = new Core_main();
        core_main.init();

        Acceuil fen = new Acceuil();
    }
}
