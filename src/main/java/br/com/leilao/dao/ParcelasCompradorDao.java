package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.ParcelasComprador;

public class ParcelasCompradorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	public void save(List<ParcelasComprador> listaDeParcelas) throws ProjetoException{
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {	
			for (int i = 0; i < listaDeParcelas.size(); i++) {
				String sql = "INSERT INTO leilao.parcelas_comprador(numero_parcela, valor_parcela, id_comprador) VALUES (?, ?, ?);";
				ps = con.prepareStatement(sql);
				ps.setInt(1, listaDeParcelas.get(i).getNumeroDaParcela());
				ps.setDouble(2, listaDeParcelas.get(i).getValorDaParcela());
				ps.setInt(3, listaDeParcelas.get(i).getComprador().getId());
				ps.execute();
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
	
	
}
