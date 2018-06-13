package br.com.leilao.service;

import java.io.Serializable;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.dao.RacaDao;
import br.com.leilao.model.Exceptions;
import br.com.leilao.model.RacaBean;

public class RacaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RacaDao racaDao;

	private ExceptionsDao exceptionsDao;
	
	public RacaService(){
		racaDao = new RacaDao();
	}
	
	public boolean salvarRaca(RacaBean raca) throws ProjetoException{
		try {
			racaDao.salvarRaca(raca);
			return true;
		} catch (Exception e){
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarRaca"));
			return false;
		}
	}
	
	public boolean editarRaca(RacaBean raca) throws ProjetoException{
		try {
			racaDao.editarRaca(raca);
			return true;
		} catch (Exception e){
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarRaca"));
			return false;
		}
	}
	
	public boolean excluirRaca(RacaBean raca) throws ProjetoException{
		try {
			racaDao.excluirRaca(raca);
			return true;
		} catch (Exception e){
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirRaca"));
			return false;
		}
	}
	
	public RacaBean findOne(Integer id) throws ProjetoException{
		try {
			return racaDao.findOne(id);
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneRaca"));
			return null;
		}
	}
	
	public List<RacaBean> findAll() throws ProjetoException{
		try {
			return racaDao.findAll();
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findAllRaca"));
			return null;
		}
	}
	
}
