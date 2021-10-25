package br.com.lanchonete.telas;

import br.com.lanchonete.dal.ModuloConexao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 */
public class PedidosBalcao extends javax.swing.JFrame {

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
     * Creates new form PedidosBalcao
     */
    public PedidosBalcao() {
        // adicionar cor no Jframe
        this.getContentPane().setBackground(Color.ORANGE);    
        this.setVisible(true);
        initComponents();
        conexao = ModuloConexao.conector();

        txtValorTotalPedido.setText("0.00");
        txtValorAcumulado.setText("0.00");
        txtTroco.setText("0.00");
        txtValorPago.setText("0.00");
        txtQtdEntrada.setText("0");

        // Proteger os componetes de tela contra seleção inesperada pelo Usuário
        // a Linha abaixo Deixar apenas o Campo de Texto Pesquisar Cliente  ablitado    
        // as  linhas abaixo deixa todoso demais Campos de Texto, Tabelas  e Botões também Desablitado 
        txtLanchePesquisar.setEditable(true);
        tblLanches.setVisible(false);
        txtBebidaPesquisar.setEditable(true);
        tblBebidas.setVisible(false);
        btnAdicionarItem.setEnabled(false);
        btnRemoverItem.setEnabled(false);
        tblItensPedido.setEnabled(false);
        /* as linhas abaixo substituem a label lblData pela data atual do sistema ao iniciar o form */
        java.util.Date data = new java.util.Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.DEFAULT);
        lblData.setText(formatador.format(data));
        //Mostrar Hora
        String thora = "" + data.getHours();
        String tminuto = "" + data.getMinutes();
        String tsegundo = "" + data.getSeconds();
        if (Integer.parseInt(tminuto) <= 9) {
            tminuto = "0" + tminuto;
        }
        if (Integer.parseInt(tsegundo) <= 9) {
            tsegundo = "0" + tsegundo;
        }
        txtHora.setText(thora + ":" + tminuto + ":" + tsegundo);
        txtHora.setEditable(false);
    }

    // metodo para pesquisar lanche com filtro 
    private void pesquisar_lanche() {
        String sql = " select idlanche as ID, nomelanche as Lanche,  valorlanche as Valor, descricaolanche as Descricao, tamanholanche as Tamanho, paolanche as Tipo_Pao from tblanches where nomelanche like ? ";
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
        // Ablitar o Botão Adicionar Item no Pedio Após Escolha do Lanche ou Bebida
        btnAdicionarItem.setEnabled(true);
        // a linha abaixo ablita para o usuário escolher o Item da Tabela Lanche
        tblLanches.setVisible(true);
        txtLanchePesquisar.setText(null);
        txtLancheId.setText(null);
        txtBebidaPesquisar.setText(null);
        txtBebidaId.setText(null);
        txtItemEntrada.setText(null);
        txtValorEntrada.setText(null);
        txtQtdEntrada.setText("0");
        txtTotalEntrada.setText(null);
    }

    // metodo para pesquisar bebida com filtro 
    private void pesquisar_bebida() {
        String sql = " select idbebida as ID, nomebebida as Nome, valorbebida as Valor, volumebebida as Volume_Ml from  tbbebidas where nomebebida like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtBebidaPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela bebida
            tblBebidas.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // Ablitar o Botão Adicionar Item no Pedio Após Escolha do Lanche ou Bebida
        btnAdicionarItem.setEnabled(true);
        // a linha abaixo ablita para o usuário escolher o Item da Tabela Bebida
        tblBebidas.setVisible(true);
        txtLanchePesquisar.setText(null);
        txtLancheId.setText(null);
        txtBebidaPesquisar.setText(null);
        txtBebidaId.setText(null);
        txtItemEntrada.setText(null);
        txtValorEntrada.setText(null);
        txtQtdEntrada.setText("0");
        txtTotalEntrada.setText(null);
    }

    // metodo para setar os campos do formulário com os conteudos da tabela lanches
    public void setarcamposlanche() {
        int setar = tblLanches.getSelectedRow();
        txtLancheId.setText(tblLanches.getModel().getValueAt(setar, 0).toString());
        txtItemEntrada.setText(tblLanches.getModel().getValueAt(setar, 1).toString());
        txtValorEntrada.setText(tblLanches.getModel().getValueAt(setar, 2).toString().replace(",", "."));
        txtIdItemEntrada.setText(tblLanches.getModel().getValueAt(setar, 0).toString());
        // as  linhas abaixo a Tabelas Lanche Desablitado  apos seleção
        tblLanches.setVisible(false);
        // linhas abaixo abilitam as Caixas de Texto Pesquisar Lanche e Bebida
        txtLanchePesquisar.setEditable(true);
        txtBebidaPesquisar.setEditable(true);
    }

    // metodo para setar os campos do formulário com os conteudos da tabela bebidas
    public void setarcamposbebida() {
        int setar = tblBebidas.getSelectedRow();
        txtBebidaId.setText(tblBebidas.getModel().getValueAt(setar, 0).toString());
        txtItemEntrada.setText(tblBebidas.getModel().getValueAt(setar, 1).toString());
        txtValorEntrada.setText(tblBebidas.getModel().getValueAt(setar, 2).toString().replace(",", "."));
        txtIdItemEntrada.setText(tblBebidas.getModel().getValueAt(setar, 0).toString());
        // as  linhas abaixo a Tabelas Bedidas Desablitado  apos seleção
        tblBebidas.setVisible(false);
        // linhas abaixo abilitam as Caixas de Texto Pesquisar Lanche e Bebida
        txtLanchePesquisar.setEditable(true);
        txtBebidaPesquisar.setEditable(true);
    }

    // método para adicionar Item Pedido
    private void itempedido() {
        String sql = "insert into tbitensvenda (nome, qtd, valor, total, nomecli, iditem, tipo, mesa) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtItemEntrada.getText());
            pst.setString(2, txtQtdEntrada.getText());
            pst.setString(3, txtValorEntrada.getText().replace(",", "."));
            // Multiplicar a quantidade pelo valor setar total
            double qtd = Double.parseDouble(txtQtdEntrada.getText());
            double valor = Double.parseDouble(txtValorEntrada.getText().replace(",", "."));
            double total = qtd * valor;
            txtTotalEntrada.setText(String.valueOf(total));
            pst.setString(4, txtTotalEntrada.getText().replace(",", "."));
            pst.setString(5, txtCliNome.getText());
            pst.setString(6, txtIdItemEntrada.getText());
            pst.setString(7, txtTipo.getText());
            pst.setString(8, txtMesa.getText());
            if (txtQtdEntrada.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Quantidade de Item (*)");
            } else {
                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Item Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtLanchePesquisar.setText(null);
                    txtLancheId.setText(null);
                    txtBebidaPesquisar.setText(null);
                    txtBebidaId.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        // as  linhas abaixo a Tabelas Clientes, Lanche e Bebidas Desablitado  apos Adicionar Pedido
        tblLanches.setVisible(true);
        tblBebidas.setVisible(true);
        tblItensPedido.setVisible(true);
    }

    // metodo para setar os campos do formulário com os conteudos da tabela tbitensvenda Saida
    public void setar_campos_pedido() {
        int setar = tblItensPedido.getSelectedRow();
        txtItemSaida.setText(tblItensPedido.getModel().getValueAt(setar, 0).toString());
        txtQtdItemSaida.setText(tblItensPedido.getModel().getValueAt(setar, 1).toString());
        txtValorItemSaida.setText(tblItensPedido.getModel().getValueAt(setar, 2).toString());
        txtTotalSaida.setText(tblItensPedido.getModel().getValueAt(setar, 3).toString());
        txtCliNome.setText(tblItensPedido.getModel().getValueAt(setar, 4).toString());
        // a linha abaixo desabilita o Botõa Adicionar e Pesquisar item Quando o usuário selecionar algum item.
        btnAdicionarItem.setEnabled(false);
        // as linhas abaixo abilitam os botões Excluir Item e Alterar item da tabela itens pedido
        btnRemoverItem.setEnabled(true);
    }

    // metodo responssável pela remoção de Item da tabela item venda
    private void remover() {
        //a estrutura abaixo confirma a exclusão do Item 
        int confirma = JOptionPane.showConfirmDialog(null, "Atenção Deseja Exclui este Item do Pedido ?", "Atenção!!!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbitensvenda where nome=? and qtd=? and valor=? and idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtItemSaida.getText());
                pst.setString(2, txtQtdItemSaida.getText());
                pst.setString(3, txtValorItemSaida.getText());
                pst.setString(4, txtCliNome.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Item Removido com Sucesso");
                    // as linhas abaixo limpam os campos
                    txtLanchePesquisar.setText(null);
                    txtLancheId.setText(null);
                    txtBebidaPesquisar.setText(null);
                    txtBebidaId.setText(null);
                    txtItemEntrada.setText(null);
                    txtValorEntrada.setText(null);
                    txtQtdEntrada.setText("0");
                    txtTotalEntrada.setText(null);
                    txtItemSaida.setText(null);
                    txtValorItemSaida.setText(null);
                    txtQtdItemSaida.setText(null);
                    txtTotalSaida.setText(null);
                    txtIdItemSaida.setText(null);
                    txtIdItemEntrada.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        // alinha abaixo desabilita o botão excluir
        btnRemoverItem.setEnabled(false);
    }

    // metodo para somar os itens pedido
    private void soma() {
        double valorAcumulado;
        double valorUnitario;
        double quantidade;
        double valorPago;
        double troco;
        double total;
        // Linhas abaixo pega valores dos valores Jtextfield
        valorAcumulado = Double.parseDouble(txtValorAcumulado.getText().replace(",", "."));
        valorUnitario = Double.parseDouble(txtValorEntrada.getText().replace(",", "."));
        quantidade = Double.parseDouble(txtQtdEntrada.getText().replace(",", "."));
        valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
        // Calcula o valor Unitário * Quantidade e Soma com Valor já pedido.
        total = (valorUnitario * quantidade) + valorAcumulado;
        // Calcula o troco
        troco = valorPago - total;
        // Retorna os valores calculados.
        DecimalFormat df = new DecimalFormat("###.00");
        txtValorTotalPedido.setText(df.format(total).replace(",", "."));
        txtTroco.setText(df.format(troco).replace(",", "."));
        // Setar valor 0 no Total acumulado para não calcular novamente
        txtValorAcumulado.setText(df.format(total).replace(",", "."));
    }

    // metodo para calcular o troco itens pedido
    private void troco() {
        double valorPago;
        double troco;
        double total;
        // Linhas abaixo pega valores dos valores Jtextfield
        total = Double.parseDouble(txtValorTotalPedido.getText().replace(",", "."));
        valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
        // Calcula o valor Unitário * Quantidade e Soma com Valor já pedido.
        // Calcula o troco
        troco = valorPago - total;
        // Retorna os valores calculados.
         DecimalFormat df = new DecimalFormat("###.00");
        txtValorTotalPedido.setText(df.format(total).replace(",", "."));
        txtTroco.setText(df.format(troco).replace(",", ".")); 
        // Setar valor 0 no Total acumulado para não calcular novamente
    }

    //metodo para subtrair valor do item excluido
    private void subtrair() {
        double ValorTotalPedido;
        double TotalSaida;
        double valorPago;
        double troco;
        double total;
        // Linhas abaixo pega valores dos valores Jtextfield
        ValorTotalPedido = Double.parseDouble(txtValorTotalPedido.getText().replace(",", "."));
        TotalSaida = Double.parseDouble(txtTotalSaida.getText().replace(",", "."));
        valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
        // Calcula o valor Unitário * Quantidade e Soma com Valor já pedido.
        total = ValorTotalPedido - TotalSaida;
        // Calcula o troco
        troco = valorPago - total;
        // Retorna os valores calculados.
       // Converter Double em 2 casas decimais
        DecimalFormat df = new DecimalFormat("###.00");
        txtValorTotalPedido.setText(df.format(total).replace(",", "."));
        txtTroco.setText(df.format(troco).replace(",", "."));
    }

    // método para adicionar Item Pedido
    private void adicionarvenda() {
        String sql = "insert into tbvenda ( totalvenda, tipo, situacao, pagto, troco, nomecli, mesa, iditemvenda, nome) values(?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtValorTotalPedido.getText().replace(",", "."));
            pst.setString(2, txtTipo.getText());
            pst.setString(3, cboStatus.getSelectedItem().toString());
            pst.setString(4, cboFormaPagto.getSelectedItem().toString());
            pst.setString(5, txtTroco.getText());
            pst.setString(6, txtCliNome.getText());
            pst.setString(7, txtMesa.getText());
            pst.setString(8, txtIdItemEntrada.getText());
            pst.setString(9, txtItemEntrada.getText());
            if (txtCliNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Nome do Cliente");
            } else {
                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtLanchePesquisar.setText(null);
                    txtLancheId.setText(null);
                    txtBebidaPesquisar.setText(null);
                    txtBebidaId.setText(null);
                    txtItemEntrada.setText(null);
                    txtValorEntrada.setText(null);
                    txtQtdEntrada.setText("0");
                    txtTotalEntrada.setText(null);
                    txtItemSaida.setText(null);
                    txtValorItemSaida.setText(null);
                    txtQtdItemSaida.setText(null);
                    txtTotalSaida.setText(null);
                    txtIdItemSaida.setText(null);
                    txtNumVenda.setText(null);
                    txtValorTotalPedido.setText("0.00");
                    txtCliNome.setText(null);
                    txtValorAcumulado.setText("0.00");
                    txtIdItemEntrada.setText(null);
                    txtValorPago.setText("0.00");
                    txtTroco.setText("0.00");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // as  linhas abaixo a Tabelas Clientes, Lanche e Bebidas Desablitado  apos Adicionar Pedido
        tblLanches.setVisible(false);
        tblBebidas.setVisible(false);
        tblItensPedido.setVisible(false);

    }

    // metodo par pesquisar uma venda
    private void pesquisar_venda() {
        // a linha abaixo cria uma caixa de entrada do tipo JOptionPane
        String num_venda = JOptionPane.showInputDialog("Numero do Pedido Balcao");
        String sql = "select datavenda, situacao, tipo, totalvenda, pagto, troco, nomecli, idvenda from tbvenda  where tbvenda.tipo ='Balcao' and tbvenda.situacao <> 'Finalizado' and tbvenda.idvenda= " + num_venda;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                
                lblData.setText(rs.getString(1));
                cboStatus.setSelectedItem(rs.getString(2));
                txtTipo.setText(rs.getString(3));
                txtValorAcumulado.setText(rs.getString(4));
                cboFormaPagto.setSelectedItem(rs.getString(5));
                txtTroco.setText(rs.getString(6));
                txtCliNome.setText(rs.getString(7));
                txtNumVenda.setText(rs.getString(8));
            } else {
                JOptionPane.showMessageDialog(null, "Pedido não cadastrado!!!");
            }
        /*
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Pedido Inválido!!!");
            //System.out.println(e);
        */
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
        // evitando problemas para não haver recadastro ou possivel troca de cliente
        btnAdicionar.setEnabled(false);
        btnLimparCampos.setEnabled(false);
        btnTroco.setEnabled(false);
        tblLanches.setVisible(true);
        tblBebidas.setVisible(true);
    }

    // metodo para alterar Pedido
    private void alterar_venda() {
        String sql = "update tbvenda set totalvenda=?, situacao=?, tipo=?,  pagto=?, troco=? where idvenda=? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtValorTotalPedido.getText().replace(",", "."));
            pst.setString(2, cboStatus.getSelectedItem().toString());
            pst.setString(3, txtTipo.getText());
            pst.setString(4, cboFormaPagto.getSelectedItem().toString());
            pst.setString(5, txtTroco.getText());
            pst.setString(6, txtNumVenda.getText());

            if (txtNumVenda.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Numero do Pedido");
            } else {
                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtLanchePesquisar.setText(null);
                    txtLancheId.setText(null);
                    txtBebidaPesquisar.setText(null);
                    txtBebidaId.setText(null);
                    txtItemEntrada.setText(null);
                    txtValorEntrada.setText(null);
                    txtQtdEntrada.setText("0");
                    txtTotalEntrada.setText(null);
                    txtItemSaida.setText(null);
                    txtValorItemSaida.setText(null);
                    txtQtdItemSaida.setText(null);
                    txtTotalSaida.setText(null);
                    txtIdItemSaida.setText(null);
                    txtNumVenda.setText(null);
                    txtValorTotalPedido.setText("0,00");
                    txtCliNome.setText(null);
                    txtTroco.setText("0,0");
                    txtValorAcumulado.setText("0,0");
                    lblData.setVisible(false);
                    txtIdItemEntrada.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // as  linhas abaixo a Tabelas Clientes, Lanche e Bebidas Abilitado  apos Alterar Pedido
        tblLanches.setVisible(false);
        tblBebidas.setVisible(false);
        tblItensPedido.setVisible(false);

    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        txtMesa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        lblData = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLanchePesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLancheId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumVenda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLanches = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtBebidaPesquisar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBebidaId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBebidas = new javax.swing.JTable();
        txtItemEntrada = new javax.swing.JTextField();
        txtValorEntrada = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtQtdEntrada = new javax.swing.JTextField();
        txtTotalEntrada = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtIdItemEntrada = new javax.swing.JTextField();
        btnAdicionarItem = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblItensPedido = new javax.swing.JTable();
        txtItemSaida = new javax.swing.JTextField();
        txtValorItemSaida = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtQtdItemSaida = new javax.swing.JTextField();
        txtTotalSaida = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIdItemSaida = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtValorAcumulado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtValorTotalPedido = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTroco = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cboFormaPagto = new javax.swing.JComboBox<>();
        btnAdicionar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnLimparCampos = new javax.swing.JButton();
        btnTroco = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedido Balcão");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Cliente:");

        txtTipo.setForeground(new java.awt.Color(255, 0, 51));
        txtTipo.setText("Balcao");
        txtTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoActionPerformed(evt);
            }
        });

        txtMesa.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel2.setText("Status:");

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aberto", "Fechado", " " }));

        lblData.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblData.setText("Data:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("Lanche:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("ID Lanche:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("N° Pedido:");

        tblLanches.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Lanche", "Valor Unit", "Composição", "Tamanho", "Título 6"
            }
        ));
        jScrollPane1.setViewportView(tblLanches);

        jLabel7.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel7.setText("Bebida:");

        jLabel8.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel8.setText("ID Bebida:");

        tblBebidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Bebida", "Valor Unit", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblBebidas);

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel9.setText("QTD:");

        jLabel10.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel10.setText("ID Item:");

        btnAdicionarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/add.png"))); // NOI18N

        btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/cancelbutton_85722.png"))); // NOI18N

        tblItensPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item", "Quantidade", "Valor Unit", "Total", "Cliente"
            }
        ));
        jScrollPane3.setViewportView(tblItensPedido);

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel11.setText("QTD:");

        jLabel12.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel12.setText("ID Item:");

        jLabel13.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel13.setText("Acumulado:");

        jLabel14.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel14.setText("Dinheiro:");

        jLabel15.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel15.setText("Total:");

        jLabel16.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel16.setText("Troco:");

        jLabel17.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel17.setText("Forma de pagamento:");

        cboFormaPagto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão de crédito", "Cartão de débito", " " }));

        btnAdicionar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/criar.png"))); // NOI18N

        btnPesquisar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/pesqui.png"))); // NOI18N

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/atuali.png"))); // NOI18N

        btnLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/limpar.png"))); // NOI18N

        btnTroco.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnTroco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/calcular.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtLanchePesquisar)))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblData)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNumVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBebidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBebidaId, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQtdEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboFormaPagto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel15))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtValorItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtQtdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel13))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValorAcumulado)
                            .addComponent(txtValorPago)
                            .addComponent(txtValorTotalPedido)
                            .addComponent(txtTroco, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblData)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLanchePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNumVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBebidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtBebidaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionarItem)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtQtdEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtIdItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRemoverItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtQtdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtIdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtValorAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtValorTotalPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(cboFormaPagto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTroco)
                    .addComponent(btnLimparCampos)
                    .addComponent(btnAlterar)
                    .addComponent(btnPesquisar)
                    .addComponent(btnAdicionar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//Linhas abaixo correspondem aos eventos :
    
    private void txtTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoActionPerformed
    }//GEN-LAST:event_txtTipoActionPerformed

     private void txtLanchePesquisarActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        tblLanches.setEnabled(true);
    }                                                  

    private void txtLanchePesquisarKeyReleased(java.awt.event.KeyEvent evt) {                                               
        // chmar o metodo pesquisar lanche por nome
        pesquisar_lanche();
        tblLanches.setEnabled(true);
    }                                              

    private void cboStatusActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void tblLanchesMouseClicked(java.awt.event.MouseEvent evt) {                                        
        JOptionPane.showMessageDialog(null, "Não Esqueça Inserir Quantidade");
        // Chamando o metodo para setar campos lanche
        setarcamposlanche();
        tblItensPedido.setEnabled(true);
    }                                       

    private void txtBebidaPesquisarActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // Abilitar tabela bebida para ecolha
        tblBebidas.setEnabled(true);
    }                                                  

    private void txtBebidaPesquisarKeyReleased(java.awt.event.KeyEvent evt) {                                               
        // chmar o metodo pesquisar bebida por nome
        pesquisar_bebida();
        tblBebidas.setEnabled(true);
    }                                              

    private void tblBebidasMouseClicked(java.awt.event.MouseEvent evt) {                                        
        JOptionPane.showMessageDialog(null, "Não Esqueça Inserir Quantidade");
        // Chamando o metodo para setar campos bebida
        setarcamposbebida();
        tblItensPedido.setEnabled(true);
    }                                       

    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // chamando o metodo adicionar Lanche     Testado OK*
        itempedido();
        // Inserir itens selecionado no campo de texto para a Tabela de Pedido.
        DefaultTableModel model = (DefaultTableModel) tblItensPedido.getModel();
        model.addRow(new Object[]{txtItemEntrada.getText(), txtQtdEntrada.getText(), txtValorEntrada.getText(), txtTotalEntrada.getText(), txtCliNome.getText()});
        // chamando o metodo Soma itens Pedido
        soma();
    }                                                

    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        //Chamando o metodo Remover Lanche
        remover();
        // chamando o metodo subtrair itens Pedido
        subtrair();
        // Linha abaixo Limpa tblPedido
        ((DefaultTableModel) tblItensPedido.getModel()).removeRow(tblItensPedido.getSelectedRow());
    }                                              

    private void tblItensPedidoMouseClicked(java.awt.event.MouseEvent evt) {                                            
        // Chamando o metodo Selecionar Item Pedido
        setar_campos_pedido();
    }                                           

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // método para adicionar venda
        adicionarvenda();
    }                                            

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        //Chamar o metodo Pesquisar Venda
        pesquisar_venda();
        pesquisar_lanche();
        pesquisar_bebida();
    }                                            

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // chamando o método para alterar Pedido
        alterar_venda();
    }                                          

    private void btnLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // Limpar campos do Formulário
        JOptionPane.showMessageDialog(null, "Formulário Limpo !!!");
        // as linhas abaixo limpam os campos
        txtLanchePesquisar.setText(null);
        txtLancheId.setText(null);
        txtBebidaPesquisar.setText(null);
        txtBebidaId.setText(null);
        txtItemEntrada.setText(null);
        txtValorEntrada.setText(null);
        txtQtdEntrada.setText("0");
        txtTotalEntrada.setText(null);
        txtItemSaida.setText(null);
        txtValorItemSaida.setText(null);
        txtQtdItemSaida.setText(null);
        txtTotalSaida.setText(null);
        txtIdItemSaida.setText(null);
        txtNumVenda.setText(null);
        txtValorTotalPedido.setText("0.00");
        txtCliNome.setText(null);
        txtTroco.setText("0,0");
        txtValorAcumulado.setText("0.00");
    }                                               

    private void btnTrocoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // chamar metodo calcular Troco
        troco();
    }                                        

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnLimparCampos;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JButton btnTroco;
    private javax.swing.JComboBox<String> cboFormaPagto;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblData;
    private javax.swing.JTable tblBebidas;
    private javax.swing.JTable tblItensPedido;
    private javax.swing.JTable tblLanches;
    private javax.swing.JTextField txtBebidaId;
    private javax.swing.JTextField txtBebidaPesquisar;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtIdItemEntrada;
    private javax.swing.JTextField txtIdItemSaida;
    private javax.swing.JTextField txtItemEntrada;
    private javax.swing.JTextField txtItemSaida;
    private javax.swing.JTextField txtLancheId;
    private javax.swing.JTextField txtLanchePesquisar;
    private javax.swing.JTextField txtMesa;
    private javax.swing.JTextField txtNumVenda;
    private javax.swing.JTextField txtQtdEntrada;
    private javax.swing.JTextField txtQtdItemSaida;
    private javax.swing.JTextField txtTipo;
    private javax.swing.JTextField txtTotalEntrada;
    private javax.swing.JTextField txtTotalSaida;
    private javax.swing.JTextField txtTroco;
    private javax.swing.JTextField txtValorAcumulado;
    private javax.swing.JTextField txtValorEntrada;
    private javax.swing.JTextField txtValorItemSaida;
    private javax.swing.JTextField txtValorPago;
    private javax.swing.JTextField txtValorTotalPedido;
    // End of variables declaration//GEN-END:variables
}
