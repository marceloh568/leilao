package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Animal;
import br.com.leilao.model.Banco;
import br.com.leilao.model.ContaCorrente;
import br.com.leilao.model.Lotes;
import br.com.leilao.model.RacaBean;
import br.com.leilao.model.VendedorBean;

public class VendedorDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public void save(VendedorBean vendedorBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Integer idVendedor = null;
		try {
			String sql = " INSERT INTO leilao.vendedor(nome, apelido, situacao, cpf_cgc, endereco, bairro, cidade,cep, uf, email, fone, email2, fone2, fax, residencial, comercial, data_atualizacao) "+
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING  id ;";
					ps = con.prepareStatement(sql);
			ps.setString(1, vendedorBean.getNome().toUpperCase());
			ps.setString(2, vendedorBean.getApelido().toUpperCase());
			ps.setString(3, vendedorBean.getSituacao().toUpperCase());
			ps.setString(4, vendedorBean.getCpfcgc().toUpperCase());
			ps.setString(5, vendedorBean.getEndereco().toUpperCase());
			ps.setString(6, vendedorBean.getBairro().toUpperCase());
			ps.setString(7, vendedorBean.getCidade().toUpperCase());
			ps.setString(8, vendedorBean.getCep().toUpperCase());
			ps.setString(9, vendedorBean.getUf().toUpperCase());
			ps.setString(10, vendedorBean.getEmail().toUpperCase());
			ps.setString(11, vendedorBean.getFone().toUpperCase());
			ps.setString(12, vendedorBean.getEmail2().toUpperCase());
			ps.setString(13, vendedorBean.getFone2().toUpperCase());
			ps.setString(14, vendedorBean.getFax().toUpperCase());
			ps.setString(15, vendedorBean.getFoneResidencial().toUpperCase());
			ps.setString(16, vendedorBean.getFoneComercial().toUpperCase());
			ps.setDate(17, new java.sql.Date(new java.util.Date().getDate()));
			ResultSet res = ps.executeQuery();
			
			while (res.next()) {
				idVendedor = res.getInt(1);
			}

			for (int i=0;i<vendedorBean.getAnimaisQueCria().size();i++) {
				String sqlVendedorRaca = "INSERT INTO leilao.vendedor_raca(id_raca, id_vendedor) VALUES (?, ?);";
				PreparedStatement psVendedorRaca;
				psVendedorRaca = con.prepareStatement(sqlVendedorRaca);
				psVendedorRaca.setInt(1, vendedorBean.getAnimaisQueCria().get(i).getId());
				psVendedorRaca.setInt(2, idVendedor);
				psVendedorRaca.execute();
			}
			
			for (int i = 0; i < vendedorBean.getContasCorrentes().size(); i++) {
				String sqlContaCorrente = "INSERT INTO leilao.conta_corrente(agencia, titular, conta, id_banco, id_vendedor) VALUES (?, ?, ?, ?, ?);";
				PreparedStatement psContaCorrente;
				psContaCorrente = con.prepareStatement(sqlContaCorrente);
				psContaCorrente.setString(1, vendedorBean.getContasCorrentes().get(i).getAgencia().toUpperCase());
				psContaCorrente.setString(2, vendedorBean.getContasCorrentes().get(i).getTitular().toUpperCase());
				psContaCorrente.setString(3, vendedorBean.getContasCorrentes().get(i).getContaCorrente().toUpperCase());
				psContaCorrente.setInt(4, vendedorBean.getContasCorrentes().get(i).getBanco().getId());
				psContaCorrente.setInt(5, idVendedor);
				psContaCorrente.executeUpdate();
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
	
	public void edit(VendedorBean vendedorBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = " UPDATE leilao.vendedor SET nome=?, apelido=?, situacao=?, cpf_cgc=?, endereco=?, bairro=?, cidade=?, cep=?, uf=?, email=?, fone=?, email2=?, fone2=?, fax=?, residencial=?, comercial=?, data_atualizacao=? WHERE id=? ;";
			ps = con.prepareStatement(sql);
			ps.setString(1, vendedorBean.getNome().toUpperCase());
			ps.setString(2, vendedorBean.getApelido().toUpperCase());
			ps.setString(3, vendedorBean.getSituacao().toUpperCase());
			ps.setString(4, vendedorBean.getCpfcgc().toUpperCase());
			ps.setString(5, vendedorBean.getEndereco().toUpperCase());
			ps.setString(6, vendedorBean.getBairro().toUpperCase());
			ps.setString(7, vendedorBean.getCidade().toUpperCase());
			ps.setString(8, vendedorBean.getCep().toUpperCase());
			ps.setString(9, vendedorBean.getUf().toUpperCase());
			ps.setString(10, vendedorBean.getEmail().toUpperCase());
			ps.setString(11, vendedorBean.getFone().toUpperCase());
			ps.setString(12, vendedorBean.getEmail2().toUpperCase());
			ps.setString(13, vendedorBean.getFone2().toUpperCase());
			ps.setString(14, vendedorBean.getFax().toUpperCase());
			ps.setString(15, vendedorBean.getFoneResidencial().toUpperCase());
			ps.setString(16, vendedorBean.getFoneComercial().toUpperCase());
			ps.setDate(17, new java.sql.Date(new java.util.Date().getDate()));
			ps.setInt(18, vendedorBean.getId());
			ps.executeUpdate();
			
			List<RacaBean> listaDeRacaVendedor = vendedorBean.getAnimaisQueCria();
			String sqlDeleteAnimais = "delete from leilao.vendedor_raca where id_vendedor = ? ";
			PreparedStatement psDeleteAnimaisQueCria = con.prepareStatement(sqlDeleteAnimais);
			psDeleteAnimaisQueCria.setInt(1, vendedorBean.getId());
			psDeleteAnimaisQueCria.executeUpdate();

			for (int i=0;i<listaDeRacaVendedor.size();i++) {
				String sqlVendedorRaca = "INSERT INTO leilao.vendedor_raca(id_raca, id_vendedor) VALUES (?, ?);";
				PreparedStatement psVendedorRaca;
				psVendedorRaca = con.prepareStatement(sqlVendedorRaca);
				psVendedorRaca.setInt(1, listaDeRacaVendedor.get(i).getId());
				psVendedorRaca.setInt(2, vendedorBean.getId());
				psVendedorRaca.execute();
			}
			
			List<ContaCorrente> listaDeContaCorrenteVendedor = vendedorBean.getContasCorrentes();
			String sqlContaCorrente = "delete from leilao.conta_corrente where id_vendedor = ? ";
			PreparedStatement psDeleteContaCorrente = con.prepareStatement(sqlContaCorrente);
			psDeleteContaCorrente.setInt(1, vendedorBean.getId());
			psDeleteContaCorrente.executeUpdate();
			
			for (int i = 0; i < listaDeContaCorrenteVendedor.size(); i++) {
				String sqlContaCorrenteInsert = "INSERT INTO leilao.conta_corrente(agencia, titular, conta, id_banco, id_vendedor) VALUES (?, ?, ?, ?, ?); ";
				PreparedStatement psContaCorrente;
				psContaCorrente = con.prepareStatement(sqlContaCorrenteInsert);
				psContaCorrente.setString(1, vendedorBean.getContasCorrentes().get(i).getAgencia().toUpperCase());
				psContaCorrente.setString(2, vendedorBean.getContasCorrentes().get(i).getTitular().toUpperCase());
				psContaCorrente.setString(3, vendedorBean.getContasCorrentes().get(i).getContaCorrente().toUpperCase());
				psContaCorrente.setInt(4, vendedorBean.getContasCorrentes().get(i).getBanco().getId());
				psContaCorrente.setInt(5, vendedorBean.getId());
				psContaCorrente.executeUpdate();
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
	
	public void delete(Integer idVendedor) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.vendedor WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idVendedor);
			ps.execute();
			
			String sqlVendedorRaca = "DELETE FROM leilao.vendedor_raca WHERE id_vendedor = ?;";
			PreparedStatement psVendedorRaca;
			psVendedorRaca = con.prepareStatement(sqlVendedorRaca);
			psVendedorRaca.setInt(1, idVendedor);
			psVendedorRaca.execute();
			
			String sqlContaCorrente = "DELETE FROM leilao.conta_corrente WHERE id_vendedor = ?;";
			PreparedStatement psContaCorrente;
			psContaCorrente = con.prepareStatement(sqlContaCorrente);
			psContaCorrente.setInt(1, idVendedor);
			psContaCorrente.execute();
			
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
	
	public List<VendedorBean> findAll() throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<VendedorBean> listaDeVendedores = new ArrayList<VendedorBean>();
		try {
			String sql = "select id,nome,cpf_cgc,email from leilao.vendedor;";
			ps = con.prepareStatement(sql); 	
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendedorBean vendedorBean = new VendedorBean();
				vendedorBean.setId(rs.getInt("id"));
				vendedorBean.setNome(rs.getString("nome"));
				vendedorBean.setCpfcgc(rs.getString("cpf_cgc"));
				vendedorBean.setEmail(rs.getString("email"));
				listaDeVendedores.add(vendedorBean);
			}
			return listaDeVendedores;
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
	
	public VendedorBean findOne(Integer idVendedorBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		VendedorBean vendedorBean = new VendedorBean();
		try {
			String sql = "select * from leilao.vendedor where id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idVendedorBean);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				vendedorBean.setId(rs.getInt("id"));
				vendedorBean.setNome(rs.getString("nome"));
				vendedorBean.setApelido(rs.getString("apelido"));
				vendedorBean.setBairro(rs.getString("bairro"));
				vendedorBean.setCep(rs.getString("cep"));
				vendedorBean.setCidade(rs.getString("cidade"));
				vendedorBean.setCpfcgc(rs.getString("cpf_cgc"));
				vendedorBean.setDataAtualizacao(rs.getDate("data_atualizacao"));
				vendedorBean.setEmail(rs.getString("email"));
				vendedorBean.setEmail2(rs.getString("email2"));
				vendedorBean.setEndereco(rs.getString("endereco"));
				vendedorBean.setUf(rs.getString("uf"));
				vendedorBean.setFax(rs.getString("fax"));
				vendedorBean.setFone(rs.getString("fone"));
				vendedorBean.setFone2(rs.getString("fone2"));
				vendedorBean.setSituacao(rs.getString("situacao"));
				vendedorBean.setFoneComercial(rs.getString("comercial"));
				vendedorBean.setFoneResidencial(rs.getString("residencial"));
			}
			
			String sqlAnimaisQueCria = "select * from leilao.raca r join leilao.vendedor_raca v on (r.id = v.id_raca) where id_vendedor = ?;";
			PreparedStatement psAnimaisQueCria = con.prepareStatement(sqlAnimaisQueCria);
			psAnimaisQueCria.setInt(1, idVendedorBean);
			rs = psAnimaisQueCria.executeQuery();
			List<RacaBean> listaDeRacasVendedor = new ArrayList<RacaBean>();

			while (rs.next()) {
				RacaBean racaBean = new RacaBean();
				racaBean.setId(rs.getInt("id"));
				racaBean.setNome(rs.getString("nome"));
				listaDeRacasVendedor.add(racaBean);
			}
			
			String sqlContasCorrente = "select * from leilao.conta_corrente c join leilao.banco b on (c.id_banco = b.id) where id_vendedor = ?;";
			PreparedStatement psContaCorrente = con.prepareStatement(sqlContasCorrente);
			psContaCorrente.setInt(1, idVendedorBean);
			rs = psContaCorrente.executeQuery();
			List<ContaCorrente> listaContaCorrenteVendedor = new ArrayList<ContaCorrente>();
			
			while (rs.next()) {
				ContaCorrente contaCorrente = new ContaCorrente();
				contaCorrente.setId(rs.getInt("id"));
				contaCorrente.setContaCorrente(rs.getString("conta"));
				contaCorrente.setCpfCnpjTitular(rs.getString("titular"));
				contaCorrente.setAgencia(rs.getString("agencia"));
				
				Banco banco = new Banco();
				banco.setId(rs.getInt("id_banco"));
				banco.setDescricao(rs.getString("descricao"));
				banco.setNumero(rs.getString("numero"));
				
				contaCorrente.setBanco(banco);
				
				listaContaCorrenteVendedor.add(contaCorrente);
			}
			
			vendedorBean.setAnimaisQueCria(listaDeRacasVendedor);
			vendedorBean.setContasCorrentes(listaContaCorrenteVendedor);
			
			return vendedorBean;
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
	
	public VendedorBean findByCPF(String cpf) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		VendedorBean vendedorBean = new VendedorBean();
		try {
			String sql = "select * from leilao.vendedor where cpf_cgc = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				vendedorBean.setId(rs.getInt("id"));
				vendedorBean.setNome(rs.getString("nome"));
				vendedorBean.setApelido(rs.getString("apelido"));
				vendedorBean.setBairro(rs.getString("bairro"));
				vendedorBean.setCep(rs.getString("cep"));
				vendedorBean.setCidade(rs.getString("cidade"));
				vendedorBean.setCpfcgc(rs.getString("cpf_cgc"));
				vendedorBean.setDataAtualizacao(rs.getDate("data_atualizacao"));
				vendedorBean.setEmail(rs.getString("email"));
				vendedorBean.setEmail2(rs.getString("email2"));
				vendedorBean.setEndereco(rs.getString("endereco"));
				vendedorBean.setUf(rs.getString("uf"));
				vendedorBean.setFax(rs.getString("fax"));
				vendedorBean.setFone(rs.getString("fone"));
				vendedorBean.setFone2(rs.getString("fone2"));
				vendedorBean.setSituacao(rs.getString("situacao"));
				vendedorBean.setFoneComercial(rs.getString("comercial"));
				vendedorBean.setFoneResidencial(rs.getString("residencial"));
			}
			return vendedorBean;
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
	
	public void salvarCompradorLote(Lotes loteBean, Integer idComprador, Double valorLance, Double valorDesconto, Integer numeroParcelas) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		String sqlInsertCompradorLote = " INSERT INTO leilao.lote_compradores(id_lote, id_comprador, valor_lance, valor_desconto, " +
										" numero_parcela) VALUES (?, ?, ?, ?, ?); ";
		try {
			PreparedStatement psInsertCompradorLote = con.prepareStatement(sqlInsertCompradorLote);
			psInsertCompradorLote.setInt(1, loteBean.getId());
			psInsertCompradorLote.setInt(2, idComprador);
			psInsertCompradorLote.setDouble(3, valorLance);
			psInsertCompradorLote.setDouble(4, valorDesconto);
			psInsertCompradorLote.setInt(5, numeroParcelas);
			psInsertCompradorLote.execute();
			con.commit();
			psInsertCompradorLote.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public List<VendedorBean> buscaVendedor (String s) throws ProjetoException {
		System.out.println("buscaproduto");
				PreparedStatement ps = null;
				Connection con = ConnectFactory.getConnection();

				try {
					List<VendedorBean> listavend = new ArrayList<VendedorBean>();  
					String sql = "select id,nome from leilao.vendedor where nome like ? order by nome";
					 
					ps = con.prepareStatement(sql);
					ps.setString(1, "%"+s.toUpperCase()+"%");
					ResultSet rs = ps.executeQuery();

					List<VendedorBean> colecao = new ArrayList<VendedorBean>();
					
					while (rs.next()) {
						
						VendedorBean vend = new VendedorBean();
						vend.setId(rs.getInt("id"));
						vend.setNome(rs.getString("nome"));
						colecao.add(vend);
						
		;
					
					}
					return colecao;
				} catch (Exception sqle) {

					throw new ProjetoException(sqle);

				} finally {
					try {
						con.close();
					} catch (Exception sqlc) {
						sqlc.printStackTrace();
						System.exit(1);
						// TODO: handle exception
					}

				}
			}	

}
