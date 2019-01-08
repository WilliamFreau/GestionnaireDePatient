package com.William.Gestionnaire_patients.Util_fonctions;

import java.util.Vector;

/**
 * Created by wfrea_000 on 12/03/2016.
 */
public class Intern_Message {

    //@TODO a refaire et a repenser. Il faudrais... Euh... Je sais pas trop.... Mais à refaire Peut être passé pas un Thread.
    //Pourquoi ne pas faire un thread qui vient gerer les log et les internals messages, cela permeterai de limiter le UIThread usage
    //Et donc d'avoir une application en apparence plus rapide

    private static Vector<String> message = new Vector<>();

    private static Intern_Message instance;

    public Intern_Message() {
        instance = this;
    }

    public static Intern_Message get_instance() {return instance;}

    public static void add_message(Key key, String p_message)
    {
        message.add(key.name());
        message.add(p_message);
    }

    public static void add_message(Key key)
    {
        Console_debug.getInstance().m_debug( key.toString() );
        message.add(key.toString());
        message.add(key.toString());
    }

    public static String get_message(Key key)
    {
        String returned = "";
        try {
            int index = message.indexOf(key.toString()) + 1;
            returned = message.elementAt(index);
            message.remove(index);
            message.remove(index - 1);
        }catch (Exception e) {Console_debug.getInstance().m_debug("Pas de message ayant la clé: " + key.toString());}
        return returned;
    }
}