package br.com.leilao.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Animal;
import br.com.leilao.model.GeneologiaMae;
import br.com.leilao.model.GeneologiaPai;
import br.com.leilao.model.RacaBean;

public class AnimalDao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public Integer save(Animal animalBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = " INSERT INTO leilao.animal(pelagem, nome, data_nascimento, numero_de_registro, numero_de_controle, sexo, criador, id_raca, pai, avo_masculino_pai, avo_feminino_pai, bisavo_masculino_parte_avo_masculino_pai, bisavo_feminimo_parte_avo_masculino_pai, " +
						 " mae, avo_masculino_mae, avo_feminino_mae, bisavo_masculino_parte_avo_masculino_mae, bisavo_feminimo_parte_avo_masculino_mae) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;";
			
			ps = con.prepareStatement(sql);
			
			if (animalBean.getPelagem() != null) {
				ps.setString(1, animalBean.getPelagem().toUpperCase());
			} else {
				ps.setNull(1, java.sql.Types.NULL);
			}
			ps.setString(2, animalBean.getNome().toUpperCase());
			if (animalBean.getDataNascimento() != null) {
				ps.setDate(3, new java.sql.Date(animalBean.getDataNascimento().getDate()));
			} else {
				ps.setNull(3, java.sql.Types.NULL);
			}
			
			if (animalBean.getNumeroDeRegistro() != null) {
				ps.setString(4, animalBean.getNumeroDeRegistro().toUpperCase());
			} else {
				ps.setNull(4, java.sql.Types.NULL);
			}

			if (animalBean.getNumeroDeControle() != null) {
				ps.setString(5, animalBean.getNumeroDeControle().toUpperCase());
			} else {
				ps.setNull(5, java.sql.Types.NULL);
			}
			
			if (animalBean.getSexo() != null) {
				ps.setString(6, animalBean.getSexo().toUpperCase());
			} else {
				ps.setNull(6, java.sql.Types.NULL);
			}

			if (animalBean.getCriador() != null) {
				ps.setString(7, animalBean.getCriador().toUpperCase());
			} else {
				ps.setNull(7, java.sql.Types.NULL);
			}

			if (animalBean.getRacaBean() != null) {
				ps.setInt(8, animalBean.getRacaBean().getId());
			} else {
				ps.setNull(8, java.sql.Types.NULL);
			}
			
			/**
			 * PAI
			 */
			ps.setString(9, animalBean.getGeneologiaPai().getPai().toUpperCase());
			ps.setString(10, animalBean.getGeneologiaPai().getAvoMasculino().toUpperCase());
			ps.setString(11, animalBean.getGeneologiaPai().getAvoFeminino().toUpperCase());
			ps.setString(12, animalBean.getGeneologiaPai().getBisavoMasculino().toUpperCase());
			ps.setString(13, animalBean.getGeneologiaPai().getBisavoFeminino().toUpperCase());
			
			/**
			 * MAE
			 */
			ps.setString(14, animalBean.getGeneologiaMae().getMae().toUpperCase());
			ps.setString(15, animalBean.getGeneologiaMae().getAvoMasculino().toUpperCase());
			ps.setString(16, animalBean.getGeneologiaMae().getAvoFemenino().toUpperCase());
			ps.setString(17, animalBean.getGeneologiaMae().getBisavoMasculino().toUpperCase());
			ps.setString(18, animalBean.getGeneologiaMae().getBisavoFemenino().toUpperCase());
			
			ResultSet set = ps.executeQuery();
			Integer retorno = null;
			while (set.next()) {
				retorno = set.getInt(1);
			}
			
			con.commit();
			ps.close();
			return retorno;
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
	
	public List<Animal> buscaAnimal (String s) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select id, nome from leilao.animal where nome like ? order by nome";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+s.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<Animal> colecao = new ArrayList<Animal>();
			while (rs.next()) {
				Animal animal = new Animal();
				animal.setId(rs.getInt("id"));
				animal.setNome(rs.getString("nome"));
				colecao.add(animal);
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
			}
		}
	}	
	
	
	public List<String> buscaPaiAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select pai from leilao.animal where pai like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("pai"));
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
			}
		}
	}
	
	public List<String> buscaAvoPaiAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select avo_masculino_pai from leilao.animal where avo_masculino_pai like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("avo_masculino_pai"));
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
			}
		}
	}	
	
	public List<String> buscaAvoFPaiAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select avo_feminino_pai from leilao.animal where avo_feminino_pai like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("avo_feminino_pai"));
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
			}
		}
	}	
	
	public List<String> buscaMaeAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select mae from leilao.animal where mae like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("mae"));
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
			}
		}
	}
	
	public List<String> buscaAvoMaeAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select avo_masculino_mae from leilao.animal where avo_masculino_mae like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("avo_masculino_mae"));
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
			}
		}
	}
	public List<String> buscaAvoFMaeAnimal (String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "select avo_feminino_mae from leilao.animal where avo_feminino_mae like ? order by id;";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+nome.toUpperCase()+"%");
			ResultSet rs = ps.executeQuery();
			List<String> colecao = new ArrayList<String>();
			while (rs.next()) {
				colecao.add(rs.getString("avo_feminino_mae"));
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
			}
		}
	}
	
	public void edit(Animal animalBean, Boolean comitar, Connection con2) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = null;
		if (comitar)
			con = ConnectFactory.getConnection();
		try {

			String sql = " UPDATE leilao.animal SET pelagem=?, nome=?, data_nascimento=?, numero_de_registro=?, numero_de_controle=?, sexo=?, criador=?, id_raca=?, pai=?, avo_masculino_pai=?, " + 
						 " avo_feminino_pai=?, bisavo_masculino_parte_avo_masculino_pai=?,  bisavo_feminimo_parte_avo_masculino_pai=?, mae=?, avo_masculino_mae=?, avo_feminino_mae=?, bisavo_masculino_parte_avo_masculino_mae=?, " +
					     " bisavo_feminimo_parte_avo_masculino_mae=? WHERE id=?; ";
			
			if (comitar)
				ps = con.prepareStatement(sql);
			else
				ps = con2.prepareStatement(sql);	
			ps.setString(1, animalBean.getPelagem().toUpperCase());
			ps.setString(2, animalBean.getNome().toUpperCase());
			if (animalBean.getDataNascimento() != null) {
				ps.setDate(3, new java.sql.Date(animalBean.getDataNascimento().getDate()));
			} else {
				ps.setNull(3, java.sql.Types.NULL);
			}
			ps.setString(4, animalBean.getNumeroDeRegistro().toUpperCase());
			ps.setString(5, animalBean.getNumeroDeControle().toUpperCase());
			ps.setString(6, animalBean.getSexo().toUpperCase());
			ps.setString(7, animalBean.getCriador().toUpperCase());
			ps.setInt(8, animalBean.getRacaBean().getId());
			
			/**
			 * PAI
			 */
			ps.setString(9, animalBean.getGeneologiaPai().getPai().toUpperCase());
			ps.setString(10, animalBean.getGeneologiaPai().getAvoMasculino().toUpperCase());
			ps.setString(11, animalBean.getGeneologiaPai().getAvoFeminino().toUpperCase());
			ps.setString(12, animalBean.getGeneologiaPai().getBisavoMasculino().toUpperCase());
			ps.setString(13, animalBean.getGeneologiaPai().getBisavoFeminino().toUpperCase());
			
			/**
			 * MAE
			 */
			ps.setString(14, animalBean.getGeneologiaMae().getMae().toUpperCase());
			ps.setString(15, animalBean.getGeneologiaMae().getAvoMasculino().toUpperCase());
			ps.setString(16, animalBean.getGeneologiaMae().getAvoFemenino().toUpperCase());
			ps.setString(17, animalBean.getGeneologiaMae().getBisavoMasculino().toUpperCase());
			ps.setString(18, animalBean.getGeneologiaMae().getBisavoFemenino().toUpperCase());
			
			ps.setInt(19, animalBean.getId());
			ps.execute();
			if (comitar)
			con.commit();
			ps.close();
		} catch (Exception sqle) {
			throw new ProjetoException(sqle);
		} finally {
			try {
				if (comitar)
				con.close();
			} catch (Exception sqlc) {
				sqlc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void delete(Animal animalBean) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		try {
			String sql = "DELETE FROM leilao.animal WHERE id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, animalBean.getId());
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
	
	
	public Animal findOne(Integer idAnimal) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Animal animal = new Animal();
		GeneologiaMae geneologiaMae = new GeneologiaMae();
		GeneologiaPai geneologiaPai = new GeneologiaPai();
		RacaBean racaBean = new RacaBean();
		try {
			String sql = "select * from leilao.animal where id = ?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idAnimal);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				animal.setId(rs.getInt("id"));
				animal.setNome(rs.getString("nome"));
				animal.setCriador(rs.getString("criador"));
				animal.setDataNascimento(rs.getDate("data_nascimento"));
				animal.setNumeroDeControle(rs.getString("numero_de_controle"));
				animal.setNumeroDeRegistro(rs.getString("numero_de_registro"));
				animal.setPelagem(rs.getString("pelagem"));
				animal.setSexo(rs.getString("sexo"));
				
				geneologiaPai.setPai(rs.getString("pai"));
				geneologiaPai.setAvoMasculino(rs.getString("avo_masculino_pai"));
				geneologiaPai.setAvoFeminino(rs.getString("avo_feminino_pai"));
				geneologiaPai.setBisavoMasculino(rs.getString("bisavo_masculino_parte_avo_masculino_pai"));
				geneologiaPai.setBisavoFeminino(rs.getString("bisavo_feminimo_parte_avo_masculino_pai"));
				
				geneologiaMae.setMae(rs.getString("mae"));
				geneologiaMae.setAvoMasculino(rs.getString("avo_masculino_mae"));
				geneologiaMae.setAvoFemenino(rs.getString("avo_feminino_mae"));
				geneologiaMae.setBisavoMasculino(rs.getString("bisavo_masculino_parte_avo_masculino_mae"));
				geneologiaMae.setBisavoFemenino(rs.getString("bisavo_feminimo_parte_avo_masculino_mae"));

				racaBean.setId(rs.getInt("id_raca"));
				animal.setGeneologiaPai(geneologiaPai);
				animal.setGeneologiaMae(geneologiaMae);
				animal.setRacaBean(racaBean);
			}
			return animal;
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

	public Animal findByName(String nome) throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		Animal animal = new Animal();
		GeneologiaMae geneologiaMae = new GeneologiaMae();
		GeneologiaPai geneologiaPai = new GeneologiaPai();
		RacaBean racaBean = new RacaBean();
		try {
			String sql = "select * from leilao.animal where nome = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, nome.toUpperCase());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				animal.setId(rs.getInt("id"));
				animal.setNome(rs.getString("nome"));
				animal.setCriador(rs.getString("criador"));
				animal.setDataNascimento(rs.getDate("data_nascimento"));
				animal.setNumeroDeControle(rs.getString("numero_de_controle"));
				animal.setNumeroDeRegistro(rs.getString("numero_de_registro"));
				animal.setPelagem(rs.getString("pelagem"));
				animal.setSexo(rs.getString("sexo"));
				
				geneologiaPai.setPai(rs.getString("pai"));
				geneologiaPai.setAvoMasculino(rs.getString("avo_masculino_pai"));
				geneologiaPai.setAvoFeminino(rs.getString("avo_feminino_pai"));
				geneologiaPai.setBisavoMasculino(rs.getString("bisavo_masculino_parte_avo_masculino_pai"));
				geneologiaPai.setBisavoFeminino(rs.getString("bisavo_feminimo_parte_avo_masculino_pai"));
				
				geneologiaMae.setMae(rs.getString("mae"));
				geneologiaMae.setAvoMasculino(rs.getString("avo_masculino_mae"));
				geneologiaMae.setAvoFemenino(rs.getString("avo_feminino_mae"));
				geneologiaMae.setBisavoMasculino(rs.getString("bisavo_masculino_parte_avo_masculino_mae"));
				geneologiaMae.setBisavoFemenino(rs.getString("bisavo_feminimo_parte_avo_masculino_mae"));
				
				racaBean.setId(rs.getInt("id_raca"));

				animal.setGeneologiaPai(geneologiaPai);
				animal.setGeneologiaMae(geneologiaMae);
				animal.setRacaBean(racaBean);
				
			}
			return animal;
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
	
	public List<Animal> findAll() throws ProjetoException {
		PreparedStatement ps = null;
		Connection con = ConnectFactory.getConnection();
		List<Animal> listaDeAnimais = new ArrayList<Animal>();
		try {
			String sql = "select id, nome, criador from leilao.animal;";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Animal animalBean = new Animal();
				animalBean.setId(rs.getInt("id"));
				animalBean.setNome(rs.getString("nome"));
				animalBean.setCriador(rs.getString("criador"));
				listaDeAnimais.add(animalBean);
			}
			return listaDeAnimais;
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
