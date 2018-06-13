package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Banco;

public class BancoDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void salvarBanco(Banco bancoBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {	
			String sql = "INSERT INTO leilao.banco(numero, descricao) VALUES (?, ?);";
			ps = con.prepareStatement(sql);
			ps.setString(1, bancoBean.getNumero().toUpperCase());
			ps.setString(2, bancoBean.getDescricao().toUpperCase());
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
	
	public void editarBanco(Banco bancoBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "UPDATE leilao.banco SET numero=?, descricao=? WHERE id=?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, bancoBean.getNumero().toUpperCase());
			ps.setString(2, bancoBean.getDescricao().toUpperCase());
			ps.setInt(3, bancoBean.getId());
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
	
	public void excluirBanco(Banco bancoBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.banco WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, bancoBean.getId());
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
	
	public Banco findOne(Integer idBanco) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Banco bancoBean = new Banco();
		try {
			String sql = "select * from leilao.banco where id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idBanco);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bancoBean.setId(rs.getInt("id"));
				bancoBean.setNumero(rs.getString("numero"));
				bancoBean.setDescricao(rs.getString("descricao"));
			}
			return bancoBean;
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
	
	public List<Banco> findAll() throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<Banco> listaDeRacas = new ArrayList<Banco>();
		try {
			String sql = "select * from leilao.banco;";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Banco bancoBean = new Banco();
				bancoBean.setId(rs.getInt("id"));
				bancoBean.setNumero(rs.getString("numero"));
				bancoBean.setDescricao(rs.getString("descricao"));
				listaDeRacas.add(bancoBean);
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
