package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.comum.util.RefreshLeilaoSession;
import br.com.leilao.comum.util.Utilites;
import br.com.leilao.model.Animal;
import br.com.leilao.model.CompradoresWrapper;
import br.com.leilao.model.GeneologiaMae;
import br.com.leilao.model.GeneologiaPai;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Lotes;
import br.com.leilao.model.RacaBean;
import br.com.leilao.model.VendedorBean;

public class LoteDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LeilaoDao leilaoDao;
	
	private RefreshLeilaoSession refreshLeilaoSession;

	private Leilao homeLeilao; 
	
	public LoteDao() {
		leilaoDao = new LeilaoDao();
		refreshLeilaoSession = new RefreshLeilaoSession();
		homeLeilao = (Leilao) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leilao");
		
	}
	
	@SuppressWarnings("deprecation")
	public void save(Lotes loteBean, List<CompradoresWrapper> listCompradoresWrapper) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Integer idLote = 0;
		try {
			String sql = "INSERT INTO leilao.lote(preposto, observacao, id_vendedor, tipo_compra, data_compra, valor_lance, numero_parcela, valor_total, valor_desconto,"
						+" taxa_inscricao, taxa_antecipada, comissao_vendedor, comissao_comprador, numero, id_leilao) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id; ";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, loteBean.getPreposto());
			ps.setString(2, loteBean.getObservacao());
			if (loteBean.getVendedorBean().getId() != null) {
				ps.setInt(3, loteBean.getVendedorBean().getId());
			} else {
				ps.setNull(3, java.sql.Types.NULL);
			}
			ps.setString(4, loteBean.getTipoCompra());
			if (loteBean.getDataCompra() != null) {
				ps.setDate(5,new java.sql.Date( loteBean.getDataCompra().getTime()));
			} else {
				ps.setNull(5, java.sql.Types.NULL);
			}
			if (loteBean.getValorLance() != null) {
				ps.setDouble(6, loteBean.getValorLance());
			} else {
				ps.setNull(6, java.sql.Types.NULL);
			}
			
			if (loteBean.getNumeroParcela() != null) {
				ps.setInt(7, loteBean.getNumeroParcela());
			} else {
				ps.setNull(7, java.sql.Types.NULL);
			}
			
			if (loteBean.getValorTotal()!=null)
			ps.setDouble(8, loteBean.getValorTotal());
			else
				ps.setNull(8, Types.OTHER);
			if (loteBean.getValorDesconto()!=null)
				ps.setDouble(9, loteBean.getValorDesconto());
				else
					ps.setNull(9, Types.OTHER);
			
			if (loteBean.getTaxaInscricao()!=null)
				ps.setDouble(10, loteBean.getTaxaInscricao());
				else
					ps.setNull(10, Types.OTHER);
			if (loteBean.getTaxaAntecipada()!=null)
				ps.setDouble(11, loteBean.getTaxaAntecipada());
				else
					ps.setNull(11, Types.OTHER);
						
			if (loteBean.getComissaoVendedor()!=null)
				ps.setDouble(12, loteBean.getComissaoVendedor());
				else
					ps.setNull(12, Types.OTHER);
			System.out.println("vamos ver o valor"+loteBean.getComissaoComprador());
			if (loteBean.getComissaoComprador()!=null)
				ps.setDouble(13, loteBean.getComissaoComprador());
				else
					ps.setNull(13, Types.OTHER);

			ps.setString(14, loteBean.getNumero());
			
			ps.setDouble(15, homeLeilao.getId());

			ResultSet set = ps.executeQuery();
			while (set.next()) {
				idLote = set.getInt(1);
			}

			/**
			 * Insere Animais caso a lista for maior que 0.
			 */
			for (int i = 0; i < loteBean.getListaDeAnimais().size(); i++) {
				Integer idAnimal = loteBean.getListaDeAnimais().get(i).getId();
				if (idAnimal == null){
					AnimalDao aDao = new AnimalDao();
					idAnimal = aDao.save(loteBean.getListaDeAnimais().get(i));
				}
				String sqlInsertAnimalLote = "INSERT INTO leilao.lote_animal (id_lote, id_animal) VALUES (?, ?);";
				PreparedStatement psInsertAnimalLote = con.prepareStatement(sqlInsertAnimalLote);
				psInsertAnimalLote.setInt(1, idLote);
				psInsertAnimalLote.setInt(2, idAnimal); //loteBean.getListaDeAnimais().get(i).getId());
				psInsertAnimalLote.execute();
				
				loteBean.getListaDeAnimais().get(i).setId(idAnimal);
				//insere na tabela lote2
				Lotes lote = new Lotes();
				lote.setId(idLote);
				saveLote2(lote, loteBean.getListaDeAnimais().get(i), con);
			}
			
			String sqlLoteLeilao = "INSERT INTO leilao.leilao_lote (id_leilao, id_lote) VALUES (?, ?);";
			PreparedStatement psLoteLeilao = con.prepareStatement(sqlLoteLeilao);
			psLoteLeilao.setInt(1, homeLeilao.getId());
			psLoteLeilao.setInt(2, idLote);
			psLoteLeilao.execute();
			
			//Insere os vendedores do lote
			for (int i = 0; i < loteBean.getListaDeVendedores().size(); i++) {
				String sqlInsertVendedorLote = "INSERT INTO leilao.lote_vendedores (id_lote, id_vendedor, percentual) VALUES (?, ?, ?);";
				PreparedStatement psInsertVendedorLote = con.prepareStatement(sqlInsertVendedorLote);
				psInsertVendedorLote.setInt(1, idLote);
				psInsertVendedorLote.setInt(2, loteBean.getListaDeVendedores().get(i).getId());
				psInsertVendedorLote.setDouble(3, loteBean.getListaDeVendedores().get(i).getPercentual());
				psInsertVendedorLote.execute();
			}
			
			//Insere os compradores do lote
			for (int i = 0; i < listCompradoresWrapper.size(); i++) {
				String sqlInsertCompradorLote = " INSERT INTO leilao.lote_compradores(id_lote, id_comprador, percentual, valor_lance, valor_desconto, numero_parcela, editavel) " +
												" VALUES (?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement psInsertCompradorLote = con.prepareStatement(sqlInsertCompradorLote);
				psInsertCompradorLote.setInt(1, idLote);
				psInsertCompradorLote.setInt(2, listCompradoresWrapper.get(i).getComprador().getId());
				psInsertCompradorLote.setDouble(3, 0d);
				psInsertCompradorLote.setDouble(4, listCompradoresWrapper.get(i).getValorLance());
				psInsertCompradorLote.setDouble(5, listCompradoresWrapper.get(i).getValorDesconto() != null ? listCompradoresWrapper.get(i).getValorDesconto() : 0.0d);
				psInsertCompradorLote.setDouble(6, listCompradoresWrapper.get(i).getNumeroParcelas());
				psInsertCompradorLote.setBoolean(7, listCompradoresWrapper.get(i).getEditavel());
				psInsertCompradorLote.execute();	
				
				String sqlLoteCompradores = "INSERT INTO leilao.lote (preposto, observacao, id_vendedor, tipo_compra,data_compra, valor_lance, numero_parcela, valor_total, valor_desconto,"
							+" taxa_inscricao, taxa_antecipada, comissao_vendedor, comissao_comprador, numero, id_leilao, id_lote_origem) VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
				
				PreparedStatement psLoteCompradores = con.prepareStatement(sqlLoteCompradores);
				
				psLoteCompradores.setString(1, loteBean.getPreposto());
				psLoteCompradores.setString(2, loteBean.getObservacao());
				if (loteBean.getVendedorBean().getId() != null) {
					psLoteCompradores.setInt(3, loteBean.getVendedorBean().getId());
				} else {
					psLoteCompradores.setNull(3, java.sql.Types.NULL);
				}
				psLoteCompradores.setString(4, loteBean.getTipoCompra());
				if (loteBean.getDataCompra() != null) {
					psLoteCompradores.setDate(5,new java.sql.Date( loteBean.getDataCompra().getTime()));
				} else {
					psLoteCompradores.setNull(5, java.sql.Types.NULL);
				}
				if (loteBean.getValorLance() != null) {
					psLoteCompradores.setDouble(6, loteBean.getValorLance());
				} else {
					psLoteCompradores.setNull(6, java.sql.Types.NULL);
				}
				
				if (loteBean.getNumeroParcela() != null) {
					psLoteCompradores.setInt(7, loteBean.getNumeroParcela());
				} else {
					psLoteCompradores.setNull(7, java.sql.Types.NULL);
				}
				
				if (loteBean.getValorTotal()!=null)
					psLoteCompradores.setDouble(8, loteBean.getValorTotal());
				else
					psLoteCompradores.setNull(8, Types.OTHER);
				if (loteBean.getValorDesconto()!=null)
					psLoteCompradores.setDouble(9, loteBean.getValorDesconto());
					else
						psLoteCompradores.setNull(9, Types.OTHER);
				
				if (loteBean.getTaxaInscricao()!=null)
					psLoteCompradores.setDouble(10, loteBean.getTaxaInscricao());
					else
						psLoteCompradores.setNull(10, Types.OTHER);
				if (loteBean.getTaxaAntecipada()!=null)
					psLoteCompradores.setDouble(11, loteBean.getTaxaAntecipada());
					else
						psLoteCompradores.setNull(11, Types.OTHER);
							
				if (loteBean.getComissaoVendedor()!=null)
					psLoteCompradores.setDouble(12, loteBean.getComissaoVendedor());
					else
						psLoteCompradores.setNull(12, Types.OTHER);
				if (loteBean.getComissaoComprador()!=null)
					psLoteCompradores.setDouble(13, loteBean.getComissaoComprador());
					else
						psLoteCompradores.setNull(13, Types.OTHER);
	
				Integer numero = i + 1;
				String proxNumero = String.valueOf(numero);
				psLoteCompradores.setString(14, loteBean.getNumero() + "." + proxNumero);
				
				psLoteCompradores.setInt(15, homeLeilao.getId());

				psLoteCompradores.setInt(16, idLote);
			
				psLoteCompradores.execute();
				
			}
			
			//Insere as compras do lote
			String sqlInsertComprasLote = "INSERT INTO leilao.compra_lote (id_lote, id_vendedor, tipo_compra, data_compra, valor_lance, numero_parcela, valor_total, valor_desconto, taxa_inscricao, taxa_antecipada, comissao_vendedor, comissao_comprador, id_comprador, vendedor_proporcional) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?,?, ?, ?,?, ?);";
			System.out.println("compra lote listCompradoresWrapper.size()"+listCompradoresWrapper.size());
			System.out.println("compra lote loteBean.getListaDeVendedores().size()"+loteBean.getListaDeVendedores().size());
			for (int i = 0; i < listCompradoresWrapper.size(); i++) {
				for (int j = 0; j < loteBean.getListaDeVendedores().size(); j++) {
					PreparedStatement psInsertComprasLote = con.prepareStatement(sqlInsertComprasLote);
					psInsertComprasLote.setInt(1, idLote);
					psInsertComprasLote.setInt(2, loteBean.getListaDeVendedores().get(i).getId());
					psInsertComprasLote.setString(3, loteBean.getListacompras().get(j).getTipoCompra());
					psInsertComprasLote.setDate(4,new java.sql.Date( loteBean.getListacompras().get(j).getDataDaCompra().getTime()));
					psInsertComprasLote.setDouble(5, listCompradoresWrapper.get(i).getValorLance());
					psInsertComprasLote.setInt(6,listCompradoresWrapper.get(i).getNumeroParcelas());
					psInsertComprasLote.setDouble(7, listCompradoresWrapper.get(i).getValorLance()*listCompradoresWrapper.get(i).getNumeroParcelas());
					psInsertComprasLote.setDouble(8, listCompradoresWrapper.get(i).getValorDesconto() != null ? listCompradoresWrapper.get(i).getValorDesconto() : 0);
					psInsertComprasLote.setDouble(9, loteBean.getListacompras().get(j).getTaxaInscricao());
					psInsertComprasLote.setDouble(10, loteBean.getListacompras().get(j).getTaxaAntecipada());
					psInsertComprasLote.setDouble(11, loteBean.getListacompras().get(j).getComissaoComprador());
					psInsertComprasLote.setDouble(12, loteBean.getListacompras().get(j).getComissaoVendedor());
					psInsertComprasLote.setInt(13, listCompradoresWrapper.get(i).getComprador().getId());
					psInsertComprasLote.setDouble(14, (listCompradoresWrapper.get(i).getValorLance()/listCompradoresWrapper.size()) / loteBean.getListaDeVendedores().size());
					psInsertComprasLote.execute();
				}
			}
			
			Utilites utilites = new Utilites();
			
			//insere as parcelas da compra
			for (int i = 0; i < listCompradoresWrapper. size(); i++) {
				for (int j = 0; j < listCompradoresWrapper.get(i).getListParcelasComprador().size(); j++) {
					String sqlInsertParcelas = "INSERT INTO leilao.parcelas_comprador (id_lote, numero_parcela, valor_parcela, id_comprador, data_vencimento) VALUES (?,?, ?, ?, ?);";
					PreparedStatement psInsertParcelas = con.prepareStatement(sqlInsertParcelas);
					psInsertParcelas.setInt(1, idLote);
					psInsertParcelas.setInt(2, listCompradoresWrapper.get(i).getListParcelasComprador().get(j).getNumeroDaParcela());
					psInsertParcelas.setDouble(3, listCompradoresWrapper.get(i).getListParcelasComprador().get(j).getValorDaParcela());
					psInsertParcelas.setInt(4, listCompradoresWrapper.get(i).getListParcelasComprador().get(j).getComprador().getId());
					psInsertParcelas.setDate(5, new java.sql.Date(utilites.addMonthDate(loteBean.getDataCompra(), j).getTime()));
					System.out.println(utilites.addMonthDate(loteBean.getDataCompra(), j).getTime());
					psInsertParcelas.execute();
				}
			}
			
			for (int i = 0; i < loteBean.getListaDeVendedores().size(); i++) {
				String sqlInsertVendedorLote = "INSERT INTO leilao.lote_vendedores (id_lote, id_vendedor, percentual) VALUES (?, ?, ?);";
				PreparedStatement psInsertVendedorLote = con.prepareStatement(sqlInsertVendedorLote);
				psInsertVendedorLote.setInt(1, idLote);
				psInsertVendedorLote.setInt(2, loteBean.getListaDeVendedores().get(i).getId());
				psInsertVendedorLote.setDouble(3, loteBean.getListaDeVendedores().get(i).getPercentual());
				psInsertVendedorLote.execute();
			}

			
			con.commit();
			ps.close();
			refreshLeilaoSession.selectLeilao(leilaoDao.findOne(homeLeilao.getId()));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ProjetoException(sqle);
			
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void update(Lotes loteBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = " UPDATE leilao.lote SET  preposto=?, observacao=?, id_vendedor=?, tipo_compra=?, data_compra=?, valor_lance=?, numero_parcela=?, valor_total=?,"+
						 " valor_desconto=?, taxa_inscricao=?, taxa_antecipada=?, comissao_vendedor=?,comissao_comprador=?, numero = ? WHERE id=?";
			ps = con.prepareStatement(sql);
			System.out.println("preposto"+loteBean.getPreposto().toUpperCase());
			ps.setString(1, loteBean.getPreposto().toUpperCase());
			ps.setString(2, loteBean.getObservacao().toUpperCase());
			
			if (loteBean.getVendedorBean().getId() != null) {
				ps.setInt(3, loteBean.getVendedorBean().getId());
			} else {
				ps.setNull(3, java.sql.Types.NULL);
			}
			if (loteBean.getTipoCompra() != null) {
				ps.setString(4, loteBean.getTipoCompra().toUpperCase());
			} else {
				ps.setNull(4, java.sql.Types.NULL);
			}
			
			ps.setDate(5, new java.sql.Date(new Date().getDate()));
			ps.setDouble(6, loteBean.getValorLance());
			ps.setInt(7, loteBean.getNumeroParcela());
			ps.setDouble(8, loteBean.getValorTotal());
			ps.setDouble(9, loteBean.getValorDesconto());
			ps.setDouble(10, loteBean.getTaxaInscricao());
			ps.setDouble(11, loteBean.getTaxaAntecipada());
			ps.setDouble(12, loteBean.getComissaoVendedor());
			ps.setDouble(13, loteBean.getComissaoComprador());
			ps.setString(14, loteBean.getNumero());
			ps.setInt(15, loteBean.getId());
			
			ps.execute();
			
			/**
			 * Update Animal Lotes
			 */
			List<Animal> listaDeAnimais = loteBean.getListaDeAnimais();
			
			String sqLDeleteAnimalLote = "DELETE FROM leilao.lote_animal where id_lote = ?";
			PreparedStatement psDeleteAnimalLote = con.prepareStatement(sqLDeleteAnimalLote);
			psDeleteAnimalLote.setInt(1, loteBean.getId());
			psDeleteAnimalLote.execute();
			
			String sqLDeleteLote2 = "DELETE FROM leilao.lote2 where idlote = ?";
			PreparedStatement psDeleteLote2 = con.prepareStatement(sqLDeleteLote2);
			psDeleteLote2.setInt(1, loteBean.getId());
			psDeleteLote2.execute();
			 
			for (int i = 0; i < listaDeAnimais.size(); i++) {
				Integer idAnimal = loteBean.getListaDeAnimais().get(i).getId();
				if (idAnimal == null) {
					AnimalDao aDao = new AnimalDao();
					idAnimal = aDao.save(loteBean.getListaDeAnimais().get(i));
				} else {
					AnimalDao aDao = new AnimalDao();
					aDao.edit(loteBean.getListaDeAnimais().get(i), false, con);
				}
				
				String sqlInsertAnimalLote = "INSERT INTO leilao.lote_animal (id_lote, id_animal) VALUES (?, ?);";
				PreparedStatement psInsertAnimalLote = con.prepareStatement(sqlInsertAnimalLote);
				psInsertAnimalLote.setInt(1, loteBean.getId());
				psInsertAnimalLote.setInt(2, idAnimal); //loteBean.getListaDeAnimais().get(i).getId());
				psInsertAnimalLote.execute();
				saveLote2(loteBean, loteBean.getListaDeAnimais().get(i), con);
				
			}
			
			String sqLDeleteVendedorLote = "DELETE FROM leilao.lote_vendedores where id_lote = ?";
			PreparedStatement psDeleteVendedorLote = con.prepareStatement(sqLDeleteVendedorLote);
			psDeleteVendedorLote.setInt(1, loteBean.getId());
			psDeleteVendedorLote.execute();
			//Insere os vendedores do lote
			for (int i = 0; i < loteBean.getListaDeVendedores().size(); i++) {
				String sqlInsertVendedorLote = "INSERT INTO leilao.lote_vendedores (id_lote, id_vendedor, percentual) VALUES (?, ?, ?);";
				PreparedStatement psInsertVendedorLote = con.prepareStatement(sqlInsertVendedorLote);
				psInsertVendedorLote.setInt(1, loteBean.getId());
				psInsertVendedorLote.setInt(2, loteBean.getListaDeVendedores().get(i).getId());
				psInsertVendedorLote.setDouble(3, loteBean.getListaDeVendedores().get(i).getPercentual());
				psInsertVendedorLote.execute();
			}	

			//insere as compras do lote
			String sqLDeleteCompradorLote = "DELETE FROM leilao.lote_compradores where id_lote = ?";
			PreparedStatement psDeleteCompradorLote = con.prepareStatement(sqLDeleteCompradorLote);
			psDeleteCompradorLote.setInt(1, loteBean.getId());
			psDeleteCompradorLote.execute();
		
			for (int i = 0; i < loteBean.getListaCompradoresWrapper().size(); i++) {
				String sqlInsertCompradorLote = " INSERT INTO leilao.lote_compradores(id_lote, id_comprador, percentual, valor_lance, valor_desconto, "+
												" numero_parcela, editavel)  VALUES (?, ?, ?, ?, ?, ?, ?); ";
				PreparedStatement psInsertCompradorLote = con.prepareStatement(sqlInsertCompradorLote);
				psInsertCompradorLote.setInt(1, loteBean.getId());
				psInsertCompradorLote.setInt(2, loteBean.getListaCompradoresWrapper().get(i).getComprador().getId());
				psInsertCompradorLote.setDouble(3, 0d);
				psInsertCompradorLote.setDouble(4, loteBean.getListaCompradoresWrapper().get(i).getValorLance());
				psInsertCompradorLote.setDouble(5, loteBean.getListaCompradoresWrapper().get(i).getValorDesconto() );
				psInsertCompradorLote.setDouble(6, loteBean.getListaCompradoresWrapper().get(i).getNumeroParcelas());
				psInsertCompradorLote.setBoolean(7, loteBean.getListaCompradoresWrapper().get(i).getEditavel());
				psInsertCompradorLote.execute();
			}
			
			String sqlDeleteComprasLotes = "DELETE FROM leilao.compra_lote where id_lote = ?";
			PreparedStatement psDeleteComprasLote = con.prepareStatement(sqlDeleteComprasLotes);
			psDeleteComprasLote.setInt(1, loteBean.getId());
			psDeleteComprasLote.execute();
			
			String sqlInsertComprasLote = "INSERT INTO leilao.compra_lote (id_lote, id_vendedor, tipo_compra, data_compra, valor_lance, numero_parcela, valor_total, valor_desconto, taxa_inscricao, taxa_antecipada, comissao_vendedor, comissao_comprador) VALUES (?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?);";
			for (int i = 0; i < loteBean.getListaCompradoresWrapper().size(); i++) {
				for (int j = 0; j < loteBean.getListacompras().size(); j++) {
					PreparedStatement psInsertComprasLote = con.prepareStatement(sqlInsertComprasLote);
					psInsertComprasLote.setInt(1, loteBean.getId());
					psInsertComprasLote.setInt(2, loteBean.getListaCompradoresWrapper().get(i).getComprador().getId());
					psInsertComprasLote.setString(3, loteBean.getListacompras().get(j).getTipoCompra());
					psInsertComprasLote.setDate(4,new java.sql.Date( loteBean.getListacompras().get(j).getDataDaCompra().getTime()));
					psInsertComprasLote.setDouble(5, loteBean.getListacompras().get(j).getValorDoLance());
					psInsertComprasLote.setInt(6, loteBean.getListacompras().get(j).getNumeroDeParcelas());
					psInsertComprasLote.setDouble(7, loteBean.getListacompras().get(j).getValorTotal());
					psInsertComprasLote.setDouble(8, loteBean.getListacompras().get(j).getValorDesconto());
					psInsertComprasLote.setDouble(9, loteBean.getListacompras().get(j).getTaxaInscricao());
					psInsertComprasLote.setDouble(10, loteBean.getListacompras().get(j).getTaxaAntecipada());
					psInsertComprasLote.setDouble(11, loteBean.getListacompras().get(j).getComissaoComprador());
					psInsertComprasLote.setDouble(12, loteBean.getListacompras().get(j).getComissaoVendedor());
					psInsertComprasLote.execute();
				}
			}
			
			con.commit();
			ps.close();
			refreshLeilaoSession.selectLeilao(leilaoDao.findOne(homeLeilao.getId()));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}

	public Lotes findOne(Integer id) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Lotes loteBean = new Lotes();
		try {
			String sql = "select * from leilao.lote l where l.id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				loteBean.setId(rs.getInt("id"));
				loteBean.setCriador(rs.getString("criador"));
				loteBean.setPreposto(rs.getString("preposto"));
				loteBean.setObservacao(rs.getString("observacao"));
				loteBean.setIdLoteOrigem(rs.getInt("id_lote_origem"));
				
				if (loteBean.getNumero() != null) {
					loteBean.setNumero(rs.getString("numero"));
				} else {
					loteBean.setNumero(getMaxIdLote());
				}
				
				if (rs.getString("comissao_comprador")!=null)
				loteBean.setComissaoComprador(rs.getDouble("comissao_comprador"));
				else
				loteBean.setComissaoComprador(homeLeilao.getComissaoComprador());
				
				if (rs.getString("comissao_vendedor")!=null)
					loteBean.setComissaoVendedor(rs.getDouble("comissao_vendedor"));
					else
					loteBean.setComissaoVendedor(homeLeilao.getComissaoVendedor());
				loteBean.setDataCompra(rs.getDate("data_compra"));
				loteBean.setNumeroParcela(rs.getInt("numero_parcela"));
				loteBean.setTaxaAntecipada(rs.getDouble("taxa_antecipada"));
				loteBean.setTaxaInscricao(rs.getDouble("taxa_inscricao"));
				loteBean.setTipoCompra(rs.getString("tipo_compra"));
				loteBean.setValorDesconto(rs.getDouble("valor_desconto"));
				loteBean.setValorLance(rs.getDouble("valor_lance"));
				loteBean.setValorTotal(rs.getDouble("valor_total"));
				
				String sqlVendedor = "select * from leilao.vendedor where id = ?;";
				PreparedStatement psVendedor = con.prepareStatement(sqlVendedor);
				psVendedor.setInt(1, rs.getInt("id_vendedor"));
				ResultSet setVendedor = psVendedor.executeQuery();
				VendedorBean vendedorBean = new VendedorBean();
				while (setVendedor.next()) {
					vendedorBean.setId(setVendedor.getInt("id"));
					vendedorBean.setNome(setVendedor.getString("nome"));
				}
				
				loteBean.setVendedorBean(vendedorBean);
				
			}
			
			/**
			 * Carregar animais do lote
			 */
			List<Animal> listaDeAnimais = new ArrayList<Animal>();
			String sqlAnimalLote = "select a.*,r.*,lote2.* from leilao.lote_animal"+
									" join leilao.lote2 on lote2.idlote = lote_animal.id_lote"+
									" join leilao.animal a on a.id = lote2.idanimal"+
									" join leilao.raca r on r.id = lote2.idraca"+
									" where lote2.idlote=?";
			PreparedStatement psAnimalLote = con.prepareStatement(sqlAnimalLote);
			psAnimalLote.setInt(1, id);
			ResultSet setAnimalLote = psAnimalLote.executeQuery();
			GeneologiaMae geneologiaMae = new GeneologiaMae();
			GeneologiaPai geneologiaPai = new GeneologiaPai();
			RacaBean racaBean = new RacaBean();
			while (setAnimalLote.next()) {
				Animal animal = new Animal();
				animal.setId(setAnimalLote.getInt("idanimal"));
				animal.setNome(setAnimalLote.getString("nome"));
				animal.setCriador(setAnimalLote.getString("criador"));
				animal.setDataNascimento(setAnimalLote.getDate("data_nascimento"));
				animal.setNumeroDeControle(setAnimalLote.getString("numero_de_controle"));
				animal.setNumeroDeRegistro(setAnimalLote.getString("numero_de_registro"));
				animal.setPelagem(setAnimalLote.getString("pelagem"));
				animal.setSexo(setAnimalLote.getString("sexo"));
				
				geneologiaPai.setPai(setAnimalLote.getString("pai"));
				geneologiaPai.setAvoMasculino(setAnimalLote.getString("avo_masculino_pai"));
				geneologiaPai.setAvoFeminino(setAnimalLote.getString("avo_feminino_pai"));
				geneologiaPai.setBisavoMasculino(setAnimalLote.getString("bisavo_masculino_parte_avo_masculino_pai"));
				geneologiaPai.setBisavoFeminino(setAnimalLote.getString("bisavo_feminimo_parte_avo_masculino_pai"));
				
				geneologiaMae.setMae(setAnimalLote.getString("mae"));
				geneologiaMae.setAvoMasculino(setAnimalLote.getString("avo_masculino_mae"));
				geneologiaMae.setAvoFemenino(setAnimalLote.getString("avo_feminino_mae"));
				geneologiaMae.setBisavoMasculino(setAnimalLote.getString("bisavo_masculino_parte_avo_masculino_mae"));
				geneologiaMae.setBisavoFemenino(setAnimalLote.getString("bisavo_feminimo_parte_avo_masculino_mae"));
				
				racaBean.setId(setAnimalLote.getInt("id_raca"));

				animal.setGeneologiaPai(geneologiaPai);
				animal.setGeneologiaMae(geneologiaMae);
				animal.setRacaBean(racaBean);
				
				listaDeAnimais.add(animal);
			}
			loteBean.setListaDeAnimais(listaDeAnimais);
			
			/**
			 * Carregar vendedores do lote
			 */
			List<VendedorBean> listaDeVendedores = new ArrayList<VendedorBean>();
			String sqlVendedorLote = "select a.cpf_cgc,a.id, a.nome, percentual from  leilao.vendedor a join leilao.lote_vendedores la on (a.id = la.id_vendedor) where la.id_lote = ?;";
			PreparedStatement psVendedorLote = con.prepareStatement(sqlVendedorLote);
			psVendedorLote.setInt(1, id);
			ResultSet setVendedorLote = psVendedorLote.executeQuery();
			
			while (setVendedorLote.next()) {
				System.out.println("achou vendedor");
				VendedorBean vend = new VendedorBean();
				vend.setId(setVendedorLote.getInt("id"));
				vend.setNome(setVendedorLote.getString("nome"));
				vend.setCpfcgc(setVendedorLote.getString("cpf_cgc"));
				vend.setPercentual(setVendedorLote.getDouble("percentual"));
				listaDeVendedores.add(vend);
			}
			loteBean.setListaDeVendedores(listaDeVendedores);
			
			/**
			 * Carregar compradores do lote..
			 */
			List<CompradoresWrapper> listaDeCompradores = new ArrayList<CompradoresWrapper>();
			String sqlCompradorLote = "select a.*, la.* from  leilao.vendedor a join leilao.lote_compradores la on (a.id = la.id_comprador) where la.id_lote = ?;";
			PreparedStatement psCompradorLote = con.prepareStatement(sqlCompradorLote);
			psCompradorLote.setInt(1, id);
			ResultSet setCompradorLote = psCompradorLote.executeQuery();
			
			while (setCompradorLote.next()) {
				VendedorBean vend = new VendedorBean();
				vend.setId(setCompradorLote.getInt("id"));
				vend.setNome(setCompradorLote.getString("nome"));
				vend.setCpfcgc(setCompradorLote.getString("cpf_cgc"));
				vend.setPercentual(setCompradorLote.getDouble("percentual"));
				CompradoresWrapper comprador = new CompradoresWrapper();
				comprador.setComprador(vend);
				comprador.setNumeroParcelas(setCompradorLote.getInt("numero_parcela"));
				comprador.setValorDesconto(setCompradorLote.getDouble("valor_desconto"));
				comprador.setValorLance(setCompradorLote.getDouble("valor_lance"));
				comprador.setEditavel(setCompradorLote.getBoolean("editavel"));
				listaDeCompradores.add(comprador);
			}
			loteBean.setListaCompradoresWrapper(listaDeCompradores);
			
			return loteBean;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void delete(Lotes loteBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.lote WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, loteBean.getId());
			ps.execute();
			con.commit();
			ps.close();
			refreshLeilaoSession.selectLeilao(leilaoDao.findOne(homeLeilao.getId()));
		} catch (Exception sqle) {
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void saveLote2(Lotes lote, Animal animalBean, Connection con) throws ProjetoException {
		PreparedStatement ps = null;
		try {
			
			String sql = "INSERT INTO leilao.lote2 (idleilao, idlote, idanimal, idraca, pelagem, nome, data_nascimento, numero_de_registro, numero_de_controle, sexo, criador, id_raca, "+
												   " pai, avo_masculino_pai, avo_feminino_pai, bisavo_masculino_parte_avo_masculino_pai, bisavo_feminimo_parte_avo_masculino_pai, mae, avo_masculino_mae, "+ 
												   " avo_feminino_mae, bisavo_masculino_parte_avo_masculino_mae, bisavo_feminimo_parte_avo_masculino_mae) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?); ";
			ps = con.prepareStatement(sql);
			ps.setInt(1, homeLeilao.getId());
			ps.setInt(2, lote.getId());
			ps.setInt(3, animalBean.getId());
			ps.setInt(4, animalBean.getRacaBean().getId());			
			ps.setString(5, animalBean.getPelagem().toUpperCase());
			ps.setString(6, animalBean.getNome().toUpperCase());
			
			if (animalBean.getDataNascimento() != null) {
				ps.setDate(7, new java.sql.Date(animalBean.getDataNascimento().getDate()));
			} else {
				ps.setNull(7, java.sql.Types.NULL);
			}
			ps.setString(8, animalBean.getNumeroDeRegistro().toUpperCase());
			ps.setString(9, animalBean.getNumeroDeControle().toUpperCase());
			ps.setString(10, animalBean.getSexo().toUpperCase());
			ps.setString(11, animalBean.getCriador().toUpperCase());
			ps.setInt(12, animalBean.getRacaBean().getId());
			
			ps.setString(13, animalBean.getGeneologiaPai().getPai().toUpperCase());
			ps.setString(14, animalBean.getGeneologiaPai().getAvoMasculino().toUpperCase());
			ps.setString(15, animalBean.getGeneologiaPai().getAvoFeminino().toUpperCase());
			ps.setString(16, animalBean.getGeneologiaPai().getBisavoMasculino().toUpperCase());
			ps.setString(17, animalBean.getGeneologiaPai().getBisavoFeminino().toUpperCase());
			
			ps.setString(18, animalBean.getGeneologiaMae().getMae().toUpperCase());
			ps.setString(19, animalBean.getGeneologiaMae().getAvoMasculino().toUpperCase());
			ps.setString(20, animalBean.getGeneologiaMae().getAvoFemenino().toUpperCase());
			ps.setString(21, animalBean.getGeneologiaMae().getBisavoMasculino().toUpperCase());
			ps.setString(22, animalBean.getGeneologiaMae().getBisavoFemenino().toUpperCase());
			ps.execute();
			ps.close();
			
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ProjetoException(sqle);
		} finally {
			try {
				
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public String getMaxIdLote() throws ProjetoException{
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select max(id) as idLote from leilao.lote;";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Integer id = 0;
			while (rs.next()) {
				id = rs.getInt("idLote");
			}
			return String.valueOf(id);
		} catch (Exception sqle) {
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	@SuppressWarnings("unused")
	public List<Lotes> findLotesLeilao() throws ProjetoException{
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<Lotes> listAllLotes = new ArrayList<>();
		try {
			String sql = "select id, numero, data_compra,id_vendedor from leilao.lote where id_leilao = ? order by numero;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, homeLeilao.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Lotes lotes = new Lotes();
				lotes.setId(rs.getInt("id"));
				lotes.setDataCompra(rs.getDate("data_compra"));
				lotes.setNumero(rs.getString("numero"));
				listAllLotes.add(lotes);
			}
		} catch (Exception sqle) {
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
		return listAllLotes;
	}
	
	public Integer verifcaAnimalJaCadastradoEmLote(Integer idAnimal) throws ProjetoException{
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Integer count = 0;
		try {
			String sql = "select * from leilao.lote_animal where id_animal = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idAnimal);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count ++;
			}
		} catch (Exception sqle) {
			throw new ProjetoException(sqle);
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
		return count;
	}
}
