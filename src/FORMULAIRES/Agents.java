/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULAIRES;

import ENTITES.Agent;

/**
 *
 * @author MECHACK
 */
public class Agents extends javax.swing.JPanel {

    /**
     * Creates new form Agents
     */
    public Agents() {
        initComponents();
        ag.remplir(JtAgent, "select * from t_agent");
    }
    Agent ag = new Agent();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pm = new javax.swing.JPopupMenu();
        miActualiser = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miAjouter = new javax.swing.JMenuItem();
        miModifier = new javax.swing.JMenuItem();
        jLabel3 = new javax.swing.JLabel();
        btnAjouter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtAgent = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        miActualiser.setText("Actualiser");
        miActualiser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miActualiserActionPerformed(evt);
            }
        });
        pm.add(miActualiser);
        pm.add(jSeparator1);

        miAjouter.setText("Ajouter");
        miAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAjouterActionPerformed(evt);
            }
        });
        pm.add(miAjouter);

        miModifier.setText("Modifier");
        miModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miModifierActionPerformed(evt);
            }
        });
        pm.add(miModifier);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Recherche");

        btnAjouter.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        btnAjouter.setText("Ajouter");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });

        JtAgent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        JtAgent.setComponentPopupMenu(pm);
        JtAgent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtAgentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JtAgent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        // TODO add your handling code here:
        JdAgent ja = new JdAgent(null, true);
        ja.type = "ajouter";
        ja.setVisible(true);
        ag.remplir(JtAgent, "select * from t_agent");
    }//GEN-LAST:event_btnAjouterActionPerformed

    private void miAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAjouterActionPerformed
        // TODO add your handling code here:
        btnAjouterActionPerformed(evt);
    }//GEN-LAST:event_miAjouterActionPerformed
    String idmat;
    private void JtAgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtAgentMouseClicked
        idmat = JtAgent.getValueAt(JtAgent.getSelectedRow(), 0).toString();
    }//GEN-LAST:event_JtAgentMouseClicked

    private void miModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miModifierActionPerformed
        // TODO add your handling code here:
        JdAgent ja = new JdAgent(null, true);
        ja.type = "modifier";
        ja.ChargerData(idmat);
        ja.setVisible(true);
        ag.remplir(JtAgent, "select * from t_agent");
    }//GEN-LAST:event_miModifierActionPerformed

    private void miActualiserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miActualiserActionPerformed
        // TODO add your handling code here:
        ag.remplir(JtAgent, "select * from t_agent");
    }//GEN-LAST:event_miActualiserActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JtAgent;
    private javax.swing.JButton btnAjouter;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JMenuItem miActualiser;
    private javax.swing.JMenuItem miAjouter;
    private javax.swing.JMenuItem miModifier;
    private javax.swing.JPopupMenu pm;
    // End of variables declaration//GEN-END:variables
}
