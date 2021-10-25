/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lanchonete.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author Gabriel
 */
public class FastFood {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }
    
    /*
    
    public static class TelaLogin extends JFrame {

        public TelaLogin() {
            super();
            initComponents();
        }
        
        @SuppressWarnings(value = "unchecked")
        private void initComponents() {
            jLayeredPane1 = new JLayeredPane();
            jLabel1 = new JLabel();
            jLabel4 = new JLabel();
            jPanel1 = new JPanel();
            jPasswordField1 = new JPasswordField();
            jLabel3 = new JLabel();
            jLabel2 = new JLabel();
            jTextField1 = new JTextField();
            jLabel5 = new JLabel();
            jButton1 = new JButton();
            GroupLayout jLayeredPane1Layout = new GroupLayout(jLayeredPane1);
            jLayeredPane1.setLayout(jLayeredPane1Layout);
            jLayeredPane1Layout.setHorizontalGroup(jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
            jLayeredPane1Layout.setVerticalGroup(jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setBackground(Color.darkGray);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            setForeground(new Color(51, 51, 51));
            jLabel1.setIcon(new ImageIcon(getClass().getResource("/Imag/Capturar6.PNG"))); // NOI18N
            jLabel4.setFont(new Font("Segoe Print", 0, 18)); // NOI18N
            jLabel4.setIcon(new ImageIcon(getClass().getResource("/Imag/iconfinderscones4193113-116033_115967.png"))); // NOI18N
            jLabel4.setText("BL Burger");
            jPanel1.setBorder(BorderFactory.createEtchedBorder());
            jPasswordField1.setText("jPasswordField1");
            jLabel3.setFont(new Font("Segoe Print", 0, 14)); // NOI18N
            jLabel3.setText("Senha:");
            jLabel2.setFont(new Font("Segoe Print", 0, 14)); // NOI18N
            jLabel2.setText("Usu\u00e1rio:");
            jLabel5.setFont(new Font("Segoe Print", 0, 18)); // NOI18N
            jLabel5.setText("Login");
            GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE).addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)).addContainerGap()).addGroup(jPanel1Layout.createSequentialGroup().addGap(74, 74, 74).addComponent(jLabel5).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addComponent(jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(28, 28, 28).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(46, 46, 46)));
            jButton1.setFont(new Font("Segoe Print", 0, 18)); // NOI18N
            jButton1.setIcon(new ImageIcon(getClass().getResource("/Imag/location_enter_icon_135431.png"))); // NOI18N
            jButton1.setText("Acessar");
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE).addGap(23, 23, 23)).addGroup(layout.createSequentialGroup().addGap(71, 71, 71).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(0, 27, Short.MAX_VALUE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton1).addGap(58, 58, 58)))));
            layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1)).addComponent(jLabel1));
            setBounds(0, 0, 698, 397);
        } // </editor-fold>


        public static void main(String[] args) {
            
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new TelaLogin().setVisible(true);
                }
            });
        }
        // Variables declaration - do not modify
        private JButton jButton1;
        private JLabel jLabel1;
        private JLabel jLabel2;
        private JLabel jLabel3;
        private JLabel jLabel4;
        private JLabel jLabel5;
        private JLayeredPane jLayeredPane1;
        private JPanel jPanel1;
        private JPasswordField jPasswordField1;
        private JTextField jTextField1;
        // End of variables declaration
    }
    */
}
