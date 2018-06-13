package br.com.leilao.service;

import java.io.Serializable;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.dao.LeilaoDao;
import br.com.leilao.model.Exceptions;
import br.com.leilao.model.Leilao;

public class LeilaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	private LeilaoDao leilaoDao;

	private ExceptionsDao exceptionsDao;
	
	public LeilaoService () {
		leilaoDao = new LeilaoDao();
		exceptionsDao = new ExceptionsDao();
	}
	
	public boolean salvarLeilao(Leilao leilao) throws ProjetoException {
		try {
			leilaoDao.save(leilao);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao salvar leilao" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarLeilao"));
			return false;
		}
	}
	
	public boolean editarLeilao(Leilao leilao) throws ProjetoException {
		try {
			leilaoDao.update(leilao);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao editar leilao" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarLeilao"));
			return false;
		}
	}
	
	public boolean excluirLeilao(Leilao leilao) throws ProjetoException {
		try {
			leilaoDao.delete(leilao);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao excluir leilao" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirLeilao"));
			return false;
		}
	}
	
	public List<Leilao> findAll() throws ProjetoException {
		try {
			return leilaoDao.findAll();
		} catch (ProjetoException e) {
			System.out.println("erro findAll leilao" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "findAllLeilao"));
			return null;
		}
	}
	
	public Leilao findOne(Integer idLeilao) throws ProjetoException {
		try {
			return leilaoDao.findOne(idLeilao);
		} catch (ProjetoException e) {
			System.out.println("erro findOne leilao" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneLeilao"));
			return null;
		}
	}
	
}
