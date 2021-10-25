package br.com.lanchonete.telas;

import br.com.lanchonete.dal.ModuloConexao;
import java.awt.Color;
//linhas abaixo importa biblotecas referentes altreção Backgraund do jDesktopPane

import java.awt.image.BufferedImage;
import java.io.InputStream;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.imageio.ImageIO;

import net.proteanit.sql.DbUtils;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.view.JasperViewer;
/**
 *
 */
public class TelaPrincipal extends javax.swing.JFrame {

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
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        // adicionar cor no Jframe
        this.getContentPane().setBackground(Color.LIGHT_GRAY); 
        this.setVisible(true);
     
        initComponents();
        conexao = ModuloConexao.conector();
        txtDinheiro.setText("0.00");
        txtTroco.setText("0.00");
    }

    // metodo para inserir imagem backgruond
    public void carregarImagem(javax.swing.JDesktopPane jDeskp, InputStream fileImagen) {
        try {
            BufferedImage image = ImageIO.read(fileImagen);
//            jDeskp.setBorder(new Background(image));
        } catch (Exception e) {
            System.out.println("Imagem não Disponível");
        }
    }

    // metodo para pesquisar clientes pelo nome com filtro
    private void pesquisar_pedido_venda_nome() {
        String sql = "select tbvenda.idvenda as Pedido, tbvenda.nomecli as Cliente, tbvenda.situacao as Status, tbvenda.tipo as Tipo, tbvenda.totalvenda as Valor, tbvenda.mesa as Mesa from tbvenda \n"
                + "where  tbvenda.situacao <> 'Finalizado' and tbvenda.nomecli like ? ";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtPedidoPesquisarNome.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblPedidoVenda.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tblPedidoVenda.setVisible(true);
    }

 
    // metodo para pesquisar clientes pelo nome com filtro
    private void pesquisar_pedido_venda_mesa() {
        String sql = "select tbvenda.idvenda as Pedido, tbvenda.nomecli as Cliente, tbvenda.situacao as Status, tbvenda.tipo as Tipo, tbvenda.totalvenda as Valor, tbvenda.mesa as Mesa from tbvenda \n"
                + "where  tbvenda.situacao <> 'Finalizado' and tbvenda.mesa like ? ";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtPedidoPesquisarMesa.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblPedidoVenda.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        tblPedidoVenda.setVisible(true);
    }
 
    // metodo para setar os campos do formulário venda com os conteudos da tabela
    public void setar_campos_venda() {
        int setar = tblPedidoVenda.getSelectedRow();
        txtIdVenda.setText(tblPedidoVenda.getModel().getValueAt(setar, 0).toString());
        txtNomeCli.setText(tblPedidoVenda.getModel().getValueAt(setar, 1).toString());
        txtSituacao.setText(tblPedidoVenda.getModel().getValueAt(setar, 2).toString());
        txtTipo.setText(tblPedidoVenda.getModel().getValueAt(setar, 3).toString());
        txtTotalVenda.setText(tblPedidoVenda.getModel().getValueAt(setar, 4).toString());
        txtMesa.setText(tblPedidoVenda.getModel().getValueAt(setar, 5).toString());
        // Chamar metodo Contar Tipo pedio
        contar_tipo_pedido();
        tblPedidoVenda.setVisible(false);
    }

    // metodo para somar os itens pedido
    private void troco() {
        double totalVenda;
        double dinheiro;
        double troco;
        // Linhas abaixo pega valores dos valores Jtextfield
        totalVenda = Double.parseDouble(txtTotalVenda.getText().replace(",", "."));
        dinheiro = Double.parseDouble(txtDinheiro.getText().replace(",", "."));
        // Calcula o troco
        troco = (dinheiro - totalVenda);
        // Converter Double em 2 casas decimais
        DecimalFormat df = new DecimalFormat("###.00");
        // Retorna os valores calculados.
        txtTroco.setText(df.format(troco).replace(",", "."));
    }
 
      // metodo finalizar Pedido
    private void finalizar_pedido() {
        String sql = "update tbvenda set nomecli=?, situacao= 'Finalizado' where idvenda=?  ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCli.getText());
            pst.setString(2, txtIdVenda.getText());
            if (txtIdVenda.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pedido A Ser Finalizado ?");

            } else {
                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                txtSituacao.setText("Finalizado");
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.

                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido Finalizado com Sucesso !!!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tblPedidoVenda.setVisible(false);
        txtPedidoPesquisarNome.setText(null);
    }

    public void contar_tipo_pedido() {
        int cont = 0;
        int contbalcao = 0;
        int contdelivery = 0;
        int contmesa = 0;
        for (int i = 0; i < tblPedidoVenda.getRowCount(); i++) {
            if (tblPedidoVenda.getValueAt(i, 3).toString().equals("Balcao")) {
                contbalcao++;
            } else {
                if (tblPedidoVenda.getValueAt(i, 3).toString().equals("Delivery")) {
                    contdelivery++;
                } else {
                    if (tblPedidoVenda.getValueAt(i, 3).toString().equals("Mesa")) {
                        contmesa++;
                    }
                }
            }
            cont++;
        }
        // atribuir valor as label com soma dos tipos de copra
        lblStatus.setText(Integer.toString(cont));
        lblBalcao.setText(Integer.toString(contbalcao));
        lblDelivery.setText(Integer.toString(contdelivery));
        lblMesa1.setText(Integer.toString(contmesa));
    }

    // metodo para imprimir pedido
    private void imprimir_pedido() {
        // imprimrido um pedido
        /*
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Impressão deste Pedido ?", "Atenção !!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // Emitindo o relatório com o Jaspersoft Studio
            try {
                // usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("NumeroPedido", Integer.parseInt(txtIdVenda.getText()));
                // usando a classe JasperPrint para preparar a impressão do relatório
                JasperPrint print = JasperFillManager.fillReport("C:/reports/MyReports/Pedido.jasper", filtro, conexao);
                // a linha abaixo apresenta o relatótio através do JasperViewr
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        */
    }

    // metodo para imprimir pedido
    private void imprimir_pedido_Delivery() {
        // imprimrido um pedido
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Impressão deste Pedido ?", "Atenção !!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // Emitindo o relatório com o Jaspersoft Studio
            try {
                // usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("NumPedido", Integer.parseInt(txtIdVenda.getText()));
                // usando a classe JasperPrint para preparar a impressão do relatório
//                JasperPrint print = JasperFillManager.fillReport("C:/reports/MyReports/Delivery.jasper", filtro, conexao);
                // a linha abaixo apresenta o relatótio através do JasperViewr
//                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Atenção Opção Válida Para Delivery ");
            }
        }

    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        txtPedidoPesquisarNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPedidoPesquisarMesa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedidoVenda = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdVenda = new javax.swing.JTextField();
        txtNomeCli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMesa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSituacao = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblBalcao = new javax.swing.JLabel();
        lblDelivery = new javax.swing.JLabel();
        lblMesa1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDinheiro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTotalVenda = new javax.swing.JTextField();
        txtTroco = new javax.swing.JTextField();
        btnDelivery = new javax.swing.JButton();
        btnCalcular = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menCad = new javax.swing.JMenu();
        menOpcCadUsuario = new javax.swing.JMenuItem();
        menOpcCadCli = new javax.swing.JMenuItem();
        menCadFuncionario = new javax.swing.JMenuItem();
        menCadLanche = new javax.swing.JMenuItem();
        menCadBebida = new javax.swing.JMenuItem();
        menRel = new javax.swing.JMenu();
        menDelivery = new javax.swing.JMenuItem();
        menBalcao = new javax.swing.JMenuItem();
        menAjuda = new javax.swing.JMenu();
        menSobre = new javax.swing.JMenuItem();
        menOpc = new javax.swing.JMenu();
        menOpcSai = new javax.swing.JMenuItem();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lanchonete");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Nome do cliente:");

        txtPedidoPesquisarNome.setSelectedTextColor(new java.awt.Color(240, 240, 240));

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel2.setText("Número da mesa:");

        tblPedidoVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "N° pedido", "Cliente", "Status", "Tipo", "Total", "N° mesa"
            }
        ));
        tblPedidoVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPedidoVendaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPedidoVenda);

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("N° Pedido:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("Cliente:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("Tipo:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("N° Mesa:");

        jLabel7.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel7.setText("Status:");

        lblUsuario.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        lblUsuario.setText("Usuário");

        lblData.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        lblData.setText("Data");

        jLabel8.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("FILA PEDIDOS:");

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("BALCAO:");

        jLabel10.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("DELIVERY:");

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("MESA:");

        lblStatus.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 0, 51));
        lblStatus.setText("QTD");

        lblBalcao.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblBalcao.setForeground(new java.awt.Color(255, 0, 51));
        lblBalcao.setText("BALCAO");

        lblDelivery.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblDelivery.setForeground(new java.awt.Color(255, 0, 51));
        lblDelivery.setText("DELIVERY");

        lblMesa1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblMesa1.setForeground(new java.awt.Color(255, 0, 51));
        lblMesa1.setText("MESA");

        jLabel12.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel12.setText("Dinheiro:");

        jLabel13.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel13.setText("Valor total:");

        jLabel14.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel14.setText("Troco:");

        btnDelivery.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnDelivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/deliv.png"))); // NOI18N
        btnDelivery.setText("Delivery");
        btnDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeliveryActionPerformed(evt);
            }
        });

        btnCalcular.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/calcular.png"))); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/limpar.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnFinalizar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/finalizar.png"))); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(255, 0, 204));
        jMenuBar1.setToolTipText("Lanchonete");

        menCad.setText("Cadastro");

        menOpcCadUsuario.setLabel("Usuário");
        menOpcCadUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcCadUsuarioActionPerformed(evt);
            }
        });
        menCad.add(menOpcCadUsuario);

        menOpcCadCli.setLabel("Cliente");
        menOpcCadCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcCadCliActionPerformed(evt);
            }
        });
        menCad.add(menOpcCadCli);

        menCadFuncionario.setLabel("Funcionário");
        menCadFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadFuncionarioActionPerformed(evt);
            }
        });
        menCad.add(menCadFuncionario);

        menCadLanche.setLabel("Lanche");
        menCadLanche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadLancheActionPerformed(evt);
            }
        });
        menCad.add(menCadLanche);

        menCadBebida.setLabel("Bebida");
        menCadBebida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadBebidaActionPerformed(evt);
            }
        });
        menCad.add(menCadBebida);

        jMenuBar1.add(menCad);

        menRel.setText("Pedidos");

        menDelivery.setText("Delivery");
        menDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menDeliveryActionPerformed(evt);
            }
        });
        menRel.add(menDelivery);

        menBalcao.setText("Balcão");
        menBalcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menBalcaoActionPerformed(evt);
            }
        });
        menRel.add(menBalcao);

        jMenuBar1.add(menRel);

        menAjuda.setText("Ajuda");

        menSobre.setText("Sobre");
        menSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menSobreActionPerformed(evt);
            }
        });
        menAjuda.add(menSobre);

        jMenuBar1.add(menAjuda);

        menOpc.setText("Opções");

        menOpcSai.setLabel("Sair");
        menOpcSai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcSaiActionPerformed(evt);
            }
        });
        menOpc.add(menOpcSai);

        jMenuBar1.add(menOpc);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtPedidoPesquisarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtPedidoPesquisarMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblMesa1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblStatus)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblDelivery)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel14)
                                                .addGap(16, 16, 16)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(txtTroco))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblBalcao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblData)
                        .addGap(181, 181, 181))))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCalcular)
                    .addComponent(btnFinalizar))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(lblData))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPedidoPesquisarMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPedidoPesquisarNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblStatus)
                    .addComponent(jLabel12)
                    .addComponent(txtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblBalcao)
                    .addComponent(txtTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblDelivery)
                    .addComponent(jLabel14)
                    .addComponent(txtTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblMesa1))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcular)
                    .addComponent(btnDelivery))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpar)
                    .addComponent(btnFinalizar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // chamar metodo Finalizar Pedido ... setar campo situação " Finalizado"
        finalizar_pedido();
// Limpar campos do Formulário
        txtIdVenda.setText(null);
        txtNomeCli.setText(null);
        txtTipo.setText(null);
        txtSituacao.setText(null);
        txtTotalVenda.setText("0.00");
        txtMesa.setText("0");
        txtTroco.setText("0.00");
        txtDinheiro.setText("0.00");
        tblPedidoVenda.setVisible(false);
        txtPedidoPesquisarNome.setText(null);
        JOptionPane.showMessageDialog(null, "Formulário Limpo !!!");
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // chamar metodo calcular Troco
        troco();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // as linhas abaixo limpam os campos
        txtIdVenda.setText(null);
        txtNomeCli.setText(null);
        txtTipo.setText(null);
        txtSituacao.setText(null);
        txtTotalVenda.setText("0.00");
        txtMesa.setText("0");
        txtDinheiro.setText("0.00");
        txtTroco.setText("0.00");
        tblPedidoVenda.setVisible(true);
        // Limpar campos do Formulário
        JOptionPane.showMessageDialog(null, "Formulário Limpo !!!");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeliveryActionPerformed
        // Chamando a classe Pedido Delivery
        PedidoDelivery pedido = new PedidoDelivery();
        pedido.setVisible(true);
//        desktop.add(pedido);
        // Apagar os Campos com valores 
        txtTotalVenda.setText("0.00");
        txtTroco.setText("0.00");
        txtDinheiro.setText("0.00");
    }//GEN-LAST:event_btnDeliveryActionPerformed

    private void tblPedidoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPedidoVendaMouseClicked
        // chamando o metodo setar campos da venda
        setar_campos_venda();
        tblPedidoVenda.setVisible(false);
    }//GEN-LAST:event_tblPedidoVendaMouseClicked

    private void menOpcSaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcSaiActionPerformed
         // exibe uma caixa de diálogo
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair ?", "Atenção !!!", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);        
        }                                         
    }//GEN-LAST:event_menOpcSaiActionPerformed

    private void menOpcCadUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcCadUsuarioActionPerformed
        // As linhas abaixo abre o form tela usuario dentro do Desktop pane
        TelaUsuario usuario = new TelaUsuario();
        usuario.setVisible(true);
