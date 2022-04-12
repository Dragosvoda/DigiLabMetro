package Components.Events.project;

import ClaseFisiere.project.Fisier;
import ClaseFisiere.project.FisierDAO;
import ClaseFisiere.project.FisierSingle;
import ClaseFisiere.project.FisierSingleDAO;
import ClaseFisiere.project.Raport;
import ClaseFisiere.project.RaportDAO;

import DataBaseConn.project.DataBaseConn;

import Digitalizare.Laborator.Metrologie.project.DLMAppStart;

import SIL.project.SIL;
import SIL.project.SILDAO;

import Users.project.UsersDAO;

import be.quodlibet.boxable.BaseTable;

import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;

import com.planbase.pdf.layoutmanager.BorderStyle;
import com.planbase.pdf.layoutmanager.CellStyle;
import com.planbase.pdf.layoutmanager.Coord;

import com.planbase.pdf.layoutmanager.LogicalPage;
import com.planbase.pdf.layoutmanager.Padding;
import com.planbase.pdf.layoutmanager.PdfLayoutMgr;

import com.planbase.pdf.layoutmanager.TableBuilder;

import com.planbase.pdf.layoutmanager.TablePart;

import com.planbase.pdf.layoutmanager.TableRowBuilder;
import com.planbase.pdf.layoutmanager.Text;
import com.planbase.pdf.layoutmanager.TextStyle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import com.spire.pdf.*;
import com.spire.pdf.automaticfields.PdfAutomaticField;
import com.spire.pdf.automaticfields.PdfCompositeField;
import com.spire.pdf.automaticfields.PdfPageCountField;
import com.spire.pdf.automaticfields.PdfPageNumberField;
import com.spire.pdf.graphics.*;
import com.spire.pdf.tables.*;
import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Dimension2D;

import java.awt.geom.Rectangle2D;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.StandardCopyOption;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontLike;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author Dragos
 */

public class MeniuTaburi extends javax.swing.JPanel {
    DLMAppStart start = new DLMAppStart();
    static JFrame fereastraFisiere = new JFrame("Administrare fisiere aplicatie");
    static JFrame fereastraUtilizatori = new JFrame("Administrare utilizatori aplicatie");
    MeniuAdmFisiere meniuFisiere = new MeniuAdmFisiere();
    MeniuAdmUtilizatori meniuUtilizatori = new MeniuAdmUtilizatori();
    String connectionUrl = String.format("jdbc:mysql://%s:%s/%s", "127.0.0.1", "3306", "dsr");
    DataBaseConn db = new DataBaseConn(connectionUrl, "root", "@Root123#");
    
    FisierDAO fisierManagement = new FisierDAO(db.getConnection());
    List<Fisier> fisiereModCom = new ArrayList<>();
    
    SILDAO SILmag = new SILDAO(db.getConnection());
    SIL selecteazaSIL = null;
    SIL modificaSIL = null;
    SIL silGlobal = null;    //trebuie dat null dupa ce a fost folosit
    
    RaportDAO raportMag = new RaportDAO(db.getConnection());
    Raport selecteazaRaport = null;
    Raport modificaRaport = null;
    Raport raportGlobal = null;  //trebuie dat null dupa ce a fost folosit
    
    List<String> listaFisiereGenCom = new ArrayList<>();
    List<String>listaFisiereModCom = new ArrayList<>();
    List<String> listaFisiereMapa = new ArrayList<>();
    List<File> dosarGenCom = new ArrayList<>();
    List<File> dosarModCom = new ArrayList<>();
    private static <T> List<T> vec(T... ts) { return Arrays.asList(ts); }
    
    String[] arrayFisiereGenCom = {};
    String[] arrayFisiereModCom = {};
    String[] listFolders = {};
    String[] arrayFisiereMapa = {};
    
    String fisiereDLM = "C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\mywork\\" + 
                        "DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\";
    String siluriNoi = "C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\mywork\\" +
                       "DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\SILNou\\";
    
