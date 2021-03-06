package Components.Events.project;

import DataBaseConn.project.DataBaseConn;

import Users.project.UsersDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dragos
 */
public class MeniuAdmUtilizatori extends javax.swing.JPanel {
    String connectionUrl = String.format("jdbc:mysql://%s:%s/%s", "127.0.0.1", "3306", "dsr");
    DataBaseConn db = new DataBaseConn(connectionUrl, "root", "@Root123#");
    List<String> arrUti = new ArrayList<>();
    String[] numeUti = {};
    String[] selectUti = {};
    /** Creates new form MeniuAdmUtilizatori */
    public MeniuAdmUtilizatori() {
        selectUti();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jTabbedPaneAdmUtilizatori = new javax.swing.JTabbedPane();
        jPanelStergeUti = new javax.swing.JPanel();
        jLabelTitluStergeUti = new javax.swing.JLabel();
        jLabelNumeStergeUti = new javax.swing.JLabel();
        jLabelConfStergeUti = new javax.swing.JLabel();
        jTextFieldNumeStergeUti = new javax.swing.JTextField();
        jTextFieldConfStergeUti = new javax.swing.JTextField();
        jBtnStergeUti = new javax.swing.JButton();
        jLabelAvertismenteStergeUti = new javax.swing.JLabel();
        jPanelResetParolaUti = new javax.swing.JPanel();
        jLabelTitluResetParola = new javax.swing.JLabel();
        jLabelSelectUti = new javax.swing.JLabel();
        jLabelParolaNoua = new javax.swing.JLabel();
        jLabelConfParolaNoua = new javax.swing.JLabel();
        jTextFieldParolaNouaUti = new javax.swing.JTextField();
        jTextFieldConfResetParola = new javax.swing.JTextField();
        jBtnResetareParolaUti = new javax.swing.JButton();
        jLabelAvertizareResetParola = new javax.swing.JLabel();
        jCheckBoxResetParolaComplexa = new javax.swing.JCheckBox();
        jSelectareUtiResetParola = new javax.swing.JComboBox<>();
        jPanelMeniuUti = new javax.swing.JPanel();
        jBtnAfisareUti = new javax.swing.JButton();
        jLabelTitluAdmUti = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAfiseazaUtilizatori = new javax.swing.JList<>();
        jLabelLogoAdmUti = new javax.swing.JLabel();
        jPanelAdaugareUti = new javax.swing.JPanel();
        jLabelTitluAdaugaUti = new javax.swing.JLabel();
        jLabelNumeAdaugaUti = new javax.swing.JLabel();
        jTextFieldAdaugaNumeUti = new javax.swing.JTextField();
        jCheckBoxParolaComplexa = new javax.swing.JCheckBox();
        jLabelSetareParola = new javax.swing.JLabel();
        jTextFieldSetareParola = new javax.swing.JTextField();
        jLabelConfirmareParola = new javax.swing.JLabel();
        jTextFieldConfParola = new javax.swing.JTextField();
        jBtnAdaugareUtilizator = new javax.swing.JButton();
        jLabelAvertismenteAdaugareUti = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1080, 680));

        jTabbedPaneAdmUtilizatori.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTabbedPaneAdmUtilizatori.setMinimumSize(new java.awt.Dimension(1080, 680));
        jTabbedPaneAdmUtilizatori.setPreferredSize(new java.awt.Dimension(1080, 680));

        jPanelStergeUti.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelStergeUti.setPreferredSize(new java.awt.Dimension(1320, 940));

        jLabelTitluStergeUti.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluStergeUti.setText("Stergere utilizator");

        jLabelNumeStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNumeStergeUti.setText("Nume utilizator:");

        jLabelConfStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelConfStergeUti.setText("Confirma stergere utilizator:");

        jTextFieldNumeStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldNumeStergeUti.setForeground(new java.awt.Color(0, 153, 255));

        jTextFieldConfStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldConfStergeUti.setForeground(new java.awt.Color(0, 153, 255));

        jBtnStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnStergeUti.setText("Sterge");
        jBtnStergeUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStergeUtiActionPerformed(evt);
            }
        });

        jLabelAvertismenteStergeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteStergeUti.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanelStergeUtiLayout = new javax.swing.GroupLayout(jPanelStergeUti);
        jPanelStergeUti.setLayout(jPanelStergeUtiLayout);
        jPanelStergeUtiLayout.setHorizontalGroup(
            jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStergeUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStergeUtiLayout.createSequentialGroup()
                        .addComponent(jLabelTitluStergeUti)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelStergeUtiLayout.createSequentialGroup()
                        .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNumeStergeUti)
                            .addComponent(jLabelConfStergeUti, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNumeStergeUti)
                            .addComponent(jTextFieldConfStergeUti)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStergeUtiLayout.createSequentialGroup()
                        .addComponent(jLabelAvertismenteStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(jBtnStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelStergeUtiLayout.setVerticalGroup(
            jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStergeUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluStergeUti)
                .addGap(55, 55, 55)
                .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumeStergeUti)
                    .addComponent(jTextFieldNumeStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConfStergeUti)
                    .addComponent(jTextFieldConfStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelStergeUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAvertismenteStergeUti, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPaneAdmUtilizatori.addTab("Stergere utilizator", jPanelStergeUti);

        jPanelResetParolaUti.setMinimumSize(new java.awt.Dimension(1080, 680));

        jLabelTitluResetParola.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluResetParola.setText("Resetare parola utilizator");

        jLabelSelectUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelSelectUti.setText("Selectare utilizator:");

        jLabelParolaNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelParolaNoua.setText("Parola noua:");

        jLabelConfParolaNoua.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelConfParolaNoua.setText("Confirmare parola:");

        jTextFieldParolaNouaUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldParolaNouaUti.setForeground(new java.awt.Color(0, 153, 255));

        jTextFieldConfResetParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldConfResetParola.setForeground(new java.awt.Color(0, 153, 255));

        jBtnResetareParolaUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnResetareParolaUti.setText("Resetare");
        jBtnResetareParolaUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnResetareParolaUtiActionPerformed(evt);
            }
        });

        jLabelAvertizareResetParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertizareResetParola.setForeground(new java.awt.Color(255, 0, 0));

        jCheckBoxResetParolaComplexa.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jCheckBoxResetParolaComplexa.setText("Parola complexa");

        jSelectareUtiResetParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jSelectareUtiResetParola.setForeground(new java.awt.Color(0, 153, 255));
        jSelectareUtiResetParola.setModel(new javax.swing.DefaultComboBoxModel<>(selectUti));

        javax.swing.GroupLayout jPanelResetParolaUtiLayout = new javax.swing.GroupLayout(jPanelResetParolaUti);
        jPanelResetParolaUti.setLayout(jPanelResetParolaUtiLayout);
        jPanelResetParolaUtiLayout.setHorizontalGroup(
            jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResetParolaUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResetParolaUtiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnResetareParolaUti, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelResetParolaUtiLayout.createSequentialGroup()
                        .addGroup(jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitluResetParola)
                            .addGroup(jPanelResetParolaUtiLayout.createSequentialGroup()
                                .addGroup(jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBoxResetParolaComplexa)
                                    .addComponent(jLabelConfParolaNoua)
                                    .addComponent(jTextFieldParolaNouaUti, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                                    .addComponent(jTextFieldConfResetParola, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                                    .addComponent(jLabelSelectUti)
                                    .addComponent(jLabelParolaNoua)
                                    .addComponent(jSelectareUtiResetParola, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelAvertizareResetParola, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelResetParolaUtiLayout.setVerticalGroup(
            jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResetParolaUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluResetParola)
                .addGap(18, 18, 18)
                .addGroup(jPanelResetParolaUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelResetParolaUtiLayout.createSequentialGroup()
                        .addComponent(jLabelSelectUti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSelectareUtiResetParola, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelParolaNoua, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldConfResetParola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxResetParolaComplexa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelConfParolaNoua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldParolaNouaUti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelAvertizareResetParola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBtnResetareParolaUti, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPaneAdmUtilizatori.addTab("Resetare parole", jPanelResetParolaUti);

        jPanelMeniuUti.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelMeniuUti.setPreferredSize(new java.awt.Dimension(1080, 680));

        jBtnAfisareUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnAfisareUti.setText("Afiseaza utilizatori");
        jBtnAfisareUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAfisareUtiActionPerformed(evt);
            }
        });

        jLabelTitluAdmUti.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluAdmUti.setText("Administrare utilizatori");

        jAfiseazaUtilizatori.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jAfiseazaUtilizatori.setForeground(new java.awt.Color(0, 153, 255));
        jScrollPane1.setViewportView(jAfiseazaUtilizatori);

        javax.swing.GroupLayout jPanelMeniuUtiLayout = new javax.swing.GroupLayout(jPanelMeniuUti);
        jPanelMeniuUti.setLayout(jPanelMeniuUtiLayout);
        jPanelMeniuUtiLayout.setHorizontalGroup(
            jPanelMeniuUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMeniuUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMeniuUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitluAdmUti)
                    .addGroup(jPanelMeniuUtiLayout.createSequentialGroup()
                        .addGroup(jPanelMeniuUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnAfisareUti))
                        .addGap(107, 107, 107)
                        .addComponent(jLabelLogoAdmUti, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMeniuUtiLayout.setVerticalGroup(
            jPanelMeniuUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMeniuUtiLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabelTitluAdmUti)
                .addGroup(jPanelMeniuUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMeniuUtiLayout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jLabelLogoAdmUti, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                    .addGroup(jPanelMeniuUtiLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAfisareUti, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPaneAdmUtilizatori.addTab("Administrare utilizatori", jPanelMeniuUti);

        jPanelAdaugareUti.setMinimumSize(new java.awt.Dimension(1080, 680));
        jPanelAdaugareUti.setPreferredSize(new java.awt.Dimension(1080, 680));

        jLabelTitluAdaugaUti.setFont(new java.awt.Font("DialogInput", 1, 48)); // NOI18N
        jLabelTitluAdaugaUti.setText("Adaugare utilizator nou");

        jLabelNumeAdaugaUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelNumeAdaugaUti.setText("Nume:");

        jTextFieldAdaugaNumeUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldAdaugaNumeUti.setForeground(new java.awt.Color(0, 153, 255));

        jCheckBoxParolaComplexa.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jCheckBoxParolaComplexa.setText("Parola complexa");

        jLabelSetareParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelSetareParola.setText("Setare parola:");

        jTextFieldSetareParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldSetareParola.setForeground(new java.awt.Color(0, 153, 255));

        jLabelConfirmareParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelConfirmareParola.setText("Confirmare parola:");

        jTextFieldConfParola.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jTextFieldConfParola.setForeground(new java.awt.Color(0, 153, 255));

        jBtnAdaugareUtilizator.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jBtnAdaugareUtilizator.setText("Adauga utilizator");
        jBtnAdaugareUtilizator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdaugareUtilizatorActionPerformed(evt);
            }
        });

        jLabelAvertismenteAdaugareUti.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabelAvertismenteAdaugareUti.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanelAdaugareUtiLayout = new javax.swing.GroupLayout(jPanelAdaugareUti);
        jPanelAdaugareUti.setLayout(jPanelAdaugareUtiLayout);
        jPanelAdaugareUtiLayout.setHorizontalGroup(
            jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                        .addComponent(jLabelTitluAdaugaUti)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                        .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelSetareParola, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNumeAdaugaUti, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelConfirmareParola, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelAvertismenteAdaugareUti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAdaugaNumeUti)
                            .addComponent(jTextFieldSetareParola)
                            .addComponent(jTextFieldConfParola, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBtnAdaugareUtilizator, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxParolaComplexa))
                                .addGap(0, 243, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanelAdaugareUtiLayout.setVerticalGroup(
            jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitluAdaugaUti)
                .addGap(18, 18, 18)
                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAdaugaNumeUti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNumeAdaugaUti))
                .addGap(18, 18, 18)
                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSetareParola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSetareParola))
                .addGap(18, 18, 18)
                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldConfParola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfirmareParola))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdaugareUtiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdaugareUtiLayout.createSequentialGroup()
                        .addComponent(jCheckBoxParolaComplexa)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAdaugareUtilizator, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelAvertismenteAdaugareUti, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPaneAdmUtilizatori.addTab("Adaugare utilizator", jPanelAdaugareUti);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneAdmUtilizatori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneAdmUtilizatori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }//GEN-END:initComponents

    private void jBtnAfisareUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAfisareUtiActionPerformed
        // TODO add your handling code here:
        UsersDAO reafiseazaUti = new UsersDAO(db.getConnection());
        try {
            arrUti = reafiseazaUti.getNames();
            numeUti = new String[arrUti.size()];
            for(int i = 0; i < arrUti.size(); i++) {
                numeUti[i] = arrUti.get(i);
            }
            Arrays.sort(numeUti);
            jAfiseazaUtilizatori.setListData(numeUti);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnAfisareUtiActionPerformed

    private void jBtnStergeUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStergeUtiActionPerformed
        // TODO add your handling code here:
        Map<String, String> utilizatori = new HashMap<>();
        UsersDAO stergeUti = new UsersDAO(db.getConnection());
        try {
            utilizatori = stergeUti.getAll();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        String numeUti = jTextFieldNumeStergeUti.getText();
        String confirmare = jTextFieldConfStergeUti.getText();

        if(!numeUti.equals("Admin")) {
            if(!utilizatori.containsKey(numeUti)) {
                jLabelAvertismenteStergeUti.setText("<html><center>Utilizatorul selectat <br>nu exista!</center></html>");
            } else {
                if (numeUti.equals(confirmare)) {
                    try {
                        boolean success = Boolean.parseBoolean(numeUti);
                        deleteUserSQL(success);
                        assert(success);
                        jLabelAvertismenteStergeUti.setText("<html><center>" + numeUti.toString() + " <br>a fost sters!</center></html>");
                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                } else {
                    jLabelAvertismenteStergeUti.setText("<html><center>Numele utilizatorului<br> trebuie sa coincida!</center></html>");
                }
            }
        } else {
            jLabelAvertismenteStergeUti.setText("<html><center>Administratorul este<br> imposibil de sters!</center></html>");
        }
    }//GEN-LAST:event_jBtnStergeUtiActionPerformed

    private void jBtnResetareParolaUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnResetareParolaUtiActionPerformed
        // TODO add your handling code here:
        String parola = jTextFieldParolaNouaUti.getText();
        String confirmare = jTextFieldConfResetParola.getText();
        boolean charSpecial = false;
        boolean majuscula = false;
        boolean numere = false;
        for(int i = 0; i < parola.length(); i++) {
            if(Character.isDigit(parola.charAt(i))) {
                numere = true;
            }
            if(Character.isUpperCase(parola.charAt(i))) {
                majuscula = true;
            }
            if(!Character.isLetterOrDigit(parola.charAt(i))) {
                charSpecial = true;
            }
        }
        UsersDAO utilizatori = new UsersDAO(db.getConnection());
        String userName = String.valueOf(jSelectareUtiResetParola.getSelectedItem());
        if(jCheckBoxResetParolaComplexa.isSelected()){
            if(!(parola.length() > 7) || !numere || !majuscula || !charSpecial) {
               jLabelAvertizareResetParola.setText("<html><center>Parola trebuie sa fie de minimum 8 caractere " +
                                                   "lungime, sa contina cel putin o majuscula, un numar si un " +
                                                   "caracter special!</center><html/>");
            } else {
                if(!parola.equals(confirmare)) {
                   jLabelAvertizareResetParola.setText("<html><center>Campul de parola si cel<br> " +
                                                       "de confirmare parola trebuie sa fie identice!</center><html/>");
                } else {
                    try {
                        utilizatori.updateParolaByName(userName, parola);
                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    jLabelAvertizareResetParola.setText("<html><center>Parola a fost schimbata cu succes!</center><html/>");
                }
            }
        } else {
            if(!parola.equals(confirmare)) {
               jLabelAvertizareResetParola.setText("<html><center>Campul de parola si cel<br> de confirmare parola " +
                                                   "trebuie sa fie identice!</center><html/>");
            } else {
                try {
                    utilizatori.updateParolaByName(userName, parola);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                jLabelAvertizareResetParola.setText("<html><center>Parola a fost schimbata cu succes!</center><html/>");
            }
        }
    }//GEN-LAST:event_jBtnResetareParolaUtiActionPerformed

    private void jBtnAdaugareUtilizatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAdaugareUtilizatorActionPerformed
        // TODO add your handling code here:
        String numeUti = jTextFieldAdaugaNumeUti.getText();
        String parola = jTextFieldSetareParola.getText();
        String confirmare = jTextFieldConfParola.getText();
        boolean charSpecial = false;
        boolean majuscula = false;
        boolean numere = false;
        Map<String, String> utilizatori = new HashMap<>();
        UsersDAO adaugaUti = new UsersDAO(db.getConnection());
        try {
            utilizatori = adaugaUti.getAll();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        for(int i = 0; i < parola.length(); i++) {
            if(Character.isDigit(parola.charAt(i))) {
                numere = true;
            }
            if(Character.isUpperCase(parola.charAt(i))) {
                majuscula = true;
            }
            if(!Character.isLetterOrDigit(parola.charAt(i))) {
                charSpecial = true;
            }
        }
        if(numeUti.isEmpty()) {
            jLabelAvertismenteAdaugareUti.setText("<html><center>Utilizatorul trebuie sa aiba un nume!</center></html>");
        } else if(parola.isEmpty() && confirmare.isEmpty()) {
            jLabelAvertismenteAdaugareUti.setText("<html><center>Orice utilizator trebuie sa aiba o parola!</center></html>");
        } else {
            if(jCheckBoxParolaComplexa.isSelected()){
                if(utilizatori.containsKey(numeUti)) {
                    jLabelAvertismenteAdaugareUti.setText("<html><center>Acest utilizator exista deja!<br> Va rog sa alegeti un nume diferit!</center><html/>");
                } else {
                    if(!(parola.length() > 7) || !numere || !majuscula || !charSpecial) {
                        jLabelAvertismenteAdaugareUti.setText("<html><center>Parola trebuie sa fie de minimum 8 caractere lungime, " +
                            "sa contina cel putin o majuscula, un numar si un caracter special!</center><html/>");
                    } else {
                        if(!parola.equals(confirmare)) {
                            jLabelAvertismenteAdaugareUti.setText("<html><center>Campul de parola si cel de confirmare parola trebuie sa fie identice!</center><html/>");
                        } else {
                            utilizatori.put(numeUti, parola);
                            try {
                                addUserSQL(numeUti, parola);
                            } catch (SQLException ex) {
                                java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                            jLabelAvertismenteAdaugareUti.setText("<html><center>Utilizatorul<br> " + numeUti + " <br>a fost adaugat!</center></html>");
                        }
                    }
                }
            } else {
                if(utilizatori.containsKey(numeUti)) {
                    jLabelAvertismenteAdaugareUti.setText("<html><center>Acest utilizator exista deja!<br> Va rog sa alegeti un nume diferit!</center><html/>");
                } else {
                    if(!parola.equals(confirmare)) {
                        jLabelAvertismenteAdaugareUti.setText("<html><center>Campul de parola si cel de confirmare parola trebuie sa fie identice!</center><html/>");
                    } else {
                        utilizatori.put(numeUti, parola);
                        try {
                            addUserSQL(numeUti, parola);
                        } catch (SQLException ex) {
                            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        jLabelAvertismenteAdaugareUti.setText("<html><center>Utilizatorul<br> " + numeUti + " <br>a fost adaugat!</center></html>");
                    }
                }
            }
        }
    }//GEN-LAST:event_jBtnAdaugareUtilizatorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> jAfiseazaUtilizatori;
    private javax.swing.JButton jBtnAdaugareUtilizator;
    private javax.swing.JButton jBtnAfisareUti;
    private javax.swing.JButton jBtnResetareParolaUti;
    private javax.swing.JButton jBtnStergeUti;
    private javax.swing.JCheckBox jCheckBoxParolaComplexa;
    private javax.swing.JCheckBox jCheckBoxResetParolaComplexa;
    private javax.swing.JLabel jLabelAvertismenteAdaugareUti;
    private javax.swing.JLabel jLabelAvertismenteStergeUti;
    private javax.swing.JLabel jLabelAvertizareResetParola;
    private javax.swing.JLabel jLabelConfParolaNoua;
    private javax.swing.JLabel jLabelConfStergeUti;
    private javax.swing.JLabel jLabelConfirmareParola;
    private javax.swing.JLabel jLabelLogoAdmUti;
    private javax.swing.JLabel jLabelNumeAdaugaUti;
    private javax.swing.JLabel jLabelNumeStergeUti;
    private javax.swing.JLabel jLabelParolaNoua;
    private javax.swing.JLabel jLabelSelectUti;
    private javax.swing.JLabel jLabelSetareParola;
    private javax.swing.JLabel jLabelTitluAdaugaUti;
    private javax.swing.JLabel jLabelTitluAdmUti;
    private javax.swing.JLabel jLabelTitluResetParola;
    private javax.swing.JLabel jLabelTitluStergeUti;
    private javax.swing.JPanel jPanelAdaugareUti;
    private javax.swing.JPanel jPanelMeniuUti;
    private javax.swing.JPanel jPanelResetParolaUti;
    private javax.swing.JPanel jPanelStergeUti;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jSelectareUtiResetParola;
    private javax.swing.JTabbedPane jTabbedPaneAdmUtilizatori;
    private javax.swing.JTextField jTextFieldAdaugaNumeUti;
    private javax.swing.JTextField jTextFieldConfParola;
    private javax.swing.JTextField jTextFieldConfResetParola;
    private javax.swing.JTextField jTextFieldConfStergeUti;
    private javax.swing.JTextField jTextFieldNumeStergeUti;
    private javax.swing.JTextField jTextFieldParolaNouaUti;
    private javax.swing.JTextField jTextFieldSetareParola;
    // End of variables declaration//GEN-END:variables

    private void selectUti() {
        UsersDAO selecteazaUti = new UsersDAO(db.getConnection());
        try {
            arrUti = selecteazaUti.getNames();
            selectUti = new String[arrUti.size()];
            for(int i = 0; i < arrUti.size(); i++) {
                selectUti[i] = arrUti.get(i);
            }
            Arrays.sort(selectUti);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MeniuTaburi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void addUserSQL(String user, String parola) throws SQLException {
        String query = "INSERT INTO Utilizatori(nume, parola) VALUES(?, ?);";
        PreparedStatement stmt = db.getConnection().prepareStatement(query, new String[]{"id"});;
        stmt.setString(1, user);
        stmt.setString(2, parola);
        stmt.executeUpdate();
    }
    
    private void deleteUserSQL(Boolean bool) throws SQLException {
        String query = "DELETE FROM Utilizatori WHERE nume = ?;";
        PreparedStatement stmt = db.getConnection().prepareStatement(query, new String[]{"id"});;
        stmt.setBoolean(1, bool);
        stmt.executeUpdate();
    }

}