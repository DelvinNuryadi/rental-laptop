/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.projekrental;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author me
 */
public class mainForm extends javax.swing.JFrame {
    private Connection con;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private Timer timer;

    /**
     * Creates new form mainForm
     */
    
    public mainForm() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getDataOwner();
        getDataLaptop();
        getDataClient();
        getDataInvoice();
        getDataMerk();
        getListLaptop();
        autoKodeId();
        tglTransaksi(new Date());
    
    }
//-------------------METHOD TIMER--------------------------//
    //method untuk menghentikan timer
    private void stopTimer(){
       timer.stop();
        
    }
    //method untuk menghentikan waktu saat jframe ditutup
    @Override
    public void dispose(){
        stopTimer();
        super.dispose();
    }
    //method untuk menghentikan waktu saat jframe ditutup
    //method untuk menghentikan timer
//-------------------METHOD TIMER--------------------------//
    
 
//-------------------METHOD LOAD/MENGAMBIL/MENAMPILKAN DATA--------------------------//
    //load data owner
    private void getDataOwner(){
        try {
            if(txtCariOwner.getText().isEmpty()){
               DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No");
                model.addColumn("ID Owner");
                model.addColumn("Nama");
                model.addColumn("Telepon");
                model.addColumn("Alamat");
                int no = 0;
       
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
            }else{
                cariOwner();
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
       
    }
    //load data owner
    
    //load data order
    private void getDataInvoice(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("NO");
            model.addColumn("ID Order");
            model.addColumn("ID Client");
            model.addColumn("ID Laptop");
            model.addColumn("Tanggal transaksi");
            model.addColumn("Tanggal ambil");
            model.addColumn("Tanggal kembali");
            model.addColumn("Total bayar");    
            int no = 0;
            
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM invoice");
            
            while(rs.next()){
                Object[] obj = new Object[8];
                obj[0] = no++;
                obj[1] = rs.getString("id_order");
                obj[2] = rs.getString("id_client");
                obj[3] = rs.getString("id_laptop");
                obj[4] = rs.getString("tanggal_transaksi");
                obj[5] = rs.getString("tanggal_ambil");
                obj[6] = rs.getString("tanggal_kembali");
                obj[7] = rs.getString("total_bayar");
               
                model.addRow(obj);
                tblOrder.setModel(model);
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load data order
    
    //load data laptop
    private void getDataLaptop(){
        try {
            if(txtCariLaptop.getText().isEmpty()){
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("NO");
                model.addColumn("ID Owner");
                model.addColumn("ID Laptop"); 
                model.addColumn("Merk");
                model.addColumn("Model");
                model.addColumn("Spesifikasi");
                model.addColumn("Tahun");
                model.addColumn("Harga sewa/hari (Rp)");
                int no = 0;
            
                con = Koneksi.getKoneksi();
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM laptop");
            
                while(rs.next()){
                Object[] obj = new Object[8];
                obj[0] = no++;
                obj[1] = rs.getString("id_owner");
                obj[2] = rs.getString("id_laptop");
                obj[3] = rs.getString("merk_laptop");
                obj[4] = rs.getString("model_laptop");
                obj[5] = rs.getString("spesifikasi_laptop");
                obj[6] = rs.getString("tahun_laptop");
                obj[7] = rs.getString("harga_sewa");
                model.addRow(obj);
                tblLaptop.setModel(model);
                }  
            }else{
                cariLaptop();
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load data laptop
    
    //load data merk ke combo box
    private void getDataMerk(){
        try {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            con = Koneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM merk");
            
            while(rs.next()){
                model.addElement(rs.getString("merk_laptop"));
                
            }
            cmbDataMerk.setModel(model);
            cmbMerk.setModel(model);
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //load data merk ke combo box
    
    
    
    //load data client
    private void getDataClient(){
        try {
            if(txtCariClient.getText().isEmpty()){
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No");
                model.addColumn("ID Client");
                model.addColumn("Nama");
                model.addColumn("Telepon");
                model.addColumn("Alamat");
                int no = 0;

            
                con = Koneksi.getKoneksi();
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM client");
            
                while(rs.next()){
                    Object[] obj = new Object[5];
                    obj[0] = no++;
                    obj[1] = rs.getString("id_client");
                    obj[2] = rs.getString("nama_client");
                    obj[3] = rs.getString("telepon_client");
                    obj[4] = rs.getString("alamat_client");
                
                    model.addRow(obj);
                    tblClient.setModel(model); 
                }
            
            }else{
                cariClient();
            }
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return result;
    }
    //load spesifikasi laptop dari list laptop
//-------------------METHOD LOAD/MENGAMBIL/MENAMPILKAN DATA--------------------------//
    
    

//--------------------------METHOD GENERATING ID CODE------------------------------//  
    //menampilkan kode id diseluruh texfield id
    private void autoKodeId(){
        timer = new Timer(1000, (ActionEvent e) -> {
            //Mengubah nilai pada JTextField dengan nilai ID transaksi saat ini
            txtNewIdLaptop.setText(generateIdLaptop());
            txtNewIdOwner.setText(generateIdOwner());
            txtAddIdClient.setText(generateIdClient());
            txtIdInvoice.setText(generateIdInvoice());
        });
        timer.start();   
    }
    //menampilkan kode id diseluruh texfield id
    
    
    //generate id owner
    private String generateIdOwner(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return "OWN" + now.format(dtf);      
    }
    //generate id owner
    
    //generate id Laptop
    private String generateIdLaptop(){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            return "LPT" + now.format(dtf);          
    } 
    //generate id Laptop

    //generate id client
    private String generateIdClient(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return "CLN" + now.format(dtf);  
            
    }
    //generate id client
        
    //generate id invoice
    private String generateIdInvoice(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return "INV" + now.format(dtf);  
    }
    //generate id invoice
//--------------------------METHOD GENERATING ID CODE------------------------------// 
    
    
    
//--------------------------METHOD MEMBERSIHKAN TEXTFIELD DKK------------------------------//    
    //Bersihkan textfield di add owner
    private void addOwnerBersih(){
        txtNewNamaOwner.setText("");
        txtNewTeleponOwner.setText("");
        txaNewAlamatOwner.setText("");
    }
    //Bersihkan textfield di add owner
    
    //bersihkan textfield di add laptop
    private void addLaptopBersih(){
        lblIdOwner.setText("");
        txtNamaOwnerLaptop.setText("");
        txtCariTelepon.setText("");
        cmbMerk.setSelectedIndex(0);
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
    
    //Bersihkan textfield dan label di panel order laptop
    private void orderLaptopBersih(){
        lblMerk.setText("-");
        lblModel.setText("-");
        lblTahun.setText("-");
        lblSpek.setText("-");
        lblIdLaptop.setText("-");
        lblOwner.setText("-");
        lblHargaSewaHarian.setText("-");
        txtCariNoTelpClient.setText("");
        dateAmbil.setDate(null);
        dateKembali.setDate(null);
        lblJumlahHari.setText("-");
        lblTotalHarga.setText("-");
    }
    //Bersihkan textfield dan label di panel order laptop
    
    //Bersihkan textfield di panel data client
    private void dataClientBersih(){
        txtDataIdClient.setText("");
        txtDataNamaClient.setText("");
        txtDataTeleponClient.setText("");
        txtDataAlamatClient.setText("");
    }
    //Bersihkan textfield di panel data client
    
    //Bersihkan textField di panel data laptop
    private void dataLaptopBersih(){
        txtDataIdOwnerLaptop.setText("");
        txtDataIdLaptop.setText("");
        cmbDataMerk.setSelectedIndex(0);
        txtDataModelLaptop.setText("");
        spDataTahunLaptop.setYear(Calendar.getInstance().get(Calendar.YEAR));
        txtDataHargaSewaLaptop.setText("");
        txtDataSpekLaptop.setText("");
    }
    //Bersihkan textField di panel data laptop
//--------------------------METHOD MEMBERSIHKAN TEXTFIELD DKK------------------------------//   
    
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
        lblWelcome = new javax.swing.JLabel();
        btnData = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnClient = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        pn_menu = new javax.swing.JPanel();
        pn_login = new javax.swing.JPanel();
        pn_loginButton = new javax.swing.JPanel();
        btnSignInPanel = new javax.swing.JButton();
        btnSignUpPanel = new javax.swing.JButton();
        pn_loginMenu = new javax.swing.JPanel();
        pn_signin = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnSignin = new javax.swing.JButton();
        txtSignInPassword = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSignInUsername = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblValidasiSignIn = new javax.swing.JLabel();
        pn_signup = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnSignup = new javax.swing.JButton();
        txtSignUpPassword = new javax.swing.JPasswordField();
        jLabel28 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtSignUpUsername = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lblValidasiSignUp = new javax.swing.JLabel();
        pn_new = new javax.swing.JPanel();
        pn_newButton = new javax.swing.JPanel();
        btnAddOwner = new javax.swing.JButton();
        btnAddLaptop = new javax.swing.JButton();
        pn_newMenu = new javax.swing.JPanel();
        pn_addOwner = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblOwnerTelepon = new javax.swing.JLabel();
        btnNewOwner = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaNewAlamatOwner = new javax.swing.JTextArea();
        txtNewTeleponOwner = new javax.swing.JTextField();
        txtNewNamaOwner = new javax.swing.JTextField();
        txtNewIdOwner = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        pn_addLaptop = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaOwnerLaptop = new javax.swing.JTextField();
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
        cmbMerk = new javax.swing.JComboBox<>();
        pn_client = new javax.swing.JPanel();
        pn_clientButton = new javax.swing.JPanel();
        btnAddClient = new javax.swing.JButton();
        btnOrderLaptop = new javax.swing.JButton();
        pn_clientMenu = new javax.swing.JPanel();
        pn_addClient = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
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
        txtNamaClient = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        lblIdLaptop = new javax.swing.JLabel();
        lblOwner = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblCariNoTelp = new javax.swing.JLabel();
        lblJumlahHari = new javax.swing.JLabel();
        txtIdInvoice = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lblSpek = new javax.swing.JTextArea();
        jLabel73 = new javax.swing.JLabel();
        tglTransaksi = new javax.swing.JLabel();
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
        txtCariOwner = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtDataIdOwner = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        pn_dataClient = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        txtDataNamaClient = new javax.swing.JTextField();
        txtDataTeleponClient = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtDataAlamatClient = new javax.swing.JTextArea();
        btnDataClientUpdate = new javax.swing.JButton();
        btnDataClientDelete = new javax.swing.JButton();
        txtCariClient = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtDataIdClient = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        pn_dataLaptop = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblLaptop = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        txtDataModelLaptop = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtDataSpekLaptop = new javax.swing.JTextArea();
        btnDataLaptopUpdate = new javax.swing.JButton();
        btnDataLaptopDelete = new javax.swing.JButton();
        txtCariLaptop = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtDataHargaSewaLaptop = new javax.swing.JTextField();
        txtDataIdOwnerLaptop = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtDataIdLaptop = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        spDataTahunLaptop = new com.toedter.calendar.JYearChooser();
        cmbDataMerk = new javax.swing.JComboBox<>();
        pn_dataTransaksi = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        jTextField26 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        pn_logout = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

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

        jLabel2.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\Logo.png")); // NOI18N

        lblWelcome.setFont(new java.awt.Font("Lato", 1, 18)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(159, 222, 249));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        btnData.setBackground(new java.awt.Color(51, 58, 72));
        btnData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnData.setForeground(new java.awt.Color(255, 255, 255));
        btnData.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnData.png")); // NOI18N
        btnData.setText("DATA");
        btnData.setBorderPainted(false);
        btnData.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnData.setEnabled(false);
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
        btnNew.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnNew.png")); // NOI18N
        btnNew.setText("NEW");
        btnNew.setBorderPainted(false);
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNew.setEnabled(false);
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
        btnClient.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnClient.png")); // NOI18N
        btnClient.setText("CLIENT");
        btnClient.setBorderPainted(false);
        btnClient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClient.setEnabled(false);
        btnClient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnClient.setIconTextGap(30);
        btnClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(28, 121, 0));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(56, 217, 123));
        btnLogin.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\sign-in-bold-green-24px.png")); // NOI18N
        btnLogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(46, 204, 113), 3, true));
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setIconTextGap(30);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Lato", 1, 18)); // NOI18N
        lblUser.setForeground(new java.awt.Color(159, 222, 249));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnLogOut.setBackground(new java.awt.Color(28, 36, 52));
        btnLogOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(28, 36, 52));
        btnLogOut.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\sign-out-bold-red-24px.png")); // NOI18N
        btnLogOut.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(247, 8, 8), 3, true));
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogOut.setIconTextGap(30);
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_dashboardLayout = new javax.swing.GroupLayout(pn_dashboard);
        pn_dashboard.setLayout(pn_dashboardLayout);
        pn_dashboardLayout.setHorizontalGroup(
            pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnClient, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnData, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_dashboardLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_dashboardLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel2))))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        pn_dashboardLayout.setVerticalGroup(
            pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dashboardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnClient, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnData, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addGroup(pn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        getContentPane().add(pn_dashboard, java.awt.BorderLayout.LINE_START);

        pn_menu.setLayout(new java.awt.CardLayout());

        pn_login.setLayout(new java.awt.BorderLayout());

        pn_loginButton.setBackground(new java.awt.Color(26, 26, 26));

        btnSignInPanel.setBackground(new java.awt.Color(107, 125, 253));
        btnSignInPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSignInPanel.setForeground(new java.awt.Color(255, 255, 255));
        btnSignInPanel.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnSignIN.png")); // NOI18N
        btnSignInPanel.setText("Sign In");
        btnSignInPanel.setBorderPainted(false);
        btnSignInPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSignInPanel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSignInPanel.setIconTextGap(15);
        btnSignInPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInPanelActionPerformed(evt);
            }
        });

        btnSignUpPanel.setBackground(new java.awt.Color(51, 58, 72));
        btnSignUpPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSignUpPanel.setForeground(new java.awt.Color(255, 255, 255));
        btnSignUpPanel.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnSignUp-white-regular-24px.png")); // NOI18N
        btnSignUpPanel.setText("Sign Up");
        btnSignUpPanel.setBorderPainted(false);
        btnSignUpPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSignUpPanel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSignUpPanel.setIconTextGap(15);
        btnSignUpPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_loginButtonLayout = new javax.swing.GroupLayout(pn_loginButton);
        pn_loginButton.setLayout(pn_loginButtonLayout);
        pn_loginButtonLayout.setHorizontalGroup(
            pn_loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_loginButtonLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnSignInPanel)
                .addGap(18, 18, 18)
                .addComponent(btnSignUpPanel)
                .addGap(0, 689, Short.MAX_VALUE))
        );
        pn_loginButtonLayout.setVerticalGroup(
            pn_loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_loginButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignInPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignUpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_login.add(pn_loginButton, java.awt.BorderLayout.PAGE_START);

        pn_loginMenu.setLayout(new java.awt.CardLayout());

        pn_signin.setBackground(new java.awt.Color(26, 26, 26));

        jPanel12.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(28, 33, 67));

        btnSignin.setBackground(new java.awt.Color(0, 168, 255));
        btnSignin.setForeground(new java.awt.Color(255, 255, 255));
        btnSignin.setText("Sign in");
        btnSignin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigninActionPerformed(evt);
            }
        });

        txtSignInPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSignInPasswordKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Password");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Username");

        txtSignInUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSignInUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSignInUsernameKeyPressed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("SIGN IN");

        jLabel3.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\Logo.png")); // NOI18N

        lblValidasiSignIn.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel65))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSignInUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(txtSignInPassword)
                            .addComponent(lblValidasiSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSignin, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel65)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtSignInUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSignInPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValidasiSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_signinLayout = new javax.swing.GroupLayout(pn_signin);
        pn_signin.setLayout(pn_signinLayout);
        pn_signinLayout.setHorizontalGroup(
            pn_signinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_signinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_signinLayout.setVerticalGroup(
            pn_signinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_signinLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 212, Short.MAX_VALUE))
        );

        pn_loginMenu.add(pn_signin, "card2");

        pn_signup.setBackground(new java.awt.Color(26, 26, 26));

        jPanel13.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(28, 33, 67));

        btnSignup.setBackground(new java.awt.Color(0, 168, 255));
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sign Up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        txtSignUpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSignUpPasswordKeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Password");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Username");

        txtSignUpUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSignUpUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSignUpUsernameKeyPressed(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("SIGN UP");

        jLabel39.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\Logo.png")); // NOI18N

        lblValidasiSignUp.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel71))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSignUpUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(txtSignUpPassword)
                            .addComponent(lblValidasiSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel71)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtSignUpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSignUpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValidasiSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignup)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_signupLayout = new javax.swing.GroupLayout(pn_signup);
        pn_signup.setLayout(pn_signupLayout);
        pn_signupLayout.setHorizontalGroup(
            pn_signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_signupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_signupLayout.setVerticalGroup(
            pn_signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_signupLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        pn_loginMenu.add(pn_signup, "card2");

        pn_login.add(pn_loginMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_login, "card2");

        pn_new.setLayout(new java.awt.BorderLayout());

        pn_newButton.setBackground(new java.awt.Color(26, 26, 26));

        btnAddOwner.setBackground(new java.awt.Color(107, 125, 253));
        btnAddOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddOwner.setForeground(new java.awt.Color(255, 255, 255));
        btnAddOwner.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\addOwner client.png")); // NOI18N
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
        btnAddLaptop.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\laptop.png")); // NOI18N
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
                .addGap(0, 641, Short.MAX_VALUE))
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

        jPanel2.setBackground(new java.awt.Color(28, 33, 67));

        lblOwnerTelepon.setForeground(new java.awt.Color(255, 0, 51));

        btnNewOwner.setText("add");
        btnNewOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewOwnerActionPerformed(evt);
            }
        });

        txaNewAlamatOwner.setColumns(20);
        txaNewAlamatOwner.setRows(5);
        jScrollPane1.setViewportView(txaNewAlamatOwner);

        txtNewTeleponOwner.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewTeleponOwnerKeyTyped(evt);
            }
        });

        txtNewIdOwner.setEditable(false);
        txtNewIdOwner.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNewIdOwner.setEnabled(false);

        jLabel68.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Id Owner");

        jLabel4.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama");

        jLabel5.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Telepon");

        jLabel6.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Alamat");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("ADD OWNER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNewTeleponOwner)
                                .addComponent(txtNewNamaOwner, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(btnNewOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(lblOwnerTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtNewIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txtNewIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNewNamaOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewTeleponOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOwnerTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewOwner)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_addOwnerLayout = new javax.swing.GroupLayout(pn_addOwner);
        pn_addOwner.setLayout(pn_addOwnerLayout);
        pn_addOwnerLayout.setHorizontalGroup(
            pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(516, Short.MAX_VALUE))
        );
        pn_addOwnerLayout.setVerticalGroup(
            pn_addOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addOwnerLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 186, Short.MAX_VALUE))
        );

        pn_newMenu.add(pn_addOwner, "card2");

        pn_addLaptop.setBackground(new java.awt.Color(26, 26, 26));

        jPanel5.setBackground(new java.awt.Color(25, 25, 112));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(28, 33, 67));

        jLabel7.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama owner");

        txtNamaOwnerLaptop.setEditable(false);
        txtNamaOwnerLaptop.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNamaOwnerLaptop.setEnabled(false);

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

        txtNewIdLaptop.setEditable(false);
        txtNewIdLaptop.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNewIdLaptop.setEnabled(false);
        txtNewIdLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewIdLaptopActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Id Laptop");

        lblHargaSewaAngka.setForeground(new java.awt.Color(255, 0, 51));

        cmbMerk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel45))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtCariTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel10))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel69)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNamaOwnerLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNewIdLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNewModel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(4, 4, 4)
                                        .addComponent(lblIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(spNewTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHargaSewaAngka, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNewHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(169, 169, 169))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(btnNewAddLaptop)
                .addGap(179, 179, 179))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCariTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10)))
                .addGap(6, 6, 6)
                .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNamaOwnerLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel69)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel9)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel11)
                        .addGap(86, 86, 86)
                        .addComponent(jLabel12))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(lblIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNewIdLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spNewTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtNewHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHargaSewaAngka, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNewAddLaptop)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_addLaptopLayout = new javax.swing.GroupLayout(pn_addLaptop);
        pn_addLaptop.setLayout(pn_addLaptopLayout);
        pn_addLaptopLayout.setHorizontalGroup(
            pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(466, Short.MAX_VALUE))
        );
        pn_addLaptopLayout.setVerticalGroup(
            pn_addLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addLaptopLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_newMenu.add(pn_addLaptop, "card2");

        pn_new.add(pn_newMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_new, "card2");

        pn_client.setLayout(new java.awt.BorderLayout());

        pn_clientButton.setBackground(new java.awt.Color(26, 26, 26));

        btnAddClient.setBackground(new java.awt.Color(107, 125, 253));
        btnAddClient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddClient.setForeground(new java.awt.Color(255, 255, 255));
        btnAddClient.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\addOwner client.png")); // NOI18N
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
        btnOrderLaptop.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\laptop.png")); // NOI18N
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
                .addGap(0, 637, Short.MAX_VALUE))
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
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(28, 33, 67));

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

        txtAddIdClient.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAddIdClient.setEnabled(false);

        jLabel70.setFont(new java.awt.Font("Lato Black", 1, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Id Client");

        lblTeleponAddClient.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTeleponAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel46))
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAddClientForm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addGap(62, 62, 62)
                                        .addComponent(txtAddIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(txtAddNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(txtAddTeleponClient, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(txtAddIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(txtAddNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(txtAddTeleponClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(lblTeleponAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddClientForm)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_addClientLayout = new javax.swing.GroupLayout(pn_addClient);
        pn_addClient.setLayout(pn_addClientLayout);
        pn_addClientLayout.setHorizontalGroup(
            pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_addClientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(498, Short.MAX_VALUE))
        );
        pn_addClientLayout.setVerticalGroup(
            pn_addClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_addClientLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 5));

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
        jLabel30.setText("Nama client         ");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 0));
        jLabel31.setText("Rp");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Tanggal ambil");

        dateAmbil.setDateFormatString("dd/MM/yyyy");
        dateAmbil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateAmbilPropertyChange(evt);
            }
        });

        dateKembali.setDateFormatString("dd/MM/yyyy");
        dateKembali.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateKembaliPropertyChange(evt);
            }
        });

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
        jLabel37.setText("Jumlah Hari           :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Total Harga           :");

        lblTotalHarga.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotalHarga.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalHarga.setText("-");

        txtNamaClient.setEditable(false);
        txtNamaClient.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNamaClient.setEnabled(false);

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("ID Laptop            :");

        lblIdLaptop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdLaptop.setForeground(new java.awt.Color(255, 255, 255));
        lblIdLaptop.setText("-");

        lblOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblOwner.setForeground(new java.awt.Color(255, 255, 255));
        lblOwner.setText("-");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("ID Owner             :");

        btnOrder.setBackground(new java.awt.Color(51, 58, 72));
        btnOrder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnOrder.setText("Process");
        btnOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOrderMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOrderMouseReleased(evt);
            }
        });
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("RP");

        lblCariNoTelp.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblCariNoTelp.setForeground(new java.awt.Color(255, 255, 255));

        lblJumlahHari.setForeground(new java.awt.Color(255, 255, 255));
        lblJumlahHari.setText("-");

        txtIdInvoice.setEditable(false);
        txtIdInvoice.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIdInvoice.setEnabled(false);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("ID Order             ");

        jScrollPane5.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane5.setBorder(null);
        jScrollPane5.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane5.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblSpek.setBackground(new java.awt.Color(26, 26, 26));
        lblSpek.setColumns(20);
        lblSpek.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSpek.setForeground(new java.awt.Color(255, 255, 255));
        lblSpek.setLineWrap(true);
        lblSpek.setRows(5);
        lblSpek.setText("-");
        lblSpek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane5.setViewportView(lblSpek);

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Tanggal Transaksi :");

        tglTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tglTransaksi.setForeground(new java.awt.Color(255, 255, 255));

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
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblModel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(32, 32, 32)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel67))
                        .addGap(14, 14, 14)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOwner, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(lblIdLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(150, 150, 150))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(txtCariNoTelpClient, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(30, 30, 30)
                                .addComponent(txtIdInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel37))
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(lblJumlahHari, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel30)
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHargaSewaHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCariNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                    .addComponent(jLabel73)
                                    .addGap(18, 18, 18)
                                    .addComponent(tglTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
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
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(lblMerk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(lblTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pn_orderLaptopLayout.createSequentialGroup()
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblIdLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel67)
                                    .addComponent(lblOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(lblHargaSewaHarian)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCariNoTelpClient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCariNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(lblIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32)
                            .addComponent(dateAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateKembali, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(lblJumlahHari, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addGroup(pn_orderLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrder)
                        .addGap(436, 436, 436))))
        );

        pn_clientMenu.add(pn_orderLaptop, "card2");

        pn_client.add(pn_clientMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_client, "card3");

        pn_data.setLayout(new java.awt.BorderLayout());

        pn_dataButton.setBackground(new java.awt.Color(26, 26, 26));

        btnDataOwner.setBackground(new java.awt.Color(107, 125, 253));
        btnDataOwner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDataOwner.setForeground(new java.awt.Color(255, 255, 255));
        btnDataOwner.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\data owner.png")); // NOI18N
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
        btnDataTransaksi.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\transaksi.png")); // NOI18N
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
        btnDataLaptop.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\laptop.png")); // NOI18N
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
        btnDataClient.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\btnClient.png")); // NOI18N
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
                .addGap(25, 25, 25)
                .addComponent(btnDataOwner)
                .addGap(18, 18, 18)
                .addComponent(btnDataLaptop)
                .addGap(18, 18, 18)
                .addComponent(btnDataClient)
                .addGap(18, 18, 18)
                .addComponent(btnDataTransaksi)
                .addContainerGap(281, Short.MAX_VALUE))
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
                "No", "ID Owner", "Nama", "Telepon", "Alamat"
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

        txtCariOwner.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariOwnerKeyReleased(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\magnifying-glass.png")); // NOI18N

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("ID Owner");

        javax.swing.GroupLayout pn_dataOwnerLayout = new javax.swing.GroupLayout(pn_dataOwner);
        pn_dataOwner.setLayout(pn_dataOwnerLayout);
        pn_dataOwnerLayout.setHorizontalGroup(
            pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE))
                    .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataOwnerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtCariOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(txtDataIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_dataOwnerLayout.setVerticalGroup(
            pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataOwnerLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dataOwnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataIdOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(txtCariOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "ID Client", "Nama", "Telepon", "Alamat"
            }
        ));
        tblClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientMouseClicked(evt);
            }
        });
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

        txtDataAlamatClient.setColumns(20);
        txtDataAlamatClient.setRows(5);
        jScrollPane9.setViewportView(txtDataAlamatClient);

        btnDataClientUpdate.setText("Update");
        btnDataClientUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataClientUpdateActionPerformed(evt);
            }
        });

        btnDataClientDelete.setText("Delete");
        btnDataClientDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataClientDeleteActionPerformed(evt);
            }
        });

        txtCariClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariClientKeyReleased(evt);
            }
        });

        jLabel52.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\magnifying-glass.png")); // NOI18N

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("ID client");

        javax.swing.GroupLayout pn_dataClientLayout = new javax.swing.GroupLayout(pn_dataClient);
        pn_dataClient.setLayout(pn_dataClientLayout);
        pn_dataClientLayout.setHorizontalGroup(
            pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataClientLayout.createSequentialGroup()
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataClientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE))
                    .addGroup(pn_dataClientLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel48)
                        .addGap(0, 861, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataClientLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtCariClient, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel52)))
                .addContainerGap())
            .addGroup(pn_dataClientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataTeleponClient, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_dataClientLayout.createSequentialGroup()
                        .addComponent(btnDataClientUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDataClientDelete))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_dataClientLayout.setVerticalGroup(
            pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dataClientLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtDataIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtDataNamaClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtDataTeleponClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDataClientUpdate)
                    .addComponent(btnDataClientDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pn_dataClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCariClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "ID Owner", "ID Laptop", "Merk", "Model", "Spesifikasi", "Tahun", "Harga sewa/hari (Rp)"
            }
        ));
        tblLaptop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaptopMouseClicked(evt);
            }
        });
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

        txtDataSpekLaptop.setColumns(20);
        txtDataSpekLaptop.setRows(5);
        jScrollPane11.setViewportView(txtDataSpekLaptop);

        btnDataLaptopUpdate.setText("Update");
        btnDataLaptopUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataLaptopUpdateActionPerformed(evt);
            }
        });

        btnDataLaptopDelete.setText("Delete");
        btnDataLaptopDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataLaptopDeleteActionPerformed(evt);
            }
        });

        txtCariLaptop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariLaptopKeyReleased(evt);
            }
        });

        jLabel57.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\magnifying-glass.png")); // NOI18N

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Tahun");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Harga sewa/hari");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("ID Owner");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("ID Laptop");

        cmbDataMerk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pn_dataLaptopLayout = new javax.swing.GroupLayout(pn_dataLaptop);
        pn_dataLaptop.setLayout(pn_dataLaptopLayout);
        pn_dataLaptopLayout.setHorizontalGroup(
            pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataLaptopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE))
                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel53)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                                    .addComponent(txtDataModelLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(txtDataIdLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(txtDataIdOwnerLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(cmbDataMerk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(spDataTahunLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                        .addGap(21, 21, 21)
                                        .addComponent(txtDataHargaSewaLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dataLaptopLayout.createSequentialGroup()
                                .addComponent(txtCariLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel57))
                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                        .addComponent(btnDataLaptopUpdate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDataLaptopDelete)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                            .addComponent(btnDataLaptopUpdate)
                            .addComponent(btnDataLaptopDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCariLaptop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(txtDataIdOwnerLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDataIdLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(cmbDataMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(txtDataModelLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(spDataTahunLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pn_dataLaptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel59))
                            .addGroup(pn_dataLaptopLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDataHargaSewaLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "ID Order", "ID Client", "ID Laptop", "Tanggal transaksi", "Tanggal ambil", "Tanggal kembali", "Total Bayar"
            }
        ));
        jScrollPane12.setViewportView(tblOrder);

        jLabel66.setIcon(new javax.swing.ImageIcon("E:\\Project Netbeans\\ProjekRental\\src\\main\\java\\image\\magnifying-glass.png")); // NOI18N

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
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_dataMenu.add(pn_dataTransaksi, "card2");

        pn_data.add(pn_dataMenu, java.awt.BorderLayout.CENTER);

        pn_menu.add(pn_data, "card4");

        jLabel23.setText("LOGIN UNTUK MEMULAI KEMBALI");

        javax.swing.GroupLayout pn_logoutLayout = new javax.swing.GroupLayout(pn_logout);
        pn_logout.setLayout(pn_logoutLayout);
        pn_logoutLayout.setHorizontalGroup(
            pn_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_logoutLayout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addComponent(jLabel23)
                .addContainerGap(413, Short.MAX_VALUE))
        );
        pn_logoutLayout.setVerticalGroup(
            pn_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_logoutLayout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(jLabel23)
                .addContainerGap(321, Short.MAX_VALUE))
        );

        pn_menu.add(pn_logout, "card6");

        getContentPane().add(pn_menu, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1175, 692));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//--------------------------METHOD MERUBAH WARNA BUTTON DAN MENGGANTI PANEL------------------------------//   
    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        //merubah warna tombol new
        btnNew.setBackground(new Color(0x6B7DFD));//biru
        btnClient.setBackground(new Color(0x333A48));
        btnData.setBackground(new Color(0x333A48));
        btnLogin.setBackground(new Color(0x1C2434));
        btnLogOut.setBackground(new Color(0x1C2434));
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
 
    private void btnClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientActionPerformed
        // TODO add your handling code here:
        btnClient.setBackground(new Color(0x6B7DFD));//biru
        btnNew.setBackground(new Color(0x333A48));
        btnData.setBackground(new Color(0x333A48));
        btnLogin.setBackground(new Color(0x1C2434));
        btnLogOut.setBackground(new Color(0x1C2434));
    
        
        
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        
        pn_menu.add(pn_client);
        pn_menu.repaint();
        pn_menu.revalidate();
    }//GEN-LAST:event_btnClientActionPerformed
  
    private void btnDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataActionPerformed
        // TODO add your handling code here:
        btnData.setBackground(new Color(0x6B7DFD));//biru
        btnNew.setBackground(new Color(0x333A48));
        btnClient.setBackground(new Color(0x333A48));
        btnLogin.setBackground(new Color(0x1C2434));
      
        
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
//--------------------------METHOD MERUBAH WARNA BUTTON DAN MENGGANTI PANEL------------------------------//   
 
 
   //method agar textfield telepon hanya bisa di input angka
    
    //METHOD ADD LAPTOP
    private void btnNewAddLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAddLaptopActionPerformed
        // TODO add your handling code here:
        try {
            String namaOwner = txtNamaOwnerLaptop.getText();
            String idOwner = lblIdOwner.getText();
            String idLaptop = txtNewIdLaptop.getText();
            String merk = cmbMerk.getSelectedItem().toString();
            String model = txtNewModel.getText();
            String spesifikasi = txtNewSpesifikasi.getText();
            String hargaSewa = txtNewHargaSewa.getText();
            int tahun = spNewTahun.getYear();
            
            if(namaOwner.isEmpty() || merk.equals("-") || model.isEmpty() || spesifikasi.isEmpty() || hargaSewa.isEmpty()){
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
            getDataLaptop();
            addLaptopBersih();
            getListLaptop();
            con.close();
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
            con.close();
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

    private void btnOrderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOrderMousePressed

    private void btnOrderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOrderMouseReleased

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
        
            addClientBersih();
            getDataClient();
                  
            con.close();
            
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
            
            
           con.close(); 
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, e.getMessage());
        }
        getDataOwner();
        dataOwnerBersih();
        
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
            
           con.close(); 
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
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtCariNoTelpClientKeyReleased
    //Menampilkan nama client dari kolom pencarian yang ada di panel order laptop
    
   //Mencari jarak hari dari kedua date
    
    
    //METHOD PROSES ORDER DAN MENGHAPUS RECORD LIST LAPTOP
    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        try {
            String idLaptop = lblIdLaptop.getText();
            String idClient = lblIdClient.getText();
            String bayar = lblTotalHarga.getText();
 
            
            if(idLaptop.equals("-") || idClient.isEmpty() || bayar.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form");
            }else{
                updateOrder();
                deleteList();
                orderLaptopBersih();
           
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnOrderActionPerformed
    //METHOD PROSES ORDER DAN MENGHAPUS RECORD LIST LAPTOP
    
    //event ketika tabel laptop diklik akan menampilkan data pada textfield
    private void tblLaptopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaptopMouseClicked
        // TODO add your handling code here:
        try {
            txtDataIdOwnerLaptop.setText(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 1).toString());
            txtDataIdLaptop.setText(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 2).toString());
            cmbDataMerk.setSelectedItem(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 3).toString());
            txtDataModelLaptop.setText(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 4).toString());
            txtDataSpekLaptop.setText(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 5).toString());
            spDataTahunLaptop.setYear(Integer.parseInt(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 6).toString()));
            txtDataHargaSewaLaptop.setText(tblLaptop.getValueAt(tblLaptop.getSelectedRow(), 7).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblLaptopMouseClicked
    //event ketika tabel laptop diklik akan menampilkan data pada textfield
    
    
    //method menampilkan dari tabel client ke textfield yang ada di panel
    private void tblClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientMouseClicked
        // TODO add your handling code here:
        try {
            txtDataIdClient.setText(tblClient.getValueAt(tblClient.getSelectedRow(), 1).toString());
            txtDataNamaClient.setText(tblClient.getValueAt(tblClient.getSelectedRow(), 2).toString());
            txtDataTeleponClient.setText(tblClient.getValueAt(tblClient.getSelectedRow(), 3).toString());
            txtDataAlamatClient.setText(tblClient.getValueAt(tblClient.getSelectedRow(), 4).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblClientMouseClicked
    //method menampilkan dari tabel client ke textfield yang ada di panel
    
    //Mencari pada tabel owner
    private void txtCariOwnerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariOwnerKeyReleased
        // TODO add your handling code here:
        cariOwner();
        
    }//GEN-LAST:event_txtCariOwnerKeyReleased
    //method cari data owner
    private void cariOwner(){
        
        try {
           String keyword = txtCariOwner.getText();
           DefaultTableModel model = new DefaultTableModel();
           model.addColumn("No");
           model.addColumn("ID Owner");
           model.addColumn("Nama");
           model.addColumn("Telepon");
           model.addColumn("Alamat"); 
           int no = 0;
           
           model.setRowCount(0);
           con = Koneksi.getKoneksi();
           st = con.createStatement();
           rs = st.executeQuery("SELECT id_owner, nama_owner, telepon_owner, alamat_owner FROM owner WHERE id_owner LIKE '%"+keyword+"%' OR nama_owner LIKE '%"+keyword+"%' OR telepon_owner LIKE '%"+keyword+"%' OR alamat_owner LIKE '%"+keyword+"%'");
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
           con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //method cari data owner
    //Mencari pada tabel owner
    
    //METHOD UPDATE DATA CLIENT
    private void btnDataClientUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataClientUpdateActionPerformed
        // TODO add your handling code here:
        try {
            String id = txtDataIdClient.getText();
            String nama = txtDataNamaClient.getText();
            String telepon = txtDataTeleponClient.getText();
            String alamat = txtDataAlamatClient.getText();      
            con = Koneksi.getKoneksi();
            
            if(nama.isEmpty() || telepon.isEmpty() || alamat.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lengkapi form");
            }else{
                String sql = "UPDATE client SET nama_client='"+nama+"', telepon_client='"+telepon+"', alamat_client='"+alamat+"' WHERE id_client='"+id+"'";
                ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(this, "Data client berhasil di update");
            }
            
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataClient();
        dataClientBersih();
    }//GEN-LAST:event_btnDataClientUpdateActionPerformed
    //METHOD UPDATE DATA CLIENT
    
    //METHOD HAPUS DATA CLIENT
    private void btnDataClientDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataClientDeleteActionPerformed
        // TODO add your handling code here:
        String id = txtDataIdClient.getText();
        try {
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("DELETE FROM client WHERE id_client='"+id+"'");
            ps.execute();
            JOptionPane.showMessageDialog(this, "data client berhasil dihapus");
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataClient();
        dataClientBersih();
    }//GEN-LAST:event_btnDataClientDeleteActionPerformed
    //METHOD HAPUS DATA CLIENT
    
    //EVENT MENCARI DATA CLIENT
    private void txtCariClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariClientKeyReleased
        // TODO add your handling code here:
        cariClient();
        
    }//GEN-LAST:event_txtCariClientKeyReleased
    //EVENT MENCARI DATA CLIENT
   
    //EVENT update data laptop
    private void btnDataLaptopUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataLaptopUpdateActionPerformed
        // TODO add your handling code here:
        try {
            String idOwner = txtDataIdOwnerLaptop.getText();
            String idLaptop = txtDataIdLaptop.getText();
            String merk = cmbDataMerk.getSelectedItem().toString();
            String model = txtDataModelLaptop.getText();
            String spek = txtDataSpekLaptop.getText();
            int tahun = spDataTahunLaptop.getYear();
            String harga = txtDataHargaSewaLaptop.getText();
            
            String sql = "UPDATE laptop SET id_owner='"+idOwner+"', merk_laptop='"+merk+"', model_laptop='"+model+"', spesifikasi_laptop='"+spek+"', tahun_laptop='"+tahun+"', harga_sewa='"+harga+"' WHERE id_laptop='"+idLaptop+"'";
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data laptop berhasil di update");
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataLaptop();
        dataLaptopBersih();
    }//GEN-LAST:event_btnDataLaptopUpdateActionPerformed
    //EVENT update data laptop
    
    //EVENT hapus data laptop
    private void btnDataLaptopDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataLaptopDeleteActionPerformed
        // TODO add your handling code here:
        try {
            String id = txtDataIdLaptop.getText();
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("DELETE FROM laptop WHERE id_laptop='"+id+"'");
            ps.execute();
            JOptionPane.showMessageDialog(this, "data client berhasil dihapus");
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataLaptop();
        dataLaptopBersih();
    }//GEN-LAST:event_btnDataLaptopDeleteActionPerformed
    //EVENT hapus data laptop
    
    //EVENT CARI DATA LAPTOP
    private void txtCariLaptopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariLaptopKeyReleased
        // TODO add your handling code here:
        cariLaptop();
    }//GEN-LAST:event_txtCariLaptopKeyReleased
    //EVENT CARI DATA LAPTOP
    
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
            con.close();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataOwner();
        addOwnerBersih();
        txtCariOwner.setText("");
    }//GEN-LAST:event_btnNewOwnerActionPerformed

    //add nomor telepon hanya bisa di input angka                                        
    private void txtNewTeleponOwnerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewTeleponOwnerKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            lblOwnerTelepon.setText("masukkan angka");
            evt.consume();
        }else{
            lblOwnerTelepon.setText("");
        }
    }
    //add nomor telepon hanya bisa di input angka  
