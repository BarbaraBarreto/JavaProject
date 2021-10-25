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
public class PedidoDelivery extends javax.swing.JFrame {

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
     * Creates new form PedidoDelivery
     */
    public PedidoDelivery() {
        initComponents();
        // adicionar cor no Jframe
        this.getContentPane().setBackground(Color.GREEN);    
        this.setVisible(true);
        conexao = ModuloConexao.conector();
        // Popular JCombobox com nomes dos Funcionário.
        this.populaJComboBox();
        // Iniciar o Jtextbox com valor 0
        txtValorTotalPedido.setText("0.00");
        txtValorAcumulado.setText("0.00");
        txtTroco.setText("0.00");
        txtValorPago.setText("0.00");
        txtQtdEntrada.setText("0");

        // Proteger os componetes de tela contra seleção inesperada pelo Usuário
        // a Linha abaixo Deixar apenas o Campo de Texto Pesquisar Cliente  ablitado    
        txtCliPesquisar.setEditable(true);
        // as  linhas abaixo deixa todoso demais Campos de Texto, Tabelas  e Botões também Desablitado 
        tblClientes.setVisible(false);
        txtLanchePesquisar.setEditable(false);
        tblLanches.setVisible(false);
        txtBebidaPesquisar.setEditable(false);
        tblBebidas.setVisible(false);
        btnAdicionarItem.setEnabled(false);
        btnRemoverItem.setEnabled(false);
        tblItensPedido.setEnabled(false);
    }

