package br.com.leilao.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.AgruparParcelas;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Lotes;
import br.com.leilao.service.ImageService;

public class LeilaoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private ImageService imagemService;
	
	public LeilaoDao() {
		imagemService = new ImageService();
	}
	
	@SuppressWarnings("deprecation")
	public void save(Leilao leilao) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Integer idLeilao = null;
		Integer idParcela = null;
		try {
			String sql = " INSERT INTO leilao.leilao(nome, endereco, bairro, cidade, uf, leiloeiro, gerente, ativo,  fechado, data, utiliza_comissao_vendedor, utiliza_taxa_inscricao, "
					 +   " comissao_vendedor, comissao_comprador, total_vendido, total_comissao, total_taxa_inscricao, numero_parcela "
					 +   ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?) returning id;";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, leilao.getNome().toUpperCase());
			ps.setString(2, leilao.getEndereco().toUpperCase());
			ps.setString(3, leilao.getBairro().toUpperCase());
			ps.setString(4, leilao.getCidade().toUpperCase());
			ps.setString(5, leilao.getUf().toUpperCase());
			ps.setString(6, leilao.getLeiloeiro().toUpperCase());
			ps.setString(7, leilao.getGerente().toUpperCase());
			ps.setBoolean(8, leilao.getAtivo());
			ps.setBoolean(9, leilao.getFechado());
			if (leilao.getData() != null) {
				ps.setDate(10, new java.sql.Date(leilao.getData().getDate()));	
			} else {
				ps.setNull(10, java.sql.Types.NULL);
			}
			ps.setBoolean(11, leilao.getUtilizaComissaoVendedor());
			ps.setBoolean(12, leilao.getUtilizaTaxaInscricao());
			ps.setDouble(13, leilao.getComissaoVendedor());
			ps.setDouble(14, leilao.getComissaoComprador());
			ps.setDouble(15, leilao.getTotalVendido());
			ps.setDouble(16, leilao.getTotalComissao());
			ps.setDouble(17, leilao.getTotalTaxaInscricao());
			ps.setDouble(18, leilao.getNumeroParcela());
			ResultSet resLeilao = ps.executeQuery();
			while (resLeilao.next()) {
				idLeilao = resLeilao.getInt(1);
			}
			
			for (int i = 0; i < leilao.getAgruparParcelas().size(); i++) {
				String sqlParcelas = "INSERT INTO leilao.agrupar_parcela(agrupar_parcela) VALUES (?) returning id;";
				PreparedStatement psParcela = con.prepareStatement(sqlParcelas);
				psParcela.setInt(1, leilao.getAgruparParcelas().get(i).getAgruparParcela());
				ResultSet res = psParcela.executeQuery();
				while (res.next()) {
					idParcela = res.getInt(1);
				}

				String sqlLeilaoParcela = "INSERT INTO leilao.leilao_parcela (id_leilao, id_agrupar_parcela) VALUES (?, ?);";
				PreparedStatement psLeilaoParcela = con.prepareStatement(sqlLeilaoParcela);
				psLeilaoParcela.setInt(1, idLeilao);
				psLeilaoParcela.setInt(2, idParcela);
				psLeilaoParcela.execute();
			}
			
			imagemService.salvarImagem(leilao.getImagem());
			
			con.commit();
			ps.close();
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
	
	public void update(Leilao leilao) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		int idParcela = 0;
		try {
			String sql = " UPDATE leilao.leilao SET  nome=?, endereco=?, bairro=?, cidade=?, uf=?, leiloeiro=?, gerente=?, ativo=?, fechado=?, data=?, utiliza_comissao_vendedor=?, " +
						" utiliza_taxa_inscricao=?, comissao_vendedor=?, comissao_comprador=?, total_vendido=?, total_comissao=?, total_taxa_inscricao=?, numero_parcela=? "+
						"  WHERE id=? ";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, leilao.getNome().toUpperCase());
			ps.setString(2, leilao.getEndereco().toUpperCase());
			ps.setString(3, leilao.getBairro().toUpperCase());
			ps.setString(4, leilao.getCidade().toUpperCase());
			if (leilao.getUf() != null) {
				ps.setString(5, leilao.getUf().toUpperCase());
			} else {
				ps.setNull(5, java.sql.Types.NULL);
			}
			ps.setString(6, leilao.getLeiloeiro().toUpperCase());
			ps.setString(7, leilao.getGerente().toUpperCase());
			ps.setBoolean(8, leilao.getAtivo());
			ps.setBoolean(9, leilao.getFechado());
			if (leilao.getData() != null) {
				ps.setDate(10, new java.sql.Date(leilao.getData().getDate()));	
			} else {
				ps.setNull(10, java.sql.Types.NULL);
			}
			ps.setBoolean(11, leilao.getUtilizaComissaoVendedor());
			ps.setBoolean(12, leilao.getUtilizaTaxaInscricao());
			ps.setDouble(13, leilao.getComissaoVendedor());
			ps.setDouble(14, leilao.getComissaoComprador());
			ps.setDouble(15, leilao.getTotalVendido());
			ps.setDouble(16, leilao.getTotalComissao());
			ps.setDouble(17, leilao.getTotalTaxaInscricao());
			ps.setDouble(18, leilao.getNumeroParcela());
			ps.setInt(19, leilao.getId());
			ps.execute();

			List<AgruparParcelas> listaAgruparParcelas = leilao.getAgruparParcelas();
			String sqlDeleteAgruparParcelas = "DELETE FROM leilao.leilao_parcela WHERE id_leilao = ?;";
			PreparedStatement psAgruparParcelas = con.prepareStatement(sqlDeleteAgruparParcelas);
			psAgruparParcelas.setInt(1, leilao.getId());
			psAgruparParcelas.executeUpdate();

			for (int i=0;i<listaAgruparParcelas.size();i++) {
				
				String sqlParcelas = "INSERT INTO leilao.agrupar_parcela (agrupar_parcela) VALUES (?) returning id;";
				PreparedStatement psParcela = con.prepareStatement(sqlParcelas);
				psParcela.setInt(1, listaAgruparParcelas.get(i).getAgruparParcela());
				ResultSet res = psParcela.executeQuery();
				while (res.next()) {
					idParcela = res.getInt(1);
				}
				
				String sqlInsertAgruparParcela = "INSERT INTO leilao.leilao_parcela (id_leilao, id_agrupar_parcela) VALUES (?, ?);";
				PreparedStatement psLeilaoAgruparParcela;
				psLeilaoAgruparParcela = con.prepareStatement(sqlInsertAgruparParcela);
				psLeilaoAgruparParcela.setInt(1, leilao.getId());
				psLeilaoAgruparParcela.setInt(2, idParcela);
				psLeilaoAgruparParcela.execute();
			}
			
			con.commit();
			ps.close();
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
	
	public List<Leilao> findAll() throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		try {
			String sql = " 	SELECT id, nome, endereco, bairro, cidade, uf, leiloeiro, gerente, ativo,  fechado, data, utiliza_comissao_vendedor, utiliza_taxa_inscricao, " +
					     " comissao_vendedor, comissao_comprador, total_vendido, total_comissao, total_taxa_inscricao, numero_parcela " +
				         " FROM leilao.leilao";
			
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Leilao leilaoBean = new Leilao();
				leilaoBean.setId(rs.getInt("id"));
				
				leilaoBean.setNome(rs.getString("nome"));
				leilaoBean.setLeiloeiro(rs.getString("leiloeiro"));
				leilaoBean.setAtivo(rs.getBoolean("ativo"));
				leilaoBean.setData(rs.getDate("data"));
				leilaoBean.setBairro(rs.getString("bairro"));
				leilaoBean.setCidade(rs.getString("cidade"));
				leilaoBean.setComissaoComprador(rs.getDouble("comissao_comprador"));
				leilaoBean.setComissaoVendedor(rs.getDouble("comissao_vendedor"));
				leilaoBean.setEndereco(rs.getString("endereco"));
				leilaoBean.setFechado(rs.getBoolean("fechado"));
				leilaoBean.setGerente(rs.getString("gerente"));
				leilaoBean.setLeiloeiro(rs.getString("leiloeiro"));
				leilaoBean.setNumeroParcela(rs.getInt("numero_parcela"));
				leilaoBean.setTotalComissao(rs.getDouble("total_comissao"));
				leilaoBean.setTotalTaxaInscricao(rs.getDouble("total_taxa_inscricao"));
				leilaoBean.setTotalVendido(rs.getDouble("total_vendido"));
				leilaoBean.setUf(rs.getString("uf"));
				leilaoBean.setUtilizaComissaoVendedor(rs.getBoolean("utiliza_comissao_vendedor"));
				leilaoBean.setUtilizaTaxaInscricao(rs.getBoolean("utiliza_taxa_inscricao"));
				
				/** 
				 *  Agrupar parcelas 
				 */
				List<AgruparParcelas> listaAgruparParcelas = new ArrayList<AgruparParcelas>();
				String sqlAgruparParcelas = "select * from leilao.agrupar_parcela a join leilao.leilao_parcela lp on (a.id = lp.id_agrupar_parcela) where lp.id_leilao = ?;";
				PreparedStatement psAgruparParcelas = con.prepareStatement(sqlAgruparParcelas);
				psAgruparParcelas.setInt(1, rs.getInt("id"));
				ResultSet setAgruparParcelas = psAgruparParcelas.executeQuery();
				while (setAgruparParcelas.next()) {
					AgruparParcelas agruparParcela = new AgruparParcelas();
					agruparParcela.setId(setAgruparParcelas.getInt("id"));
					agruparParcela.setAgruparParcela(setAgruparParcelas.getInt("agrupar_parcela"));
					listaAgruparParcelas.add(agruparParcela);
				}

				leilaoBean.setAgruparParcelas(listaAgruparParcelas);
				
				
				List<Lotes> listaDeLotes = new ArrayList<Lotes>();
				String sqlLotesLeilao = "select l.id as idLote,* from leilao.lote l join leilao.leilao_lote ll on (l.id = ll.id_lote) where ll.id_leilao = ? " ;
				PreparedStatement psLoteLeilao = con.prepareStatement(sqlLotesLeilao);
				psLoteLeilao.setInt(1, rs.getInt("id"));
				ResultSet setLotes = psLoteLeilao.executeQuery();
				while (setLotes.next()) {
					Lotes lotesBean = new Lotes();
					lotesBean.setId(setLotes.getInt("idLote"));
					lotesBean.setCriador(setLotes.getString("criador"));
					lotesBean.setObservacao(setLotes.getString("observacao"));
					lotesBean.setPreposto(setLotes.getString("preposto"));
					listaDeLotes.add(lotesBean);
				}
				
				leilaoBean.setLotes(listaDeLotes);

				listaDeLeiloes.add(leilaoBean);
			}
				
			return listaDeLeiloes;
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
	

	public Leilao findOne(Integer id) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Leilao leilaoBean = new Leilao();
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		List<AgruparParcelas> listaAgruparParcelas = new ArrayList<AgruparParcelas>();
		try {
			String sql = " 	SELECT id, nome, endereco, bairro, cidade, uf, leiloeiro, gerente, ativo,  fechado, data, utiliza_comissao_vendedor, utiliza_taxa_inscricao, " +
						 " comissao_vendedor, comissao_comprador, total_vendido, total_comissao, total_taxa_inscricao, numero_parcela " +
					     " FROM leilao.leilao where id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				leilaoBean.setId(rs.getInt("id"));
				leilaoBean.setNome(rs.getString("nome"));
				leilaoBean.setLeiloeiro(rs.getString("leiloeiro"));
				leilaoBean.setAtivo(rs.getBoolean("ativo"));
				leilaoBean.setData(rs.getDate("data"));
				leilaoBean.setBairro(rs.getString("bairro"));
				leilaoBean.setCidade(rs.getString("cidade"));
				leilaoBean.setComissaoComprador(rs.getDouble("comissao_comprador"));
				leilaoBean.setComissaoVendedor(rs.getDouble("comissao_vendedor"));
				leilaoBean.setEndereco(rs.getString("endereco"));
				leilaoBean.setFechado(rs.getBoolean("fechado"));
				leilaoBean.setGerente(rs.getString("gerente"));
				leilaoBean.setLeiloeiro(rs.getString("leiloeiro"));
				leilaoBean.setNumeroParcela(rs.getInt("numero_parcela"));
				leilaoBean.setTotalComissao(rs.getDouble("total_comissao"));
				leilaoBean.setTotalTaxaInscricao(rs.getDouble("total_taxa_inscricao"));
				leilaoBean.setTotalVendido(rs.getDouble("total_vendido"));
				leilaoBean.setUf(rs.getString("uf"));
				leilaoBean.setUtilizaComissaoVendedor(rs.getBoolean("utiliza_comissao_vendedor"));
				leilaoBean.setUtilizaTaxaInscricao(rs.getBoolean("utiliza_taxa_inscricao"));
				
				/**
				 * Agrupar Parcelas
				 */
	            String sqlAgruparParcelas = "select * from leilao.agrupar_parcela a join leilao.leilao_parcela lp on (a.id = lp.id_agrupar_parcela) where lp.id_leilao = ?;";
				PreparedStatement psAgruparParcelas = con.prepareStatement(sqlAgruparParcelas);
				psAgruparParcelas.setInt(1, id);
				ResultSet setAgruparParcelas = psAgruparParcelas.executeQuery();
				
				while (setAgruparParcelas.next()) {
					AgruparParcelas agruparParcela = new AgruparParcelas();
					agruparParcela.setId(setAgruparParcelas.getInt("id"));
					agruparParcela.setAgruparParcela(setAgruparParcelas.getInt("agrupar_parcela"));
					listaAgruparParcelas.add(agruparParcela);
				}
				
				leilaoBean.setAgruparParcelas(listaAgruparParcelas);
				
				List<Lotes> listaDeLotes = new ArrayList<Lotes>();
				String sqlLotesLeilao = "select a.nome nomeanimal,r.nome descraca,lote2.* from leilao.lote_animal"+
										" join leilao.lote2 on lote2.idlote = lote_animal.id_lote"+
										" join leilao.animal a on a.id = lote2.idanimal"+
										" join leilao.raca r on r.id = lote2.idraca where lote2.idleilao=?";
				PreparedStatement psLoteLeilao = con.prepareStatement(sqlLotesLeilao);
				psLoteLeilao.setInt(1, id);
				ResultSet setLotes = psLoteLeilao.executeQuery();
				while (setLotes.next()) {
					Lotes lotesBean = new Lotes();
					lotesBean.setId(setLotes.getInt("idLote"));
					lotesBean.setCriador(setLotes.getString("criador"));
					//lotesBean.setObservacao(setLotes.getString("observacao"));
					//lotesBean.setPreposto(setLotes.getString("preposto"));
					listaDeLotes.add(lotesBean);
				}
				
				leilaoBean.setLotes(listaDeLotes);
			}
			
//			ImagemLeilao imagemLeilao = imagemService.findByImagemLeilao(7); //TODO : leilaoBean.getId()
//			File folder = new File(sContext.getRealPath("/img"));
//            if (!folder.exists()){
//                folder.mkdirs();
//                System.out.println("criou?");
//            }
//            String nomeArquivo = "7.jpg"; //TODO :leilaoBean.getId()
//            String arquivo = sContext.getRealPath("/img") + File.separator + nomeArquivo;
//            criaArquivo(imagemLeilao.getImagem(), arquivo);
			
			return leilaoBean;
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
	
	public void delete(Leilao leilaoBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.leilao WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, leilaoBean.getId());
			ps.execute();
			con.commit();
			ps.close();
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
	
	private void criaArquivo(byte[] bytes, String arquivo) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
 
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
}