    /** Creates new form MeniuTaburi. */
    public MeniuTaburi() {
        initComponents();
        scoateTaburi();
        incarcaLogo();
        selectoareMapeSiFisiere();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jTabbedPaneMeniu = new javax.swing.JTabbedPane();
        jPanelMeniuPrincipal = new javax.swing.JPanel();
        jLabelTitluMeniuPrincipal = new javax.swing.JLabel();
        jBtnTabGenComanda = new javax.swing.JButton();
        jBtnTabVizCom = new javax.swing.JButton();
        jBtnTabAdmFisiere = new javax.swing.JButton();
        jBtnTabFiseLaborator = new javax.swing.JButton();
        jBtnTabAdmUti = new javax.swing.JButton();
        jBtnInchideApp = new javax.swing.JButton();
        jLabelLogoMeniuPrincipal = new javax.swing.JLabel();
        jPanelComandaNoua = new javax.swing.JPanel();
        jLabelTitluComNoua = new javax.swing.JLabel();
        jLabelNumeComNoua = new javax.swing.JLabel();
        jLabelDescComNoua = new javax.swing.JLabel();
        jTextFieldNumeComNoua = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDescComNoua = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListComNoua = new javax.swing.JList<>();
        jBtnGenComNoua = new javax.swing.JButton();
        jBtnInchideMeniuComNoua = new javax.swing.JButton();
        jBtnAdaugaFisierComNoua = new javax.swing.JButton();
        jLabelAvertismenteComNoua = new javax.swing.JLabel();
        jBtnFinalizareCom = new javax.swing.JButton();
        jFileChooserGenCom = new javax.swing.JFileChooser();
        jLabelTipGenCom = new javax.swing.JLabel();
        jTextFieldTipGenCom = new javax.swing.JTextField();
        jLabelFisiereGenCom = new javax.swing.JLabel();
        jBtnNextStep = new javax.swing.JButton();
        jBtnScoateFisierGenCom = new javax.swing.JButton();
        jPanelVizCom = new javax.swing.JPanel();
        jFileChooserVizCom = new javax.swing.JFileChooser();
        jBtnStergeCom = new javax.swing.JButton();
        jBtnInchideTabVizCom = new javax.swing.JButton();
        jLabelAvertismenteVizCom = new javax.swing.JLabel();
        jLabelTitluVizCom = new javax.swing.JLabel();
        jBtnModificaCom = new javax.swing.JButton();
        jBtnExpCom = new javax.swing.JButton();
        jPanelModCom = new javax.swing.JPanel();
        jFileChooserModCom = new javax.swing.JFileChooser();
        jTextFieldNumeModCom = new javax.swing.JTextField();
        jTextFieldTipModCom = new javax.swing.JTextField();
        jLabelNumeModCom = new javax.swing.JLabel();
        jLabelTipModCom = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaDescModCom = new javax.swing.JTextArea();
        jLabelDescModCom = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListModCom = new javax.swing.JList<>();
        jLabelFisiereModCom = new javax.swing.JLabel();
        jBtnInchideTabModCom = new javax.swing.JButton();
        jLabelAvertismenteModCom = new javax.swing.JLabel();
        jBtnSelectModCom = new javax.swing.JButton();
        jBtnModificareCom = new javax.swing.JButton();
        jBtnScoateModCom = new javax.swing.JButton();
        jBtnCopiazaModCom = new javax.swing.JButton();
        jBtnIncarcaMapa = new javax.swing.JButton();
        jLabelTitluModCom = new javax.swing.JLabel();
        jBtnGolesteSelectie = new javax.swing.JButton();
        jFileChooserAccesFisiere = new javax.swing.JFileChooser();
        jLabelAfisareComModCom = new javax.swing.JLabel();
        jLabelAfisareFisiereModCom = new javax.swing.JLabel();
        jPanelVerMet = new javax.swing.JPanel();
        jLabelTitluVerMet = new javax.swing.JLabel();
        jLabelNr = new javax.swing.JLabel();
        jTextFieldNrVerMet = new javax.swing.JTextField();
        jLabelData = new javax.swing.JLabel();
        jTextFieldDataVerMet = new javax.swing.JTextField();
        jLabelNumeSolicitant = new javax.swing.JLabel();
        jTextFieldNumeSolicitant = new javax.swing.JTextField();
        jLabelAdresa = new javax.swing.JLabel();
        jTextFieldAdresa = new javax.swing.JTextField();
        jLabelCodFiscal = new javax.swing.JLabel();
        jTextFieldCodFiscal = new javax.swing.JTextField();
        jLabelIBAN = new javax.swing.JLabel();
        jTextFieldIBAN = new javax.swing.JTextField();
        jLabelPersoanaContact = new javax.swing.JLabel();
        jLabelNrOV = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableVerMet = new javax.swing.JTable();
        jLabelTensiuneMedie = new javax.swing.JLabel();
        jLabelBucCurentTensiuneMedie = new javax.swing.JLabel();
        jTextFieldBucTensiuneMedie = new javax.swing.JTextField();
        jLabelTensiuneMedieTensiune = new javax.swing.JLabel();
        jLabelBucTensiuneMedieTensiune = new javax.swing.JLabel();
        jTextFieldBucTensiuneMedieTensiune = new javax.swing.JTextField();
        jLabelJoasaTensiune = new javax.swing.JLabel();
        jLabelBucCurentJoasaTensiune = new javax.swing.JLabel();
        jTextFieldBucJoasaTensiune = new javax.swing.JTextField();
        jLabelTensiuneJoasaTensiune = new javax.swing.JLabel();
        jLabelBucTensiuneJoasaTensiune = new javax.swing.JLabel();
        jTextFieldBucTensiuneJoasaTensiune = new javax.swing.JTextField();
        jLabelAverstismenteVerMet = new javax.swing.JLabel();
        jBtnInchideTabVerMet = new javax.swing.JButton();
        jBtnSalvareVerMet = new javax.swing.JButton();
        jButtonTabFisaAnalizaDoc = new javax.swing.JButton();
        jTextFieldPersoanaContact = new javax.swing.JTextField();
        jPanelFisaAnalizaDoc = new javax.swing.JPanel();
        jLabelTitluAnalizaDoc = new javax.swing.JLabel();
        jLabelDenumireDocAnalizat = new javax.swing.JLabel();
        jTextFieldDenumireDocAnalizat = new javax.swing.JTextField();
        jLabelClientFizaAnalizaDoc = new javax.swing.JLabel();
        jTextFieldClientFisaAnalizaDoc = new javax.swing.JTextField();
        jLabelContinutDocFisaAnaliza = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaContinutDocFisaAnaliza = new javax.swing.JTextArea();
        jLabelMetodaFolositaAnalizaDoc = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextAreaMetodaFisaAnaliza = new javax.swing.JTextArea();
        jLabelObservatiiFisaAnalizaDoc = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextAreaObservatiiFisaAnaliza = new javax.swing.JTextArea();
        jLabelDecizieAnaliza = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextAreaDecizieAnaliza = new javax.swing.JTextArea();
        jLabelDataFisaAnalizaDoc = new javax.swing.JLabel();
        jTextFieldDataFisaAnalizaDoc = new javax.swing.JTextField();
        jButtonSalvareFisaAnalizaDoc = new javax.swing.JButton();
        jButtonInchideTabFisaAnalizaDoc = new javax.swing.JButton();
        jLabelAvertismenteFisaAnalizaDoc = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabelIntocmitFisaAnalizaDoc = new javax.swing.JLabel();
        jTextFieldIntocmitFisaAnalizaDocument = new javax.swing.JTextField();
        jPanelProcesVerbalReceptieOV = new javax.swing.JPanel();
        jLabelTitluProcesVerbalRec = new javax.swing.JLabel();
        jLabelNrProcesVerbalRec = new javax.swing.JLabel();
        jTextFieldNrProcesVerbalRec = new javax.swing.JTextField();
        jLabelDataProcesVerbalRec = new javax.swing.JLabel();
        jTextFieldDataProcesVerbalRec = new javax.swing.JTextField();
        jLabelDeLaProcesVerbalRec = new javax.swing.JLabel();
        jTextFieldDeLaProcesVerbalRec = new javax.swing.JTextField();
        jLabelDenumireOV_1 = new javax.swing.JLabel();
        jTextFieldDenumireOV_1 = new javax.swing.JTextField();
        jLabelProducatorProcesVerbalRec_1 = new javax.swing.JLabel();
        jTextFieldProducatorProcesVerbalRec_1 = new javax.swing.JTextField();
        jLabelCaracProcesVerbalRec_1 = new javax.swing.JLabel();
        jTextFieldCaracProcesVerbalRec_1 = new javax.swing.JTextField();
        jLabelSerieAnFabBuc = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextAreaSerieAnFabBuc = new javax.swing.JTextArea();
        jLabelDenumireOV_2 = new javax.swing.JLabel();
        jTextFieldDenumireOV_2 = new javax.swing.JTextField();
        jLabelProducatorProcesVerbalRec_2 = new javax.swing.JLabel();
        jTextFieldProducatorProcesVerbalRec_2 = new javax.swing.JTextField();
        jLabelCaracProcesVerbalRec_2 = new javax.swing.JLabel();
        jTextFieldCaracProcesVerbalRec_2 = new javax.swing.JTextField();
        jLabelSerieAnFabBuc_2 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextAreaSerieAnFabBuc_2 = new javax.swing.JTextArea();
        jLabelAbateri = new javax.swing.JLabel();
        jTextFieldAbateri = new javax.swing.JTextField();
        jLabelRezultatConsultare = new javax.swing.JLabel();
        jTextFieldRezultatConsultare = new javax.swing.JTextField();
        jLabelNotaProcesVerbalRec = new javax.swing.JLabel();
        jTextFieldNotaProcesVerbalRec = new javax.swing.JTextField();
        jLabelNumePrenumeProcesVerbalRec_1 = new javax.swing.JLabel();
        jTextFieldNumePrenumeProcesVerbalRec_1 = new javax.swing.JTextField();
        jLabelFunctieProcesVerbalRec_1 = new javax.swing.JLabel();
        jTextFieldFunctieProcesVerbalrec_1 = new javax.swing.JTextField();
        jLabelNumePrenumeProcesVerbalRec_2 = new javax.swing.JLabel();
        jLabelFunctieProcesVerbalRec_2 = new javax.swing.JLabel();
        jTextFieldNumePrenumeProcesVerbalRec_2 = new javax.swing.JTextField();
        jTextFieldFunctieProcesVerbalrec_2 = new javax.swing.JTextField();
        jLabelAvertismenteProcesVerbalRec = new javax.swing.JLabel();
        jButtonNextReceptieOV = new javax.swing.JButton();
        jButtonInchideTabReceptieOV = new javax.swing.JButton();
        jButtonSalvareReceptieOV = new javax.swing.JButton();

        jTabbedPaneMeniu.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTabbedPaneMeniu.setMinimumSize(new java.awt.Dimension(1080, 680));
        jTabbedPaneMeniu.setPreferredSize(new java.awt.Dimension(1080, 680));

        jPanelMeniuPrincipal.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelMeniuPrincipal.setPreferredSize(new java.awt.Dimension(1080, 680));

        jLabelTitluMeniuPrincipal.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluMeniuPrincipal.setText("Sistem Informatic Laborator Metrologie");

        jBtnTabGenComanda.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnTabGenComanda.setText("Generare comanda");
        jBtnTabGenComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTabGenComandaActionPerformed(evt);
            }
        });

        jBtnTabVizCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnTabVizCom.setText("Vizualizare comenzi");
        jBtnTabVizCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTabVizComActionPerformed(evt);
            }
        });

        jBtnTabAdmFisiere.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnTabAdmFisiere.setText("Administrare fisiere");
        jBtnTabAdmFisiere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTabAdmFisiereActionPerformed(evt);
            }
        });

        jBtnTabFiseLaborator.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnTabFiseLaborator.setText("Fise laborator");
        jBtnTabFiseLaborator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTabFiseLaboratorActionPerformed(evt);
            }
        });

        jBtnTabAdmUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnTabAdmUti.setText("Administrare utilizatori");
        jBtnTabAdmUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTabAdmUtiActionPerformed(evt);
            }
        });

        jBtnInchideApp.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnInchideApp.setText("Inchide");
        jBtnInchideApp.setToolTipText("Inchide complet aplicatia?");
        jBtnInchideApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInchideAppActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMeniuPrincipalLayout = new javax.swing.GroupLayout(jPanelMeniuPrincipal);
        jPanelMeniuPrincipal.setLayout(jPanelMeniuPrincipalLayout);
        jPanelMeniuPrincipalLayout.setHorizontalGroup(
            jPanelMeniuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMeniuPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMeniuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMeniuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabelTitluMeniuPrincipal)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelMeniuPrincipalLayout.createSequentialGroup()
                        .addGroup(jPanelMeniuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnTabAdmUti)
                            .addComponent(jBtnTabGenComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnTabVizCom, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnTabAdmFisiere, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnTabFiseLaborator, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelLogoMeniuPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMeniuPrincipalLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnInchideApp, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelMeniuPrincipalLayout.setVerticalGroup(
            jPanelMeniuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMeniuPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluMeniuPrincipal)
                .addGap(18, 18, 18)
                .addGroup(jPanelMeniuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMeniuPrincipalLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jBtnTabGenComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jBtnTabVizCom, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jBtnTabAdmFisiere, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jBtnTabFiseLaborator, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jBtnTabAdmUti, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 177, Short.MAX_VALUE))
                    .addComponent(jLabelLogoMeniuPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBtnInchideApp, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneMeniu.addTab("Meniu principal", jPanelMeniuPrincipal);

        jPanelComandaNoua.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelComandaNoua.setPreferredSize(new java.awt.Dimension(1080, 680));

        jLabelTitluComNoua.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluComNoua.setText("Generare comanda laborator metrologie");

        jLabelNumeComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNumeComNoua.setText("Nume:");

        jLabelDescComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelDescComNoua.setText("Descriere:");

        jTextFieldNumeComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldNumeComNoua.setForeground(new java.awt.Color(0, 153, 255));

        jTextAreaDescComNoua.setColumns(20);
        jTextAreaDescComNoua.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaDescComNoua.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaDescComNoua.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDescComNoua);

        jListComNoua.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jListComNoua.setForeground(new java.awt.Color(0, 153, 255));
        jScrollPane4.setViewportView(jListComNoua);

        jBtnGenComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnGenComNoua.setText("Genereaza");
        jBtnGenComNoua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGenComNouaActionPerformed(evt);
            }
        });

        jBtnInchideMeniuComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnInchideMeniuComNoua.setText("Inchide");
        jBtnInchideMeniuComNoua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInchideMeniuComNouaActionPerformed(evt);
            }
        });

        jBtnAdaugaFisierComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnAdaugaFisierComNoua.setText("Adauga fisier");
        jBtnAdaugaFisierComNoua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdaugaFisierComNouaActionPerformed(evt);
            }
        });

        jLabelAvertismenteComNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteComNoua.setForeground(new java.awt.Color(255, 0, 0));

        jBtnFinalizareCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnFinalizareCom.setText("Finalizare");
        jBtnFinalizareCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFinalizareComActionPerformed(evt);
            }
        });

        jLabelTipGenCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelTipGenCom.setText("Tip:");

        jTextFieldTipGenCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldTipGenCom.setForeground(new java.awt.Color(0, 153, 255));

        jLabelFisiereGenCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelFisiereGenCom.setText("Fisiere:");

        jBtnNextStep.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnNextStep.setText("Verificare metrologica =>");
        jBtnNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNextStepActionPerformed(evt);
            }
        });

        jBtnScoateFisierGenCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnScoateFisierGenCom.setText("Scoate Fisier");

        javax.swing.GroupLayout jPanelComandaNouaLayout = new javax.swing.GroupLayout(jPanelComandaNoua);
        jPanelComandaNoua.setLayout(jPanelComandaNouaLayout);
        jPanelComandaNouaLayout.setHorizontalGroup(
            jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                        .addComponent(jLabelTitluComNoua)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                                .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                                            .addGap(7, 7, 7)
                                            .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabelNumeComNoua)
                                                .addComponent(jLabelTipGenCom)
                                                .addComponent(jLabelDescComNoua))
                                            .addGap(7, 7, 7))
                                        .addComponent(jLabelFisiereGenCom, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jBtnFinalizareCom, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnGenComNoua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addComponent(jTextFieldTipGenCom, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNumeComNoua, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jBtnNextStep)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelComandaNouaLayout.createSequentialGroup()
                                .addComponent(jBtnScoateFisierGenCom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBtnAdaugaFisierComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFileChooserGenCom, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                            .addComponent(jLabelAvertismenteComNoua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelComandaNouaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jBtnInchideMeniuComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanelComandaNouaLayout.setVerticalGroup(
            jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluComNoua)
                .addGap(35, 35, 35)
                .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumeComNoua)
                            .addComponent(jTextFieldNumeComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTipGenCom)
                            .addComponent(jTextFieldTipGenCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                                .addComponent(jLabelDescComNoua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnGenComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelAvertismenteComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                                .addComponent(jLabelFisiereGenCom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnFinalizareCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnAdaugaFisierComNoua, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jBtnScoateFisierGenCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelComandaNouaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFileChooserGenCom, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanelComandaNouaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnInchideMeniuComNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnNextStep, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jTabbedPaneMeniu.addTab("Generare comanda", jPanelComandaNoua);

        jPanelVizCom.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelVizCom.setPreferredSize(new java.awt.Dimension(1080, 680));

        jBtnStergeCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnStergeCom.setText("Sterge comanda");
        jBtnStergeCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStergeComActionPerformed(evt);
            }
        });

        jBtnInchideTabVizCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnInchideTabVizCom.setText("Inchide");
        jBtnInchideTabVizCom.setToolTipText("Doresti sa inchizi tabul de vizualizare comenzi?");
        jBtnInchideTabVizCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInchideTabVizComActionPerformed(evt);
            }
        });

        jLabelAvertismenteVizCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteVizCom.setForeground(new java.awt.Color(255, 0, 0));

        jLabelTitluVizCom.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluVizCom.setText("Vizualizare comenzi laborator metrologie");

        jBtnModificaCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnModificaCom.setText("Modifica comanda");
        jBtnModificaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnModificaComActionPerformed(evt);
            }
        });

        jBtnExpCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnExpCom.setText("Exporta comanda");
        jBtnExpCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExpComActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelVizComLayout = new javax.swing.GroupLayout(jPanelVizCom);
        jPanelVizCom.setLayout(jPanelVizComLayout);
        jPanelVizComLayout.setHorizontalGroup(
            jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVizComLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVizComLayout.createSequentialGroup()
                        .addComponent(jLabelTitluVizCom)
                        .addGap(0, 171, Short.MAX_VALUE))
                    .addGroup(jPanelVizComLayout.createSequentialGroup()
                        .addGroup(jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnStergeCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnModificaCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnExpCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVizComLayout.createSequentialGroup()
                                .addComponent(jLabelAvertismenteVizCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnInchideTabVizCom, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFileChooserVizCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelVizComLayout.setVerticalGroup(
            jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVizComLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluVizCom)
                .addGroup(jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVizComLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jBtnExpCom, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnModificaCom, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnStergeCom, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelVizComLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFileChooserVizCom, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelVizComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAvertismenteVizCom, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                    .addGroup(jPanelVizComLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnInchideTabVizCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPaneMeniu.addTab("Vizualizare comenzi", jPanelVizCom);

        jPanelModCom.setPreferredSize(new java.awt.Dimension(1080, 680));

        jFileChooserModCom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jTextFieldNumeModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldNumeModCom.setForeground(new java.awt.Color(0, 153, 255));

        jTextFieldTipModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldTipModCom.setForeground(new java.awt.Color(0, 153, 255));

        jLabelNumeModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNumeModCom.setText("Numele:");

        jLabelTipModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelTipModCom.setText("Tipul:");

        jTextAreaDescModCom.setColumns(20);
        jTextAreaDescModCom.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaDescModCom.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaDescModCom.setRows(5);
        jScrollPane6.setViewportView(jTextAreaDescModCom);

        jLabelDescModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelDescModCom.setText("Descrierea:");

        jListModCom.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jListModCom.setForeground(new java.awt.Color(0, 153, 255));
        jScrollPane7.setViewportView(jListModCom);

        jLabelFisiereModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelFisiereModCom.setText("Fisiere:");

        jBtnInchideTabModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnInchideTabModCom.setText("Inchide");
        jBtnInchideTabModCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInchideTabModComActionPerformed(evt);
            }
        });

        jLabelAvertismenteModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteModCom.setForeground(new java.awt.Color(255, 0, 0));

        jBtnSelectModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnSelectModCom.setText("Selecteaza");
        jBtnSelectModCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSelectModComActionPerformed(evt);
            }
        });

        jBtnModificareCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnModificareCom.setText("Salveaza");
        jBtnModificareCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnModificareComActionPerformed(evt);
            }
        });

        jBtnScoateModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnScoateModCom.setText("Scoate");
        jBtnScoateModCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnScoateModComActionPerformed(evt);
            }
        });

        jBtnCopiazaModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnCopiazaModCom.setText("Adauga");
        jBtnCopiazaModCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCopiazaModComActionPerformed(evt);
            }
        });

        jBtnIncarcaMapa.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnIncarcaMapa.setText("Mapa");
        jBtnIncarcaMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncarcaMapaActionPerformed(evt);
            }
        });

        jLabelTitluModCom.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluModCom.setText("Moficare comenzi laborator metrologie");

        jBtnGolesteSelectie.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnGolesteSelectie.setText("Goleste");
        jBtnGolesteSelectie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGolesteSelectieActionPerformed(evt);
            }
        });

        jFileChooserAccesFisiere.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabelAfisareComModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAfisareComModCom.setText("Afisare comenzi:");

        jLabelAfisareFisiereModCom.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAfisareFisiereModCom.setText("Afisare fisiere:");

        javax.swing.GroupLayout jPanelModComLayout = new javax.swing.GroupLayout(jPanelModCom);
        jPanelModCom.setLayout(jPanelModComLayout);
        jPanelModComLayout.setHorizontalGroup(
            jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelModComLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelModComLayout.createSequentialGroup()
                        .addComponent(jLabelTitluModCom)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelModComLayout.createSequentialGroup()
                        .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelAvertismenteModCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelModComLayout.createSequentialGroup()
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelModComLayout.createSequentialGroup()
                                        .addComponent(jLabelFisiereModCom)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelModComLayout.createSequentialGroup()
                                        .addGap(0, 32, Short.MAX_VALUE)
                                        .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelNumeModCom, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelTipModCom, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelDescModCom, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jBtnGolesteSelectie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNumeModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTipModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelModComLayout.createSequentialGroup()
                                .addComponent(jBtnCopiazaModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnScoateModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnIncarcaMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFileChooserModCom, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                            .addComponent(jFileChooserAccesFisiere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelModComLayout.createSequentialGroup()
                                .addComponent(jBtnModificareCom, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnSelectModCom))
                            .addGroup(jPanelModComLayout.createSequentialGroup()
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelAfisareFisiereModCom)
                                    .addComponent(jLabelAfisareComModCom))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelModComLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jBtnInchideTabModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanelModComLayout.setVerticalGroup(
            jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelModComLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluModCom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldNumeModCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelNumeModCom))
                    .addComponent(jLabelAfisareComModCom))
                .addGap(18, 18, 18)
                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelModComLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jFileChooserAccesFisiere, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnInchideTabModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanelModComLayout.createSequentialGroup()
                        .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelModComLayout.createSequentialGroup()
                                .addComponent(jFileChooserModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jBtnModificareCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnSelectModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabelAfisareFisiereModCom)
                                .addGap(172, 172, 172))
                            .addGroup(jPanelModComLayout.createSequentialGroup()
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldTipModCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelTipModCom))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelModComLayout.createSequentialGroup()
                                        .addComponent(jLabelDescModCom)
                                        .addGap(18, 18, 18)
                                        .addComponent(jBtnGolesteSelectie)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelFisiereModCom))
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelModComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jBtnScoateModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnCopiazaModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnIncarcaMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelAvertismenteModCom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPaneMeniu.addTab("Modificare comanda", jPanelModCom);

        jPanelVerMet.setPreferredSize(new java.awt.Dimension(1080, 680));

        jLabelTitluVerMet.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluVerMet.setText("Comanda de verificare metrologica");

        jLabelNr.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNr.setText("Numar:");

        jTextFieldNrVerMet.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldNrVerMet.setForeground(new java.awt.Color(0, 153, 255));

        jLabelData.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelData.setText("Data:");

        jTextFieldDataVerMet.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldDataVerMet.setForeground(new java.awt.Color(0, 153, 255));

        jLabelNumeSolicitant.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNumeSolicitant.setText("Denumire solicitant:");

        jTextFieldNumeSolicitant.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldNumeSolicitant.setForeground(new java.awt.Color(0, 153, 255));

        jLabelAdresa.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAdresa.setText("Adresa:");

        jTextFieldAdresa.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldAdresa.setForeground(new java.awt.Color(0, 153, 255));

        jLabelCodFiscal.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelCodFiscal.setText("Cod Fiscal:");

        jTextFieldCodFiscal.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldCodFiscal.setForeground(new java.awt.Color(0, 153, 255));

        jLabelIBAN.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelIBAN.setText("Cod IBAN banca:");

        jTextFieldIBAN.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldIBAN.setForeground(new java.awt.Color(0, 153, 255));

        jLabelPersoanaContact.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelPersoanaContact.setText("Persoana contact:");

        jLabelNrOV.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNrOV.setText("Denumire / Nr. O.V.");

        jTableVerMet.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jTableVerMet.setForeground(new java.awt.Color(0, 153, 255));
        jTableVerMet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tip / Caracteristici tehnice", "Producator", "Serii / An fabricatie", "Nr. aprobare model"
            }
        ));
        jTableVerMet.setRowHeight(90);
        jTableVerMet.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTableVerMet);
        if (jTableVerMet.getColumnModel().getColumnCount() > 0) {
            jTableVerMet.getColumnModel().getColumn(0).setHeaderValue("Tip / Caracteristici tehnice");
            jTableVerMet.getColumnModel().getColumn(1).setHeaderValue("Producator");
            jTableVerMet.getColumnModel().getColumn(2).setHeaderValue("Serii / An fabricatie");
            jTableVerMet.getColumnModel().getColumn(3).setHeaderValue("Nr. aprobare model");
        }

        jLabelTensiuneMedie.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelTensiuneMedie.setText("Curent medie tensiune");

        jLabelBucCurentTensiuneMedie.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelBucCurentTensiuneMedie.setText("Nr. buc:");

        jTextFieldBucTensiuneMedie.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldBucTensiuneMedie.setForeground(new java.awt.Color(0, 153, 255));

        jLabelTensiuneMedieTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelTensiuneMedieTensiune.setText("Tensiune medie tensiune");

        jLabelBucTensiuneMedieTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelBucTensiuneMedieTensiune.setText("Nr. buc:");

        jTextFieldBucTensiuneMedieTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldBucTensiuneMedieTensiune.setForeground(new java.awt.Color(0, 153, 255));

        jLabelJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelJoasaTensiune.setText("Curent joasa tensiune");

        jLabelBucCurentJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelBucCurentJoasaTensiune.setText("Nr. buc:");

        jTextFieldBucJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldBucJoasaTensiune.setForeground(new java.awt.Color(0, 153, 255));

        jLabelTensiuneJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelTensiuneJoasaTensiune.setText("Tensiune joasa tensiune");

        jLabelBucTensiuneJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelBucTensiuneJoasaTensiune.setText("Nr. buc:");

        jTextFieldBucTensiuneJoasaTensiune.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldBucTensiuneJoasaTensiune.setForeground(new java.awt.Color(0, 153, 255));

        jLabelAverstismenteVerMet.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAverstismenteVerMet.setForeground(new java.awt.Color(255, 0, 0));

        jBtnInchideTabVerMet.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnInchideTabVerMet.setText("Inchide");
        jBtnInchideTabVerMet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInchideTabVerMetActionPerformed(evt);
            }
        });

        jBtnSalvareVerMet.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnSalvareVerMet.setText("Salvare");
        jBtnSalvareVerMet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalvareVerMetActionPerformed(evt);
            }
        });

        jButtonTabFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonTabFisaAnalizaDoc.setText("Fisa analiza document =>");
        jButtonTabFisaAnalizaDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTabFisaAnalizaDocActionPerformed(evt);
            }
        });

        jTextFieldPersoanaContact.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldPersoanaContact.setForeground(new java.awt.Color(0, 153, 255));

        javax.swing.GroupLayout jPanelVerMetLayout = new javax.swing.GroupLayout(jPanelVerMet);
        jPanelVerMet.setLayout(jPanelVerMetLayout);
        jPanelVerMetLayout.setHorizontalGroup(
            jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelCodFiscal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCodFiscal))
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelAdresa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAdresa))
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelNumeSolicitant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNumeSolicitant))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVerMetLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                        .addComponent(jLabelNr)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldNrVerMet, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelData)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDataVerMet, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabelTitluVerMet))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAverstismenteVerMet, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVerMetLayout.createSequentialGroup()
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelBucTensiuneJoasaTensiune)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldBucTensiuneJoasaTensiune, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelBucCurentJoasaTensiune)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldBucJoasaTensiune, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelTensiuneJoasaTensiune, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNrOV)
                            .addComponent(jLabelTensiuneMedieTensiune)
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelBucTensiuneMedieTensiune)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldBucTensiuneMedieTensiune, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelVerMetLayout.createSequentialGroup()
                                    .addComponent(jLabelBucCurentTensiuneMedie)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldBucTensiuneMedie, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelTensiuneMedie))
                            .addComponent(jLabelJoasaTensiune)
                            .addComponent(jBtnSalvareVerMet, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jButtonTabFisaAnalizaDoc)
                                .addGap(127, 127, 127)
                                .addComponent(jBtnInchideTabVerMet, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                        .addComponent(jLabelPersoanaContact)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPersoanaContact))
                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                        .addComponent(jLabelIBAN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIBAN)))
                .addContainerGap())
        );
        jPanelVerMetLayout.setVerticalGroup(
            jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                        .addComponent(jLabelTitluVerMet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNr)
                            .addComponent(jLabelData)
                            .addComponent(jTextFieldDataVerMet)
                            .addComponent(jTextFieldNrVerMet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNumeSolicitant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNumeSolicitant))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAdresa))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCodFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCodFiscal)))
                    .addComponent(jLabelAverstismenteVerMet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelIBAN)
                    .addComponent(jTextFieldIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPersoanaContact, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPersoanaContact))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVerMetLayout.createSequentialGroup()
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addComponent(jLabelNrOV)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTensiuneMedie)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldBucTensiuneMedie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelBucCurentTensiuneMedie))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTensiuneMedieTensiune)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelBucTensiuneMedieTensiune)
                                    .addComponent(jTextFieldBucTensiuneMedieTensiune))
                                .addGap(45, 45, 45))
                            .addGroup(jPanelVerMetLayout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(jLabelJoasaTensiune)))
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBucCurentJoasaTensiune)
                            .addComponent(jTextFieldBucJoasaTensiune, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTensiuneJoasaTensiune)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBucTensiuneJoasaTensiune)
                            .addComponent(jTextFieldBucTensiuneJoasaTensiune)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelVerMetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnInchideTabVerMet, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jBtnSalvareVerMet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonTabFisaAnalizaDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneMeniu.addTab("Verificare metrologica", jPanelVerMet);

        jPanelFisaAnalizaDoc.setMinimumSize(new java.awt.Dimension(1080, 720));
        jPanelFisaAnalizaDoc.setPreferredSize(new java.awt.Dimension(1080, 720));

        jLabelTitluAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluAnalizaDoc.setText("Fisa Analiza Document");

        jLabelDenumireDocAnalizat.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelDenumireDocAnalizat.setText("Denumire Document Analizat:");

        jTextFieldDenumireDocAnalizat.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldDenumireDocAnalizat.setForeground(new java.awt.Color(0, 153, 255));

        jLabelClientFizaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelClientFizaAnalizaDoc.setText("Client:");

        jTextFieldClientFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldClientFisaAnalizaDoc.setForeground(new java.awt.Color(0, 153, 255));

        jLabelContinutDocFisaAnaliza.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelContinutDocFisaAnaliza.setText("1. Continutul documentului:");

        jTextAreaContinutDocFisaAnaliza.setColumns(20);
        jTextAreaContinutDocFisaAnaliza.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaContinutDocFisaAnaliza.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaContinutDocFisaAnaliza.setRows(5);
        jScrollPane9.setViewportView(jTextAreaContinutDocFisaAnaliza);

        jLabelMetodaFolositaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelMetodaFolositaAnalizaDoc.setText("2. Metoda folosita:");

        jTextAreaMetodaFisaAnaliza.setColumns(20);
        jTextAreaMetodaFisaAnaliza.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaMetodaFisaAnaliza.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaMetodaFisaAnaliza.setRows(5);
        jScrollPane10.setViewportView(jTextAreaMetodaFisaAnaliza);

        jLabelObservatiiFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelObservatiiFisaAnalizaDoc.setText("3. Observatii:");

        jTextAreaObservatiiFisaAnaliza.setColumns(20);
        jTextAreaObservatiiFisaAnaliza.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaObservatiiFisaAnaliza.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaObservatiiFisaAnaliza.setRows(5);
        jScrollPane11.setViewportView(jTextAreaObservatiiFisaAnaliza);

        jLabelDecizieAnaliza.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelDecizieAnaliza.setText("4. Decizie in urma analizei:");

        jTextAreaDecizieAnaliza.setColumns(20);
        jTextAreaDecizieAnaliza.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaDecizieAnaliza.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaDecizieAnaliza.setRows(5);
        jScrollPane12.setViewportView(jTextAreaDecizieAnaliza);

        jLabelDataFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelDataFisaAnalizaDoc.setText("Data:");

        jTextFieldDataFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldDataFisaAnalizaDoc.setForeground(new java.awt.Color(0, 153, 255));

        jButtonSalvareFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonSalvareFisaAnalizaDoc.setText("Salvare");
        jButtonSalvareFisaAnalizaDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvareFisaAnalizaDocActionPerformed(evt);
            }
        });

        jButtonInchideTabFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonInchideTabFisaAnalizaDoc.setText("Inchide");
        jButtonInchideTabFisaAnalizaDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInchideTabFisaAnalizaDocActionPerformed(evt);
            }
        });

        jLabelAvertismenteFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteFisaAnalizaDoc.setForeground(new java.awt.Color(255, 0, 0));

        jButton1.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButton1.setText("Proces verbal receptie =>");

        jLabelIntocmitFisaAnalizaDoc.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelIntocmitFisaAnalizaDoc.setText("Intocmit:");

        jTextFieldIntocmitFisaAnalizaDocument.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldIntocmitFisaAnalizaDocument.setForeground(new java.awt.Color(0, 153, 255));

        javax.swing.GroupLayout jPanelFisaAnalizaDocLayout = new javax.swing.GroupLayout(jPanelFisaAnalizaDoc);
        jPanelFisaAnalizaDoc.setLayout(jPanelFisaAnalizaDocLayout);
        jPanelFisaAnalizaDocLayout.setHorizontalGroup(
            jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jLabelDenumireDocAnalizat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDenumireDocAnalizat))
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jLabelClientFizaAnalizaDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldClientFisaAnalizaDoc))
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelDecizieAnaliza)
                            .addComponent(jLabelTitluAnalizaDoc)
                            .addComponent(jLabelContinutDocFisaAnaliza)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addComponent(jLabelMetodaFolositaAnalizaDoc)
                            .addComponent(jScrollPane10)
                            .addComponent(jLabelObservatiiFisaAnalizaDoc)
                            .addComponent(jScrollPane11)
                            .addComponent(jScrollPane12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelAvertismenteFisaAnalizaDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jButtonSalvareFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(140, 140, 140)
                        .addComponent(jButtonInchideTabFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jLabelDataFisaAnalizaDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDataFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIntocmitFisaAnalizaDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIntocmitFisaAnalizaDocument)))
                .addContainerGap())
        );
        jPanelFisaAnalizaDocLayout.setVerticalGroup(
            jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluAnalizaDoc)
                .addGap(18, 18, 18)
                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDenumireDocAnalizat)
                    .addComponent(jTextFieldDenumireDocAnalizat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelClientFizaAnalizaDoc)
                    .addComponent(jTextFieldClientFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelContinutDocFisaAnaliza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jLabelMetodaFolositaAnalizaDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelObservatiiFisaAnalizaDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDecizieAnaliza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDataFisaAnalizaDoc)
                                    .addComponent(jLabelDataFisaAnalizaDoc)
                                    .addComponent(jLabelIntocmitFisaAnalizaDoc)
                                    .addComponent(jTextFieldIntocmitFisaAnalizaDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(96, 96, 96))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFisaAnalizaDocLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelFisaAnalizaDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonInchideTabFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonSalvareFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(jPanelFisaAnalizaDocLayout.createSequentialGroup()
                        .addComponent(jLabelAvertismenteFisaAnalizaDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPaneMeniu.addTab("Fisa analiza document", jPanelFisaAnalizaDoc);

        jPanelProcesVerbalReceptieOV.setPreferredSize(new java.awt.Dimension(1080, 680));

        jLabelTitluProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluProcesVerbalRec.setText("Proces verbal receptie O.V.");

        jLabelNrProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelNrProcesVerbalRec.setText("Nr:");

        jTextFieldNrProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldNrProcesVerbalRec.setForeground(new java.awt.Color(0, 153, 255));

        jLabelDataProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelDataProcesVerbalRec.setText("Data:");

        jTextFieldDataProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldDataProcesVerbalRec.setForeground(new java.awt.Color(0, 153, 255));

        jLabelDeLaProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelDeLaProcesVerbalRec.setText("De la:");

        jTextFieldDeLaProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldDeLaProcesVerbalRec.setForeground(new java.awt.Color(0, 153, 255));

        jLabelDenumireOV_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelDenumireOV_1.setText("Denumire O.V:");

        jTextFieldDenumireOV_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldDenumireOV_1.setForeground(new java.awt.Color(0, 153, 255));

        jLabelProducatorProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelProducatorProcesVerbalRec_1.setText("Producator / tip:");

        jTextFieldProducatorProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldProducatorProcesVerbalRec_1.setForeground(new java.awt.Color(0, 153, 255));

        jLabelCaracProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelCaracProcesVerbalRec_1.setText("Caracteristici:");

        jTextFieldCaracProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldCaracProcesVerbalRec_1.setForeground(new java.awt.Color(0, 153, 255));

        jLabelSerieAnFabBuc.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelSerieAnFabBuc.setText("Serie / An fabricatie / Buc:");

        jTextAreaSerieAnFabBuc.setColumns(20);
        jTextAreaSerieAnFabBuc.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaSerieAnFabBuc.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaSerieAnFabBuc.setRows(5);
        jScrollPane13.setViewportView(jTextAreaSerieAnFabBuc);

        jLabelDenumireOV_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelDenumireOV_2.setText("Denumire O.V:");

        jTextFieldDenumireOV_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldDenumireOV_2.setForeground(new java.awt.Color(0, 153, 255));

        jLabelProducatorProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelProducatorProcesVerbalRec_2.setText("Producator / tip:");

        jTextFieldProducatorProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldProducatorProcesVerbalRec_2.setForeground(new java.awt.Color(0, 153, 255));

        jLabelCaracProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelCaracProcesVerbalRec_2.setText("Caracteristici:");

        jTextFieldCaracProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldCaracProcesVerbalRec_2.setForeground(new java.awt.Color(0, 153, 255));

        jLabelSerieAnFabBuc_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelSerieAnFabBuc_2.setText("Serie / An fabricatie / Buc:");

        jTextAreaSerieAnFabBuc_2.setColumns(20);
        jTextAreaSerieAnFabBuc_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextAreaSerieAnFabBuc_2.setForeground(new java.awt.Color(0, 153, 255));
        jTextAreaSerieAnFabBuc_2.setRows(5);
        jScrollPane14.setViewportView(jTextAreaSerieAnFabBuc_2);

        jLabelAbateri.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelAbateri.setText("Abateri de la conditiile normale constatate la receptie:");

        jTextFieldAbateri.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldAbateri.setForeground(new java.awt.Color(0, 153, 255));

        jLabelRezultatConsultare.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelRezultatConsultare.setText("Rezultatul consultarii clientului:");

        jTextFieldRezultatConsultare.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldRezultatConsultare.setForeground(new java.awt.Color(0, 153, 255));

        jLabelNotaProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelNotaProcesVerbalRec.setText("Nota:");

        jTextFieldNotaProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldNotaProcesVerbalRec.setForeground(new java.awt.Color(0, 153, 255));

        jLabelNumePrenumeProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelNumePrenumeProcesVerbalRec_1.setText("Nume si prenume:");

        jTextFieldNumePrenumeProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldNumePrenumeProcesVerbalRec_1.setForeground(new java.awt.Color(0, 153, 255));

        jLabelFunctieProcesVerbalRec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelFunctieProcesVerbalRec_1.setText("Functia:");

        jTextFieldFunctieProcesVerbalrec_1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldFunctieProcesVerbalrec_1.setForeground(new java.awt.Color(0, 153, 255));

        jLabelNumePrenumeProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelNumePrenumeProcesVerbalRec_2.setText("Nume si prenume:");

        jLabelFunctieProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabelFunctieProcesVerbalRec_2.setText("Functia:");

        jTextFieldNumePrenumeProcesVerbalRec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldNumePrenumeProcesVerbalRec_2.setForeground(new java.awt.Color(0, 153, 255));

        jTextFieldFunctieProcesVerbalrec_2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextFieldFunctieProcesVerbalrec_2.setForeground(new java.awt.Color(0, 153, 255));

        jLabelAvertismenteProcesVerbalRec.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteProcesVerbalRec.setForeground(new java.awt.Color(255, 0, 0));

        jButtonNextReceptieOV.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonNextReceptieOV.setText("Pasul urmator =>");

        jButtonInchideTabReceptieOV.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonInchideTabReceptieOV.setText("Inchide");

        jButtonSalvareReceptieOV.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jButtonSalvareReceptieOV.setText("Salveaza");

        javax.swing.GroupLayout jPanelProcesVerbalReceptieOVLayout = new javax.swing.GroupLayout(jPanelProcesVerbalReceptieOV);
        jPanelProcesVerbalReceptieOV.setLayout(jPanelProcesVerbalReceptieOVLayout);
        jPanelProcesVerbalReceptieOVLayout.setHorizontalGroup(
            jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelSerieAnFabBuc_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelCaracProcesVerbalRec_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCaracProcesVerbalRec_2))
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelProducatorProcesVerbalRec_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldProducatorProcesVerbalRec_2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelNotaProcesVerbalRec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNotaProcesVerbalRec))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelRezultatConsultare)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRezultatConsultare))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelAbateri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAbateri))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelNumePrenumeProcesVerbalRec_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNumePrenumeProcesVerbalRec_1))
                            .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelFunctieProcesVerbalRec_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFunctieProcesVerbalrec_1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelNumePrenumeProcesVerbalRec_2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNumePrenumeProcesVerbalRec_2, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonInchideTabReceptieOV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                    .addComponent(jLabelFunctieProcesVerbalRec_2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldFunctieProcesVerbalrec_2, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelNrProcesVerbalRec)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNrProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDataProcesVerbalRec)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDataProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelDeLaProcesVerbalRec)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDeLaProcesVerbalRec))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelDenumireOV_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDenumireOV_1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelProducatorProcesVerbalRec_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldProducatorProcesVerbalRec_1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jLabelCaracProcesVerbalRec_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCaracProcesVerbalRec_1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAvertismenteProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelSerieAnFabBuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13))
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelDenumireOV_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDenumireOV_2))
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitluProcesVerbalRec)
                            .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                                .addComponent(jButtonNextReceptieOV, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(151, 151, 151)
                                .addComponent(jButtonSalvareReceptieOV, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(169, 169, 169))
        );
        jPanelProcesVerbalReceptieOVLayout.setVerticalGroup(
            jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelProcesVerbalReceptieOVLayout.createSequentialGroup()
                        .addComponent(jLabelTitluProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelDataProcesVerbalRec)
                                .addComponent(jTextFieldDataProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelNrProcesVerbalRec)
                                .addComponent(jTextFieldNrProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDeLaProcesVerbalRec)
                            .addComponent(jTextFieldDeLaProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDenumireOV_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDenumireOV_1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldProducatorProcesVerbalRec_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelProducatorProcesVerbalRec_1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCaracProcesVerbalRec_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCaracProcesVerbalRec_1)))
                    .addComponent(jLabelAvertismenteProcesVerbalRec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSerieAnFabBuc)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDenumireOV_2)
                    .addComponent(jTextFieldDenumireOV_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProducatorProcesVerbalRec_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelProducatorProcesVerbalRec_2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCaracProcesVerbalRec_2)
                    .addComponent(jTextFieldCaracProcesVerbalRec_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSerieAnFabBuc_2)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAbateri)
                    .addComponent(jTextFieldAbateri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRezultatConsultare)
                    .addComponent(jTextFieldRezultatConsultare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNotaProcesVerbalRec)
                    .addComponent(jTextFieldNotaProcesVerbalRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumePrenumeProcesVerbalRec_1)
                    .addComponent(jTextFieldNumePrenumeProcesVerbalRec_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNumePrenumeProcesVerbalRec_2)
                    .addComponent(jTextFieldNumePrenumeProcesVerbalRec_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFunctieProcesVerbalRec_1)
                    .addComponent(jTextFieldFunctieProcesVerbalrec_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFunctieProcesVerbalRec_2)
                    .addComponent(jTextFieldFunctieProcesVerbalrec_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanelProcesVerbalReceptieOVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInchideTabReceptieOV, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSalvareReceptieOV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNextReceptieOV, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneMeniu.addTab("Proces verbal receptie", jPanelProcesVerbalReceptieOV);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMeniu, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMeniu, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
        );
    }//GEN-END:initComponents

    private void jBtnInchideMeniuComNouaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInchideMeniuComNouaActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.remove(jPanelComandaNoua);
    }//GEN-LAST:event_jBtnInchideMeniuComNouaActionPerformed

    private void jBtnInchideAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInchideAppActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_jBtnInchideAppActionPerformed

    private void jBtnTabAdmUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTabAdmUtiActionPerformed
        // TODO add your handling code here:
        JPasswordField parola = new JPasswordField();
        parola.setFont(new Font("DialogInput", Font.BOLD, 18));
        JCheckBox bifa = new JCheckBox();
        bifa.setFont(new Font("DialogInput", Font.BOLD, 18));
        bifa.setText("Afiseaza parola");
        ActionListener actionListener = new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            if (parola.echoCharIsSet()) {
                parola.setEchoChar((char) 0);
            } else {
                parola.setEchoChar('•');
            }
          }
        };
        bifa.addActionListener(actionListener);
        Object [] inputs = {"Numai administratorul are acces la utilizatori!\n" +
                            "Va rog sa introduceti parola de administrator:", parola, bifa};
        Object [] optiuni = {"Acceseaza", "Anuleaza"};
        UIManager.put("OptionPane.messageForeground", Color.RED);
        UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
        UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
        final JOptionPane panouOptiuni = new JOptionPane(inputs, JOptionPane.WARNING_MESSAGE,
                                                         JOptionPane.YES_NO_OPTION,
                                                         null,
                                                         optiuni,
                                                         optiuni[0]);
        final JDialog dialog = new JDialog(DLMAppStart.appStart, 
                                           "Confirmare:",
                                           true);
        dialog.setContentPane(panouOptiuni);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                parola.requestFocusInWindow();
            }
        });
        panouOptiuni.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
                String proprietate = ev.getPropertyName();
                if (dialog.isVisible() && ev.getSource() == panouOptiuni
                    && proprietate.equals(JOptionPane.VALUE_PROPERTY)
                    || proprietate.equals(JOptionPane.INPUT_VALUE_PROPERTY)){
                    panouOptiuni.setInputValue(JOptionPane.YES_NO_OPTION);
                    Object valoare = panouOptiuni.getValue();
                    if (valoare == JOptionPane.UNINITIALIZED_VALUE) {
                        return;
                    }
                    panouOptiuni.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    if ("Acceseaza".equals(valoare)) {
                        UsersDAO userManagement = new UsersDAO(db.getConnection());
                        Map<String, String> listaUti = new HashMap<>();
                        try {
                             listaUti = userManagement.getAll();
                        } catch (SQLException ex) {
                             java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        String getParola = listaUti.get("Admin");
                        if (!parola.getText().isEmpty() && parola.getText().equals(getParola)) {
                            Image logo = Toolkit.getDefaultToolkit().getImage("C:\\Oracle\\Middleware\\Oracle_Home\\" +
                                                                              "jdeveloper\\mywork\\mywork\\" +
                                                                              "DigitalizareLaboratorMetrologie\\project\\" +
                                                                              "FisiereDLM\\FisiereAplicatie\\Dsr.png");
                            fereastraUtilizatori.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            fereastraUtilizatori.addWindowListener(new inchideAdmUtilizatori());
                            fereastraUtilizatori.setIconImage(logo);
                            fereastraUtilizatori.setSize(1090, 710);
                            fereastraUtilizatori.toFront();
                            fereastraUtilizatori.setVisible(true);
                            fereastraUtilizatori.requestFocus();
                            fereastraUtilizatori.add(meniuUtilizatori);
                            /*jTabbedPaneMeniu.addTab("Administrare utilizatori", jPanelMeniuUti);
                            ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
                            jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelMeniuUti), inchide);*/
                            dialog.dispose();
                        } else {
                            Font normalFont = new Font("DialogInput", Font.BOLD, 18);
                            UIManager.put("OptionPane.messageForeground", Color.red);
                            UIManager.put("OptionPane.font", normalFont);
                            JOptionPane.showMessageDialog(DLMAppStart.appStart,
                                                          "<html><center>Parola incorecta!<br>" +
                                                          "Incercati din nou va rog!</center></html>",
                                                          "Eroare:", JOptionPane.ERROR_MESSAGE);
                            parola.requestFocusInWindow();
                        }    
                    } else {
                        dialog.dispose();    
                    }
                }
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(DLMAppStart.appStart);
        dialog.setVisible(true);
    }//GEN-LAST:event_jBtnTabAdmUtiActionPerformed

    private void jBtnTabFiseLaboratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTabFiseLaboratorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnTabFiseLaboratorActionPerformed

    private void jBtnTabAdmFisiereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTabAdmFisiereActionPerformed
        // TODO add your handling code here:
        JPasswordField parola = new JPasswordField();
        parola.setFont(new Font("DialogInput", Font.BOLD, 18));
        JCheckBox bifa = new JCheckBox();
        bifa.setFont(new Font("DialogInput", Font.BOLD, 18));
        bifa.setText("Afiseaza parola");
        ActionListener actionListener = new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            if (parola.echoCharIsSet()) {
                parola.setEchoChar((char) 0);
            } else {
                parola.setEchoChar('•');
            }
          }
        };
        bifa.addActionListener(actionListener);
        Object [] inputs = {"<html><center>Trebuie sa fii dev, daca doresti<br>sa accesezi administrarea fisierelor!<br>" +
                            "Stii parola de dev?</center></html>", parola, bifa};
        Object [] optiuni = {"Acceseaza", "Anuleaza"};
        UIManager.put("OptionPane.messageForeground", Color.RED);
        UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
        UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
        final JOptionPane panouOptiuni = new JOptionPane(inputs, JOptionPane.WARNING_MESSAGE,
                                                         JOptionPane.YES_NO_OPTION,
                                                         null,
                                                         optiuni,
                                                         optiuni[0]);
        final JDialog dialog = new JDialog(DLMAppStart.appStart, 
                                           "Confirmare:",
                                           true);
        dialog.setContentPane(panouOptiuni);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                parola.requestFocusInWindow();
            }
        });
        panouOptiuni.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
                String proprietate = ev.getPropertyName();
                if (dialog.isVisible() && ev.getSource() == panouOptiuni
                    && proprietate.equals(JOptionPane.VALUE_PROPERTY)
                    || proprietate.equals(JOptionPane.INPUT_VALUE_PROPERTY)){
                    panouOptiuni.setInputValue(JOptionPane.YES_NO_OPTION);
                    Object valoare = panouOptiuni.getValue();
                    if (valoare == JOptionPane.UNINITIALIZED_VALUE) {
                        return;
                    }
                    panouOptiuni.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    if ("Acceseaza".equals(valoare)) {
                        UsersDAO userManagement = new UsersDAO(db.getConnection());
                        Map<String, String> listaUti = new HashMap<>();
                        try {
                             listaUti = userManagement.getAll();
                        } catch (SQLException ex) {
                             java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        String getParola = listaUti.get("Dev");
                        if (!parola.getText().isEmpty() && parola.getText().equals(getParola)) {
                            Image logo = Toolkit.getDefaultToolkit().getImage("C:\\Oracle\\Middleware\\Oracle_Home\\" +
                                                                              "jdeveloper\\mywork\\mywork\\" +
                                                                              "DigitalizareLaboratorMetrologie\\project\\" +
                                                                              "FisiereDLM\\FisiereAplicatie\\Dsr.png");
                            fereastraFisiere.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            fereastraFisiere.addWindowListener(new inchideAdmFisiere());
                            fereastraFisiere.setIconImage(logo);
                            fereastraFisiere.setSize(1080, 710);
                            fereastraFisiere.toFront();
                            fereastraFisiere.setVisible(true);
                            fereastraFisiere.requestFocus();
                            fereastraFisiere.add(meniuFisiere);
                            /*jTabbedPaneMeniu.addTab("Administrare fisiere", jPanelMeniuFisiere);
                            ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
                            jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelMeniuFisiere), inchide); */
                            dialog.dispose();
                        } else {
                            Font normalFont = new Font("DialogInput", Font.BOLD, 18);
                            UIManager.put("OptionPane.messageForeground", Color.red);
                            UIManager.put("OptionPane.font", normalFont);
                            JOptionPane.showMessageDialog(DLMAppStart.appStart,
                                                          "<html><center>Parola gresita!</center></html>",
                                                          "Eroare:", JOptionPane.ERROR_MESSAGE);
                            parola.requestFocusInWindow();
                        }    
                    } else {
                        dialog.dispose();    
                    }
                }
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(DLMAppStart.appStart);
        dialog.setVisible(true);
    }//GEN-LAST:event_jBtnTabAdmFisiereActionPerformed

    private void jBtnTabVizComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTabVizComActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.addTab("Vizualizare comenzi", jPanelVizCom);
        ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
        jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelVizCom), inchide);
    }//GEN-LAST:event_jBtnTabVizComActionPerformed

    private void jBtnTabGenComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTabGenComandaActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.addTab("Generare comanda", jPanelComandaNoua);
        ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
        jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelComandaNoua), inchide);
        raportGlobal = null;
        silGlobal = null;
    }//GEN-LAST:event_jBtnTabGenComandaActionPerformed

    private void jBtnAdaugaFisierComNouaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAdaugaFisierComNouaActionPerformed
        // TODO add your handling code here:
        try {
            if (jFileChooserGenCom.getSelectedFile().exists()) {
                listaFisiereGenCom.add(jFileChooserGenCom.getSelectedFile().getName());
                arrayFisiereGenCom = new String[listaFisiereGenCom.size()];
                for(int i = 0; i < listaFisiereGenCom.size(); i++) {
                    arrayFisiereGenCom[i] = listaFisiereGenCom.get(i);
                }
                Arrays.sort(arrayFisiereGenCom);
                jListComNoua.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = arrayFisiereGenCom;
                    public int getSize() { return strings.length; }
                    public String getElementAt(int i) { return strings[i]; }
                });
                dosarGenCom.add(new File(jFileChooserGenCom.getSelectedFile().getAbsolutePath()));
            }
        } catch(NullPointerException ex) {
            jLabelAvertismenteComNoua.setText("<html><center>Trebuie sa selectezi un fisier!</center></html>");
        }
    }//GEN-LAST:event_jBtnAdaugaFisierComNouaActionPerformed

    private void jBtnGenComNouaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGenComNouaActionPerformed
        // TODO add your handling code here:
        try {
            if (!jTextFieldNumeComNoua.getText().isEmpty() && !jTextFieldTipGenCom.getText().isEmpty() && !jTextAreaDescComNoua.getText().isEmpty() && !dosarGenCom.isEmpty()) {
                try {
                    SIL silNou = new SIL(jTextFieldNumeComNoua.getText(), jTextFieldTipGenCom.getText(), jTextAreaDescComNoua.getText());
                    SILmag.save(silNou);
                    silGlobal = silNou;
                    Raport raportNou = new Raport(jTextFieldNumeComNoua.getText(), jTextFieldTipGenCom.getText(), jTextAreaDescComNoua.getText(), silNou.getId());
                    raportMag.save(raportNou);
                    raportGlobal = raportNou;
                    File SILNou = new File(siluriNoi + jTextFieldNumeComNoua.getText() + "\\");
                    SILNou.mkdir();
                    for(File fisier : dosarGenCom) {
                        Files.copy(Paths.get(fisier.toString()),
                        Paths.get(siluriNoi + jTextFieldNumeComNoua.getText() + "\\" + fisier.getName().toString()));
                        fisierManagement.save(new Fisier(siluriNoi + jTextFieldNumeComNoua.getText() +
                                                         "\\" + fisier.getName().toString(), silNou.getId(), 
                                                         raportNou.getId()));
                    }
                    jLabelAvertismenteComNoua.setText("<html><center>Comanda " + jTextFieldNumeComNoua.getText() +
                                                      " s-a generat!</center><html/>");
                    dosarGenCom.clear();
                    listaFisiereGenCom.clear();
                    jListComNoua.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = {};
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jFileChooserVizCom.rescanCurrentDirectory();
                    jFileChooserVizCom.revalidate();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else if (jTextFieldTipGenCom.getText().isEmpty() && jTextAreaDescComNoua.getText().isEmpty() && dosarGenCom.isEmpty()) {
                if (!jTextFieldNumeComNoua.getText().isEmpty()) {
                    Object[] selectie = {"Confirma", "Anuleaza"};
                    UIManager.put("OptionPane.messageForeground", Color.RED);
                    UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
                    UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
                    int optiune = JOptionPane.showOptionDialog(DLMAppStart.appStart,
                        "<html><center>Esti pe cale de a genera o comanda noua,<br>" +
                        "cu campurile tip, descriere si fisiere goale!<br>" +
                        "Esti sigur ca doresti sa continui?</center></html>",
                        "Confirmare:",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,     //do not use a custom Icon
                        selectie,  //the titles of buttons
                        selectie[0]); //default button title
                    if (optiune == JOptionPane.YES_OPTION) {
                        try {
                            SIL silNou = new SIL(jTextFieldNumeComNoua.getText(), jTextFieldTipGenCom.getText(), jTextAreaDescComNoua.getText());
                            SILmag.save(silNou);
                            silGlobal = silNou;
                            
                            Raport raportNou = new Raport(jTextFieldNumeComNoua.getText(), jTextFieldTipGenCom.getText(), jTextAreaDescComNoua.getText(), silNou.getId());
                            raportGlobal = raportNou;
                            raportMag.save(raportNou);
                            File SILNou = new File(siluriNoi + jTextFieldNumeComNoua.getText() + "\\");
                            SILNou.mkdir();
                            for(File fisier : dosarGenCom) {
                                Files.copy(Paths.get(fisier.toString()),
                                Paths.get(siluriNoi + jTextFieldNumeComNoua.getText() + "\\" + 
                                          fisier.getName().toString()));
                                fisierManagement.save(new Fisier(siluriNoi + jTextFieldNumeComNoua.getText() +
                                                                 "\\" + fisier.getName().toString(),
                                                                 silNou.getId(),raportNou.getId()));
                            }
                            jLabelAvertismenteComNoua.setText("<html><center>Comanda " + jTextFieldNumeComNoua.getText() + " s-a generat!</center><html/>");
                            dosarGenCom.clear();
                            listaFisiereGenCom.clear();
                            jListComNoua.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = {};
                                public int getSize() { return strings.length; }
                                public String getElementAt(int i) { return strings[i]; }
                            });
                            jFileChooserVizCom.rescanCurrentDirectory();
                            jFileChooserVizCom.revalidate();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    jLabelAvertismenteComNoua.setText("<html><center>Orice comanda noua<br> trebuie sa aiba macar un nume!</center><html/>");
                }
            }
        } catch(NullPointerException ex) {
            jLabelAvertismenteComNoua.setText("<html><center>In mod normal, toate<br> campurile ar trebui completate!</center><html/>");
        }
    }//GEN-LAST:event_jBtnGenComNouaActionPerformed

    private void jBtnInchideTabVizComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInchideTabVizComActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.remove(jPanelVizCom);
    }//GEN-LAST:event_jBtnInchideTabVizComActionPerformed

    private void jBtnStergeComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStergeComActionPerformed
        // TODO add your handling code here:
        JPasswordField parola = new JPasswordField();
        parola.setFont(new Font("DialogInput", Font.BOLD, 18));
        JCheckBox bifa = new JCheckBox();
        bifa.setFont(new Font("DialogInput", Font.BOLD, 18));
        bifa.setText("Afiseaza parola");
        ActionListener actionListener = new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            if (parola.echoCharIsSet()) {
                parola.setEchoChar((char) 0);
            } else {
                parola.setEchoChar('•');
            }
          }
        };
        bifa.addActionListener(actionListener);
        Object [] inputs = {"Numai administratorul poate sterge o comanda!\n" +
                            "Va rog sa introduceti parola de administrator:", parola, bifa};
        Object [] optiuni = {"Sterge", "Anuleaza"};
        UIManager.put("OptionPane.messageForeground", Color.RED);
        UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
        UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
        final JOptionPane panouOptiuni = new JOptionPane(inputs, JOptionPane.WARNING_MESSAGE,
                                                         JOptionPane.YES_NO_OPTION,
                                                         null,
                                                         optiuni,
                                                         optiuni[0]);
        final JDialog dialog = new JDialog(DLMAppStart.appStart, "Confirmare:", true);
        dialog.setContentPane(panouOptiuni);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                parola.requestFocusInWindow();
            }
        });
        panouOptiuni.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
                String proprietate = ev.getPropertyName();
                if (dialog.isVisible() && ev.getSource() == panouOptiuni
                    && proprietate.equals(JOptionPane.VALUE_PROPERTY)
                    || proprietate.equals(JOptionPane.INPUT_VALUE_PROPERTY)){
                    panouOptiuni.setInputValue(JOptionPane.YES_NO_OPTION);
                    Object valoare = panouOptiuni.getValue();
                    if (valoare == JOptionPane.UNINITIALIZED_VALUE) {
                        return;
                    }
                    panouOptiuni.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    if ("Sterge".equals(valoare)) {
                        UsersDAO userManagement = new UsersDAO(db.getConnection());
                        Map<String, String> listaUti = new HashMap<>();
                        try {
                             listaUti = userManagement.getAll();
                        } catch (SQLException ex) {
                             java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        String getParola = listaUti.get("Admin");
                        if (!parola.getText().isEmpty() && parola.getText().equals(getParola)) {
                            try {  
                                SIL stergeSIL = SILmag.getSilDupaNume(jFileChooserVizCom.getSelectedFile().getName().toString());
                                if(jFileChooserVizCom.getSelectedFile().getAbsolutePath().toString().startsWith(siluriNoi)
                                   && jFileChooserVizCom.getSelectedFile().isDirectory()) {
                                    try {
                                        fisierManagement.deleteBySil(stergeSIL.getId());
                                        raportMag.deleteBySil(stergeSIL.getId());
                                        SILmag.stergeDupaNume(stergeSIL.getNume());
                                        deleteMultipleFiles(jFileChooserVizCom.getSelectedFile());
                                        jFileChooserVizCom.rescanCurrentDirectory();
                                        jLabelAvertismenteVizCom.setText("<html><center>Comanda " + stergeSIL.getNume() + " a fost stearsa!</center><html/>");
                                    } catch(NullPointerException ex) {
                                        jLabelAvertismenteVizCom.setText("<html><center>"+jFileChooserVizCom.getSelectedFile().getName().toString()+"Trebuie sa alegi un fisier!</center></html>");
                                    } catch (SQLException ex) {
                                        java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                 } else {
                                    jLabelAvertismenteVizCom.setText("<html><center>Comenzile pot fi sterse doar<br> din locatia la care ai access!</center></html>");
                                 }
                            } catch(NullPointerException ex) {
                                jLabelAvertismenteVizCom.setText("<html><center>Trebuie sa alegi o comanda!</center></html>");
                            } catch (SQLException ex) {
                                java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                            dialog.dispose();
                        } else {
                            Font normalFont = new Font("DialogInput", Font.BOLD, 18);
                            UIManager.put("OptionPane.messageForeground", Color.red);
                            UIManager.put("OptionPane.font", normalFont);
                            JOptionPane.showMessageDialog(DLMAppStart.appStart,
                                                          "<html><center>Parola incorecta!<br>" +
                                                          "Incercati din nou va rog!</center></html>",
                                                          "Eroare:", JOptionPane.ERROR_MESSAGE);
                            parola.requestFocusInWindow();
                        }    
                    } else {
                        dialog.dispose();    
                    }
                }
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(DLMAppStart.appStart);
        dialog.setVisible(true);
    }//GEN-LAST:event_jBtnStergeComActionPerformed

    private void jBtnModificaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnModificaComActionPerformed
        // TODO add your handling code here:
        JPasswordField parola = new JPasswordField();
        parola.setFont(new Font("DialogInput", Font.BOLD, 18));
        JCheckBox bifa = new JCheckBox();
        bifa.setFont(new Font("DialogInput", Font.BOLD, 18));
        bifa.setText("Afiseaza parola");
        ActionListener actionListener = new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            if (parola.echoCharIsSet()) {
                parola.setEchoChar((char) 0);
            } else {
                parola.setEchoChar('•');
            }
          }
        };
        bifa.addActionListener(actionListener);
        Object [] inputs = {"Numai administratorul poate modifica o comanda!\n" +
                            "Va rog sa introduceti parola de administrator:", parola, bifa};
        Object [] optiuni = {"Acceseaza", "Anuleaza"};
        UIManager.put("OptionPane.messageForeground", Color.RED);
        UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
        UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
        final JOptionPane panouOptiuni = new JOptionPane(inputs, JOptionPane.WARNING_MESSAGE,
                                                         JOptionPane.YES_NO_OPTION,
                                                         null,
                                                         optiuni,
                                                         optiuni[0]);
        final JDialog dialog = new JDialog(DLMAppStart.appStart, 
                                           "Confirmare:",
                                           true);
        dialog.setContentPane(panouOptiuni);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                parola.requestFocusInWindow();
            }
        });
        panouOptiuni.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
                String proprietate = ev.getPropertyName();
                if (dialog.isVisible() && ev.getSource() == panouOptiuni
                    && proprietate.equals(JOptionPane.VALUE_PROPERTY)
                    || proprietate.equals(JOptionPane.INPUT_VALUE_PROPERTY)){
                    panouOptiuni.setInputValue(JOptionPane.YES_NO_OPTION);
                    Object valoare = panouOptiuni.getValue();
                    if (valoare == JOptionPane.UNINITIALIZED_VALUE) {
                        return;
                    }
                    panouOptiuni.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    if ("Acceseaza".equals(valoare)) {
                        UsersDAO userManagement = new UsersDAO(db.getConnection());
                        Map<String, String> listaUti = new HashMap<>();
                        try {
                             listaUti = userManagement.getAll();
                        } catch (SQLException ex) {
                             java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        String getParola = listaUti.get("Admin");
                        if (!parola.getText().isEmpty() && parola.getText().equals(getParola)) {
                            jTabbedPaneMeniu.addTab("Modificare comanda", jPanelModCom);
                            ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
                            jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelModCom), inchide);
                            dialog.dispose();
                        } else {
                            Font normalFont = new Font("DialogInput", Font.BOLD, 18);
                            UIManager.put("OptionPane.messageForeground", Color.red);
                            UIManager.put("OptionPane.font", normalFont);
                            JOptionPane.showMessageDialog(DLMAppStart.appStart,
                                                          "<html><center>Parola incorecta!<br>" +
                                                          "Incercati din nou va rog!</center></html>",
                                                          "Eroare:", JOptionPane.ERROR_MESSAGE);
                            parola.requestFocusInWindow();
                        }    
                    } else {
                        dialog.dispose();    
                    }
                }
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(DLMAppStart.appStart);
        dialog.setVisible(true);
    }//GEN-LAST:event_jBtnModificaComActionPerformed

    private void jBtnInchideTabModComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInchideTabModComActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.remove(jPanelModCom);
        listaFisiereModCom.clear();
        listaFisiereMapa.clear();
        dosarModCom.clear();
        jPanelModCom.resetKeyboardActions();
        jPanelModCom.revalidate();
    }//GEN-LAST:event_jBtnInchideTabModComActionPerformed

    private void jBtnSelectModComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSelectModComActionPerformed
        // TODO add your handling code here:
        try {
            if (jFileChooserModCom.getSelectedFile().getAbsolutePath().toString().startsWith(siluriNoi)
                && jFileChooserModCom.getSelectedFile().isDirectory()) {
                selecteazaSIL = SILmag.getSilDupaNume(jFileChooserModCom.getSelectedFile().getName().toString());
                selecteazaRaport = new Raport(selecteazaSIL.getId());
                jTextFieldNumeModCom.setText(selecteazaSIL.getNume());
                jTextFieldTipModCom.setText(selecteazaSIL.getTip());
                jTextAreaDescModCom.setText(selecteazaSIL.getDescriere());
                File locatieMapa = jFileChooserModCom.getSelectedFile();
                File[] arrayFisiere = locatieMapa.listFiles();
                for(File f : arrayFisiere){
                    listaFisiereModCom.add(f.getName());
                    dosarModCom.add(f);
                }
                arrayFisiereModCom = new String[listaFisiereModCom.size()];
                for(int i = 0; i < listaFisiereModCom.size(); i++){
                    arrayFisiereModCom[i] = listaFisiereModCom.get(i);
                }
                Arrays.sort(arrayFisiereModCom);
                jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = arrayFisiereModCom;
                    public int getSize() { return strings.length; }
                    public String getElementAt(int i) { return strings[i]; }
                });
                jFileChooserModCom.setCurrentDirectory(locatieMapa);
            } else {
                jLabelAvertismenteModCom.setText("<html><center>Comenzile se pot modifica doar<br> din locatia la care ai access!</center></html>");
             }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(NullPointerException ex) {
            jLabelAvertismenteModCom.setText("<html><center>Trebuie sa selectezi o comanda!</center></html>");
        }
    }//GEN-LAST:event_jBtnSelectModComActionPerformed

    private void jBtnGolesteSelectieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGolesteSelectieActionPerformed
        // TODO add your handling code here:
        try {
            selecteazaSIL = null;
            modificaSIL = null;
            jTextFieldNumeModCom.setText(null);
            jTextFieldTipModCom.setText(null);
            jTextAreaDescModCom.setText(null);
            listaFisiereModCom.clear();
            listaFisiereMapa.clear();
            dosarModCom.clear();
            jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = {};
                public int getSize() { return strings.length; }
                public String getElementAt(int i) { return strings[i]; }
            });
            jFileChooserModCom.setCurrentDirectory(new File(siluriNoi));
            jPanelModCom.resetKeyboardActions();
            jPanelModCom.revalidate();
        }  catch (NullPointerException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnGolesteSelectieActionPerformed

    private void jBtnModificareComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnModificareComActionPerformed
        // TODO add your handling code here:
        try {
            if(jFileChooserModCom.getCurrentDirectory().getName().toString().equals(selecteazaSIL.getNume().toString())) {
                fisierManagement.deleteBySil(selecteazaSIL.getId());
                modificaSIL = SILmag.getSilDupaNume(jFileChooserModCom.getCurrentDirectory().getName().toString());
                modificaSIL.setNume(jTextFieldNumeModCom.getText());
                modificaSIL.setTip(jTextFieldTipModCom.getText());
                modificaSIL.setDescriere(jTextAreaDescModCom.getText());
                SILmag.update(modificaSIL);
                jFileChooserModCom.getCurrentDirectory().renameTo(new File(jFileChooserModCom.getCurrentDirectory().getParent() +
                                                                           "\\" + jTextFieldNumeModCom.getText())); 
                
                modificaRaport = new Raport(jTextFieldNumeModCom.getText(), jTextFieldTipModCom.getText(), jTextAreaDescModCom.getText(), modificaSIL.getId());
                raportMag.updateBySIL(modificaRaport);
                scoateSQL_fkConstr();
                for(File fisiere : dosarModCom) {
                    fisierManagement.save(new Fisier(siluriNoi + jTextFieldNumeModCom.getText() +
                                                     "\\" + fisiere.getName().toString(), modificaSIL.getId(),
                                                     modificaRaport.getId()));
                    fisierManagement.updateRaportLink(modificaRaport);
                    //reunire_FK();
                }
                reinitiereSQL_fkConstr();
                jLabelAvertismenteModCom.setText("<html><center>Comanda " + modificaSIL.getNume() + " <br>a fost modificata cu succes!</center><html/>");
                jFileChooserVizCom.rescanCurrentDirectory();
            } else {
                jLabelAvertismenteModCom.setText("<html><center>Doar comanda selectata<br> poate fi modificata!</center></html>");
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(NullPointerException ex) {
            jLabelAvertismenteModCom.setText("<html><center>Nu ai selectat nici o comanda!</center></html>");
        }
    }//GEN-LAST:event_jBtnModificareComActionPerformed

    private void jBtnExpComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExpComActionPerformed
        // TODO add your handling code here:
        try {
            if (jFileChooserVizCom.getSelectedFile().getAbsolutePath().toString().startsWith(siluriNoi)
                && jFileChooserVizCom.getSelectedFile().isDirectory()) {
                try {
                    SIL selectatSIL = SILmag.getSilDupaNume(jFileChooserVizCom.getSelectedFile().getName().toString());
                    List<Fisier> fisiere = new ArrayList<>();
                    fisiere = fisierManagement.getAllBySil(selectatSIL.getId());
                    List<File> files = new ArrayList<>();
                    List<String> adaugareSlash = new ArrayList<>();
                    for(Fisier fisier : fisiere) {
                        adaugareSlash.add(fisier.getLocatie());
                        }
                    List<String> listaSortata = modificareLocatie(adaugareSlash);
                    for(String path : listaSortata) {
                        files.add(new File(path));
                        }
                    PDFMergerUtility PDFmerger = new PDFMergerUtility();
                    PDFmerger.setDestinationFileName("C:\\Users\\Dragos\\Desktop\\" + selectatSIL.getNume() + ".pdf");
                        try {
                            for(File file : files){   
                                PDFmerger.addSource(file);
                            }
                            PDFmerger.mergeDocuments();
                            jLabelAvertismenteVizCom.setText("<html><center>Comanda " + selectatSIL.getNume() + " a fost exportata!</center></html>");
                        } catch (FileNotFoundException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                }  catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                jLabelAvertismenteVizCom.setText("<html><center>Comenzile set pot exporta doar<br> din locatia la care ai access!</center></html>");
             }
        } catch(NullPointerException ex) {
            jLabelAvertismenteVizCom.setText("<html><center>Trebuie sa selectezi o comanda!</center></html>");
        }
    }//GEN-LAST:event_jBtnExpComActionPerformed

    private void jBtnIncarcaMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncarcaMapaActionPerformed
        // TODO add your handling code here:
        try {
            listaFisiereMapa.clear();
            if (jFileChooserModCom.getSelectedFile().isDirectory()) {
                File mapa = jFileChooserModCom.getSelectedFile();
                File[] arrayFisiere = mapa.listFiles();
                for(File f : arrayFisiere){
                    listaFisiereMapa.add(f.getName());
                }
                arrayFisiereMapa = new String[listaFisiereMapa.size()];
                for(int i = 0; i < listaFisiereMapa.size(); i++){
                    arrayFisiereMapa[i] = listaFisiereMapa.get(i);
                }
                Arrays.sort(arrayFisiereMapa);
                jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = arrayFisiereMapa;
                    public int getSize() { return strings.length; }
                    public String getElementAt(int i) { return strings[i]; }
                });
                jFileChooserModCom.setCurrentDirectory(mapa);
            }
        } catch(NullPointerException ex) {
            jLabelAvertismenteModCom.setText("<html><center>Trebuie sa si alegi o mapa daca<br> doresti sa-i vizualizezi continutul!</center><html/>");
        }
    }//GEN-LAST:event_jBtnIncarcaMapaActionPerformed

    private void jBtnCopiazaModComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCopiazaModComActionPerformed
        // TODO add your handling code here:
        try {
            if (jFileChooserAccesFisiere.getSelectedFile().exists() &&
                jFileChooserModCom.getCurrentDirectory().toString().equals(SILmag.getSilDupaNume(
                jFileChooserModCom.getCurrentDirectory().getName().toString()))) {
                listaFisiereModCom.clear();
                dosarModCom.clear();
                try {
                    Files.copy(Paths.get(jFileChooserAccesFisiere.getSelectedFile()
                                                                 .getAbsolutePath().toString()),
                               Paths.get(jFileChooserModCom.getCurrentDirectory()
                                                           .getAbsolutePath().toString()
                                         + "\\" + jFileChooserAccesFisiere.getSelectedFile()
                                                                          .getName().toString()),
                               StandardCopyOption.REPLACE_EXISTING);
                    Path destinatieFisier = Paths.get(jFileChooserModCom.getCurrentDirectory().getAbsolutePath()
                                                      .toString() + "\\" + jFileChooserAccesFisiere.getSelectedFile()
                                                      .getName().toString());
                    assert(destinatieFisier.toFile().exists());
                    assert(Files.readAllLines(jFileChooserAccesFisiere.getSelectedFile().getAbsoluteFile().toPath()
                                              .getFileName().getRoot()).equals(Files.readAllLines(destinatieFisier)));
                    
                    jFileChooserModCom.rescanCurrentDirectory();
                    File mapaSelectata = jFileChooserModCom.getCurrentDirectory();
                    File[] arrayFisiere = mapaSelectata.listFiles();
                    for(File f : arrayFisiere){
                        listaFisiereModCom.add(f.getName());
                        dosarModCom.add(f);
                    }
                    arrayFisiereModCom = new String[listaFisiereModCom.size()];
                    for(int i = 0; i < listaFisiereModCom.size(); i++){
                        arrayFisiereModCom[i] = listaFisiereModCom.get(i);
                    }
                    Arrays.sort(arrayFisiereModCom);
                    jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = arrayFisiereModCom;
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jListModCom.repaint();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else if(jFileChooserAccesFisiere.getSelectedFile().exists() &&
                !jFileChooserModCom.getCurrentDirectory().equals(
                SILmag.getSilDupaNume(jFileChooserModCom.getSelectedFile().getName().toString()))) {
                listaFisiereMapa.clear();
                try {
                    Files.copy(Paths.get(jFileChooserAccesFisiere.getSelectedFile()
                                                                 .getAbsolutePath()
                                                                 .toString()),
                               Paths.get(jFileChooserModCom.getCurrentDirectory()
                                                                .getAbsolutePath()
                                                                .toString() +
                                         "\\" + jFileChooserAccesFisiere.getSelectedFile()
                                                                        .getName()
                                                                        .toString()),
                               StandardCopyOption.REPLACE_EXISTING);
                    Path destinatieFisier = Paths.get(jFileChooserModCom.getCurrentDirectory().getAbsolutePath()
                                                      .toString() + "\\" + jFileChooserAccesFisiere.getSelectedFile()
                                                      .getName().toString());
                    assert(destinatieFisier.toFile().exists());
                    assert(Files.readAllLines(jFileChooserAccesFisiere.getSelectedFile().getAbsoluteFile().toPath()
                                              .getFileName().getRoot()).equals(Files.readAllLines(destinatieFisier)));
                    File mapaSelectata = jFileChooserModCom.getCurrentDirectory();
                    File[] arrayFisiere = mapaSelectata.listFiles();
                    for(File f : arrayFisiere){
                        listaFisiereMapa.add(f.getName());
                    }
                    arrayFisiereMapa = new String[listaFisiereMapa.size()];
                    for(int i = 0; i < listaFisiereMapa.size(); i++){
                        arrayFisiereMapa[i] = listaFisiereMapa.get(i);
                    }
                    Arrays.sort(arrayFisiereMapa);
                    jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = arrayFisiereMapa;
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jListModCom.repaint();
                    jFileChooserModCom.rescanCurrentDirectory();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(NullPointerException ex) {
            jLabelAvertismenteModCom.setText("<html><center>Nu ai ales<br> nici un fisier!</center><html/>");
        }
    }//GEN-LAST:event_jBtnCopiazaModComActionPerformed

    private void jBtnScoateModComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnScoateModComActionPerformed
        // TODO add your handling code here:
        try {
            if (jFileChooserModCom.getSelectedFile().isFile() || jFileChooserModCom.getSelectedFile().isDirectory()) {
                Object[] selectie = {"Confirma", "Anuleaza"};
                UIManager.put("OptionPane.messageForeground", Color.RED);
                UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
                UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
                int optiune = JOptionPane.showOptionDialog(DLMAppStart.appStart,
                    "<html><center>Esti pe cale de a sterge un fisier pe care,<br>" +
                    "s-ar putea sa-l nu mai poti recupera inapoi!<br>" +
                    "Esti absolut sigur ca doresti sa continui?</center></html>",
                    "Confirmare:",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,     //do not use a custom Icon
                    selectie,  //the titles of buttons
                    selectie[0]); //default button title
                try {
                    if (optiune == JOptionPane.YES_OPTION &&
                        jFileChooserModCom.getCurrentDirectory().toString()
                                          .equals(SILmag.getSilDupaNume(jFileChooserModCom.getCurrentDirectory()
                                                                                          .getName()
                                                                                          .toString()))) {
                        listaFisiereModCom.clear();
                        dosarModCom.clear();
                        try {
                            boolean success = Files.deleteIfExists(Paths.get(jFileChooserModCom.getSelectedFile().toString()));
                            assert(success);
                            jFileChooserModCom.rescanCurrentDirectory();
                            File mapaSelectata = jFileChooserModCom.getCurrentDirectory();
                            File[] arrayFisiere = mapaSelectata.listFiles();
                            for(File f : arrayFisiere){
                                listaFisiereModCom.add(f.getName());
                                dosarModCom.add(f);
                            }
                            arrayFisiereModCom = new String[listaFisiereModCom.size()];
                            for (int i = 0; i < listaFisiereModCom.size(); i++) {
                                arrayFisiereModCom[i] = listaFisiereModCom.get(i);
                            }
                            Arrays.sort(arrayFisiereModCom);
                            jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = arrayFisiereModCom;
                                public int getSize() {return strings.length;}
                                public String getElementAt(int i) {return strings[i];}
                            });
                            jListModCom.repaint();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    } else if(optiune == JOptionPane.YES_OPTION &&
                              !jFileChooserModCom.getCurrentDirectory().toString()
                                                 .equals(SILmag.getSilDupaNume(jFileChooserModCom
                                                                               .getCurrentDirectory()
                                                                               .getName()
                                                                               .toString()))) {
                        listaFisiereMapa.clear();
                        try {
                            boolean success = Files.deleteIfExists(Paths.get(jFileChooserModCom.getSelectedFile().toString()));
                            assert(success);
                            jFileChooserModCom.rescanCurrentDirectory();
                            File mapaSelectata = jFileChooserModCom.getCurrentDirectory();
                            File[] arrayFisiere = mapaSelectata.listFiles();
                            for(File f : arrayFisiere){
                                listaFisiereMapa.add(f.getName());
                            }
                            arrayFisiereMapa = new String[listaFisiereMapa.size()];
                            for (int i = 0; i < listaFisiereMapa.size(); i++) {
                                arrayFisiereMapa[i] = listaFisiereMapa.get(i);
                            }
                            Arrays.sort(arrayFisiereMapa);
                            jListModCom.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = arrayFisiereMapa;
                                public int getSize() {return strings.length;}
                                public String getElementAt(int i) {return strings[i];}
                            });
                            jListModCom.repaint();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        } catch(NullPointerException ex) {
            jLabelAvertismenteModCom.setText("<html><center>Ca sa poti sterge ceva,<br> mai intai trebuie sa-l si selectezi!</center><html/>");
        }
    }//GEN-LAST:event_jBtnScoateModComActionPerformed

    private void jBtnNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNextStepActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.addTab("Verificare metrologica", jPanelVerMet);
        ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
        jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelVerMet), inchide);
    }//GEN-LAST:event_jBtnNextStepActionPerformed

    private void jBtnInchideTabVerMetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInchideTabVerMetActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.remove(jPanelVerMet);
    }//GEN-LAST:event_jBtnInchideTabVerMetActionPerformed

    private void jBtnSalvareVerMetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalvareVerMetActionPerformed
        // TODO add your handling code here:
        /*PDDocument document = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.A4);
        document.addPage(pagina);
        try {
            PDFontLike font = PDType0Font.load(document, new File("C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\" +
                                                                  "mywork\\DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\" +
                                                                  "FisiereAplicatie\\arial.ttf"));
            PDPageContentStream contents = new PDPageContentStream(document, pagina);
            contents.beginText();
            contents.setLeading(14.5f);
            contents.setFont((PDFont) font, 16);
            contents.newLineAtOffset(50, 725);
            contents.showText("S.C. Necom S.R.L");
            contents.newLineAtOffset(0, -20);
            contents.showText("Laborator Metrologie");
            contents.newLineAtOffset(150, -60);
            contents.showText("Comanda verificare metrologica");
            contents.newLineAtOffset(12, -25);
            contents.showText("Nr: "+jTextFieldNrVerMet.getText() + "   Data: " + jTextFieldDataVerMet.getText());
            contents.newLineAtOffset(-60, -20);
            contents.endText();
            BaseTable table = new BaseTable(-60, pagina.getMediaBox().getHeight(), 70, pagina.getMediaBox().getWidth() - (2 * 50), 50,
                                            document, pagina, true, true);
            Row<PDPage> row = table.createRow(20);
            Cell<PDPage> cell;
            cell = row.createCell(50, "Denumire solicitant");
            cell.setAlign(HorizontalAlignment.LEFT);
            cell.setFontSize(15);
            cell = row.createCell(50, jTextFieldNumeSolicitant.getText());
            cell.setFontSize(15);
            cell.setFont(PDType0Font.load(document, new File("C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\" +
                                                             "mywork\\DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\" +
                                                             "FisiereAplicatie\\arial.ttf")));

            row = table.createRow(20);
            cell = row.createCell(50, "Date identificare");
            cell.setTextColor(Color.BLACK);
            cell.setFontSize(15);
            cell.setFont(PDType0Font.load(document, new File("C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\" +
                                                             "mywork\\DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\" +
                                                             "FisiereAplicatie\\arial.ttf")));
            cell.setAlign(HorizontalAlignment.LEFT);
            cell = row.createCell(50, "Adresa: " + jTextFieldAdresa.getText() + "<br>Cod fiscal: " +
                                  jTextFieldCodFiscal.getText() + "<br>Cod IBAN: " + jTextFieldIBAN.getText());
            cell.setTextColor(Color.BLACK);
            cell.setFontSize(15);
            cell.setFont(PDType0Font.load(document, new File("C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\" +
                                                             "mywork\\DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\" +
                                                             "FisiereAplicatie\\arial.ttf")));
            cell.setAlign(HorizontalAlignment.LEFT);

            row = table.createRow(20);
            cell = row.createCell(50, "Persoana contact / Telefon");
            cell.setFontSize(15);
            cell.setAlign(HorizontalAlignment.LEFT);
            cell = row.createCell(50, jTextFieldPersoanaContact.getText());
            cell.setFontSize(15);
            table.draw();
            contents.close();
            document.save("C:\\Users\\Dragos\\Desktop\\Comanda de verificare metrologica.pdf");
            jLabelAverstismenteVerMet.setText("<html><center>Datele au fost salvate cu succes!</center></html>");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                document.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }*/
        PdfDocument doc = new PdfDocument();
        //Set margins
        PdfMargins margin = new PdfMargins(60, 60, 40, 40);
        //Call the method addHeaderAndFooter() to add header and footer
        addHeaderAndFooter(doc, PdfPageSize.A4, margin);
        //Add two pages to the PDF document and draw string to it.
        PdfPageBase page = doc.getPages().add();
        //PdfPageBase page2 = doc.getPages().add();
        PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("Arial", Font.PLAIN, 14));
        String NrSiData = "S.C. Necom S.R.L.\n" +
                          "Laborator Metrologie\n\n" +
                          "                          Comanda verificare metrologica\n" +
                          "                                 Nr: " + jTextFieldNrVerMet.getText()+ " Data: " +
                          jTextFieldDataVerMet.getText();
        page.getCanvas().drawString(NrSiData, font, PdfBrushes.getBlack(), 0, 0);
        //Creearea primului tabel
        //Set margins
        PdfUnitConvertor unitCvtr = new PdfUnitConvertor();
        margin.setTop(unitCvtr.convertUnits(2.54f, PdfGraphicsUnit.Centimeter, PdfGraphicsUnit.Point));
        margin.setBottom(margin.getTop());
        margin.setLeft(unitCvtr.convertUnits(3.17f, PdfGraphicsUnit.Centimeter, PdfGraphicsUnit.Point));
        margin.setRight(margin.getLeft());
        //Add one page
        //page = doc.getPages().add(PdfPageSize.A4, margin);
        float y = 10;
        //Draw title
        PdfTrueTypeFont fontTabel = new PdfTrueTypeFont(new Font("Arial", Font.BOLD ,16));
        PdfStringFormat formatTabel = new PdfStringFormat(PdfTextAlignment.Center);
        //page.getCanvas().drawString("\n\n\n\n\n"+"Country List", font1, brush1, page.getCanvas().getClientSize().getWidth() / 2, y, format1);
        //y = y + (float) font1.measureString("Country List", format1).getHeight();
        y = y + 5;
        y = y + (float) fontTabel.measureString(NrSiData, formatTabel).getHeight();
        y = y + 5;
        //Data source to create table
        String[] data = {"Denumire solicitant;" + jTextFieldNumeSolicitant.getText(), "Date identificare;" + "Adresa: " +
                         jTextFieldAdresa.getText() + "\nCod fiscal: " + jTextFieldCodFiscal.getText() + "\nCod IBAN: " +
                         jTextFieldIBAN.getText(), "Persoana contact / Telefon;" + jTextFieldPersoanaContact.getText()};
        String[][] dataSource = new String[data.length][];
        for (int i = 0; i < data.length; i++) {
            dataSource[i] = data[i].split("[;]", -1);
        }
        //Create a PdfTable instance and set data source
        PdfTable table = new PdfTable();
        table.getStyle().setCellPadding(2);
        table.getStyle().setHeaderSource(PdfHeaderSource.Rows);
        table.getStyle().setHeaderRowCount(1);
        table.getStyle().setShowHeader(true);
        table.setDataSource(dataSource);
        //Draw table to the page
        PdfLayoutResult result = table.draw(page, new Point2D.Float(0, y));
        y = y + (float) result.getBounds().getHeight() + 5;
        //Creearea tabelului 2
        String[] data2 = {"Denumirea / Numarul O.V.;Tip / caracteristici tehnice (valori primar /" +
                          "secundar, putere secundar, clasa precizie);Producator;Serii / an fabricatie;" +
                          "Nr. aprobare model","Curent medie tensiune \nNr. buc: " + jTextFieldBucTensiuneMedie.getText() +";" +
                          convertToEmpty((String) jTableVerMet.getValueAt(0, 0)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(0, 1)) + ";" +
                          convertToEmpty((String)jTableVerMet.getValueAt(0, 2)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(0, 3)),
                          "Tensiune medie tensiune\nNr. buc: " + jTextFieldBucTensiuneMedieTensiune.getText() +
                          ";" + convertToEmpty((String)jTableVerMet.getValueAt(1, 0)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(1, 1)) + ";" +
                          convertToEmpty((String)jTableVerMet.getValueAt(1, 2)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(1, 3)), "Curent joasa\ntensiune\nNr. buc: "
                          + jTextFieldBucJoasaTensiune.getText() + ";" + convertToEmpty((String)jTableVerMet.getValueAt(2, 0)) + ";" +
                          convertToEmpty((String)jTableVerMet.getValueAt(2, 1)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(2, 2)) + ";" +
                          convertToEmpty((String)jTableVerMet.getValueAt(2, 3)), "Tensiune joasa tensiune\nNr. buc: " +
                          jTextFieldBucTensiuneJoasaTensiune.getText() + ";" + convertToEmpty((String)jTableVerMet.getValueAt(3, 0)) +
                          ";" + convertToEmpty((String)jTableVerMet.getValueAt(3, 1)) + ";" + convertToEmpty((String)jTableVerMet.getValueAt(3, 2)) +
                          ";" + convertToEmpty((String)jTableVerMet.getValueAt(3, 3))};
        String[][] dataSource2 = new String[data2.length][];
        for (int i = 0; i < data2.length; i++) {
            dataSource2[i] = data2[i].split("[;]", -1);
        }
        //Create a PdfTable instance and set data source
        PdfTable table2 = new PdfTable();
        table2.getStyle().setCellPadding(2);
        table2.getStyle().setHeaderSource(PdfHeaderSource.Rows);
        table2.getStyle().setHeaderRowCount(1);
        table2.getStyle().setShowHeader(true);
        table2.setDataSource(dataSource2);
        //Draw table to the page
        PdfLayoutResult result2 = table2.draw(page, new Point2D.Float(0, y));
        y = y + (float) result2.getBounds().getHeight() + 10;
        //Draw string below table
        PdfBrush brush2 = PdfBrushes.getGray();
        PdfTrueTypeFont font2 = new PdfTrueTypeFont(new Font("Arial", 0,9));
        page.getCanvas().drawString("Formular cod FL 48.1" + String.format("%" + 75 + "s", "") +
                                    "Semnatura si stampila solicitant", font2, brush2, 5, y);  // era y cel mai in dreapta
        //Save the document
        String locatieFisier = "C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\mywork\\" +
                               "DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\SILNou\\" + 
                               silGlobal.getNume() + "\\" +"Comanda verificare metrologica.pdf";
        doc.saveToFile(locatieFisier);
                    
        doc.close();
        try {
            fisierManagement.save(new Fisier(locatieFisier, silGlobal.getId(), raportGlobal.getId()));
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        jLabelAverstismenteVerMet.setText("<html><center>Datele au fost salvate cu succes!</center></html>");
    }//GEN-LAST:event_jBtnSalvareVerMetActionPerformed

    private void jButtonTabFisaAnalizaDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTabFisaAnalizaDocActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.addTab("Fisa analiza document", jPanelFisaAnalizaDoc);
        ButonInchideTaburi inchide = new ButonInchideTaburi(jTabbedPaneMeniu);
        jTabbedPaneMeniu.setTabComponentAt(jTabbedPaneMeniu.indexOfComponent(jPanelFisaAnalizaDoc), inchide);
    }//GEN-LAST:event_jButtonTabFisaAnalizaDocActionPerformed

    private void jButtonInchideTabFisaAnalizaDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInchideTabFisaAnalizaDocActionPerformed
        // TODO add your handling code here:
        jTabbedPaneMeniu.remove(jPanelFisaAnalizaDoc);
    }//GEN-LAST:event_jButtonInchideTabFisaAnalizaDocActionPerformed

    private void jBtnFinalizareComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFinalizareComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnFinalizareComActionPerformed

    private void jButtonSalvareFisaAnalizaDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvareFisaAnalizaDocActionPerformed
        // TODO add your handling code here:
        PdfDocument doc = new PdfDocument();
        //Set margins
        PdfMargins margin = new PdfMargins(60, 60, 40, 40);
        //Call the method addHeaderAndFooter() to add header and footer
        addHeaderAndFooter(doc, PdfPageSize.A4, margin);
        //Add two pages to the PDF document and draw string to it.
        PdfPageBase page = doc.getPages().add();
        PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("Arial", Font.PLAIN, 16));
        PdfTrueTypeFont fontBold = new PdfTrueTypeFont(new Font("Arial", Font.BOLD, 18));
        PdfTrueTypeFont fontMic = new PdfTrueTypeFont(new Font("Arial", Font.PLAIN, 14));
        String NrSiData = "S.C. Necom S.R.L.\n" +
                          "Laborator Metrologie";
        page.getCanvas().drawString(NrSiData, font, PdfBrushes.getBlack(), 0, 0);
        page.getCanvas().drawString("Fisa analiza document", fontBold, PdfBrushes.getBlack(), 110, 80);
        String DocSiClient = "Denumire document analizat: " + jTextFieldDenumireDocAnalizat.getText() + "\n" + 
                             "Client: " + jTextFieldClientFisaAnalizaDoc.getText();
        page.getCanvas().drawString(DocSiClient, fontMic, PdfBrushes.getBlack(), 0, 120);
        page.getCanvas().drawString("1. Continutul documentului", fontMic, PdfBrushes.getBlack(), 0, 160);
        PdfPen pen = new PdfPen(new PdfRGBColor(Color.black), 0.1);
        PdfStringFormat leftAlignment = new PdfStringFormat(PdfTextAlignment.Left, PdfVerticalAlignment.Top);
        // Creeare chenar_1
        Rectangle2D.Float rectContinut = new Rectangle2D.Float(0, 180, 410, 80);
        page.getCanvas().drawRectangle(pen, PdfBrushes.getWhite(), rectContinut);
        page.getCanvas().drawString(jTextAreaContinutDocFisaAnaliza.getText().toString(), font, PdfBrushes.getBlack(),
                                    rectContinut, leftAlignment);
        // Creeare chenar_2
        page.getCanvas().drawString("2. Metoda folosita", fontMic, PdfBrushes.getBlack(), 0, 200);
        Rectangle2D.Float rectMetoda = new Rectangle2D.Float(0, 220, 410, 80);
        page.getCanvas().drawRectangle(pen, PdfBrushes.getWhite(), rectContinut);
        page.getCanvas().drawString(jTextAreaMetodaFisaAnaliza.getText().toString(), font, PdfBrushes.getBlack(),
                                    rectMetoda, leftAlignment);
        // Creeare chenar_3
        page.getCanvas().drawString("3. Observatii", fontMic, PdfBrushes.getBlack(), 0, 240);
        Rectangle2D.Float rectObservatii = new Rectangle2D.Float(0, 260, 410, 80);
        page.getCanvas().drawRectangle(pen, PdfBrushes.getWhite(), rectContinut);
        page.getCanvas().drawString(jTextAreaObservatiiFisaAnaliza.getText().toString(), font, PdfBrushes.getBlack(),
                                    rectObservatii, leftAlignment);
        // Creearec chenar_4
        page.getCanvas().drawString("4. Decizie in urma analizei", fontMic, PdfBrushes.getBlack(), 0, 280);
        Rectangle2D.Float rectDecizie = new Rectangle2D.Float(0, 300, 410, 80);
        page.getCanvas().drawRectangle(pen, PdfBrushes.getWhite(), rectContinut);
        page.getCanvas().drawString(jTextAreaDecizieAnaliza.getText().toString(), font, PdfBrushes.getBlack(),
                                    rectDecizie, leftAlignment);
        //Draw string below table
        PdfTrueTypeFont fontFooter = new PdfTrueTypeFont(new Font("Arial",Font.PLAIN, 8));
        page.getCanvas().drawString("Data: " + jTextFieldDataFisaAnalizaDoc.getText() + String.format("%" + 75 + "s", "") +
                                    "Intocmit, " + jTextFieldIntocmitFisaAnalizaDocument.getText(),
                                    fontFooter, PdfBrushes.getBlack(), 0, 600);  // era y cel mai in dreapta
        page.getCanvas().drawString("RT,", fontFooter, PdfBrushes.getBlack(), 150, 610);
        page.getCanvas().drawString("Formular cod FL 12.0", fontFooter, PdfBrushes.getBlack(), 0, 620);
        //Save the document
        /*String locatieFisier="C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\mywork\\mywork\\" +
                             "DigitalizareLaboratorMetrologie\\project\\FisiereDLM\\SILNou\\" + 
                             silGlobal.getNume() + "\\" +"Fisa analiza document.pdf"; */
        doc.saveToFile("C:\\Users\\Dragos\\Desktop\\Fisa analiza document.pdf");   
        doc.close();
        /*try {
            fisierManagement.save(new Fisier(locatieFisier, silGlobal.getId(), raportGlobal.getId()));
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        jLabelAvertismenteFisaAnalizaDoc.setText("<html><center>Datele au fost salvate cu succes!</center></html>");
    }//GEN-LAST:event_jButtonSalvareFisaAnalizaDocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAdaugaFisierComNoua;
    private javax.swing.JButton jBtnCopiazaModCom;
    private javax.swing.JButton jBtnExpCom;
    private javax.swing.JButton jBtnFinalizareCom;
    private javax.swing.JButton jBtnGenComNoua;
    private javax.swing.JButton jBtnGolesteSelectie;
    private javax.swing.JButton jBtnIncarcaMapa;
    private javax.swing.JButton jBtnInchideApp;
    private javax.swing.JButton jBtnInchideMeniuComNoua;
    private javax.swing.JButton jBtnInchideTabModCom;
    private javax.swing.JButton jBtnInchideTabVerMet;
    private javax.swing.JButton jBtnInchideTabVizCom;
    private javax.swing.JButton jBtnModificaCom;
    private javax.swing.JButton jBtnModificareCom;
    private javax.swing.JButton jBtnNextStep;
    private javax.swing.JButton jBtnSalvareVerMet;
    private javax.swing.JButton jBtnScoateFisierGenCom;
    private javax.swing.JButton jBtnScoateModCom;
    private javax.swing.JButton jBtnSelectModCom;
    private javax.swing.JButton jBtnStergeCom;
    private javax.swing.JButton jBtnTabAdmFisiere;
    private javax.swing.JButton jBtnTabAdmUti;
    private javax.swing.JButton jBtnTabFiseLaborator;
    private javax.swing.JButton jBtnTabGenComanda;
    private javax.swing.JButton jBtnTabVizCom;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonInchideTabFisaAnalizaDoc;
    private javax.swing.JButton jButtonInchideTabReceptieOV;
    private javax.swing.JButton jButtonNextReceptieOV;
    private javax.swing.JButton jButtonSalvareFisaAnalizaDoc;
    private javax.swing.JButton jButtonSalvareReceptieOV;
    private javax.swing.JButton jButtonTabFisaAnalizaDoc;
    private javax.swing.JFileChooser jFileChooserAccesFisiere;
    private javax.swing.JFileChooser jFileChooserGenCom;
    private javax.swing.JFileChooser jFileChooserModCom;
    private javax.swing.JFileChooser jFileChooserVizCom;
    private javax.swing.JLabel jLabelAbateri;
    private javax.swing.JLabel jLabelAdresa;
    private javax.swing.JLabel jLabelAfisareComModCom;
    private javax.swing.JLabel jLabelAfisareFisiereModCom;
    private javax.swing.JLabel jLabelAverstismenteVerMet;
    private javax.swing.JLabel jLabelAvertismenteComNoua;
    private javax.swing.JLabel jLabelAvertismenteFisaAnalizaDoc;
    private javax.swing.JLabel jLabelAvertismenteModCom;
    private javax.swing.JLabel jLabelAvertismenteProcesVerbalRec;
    private javax.swing.JLabel jLabelAvertismenteVizCom;
    private javax.swing.JLabel jLabelBucCurentJoasaTensiune;
    private javax.swing.JLabel jLabelBucCurentTensiuneMedie;
    private javax.swing.JLabel jLabelBucTensiuneJoasaTensiune;
    private javax.swing.JLabel jLabelBucTensiuneMedieTensiune;
    private javax.swing.JLabel jLabelCaracProcesVerbalRec_1;
    private javax.swing.JLabel jLabelCaracProcesVerbalRec_2;
    private javax.swing.JLabel jLabelClientFizaAnalizaDoc;
    private javax.swing.JLabel jLabelCodFiscal;
    private javax.swing.JLabel jLabelContinutDocFisaAnaliza;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDataFisaAnalizaDoc;
    private javax.swing.JLabel jLabelDataProcesVerbalRec;
    private javax.swing.JLabel jLabelDeLaProcesVerbalRec;
    private javax.swing.JLabel jLabelDecizieAnaliza;
    private javax.swing.JLabel jLabelDenumireDocAnalizat;
    private javax.swing.JLabel jLabelDenumireOV_1;
    private javax.swing.JLabel jLabelDenumireOV_2;
    private javax.swing.JLabel jLabelDescComNoua;
    private javax.swing.JLabel jLabelDescModCom;
    private javax.swing.JLabel jLabelFisiereGenCom;
    private javax.swing.JLabel jLabelFisiereModCom;
    private javax.swing.JLabel jLabelFunctieProcesVerbalRec_1;
    private javax.swing.JLabel jLabelFunctieProcesVerbalRec_2;
    private javax.swing.JLabel jLabelIBAN;
    private javax.swing.JLabel jLabelIntocmitFisaAnalizaDoc;
    private javax.swing.JLabel jLabelJoasaTensiune;
    private javax.swing.JLabel jLabelLogoMeniuPrincipal;
    private javax.swing.JLabel jLabelMetodaFolositaAnalizaDoc;
    private javax.swing.JLabel jLabelNotaProcesVerbalRec;
    private javax.swing.JLabel jLabelNr;
    private javax.swing.JLabel jLabelNrOV;
    private javax.swing.JLabel jLabelNrProcesVerbalRec;
    private javax.swing.JLabel jLabelNumeComNoua;
    private javax.swing.JLabel jLabelNumeModCom;
    private javax.swing.JLabel jLabelNumePrenumeProcesVerbalRec_1;
    private javax.swing.JLabel jLabelNumePrenumeProcesVerbalRec_2;
    private javax.swing.JLabel jLabelNumeSolicitant;
    private javax.swing.JLabel jLabelObservatiiFisaAnalizaDoc;
    private javax.swing.JLabel jLabelPersoanaContact;
    private javax.swing.JLabel jLabelProducatorProcesVerbalRec_1;
    private javax.swing.JLabel jLabelProducatorProcesVerbalRec_2;
    private javax.swing.JLabel jLabelRezultatConsultare;
    private javax.swing.JLabel jLabelSerieAnFabBuc;
    private javax.swing.JLabel jLabelSerieAnFabBuc_2;
    private javax.swing.JLabel jLabelTensiuneJoasaTensiune;
    private javax.swing.JLabel jLabelTensiuneMedie;
    private javax.swing.JLabel jLabelTensiuneMedieTensiune;
    private javax.swing.JLabel jLabelTipGenCom;
    private javax.swing.JLabel jLabelTipModCom;
    private javax.swing.JLabel jLabelTitluAnalizaDoc;
    private javax.swing.JLabel jLabelTitluComNoua;
    private javax.swing.JLabel jLabelTitluMeniuPrincipal;
    private javax.swing.JLabel jLabelTitluModCom;
    private javax.swing.JLabel jLabelTitluProcesVerbalRec;
    private javax.swing.JLabel jLabelTitluVerMet;
    private javax.swing.JLabel jLabelTitluVizCom;
    private javax.swing.JList<String> jListComNoua;
    private javax.swing.JList<String> jListModCom;
    private javax.swing.JPanel jPanelComandaNoua;
    private javax.swing.JPanel jPanelFisaAnalizaDoc;
    private javax.swing.JPanel jPanelMeniuPrincipal;
    private javax.swing.JPanel jPanelModCom;
    private javax.swing.JPanel jPanelProcesVerbalReceptieOV;
    private javax.swing.JPanel jPanelVerMet;
    private javax.swing.JPanel jPanelVizCom;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPaneMeniu;
    private javax.swing.JTable jTableVerMet;
    private javax.swing.JTextArea jTextAreaContinutDocFisaAnaliza;
    private javax.swing.JTextArea jTextAreaDecizieAnaliza;
    private javax.swing.JTextArea jTextAreaDescComNoua;
    private javax.swing.JTextArea jTextAreaDescModCom;
    private javax.swing.JTextArea jTextAreaMetodaFisaAnaliza;
    private javax.swing.JTextArea jTextAreaObservatiiFisaAnaliza;
    private javax.swing.JTextArea jTextAreaSerieAnFabBuc;
    private javax.swing.JTextArea jTextAreaSerieAnFabBuc_2;
    private javax.swing.JTextField jTextFieldAbateri;
    private javax.swing.JTextField jTextFieldAdresa;
    private javax.swing.JTextField jTextFieldBucJoasaTensiune;
    private javax.swing.JTextField jTextFieldBucTensiuneJoasaTensiune;
    private javax.swing.JTextField jTextFieldBucTensiuneMedie;
    private javax.swing.JTextField jTextFieldBucTensiuneMedieTensiune;
    private javax.swing.JTextField jTextFieldCaracProcesVerbalRec_1;
    private javax.swing.JTextField jTextFieldCaracProcesVerbalRec_2;
    private javax.swing.JTextField jTextFieldClientFisaAnalizaDoc;
    private javax.swing.JTextField jTextFieldCodFiscal;
    private javax.swing.JTextField jTextFieldDataFisaAnalizaDoc;
    private javax.swing.JTextField jTextFieldDataProcesVerbalRec;
    private javax.swing.JTextField jTextFieldDataVerMet;
    private javax.swing.JTextField jTextFieldDeLaProcesVerbalRec;
    private javax.swing.JTextField jTextFieldDenumireDocAnalizat;
    private javax.swing.JTextField jTextFieldDenumireOV_1;
    private javax.swing.JTextField jTextFieldDenumireOV_2;
    private javax.swing.JTextField jTextFieldFunctieProcesVerbalrec_1;
    private javax.swing.JTextField jTextFieldFunctieProcesVerbalrec_2;
    private javax.swing.JTextField jTextFieldIBAN;
    private javax.swing.JTextField jTextFieldIntocmitFisaAnalizaDocument;
    private javax.swing.JTextField jTextFieldNotaProcesVerbalRec;
    private javax.swing.JTextField jTextFieldNrProcesVerbalRec;
    private javax.swing.JTextField jTextFieldNrVerMet;
    private javax.swing.JTextField jTextFieldNumeComNoua;
    private javax.swing.JTextField jTextFieldNumeModCom;
    private javax.swing.JTextField jTextFieldNumePrenumeProcesVerbalRec_1;
    private javax.swing.JTextField jTextFieldNumePrenumeProcesVerbalRec_2;
    private javax.swing.JTextField jTextFieldNumeSolicitant;
    private javax.swing.JTextField jTextFieldPersoanaContact;
    private javax.swing.JTextField jTextFieldProducatorProcesVerbalRec_1;
    private javax.swing.JTextField jTextFieldProducatorProcesVerbalRec_2;
    private javax.swing.JTextField jTextFieldRezultatConsultare;
    private javax.swing.JTextField jTextFieldTipGenCom;
    private javax.swing.JTextField jTextFieldTipModCom;
    // End of variables declaration//GEN-END:variables

    private void scoateTaburi() {
        jTabbedPaneMeniu.remove(jPanelVizCom);
        jTabbedPaneMeniu.remove(jPanelComandaNoua); 
        jTabbedPaneMeniu.remove(jPanelModCom);
        jTabbedPaneMeniu.remove(jPanelVerMet);
        jTabbedPaneMeniu.remove(jPanelFisaAnalizaDoc);
        jTabbedPaneMeniu.remove(jPanelProcesVerbalReceptieOV);
    }
    
    private void incarcaLogo() {
        ImageIcon logoImage = new ImageIcon(
                              new ImageIcon("C:\\Oracle\\Middleware\\Oracle_Home\\jdeveloper\\" +
                                            "mywork\\mywork\\DigitalizareLaboratorMetrologie\\" +
                                            "project\\FisiereDLM\\FisiereAplicatie\\Dsr.png")
                              .getImage().getScaledInstance(720, 648, Image.SCALE_SMOOTH));
        jLabelLogoMeniuPrincipal.setIcon(logoImage);    
    }
    
    private void selectoareMapeSiFisiere() {
        jFileChooserGenCom.setControlButtonsAreShown(false);
        jFileChooserVizCom.setCurrentDirectory(new File(siluriNoi));
        jFileChooserVizCom.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooserVizCom.setControlButtonsAreShown(false);
        jFileChooserVizCom.rescanCurrentDirectory();
        jFileChooserModCom.setCurrentDirectory(new File(siluriNoi));
        jFileChooserModCom.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooserModCom.setControlButtonsAreShown(false);
        jFileChooserModCom.rescanCurrentDirectory();
        jFileChooserAccesFisiere.setCurrentDirectory(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooserAccesFisiere.setControlButtonsAreShown(false);
    }
    
    private void deleteMultipleFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteMultipleFiles(files[i]);
            }
            file.delete();
        } else {
            file.delete();
        }
    }
    
    private List<String> modificareLocatie(List<String> lista){
        List<String> listaSortata = new ArrayList<>();
        String modificare = null;
        for(String item : lista){ 
            for(int i = 0 ;i < item.length(); i++){
                if(item.charAt(i) == '\\') modificare = item.substring(0, i) + "\\" + item.substring(i, item.length());
                }
            listaSortata.add(modificare);
            }
        return listaSortata;
    }
    
    static void addHeaderAndFooter(PdfDocument doc, Dimension2D pageSize, PdfMargins margin) {
          PdfPageTemplateElement header = new PdfPageTemplateElement(margin.getLeft(), pageSize.getHeight());
          doc.getTemplate().setLeft(header);
          PdfPageTemplateElement topSpace = new PdfPageTemplateElement(pageSize.getWidth(), margin.getTop());
          topSpace.setForeground(true);
          doc.getTemplate().setTop(topSpace);
          //Draw header label
          PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("Arial",Font.PLAIN,12));
          PdfStringFormat format = new PdfStringFormat(PdfTextAlignment.Left);
          String label = "E-iceblue Co.,Ltd";
          Dimension2D dimension2D = new Dimension();
          dimension2D.setSize(font.measureString(label, format));
          float y = topSpace.getHeight() - font.getHeight() - 1;
          PdfPen pen = new PdfPen(new PdfRGBColor(Color.black), 0.75f);
          topSpace.getGraphics().setTransparency(0.5f);
          topSpace.getGraphics().drawLine(pen, margin.getLeft(), y, pageSize.getWidth() - margin.getRight(), y);
          y = y - 1 - (float) dimension2D.getHeight();
          topSpace.getGraphics().drawString(label, font, PdfBrushes.getBlack(), margin.getLeft(), y, format);
          PdfPageTemplateElement rightSpace = new PdfPageTemplateElement(margin.getRight(), pageSize.getHeight());
          doc.getTemplate().setRight(rightSpace);
          //Draw dynamic fields as footer
          PdfPageTemplateElement footer = new PdfPageTemplateElement(pageSize.getWidth(), margin.getBottom());
          footer.setForeground(true);
          doc.getTemplate().setBottom(footer);
          y = font.getHeight() + 1;
          footer.getGraphics().setTransparency(0.5f);
          footer.getGraphics().drawLine(pen, margin.getLeft(), y, pageSize.getWidth() - margin.getRight(), y);
          y = y + 1;
          PdfPageNumberField pageNumber = new PdfPageNumberField();
          PdfPageCountField pageCount = new PdfPageCountField();
          PdfCompositeField pageNumberLabel = new PdfCompositeField();
          pageNumberLabel.setAutomaticFields(new PdfAutomaticField[]{pageNumber, pageCount});
          pageNumberLabel.setBrush(PdfBrushes.getBlack());
          pageNumberLabel.setFont(font);
          format = new PdfStringFormat(PdfTextAlignment.Right);
          pageNumberLabel.setStringFormat(format);
          pageNumberLabel.setText("page {0} of {1}");
          pageNumberLabel.setBounds(footer.getBounds());
          pageNumberLabel.draw(footer.getGraphics(), - margin.getLeft(), y);
    }
    
    public String convertToEmpty(String value) {
        if(value == null) return "";
        else return value;
    }
    
    private static class inchideAdmFisiere extends WindowAdapter {  
        public void windowClosing(WindowEvent evt) {  
            Object[] optiuni = {"Confirma", "Anuleaza"};
            UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
            UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
            int optiune = JOptionPane.showOptionDialog(DLMAppStart.appStart,
                "Esti sigur ca doresti sa inchizi complet acesta fereastra?",
                "Confirmare:",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                optiuni,  //the titles of buttons
                optiuni[0]); //default button title
            if (optiune == JOptionPane.YES_OPTION) {
                fereastraFisiere.dispose();
            }
        }  
    }
    
    private static class inchideAdmUtilizatori extends WindowAdapter {  
        public void windowClosing(WindowEvent evt) {  
            Object[] optiuni = {"Confirma", "Anuleaza"};
            UIManager.put("OptionPane.messageFont", new Font("DialogInput", Font.BOLD, 18));
            UIManager.put("OptionPane.buttonFont", new Font("DialogInput", Font.BOLD, 18));
            int optiune = JOptionPane.showOptionDialog(DLMAppStart.appStart,
                "Esti sigur ca doresti sa inchizi complet acesta fereastra?",
                "Confirmare:",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                optiuni,  //the titles of buttons
                optiuni[0]); //default button title
            if (optiune == JOptionPane.YES_OPTION) {
                fereastraUtilizatori.dispose();
            }
        }  
    }
    
    private void scoateSQL_fkConstr() {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void reinitiereSQL_fkConstr() {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void reunire_FK() {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
            "SELECT fisiere.Raportlink FROM fisiere LEFT JOIN rapoarte ON rapoarte.id = fisiere.Raportlink " +
            "WHERE fisiere.Raportlink IS NULL;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void setNULL_SQL() {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
            "UPDATE fisiere SET Raportlink = NULL WHERE Raportlink = 0;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void adaugaSQL_fkConstr() {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
            "ALTER TABLE fisiere ADD CONSTRAINT symbol " +
            "FOREIGN KEY idx_fisiere_Raportlink (Raportlink) " +
            "REFERENCES raporate (id) ON DELETE SET NULL ON UPDATE CASCADE;");
            stmt.execute();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DLMAppStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}