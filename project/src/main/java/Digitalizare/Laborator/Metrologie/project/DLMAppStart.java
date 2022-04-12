package Digitalizare.Laborator.Metrologie.project;
import ClaseFisiere.project.FisierSingle;
import ClaseFisiere.project.FisierSingleDAO;
import Components.Events.project.MeniuLogare;
import Components.Events.project.MeniuTaburi;
import DataBaseConn.project.DataBaseConn;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class DLMAppStart {
    public static MeniuLogare logare = new MeniuLogare();
    //public static ArrayList<String> arrUti;
    public static JFrame appStart = new JFrame("Sistem informatic laborator metrologie");

    @SuppressWarnings("org.adfemg.audits.java.system-out-usage")
    public static void main(String[] args) {
        //SpringApplication.run(DLMAppStart.class, args);
        createAndShowGUI();
        
        String connectionUrl = String.format("jdbc:mysql://%s:%s/%s", "127.0.0.1", "3306", "dsr");
        DataBaseConn db = new DataBaseConn(connectionUrl, "root", "@Root123#");
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement("use DSR;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /*Map<String, String> getUtilizatori = new HashMap<>();
        System.out.println(getUtilizatori.get("Admin"));
        
        FisierSingleDAO fisiereSingleDB = new FisierSingleDAO(db.getConnection());
        List<FisierSingle> fisiereSingle = new ArrayList<>();

        try {
            fisiereSingle = fisiereSingleDB.getAll();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
    }
    
    private static void createAndShowGUI() {
        try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image logo = Toolkit.getDefaultToolkit().getImage("C:\\Oracle\\Middleware\\Oracle_Home\\" +
                                                          "jdeveloper\\mywork\\mywork\\" +
                                                          "DigitalizareLaboratorMetrologie\\project\\" +
                                                          "FisiereDLM\\FisiereAplicatie\\Dsr.png");
        appStart.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        appStart.addWindowListener(new inchideApp());
        appStart.setIconImage(logo);
        appStart.setSize(1360, 1040);
        appStart.setVisible(true);
        appStart.add(logare);
    }
    
    private static class inchideApp extends WindowAdapter {  
        public void windowClosing(WindowEvent evt) {  
            Object[] optiuni = {"Confirma", "Anuleaza"};
            UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
            UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
            int optiune = JOptionPane.showOptionDialog(DLMAppStart.appStart,
                "Esti sigur ca doresti sa inchizi complet aplicatia?",
                "Confirmare:",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                optiuni,  //the titles of buttons
                optiuni[0]); //default button title
            if (optiune == JOptionPane.YES_NO_OPTION) {
                System.exit(0);     
            }
        }  
    }
}