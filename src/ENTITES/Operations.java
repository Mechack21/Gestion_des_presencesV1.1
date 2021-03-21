/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITES;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author LE GRAND AL
 */
public class Operations {
    public  String lienPhoto;

    public void OuvrirParcourirImage(JLabel lblImage) {
        try {
            // TODO add your handling code here:
            JFileChooser chooser = new JFileChooser();
            //        chooser.FILES_ONLY;
            javax.swing.filechooser.FileFilter filtreTxt = new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    // accepte-t-on f ? 
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")
                            || f.getName().toLowerCase().endsWith(".png");
                }//accept 

                @Override
                public String getDescription() {
                    // description du filtre 
                    return "Fichiers Texte (*.jpg) | (*.png)";
                }//getDescription 
            };
            // on ajoute le filtre 
            chooser.addChoosableFileFilter(filtreTxt);
            // on veut aussi le filtre de tous les fichiers 
            chooser.setFileFilter(filtreTxt);
            chooser.showOpenDialog(null);
            //        picture.setText(jFileChooser1.getSelectedFile().toString());
            System.out.println(chooser.getSelectedFile().toString());
            System.out.println(chooser.getSelectedFile().toString().replace("\\", "/"));
            String pic = chooser.getSelectedFile().toString().replace("\\", "/");
            String n = chooser.getSelectedFile().getName();
            Image img1 = (new javax.swing.ImageIcon(pic)).getImage().
                    getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), 0);

            lblImage.setText("");
            lblImage.setIcon(new ImageIcon(img1));
            lienPhoto = pic;
            //            tfPhotoAdresse.setText(pic);
//            tfnom.setText(n);
            //
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
