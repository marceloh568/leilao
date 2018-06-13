package br.com.leilao.comum.util;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.leilao.acl.model.Permissoes;
import br.com.leilao.acl.model.Sistema;
import br.com.leilao.control.UsuarioController;
import br.com.leilao.model.UsuarioBean;

@SuppressWarnings("serial")
public class AuthorizationListener implements PhaseListener {

    private List<Permissoes> permsUsuarioLogado;
    
    @SuppressWarnings({ "unchecked", "unused" })
	@Override
    public void afterPhase(PhaseEvent event) {
        // Adiquirindo o FacesContext.
        FacesContext facesContext = event.getFacesContext();

        // Página Atual.
        String currentPage = facesContext.getViewRoot().getViewId();

        // Verifica se está na página de login.
        boolean isLoginPage = (currentPage.lastIndexOf("login") > -1);
        
        boolean principal = (currentPage.replace(".xhtml", ".faces")
            .equals("/pages/comum/selecaoSistema.faces"));
        
        boolean contato = (currentPage.replace(".xhtml", ".faces")
            .equals("/pages/comum/contato.faces"));
        
        boolean primeiroacesso = (currentPage.replace(".xhtml", ".faces")
            .equals("/pages/comum/primeiroAcesso.faces"));

        Integer count = 0;
        boolean naoPermitido = false;

        // Recuperando objetos da sessão.
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        UsuarioBean usuarioLogado = (UsuarioBean) session.getAttribute("obj_usuario");

        Sistema sistemaLogado = (Sistema) session.getAttribute("sistema_logado");

        permsUsuarioLogado = (List<Permissoes>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("perms_usuario");

        // Redireciona para a página de login quando a sessão expira.
        if (!isLoginPage && usuarioLogado == null) {
            try {
            	UsuarioController.timeOut();
                if (SessionUtil.getSession() != null) {
                    SessionUtil.getSession().invalidate();

                    FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("sessao_expirada", "S");
                }
                FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/agreste-leilao/pages/comum/login.faces");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (!isLoginPage && !principal && usuarioLogado != null) {
            for (Permissoes p : permsUsuarioLogado) {
                String aux = p.getMenu().getUrl();
                if (p.getMenu().getUrl() != null) {
                    if (currentPage.replace(".xhtml", ".faces").replace("?faces-redirect=true", "")
                            .equals(aux.replace("?faces-redirect=true", ""))) {
                        if (currentPage.contains(sistemaLogado.getSigla())) {
                            count++;
                        }
                    }
                }
            }
            if (count < 1) {
                naoPermitido = true;
            }
            if (permsUsuarioLogado.isEmpty() && !principal) {
                naoPermitido = true;
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}