//GEN-LAST:event_txtNewTeleponOwnerKeyTyped

    //event untuk warna button login dan mengganti panel
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        btnData.setBackground(new Color(0x333A48));
        btnNew.setBackground(new Color(0x333A48));
        btnClient.setBackground(new Color(0x333A48));
        btnLogin.setBackground(new Color(0x1C7900));//green
        btnLogOut.setBackground(new Color(0x1C2434));
      
       
        
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        
        pn_menu.add(pn_login);
        pn_menu.repaint();
        pn_menu.revalidate();
        
        txtSignInUsername.requestFocus();
    }//GEN-LAST:event_btnLoginActionPerformed
    //event untuk warna button login dan mengganti panel
    
    //method ketika tombol sign in ditekan, warna button berubah dan panel berganti
    private void btnSignInPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInPanelActionPerformed
        // TODO add your handling code here:
        btnSignInPanel.setBackground(new Color(0x6B7DFD));//biru
        btnSignUpPanel.setBackground(new Color(0x333A48));//grey
        
        pn_loginMenu.removeAll();
        pn_loginMenu.repaint();
        pn_loginMenu.revalidate();
        
        pn_loginMenu.add(pn_signin);
        pn_loginMenu.repaint();
        pn_loginMenu.revalidate();
    
    }//GEN-LAST:event_btnSignInPanelActionPerformed
    //method ketika tombol sign in ditekan, warna button berubah dan panel berganti
    
    //method ketika tombol sign up ditekan, warna button berubah dan panel berganti
    private void btnSignUpPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpPanelActionPerformed
        // TODO add your handling code here:
        btnSignInPanel.setBackground(new Color(0x333A48));//grey
        btnSignUpPanel.setBackground(new Color(0x6B7DFD));//biru
        
        pn_loginMenu.removeAll();
        pn_loginMenu.repaint();
        pn_loginMenu.revalidate();
        
        pn_loginMenu.add(pn_signup);
        pn_loginMenu.repaint();
        pn_loginMenu.revalidate();
    }//GEN-LAST:event_btnSignUpPanelActionPerformed
    //method ketika tombol sign up ditekan, warna button berubah dan panel berganti
    
   //method login aplikasi
    private void signIn(){
        try {
            
                String user = txtSignInUsername.getText();
                String pass = String.valueOf(txtSignInPassword.getPassword());
                con = Koneksi.getKoneksi();
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM user WHERE username='"+user+"' AND password='"+pass+"'");
                if(rs.next()){
                    btnNew.setEnabled(true);
                    btnClient.setEnabled(true);
                    btnData.setEnabled(true);
                    lblUser.setText(user);
                    lblWelcome.setText("Hello,");
                    txtSignInUsername.setText("");
                    txtSignInPassword.setText("");
                    lblValidasiSignIn.setText("");
                    txtSignInUsername.setEnabled(false);
                    txtSignInPassword.setEnabled(false);
                    btnSignin.setEnabled(false);
                    
                    btnNew.setBackground(new Color(0x6B7DFD));//biru
                    btnLogin.setBackground(new Color(0x1C2434));
                    
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
                    

                }else{
                    lblValidasiSignIn.setText("Username atau Password tidak terdaftar");
                }
                con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
   //method login aplikasi

   //method sign up orang baru
    private void signUp(){
        try {
            String user = txtSignUpUsername.getText();
            String pass = String.valueOf(txtSignUpPassword.getPassword());
            if(user.isEmpty() || pass.isEmpty()){
                lblValidasiSignUp.setText("Lengkapi form");
            }else{
                con = Koneksi.getKoneksi();
                ps = con.prepareStatement("INSERT INTO user(username,password) VALUES (?,?)");
                ps.setString(1, user);
                ps.setString(2, pass);
                ps.executeUpdate();
                txtSignUpUsername.setText("");
                txtSignUpPassword.setText("");
                JOptionPane.showMessageDialog(this, "Berhasil terdaftar");
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
   //method sign up orang baru
    
    
    
    //method yang terjadi jika tombol logout ditekan
    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        btnData.setBackground(new Color(0x333A48));
        btnNew.setBackground(new Color(0x333A48));
        btnClient.setBackground(new Color(0x333A48));
        btnLogin.setBackground(new Color(0x1C2434));
        btnLogOut.setBackground(new Color(0x720000));//red
        
        //menghapus semua komponen pada panel
        pn_menu.removeAll();
        pn_menu.repaint();
        pn_menu.revalidate();
        //menghapus semua komponen pada panel
        
        //menambahkan komponen pada panel
        pn_menu.add(pn_logout);
        pn_menu.repaint();
        pn_menu.revalidate();
        //menambahkan komponen pada panel
        
        btnNew.setEnabled(false);
        btnClient.setEnabled(false);
        btnData.setEnabled(false);
        lblUser.setText("Beristirahat!");
        lblWelcome.setText("Selamat,");
        txtSignInUsername.setText("");
        txtSignInPassword.setText("");
        lblValidasiSignIn.setText("");
        txtSignInUsername.setEnabled(true);
        txtSignInPassword.setEnabled(true);
        btnSignin.setEnabled(true);
    }//GEN-LAST:event_btnLogOutActionPerformed
    //method yang terjadi jika tombol logout ditekan
    
    //jalankan method login/signIn() ketika tombol enter ditekan
    private void txtSignInPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignInPasswordKeyPressed
        // TODO add your handling code here:
        try {
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                signIn();
                txtSignInPassword.transferFocus();
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtSignInPasswordKeyPressed

    //jalankan method login/signIn() ketika button sign in ditekan
    private void btnSigninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSigninActionPerformed
        // TODO add your handling code here:
        try {
            signIn();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSigninActionPerformed

    //pindah fokus ke textfield password ketika enter ditekan saat fokus di username textfield
    private void txtSignInUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignInUsernameKeyPressed
        // TODO add your handling code here:
        try {
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                txtSignInPassword.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtSignInUsernameKeyPressed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSignupActionPerformed

    //method langsung sign up ketika password sudah dilengkapi dan membersihkan textfield
    private void txtSignUpPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignUpPasswordKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            signUp();
            
        }
    }//GEN-LAST:event_txtSignUpPasswordKeyPressed

    //event pindah fokus dari sign up username ke password setelah tekan enter
    private void txtSignUpUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignUpUsernameKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            txtSignUpPassword.requestFocus();
        }
    }//GEN-LAST:event_txtSignUpUsernameKeyPressed

    //menggunakan method jarakHari() pada variabel dateAmbil
    private void dateAmbilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateAmbilPropertyChange
        // TODO add your handling code here:
        jarakHari();
    }//GEN-LAST:event_dateAmbilPropertyChange

    //menggunakan method jarakHari() pada variabel dateKembali
    private void dateKembaliPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateKembaliPropertyChange
        // TODO add your handling code here:
        jarakHari();
    }//GEN-LAST:event_dateKembaliPropertyChange

    //fokus kursor langung ke textfield sign in saat dirun
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        txtSignInUsername.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void txtNewIdLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewIdLaptopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewIdLaptopActionPerformed

    //tampilkan tanggal sekarang
    private void tglTransaksi(Date tanggal) {
        SimpleDateFormat formatterTanggal = new SimpleDateFormat("dd/MM/yyyy");
        
        tglTransaksi.setText(formatterTanggal.format(tanggal));
    }
    //tampilkan tanggal sekarang

        

    //METHOD MENCARI DATA CLIENT
    private void cariClient(){
        try {
           String keyword = txtCariClient.getText();
           DefaultTableModel model = new DefaultTableModel();
           model.addColumn("No");
           model.addColumn("ID Client");
           model.addColumn("Nama");
           model.addColumn("Telepon");
           model.addColumn("Alamat"); 
           int no = 0;
           
           model.setRowCount(0);
           con = Koneksi.getKoneksi();
           st = con.createStatement();
           rs = st.executeQuery("SELECT id_client, nama_client, telepon_client, alamat_client FROM client WHERE id_client LIKE '%"+keyword+"%' OR nama_client LIKE '%"+keyword+"%' OR telepon_client LIKE '%"+keyword+"%' OR alamat_client LIKE '%"+keyword+"%'");
           while(rs.next()){
               Object[] obj = new Object[5];
                obj[0] = no++;
                obj[1] = rs.getString("id_client");
                obj[2] = rs.getString("nama_client");
                obj[3] = rs.getString("telepon_client");
                obj[4] = rs.getString("alamat_client");
                
                model.addRow(obj);
                tblClient.setModel(model);
                
           }
           con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //METHOD MENCARI DATA CLIENT

    //METHOD MENCARI DATA LAPTOP
    private void cariLaptop(){
        try {
           String keyword = txtCariLaptop.getText();
           DefaultTableModel model = new DefaultTableModel();
           model.addColumn("NO");
           model.addColumn("ID Owner");
           model.addColumn("ID Laptop"); 
           model.addColumn("Merk");
           model.addColumn("Model");
           model.addColumn("Spesifikasi");
           model.addColumn("Tahun");
           model.addColumn("Harga sewa/hari (Rp)");
           int no = 0;
           
           model.setRowCount(0);
           con = Koneksi.getKoneksi();
           st = con.createStatement();
           rs = st.executeQuery("SELECT id_owner, id_laptop, merk_laptop, model_laptop, spesifikasi_laptop, tahun_laptop, harga_sewa FROM laptop WHERE id_laptop LIKE '%"+keyword+"%' OR merk_laptop LIKE '%"+keyword+"%' OR id_owner LIKE '%"+keyword+"%' OR model_laptop LIKE '%"+keyword+"%'");
           while(rs.next()){
                Object[] obj = new Object[8];
                obj[0] = no++;
                obj[1] = rs.getString("id_owner");
                obj[2] = rs.getString("id_laptop");
                obj[3] = rs.getString("merk_laptop");
                obj[4] = rs.getString("model_laptop");
                obj[5] = rs.getString("spesifikasi_laptop");
                obj[6] = rs.getString("tahun_laptop");
                obj[7] = rs.getString("harga_sewa");
                model.addRow(obj);
                tblLaptop.setModel(model);
                
           }
           con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //METHOD MENCARI DATA LAPTOP
    
    
    //METHOD UPDATE ORDER
    private void updateOrder(){
        try {
            String idOrder = txtIdInvoice.getText();
            String idLaptop = lblIdLaptop.getText();
            String idClient = lblIdClient.getText();
            String tgl = tglTransaksi.getText();
            String bayar = lblTotalHarga.getText();
            
            Date tanggal1 = dateAmbil.getDate();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            String strTanggal1 = formatter1.format(tanggal1);
            
            Date tanggal2 = dateKembali.getDate();
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            String strTanggal2 = formatter2.format(tanggal2);
            
            String sql1 = "INSERT INTO invoice(id_order, id_client, id_laptop, tanggal_transaksi,tanggal_ambil , tanggal_kembali, total_bayar) VALUES (?,?,?,?,?,?,?)";
                
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement(sql1);
            ps.setString(1, idOrder);
            ps.setString(2, idClient);
            ps.setString(3, idLaptop);
            ps.setString(4, tgl);
            ps.setString(5, strTanggal1);
            ps.setString(6, strTanggal2);
            ps.setString(7, bayar);
            ps.executeUpdate();
                
                
            JOptionPane.showMessageDialog(this, "berhasil membuat orderan");
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getDataInvoice();
    }
    //METHOD UPDATE ORDER
    
    //METHOD UNTUK MENGHAPUS RECORD LIST LAPTOP KETIKA DI TEKAN TOMBOL
    private void deleteList(){
        try {
            String model = lblModel.getText();
            
            con = Koneksi.getKoneksi();
            ps = con.prepareStatement("DELETE FROM laptop WHERE model_laptop='"+model+"'");
            ps.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        getListLaptop();
    }
    //METHOD UNTUK MENGHAPUS RECORD LIST LAPTOP KETIKA DI TEKAN TOMBOL

    
    //method mencari jarak hari
    private void jarakHari(){
        try {
            
            Date tanggal1 = dateAmbil.getDate();
            Date tanggal2 = dateKembali.getDate();
            if (tanggal1  != null && tanggal2 != null && lblHargaSewaHarian.getText()!= "-") {
                LocalDate tanggalAmbil = dateAmbil.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalDate tanggalKembali = dateKembali.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                long diff = ChronoUnit.DAYS.between(tanggalAmbil, tanggalKembali);
                lblJumlahHari.setText(String.valueOf(diff));
                int harga = Integer.parseInt(lblHargaSewaHarian.getText());
                int totalHarga = harga * Integer.parseInt(lblJumlahHari.getText());
                lblTotalHarga.setText(String.valueOf(totalHarga));
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    //method mencari jarak hari
    
    
    
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
    private javax.swing.JButton btnClient;
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnDataClient;
    private javax.swing.JButton btnDataClientDelete;
    private javax.swing.JButton btnDataClientUpdate;
    private javax.swing.JButton btnDataLaptop;
    private javax.swing.JButton btnDataLaptopDelete;
    private javax.swing.JButton btnDataLaptopUpdate;
    private javax.swing.JButton btnDataOwner;
    private javax.swing.JButton btnDataOwnerDelete;
    private javax.swing.JButton btnDataOwnerUpdate;
    private javax.swing.JButton btnDataTransaksi;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNewAddLaptop;
    private javax.swing.JButton btnNewOwner;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnOrderLaptop;
    private javax.swing.JButton btnSignInPanel;
    private javax.swing.JButton btnSignUpPanel;
    private javax.swing.JButton btnSignin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JComboBox<String> cmbDataMerk;
    private javax.swing.JComboBox<String> cmbMerk;
    private com.toedter.calendar.JDateChooser dateAmbil;
    private com.toedter.calendar.JDateChooser dateKembali;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
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
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblValidasiSignIn;
    private javax.swing.JLabel lblValidasiSignUp;
    private javax.swing.JLabel lblWelcome;
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
    private javax.swing.JPanel pn_login;
    private javax.swing.JPanel pn_loginButton;
    private javax.swing.JPanel pn_loginMenu;
    private javax.swing.JPanel pn_logout;
    private javax.swing.JPanel pn_menu;
    private javax.swing.JPanel pn_new;
    private javax.swing.JPanel pn_newButton;
    private javax.swing.JPanel pn_newMenu;
    private javax.swing.JPanel pn_orderLaptop;
    private javax.swing.JPanel pn_signin;
    private javax.swing.JPanel pn_signup;
    private javax.swing.JPanel pn_title;
    private com.toedter.calendar.JYearChooser spDataTahunLaptop;
    private com.toedter.calendar.JYearChooser spNewTahun;
    private javax.swing.JTable tblClient;
    private javax.swing.JTable tblLaptop;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTable tblOwner;
    private javax.swing.JLabel tglTransaksi;
    private javax.swing.JTextArea txaNewAlamatOwner;
    private javax.swing.JTextArea txtAddAlamatClient;
    private javax.swing.JTextField txtAddIdClient;
    private javax.swing.JTextField txtAddNamaClient;
    private javax.swing.JTextField txtAddTeleponClient;
    private javax.swing.JTextField txtCariClient;
    private javax.swing.JTextField txtCariLaptop;
    private javax.swing.JTextField txtCariNoTelpClient;
    private javax.swing.JTextField txtCariOwner;
    private javax.swing.JTextField txtCariTelepon;
    private javax.swing.JTextArea txtDataAlamatClient;
    private javax.swing.JTextArea txtDataAlamatOwner;
    private javax.swing.JTextField txtDataHargaSewaLaptop;
    private javax.swing.JTextField txtDataIdClient;
    private javax.swing.JTextField txtDataIdLaptop;
    private javax.swing.JTextField txtDataIdOwner;
    private javax.swing.JTextField txtDataIdOwnerLaptop;
    private javax.swing.JTextField txtDataModelLaptop;
    private javax.swing.JTextField txtDataNamaClient;
    private javax.swing.JTextField txtDataNamaOwner;
    private javax.swing.JTextArea txtDataSpekLaptop;
    private javax.swing.JTextField txtDataTeleponClient;
    private javax.swing.JTextField txtDataTeleponOwner;
    private javax.swing.JTextField txtIdInvoice;
    private javax.swing.JTextField txtNamaClient;
    private javax.swing.JTextField txtNamaOwnerLaptop;
    private javax.swing.JTextField txtNewHargaSewa;
    private javax.swing.JTextField txtNewIdLaptop;
    private javax.swing.JTextField txtNewIdOwner;
    private javax.swing.JTextField txtNewModel;
    private javax.swing.JTextField txtNewNamaOwner;
    private javax.swing.JTextArea txtNewSpesifikasi;
    private javax.swing.JTextField txtNewTeleponOwner;
    private javax.swing.JPasswordField txtSignInPassword;
    private javax.swing.JTextField txtSignInUsername;
    private javax.swing.JPasswordField txtSignUpPassword;
    private javax.swing.JTextField txtSignUpUsername;
    // End of variables declaration//GEN-END:variables
}