//        desktop.add(usuario);
    }//GEN-LAST:event_menOpcCadUsuarioActionPerformed

    private void menOpcCadCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcCadCliActionPerformed
        // Chamando a Tela Cliente
        TelaCliente cliente = new TelaCliente();
        cliente.setVisible(true);
//        desktop.add(cliente);
    }//GEN-LAST:event_menOpcCadCliActionPerformed

    private void menCadFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadFuncionarioActionPerformed
        // Chamando a Tela Funcionario
        TelaFuncionario funcionario = new TelaFuncionario();
        funcionario.setVisible(true);
//        desktop.add(funcionario);
    }//GEN-LAST:event_menCadFuncionarioActionPerformed

    private void menCadLancheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadLancheActionPerformed
        // Chamando a classe Lanche
        TelaLanche lanche = new TelaLanche();
        lanche.setVisible(true);
//        desktop.add(lanche);
    }//GEN-LAST:event_menCadLancheActionPerformed

    private void menCadBebidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadBebidaActionPerformed
        // Chamando a classe Bebida
        TelaBebida bebida = new TelaBebida();
        bebida.setVisible(true);
//        desktop.add(bebida);
    }//GEN-LAST:event_menCadBebidaActionPerformed

    private void menSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menSobreActionPerformed
        // Chamando a classe Bebida
        TelaSobre sobre = new TelaSobre();
        sobre.setVisible(true);
