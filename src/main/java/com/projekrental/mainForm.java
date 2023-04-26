/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.projekrental;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author me
 */
public class mainForm extends javax.swing.JFrame {
    private Connection con;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;

    /**
     * Creates new form mainForm
     */
    public mainForm() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getDataOwner();
        getDataLaptop();
        getDataClient();
        autoKodeOwner();
        autoKodeLaptop();
        autoKodeClient();
        
        getListLaptop();
        
    }
    
    
    //load data owner
    private void getDataOwner(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No");
            model.addColumn("ID Owner");
            model.addColumn("Nama");
            model.addColumn("Telepon");
            model.addColumn("Alamat");
            int no = 1;
       
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM owner");
            
            while(rs.next()){
                Object[] obj = new Object[5];
                obj[0] = no++;
                obj[1] = rs.getString("id_owner");
                obj[2] = rs.getString("nama_owner");
                obj[3] = rs.getString("telepon_owner");
                obj[4] = rs.getString("alamat_owner");
                
                model.addRow(obj);
                tblOwner.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mendapatkan data owner");
        }
    }
    //load data owner
    
    //load data laptop
    private void getDataLaptop(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("NO");
            model.addColumn("ID Laptop");
            model.addColumn("ID Owner");
            model.addColumn("Merk");
            model.addColumn("Model");
            model.addColumn("Spesifikasi");
            model.addColumn("Tahun");
            model.addColumn("Harga sewa/hari");
            int no = 1;
            
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM laptop");
            
            while(rs.next()){
                Object[] obj = new Object[8];
                obj[0] = no++;
                obj[1] = rs.getString("id_laptop");
                obj[2] = rs.getString("id_owner");
                obj[3] = rs.getString("merk_laptop");
                obj[4] = rs.getString("model_laptop");
                obj[5] = rs.getString("spesifikasi_laptop");
                obj[6] = rs.getString("tahun_laptop");
                obj[7] = rs.getString("harga_sewa");
                model.addRow(obj);
                tblLaptop.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load data laptop
    
    //load data client
    private void getDataClient(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Client");
            model.addColumn("Nama");
            model.addColumn("Telepon");
            model.addColumn("Alamat");

            
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM client");
            
            while(rs.next()){
                Object[] obj = new Object[4];
                obj[0] = rs.getString("id_client");
                obj[1] = rs.getString("nama_client");
                obj[2] = rs.getString("telepon_client");
                obj[3] = rs.getString("alamat_client");
                
                model.addRow(obj);
                tblClient.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load data client
    
    //load list laptop
    private void getListLaptop(){
        try {
            DefaultListModel model = new DefaultListModel();
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT model_laptop FROM laptop");
            
            while(rs.next()){
                String list = rs.getString("model_laptop");
                model.addElement(list);
                listLaptop.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load list laptop
    
    
    //load merk dari list laptop
    private String getMerk(String model){
       String result="";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT merk_laptop FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            
            if(rs.next()){
               result = rs.getString("merk_laptop");
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
   
    }
    //load merk dari list laptop
    
    //load model dari list laptop
    private String getModel(String model){
        String result="";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT model_laptop FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("model_laptop");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load model dari list laptop
    
    //load harga sewa harian laptop dari list laptop
    private String getHarga(String model){
        String result="";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT harga_sewa FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("harga_sewa");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load harga sewa harian laptop dari list laptop
    
    //load ID Laptop dari list laptop
    private String getIdLaptop(String model){
        String result = "";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT id_laptop FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("id_laptop");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load ID Laptop dari list laptop
    
    //load ID Owner dari list laptop
    private String getIdOwner(String model){
        String result = "";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT id_owner FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("id_owner");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load ID Owner dari list laptop
    
    //load tahun laptop dari list laptop
    private String getTahun(String model){
        String result="";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT tahun_laptop FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("tahun_laptop");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load tahun laptop dari list laptop
    
    //load spesifikasi laptop dari list laptop
    private String getSpek(String model){
        String result="";
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT spesifikasi_laptop FROM laptop WHERE model_laptop=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("spesifikasi_laptop");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load spesifikasi laptop dari list laptop

    //Auto Kode owner
    private void autoKodeOwner(){
        try {
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM owner ORDER BY id_owner DESC");
            if(rs.next()){
                String lastId = rs.getString("id_owner");
                int number = Integer.parseInt(lastId.substring(3));
                String newId = String.format("OWN%05d", number + 1);
                txtNewIdOwner.setText(newId);
            }else{
                txtNewIdOwner.setText("OWN00001");
            }
                
                
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }
    //Auto Kode owner
    
    //Auto Kode Laptop
    private void autoKodeLaptop(){
        try {
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM laptop ORDER BY id_laptop DESC");
            if(rs.next()){
                String lastId = rs.getString("id_laptop");
                int number = Integer.parseInt(lastId.substring(3));
                String newId = String.format("LPT%05d", number + 1);
                txtNewIdLaptop.setText(newId);
            }else{
                txtNewIdLaptop.setText("LPT00001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Auto Kode Laptop
    
    //Auto Kode client
        private void autoKodeClient(){
            try {
                con = Koneksi.getKoneksi();
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM client ORDER BY id_client DESC");
                if(rs.next()){
                    String lastId = rs.getString("id_client");
                    int number = Integer.parseInt(lastId.substring(3));
                    String newId = String.format("CLN%05d", number + 1);
                    txtAddIdClient.setText(newId);
                }else{
                    txtAddIdClient.setText("CLN00001");
                }
            } catch (NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    //Auto kode client
    
    //Bersihkan textfield di add owner
    private void addOwnerBersih(){
        txtNewNamaOwner.setText("");
        txtNewTeleponOwner.setText("");
        txaNewAlamatOwner.setText("");
    }
    //Bersihkan textfield di add owner
    
    //bersihkan textfield di add laptop
    private void addLaptopBersih(){
        txtCariTelepon.setText("");
        txtNewMerk.setText("");
        txtNewModel.setText("");
        txtNewSpesifikasi.setText("");
        txtNewHargaSewa.setText("");
    }
    //bersihkan textfield di add laptop
    
    //bersihkan textfield di add laptop
    private void addClientBersih(){
        txtAddNamaClient.setText("");
        txtAddTeleponClient.setText("");
        txtAddAlamatClient.setText("");
    }
    //bersihkan textfield di add laptop
    
    //bersihkan textfield di data owner
    private void dataOwnerBersih(){
        txtDataIdOwner.setText("");
        txtDataNamaOwner.setText("");
        txtDataTeleponOwner.setText("");
        txtDataAlamatOwner.setText("");
    }
    //bersihkan textfield di data owner
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_title = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pn_dashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnData = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnClient = new javax.swing.JButton();
        pn_menu = new javax.swing.JPanel();
        pn_new = new javax.swing.JPanel();
        pn_newButton = new javax.swing.JPanel();
        btnAddOwner = new javax.swing.JButton();
        btnAddLaptop = new javax.swing.JButton();
        pn_newMenu = new javax.swing.JPanel();
        pn_addOwner = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNewNamaOwner = new javax.swing.JTextField();
        txtNewTeleponOwner = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaNewAlamatOwner = new javax.swing.JTextArea();
        btnNewOwner = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        lblOwnerTelepon = new javax.swing.JLabel();
        txtNewIdOwner = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        pn_addLaptop = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaOwnerLaptop = new javax.swing.JTextField();
        txtNewMerk = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCariTelepon = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNewModel = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNewSpesifikasi = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        txtNewHargaSewa = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnNewAddLaptop = new javax.swing.JButton();
        lblCari = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblIdOwner = new javax.swing.JLabel();
        spNewTahun = new com.toedter.calendar.JYearChooser();
        jLabel45 = new javax.swing.JLabel();
        txtNewIdLaptop = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        lblHargaSewaAngka = new javax.swing.JLabel();
        pn_client = new javax.swing.JPanel();
        pn_clientButton = new javax.swing.JPanel();
        btnAddClient = new javax.swing.JButton();
        btnOrderLaptop = new javax.swing.JButton();
        pn_clientMenu = new javax.swing.JPanel();
        pn_addClient = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtAddNamaClient = new javax.swing.JTextField();
        txtAddTeleponClient = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAddAlamatClient = new javax.swing.JTextArea();
        btnAddClientForm = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        txtAddIdClient = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        lblTeleponAddClient = new javax.swing.JLabel();
        pn_orderLaptop = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listLaptop = new javax.swing.JList<>();
        jLabel17 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblMerk = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblModel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblTahun = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblHargaSewaHarian = new javax.swing.JLabel();
        txtCariNoTelpClient = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        dateAmbil = new com.toedter.calendar.JDateChooser();
        dateKembali = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblIdClient = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        lblSpek = new javax.swing.JTextArea();
        txtNamaClient = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        lblIdLaptop = new javax.swing.JLabel();
        lblOwner = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        btnOrder1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblCariNoTelp = new javax.swing.JLabel();
        btnCalc = new javax.swing.JButton();
        lblJumlahHari = new javax.swing.JLabel();
        pn_data = new javax.swing.JPanel();
        pn_dataButton = new javax.swing.JPanel();
        btnDataOwner = new javax.swing.JButton();
        btnDataTransaksi = new javax.swing.JButton();
        btnDataLaptop = new javax.swing.JButton();
        btnDataClient = new javax.swing.JButton();
        pn_dataMenu = new javax.swing.JPanel();
        pn_dataOwner = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblOwner = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        txtDataNamaOwner = new javax.swing.JTextField();
        txtDataTeleponOwner = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtDataAlamatOwner = new javax.swing.JTextArea();
        btnDataOwnerUpdate = new javax.swing.JButton();
        btnDataOwnerDelete = new javax.swing.JButton();
        jTextField13 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtDataIdOwner = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        pn_dataClient = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField16 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        pn_dataLaptop = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblLaptop = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField19 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        pn_dataTransaksi = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField26 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pn_title.setBackground(new java.awt.Color(25, 25, 112));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CV. SARANA MULTI KOMPUTER");

        javax.swing.GroupLayout pn_titleLayout = new javax.swing.GroupLayout(pn_title);
        pn_title.setLayout(pn_titleLayout);
        pn_titleLayout.setHorizontalGroup(
            pn_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_titleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(817, Short.MAX_VALUE))
        );
        pn_titleLayout.setVerticalGroup(
            pn_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        getContentPane().add(pn_title, java.awt.BorderLayout.PAGE_START);

        pn_dashboard.setBackground(new java.awt.Color(28, 36, 52));

        jLabel2.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\image 2.png")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lato", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(159, 222, 249));
        jLabel3.setText("Welcome <admin>");

        btnData.setBackground(new java.awt.Color(51, 58, 72));
        btnData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnData.setForeground(new java.awt.Color(255, 255, 255));
        btnData.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\database.png")); // NOI18N
        btnData.setText("DATA");
        btnData.setBorderPainted(false);
        btnData.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnData.setIconTextGap(30);
        btnData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(107, 125, 253));
        btnNew.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\src\\main\\java\\image\\plus-circle.png")); // NOI18N
        btnNew.setText("NEW");
        btnNew.setBorderPainted(false);
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNew.setIconTextGap(36);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnClient.setBackground(new java.awt.Color(51, 58, 72));
        btnClient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClient.setForeground(new java.awt.Color(255, 255, 255));
        btnClient.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\src\\main\\java\\image\\users.png")); // NOI18N
        btnClient.setText("CLIENT");
        btnClient.setBorderPainted(false);
        btnClient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnClient.setIconTextGap(30);
        btnClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_dashboardLayout = new javax.swing.GroupLayout(pn_dashboard);
        pn_dashboard.setLayout(pn_dashboardLayout);
        pn_dashboardLayout.setHorizontalGroup(
            pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClient, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnData, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_dashboardLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );
        pn_dashboardLayout.setVerticalGroup(
            pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dashboardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnClient, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnData, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        getContentPane().add(pn_dashboard, java.awt.BorderLayout.LINE_START);

        pn_menu.setLayout(new java.awt.CardLayout());

        pn_new.setLayout(new java.awt.BorderLayout());

        pn_newButton.setBackground(new java.awt.Color(26, 26, 26));

        btnAddOwner.setBackground(new java.awt.Color(107, 125, 253));
        btnAddOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddOwner.setForeground(new java.awt.Color(255, 255, 255));
        btnAddOwner.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\user-plus.png")); // NOI18N
        btnAddOwner.setText("add Owner");
        btnAddOwner.setBorderPainted(false);
        btnAddOwner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddOwner.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddOwner.setIconTextGap(15);
        btnAddOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOwnerActionPerformed(evt);
            }
        });

        btnAddLaptop.setBackground(new java.awt.Color(51, 58, 72));
        btnAddLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddLaptop.setForeground(new java.awt.Color(255, 255, 255));
        btnAddLaptop.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\laptop.png")); // NOI18N
        btnAddLaptop.setText("add Laptop");
        btnAddLaptop.setBorderPainted(false);
        btnAddLaptop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddLaptop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddLaptop.setIconTextGap(15);
        btnAddLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLaptopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_newButtonLayout = new javax.swing.GroupLayout(pn_newButton);
        pn_newButton.setLayout(pn_newButtonLayout);
        pn_newButtonLayout.setHorizontalGroup(
            pn_newButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_newButtonLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnAddOwner)
                .addGap(18, 18, 18)
                .addComponent(btnAddLaptop)
                .addGap(0, 637, Short.MAX_VALUE))
        );
        pn_newButtonLayout.setVerticalGroup(
            pn_newButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_newButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_newButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddOwner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_new.add(pn_newButton, java.awt.BorderLayout.PAGE_START);

        pn_newMenu.setLayout(new java.awt.CardLayout());

        pn_addOwner.setBackground(new java.awt.Color(26, 26, 26));

        jPanel4.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama");

        txtNewTeleponOwner.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewTeleponOwnerKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Telepon");

        jLabel6.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Alamat");

        txaNewAlamatOwner.setColumns(20);
        txaNewAlamatOwner.setRows(5);
        jScrollPane1.setViewportView(txaNewAlamatOwner);

        btnNewOwner.setText("add");
        btnNewOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewOwnerActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("ADD OWNER");

        lblOwnerTelepon.setForeground(new java.awt.Color(255, 0, 51));

        jLabel68.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Id Owner");

        javax.swing.GroupLayout pn_addOwnerLayout = new javax.swing.GroupLayout(pn_addOwner);
        pn_addOwner.setLayout(pn_addOwnerLayout);
        pn_addOwnerLayout.setHorizontalGroup(
            pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_addOwnerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44))
                    .addGroup(pn_addOwnerLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNewTeleponOwner)
                                    .addComponent(txtNewNamaOwner)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNewOwner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addGap(53, 53, 53)
                                .addComponent(txtNewIdOwner)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOwnerTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_addOwnerLayout.setVerticalGroup(
            pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txtNewIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNewNamaOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOwnerTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtNewTeleponOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnNewOwner)
                .addGap(0, 248, Short.MAX_VALUE))
        );

        pn_newMenu.add(pn_addOwner, "card2");

        pn_addLaptop.setBackground(new java.awt.Color(26, 26, 26));

        jPanel5.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama owner");

        jLabel8.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Merk");

        jLabel9.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Model");

        txtCariTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariTeleponKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("cari no telepon");

        jLabel11.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Spesifikasi");

        txtNewSpesifikasi.setColumns(20);
        txtNewSpesifikasi.setRows(5);
        jScrollPane2.setViewportView(txtNewSpesifikasi);

        jLabel12.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tahun");

        txtNewHargaSewa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewHargaSewaKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Harga Sewa");

        btnNewAddLaptop.setText("add");
        btnNewAddLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewAddLaptopActionPerformed(evt);
            }
        });

        lblCari.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("id :");

        lblIdOwner.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIdOwner.setForeground(new java.awt.Color(255, 255, 255));

        spNewTahun.setOpaque(false);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("ADD LAPTOP");

        jLabel69.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Id Laptop");

        lblHargaSewaAngka.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout pn_addLaptopLayout = new javax.swing.GroupLayout(pn_addLaptop);
        pn_addLaptop.setLayout(pn_addLaptopLayout);
        pn_addLaptopLayout.setHorizontalGroup(
            pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_addLaptopLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGap(31, 31, 31)
                                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNewMerk, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNewModel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNewHargaSewa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNewAddLaptop)
                                    .addComponent(spNewTahun, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_addLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(24, 24, 24)
                                .addComponent(txtNamaOwnerLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblCari, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCariTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNewIdLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblHargaSewaAngka, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pn_addLaptopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel45)))
                .addContainerGap(402, Short.MAX_VALUE))
        );
        pn_addLaptopLayout.setVerticalGroup(
            pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addGap(29, 29, 29)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNamaOwnerLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(lblIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(txtNewIdLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNewMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNewModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(spNewTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHargaSewaAngka, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNewHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addGap(18, 18, 18)
                .addComponent(btnNewAddLaptop)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pn_newMenu.add(pn_addLaptop, "card2");

        pn_new.add(pn_newMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_new, "card2");

        pn_client.setLayout(new java.awt.BorderLayout());

        pn_clientButton.setBackground(new java.awt.Color(26, 26, 26));

        btnAddClient.setBackground(new java.awt.Color(107, 125, 253));
        btnAddClient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddClient.setForeground(new java.awt.Color(255, 255, 255));
        btnAddClient.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\user-plus.png")); // NOI18N
        btnAddClient.setText("add Client");
        btnAddClient.setBorderPainted(false);
        btnAddClient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddClient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddClient.setIconTextGap(15);
        btnAddClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClientActionPerformed(evt);
            }
        });

        btnOrderLaptop.setBackground(new java.awt.Color(51, 58, 72));
        btnOrderLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnOrderLaptop.setForeground(new java.awt.Color(255, 255, 255));
        btnOrderLaptop.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\laptop.png")); // NOI18N
        btnOrderLaptop.setText("order Laptop");
        btnOrderLaptop.setBorderPainted(false);
        btnOrderLaptop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrderLaptop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOrderLaptop.setIconTextGap(15);
        btnOrderLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderLaptopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_clientButtonLayout = new javax.swing.GroupLayout(pn_clientButton);
        pn_clientButton.setLayout(pn_clientButtonLayout);
        pn_clientButtonLayout.setHorizontalGroup(
            pn_clientButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clientButtonLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnAddClient)
                .addGap(18, 18, 18)
                .addComponent(btnOrderLaptop)
                .addGap(0, 634, Short.MAX_VALUE))
        );
        pn_clientButtonLayout.setVerticalGroup(
            pn_clientButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_clientButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_clientButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOrderLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_client.add(pn_clientButton, java.awt.BorderLayout.PAGE_START);

        pn_clientMenu.setLayout(new java.awt.CardLayout());

        pn_addClient.setBackground(new java.awt.Color(26, 26, 26));

        jPanel7.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel20.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Nama");

        txtAddTeleponClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAddTeleponClientKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Telepon");

        jLabel22.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Alamat");

        txtAddAlamatClient.setColumns(20);
        txtAddAlamatClient.setRows(5);
        jScrollPane4.setViewportView(txtAddAlamatClient);

        btnAddClientForm.setText("add");
        btnAddClientForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClientFormActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("ADD CLIENT");

        jLabel70.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Id Client");

        lblTeleponAddClient.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pn_addClientLayout = new javax.swing.GroupLayout(pn_addClient);
        pn_addClient.setLayout(pn_addClientLayout);
        pn_addClientLayout.setHorizontalGroup(
            pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addClientLayout.createSequentialGroup()
                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_addClientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel46))
                    .addGroup(pn_addClientLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_addClientLayout.createSequentialGroup()
                                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAddTeleponClient)
                                    .addComponent(txtAddNamaClient)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddClientForm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pn_addClientLayout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addGap(62, 62, 62)
                                .addComponent(txtAddIdClient)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTeleponAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(372, Short.MAX_VALUE))
        );
        pn_addClientLayout.setVerticalGroup(
            pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addClientLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(txtAddIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtAddNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtAddTeleponClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTeleponAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAddClientForm)
                .addGap(0, 250, Short.MAX_VALUE))
        );

        pn_clientMenu.add(pn_addClient, "card2");

        pn_orderLaptop.setBackground(new java.awt.Color(26, 26, 26));

        jPanel8.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        listLaptop.setBackground(new java.awt.Color(51, 58, 72));
        listLaptop.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LIST LAPTOP", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        listLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        listLaptop.setForeground(new java.awt.Color(255, 255, 255));
        listLaptop.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listLaptop.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listLaptop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listLaptopMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listLaptop);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Merk              :");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        lblMerk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMerk.setForeground(new java.awt.Color(255, 255, 255));
        lblMerk.setText("-");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Model            :");

        lblModel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblModel.setForeground(new java.awt.Color(255, 255, 255));
        lblModel.setText("-");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tahun             :");

        lblTahun.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTahun.setForeground(new java.awt.Color(255, 255, 255));
        lblTahun.setText("-");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Spesifikasi      :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Harga sewa/hari   :");

        lblHargaSewaHarian.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHargaSewaHarian.setForeground(new java.awt.Color(255, 51, 51));
        lblHargaSewaHarian.setText("-");

        txtCariNoTelpClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariNoTelpClientKeyReleased(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("cari no telp client");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Nama client         :");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 0));
        jLabel31.setText("Rp");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Tanggal ambil");

        dateAmbil.setDateFormatString("dd/MM/yyyy");

        dateKembali.setDateFormatString("dd/MM/yyyy");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Tanggal kembali");

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("id :");

        lblIdClient.setBackground(new java.awt.Color(255, 255, 255));
        lblIdClient.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIdClient.setForeground(new java.awt.Color(255, 255, 255));

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Jumlah Hari         :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Total Harga         :");

        lblTotalHarga.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotalHarga.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalHarga.setText("-");

        jScrollPane13.setBorder(null);
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        lblSpek.setBackground(new java.awt.Color(26, 26, 26));
        lblSpek.setColumns(20);
        lblSpek.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSpek.setForeground(new java.awt.Color(255, 255, 255));
        lblSpek.setRows(5);
        lblSpek.setText("-");
        lblSpek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane13.setViewportView(lblSpek);

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("ID Laptop     :");

        lblIdLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdLaptop.setForeground(new java.awt.Color(255, 255, 255));
        lblIdLaptop.setText("-");

        lblOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblOwner.setForeground(new java.awt.Color(255, 255, 255));
        lblOwner.setText("-");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("ID Owner      :");

        btnOrder1.setBackground(new java.awt.Color(51, 58, 72));
        btnOrder1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOrder1.setForeground(new java.awt.Color(255, 255, 255));
        btnOrder1.setText("Process");
        btnOrder1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOrder1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOrder1MouseReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("RP");

        lblCariNoTelp.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblCariNoTelp.setForeground(new java.awt.Color(255, 255, 255));

        btnCalc.setText("Calc");
        btnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcActionPerformed(evt);
            }
        });

        lblJumlahHari.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pn_orderLaptopLayout = new javax.swing.GroupLayout(pn_orderLaptop);
        pn_orderLaptop.setLayout(pn_orderLaptopLayout);
        pn_orderLaptopLayout.setHorizontalGroup(
            pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lblTahun, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblModel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblMerk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel63)
                                            .addComponent(jLabel67)))
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHargaSewaHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOwner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIdLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(351, 351, 351))
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(869, 869, 869))
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(txtCariNoTelpClient, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel30)
                                    .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                                .addComponent(jLabel31)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                                .addComponent(lblJumlahHari, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(dateKembali, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                            .addComponent(dateAmbil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNamaClient))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblCariNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pn_orderLaptopLayout.setVerticalGroup(
            pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel63))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel67)))
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addComponent(lblIdLaptop)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblOwner)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(lblMerk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblModel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTahun)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(lblHargaSewaHarian)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCariNoTelpClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCariNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel34)
                            .addComponent(lblIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32)
                            .addComponent(dateAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(dateKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJumlahHari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel37)
                                .addComponent(btnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel31)
                            .addComponent(lblTotalHarga))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOrder1)
                        .addGap(24, 24, 24))))
        );

        pn_clientMenu.add(pn_orderLaptop, "card2");

        pn_client.add(pn_clientMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_client, "card3");

        pn_data.setLayout(new java.awt.BorderLayout());

        pn_dataButton.setBackground(new java.awt.Color(26, 26, 26));

        btnDataOwner.setBackground(new java.awt.Color(107, 125, 253));
        btnDataOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDataOwner.setForeground(new java.awt.Color(255, 255, 255));
        btnDataOwner.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\user-list.png")); // NOI18N
        btnDataOwner.setText("Data Owner");
        btnDataOwner.setBorderPainted(false);
        btnDataOwner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDataOwner.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDataOwner.setIconTextGap(15);
        btnDataOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataOwnerActionPerformed(evt);
            }
        });

        btnDataTransaksi.setBackground(new java.awt.Color(51, 58, 72));
        btnDataTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDataTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnDataTransaksi.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\notepad.png")); // NOI18N
        btnDataTransaksi.setText("Data Transaksi");
        btnDataTransaksi.setBorderPainted(false);
        btnDataTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDataTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDataTransaksi.setIconTextGap(15);
        btnDataTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataTransaksiActionPerformed(evt);
            }
        });

        btnDataLaptop.setBackground(new java.awt.Color(51, 58, 72));
        btnDataLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDataLaptop.setForeground(new java.awt.Color(255, 255, 255));
        btnDataLaptop.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\laptop.png")); // NOI18N
        btnDataLaptop.setText("Data Laptop");
        btnDataLaptop.setBorderPainted(false);
        btnDataLaptop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDataLaptop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDataLaptop.setIconTextGap(15);
        btnDataLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataLaptopActionPerformed(evt);
            }
        });

        btnDataClient.setBackground(new java.awt.Color(51, 58, 72));
        btnDataClient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDataClient.setForeground(new java.awt.Color(255, 255, 255));
        btnDataClient.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\users.png")); // NOI18N
        btnDataClient.setText("Data Client");
        btnDataClient.setBorderPainted(false);
        btnDataClient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDataClient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDataClient.setIconTextGap(15);
        btnDataClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_dataButtonLayout = new javax.swing.GroupLayout(pn_dataButton);
        pn_dataButton.setLayout(pn_dataButtonLayout);
        pn_dataButtonLayout.setHorizontalGroup(
            pn_dataButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDataOwner)
                .addGap(18, 18, 18)
                .addComponent(btnDataLaptop)
                .addGap(18, 18, 18)
                .addComponent(btnDataClient)
                .addGap(18, 18, 18)
                .addComponent(btnDataTransaksi)
                .addContainerGap(296, Short.MAX_VALUE))
        );
        pn_dataButtonLayout.setVerticalGroup(
            pn_dataButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dataButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDataOwner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDataTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDataClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDataLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_data.add(pn_dataButton, java.awt.BorderLayout.PAGE_START);

        pn_dataMenu.setLayout(new java.awt.CardLayout());

        pn_dataOwner.setBackground(new java.awt.Color(26, 26, 26));

        jPanel6.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("DATA OWNER");

        tblOwner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Id Owner", "Nama", "Telepon", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOwner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOwnerMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblOwner);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Nama");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Telepon");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Alamat");

        txtDataAlamatOwner.setColumns(20);
        txtDataAlamatOwner.setRows(5);
        jScrollPane7.setViewportView(txtDataAlamatOwner);

        btnDataOwnerUpdate.setText("Update");
        btnDataOwnerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataOwnerUpdateActionPerformed(evt);
            }
        });

        btnDataOwnerDelete.setText("Delete");
        btnDataOwnerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataOwnerDeleteActionPerformed(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\magnifying-glass.png")); // NOI18N

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Id Owner");

        javax.swing.GroupLayout pn_dataOwnerLayout = new javax.swing.GroupLayout(pn_dataOwner);
        pn_dataOwner.setLayout(pn_dataOwnerLayout);
        pn_dataOwnerLayout.setHorizontalGroup(
            pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6))
                    .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43)
                            .addComponent(jLabel72))
                        .addGap(19, 19, 19)
                        .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataTeleponOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataNamaOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                                .addComponent(btnDataOwnerUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDataOwnerDelete))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                                .addComponent(txtDataIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(623, 623, 623))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pn_dataOwnerLayout.setVerticalGroup(
            pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addComponent(txtDataIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtDataNamaOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtDataTeleponOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDataOwnerUpdate)
                    .addComponent(btnDataOwnerDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pn_dataMenu.add(pn_dataOwner, "card2");

        pn_dataClient.setBackground(new java.awt.Color(26, 26, 26));

        jPanel9.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("DATA CLIENT");

        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id Client", "Nama", "Telepon", "Alamat"
            }
        ));
        jScrollPane8.setViewportView(tblClient);

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Nama");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Telepon");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Alamat");

        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jScrollPane9.setViewportView(jTextArea6);

        jButton6.setText("Update");

        jButton7.setText("Delete");

        jLabel52.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\magnifying-glass.png")); // NOI18N

        javax.swing.GroupLayout pn_dataClientLayout = new javax.swing.GroupLayout(pn_dataClient);
        pn_dataClient.setLayout(pn_dataClientLayout);
        pn_dataClientLayout.setHorizontalGroup(
            pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataClientLayout.createSequentialGroup()
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataClientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataClientLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel52)
                                .addGap(12, 12, 12))))
                    .addGroup(pn_dataClientLayout.createSequentialGroup()
                        .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_dataClientLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel48))
                            .addGroup(pn_dataClientLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel51))
                                .addGap(27, 27, 27)
                                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pn_dataClientLayout.createSequentialGroup()
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7))
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pn_dataClientLayout.setVerticalGroup(
            pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataClientLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_dataMenu.add(pn_dataClient, "card2");

        pn_dataLaptop.setBackground(new java.awt.Color(26, 26, 26));

        jPanel10.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("DATA LAPTOP");

        tblLaptop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Laptop", "Id Owner", "Merk", "Model", "Spesifikasi", "Tahun", "Harga sewa/hari"
            }
        ));
        jScrollPane10.setViewportView(tblLaptop);

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Merk");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Model");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Spesifikasi");

        jTextArea7.setColumns(20);
        jTextArea7.setRows(5);
        jScrollPane11.setViewportView(jTextArea7);

        jButton8.setText("Update");

        jButton9.setText("Delete");

        jLabel57.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\magnifying-glass.png")); // NOI18N

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Tahun");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Harga sewa/hari");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Id Owner");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Id Laptop");

        javax.swing.GroupLayout pn_dataLaptopLayout = new javax.swing.GroupLayout(pn_dataLaptop);
        pn_dataLaptop.setLayout(pn_dataLaptopLayout);
        pn_dataLaptopLayout.setHorizontalGroup(
            pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataLaptopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataLaptopLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel57)
                                .addGap(12, 12, 12))))
                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel53))
                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel55)
                                            .addComponent(jLabel58))
                                        .addGap(77, 77, 77)
                                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextField23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(114, 114, 114))
                                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel56))
                                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                                .addComponent(jLabel59)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                        .addComponent(jButton8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton9)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pn_dataLaptopLayout.setVerticalGroup(
            pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton9))
                        .addGap(79, 79, 79)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pn_dataMenu.add(pn_dataLaptop, "card2");

        pn_dataTransaksi.setBackground(new java.awt.Color(26, 26, 26));

        jPanel11.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("DATA TRANSAKSI");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(jTable4);

        jLabel66.setIcon(new javax.swing.ImageIcon("E:\\Netbeans Project\\ProjekRental\\icon\\magnifying-glass.png")); // NOI18N

        javax.swing.GroupLayout pn_dataTransaksiLayout = new javax.swing.GroupLayout(pn_dataTransaksi);
        pn_dataTransaksi.setLayout(pn_dataTransaksiLayout);
        pn_dataTransaksiLayout.setHorizontalGroup(
            pn_dataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataTransaksiLayout.createSequentialGroup()
                .addGroup(pn_dataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataTransaksiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE))
                    .addGroup(pn_dataTransaksiLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel62)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataTransaksiLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66)
                .addGap(17, 17, 17))
        );
        pn_dataTransaksiLayout.setVerticalGroup(
            pn_dataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataTransaksiLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel62)
                .addGap(25, 25, 25)
                .addGroup(pn_dataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_dataMenu.add(pn_dataTransaksi, "card2");

        pn_data.add(pn_dataMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_data, "card4");

        getContentPane().add(pn_menu, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1175, 692));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //merubah warna tombol new dan mengganti menu panel
    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        //merubah warna tombol new
        btnNew.setBackground(new Color(0x6B7DFD));
        btnClient.setBackground(new Color(0x333A48));
        btnData.setBackground(new Color(0x333A48));
        //merubah warna tombol new
        
        //menghapus semua komponen pada panel
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        //menghapus semua komponen pada panel
        
        //menambahkan komponen pada panel
        pn_menu.add(pn_new);
        pn_menu.repaint();
        pn_menu.revalidate();
        //menambahkan komponen pada panel
    }//GEN-LAST:event_btnNewActionPerformed
    //merubah warna tombol new dan mengganti menu panel
    
    //merubah warna tombol client dan mengganti menu panel
    private void btnClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientActionPerformed
        // TODO add your handling code here:
        btnClient.setBackground(new Color(0x6B7DFD));
        btnNew.setBackground(new Color(0x333A48));
        btnData.setBackground(new Color(0x333A48));
        
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        
        pn_menu.add(pn_client);
        pn_menu.repaint();
        pn_menu.revalidate();
    }//GEN-LAST:event_btnClientActionPerformed
    //merubah warna tombol client dan mengganti menu panel
    
    private void btnDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataActionPerformed
        // TODO add your handling code here:
        btnData.setBackground(new Color(0x6B7DFD));
        btnNew.setBackground(new Color(0x333A48));
        btnClient.setBackground(new Color(0x333A48));
        
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        
        pn_menu.add(pn_data);
        pn_menu.repaint();
        pn_menu.revalidate();
    }//GEN-LAST:event_btnDataActionPerformed

    private void btnAddOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOwnerActionPerformed
        // TODO add your handling code here:
        btnAddLaptop.setBackground(new Color(0x333A48));
        btnAddOwner.setBackground(new Color(0x6b7dfd));
     
        pn_newMenu.removeAll();
        pn_newMenu.repaint();
        pn_newMenu.revalidate();
        
        pn_newMenu.add(pn_addOwner);
        pn_newMenu.repaint();
        pn_newMenu.revalidate();
        
    }//GEN-LAST:event_btnAddOwnerActionPerformed

    private void btnAddLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLaptopActionPerformed
        // TODO add your handling code here:
        btnAddOwner.setBackground(new Color(0x333A48));
        btnAddLaptop.setBackground(new Color(0x6b7dfd));
        
        pn_newMenu.removeAll();
        pn_newMenu.repaint();
        pn_newMenu.revalidate();
        
        pn_newMenu.add(pn_addLaptop);
        pn_newMenu.repaint();
        pn_newMenu.revalidate();
    }//GEN-LAST:event_btnAddLaptopActionPerformed

    private void btnAddClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClientActionPerformed
        // TODO add your handling code here:
        btnOrderLaptop.setBackground(new Color(0x333A48));
        btnAddClient.setBackground(new Color(0x6b7dfd));
     
        pn_clientMenu.removeAll();
        pn_clientMenu.repaint();
        pn_clientMenu.revalidate();
        
        pn_clientMenu.add(pn_addClient);
        pn_clientMenu.repaint();
        pn_clientMenu.revalidate();
    }//GEN-LAST:event_btnAddClientActionPerformed

    private void btnOrderLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderLaptopActionPerformed
        // TODO add your handling code here:
        btnOrderLaptop.setBackground(new Color(0x6b7dfd));
        btnAddClient.setBackground(new Color(0x333A48));
     
        pn_clientMenu.removeAll();
        pn_clientMenu.repaint();
        pn_clientMenu.revalidate();
        
        pn_clientMenu.add(pn_orderLaptop);
        pn_clientMenu.repaint();
        pn_clientMenu.revalidate();
        
    }//GEN-LAST:event_btnOrderLaptopActionPerformed

    private void btnDataOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataOwnerActionPerformed
        // TODO add your handling code here:
        btnDataOwner.setBackground(new Color(0x6b7dfd)); //biru
        btnDataTransaksi.setBackground(new Color(0x333A48));
        btnDataLaptop.setBackground(new Color(0x333A48));
        btnDataClient.setBackground(new Color(0x333A48));
        
        pn_dataMenu.removeAll();
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
        
        pn_dataMenu.add(pn_dataOwner);
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
     

    }//GEN-LAST:event_btnDataOwnerActionPerformed

    private void btnDataTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataTransaksiActionPerformed
        // TODO add your handling code here:
        btnDataOwner.setBackground(new Color(0x333A48)); 
        btnDataTransaksi.setBackground(new Color(0x6b7dfd)); //biru
        btnDataLaptop.setBackground(new Color(0x333A48));
        btnDataClient.setBackground(new Color(0x333A48));
        
        pn_dataMenu.removeAll();
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
        
        pn_dataMenu.add(pn_dataTransaksi);
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
    }//GEN-LAST:event_btnDataTransaksiActionPerformed

    private void btnDataLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataLaptopActionPerformed
        // TODO add your handling code here:
        btnDataOwner.setBackground(new Color(0x333A48)); 
        btnDataTransaksi.setBackground(new Color(0x333A48));
        btnDataLaptop.setBackground(new Color(0x6b7dfd)); //biru
        btnDataClient.setBackground(new Color(0x333A48));
        
        pn_dataMenu.removeAll();
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
        
        pn_dataMenu.add(pn_dataLaptop);
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
    }//GEN-LAST:event_btnDataLaptopActionPerformed

    private void btnDataClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataClientActionPerformed
        // TODO add your handling code here:
        btnDataOwner.setBackground(new Color(0x333A48)); 
        btnDataTransaksi.setBackground(new Color(0x333A48));
        btnDataLaptop.setBackground(new Color(0x333A48));
        btnDataClient.setBackground(new Color(0x6b7dfd)); //biru
        
        pn_dataMenu.removeAll();
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
        
        pn_dataMenu.add(pn_dataClient);
        pn_dataMenu.repaint();
        pn_dataMenu.revalidate();
    }//GEN-LAST:event_btnDataClientActionPerformed
    
    //METHOD ADD OWNER
    private void btnNewOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewOwnerActionPerformed
        // TODO add your handling code here:
        String idOwner = txtNewIdOwner.getText();
        String namaOwner = txtNewNamaOwner.getText();
        String teleponOwner = txtNewTeleponOwner.getText();
        String alamatOwner = txaNewAlamatOwner.getText();
        if (namaOwner.isEmpty() || teleponOwner.isEmpty() || alamatOwner.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form diatas");
            }
        
        try {          
            
                String sql = "INSERT INTO owner(id_owner,nama_owner,telepon_owner,alamat_owner) VALUES (?,?,?,?)";
                con = Koneksi.getKoneksi();
                ps = con.prepareStatement(sql);
                ps.setString(1, idOwner);
                ps.setString(2, namaOwner);
                ps.setString(3, teleponOwner);
                ps.setString(4, alamatOwner);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Owner Berhasil didaftarkan");
           
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataOwner();
        autoKodeOwner();
        addOwnerBersih();
        
        
    }//GEN-LAST:event_btnNewOwnerActionPerformed

    //method agar textfield telepon hanya bisa di input angka
    private void txtNewTeleponOwnerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewTeleponOwnerKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            lblOwnerTelepon.setText("masukkan angka");
            evt.consume();
        }else{
            lblOwnerTelepon.setText("");
        }
    }//GEN-LAST:event_txtNewTeleponOwnerKeyTyped
    //method agar textfield telepon hanya bisa di input angka
    
    //METHOD ADD LAPTOP
    private void btnNewAddLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAddLaptopActionPerformed
        // TODO add your handling code here:
        try {
            String namaOwner = txtNamaOwnerLaptop.getText();
            String idOwner = lblIdOwner.getText();
            String idLaptop = txtNewIdLaptop.getText();
            String merk = txtNewMerk.getText();
            String model = txtNewModel.getText();
            String spesifikasi = txtNewSpesifikasi.getText();
            String hargaSewa = txtNewHargaSewa.getText();
            int tahun = spNewTahun.getYear();
            
            if(namaOwner.isEmpty() ||merk.isEmpty() || model.isEmpty() || spesifikasi.isEmpty() || hargaSewa.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form diatas");
            }else{
                String sql = "INSERT INTO laptop(id_laptop,id_owner,merk_laptop,model_laptop,spesifikasi_laptop,tahun_laptop,harga_sewa) VALUES (?,?,?,?,?,?,?)";           
                con = Koneksi.getKoneksi();
                ps = con.prepareStatement(sql);
                ps.setString(1, idLaptop);
                ps.setString(2, idOwner);
                ps.setString(3, merk);
                ps.setString(4, model);
                ps.setString(5, spesifikasi);
                ps.setInt(6, tahun);
                ps.setString(7, hargaSewa);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Laptop berhasil ditambahkan");
            }
            autoKodeLaptop();
            getDataLaptop();
            addLaptopBersih();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnNewAddLaptopActionPerformed

    //METHOD CARI TELEPON OWNER
    private void txtCariTeleponKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariTeleponKeyReleased
        // TODO add your handling code here:
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT id_owner, nama_owner, telepon_owner FROM owner WHERE telepon_owner=?");
            ps.setString(1, txtCariTelepon.getText());
            rs = ps.executeQuery();
            
            if(rs.next() == false){
                lblCari.setText("data tidak ditemukan");
                lblIdOwner.setText("");
                txtNamaOwnerLaptop.setText("");
                if(txtCariTelepon.getText().isEmpty()){
                    lblCari.setText("");
                }
            }else{
                lblCari.setText("data ditemukan");
                lblIdOwner.setText(rs.getString("id_owner"));
                txtNamaOwnerLaptop.setText(rs.getString("nama_owner"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtCariTeleponKeyReleased
    
    //METHOD TEXTFIELD HARGA SEWA AGAR CUMA BISA DIINPUT ANGKA
    private void txtNewHargaSewaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewHargaSewaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)){
            lblHargaSewaAngka.setText("Masukkan angka");
            evt.consume();
            if(txtNewHargaSewa.getText().isEmpty()){
                lblHargaSewaAngka.setText("");
            }
        }else{
            lblHargaSewaAngka.setText("");
        }
    }//GEN-LAST:event_txtNewHargaSewaKeyTyped

    private void btnOrder1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrder1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOrder1MousePressed

    private void btnOrder1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrder1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOrder1MouseReleased

    //METHOD ADD CLIENT
    private void btnAddClientFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClientFormActionPerformed
        // TODO add your handling code here:       
        try {
            String id = txtAddIdClient.getText();
            String nama = txtAddNamaClient.getText();
            String telepon = txtAddTeleponClient.getText();
            String alamat = txtAddAlamatClient.getText();
            
            if(id.isEmpty() || nama.isEmpty() || telepon.isEmpty() || alamat.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form");
            }else{
                String sql = "INSERT INTO client(id_client,nama_client,telepon_client,alamat_client) VALUES (?,?,?,?)";
                con = Koneksi.getKoneksi();
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, nama);
                ps.setString(3, telepon);
                ps.setString(4, alamat);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Berhasil ditambahkan");
            }
            autoKodeClient();
            addClientBersih();
            getDataClient();
                  
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnAddClientFormActionPerformed

    
    //METHOD TEXTFIELD ADD TELEPON CLIENT AGAR CUMA BISA DIINPUT ANGKA
    private void txtAddTeleponClientKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddTeleponClientKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            lblTeleponAddClient.setText("masukkan angka");
            evt.consume();
        }else{
            lblTeleponAddClient.setText("");
        }
    }//GEN-LAST:event_txtAddTeleponClientKeyTyped

    
    //METHOD MENAMPILKAN SEMUA DATA OWNER KE TEXTFIELD UNTUK MENGUPDATE/HAPUS DATA 
    private void tblOwnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOwnerMouseClicked
        // TODO add your handling code here:
        txtDataIdOwner.setText(tblOwner.getValueAt(tblOwner.getSelectedRow(), 1).toString());
        txtDataNamaOwner.setText(tblOwner.getValueAt(tblOwner.getSelectedRow(), 2).toString());
        txtDataTeleponOwner.setText(tblOwner.getValueAt(tblOwner.getSelectedRow(), 3).toString());
        txtDataAlamatOwner.setText(tblOwner.getValueAt(tblOwner.getSelectedRow(), 4).toString());
    }//GEN-LAST:event_tblOwnerMouseClicked

    
    //METHOD UPDATE DATA OWNER
    private void btnDataOwnerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataOwnerUpdateActionPerformed
        // TODO add your handling code here:
        try {
            String id = txtDataIdOwner.getText();
            String nama = txtDataNamaOwner.getText();
            String telepon = txtDataTeleponOwner.getText();
            String alamat = txtDataAlamatOwner.getText();
            
            if(nama.isEmpty() || telepon.isEmpty() || alamat.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form");
            }else{
                String sql = "UPDATE owner SET nama_owner='"+nama+"', telepon_owner='"+telepon+"', alamat_owner='"+alamat+"' WHERE id_owner='"+id+"'";
                con = Koneksi.getKoneksi();
                ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(this, "Data owner berhasil di update");
            }
            getDataOwner();
            dataOwnerBersih();
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, e.getMessage());
        }
        
        
    }//GEN-LAST:event_btnDataOwnerUpdateActionPerformed

    //METHOD DELETE DATA OWNER
    private void btnDataOwnerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataOwnerDeleteActionPerformed
        // TODO add your handling code here:
        try {
            String id = txtDataIdOwner.getText();
            con = Koneksi.getKoneksi();
            String sql = "DELETE FROM owner WHERE id_owner='"+id+"'";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Berhasil Dihapus");
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataOwner();
        dataOwnerBersih();
    }//GEN-LAST:event_btnDataOwnerDeleteActionPerformed

    
    //METHOD YANG TERJADI JIKA LIST LAPTOP DI CLICK
    private void listLaptopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listLaptopMouseClicked
        // TODO add your handling code here:
        try {
            if(evt.getClickCount() == 1){
                String selectedModel = listLaptop.getSelectedValue();
                String merk = getMerk(selectedModel);
                lblMerk.setText(merk);
            
                String model = getModel(selectedModel);
                lblModel.setText(model);
                
                String idLaptop = getIdLaptop(selectedModel);
                lblIdLaptop.setText(idLaptop);
                
                String tahun = getTahun(selectedModel);
                lblTahun.setText(tahun);
                
                String idOwner = getIdOwner(selectedModel);
                lblOwner.setText(idOwner);
                
                String spek = getSpek(selectedModel);
                lblSpek.setText(spek);
                
                String harga = getHarga(selectedModel);
                lblHargaSewaHarian.setText(harga);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_listLaptopMouseClicked

    //Menampilkan nama client dari kolom pencarian yang ada di panel order laptop
    private void txtCariNoTelpClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariNoTelpClientKeyReleased
        // TODO add your handling code here:
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("SELECT id_client, nama_client, telepon_client FROM client WHERE telepon_client=?");
            ps.setString(1, txtCariNoTelpClient.getText());
            rs = ps.executeQuery();
            
            if(rs.next() == false){
                lblCariNoTelp.setText("data tidak ditemukan");
                lblIdClient.setText("");
                txtNamaClient.setText("");
                if(txtCariTelepon.getText().isEmpty()){
                    lblCariNoTelp.setText("");
                }
            }else{
                lblCariNoTelp.setText("data ditemukan");
                lblIdClient.setText(rs.getString("id_client"));
                txtNamaClient.setText(rs.getString("nama_client"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtCariNoTelpClientKeyReleased
    //Menampilkan nama client dari kolom pencarian yang ada di panel order laptop
    
    //Mencari jarak hari dari kedua 
    private void btnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcActionPerformed
        // TODO add your handling code here:
        LocalDate tanggal1 = dateAmbil.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate tanggal2 = dateKembali.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        long jarakHari = ChronoUnit.DAYS.between(tanggal1, tanggal2);
        
        String jarak = String.valueOf(jarakHari);
        lblJumlahHari.setText(jarak);
        
        int hargaSewa = Integer.parseInt(lblHargaSewaHarian.getText());
        int totalHarga = hargaSewa * Integer.parseInt(lblJumlahHari.getText());
        
        lblTotalHarga.setText(Integer.toString(totalHarga));
        

        
    }//GEN-LAST:event_btnCalcActionPerformed
    //Mencari jarak hari dari kedua 
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddClient;
    private javax.swing.JButton btnAddClientForm;
    private javax.swing.JButton btnAddLaptop;
    private javax.swing.JButton btnAddOwner;
    private javax.swing.JButton btnCalc;
    private javax.swing.JButton btnClient;
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnDataClient;
    private javax.swing.JButton btnDataLaptop;
    private javax.swing.JButton btnDataOwner;
    private javax.swing.JButton btnDataOwnerDelete;
    private javax.swing.JButton btnDataOwnerUpdate;
    private javax.swing.JButton btnDataTransaksi;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNewAddLaptop;
    private javax.swing.JButton btnNewOwner;
    private javax.swing.JButton btnOrder1;
    private javax.swing.JButton btnOrderLaptop;
    private com.toedter.calendar.JDateChooser dateAmbil;
    private com.toedter.calendar.JDateChooser dateKembali;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JLabel lblCari;
    private javax.swing.JLabel lblCariNoTelp;
    private javax.swing.JLabel lblHargaSewaAngka;
    private javax.swing.JLabel lblHargaSewaHarian;
    private javax.swing.JLabel lblIdClient;
    private javax.swing.JLabel lblIdLaptop;
    private javax.swing.JLabel lblIdOwner;
    private javax.swing.JLabel lblJumlahHari;
    private javax.swing.JLabel lblMerk;
    private javax.swing.JLabel lblModel;
    private javax.swing.JLabel lblOwner;
    private javax.swing.JLabel lblOwnerTelepon;
    private javax.swing.JTextArea lblSpek;
    private javax.swing.JLabel lblTahun;
    private javax.swing.JLabel lblTeleponAddClient;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JList<String> listLaptop;
    private javax.swing.JPanel pn_addClient;
    private javax.swing.JPanel pn_addLaptop;
    private javax.swing.JPanel pn_addOwner;
    private javax.swing.JPanel pn_client;
    private javax.swing.JPanel pn_clientButton;
    private javax.swing.JPanel pn_clientMenu;
    private javax.swing.JPanel pn_dashboard;
    private javax.swing.JPanel pn_data;
    private javax.swing.JPanel pn_dataButton;
    private javax.swing.JPanel pn_dataClient;
    private javax.swing.JPanel pn_dataLaptop;
    private javax.swing.JPanel pn_dataMenu;
    private javax.swing.JPanel pn_dataOwner;
    private javax.swing.JPanel pn_dataTransaksi;
    private javax.swing.JPanel pn_menu;
    private javax.swing.JPanel pn_new;
    private javax.swing.JPanel pn_newButton;
    private javax.swing.JPanel pn_newMenu;
    private javax.swing.JPanel pn_orderLaptop;
    private javax.swing.JPanel pn_title;
    private com.toedter.calendar.JYearChooser spNewTahun;
    private javax.swing.JTable tblClient;
    private javax.swing.JTable tblLaptop;
    private javax.swing.JTable tblOwner;
    private javax.swing.JTextArea txaNewAlamatOwner;
    private javax.swing.JTextArea txtAddAlamatClient;
    private javax.swing.JTextField txtAddIdClient;
    private javax.swing.JTextField txtAddNamaClient;
    private javax.swing.JTextField txtAddTeleponClient;
    private javax.swing.JTextField txtCariNoTelpClient;
    private javax.swing.JTextField txtCariTelepon;
    private javax.swing.JTextArea txtDataAlamatOwner;
    private javax.swing.JTextField txtDataIdOwner;
    private javax.swing.JTextField txtDataNamaOwner;
    private javax.swing.JTextField txtDataTeleponOwner;
    private javax.swing.JTextField txtNamaClient;
    private javax.swing.JTextField txtNamaOwnerLaptop;
    private javax.swing.JTextField txtNewHargaSewa;
    private javax.swing.JTextField txtNewIdLaptop;
    private javax.swing.JTextField txtNewIdOwner;
    private javax.swing.JTextField txtNewMerk;
    private javax.swing.JTextField txtNewModel;
    private javax.swing.JTextField txtNewNamaOwner;
    private javax.swing.JTextArea txtNewSpesifikasi;
    private javax.swing.JTextField txtNewTeleponOwner;
    // End of variables declaration//GEN-END:variables
}
