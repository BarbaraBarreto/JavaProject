package br.com.lanchonete.telas;


import java.sql.*;
import br.com.lanchonete.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recurso da biblioteca para busca por nome rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 */
public class TelaFuncionario extends javax.swing.JFrame {

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
     * Creates new form TelaFuncionario
     */
    public TelaFuncionario() {
        initComponents();
        // estabelecendo a conexao com banco de dados sempre neste ponto
        conexao = ModuloConexao.conector();
    }

    // método para adicionar novo funcionario
    private void adicionar() {
        String sql = "insert into tbfuncionarios (nomefunc, endfunc, bairrofunc, cidadefunc, cpffunc, nascfunc, telfunc, emailfunc) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtFuncNome.getText());
            pst.setString(2, txtFuncEndereco.getText());
            pst.setString(3, txtFuncBairro.getText());
            pst.setString(4, txtFuncCidade.getText());
            pst.setString(5, txtFuncCpf.getText());
            pst.setString(6, txtFuncDataNasc.getText());
            pst.setString(7, txtFuncFone.getText());
            pst.setString(8, txtFuncEmail.getText());

            if ((txtFuncNome.getText().isEmpty()) || (txtFuncFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de funcionarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionario Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    
                    txtFuncNome.setText(null);
                    txtFuncEndereco.setText(null);
                    txtFuncBairro.setText(null);
                    txtFuncCidade.setText(null);
                    txtFuncCpf.setText(null);
                    txtFuncDataNasc.setText(null);
                    txtFuncFone.setText(null);
                    txtFuncEmail.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para pesquisar funcionario pelo telefone com filtro 
    private void pesquisar_cliente() {
        String sql = " select * from tbfuncionarios where nomefunc like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtFuncPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblFuncionarios.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    // metodo para setar os campos do formulário com os conteudos da tabela funcionario
    public void setar_campos() {
        int setar = tblFuncionarios.getSelectedRow();
        txtFuncId.setText(tblFuncionarios.getModel().getValueAt(setar, 0).toString());
        txtFuncNome.setText(tblFuncionarios.getModel().getValueAt(setar, 1).toString());
        txtFuncEndereco.setText(tblFuncionarios.getModel().getValueAt(setar, 2).toString());
        txtFuncBairro.setText(tblFuncionarios.getModel().getValueAt(setar, 3).toString());
        txtFuncCidade.setText(tblFuncionarios.getModel().getValueAt(setar, 4).toString());
        txtFuncCpf.setText(tblFuncionarios.getModel().getValueAt(setar, 5).toString());
        txtFuncDataNasc.setText(tblFuncionarios.getModel().getValueAt(setar, 6).toString());
        txtFuncFone.setText(tblFuncionarios.getModel().getValueAt(setar, 7).toString());
        txtFuncEmail.setText(tblFuncionarios.getModel().getValueAt(setar, 8).toString());

        // alinha abaixo desabilata o botão adicionar, isso evita duplicar e cadastrar o memso funcionario.
        btnAdicionar.setEnabled(false);
    }

    // Criando o metodo para alterar dados do funcionario
    private void alterar() {
        String sql = "update tbfuncionarios set nomefunc=?, endfunc=?, bairrofunc=?, cidadefunc=?, cpffunc=?, nascfunc=?, telfunc=?, emailfunc=? where idfunc=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtFuncNome.getText());
            pst.setString(2, txtFuncEndereco.getText());
            pst.setString(3, txtFuncBairro.getText());
            pst.setString(4, txtFuncCidade.getText());
            pst.setString(5, txtFuncCpf.getText());
            pst.setString(6, txtFuncDataNasc.getText());
            pst.setString(7, txtFuncFone.getText());
            pst.setString(8, txtFuncEmail.getText());
            // obs CliId será o 9 item a ser capturado 
            pst.setString(9, txtFuncId.getText());
            if ((txtFuncNome.getText().isEmpty()) || (txtFuncFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Funcionario com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Funcionario Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    
                    txtFuncNome.setText(null);
                    txtFuncEndereco.setText(null);
                    txtFuncBairro.setText(null);
                    txtFuncCidade.setText(null);
                    txtFuncCpf.setText(null);
                    txtFuncDataNasc.setText(null);
                    txtFuncFone.setText(null);
                    txtFuncEmail.setText(null);
                    // Reabiltar o botão Cadastrar após a alteração do cliente realizada.
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo responssável pela remoção de funcionario
    private void remover() {
        //a estrutura abaixo confirma a remoção do funcionario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Remover este Funcionário ?", "Atenção!!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbfuncionarios where idfunc=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtFuncId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionário Removido com Sucesso");
                    // as linhas abaixo limpam os campos
                    txtFuncId.setText(null);
                    txtFuncNome.setText(null);
                    txtFuncEndereco.setText(null);
                    txtFuncBairro.setText(null);
                    txtFuncCidade.setText(null);
                    txtFuncCpf.setText(null);
                    txtFuncDataNasc.setText(null);
                    txtFuncFone.setText(null);
                    txtFuncEmail.setText(null);
                    // Reabiltar o botão Cadastrar após a alteração do cliente realizada.
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
        txtFuncPesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFuncionarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtFuncId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFuncNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFuncEndereco = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFuncBairro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFuncCidade = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFuncCpf = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFuncDataNasc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFuncFone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtFuncEmail = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Funcionários");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Pesquisar nome funcionário:");

        txtFuncPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFuncPesquisarKeyReleased(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/pesqui.png"))); // NOI18N

        tblFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFuncionariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFuncionarios);

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("ID Cliente:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("*Nome:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("Endereço:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("Bairro:");

        jLabel7.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel7.setText("Cidade:");

        jLabel8.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel8.setText("CPF:");

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel9.setText("Nascimento:");

        jLabel10.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel10.setText("Celular:");

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel11.setText("E-mail:");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(txtFuncPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(36, 36, 36)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addComponent(jLabel10))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtFuncFone, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtFuncCpf, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtFuncBairro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel9))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtFuncCidade)
                                                .addComponent(txtFuncDataNasc, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(52, 52, 52)
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtFuncEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))))
                                .addComponent(txtFuncEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFuncNome, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFuncId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFuncPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFuncId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtFuncNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtFuncEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtFuncBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtFuncCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFuncCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(txtFuncDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtFuncFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtFuncEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 99, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAlterar)
                            .addComponent(btnRemover)
                            .addComponent(btnAdicionar))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // método para adicionar clientes
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chamando o metodo remover cliente
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtFuncPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFuncPesquisarKeyReleased
        // chamar metodo pesquisar clientes por numero do Telefone
        pesquisar_cliente();
    }//GEN-LAST:event_txtFuncPesquisarKeyReleased

    private void tblFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFuncionariosMouseClicked
        // Chamando o metodo para setar campos
        setar_campos();
    }//GEN-LAST:event_tblFuncionariosMouseClicked
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFuncionarios;
    private javax.swing.JTextField txtFuncBairro;
    private javax.swing.JTextField txtFuncCidade;
    private javax.swing.JTextField txtFuncCpf;
    private javax.swing.JTextField txtFuncDataNasc;
    private javax.swing.JTextField txtFuncEmail;
    private javax.swing.JTextField txtFuncEndereco;
    private javax.swing.JTextField txtFuncFone;
    private javax.swing.JTextField txtFuncId;
    private javax.swing.JTextField txtFuncNome;
    private javax.swing.JTextField txtFuncPesquisar;
    // End of variables declaration//GEN-END:variables
}