//        desktop.add(bebida);
    }//GEN-LAST:event_menSobreActionPerformed

    private void menDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menDeliveryActionPerformed
        PedidoDelivery delivery = new PedidoDelivery();
        delivery.setVisible(true);
//        desktop.add(bebida);
    }//GEN-LAST:event_menDeliveryActionPerformed

    private void menBalcaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menBalcaoActionPerformed
        // Chamando a classe Bebida
        PedidosBalcao balcao = new PedidosBalcao();
        balcao.setVisible(true);
//        desktop.add(bebida);
    }//GEN-LAST:event_menBalcaoActionPerformed
 
 private void formWindowActivated(java.awt.event.WindowEvent evt) {                                     
        /* as linhas abaixo substituem a label lblData pela data atual do
        sistema ao iniciar o form
         */
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
    }                                    

    private void menRelCliActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //Gerando um Relatório de Clientes
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Impressão deste Relatório ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // imprimindo Relatório de Clientes com o framework JasperStudio
            try {
                // Usando a classe JasperPrint para preparar a impressão do relatório
//                JasperPrint print = JasperFillManager.fillReport("C:/reports/MyReports/ClientesLanchonete.jasper", null, conexao);
                // A linha abaixo exibe o relatório Cliente através da classe JasperViewer
//                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }                                         

  
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // chamando o metodo imprimir pedido
        //imprimir_pedido();
    }                                           

    private void txtPedidoPesquisarNomeActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        //Chamar o metodo pesquisar Pedido Mesa
        pesquisar_pedido_venda_mesa();
    }                                                      

    private void txtPedidoPesquisarNomeKeyReleased(java.awt.event.KeyEvent evt) {                                                   
        //Chamar o metodo pesquisar Pedido cliente por Nome
        pesquisar_pedido_venda_nome();
    }                                                  
      
    private void MenCadPedBalcaoActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // Chamando a classe Pedido Balcao
        PedidosBalcao pedido = new PedidosBalcao();
        pedido.setVisible(true);
