/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULAIRES;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.contrib.FaceRecognizer;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
//import org.opencv.face.Face;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

//-----------------------------------------------------
/**
 *
 * @author MECHACK
 */
public class FaceDetect extends javax.swing.JFrame {

    FaceRecognizer fr;
    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    CascadeClassifier faceDetector = new CascadeClassifier(FaceDetect.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
    MatOfRect faceDetections = new MatOfRect();
    Rect[] rectCrop;
    public HashMap<Integer, String> names = new HashMap<Integer, String>();
    int index = 0;

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            rectCrop = faceDetections.toArray();
                            for (Rect rectCrop1 : rectCrop) {
                                // System.out.println("ttt");
                                String box_text = null;
//                                      Core.putText(frame, box_text, new Point(12, 20), 
//            		Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
                                Core.rectangle(frame, rectCrop1.tl(), rectCrop1.br(), new Scalar(0, 255, 0),3);
                                Rect Crop = new Rect(rectCrop1.tl(), rectCrop1.br());
                                Mat croppedImage = new Mat(frame, Crop);
                                Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
                                Mat resizeImage = new Mat();
                                Size size = new Size(250, 250);
                                Imgproc.resize(croppedImage, resizeImage, size);
                                double[] returnedResults = faceRecognition(resizeImage);
                                double prediction = returnedResults[0];
                                double confidence = returnedResults[1];
//			System.out.println("PREDICTED LABEL IS: " + prediction);
                                int label = (int) prediction;
                                String name = "";
                                if (names.containsKey(label)) {
                                    name = names.get(label);
                                } else {
                                    name = "MUTOKA Mechack";
                                }
                                // Create the text we will annotate the box with:
//            String box_text = "Prediction = " + prediction + " Confidence = " + confidence;
                                box_text = "Prediction = " + name + " Confidence = " + confidence;
                                // Calculate the position for annotated text (make sure we don't
                                // put illegal values in there):
                                double pos_x = Math.max(rectCrop1.tl().x - 10, 0);
                                double pos_y = Math.max(rectCrop1.tl().y - 10, 0);
                                // And now put it into the image:
                                Core.putText(frame, box_text, new Point(pos_x, pos_y),
                                        Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 3),2);
//                                Rect Crop = new Rect(rectCrop[i].tl(), rectCrop[i].br());
//                                Mat croppedImage = new Mat(frame, Crop);
//                                Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
//                                // Equalize histogram
////                                Imgproc.equalizeHist(croppedImage, croppedImage);
//                                // Resize the image to a default size
//                                Mat resizeImage = new Mat();
//                                Size size = new Size(250, 250);
//                                Imgproc.resize(croppedImage, resizeImage, size);
//
//                                String filename = "Ouput.jpg";
//                                Highgui.imwrite("D:\\" + filename, resizeImage);

//                                rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
// LBPHFaceRecognizer facereco=Face.createLBPHFaceRecognizer();
// facereco.train(null, frame);
// facereco.load(null);
//                                  String filename = "Ouput.jpg";
//                            Imgcodecs.imwrite("D:\\" + filename, frame);
//                            Mat markedImage = new Mat(frame, rectCrop);
//                            Imgcodecs.imwrite("D:\\cropimage.jpg", markedImage);
//                                      Core.putText(frame, "MECHACK", new Point(rect.x, rect.y - 10 ), NORMAL, 0.5, new Scalar(0, 255, 0));
                            }

//---------------------------------------------------------------------------------------
                            Highgui.imencode(".jpg", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;

//                            String filename = "Ouput.jpg";
//                            Highgui.imwrite("D:\\" + filename, frame);
//                            Mat markedImage = new Mat(frame, rectCrop);
//                            Highgui.imwrite("D:\\cropimage.jpg", markedImage);
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                   
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (IOException | InterruptedException ex) {
                            System.out.println("Error");
                            System.out.println(ex.getMessage());
                        }

                    }
                }
            }
        }

        private double[] faceRecognition(Mat currentFace) {

            // predict the label
            int[] predLabel = new int[1];
            double[] confidence = new double[1];
            int result;

//                FaceRecognizer facerec=new FisherFaceRecognizer();
//            FaceRecognizer fr=new FaceRecognizer(15);
////            LBPHFaceRecognizer lbphFaceRecognizer = createLBPHFaceRecognizer();
////        lbphFaceRecognizer.load(trainedResult);
////            BasicFaceRecognizer faceRecognizer = Face.createEigenFaceRecognizer();
//            lbphFaceRecognizer .load("D:\\");
//            lbphFaceRecognizer.predict(currentFace, predLabel, confidence);
//
//        	result = lbphFaceRecognizer .predict_label(currentFace);
            result = predLabel[0];

            return new double[]{result, confidence[0]};
        }

    }

    /**
     * Creates new form FaceDetect
     */
    public FaceDetect() {
        initComponents();
//        trainModel();
//new FaceDetect().setVisible(false);
    }

    private void trainModel() {
        // Read the data from the training set
        File root = new File("D:\\");

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        List<Mat> images = new ArrayList<Mat>();

        System.out.println("THE NUMBER OF IMAGES READ IS: " + imageFiles.length);

        List<Integer> trainingLabels = new ArrayList<>();
//    opencv_core.MatVector img=new opencv_core.Matvector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CvType.CV_32SC1);

//        IntBuffer lab=labels.createBuffer();
        int counter = 0;

        for (File image : imageFiles) {
            // Parse the training set folder files

            Mat img = Highgui.imread(image.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);
            for (int i = 0; i < imageFiles.length; i++) {
                String b=imageFiles[i].getName().split(".jpg")[0];
                System.out.println("dans l'emplacement "+i+" nous avons "+imageFiles[i]);
                System.out.println(b);
//                String na = image.getName();
//                System.out.println(na);
            }
            
//            System.out.println(i);
            // Change to Grayscale and equalize the histogram
//                Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
//                Imgproc.equalizeHist(img, img);
            // Extract label from the file name
            Integer label = null;
            try {
                label = Integer.parseInt(image.getName().split(".jpg")[0]);
                System.out.println(label);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

//                String i=image.getName();
//                System.out.println(i);
            // Extract name from the file name and add it to names HashMap
            String labnname = image.getName().split("\\_")[0];
            String name = labnname;
            names.put(label, name);
            // Add training set images to images Mat
            images.add(img);

            labels.put(counter, 0, label);
            counter++;
        }
        //FaceRecognizer faceRecognizer = Face.createFisherFaceRecognizer(0,1500);
        FaceRecognizer fre = null;
//        Face.createLBPHFaceRecognizer();
//        org.opencv.face.FaceRecognizer faceRecognizer = Face.createFisherFaceRecognizer();
        //FaceRecognizer faceRecognizer = Face.createEigenFaceRecognizer(0,50);
//        faceRecognizer.train(images, labels);
//        faceRecognizer.save("D:\\");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        capt = new javax.swing.JButton();
        JtextName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        capt.setText("Capture");
        capt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captActionPerformed(evt);
            }
        });

        jLabel1.setText("Name");

        jButton3.setText("Reconnaitre");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(capt)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(JtextName, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(capt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtextName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(620, 478));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        webSource = new VideoCapture(0); // video capture from default cam
        myThread = new DaemonThread(); //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thrad
        jButton1.setEnabled(false);  // deactivate start button
        jButton2.setEnabled(true);  //  activate stop button

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        myThread.runnable = false;            // stop thread
        jButton2.setEnabled(false);   // activate start button 
        jButton1.setEnabled(true);     // deactivate stop button

        webSource.release();  // stop caturing fron cam
    }//GEN-LAST:event_jButton2ActionPerformed

    private void captActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captActionPerformed
        // TODO add your handling code here:
        for (int i = 0; i < rectCrop.length; i++) {
            // System.out.println("ttt");

            String box_text = null;
//                                      Core.putText(frame, box_text, new Point(12, 20), 
//            		Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
//                                Core.rectangle(frame, rectCrop[i].tl(), rectCrop[i].br(), new Scalar(0, 255, 0));
            Rect Crop = new Rect(rectCrop[i].tl(), rectCrop[i].br());
            Mat croppedImage = new Mat(frame, Crop);
            Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
            // Equalize histogram
//                                Imgproc.equalizeHist(croppedImage, croppedImage);
            // Resize the image to a default size
            Mat resizeImage = new Mat();
            Size size = new Size(250, 250);
            Imgproc.resize(croppedImage, resizeImage, size);

            String filename = JtextName.getText();
            Highgui.imwrite("D:\\" + filename + "_" + (index++) + ".jpg", resizeImage);

            myThread.runnable = false;
            webSource.release();
//                                rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
// LBPHFaceRecognizer facereco=Face.createLBPHFaceRecognizer();
// facereco.train(null, frame);
// facereco.load(null);
//                                  String filename = "Ouput.jpg";
//                            Imgcodecs.imwrite("D:\\" + filename, frame);
//                            Mat markedImage = new Mat(frame, rectCrop);
//                            Imgcodecs.imwrite("D:\\cropimage.jpg", markedImage);
//                                      Core.putText(frame, "MECHACK", new Point(rect.x, rect.y - 10 ), NORMAL, 0.5, new Scalar(0, 255, 0));
        }
    }//GEN-LAST:event_captActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new FrmPrincipal().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FaceDetect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceDetect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceDetect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceDetect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetect().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JtextName;
    private javax.swing.JButton capt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
