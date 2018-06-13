package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.RacaBean;

public class RacaDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void salvarRaca(RacaBean racaBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "INSERT INTO leilao.raca(nome, criacao, criacao_tipo) VALUES ( ?, ?, ?); ";
			ps = con.prepareStatement(sql);
			ps.setString(1, racaBean.getNome().toUpperCase());
			ps.setBoolean(2, racaBean.getCriacao());
			ps.setString(3, racaBean.getTipoCriacao());
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
	
	public void editarRaca(RacaBean racaBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "UPDATE leilao.raca SET nome=?, criacao=?, criacao_tipo=? WHERE id=?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, racaBean.getNome().toUpperCase());
			ps.setBoolean(2, racaBean.getCriacao());
			ps.setString(3, racaBean.getTipoCriacao());
			ps.setInt(4, racaBean.getId());
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
	
	public void excluirRaca(RacaBean racaBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.raca WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, racaBean.getId());
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
	
	public RacaBean findOne(Integer idRaca) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		RacaBean racaBean = new RacaBean();
		try {
			String sql = "select * from leilao.raca where id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idRaca);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				racaBean.setId(rs.getInt("id"));
				racaBean.setNome(rs.getString("nome"));
				racaBean.setCriacao(rs.getBoolean("criacao"));
				racaBean.setTipoCriacao(rs.getString("criacao_tipo"));
			}
			return racaBean;
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
	
	public List<RacaBean> findAll() throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<RacaBean> listaDeRacas = new ArrayList<RacaBean>();
		try {
			String sql = "select * from leilao.raca;";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RacaBean racaBean = new RacaBean();
				racaBean.setId(rs.getInt("id"));
				racaBean.setNome(rs.getString("nome"));
				racaBean.setCriacao(rs.getBoolean("criacao"));
				racaBean.setTipoCriacao(rs.getString("criacao_tipo"));
				listaDeRacas.add(racaBean);
			}
			return listaDeRacas;
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
	
}
