/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITES;

import CONNEXIONS.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MECHACK
 */
public class Directions {

    String iddirectn, libdirectn;

    public String getIddirectn() {
        return iddirectn;
    }

    public void setIddirectn(String iddirectn) {
        this.iddirectn = iddirectn;
    }

    public String getLibdirectn() {
        return libdirectn;
    }

    public void setLibdirectn(String libdirectn) {
        this.libdirectn = libdirectn;
    }
    Connexion cnx = new Connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into t_direction(iddirect,libdirect) values(?,?)";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getIddirectn());
            pst.setString(2, getLibdirectn());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Modifier() {
        try {
            String req = "update t_direction set libdirect=? where iddirect=?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getLibdirectn());
            pst.setString(2, getIddirectn());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from  t_direction where iddirect=?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getIddirectn());
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
            Object data[][] = new Object[n][2];
            n = 0;
            while (Resultat.next()) {
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                n++;
            }
            String titre[] = {"Code", "Libelle Direction"};

            jt.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Charger(JComboBox cbo) {
        try {
            String req = "select libdirect from t_direction";
            pst = cnx.connec.prepareStatement(req);
            Resultat = pst.executeQuery();
            cbo.setModel(new DefaultComboBoxModel(new String[]{""}));
            while (Resultat.next()) {
                cbo.addItem(Resultat.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Directions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String RechID(String Lib) {
        try {
            String req = "select iddirect from t_direction where libdirect = ?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, Lib);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {                
                return Resultat.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String RechLib(String id) {
        try {
            String req = "select libdirect from t_direction where iddirect = ?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, id);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {                
                return Resultat.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
