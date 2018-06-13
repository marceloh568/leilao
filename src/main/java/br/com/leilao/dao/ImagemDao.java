package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.ImagemLeilao;
import br.com.leilao.model.RacaBean;

public class ImagemDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void save(ImagemLeilao imagemLeilao) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Integer idImagem = 0;
		try {
			String sql = "INSERT INTO leilao.imagem(imagem) VALUES (?) returning id;";
			ps = con.prepareStatement(sql);
			ps.setBytes(1, imagemLeilao.getImagem());
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				idImagem = set.getInt(1);
			}
			System.out.println("id img" + idImagem);
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
	
	public ImagemLeilao findByImageLeilao(Integer idLeilao) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		ImagemLeilao imagemLeilao = new ImagemLeilao();
		try {
			String sql = "select i.id,i.imagem from leilao.imagem i join leilao.leilao_imagem li on (i.id = li.id_imagem) where id_leilao = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idLeilao);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				imagemLeilao.setId(rs.getInt("id"));
				imagemLeilao.setImagem(rs.getBytes("imagem"));
			}
			return imagemLeilao;
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
