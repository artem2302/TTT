/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author MINI
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    private boolean krestiks;
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream iStream;
    private OutputStream oStream;
    boolean myTurn;
    
    public MainForm() {
        initComponents();
        krestiks = true;
    }

      
    private void setCell(int cellNum){
        if (myTurn){
            JButton cell = getCellByNumber(cellNum);
            cell.setText(krestiks?"X":"O");
            cell.setEnabled(false);
            sendTurn(cellNum);    
        } else JOptionPane.showConfirmDialog(this, "Не ваш ход!");
    }
    
    private JButton getCellByNumber(int number){
        switch(number){
            case 1: return jButton1;
            case 2: return jButton2;
            case 3: return jButton3;
            case 4: return jButton4;
            case 5: return jButton5;
            case 6: return jButton6;
            case 7: return jButton7;
            case 8: return jButton8;
            case 9: return jButton9;
            default: return null;
        }        
    }
    
    private void recieveCell(int number){
        String opponentSymbol = krestiks?"O":"X";
        JButton cell = getCellByNumber(number);
        cell.setEnabled(false);
        cell.setText(opponentSymbol);
        myTurn = true;
    }
    
    private void clearCells(){
        jButton1.setText("");
        jButton2.setText("");
        jButton3.setText("");
        jButton4.setText("");
        jButton5.setText("");
        jButton6.setText("");
        jButton7.setText("");
        jButton8.setText("");
        jButton9.setText("");
    }
    
    private boolean isGameOver(boolean krestiks){
        String winnerSymbol = krestiks?"X":"O";
        return (
                (jButton1.getText().equals(winnerSymbol) && jButton2.getText().equals(winnerSymbol) && jButton3.getText().equals(winnerSymbol)) || 
                (jButton4.getText().equals(winnerSymbol) && jButton5.getText().equals(winnerSymbol) && jButton6.getText().equals(winnerSymbol)) || 
                (jButton7.getText().equals(winnerSymbol) && jButton8.getText().equals(winnerSymbol) && jButton9.getText().equals(winnerSymbol)) || 
                (jButton1.getText().equals(winnerSymbol) && jButton4.getText().equals(winnerSymbol) && jButton6.getText().equals(winnerSymbol)) || 
                (jButton2.getText().equals(winnerSymbol) && jButton5.getText().equals(winnerSymbol) && jButton7.getText().equals(winnerSymbol)) || 
                (jButton3.getText().equals(winnerSymbol) && jButton6.getText().equals(winnerSymbol) && jButton9.getText().equals(winnerSymbol)) || 
                (jButton1.getText().equals(winnerSymbol) && jButton5.getText().equals(winnerSymbol) && jButton9.getText().equals(winnerSymbol)) ||
                (jButton3.getText().equals(winnerSymbol) && jButton5.getText().equals(winnerSymbol) && jButton7.getText().equals(winnerSymbol)) 
                );
        
    }
    
    private void host() throws IOException{

        myTurn = true;
        serverSocket = new ServerSocket(3333);
        socket = serverSocket.accept();
        krestiks = true;
        
        iStream = socket.getInputStream(); 
        oStream = socket.getOutputStream();  
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    polling();
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(MainForm.this, "Game over");
                System.exit(0);
            }
        });
        t.start();        
    }
    
    private void resetAll() {
        jButton1.setText(" ");
        jButton1.setEnabled(true);
        jButton2.setText(" ");
        jButton2.setEnabled(true);
        jButton3.setText(" ");
        jButton3.setEnabled(true);
        jButton4.setText(" ");
        jButton4.setEnabled(true);
        jButton5.setText(" ");
        jButton5.setEnabled(true);
        jButton6.setText(" ");
        jButton6.setEnabled(true);
        jButton7.setText(" ");
        jButton7.setEnabled(true);
        jButton8.setText(" ");
        jButton8.setEnabled(true);
        jButton9.setText(" ");
        jButton9.setEnabled(true);
        try{
            socket.close();
            iStream.close();
            oStream.close();
        } catch (IOException e) {
            
        }
    }
    
    private void polling() throws IOException{
        boolean iWin = false;
        while (!isGameOver(true) && !isGameOver(false)){
            byte [] bytes = new byte[8];
            iStream.read(bytes);
            String s = new String(bytes);
            if (s.equals("GameOver")) break;
            int ind = bytes[0];
            recieveCell(ind);
        }
        if (iWin){
            byte [] gameOver = new String("GameOver").getBytes();
            oStream.write(gameOver);
        }
    }
    
    private void connectToHost(String IP) throws IOException{
        myTurn = false;
        socket = new Socket(IP, 3333);
        iStream = socket.getInputStream();
        oStream = socket.getOutputStream();
        
        krestiks = false;
         
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    polling();
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(MainForm.this, "Game over");
                resetAll();
            }
        });
        t.start();
        
    }
    
    private void sendTurn(int cellNum){
        if (myTurn){
            byte [] buf = new byte[1];
            buf[0] = new Integer(cellNum).byteValue();
            try {
                oStream.write(buf);
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            myTurn = false;
        } else JOptionPane.showMessageDialog(this, "Не ваш ход!");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        btnHost = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        tfIP = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton1.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton2.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton3.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton3.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton4.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton4.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton5.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton5.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton6.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton6.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton7.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton7.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton8.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton8.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton9.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton9.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        btnHost.setText("Создать сервер");
        btnHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHostActionPerformed(evt);
            }
        });

        btnConnect.setText("Подключиться");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(tfIP, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConnect))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnHost)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConnect))
                        .addGap(44, 44, 44)
                        .addComponent(btnHost)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        setCell(1);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setCell(2);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setCell(3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        setCell(4);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        setCell(5);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        setCell(6);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        setCell(7);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        setCell(8);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        setCell(9);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHostActionPerformed
        // TODO add your handling code here:
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    host();
                    JOptionPane.showMessageDialog(MainForm.this, "Хостед!");
                    btnHost.setText("Создать сервер");
                    btnHost.setEnabled(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainForm.this, "Коннекшон упал!");
                }
            }
        });
        btnHost.setText("Жду соединения...");
        btnHost.setEnabled(false);
        t.start();
      
    }//GEN-LAST:event_btnHostActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        try {
            // TODO add your handling code here:
            connectToHost(tfIP.getText());
            JOptionPane.showMessageDialog(MainForm.this, "Коннекшон эстаблишд!");
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConnectActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnHost;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JTextField tfIP;
    // End of variables declaration//GEN-END:variables
}
