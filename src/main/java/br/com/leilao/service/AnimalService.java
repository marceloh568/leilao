package br.com.leilao.service;

import java.io.Serializable;
import java.util.List;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.AnimalDao;
import br.com.leilao.dao.ExceptionsDao;
import br.com.leilao.model.Animal;
import br.com.leilao.model.Exceptions;

public class AnimalService implements Serializable {

	private static final long serialVersionUID = 1L;

	private AnimalDao animalDao;
	
	private ExceptionsDao exceptionsDao;
	
	public AnimalService() {
		animalDao = new AnimalDao();
		exceptionsDao = new ExceptionsDao();
	}
	
	public boolean salvarAnimal(Animal animal) throws ProjetoException {
		try {
			animalDao.save(animal);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao salvarAnimal()" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "salvarAnimal()"));
			return false;
		}
	}

	public boolean editarAnimal(Animal animal) throws ProjetoException {
		try {
			animalDao.edit(animal,true, null);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao editarAnimal()" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "editarAnimal()"));
			return false;
		}
	}

	public boolean excluirAnimal(Animal animal) throws ProjetoException {
		try {
			animalDao.delete(animal);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao excluirAnimal()" + e);
			exceptionsDao.save(new Exceptions(e.getMessage(), "excluirAnimal()"));
			return false;
		}
	}
	
	public List<Animal> findAll() throws ProjetoException {
		try {
			return animalDao.findAll();
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findAllAnimal()"));
			return null;
		}
	}
	
	public Animal findOne(Integer idAnimal) throws ProjetoException {
		try {
			return animalDao.findOne(idAnimal);
		} catch (ProjetoException e) {
			exceptionsDao.save(new Exceptions(e.getMessage(), "findOneAnimal()"));
			return null;
		}
	}
	
}