    // metodo para pesquisar clientes pelo telefone com filtro 
    private void pesquisar_cliente() {
        String sql = " select idcli as ID, nomecli as Nome, endcli as Endereco, bairrocli as Bairro, cidadecli as Cidade, cpfcli as CPF, nasccli as DataNascimento, telcli as Telefone, emailcli as e_mailcli, celcli as Celular from tbclientes where telcli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o " ? "
            //atenção ao " % " - continuação da String sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
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
        // a linha abaixo ablita a tabela de Clientes para a escolha do cliente
        tblClientes.setVisible(true);

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

    // metodo para setar os campos do formulário com os conteudos da tabela clientes
    public void setarcamposcliente() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
        txtCliCelular.setText(tblClientes.getModel().getValueAt(setar, 9).toString());
        // as  linhas abaixo a Tabelas Cliente Desablitado após seleção
        tblClientes.setVisible(false);
        // linhas abaixo abilitam as Caixas de Texto Pesquisar Lanche e Bebida
        txtLanchePesquisar.setEditable(true);
        txtBebidaPesquisar.setEditable(true);
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
        String sql = "insert into tbitensvenda (nome, qtd, valor, total, idcli, nomecli, iditem, tipo, mesa) values(?,?,?,?,?,?,?,?,?)";
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
            pst.setString(5, txtCliId.getText());
            pst.setString(6, txtCliNome.getText());
            pst.setString(7, txtIdItemEntrada.getText());
            pst.setString(8, txtTipo.getText());
            pst.setString(9, txtMesa.getText());
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
        tblClientes.setVisible(false);
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
        txtCliId.setText(tblItensPedido.getModel().getValueAt(setar, 4).toString());
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
                pst.setString(4, txtCliId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Item Removido com Sucesso");
                    // as linhas abaixo limpam os campos
                    txtCliPesquisar.setText(null);
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
        // Converter Double em 2 casas decimais
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
        // Converter Double em 2 casas decimais
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

    // Popular Combobox com nomes de Motoboy
    public void populaJComboBox() {
        String sql = "select * from tbfuncionarios";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboMotoboy.addItem(rs.getString("nomefunc"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // método para adicionar Item Pedido
    private void adicionarvenda() {
        String sql = "insert into tbvenda ( totalvenda, idcli, nomefunc, tipo, situacao, pagto, troco, nomecli, mesa, iditemvenda, nome) values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtValorTotalPedido.getText().replace(",", "."));
            pst.setString(2, txtCliId.getText());
            pst.setString(3, cboMotoboy.getSelectedItem().toString());
            pst.setString(4, txtTipo.getText());
            pst.setString(5, cboStatus.getSelectedItem().toString());
            pst.setString(6, cboFormaPagto.getSelectedItem().toString());
            pst.setString(7, txtTroco.getText());
            pst.setString(8, txtCliNome.getText());
            pst.setString(9, txtMesa.getText());
            pst.setString(10, txtIdItemEntrada.getText());
            pst.setString(11, txtItemEntrada.getText());
            if (txtCliNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione Nome do Cliente ou Cadastre Novo Cliente");
            } else {
                // a linha abaixo atualiza a tabela de lanches com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtCliPesquisar.setText(null);
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
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliFone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliCelular.setText(null);
                    txtTroco.setText("0.00");
                    txtValorAcumulado.setText("0.00");
                    txtIdItemEntrada.setText(null);
                    txtValorPago.setText("0.00");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // as  linhas abaixo a Tabelas Clientes, Lanche e Bebidas Desablitado  apos Adicionar Pedido
        tblClientes.setVisible(false);
        tblLanches.setVisible(false);
        tblBebidas.setVisible(false);
        tblItensPedido.setVisible(false);

    }

    // metodo par pesquisar uma venda
    private void pesquisar_venda() {
        // a linha abaixo cria uma caixa de entrada do tipo JOptionPane
        String num_venda = JOptionPane.showInputDialog("Numero do Pedido Delivery");
        String sql = "select tbvenda.datavenda, tbclientes.idcli, tbvenda.nomefunc, tbvenda.situacao, tbvenda.tipo, tbvenda.totalvenda, tbclientes.nomecli, tbclientes.endcli, tbclientes.telcli, tbclientes.celcli, tbvenda.idvenda, tbvenda.pagto, tbvenda.troco  from tbvenda inner join tbclientes where tbvenda.nomecli = tbclientes.nomecli and tbvenda.tipo ='Delivery' and tbvenda.situacao <> 'Finalizado' and tbvenda.idvenda= " + num_venda;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lblData.setText(rs.getString(1));
                txtCliId.setText(rs.getString(2));
                cboMotoboy.setSelectedItem(rs.getString(3));
                cboStatus.setSelectedItem(rs.getString(4));
                txtTipo.setText(rs.getString(5));
                txtValorAcumulado.setText(rs.getString(6));
                txtCliNome.setText(rs.getString(7));
                txtCliEndereco.setText(rs.getString(8));
                txtCliFone.setText(rs.getString(9));
                txtCliCelular.setText(rs.getString(10));
                txtNumVenda.setText(rs.getString(11));
                cboFormaPagto.setSelectedItem(rs.getString(12));
                txtTroco.setText(rs.getString(13));
                
            } else {
                JOptionPane.showMessageDialog(null, "Pedido Delivery não cadastrado!!!");
            }
        /*
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Pedido Delivery Inválido!!!");
            //System.out.println(e);
        */
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
        // evitando problemas para não haver recadastro ou possivel troca de cliente
        btnAdicionar.setEnabled(false);
        btnLimparCampos.setEnabled(false);
        btnTroco.setEnabled(false);
        txtCliPesquisar.setEditable(false);
        tblClientes.setVisible(false);
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
                    txtIdItemEntrada.setText(null);
                    txtTotalEntrada.setText(null);
                    txtItemSaida.setText(null);
                    txtValorItemSaida.setText(null);
                    txtQtdItemSaida.setText(null);
                    txtTotalSaida.setText(null);
                    txtIdItemSaida.setText(null);
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliFone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliCelular.setText(null);
                    txtTroco.setText("0,0");
                    txtValorAcumulado.setText("0,0");
                    lblData.setVisible(false);
                    txtValorTotalPedido.setText("0.00");
                    txtValorAcumulado.setText("0.00");
                    txtTroco.setText("0.00");
                    txtValorPago.setText("0.00");
                    txtQtdEntrada.setText("0");
                    txtNumVenda.setText(null);
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
        txtCliPesquisar = new javax.swing.JTextField();
        txtMesa = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        lblData = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtLanchePesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLancheId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboMotoboy = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLanches = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtBebidaPesquisar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtBebidaId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBebidas = new javax.swing.JTable();
        txtItemEntrada = new javax.swing.JTextField();
        txtValorEntrada = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtQtdEntrada = new javax.swing.JTextField();
        txtTotalEntrada = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtIdItemEntrada = new javax.swing.JTextField();
        btnAdicionarItem = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblItensPedido = new javax.swing.JTable();
        txtItemSaida = new javax.swing.JTextField();
        txtValorItemSaida = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtQtdItemSaida = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtIdItemSaida = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumVenda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtValorAcumulado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCliFone = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCliEndereco = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCliCelular = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtValorTotalPedido = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTroco = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        cboFormaPagto = new javax.swing.JComboBox<>();
        btnPesquisar = new javax.swing.JButton();
        btnTroco = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnLimparCampos = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtTotalSaida = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedido Delivery");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel1.setText("Telefone:");

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        txtMesa.setText("0");

        txtTipo.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        txtTipo.setForeground(new java.awt.Color(255, 0, 51));
        txtTipo.setText("Delivery");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("Status:");

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aberto", "Fechado" }));

        lblData.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblData.setText("Data:");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Cliente", "Nome", "Endereço", "Bairro", "Cidade", "CPF", "Data Nasc", "Telefone", "Email", "Celular"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel2.setText("Lanche:");

        txtLanchePesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanchePesquisarActionPerformed(evt);
            }
        });
        txtLanchePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLanchePesquisarKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("ID Lanche:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("Motoboy:");

        tblLanches.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Lanche", "Valor Unit", "Composição", "Tamanho", "Pão"
            }
        ));
        tblLanches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLanchesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLanches);

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel6.setText("Bebida:");

        txtBebidaPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBebidaPesquisarActionPerformed(evt);
            }
        });
        txtBebidaPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBebidaPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel7.setText("ID Bebida:");

        tblBebidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Bebida", "Valor Unit", "Volume ML"
            }
        ));
        tblBebidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBebidasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBebidas);

        jLabel8.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel8.setText("QTD:");

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel9.setText("ID Item:");

        btnAdicionarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/add.png"))); // NOI18N
        btnAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarItemActionPerformed(evt);
            }
        });

        btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/cancelbutton_85722.png"))); // NOI18N
        btnRemoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverItemActionPerformed(evt);
            }
        });

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
        tblItensPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItensPedidoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblItensPedido);

        jLabel10.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel10.setText("QTD:");

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel11.setText("ID Item:");

        jLabel12.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel12.setText("N° Pedido:");

        jLabel13.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel13.setText("Acumulado:");

        jLabel14.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel14.setText("ID Cliente:");

        jLabel15.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel15.setText("Cliente:");

        jLabel16.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel16.setText("Telefone:");

        jLabel17.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel17.setText("Dinheiro:");

        jLabel18.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel18.setText("Endereço:");
        jLabel18.setToolTipText("");

        jLabel19.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel19.setText("Celular:");

        jLabel20.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel20.setText("Total:");

        jLabel21.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel21.setText("Troco:");

        btnAdicionar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/criar.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel22.setText("Forma de pagamento:");

        cboFormaPagto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão de crédito", "Cartão de débito" }));

        btnPesquisar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/pesqui.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnTroco.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnTroco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/calcular.png"))); // NOI18N
        btnTroco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrocoActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/atuali.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imag/limpar.png"))); // NOI18N
        btnLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCamposActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel23.setText("Total Saída:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane2)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane4)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtLanchePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cboMotoboy, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQtdEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(btnAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblData)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(18, 18, 18)
                            .addComponent(txtBebidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(63, 63, 63)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBebidaId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCliCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(105, 105, 105))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(jLabel22)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cboFormaPagto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtValorPago)
                                    .addComponent(txtValorTotalPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
                            .addGap(8, 8, 8))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(37, 37, 37)
                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(54, 54, 54)
                            .addComponent(txtItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtValorItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtQtdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtIdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNumVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtValorAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblData)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLanchePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtLancheId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cboMotoboy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBebidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtBebidaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemoverItem)
                    .addComponent(txtIdItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtItemEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtQtdEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(btnAdicionarItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtQtdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtIdItemSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNumVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtValorAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtCliCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorTotalPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(cboFormaPagto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdicionar)
                            .addComponent(btnPesquisar)
                            .addComponent(btnAlterar)
                            .addComponent(btnLimparCampos)))
                    .addComponent(btnTroco, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // método para adicionar venda
        adicionarvenda();

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        //Chamar o metodo Pesquisar Venda
        pesquisar_venda();
        pesquisar_lanche();
        pesquisar_bebida();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando o método para alterar Pedido
        alterar_venda();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCamposActionPerformed
        // Limpar campos do Formulário
        JOptionPane.showMessageDialog(null, "Formulário Limpo !!!");
        // as linhas abaixo limpam os campos
        txtCliPesquisar.setText(null);
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
        txtCliId.setText(null);
        txtCliNome.setText(null);
        txtCliFone.setText(null);
        txtCliEndereco.setText(null);
        txtCliCelular.setText(null);
        txtTroco.setText("0,0");
        txtValorAcumulado.setText("0.00");
    }//GEN-LAST:event_btnLimparCamposActionPerformed

    private void btnTrocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrocoActionPerformed
        // chamar metodo calcular Troco
        troco();
    }//GEN-LAST:event_btnTrocoActionPerformed

    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemActionPerformed
        // chamando o metodo adicionar Lanche     Testado OK*
        itempedido();
        // Inserir itens selecionado no campo de texto para a Tabela de Pedido.
        DefaultTableModel model = (DefaultTableModel) tblItensPedido.getModel();
        model.addRow(new Object[]{txtItemEntrada.getText(), txtQtdEntrada.getText(), txtValorEntrada.getText(), txtTotalEntrada.getText(), txtCliNome.getText()});
        // chamando o metodo Soma itens Pedido
        soma();
    }//GEN-LAST:event_btnAdicionarItemActionPerformed

    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverItemActionPerformed
        //Chamando o metodo Remover Lanche
        remover();
        // chamando o metodo subtrair itens Pedido
        subtrair();
        // Linha abaixo Limpa tblPedido
        ((DefaultTableModel) tblItensPedido.getModel()).removeRow(tblItensPedido.getSelectedRow());
    }//GEN-LAST:event_btnRemoverItemActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chmar o metodo pesquisar clientes por telefone
        pesquisar_cliente();
        tblClientes.setEnabled(true);
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chamando o metodo para setar campos cliente
        setarcamposcliente();
        tblClientes.setEnabled(false);
        txtLanchePesquisar.setEditable(true);
        txtBebidaPesquisar.setEditable(true);
    }//GEN-LAST:event_tblClientesMouseClicked

    private void txtLanchePesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanchePesquisarActionPerformed
        // TODO add your handling code here:
        tblLanches.setEnabled(true);
    }//GEN-LAST:event_txtLanchePesquisarActionPerformed

    private void txtLanchePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLanchePesquisarKeyReleased
        // chmar o metodo pesquisar lanche por nome
        pesquisar_lanche();
        tblLanches.setEnabled(true);
    }//GEN-LAST:event_txtLanchePesquisarKeyReleased

    private void tblLanchesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLanchesMouseClicked
        JOptionPane.showMessageDialog(null, "Não Esqueça Inserir Quantidade");
        // Chamando o metodo para setar campos lanche
        setarcamposlanche();
        tblItensPedido.setEnabled(true);
    }//GEN-LAST:event_tblLanchesMouseClicked

    private void txtBebidaPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBebidaPesquisarActionPerformed
        // Abilitar tabela bebida para ecolha
        tblBebidas.setEnabled(true);
    }//GEN-LAST:event_txtBebidaPesquisarActionPerformed

    private void txtBebidaPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBebidaPesquisarKeyReleased
        // chmar o metodo pesquisar bebida por nome
        pesquisar_bebida();
        tblBebidas.setEnabled(true);
    }//GEN-LAST:event_txtBebidaPesquisarKeyReleased

    private void tblBebidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBebidasMouseClicked
        JOptionPane.showMessageDialog(null, "Não Esqueça Inserir Quantidade");
        // Chamando o metodo para setar campos bebida
        setarcamposbebida();
        tblItensPedido.setEnabled(true);    }//GEN-LAST:event_tblBebidasMouseClicked

    private void tblItensPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItensPedidoMouseClicked
        // Chamando o metodo Selecionar Item Pedido
        setar_campos_pedido();
    }//GEN-LAST:event_tblItensPedidoMouseClicked
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnLimparCampos;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JButton btnTroco;
    private javax.swing.JComboBox<String> cboFormaPagto;
    private javax.swing.JComboBox<String> cboMotoboy;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblData;
    private javax.swing.JTable tblBebidas;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblItensPedido;
    private javax.swing.JTable tblLanches;
    private javax.swing.JTextField txtBebidaId;
    private javax.swing.JTextField txtBebidaPesquisar;
    private javax.swing.JTextField txtCliCelular;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
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
