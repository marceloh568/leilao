package br.com.leilao.service;

import java.io.Serializable;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.BancoDao;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.model.Banco;
import br.com.leilao.model.Exceptions;

public class BancoService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BancoDao bancoDao;
	
	private ExceptionsDao exceptionsDao;
	
	public BancoService(){
		bancoDao = new BancoDao();
		exceptionsDao = new ExceptionsDao();
	}
	
	public boolean salvarBanco(Banco bancoBean) throws ProjetoException {
		try {
			bancoDao.salvarBanco(bancoBean);
			return true;
		} catch (Exception e){
			System.out.println("erro ao salvar banco" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarBanco"));
			return false;
		}
	}
	
	public boolean editarBanco(Banco bancoBean) throws ProjetoException {
		try {
			bancoDao.editarBanco(bancoBean);
			return true;
		} catch (Exception e){
			System.out.println("erro ao editar banco" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarBanco"));
			return false;
		}
	}
	
	public boolean excluirBanco(Banco bancoBean) throws ProjetoException {
		try {
			bancoDao.excluirBanco(bancoBean);
			return true;
		} catch (Exception e){
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirBanco"));
			return false;
		}
	}
	
	public Banco findOne(Integer id) throws ProjetoException {
		try {
			return bancoDao.findOne(id);
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneBanco"));
			return null;
		}
	}
	
	public List<Banco> findAll() throws ProjetoException {
		try {
			return bancoDao.findAll();
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findAllBanco"));
			return null;
		}
	}
	
	
}
