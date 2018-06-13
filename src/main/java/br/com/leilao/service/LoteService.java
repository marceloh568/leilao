package br.com.leilao.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.dao.LoteDao;
import br.com.leilao.model.CompradoresWrapper;
import br.com.leilao.model.Exceptions;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Lotes;

public class LoteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoteDao loteDao;

	private ExceptionsDao exceptionsDao;

	public LoteService() {
		loteDao = new LoteDao();
	}
	
	public boolean salvarLote(Lotes loteBean, List<CompradoresWrapper> listCompradoresWrapper) throws ProjetoException{
		try {
			loteDao.save(loteBean, listCompradoresWrapper);
			return true;
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarLote"));
			return false;
		}
	}

	public boolean editarLote(Lotes loteBean) throws ProjetoException{
		try {
			loteDao.update(loteBean);
			return true;
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarLote"));
			return false;
		}
	}
	public String getMaxId() throws ProjetoException{
		try {
			return loteDao.getMaxIdLote();
		} catch (ProjetoException e) {
			return null;
		}
	}

	public boolean excluirLote(Lotes loteBean) throws ProjetoException{
		try {
			loteDao.delete(loteBean);
			return true;
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirLote"));
			return false;
		}
	}

	public Lotes findOne(Integer id) throws ProjetoException {
		try {
			return loteDao.findOne(id);
		} catch (ProjetoException e) {
			System.out.println("erro findOneLote" + e.getMessage());
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneLote"));
			return null;
		}
	}

	public List<Lotes> findLotesLeilao() throws ProjetoException{
		try {
			return loteDao.findLotesLeilao();
		} catch (ProjetoException e) {
			System.out.println("erro findLotesLeilao" + e.getMessage());
			exceptionsDao.save(new Exceptions(e.getMessage(), "findLotesLeilao"));
			return null;
		}
	}
	
	public boolean verificaSeAnimalJaCadastrado(Integer idAnimal) throws ProjetoException {
		 if(loteDao.verifcaAnimalJaCadastradoEmLote(idAnimal) > 0){
			return true;
		 } else {
			 return false;
		 }
	}
	
	@SuppressWarnings("unused")
	private void atualizarLeilaoSessao(Leilao leilao) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("leilao", leilao);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/leilao/pages/home/dados.faces?dados=true");
	}

}
