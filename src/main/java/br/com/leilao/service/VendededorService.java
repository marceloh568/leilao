package br.com.leilao.service;

import java.io.Serializable;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.dao.VendedorDao;
import br.com.leilao.model.Exceptions;
import br.com.leilao.model.VendedorBean;

public class VendededorService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VendedorDao vendedorDao;

	private ExceptionsDao exceptionsDao;

	public VendededorService(){
		vendedorDao = new VendedorDao();
	}
	
	public boolean salvarVendedor(VendedorBean vendedorBean) throws ProjetoException {
		try {
			vendedorDao.save(vendedorBean);
			return true;
		} catch (Exception e) {
			System.out.println("erro ao cadastrar vendedor " + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarVendedor"));
			return false;
		}
	}
	
	public boolean editarVendedor(VendedorBean vendedorBean) throws ProjetoException {
		try {
			vendedorDao.edit(vendedorBean);
			return true;
		} catch (Exception e) {
			System.out.println("erro ao editar vendedor" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarVendedor"));
			return false;
		}
	}
	
	public boolean excluirVendedor(Integer idVendedor) throws ProjetoException {
		try {
			vendedorDao.delete(idVendedor);
			return true;
		} catch (Exception e) {
			System.out.println("erro ao excluir vendedor" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirVendedor"));
			return false;
		}
	}
	
	public List<VendedorBean> findAll() throws ProjetoException {
		try {
			return vendedorDao.findAll();
		} catch (Exception e) {
			System.out.println("erro ao listar vendedor findAll() " + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "findAllVendedor"));
			return null;
		}
	}
	
	public VendedorBean findOne(Integer vendedorBean) throws ProjetoException {
		try {
			return vendedorDao.findOne(vendedorBean);
		} catch (Exception e) {
			System.out.println("erro findOne vendededor " + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneVendedor"));
			return null;
		}
	}
	
	public VendedorBean findByCPF(String cpf) throws ProjetoException {
		try {
			return vendedorDao.findByCPF(cpf);
		} catch (Exception e) {
			System.out.println("erro findOne vendededor " + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "findByCPF"));
			return null;
		}
	}
	
}
