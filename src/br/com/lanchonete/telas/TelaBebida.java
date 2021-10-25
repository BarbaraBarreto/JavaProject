package br.com.lanchonete.telas;

import java.sql.*;
import br.com.lanchonete.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recurso da biblioteca para busca por nome rs2xml.jar
import net.proteanit.sql.DbUtils;
/**
 *
 */
public class TelaBebida extends javax.swing.JFrame {

      /*
   usando variável de conexao DAL
     */
    Connection conexao = null;
    /*
    Criando variáveis especiais para conexao com o banco de dados
    Prepared Statement e ResultSet são framewoks do pacote java.sql
    servem oara preparar e executar as indtruções SQL
     */
    PreparedStatement pst = null;
    ResultSet rs = null;
 
 
    /**
     * Creates new form TelaBebida
     */
    public TelaBebida() {
        initComponents();
        // estabelecendo a conexao com banco de dados sempre neste ponto
        conexao = ModuloConexao.conector();
    }

 
    // método para adicionar nova bebida
    private void adicionar() {
        String sql = "insert into tbbebidas (nomebebida, valorbebida, volumebebida) values(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBebiNome.getText());
            pst.setString(2, txtBebiValor.getText().replace(",", "."));
             pst.setString(3, txtBebiVolume.getText());
         
            if (txtBebiNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de bebidas com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Bebida Adicionada com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                 
                    txtBebiNome.setText(null);
                    txtBebiVolume.setText(null);
                    txtBebiValor.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
 
     // metodo para pesquisar bebida com filtro
    private void pesquisar_bebida() {
        String sql = " select idbebida as ID, nomebebida as Bebida, valorbebida as Valor, volumebebida as Volume from tbbebidas where nomebebida like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtBebiPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela bebida
            tblBebidas.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
 
 
     // metodo para setar os campos do formulário com os conteudos da tabela bebidas
    public void setar_campos() {
        int setar = tblBebidas.getSelectedRow();
        txtBebiId.setText(tblBebidas.getModel().getValueAt(setar, 0).toString());
        txtBebiNome.setText(tblBebidas.getModel().getValueAt(setar, 1).toString());
        txtBebiValor.setText(tblBebidas.getModel().getValueAt(setar, 2).toString().replace(",", "."));
        txtBebiVolume.setText(tblBebidas.getModel().getValueAt(setar, 3).toString());
     
     
        // alinha abaixo desabilata o botão adicionar, isso evita duplicar e cadastrar o mesma bebida.
        btnAdicionar.setEnabled(false);
    }
 
 
     // Criando o metodo para alterar dados da bebida
    private void alterar() {
        String sql = "update tbbebidas set nomebebida=?, valorbebida=?, volumebebida=?,  where idbebida=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBebiNome.getText());
            pst.setString(2, txtBebiValor.getText().replace(",", "."));
            pst.setString(3, txtBebiVolume.getText());
         
            // obs Idbebida será o 4item a ser capturado
            pst.setString(4, txtBebiId.getText());
            if (txtBebiNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Bebida com os dados da Bebida
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabela Bebida
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da Bebida Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                 
                    txtBebiNome.setText(null);
                    txtBebiVolume.setText(null);
                    txtBebiValor.setText(null);
               
                    // Reabiltar o botão Cadastrar após a alteração da bebida realizada.
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

   
     // metodo responssável pela remoção da bebida
    private void remover() {
        //a estrutura abaixo confirma a remoção da Bebida
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Remover esta Bebida ?", "Atenção!!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbbebidas where idbebida=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtBebiId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Bebida Removida com Sucesso");
                    // as linhas abaixo limpam os campos
                    txtBebiId.setText(null);
                    txtBebiNome.setText(null);
                    txtBebiVolume.setText(null);
                    txtBebiValor.setText(null);
                    // Reabiltar o botão Cadastrar após a alteração da bebida realizada.
                    btnAdicionar.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBebiPesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBebidas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtBebiId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBebiNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBebiVolume = new javax.swing.JTextField();
        txtBebiValor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bebidas");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Pesquisar nome bebida:");

        txtBebiPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBebiPesquisarActionPerformed(evt);
            }
        });
        txtBebiPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBebiPesquisarKeyReleased(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/pesqui.png"))); // NOI18N

        tblBebidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBebidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBebidasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBebidas);

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("ID Bebida:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("*Bebida:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("Volume ML:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("Valor R$:");

        btnAdicionar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/criar.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/atuali.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/excluir.png"))); // NOI18N
        btnRemover.setText("Excluir");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtBebiPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBebiId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBebiNome, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBebiVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBebiValor, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAdicionar)
                        .addGap(44, 44, 44)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBebiPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBebiId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBebiNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBebiVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBebiValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnAlterar)
                    .addComponent(btnRemover))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // método para adicionar bebida
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando o método para alterar bebida
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chamando o metodo remover bebida
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtBebiPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBebiPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBebiPesquisarActionPerformed

    private void txtBebiPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBebiPesquisarKeyReleased
        // chamar metodo pesquisar bebida
        pesquisar_bebida();
    }//GEN-LAST:event_txtBebiPesquisarKeyReleased

    private void tblBebidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBebidasMouseClicked
        // Chamando o metodo para setar campos
        setar_campos();
    }//GEN-LAST:event_tblBebidasMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBebidas;
    private javax.swing.JTextField txtBebiId;
    private javax.swing.JTextField txtBebiNome;
    private javax.swing.JTextField txtBebiPesquisar;
    private javax.swing.JTextField txtBebiValor;
    private javax.swing.JTextField txtBebiVolume;
    // End of variables declaration//GEN-END:variables
}
