/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITES;

import java.awt.Component;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Lasourceplus
 */
public class TraitementImage {

    public static Image ByteVersImage(Component componentParent, byte[] tabBinaireImage) {
        try {
            File fileTmp = new File("photoTmp.txt");
            FileOutputStream os = new FileOutputStream(fileTmp);           
            os.write(tabBinaireImage);
            BufferedInputStream bis = new BufferedInputStream(new DataInputStream(new FileInputStream(fileTmp)));
            InputStream is = bis;
            Image image = ImageIO.read(is);
            fileTmp.delete();
            return image;
            //
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(componentParent, e);
        }
        return null;
    }

    public byte[] imageVersByte(Component componentParent, JTextField tfPhotoAdresse) {
        byte[] tabImage = new byte[1];
        //
        try {
            //
            File fileTmp = new File(tfPhotoAdresse.getText());
            //
            FileInputStream fis = new FileInputStream(fileTmp);
            String txt = "FIN !!!";
            int nbreLTab = 0;
            while (fis.read(tabImage) != -1) {
                nbreLTab++;
            }
            //
            tabImage = new byte[nbreLTab];
            System.out.println(txt);
            //
            fis = new FileInputStream(fileTmp);
            byte[] Tab = new byte[1];
            int iCpt = 0;
            while (fis.read(Tab) != -1) {
                //
                tabImage[iCpt] = Tab[0];
                iCpt++;
            }
            //
            fis.close();
            //
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(componentParent, e);
        }
        return tabImage;
    }

    public static void ByteVersImageAffichageLabel(Component componentParent, byte[] tabBinaireImage, JLabel lblImage,int w,int h) {
        try {
            lblImage.setText("");
            Image image = ByteVersImage(componentParent, tabBinaireImage);
            image = image.getScaledInstance(w, h,0);
            lblImage.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(componentParent, e);
        }
    }

    public void OuvrirParcourirImage(Component componentParent, JTextField tfPhotoAdresse, JLabel lblImage_MAJ, JTextField tfnom) {
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
            chooser.showOpenDialog(componentParent);
            //        picture.setText(jFileChooser1.getSelectedFile().toString());
            System.out.println(chooser.getSelectedFile().toString());
            System.out.println(chooser.getSelectedFile().toString().replace("\\", "/"));
            String pic = chooser.getSelectedFile().toString().replace("\\", "/");
            String n = chooser.getSelectedFile().getName();
            Image img1 = (new javax.swing.ImageIcon(pic)).getImage().
                    getScaledInstance(lblImage_MAJ.getWidth(), lblImage_MAJ.getHeight(), 0);
            
            lblImage_MAJ.setText("");
            lblImage_MAJ.setIcon(new ImageIcon(img1));
            tfPhotoAdresse.setText(pic);
            tfnom.setText(n);
            //
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
