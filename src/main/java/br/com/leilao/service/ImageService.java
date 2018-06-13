package br.com.leilao.service;

import java.io.Serializable;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ImagemDao;
import br.com.leilao.model.ImagemLeilao;

public class ImageService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ImagemDao imagemDao;
	
	public ImageService() {
		imagemDao = new ImagemDao();
	}
	
	public boolean salvarImagem(ImagemLeilao imagemLeilao) {
		try {
			imagemDao.save(imagemLeilao);
			return true;
		} catch (ProjetoException e) {
			System.out.println("erro ao salvarImagem" + e);
			return false;
		}
	}

	public ImagemLeilao findByImagemLeilao(Integer id) {
		ImagemLeilao imgLeilao = null;
		try {
			imgLeilao = imagemDao.findByImageLeilao(id);
			return imgLeilao;
		} catch (ProjetoException e) {
			System.out.println("erro ao findByImagemLeilao" + e);
			return null;
		}
	}
	
}
