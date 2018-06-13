package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Exceptions;
import br.com.leilao.model.UsuarioBean;

public class ExceptionsDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public void save(Exceptions ex) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		 UsuarioBean coduser = (UsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj_usuario");
		try {
			String sql = "INSERT INTO leilao.exceptions(mensagem, nome_metodo, id_usuario) VALUES (?, ?, ?);";
			ps = con.prepareStatement(sql);
			ps.setString(1, ex.getMensagem());
			ps.setString(2, ex.getNomeMetodo());
			ps.setInt(3, coduser.getCodfunc());
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
}
