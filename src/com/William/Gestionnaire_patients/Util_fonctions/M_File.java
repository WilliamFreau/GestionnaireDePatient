package com.William.Gestionnaire_patients.Util_fonctions;

import java.io.*;
import java.net.URI;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static java.lang.System.out;

/**
 * Created by wfrea_000 on 15/02/2016.
 */
public class M_File {

    /**
     * Copie le fichier de source vers dest
     * @param source
     * @param dest
     * @return
     * @throws IOException
     */
    public static boolean copyFile(File source, File dest) throws IOException {
        try{
            // Declaration et ouverture des flux
            FileInputStream sourceFile = new FileInputStream(source);

            try{
                FileOutputStream destinationFile = null;

                try{
                    destinationFile = new FileOutputStream(dest);

                    // Lecture par segment de 0.5Mo
                    byte buffer[] = new byte[512 * 1024];
                    int nbLecture;

                    while ((nbLecture = sourceFile.read(buffer)) != -1){
                        destinationFile.write(buffer, 0, nbLecture);
                    }
                } finally {
                    destinationFile.close();
                }
            } finally {
                sourceFile.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            return false; // Erreur
        }

        return true; // Résultat OK
    }

    /**
     * Copier un dossier et ses sous dossier de source vers dest
     * @param source
     * @param dest
     * @return
     * @throws IOException
     */
    public static boolean copy_fold(File source, File dest) throws IOException
    {
        if(source.exists()==false)
        {
            return false;
        }

        if(dest.exists() == false)
        {
            dest.mkdir();
        }

        //passe si la source existe
        for(File src : source.listFiles())
        {
            if(src.isDirectory())
            {
                //copie le sous dossier
                copy_fold(src,new File(String.valueOf(dest.getPath() + '/' + src.getPath().substring(src.getParent().length()))));
            }
            else
            {
                //Copie le fichier
                copyFile(src, new File(dest.getPath() + '/' + src.getPath().substring(src.getParent().length())));
            }
        }
        return true;
    }

    /**
     *  Compresse m_file dans m_file_zip
     * @param m_file
     * @param m_file_zip
     * @throws IOException
     */
    public static void compresse(File m_file, File m_file_zip) throws IOException {

        if(!m_file.exists()) {return;}

        if(m_file_zip.exists())
        {
            m_file_zip.delete();
        }

        Console_debug.getInstance().m_debug("Compression du dossier de " + m_file.getName());

        Closeable res = out;
        try{
            URI base = m_file.toURI();
            Deque<File> queue = new LinkedList<File>();
            queue.push(m_file);
            OutputStream out = new FileOutputStream(m_file_zip);
            ZipOutputStream zout = new ZipOutputStream(out);
            res = zout;
            while (!queue.isEmpty()) {
                m_file = queue.pop();
                for (File kid : m_file.listFiles()) {
                    String name = base.relativize(kid.toURI()).getPath();
                    if (kid.isDirectory()) {
                        queue.push(kid);
                        name = name.endsWith("/") ? name : name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));
                        copy(kid, zout);
                        zout.closeEntry();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            res.close();
        }
    }

    /**
     * Dé-compresse le zip dans m_file
     * @param m_file_zip
     * @param m_file
     */
    public static void un_compress(File m_file_zip, File m_file)
    {
        try (ZipFile zfile = new ZipFile(m_file_zip)) {
            Console_debug.getInstance().m_debug(m_file_zip.getAbsolutePath());
            Enumeration<? extends ZipEntry> entries = zfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File file = new File(m_file, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    InputStream in = zfile.getInputStream(entry);
                    try {
                        copy(in, file);
                    } finally {
                        in.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    private static void copy(File file, OutputStream out) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            copy(in, out);
        } finally {
            in.close();
        }
    }

    private static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        try {
            copy(in, out);
        } finally {
            out.close();
        }
    }

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }
}