//        desktop.add(pedido);
        // Apagar os Campos com valores 
        txtTotalVenda.setText("0.00");
        txtTroco.setText("0.00");
        txtDinheiro.setText("0.00");
    }                                               

    private void MenCadPedDeliveryActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // Chamando a classe Pedido Delivery
        PedidoDelivery pedido = new PedidoDelivery();
        pedido.setVisible(true);
//        desktop.add(pedido);
        // Apagar os Campos com valores 
        txtTotalVenda.setText("0.00");
        txtTroco.setText("0.00");
        txtDinheiro.setText("0.00");

    }                                                 

    private void btnBalcaoActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Chamando a classe Pedido Balcao
        PedidosBalcao pedido = new PedidosBalcao();
        pedido.setVisible(true);
//        desktop.add(pedido);
        // Apagar os Campos com valores 
        txtTotalVenda.setText("0.00");
        txtTroco.setText("0.00");
        txtDinheiro.setText("0.00");
    }                                         

    private void btnImprimirDeliveryActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        //Chamar Metodo Imprimir delivery
        imprimir_pedido_Delivery();
    }                                                   

    private void txtPedidoPesquisarMesaKeyReleased(java.awt.event.KeyEvent evt) {                                                   
       //Chamar o metodo pesquisar Pedido cliente por Nome
        pesquisar_pedido_venda_mesa();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnDelivery;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBalcao;
    public javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDelivery;
    private javax.swing.JLabel lblMesa1;
    private javax.swing.JLabel lblStatus;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu menAjuda;
    private javax.swing.JMenuItem menBalcao;
    public static javax.swing.JMenu menCad;
    private javax.swing.JMenuItem menCadBebida;
    private javax.swing.JMenuItem menCadFuncionario;
    private javax.swing.JMenuItem menCadLanche;
    private javax.swing.JMenuItem menDelivery;
    private javax.swing.JMenu menOpc;
    private javax.swing.JMenuItem menOpcCadCli;
    private javax.swing.JMenuItem menOpcCadUsuario;
    private javax.swing.JMenuItem menOpcSai;
    public static javax.swing.JMenu menRel;
    private javax.swing.JMenuItem menSobre;
    private javax.swing.JTable tblPedidoVenda;
    private javax.swing.JTextField txtDinheiro;
    private javax.swing.JTextField txtIdVenda;
    private javax.swing.JTextField txtMesa;
    private javax.swing.JTextField txtNomeCli;
    private javax.swing.JTextField txtPedidoPesquisarMesa;
    private javax.swing.JTextField txtPedidoPesquisarNome;
    private javax.swing.JTextField txtSituacao;
    private javax.swing.JTextField txtTipo;
    private javax.swing.JTextField txtTotalVenda;
    private javax.swing.JTextField txtTroco;
    // End of variables declaration//GEN-END:variables
}