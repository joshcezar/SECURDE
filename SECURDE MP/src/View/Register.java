package View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JPanel {

    public Frame frame;
    String pass;
    String pass2;

    public Register() {
        initComponents();
        pass = "";
        pass2 = "";
        password.setTransferHandler(null);
        confpass.setTransferHandler(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        password = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        confpass = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGISTER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        password.setBackground(new java.awt.Color(240, 240, 240));
        password.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        password.setToolTipText("Must be at least 8 characters with at least one number and one letter.");
        password.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        password.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                passwordMouseDragged(evt);
            }
        });
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordKeyTyped(evt);
            }
        });

        username.setBackground(new java.awt.Color(240, 240, 240));
        username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        confpass.setBackground(new java.awt.Color(240, 240, 240));
        confpass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confpass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpass.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        confpass.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                confpassMouseDragged(evt);
            }
        });
        confpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                confpassKeyTyped(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("<Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(username)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addComponent(password, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confpass, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(120, 120, 120))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confpass, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (password.getText().equals(confpass.getText())) {
            if (pass.contains("null")) {
                frame.registerAction(username.getText(), pass.substring(4) + password.getText().substring(password.getText().length() - 1), pass2.substring(4) + confpass.getText().substring(confpass.getText().length() - 1));
            } else if (password.getText().length() > 0 || confpass.getText().length() > 0) {
                frame.registerAction(username.getText(), pass + password.getText().substring(password.getText().length() - 1), pass2 + confpass.getText().substring(confpass.getText().length() - 1));
            } else if (password.getText().length() > 0 || confpass.getText().length() <= 0) {
                frame.registerAction(username.getText(), pass + password.getText(), pass2 + confpass.getText());

            }
            username.setText("");
            password.setText("");
            confpass.setText("");
            frame.loginNav();
        } else {
            username.setText("");
            password.setText("");
            confpass.setText("");
            JOptionPane.showMessageDialog(null, "Passwords Do Not Match!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frame.loginNav();
        username.setText("");
        password.setText("");
        confpass.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void passwordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyTyped
        // TODO add your handling code here:
        password.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent arg0) {
                pass = frame.main.savePassword(password.getText());
                password.setText(frame.main.hidePassword(password.getText()));
            }
        });
    }//GEN-LAST:event_passwordKeyTyped

    private void confpassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confpassKeyTyped
        // TODO add your handling code here:
        confpass.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent arg0) {
                pass2 = frame.main.savePassword(confpass.getText());
                confpass.setText(frame.main.hidePassword(confpass.getText()));
            }
        });
    }//GEN-LAST:event_confpassKeyTyped

    private void confpassMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confpassMouseDragged
        // TODO add your handling code here:
        confpass.setText("");
    }//GEN-LAST:event_confpassMouseDragged

    private void passwordMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordMouseDragged
        // TODO add your handling code here:
        password.setText("");
    }//GEN-LAST:event_passwordMouseDragged
    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField confpass;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
