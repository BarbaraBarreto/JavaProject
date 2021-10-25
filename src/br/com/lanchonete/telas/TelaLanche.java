package br.com.lanchonete.telas;

import java.sql.*;
import br.com.lanchonete.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recurso da biblioteca para busca por nome rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 */
public class TelaLanche extends javax.swing.JFrame {

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
     * Creates new form TelaLanche
     */
    public TelaLanche() {
        initComponents();
        
        // estabelecendo a conexao com banco de dados sempre neste ponto
        conexao = ModuloConexao.conector();
    }

    // método para adicionar novo lanche
    private void adicionar() {
        String sql = "insert into tblanches (nomelanche, valorlanche, descricaolanche, tamanholanche, paolanche) values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtLancheNome.getText());
            pst.setString(2, txtLancheValor.getText().replace(",", "."));
            pst.setString(3, txtLancheDescricao.getText());
            // Capturar o Texto do Combobox Tamanho "obs converter para String" 
            pst.setString(4, cboLancheTamanho.getSelectedItem().toString());
             // Capturar o Texto do Combobox Tipo de Pão"obs converter para String
            pst.setString(5, cboLanchePao.getSelectedItem().toString());
            
            
            if (txtLancheNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Lanche Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    
                    txtLancheNome.setText(null);
                    txtLancheDescricao.setText(null);
                    txtLancheValor.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
       // metodo para pesquisar lanche com filtro 
    private void pesquisar_lanche() {
        String sql = " select idlanche as ID, nomelanche as Lanche, valorlanche as Valor, descricaolanche as Descricao, tamanholanche as Tamanho, paolanche as Tipo_Pao from tblanches where nomelanche like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtLanchePesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela 
            tblLanches.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    
    
     // metodo para setar os campos do formulário com os conteudos da tabela lanches
    public void setar_campos() {
        int setar = tblLanches.getSelectedRow();
        txtLancheId.setText(tblLanches.getModel().getValueAt(setar, 0).toString());
        txtLancheNome.setText(tblLanches.getModel().getValueAt(setar, 1).toString());
        txtLancheValor.setText(tblLanches.getModel().getValueAt(setar, 2).toString().replace(",", "."));
        txtLancheDescricao.setText(tblLanches.getModel().getValueAt(setar, 3).toString());
       
        
        // alinha abaixo desabilata o botão adicionar, isso evita duplicar e cadastrar o mesmo lanche.
        btnAdicionar.setEnabled(false);
    }
    
    
    
    // Criando o metodo para alterar dados da lanche
    private void alterar() {
        String sql = "update tblanches set nomelanche=?, valorlanche=?, descricaolanche=?, tamanholanche=?, paolanche=? where idlanche=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtLancheNome.getText());
            pst.setString(2, txtLancheValor.getText().replace(",", "."));
             pst.setString(3, txtLancheDescricao.getText());
            // Capturar o Texto do Combobox Tamanho "obs converter para String" 
            pst.setString(4, cboLancheTamanho.getSelectedItem().toString());
             // Capturar o Texto do Combobox Tipo de Pão"obs converter para String
            pst.setString(5, cboLanchePao.getSelectedItem().toString());
            
            // obs Idbebida será o item a ser capturado 
            pst.setString(6, txtLancheId.getText());
            if (txtLancheNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de lanche com os dados do lanche
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabela lanche
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Lanche Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    
                    txtLancheNome.setText(null);
                    txtLancheDescricao.setText(null);
                    txtLancheValor.setText(null);
                    // Reabiltar o botão Cadastrar após a alteração do lanche realizada.
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    
     // metodo responssável pela remoção do lanche
    private void remover() {
        //a estrutura abaixo confirma a remoção do lanche
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Remover este Lanche ?", "Atenção!!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tblanches where idlanche=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtLancheId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Lanche Removida com Sucesso");
                    // as linhas abaixo limpam os campos
                    txtLancheId.setText(null);
                    txtLancheNome.setText(null);
                    txtLancheDescricao.setText(null);
                    txtLancheValor.setText(null);
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
        txtLanchePesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLanches = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtLancheId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLancheNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLancheDescricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboLancheTamanho = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboLanchePao = new javax.swing.JComboBox<>();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtLancheValor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lanches");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Pesquisar Lanche:");

        txtLanchePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLanchePesquisarKeyReleased(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/pesqui.png"))); // NOI18N

        tblLanches.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLanches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLanchesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLanches);

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("ID Lanche:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("*Lanche:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("Descrição:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("Tamanho:");

        cboLancheTamanho.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        cboLancheTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pequeno", "Médio", "Grande", " " }));

        jLabel7.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel7.setText("Tipo pão:");

        cboLanchePao.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        cboLanchePao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hamburguer", "Pão de pimenta", "Pão hot dog", " " }));

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

        jLabel8.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel8.setText("Valor R$:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtLanchePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLancheNome, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                    .addComponent(txtLancheDescricao)
                                    .addComponent(cboLanchePao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboLancheTamanho, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLancheValor, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addComponent(txtLanchePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLancheNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtLancheDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboLancheTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(cboLanchePao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLancheValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnAlterar)
                    .addComponent(btnRemover))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // método para adicionar clientes
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chamando o metodo remover cliente
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtLanchePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLanchePesquisarKeyReleased
        // chamar metodo pesquisar clientes por numero do Telefone
        pesquisar_lanche();    }//GEN-LAST:event_txtLanchePesquisarKeyReleased

    private void tblLanchesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLanchesMouseClicked
        // Chamando o metodo para setar campos
        setar_campos();
    }//GEN-LAST:event_tblLanchesMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando o método para alterar Cliente
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboLanchePao;
    private javax.swing.JComboBox<String> cboLancheTamanho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLanches;
    private javax.swing.JTextField txtLancheDescricao;
    private javax.swing.JTextField txtLancheId;
    private javax.swing.JTextField txtLancheNome;
    private javax.swing.JTextField txtLanchePesquisar;
    private javax.swing.JTextField txtLancheValor;
    // End of variables declaration//GEN-END:variables
}
