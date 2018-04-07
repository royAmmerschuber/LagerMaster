package controller;

import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLogger {
    public static final String PATH="C:/LagerMasterLogs/";
    public static final String NAME ="LagerMaster.log";
    public static void logException(Exception e){
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        File f=new File(PATH+dateFormat.format(date)+NAME);
        f.getParentFile().mkdirs();
        try(
            FileWriter fw = new FileWriter(f, true);
            PrintWriter out = new PrintWriter(new BufferedWriter(fw))
        )
        {
            DateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
            out.println("time:"+timeFormat.format(date));
            out.println("--------------------------");
            out.println("cause:"+e.toString());
            out.println("--------------------------");
            for(StackTraceElement el:e.getStackTrace()){
                out.println(el.toString());
            }
            out.println("");
            out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            out.println("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"there has been an unexpected exception. the Stacktrace is located at:\n"+PATH+dateFormat.format(date)+NAME);
    }
}
