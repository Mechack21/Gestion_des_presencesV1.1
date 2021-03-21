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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Presences {

    String datepres, type, matricule;

    public String getDatepres() {
        return datepres;
    }

    public void setDatepres(String datepres) {
        this.datepres = datepres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    Connexion cnx = new Connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into t_presences(datepres,type,matricule) values(?,?,?)";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getDatepres());
            pst.setString(2, getType());
            pst.setString(3, getMatricule());
            int t = pst.executeUpdate();
            if (t == 1) {
                System.out.println("Présence OK");
            } else {
                System.out.println("Présence Failed!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Presences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public int g;
    public int Verification() {
        try {
            Date lelo = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String req = "select count(*) from t_presences where matricule = ? and datepres like '" + sdf.format(lelo) + "%' and Type = ?";
            pst = cnx.connec.prepareStatement(req);
            pst.setString(1, getMatricule());
            pst.setString(2, getType());
            Resultat = pst.executeQuery();
            int b=0;
            while (Resultat.next()) {
                if (Resultat.getInt(1) > 0) {
                   return Resultat.getInt(1);
                }
              System.out.println(Resultat.getInt(1)+""); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Presences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int NbrPres() {
        try {
            String req = "select count(*) from t_presences where  datepres like ? and type = ?";
            pst = cnx.connec.prepareStatement(req);
            Date lelo = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            pst.setString(1, sdf.format(lelo) + " %");
            pst.setString(2, getType());
            Resultat = pst.executeQuery();
            while (Resultat.next()) {
                return Resultat.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Presences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void remplir(JTable jt){
        try {
            String req = "SELECT t_presences.datepres,t_presences.Type, t_agent.nom,t_agent.postnom,t_agent.prenom,t_division.libdiv FROM t_presences,t_agent,t_division WHERE t_division.iddiv = t_agent.iddiv AND t_agent.matricule = t_presences.matricule";
            pst = cnx.connec.prepareStatement(req);
            Resultat=pst.executeQuery();
            int n = 0;
            while (Resultat.next()) {                
                n++;
            }
            
            pst = cnx.connec.prepareStatement(req);
            Resultat=pst.executeQuery();
            Object data[][] = new Object[n][4];
            n=0;
            while (Resultat.next()) {                
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getString(3) + " " + Resultat.getString(4) + " " + Resultat.getString(5);
                data[n][3] = Resultat.getString(6);
                n++;
            }
            String titre[] = {"Date","Type","Noms","Division"};
            
            jt.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
