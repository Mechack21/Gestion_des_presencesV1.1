package CONNEXIONS;
//import stox.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {

    private String url;
    private String user;
    private String password;
    public Connection connec;
    public int nbre_col;
    public boolean EtatCnx = false;
    public String erreur;

    public Connexion() {
        try {

            String Adresse = "ip.txt";
            String port = "port.txt";
            String bd = "bd.txt";
            DataInputStream disAdresse = new DataInputStream(new FileInputStream(Adresse));
            DataInputStream disPort = new DataInputStream(new FileInputStream(port));
            DataInputStream disBd = new DataInputStream(new FileInputStream(bd));
            url = "jdbc:mysql://" + disAdresse.readUTF() + ":" + disPort.readUTF() + "/" + disBd.readUTF()+"";
            System.out.println(url);
            
            String Utilisateur = "user.txt";
            DataInputStream disUser = new DataInputStream(new FileInputStream(Utilisateur));
            user = disUser.readUTF();

            String MotDePasse = "pwd.txt";
            DataInputStream disPwd = new DataInputStream(new FileInputStream(MotDePasse));
            password = disPwd.readUTF();
            
            connec = DriverManager.getConnection(url, user, password);
            System.out.println("Connecté to data!");
            EtatCnx = true;
            
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Veuillez verifier la connexion interne ou \nFait appel à un support IT");
            erreur = ex.getMessage();
        }
    }

    public Connection conx() {
        return connec;
    }
    
    public  void Fermer_cnx(){
        try {
            connec.close();
            System.out.println("Connexion closed!");
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
