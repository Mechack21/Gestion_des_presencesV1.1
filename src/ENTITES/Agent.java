/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITES;

import CONNEXIONS.Connexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MECHACK
 */
public class Agent {

    String mat, nom, postnom, prenom, sexe, telephone, email, idgrade, iddiv, lienphoto;

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdgrade() {
        return idgrade;
    }

    public void setIdgrade(String idgrade) {
        this.idgrade = idgrade;
    }

    public String getIddiv() {
        return iddiv;
    }

    public void setIddiv(String iddiv) {
        this.iddiv = iddiv;
    }

    public String getLienphoto() {
        return lienphoto;
    }

    public void setLienphoto(String lienphoto) {
        this.lienphoto = lienphoto;
    }
    Connexion cnx = new Connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into t_agent(matricule,nom,postnom,prenom,sexe,telephone,email,photo,idgrade,iddiv) values(?,?,?,?,?,?,?,?,?,?)";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getMat());
            pst.setString(2, getNom());
            pst.setString(3, getPostnom());
            pst.setString(4, getPrenom());
            pst.setString(5, getSexe());
            pst.setString(6, getTelephone());
            pst.setString(7, getEmail());
            //Photo
            File file = new File(getLienphoto());
            FileInputStream fis = new FileInputStream(file);
            long len = (long) file.length();
            pst.setBinaryStream(8, fis, len);
            
            //pst.setString(8, getLienphoto());
            pst.setString(9, getIdgrade());
            pst.setString(10, getIddiv());
            
            
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Modifier() {
        try {
            String req = "update t_agent set nom=?, postnom=?,prenom=?,sexe=?,telephone=?,email=?,photo=?,idgrade=?,iddiv=? where matricule=?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getNom());
            pst.setString(2, getPostnom());
            pst.setString(3, getPrenom());
            pst.setString(4, getSexe());
            pst.setString(5, getTelephone());
            pst.setString(6, getEmail());
            
            //Photo
            File file = new File(getLienphoto());
            FileInputStream fis = new FileInputStream(file);
            long len = (long) file.length();
            pst.setBinaryStream(7, fis, len);
            
            pst.setString(8, getIdgrade());
            pst.setString(9, getIddiv());
            pst.setString(10, getMat());
            pst.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void Supprimer() {
        try {
            String req = "delete from  t_agent where matricule=?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getMat());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remplir(JTable jt, String req) {
        try {
            pst = cnx.connec.prepareStatement(req);
            Resultat = pst.executeQuery();
            int n = 0;
            while (Resultat.next()) {
                n++;
            }

            pst = cnx.connec.prepareStatement(req);
            Resultat = pst.executeQuery();
            Object data[][] = new Object[n][10];
            n = 0;
            while (Resultat.next()) {
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getString(3);
                data[n][3] = Resultat.getString(4);
                data[n][4] = Resultat.getString(5);
                data[n][5] = Resultat.getString(6);
                data[n][6] = Resultat.getString(7);
                data[n][7] = Resultat.getString(9);
                data[n][8] = Resultat.getString(10);
                n++;
            }
            String titre[] = {"Matricule", "Nom", "Postnom", "Prenom", "Sexe", "Tél.", "Email", "Grade", "Division"};

            jt.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getImagePhoto(String index, JLabel lblIMAGE) {
        try {
            String req = "select photo from t_agent where matricule = ? ";
            byte[] img = null;
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, index);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {

                img = Resultat.getBytes(1);
            }
            TraitementImage.ByteVersImageAffichageLabel(null, img, lblIMAGE,156,151);
        } catch (SQLException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SaveOnDISCPhoto(String index) {
        try {
            String req = "select matricule,photo from t_agent where matricule = ?";
            byte[] img = null;
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, index);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {
                setMat(Resultat.getString(1));
                img = Resultat.getBytes(2);
            }
            OutputStream fichier = new FileOutputStream(getMat()+ ".jpg");
            String lien = getMat()+ ".jpg";
            //lien = lien.replace("\\", "/");
            setLienphoto(lien);
            fichier.write(img);
            fichier.close();

        } catch (Exception ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void DeleteFile(){
        
        try {
            File file = new File(getMat() + ".jpg");
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void ChargerInfos(String matr) {
        try {
            String req = "select * from t_agent where matricule = ?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, matr);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {
                setMat(Resultat.getString(1));
                setNom(Resultat.getString(2));
                setPostnom(Resultat.getString(3));
                setPrenom(Resultat.getString(4));
                setSexe(Resultat.getString(5));
                setTelephone(Resultat.getString(6));
                setEmail(Resultat.getString(7));
                setIdgrade(Resultat.getString(9));
                setIddiv(Resultat.getString(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
