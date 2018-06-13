package br.com.leilao.comum.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.leilao.model.Leilao;

public class RefreshLeilaoSession implements Serializable {

	private static final long serialVersionUID = 1L;

	public void selectLeilao(Leilao leilao) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("leilao", leilao);
	}

}
