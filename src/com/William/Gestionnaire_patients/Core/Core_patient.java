package com.William.Gestionnaire_patients.Core;

import com.William.Gestionnaire_patients.Util_fonctions.Console_debug;
import com.William.Gestionnaire_patients.Util_fonctions.Intern_Message;
import com.William.Gestionnaire_patients.Util_fonctions.Key;
import com.William.Gestionnaire_patients.Util_fonctions.M_File;

import java.io.File;
import java.io.IOException;

/**
 * Created by wfrea_000 on 05/02/2016.
 */
public class Core_patient {

    private Patient[] p_list;
    private int nb_patient;

    public Core_patient() {}

    protected void init(){
        update_pat_list_force();
        Console_debug.getInstance().m_debug("Nombre de patient trouvé dans le BDD: " + nb_patient);
    }


    public void add_patient(long id, String p_nom) {
        File pat_0 = new File(System.getProperty("user.dir") + "/Data/Patient0");
        File pat_dest = new File(System.getProperty("user.dir") + "/Data/U_Patients/" + p_nom + "." + id);
        pat_dest.mkdir();

        try {
            M_File.copy_fold(pat_0, pat_dest);
            File base = new File(pat_dest + "/Base.docx");
            base.renameTo(new File(pat_dest + "/" + p_nom +" .docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] get_Pat_Liste_str() {
        update();
        if(p_list==null)
            return new String[]{""};

        String [] returned = new String[p_list.length];

        for(int i = 0 ; i < p_list.length ; i++)
            returned[i] = p_list[i].get_nom();

        return returned;
    }

    public void update() {
        update_pat_list();
    }

    private void update_pat_list() {
        if (Intern_Message.get_message(Key.CORE_PAT_UPDATE).equals(Key.CORE_PAT_UPDATE.toString())) {
            Patient[] p_p_list = Core_main.getInstance().get_core_bdd().get_Patient();
            p_list = p_p_list;
        }

        if(Intern_Message.get_message( Key.CORE_PAT_ADD_L).equals( Key.CORE_PAT_ADD_L.toString() )) {
            Console_debug.getInstance().m_debug( "Passe dans le add 1" );
            Patient last_add = Core_main.getInstance().core_bdd.get_last_patient();
            Patient[] tempo = new Patient[p_list.length + 1];

            System.arraycopy( p_list, 0, tempo, 0, p_list.length);

            tempo[p_list.length] = last_add;
            p_list = tempo;
        }

        this.nb_patient = p_list.length;
    }


    private void update_pat_list_force() {
        Patient[] p_p_list = Core_main.getInstance().get_core_bdd().get_Patient();
        p_list = p_p_list;
        this.nb_patient = p_list.length;
    }

    public void open_pat_folder(int rang, String nom)
    {
        if(p_list[rang].get_nom().equals(nom))
        {
            p_list[rang].open_folder();
        }
    }

    public Patient[] get_pat_list(){return p_list;}

    public void add_last() {
        Patient last_add = Core_main.getInstance().core_bdd.get_last_patient();
        Patient[] tempo = new Patient[p_list.length + 1];

        System.arraycopy( p_list, 0, tempo, 0, p_list.length);

        tempo[p_list.length] = last_add;
        p_list = tempo;
    }


    /**
     * Renvoie le nom du patient à l'id
     * Si l'id n'exite pas cela renvoie null
     * @param id
     * @return
     */
    public String get_pat_name_from_id(long id)
    {
        for(Patient p:this.p_list)
        {
            if(p.get_id()==id)
            {
                return p.get_nom();
            }
        }
        return null;
    }

    public int get_rang_in_cmb(int pat_id) {
        for(int i=0;i<this.p_list.length;i++)
        {
            if(this.p_list[i].get_id()==pat_id)
            {
                return i;
            }
        }
        return -1;
    }

    public Patient get_patient_from_id(int id_patient) {
        for(Patient p: p_list)
        {
            if(p.get_id()==id_patient)
            {
                return p;
            }
        }
        return null;
    }
}