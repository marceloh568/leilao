package br.com.leilao.control;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.leilao.acl.model.Menu;
import br.com.leilao.acl.model.Permissoes;
import br.com.leilao.acl.model.Sistema;
import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.comum.util.SessionUtil;
import br.com.leilao.dao.IUsuarioDAO;
import br.com.leilao.dao.UsuarioDAO;
import br.com.leilao.model.UsuarioBean;

public class UsuarioController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UsuarioBean usuario;
    private UsuarioBean novousuario, editausuario;
    private UIData id_tupla;
    private List listaUserSecretaria;

    private Object objeto1, menuAdm, nomeUserLogado, permissaoInteressado;
    private Object nomeSecretaria, nomeSetor;
    public static String DescSetor, permissaoProcessos;
    private UIInput senhaInput;
    private UsuarioBean user;
    
    private String sessaoExpirada;
    
    // ACL
    private UsuarioBean usuarioLogado;
    private Sistema sistemaLogado;
    
    private List<Sistema>sistemasUsuarioLogado;
    private List<Permissoes> permsUsuarioLogado;
    private List<Menu> listaMenus;
    
    private MenuModel menuModel;

    public UsuarioController() {
        usuario = new UsuarioBean();
        novousuario = new UsuarioBean();
        editausuario = new UsuarioBean();
        user = new UsuarioBean();
        
        // ACL
        usuarioLogado = new UsuarioBean();
        permsUsuarioLogado = new ArrayList<>();       
        sistemasUsuarioLogado = new ArrayList<>();
        sistemaLogado = new Sistema();       
        listaMenus = new ArrayList<>();     
        menuModel = new DefaultMenuModel();
    }
    
    public static void timeOut() throws IOException {
        
        if(SessionUtil.getSession() != null) {
        
            SessionUtil.getSession().invalidate();
            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("sessao_expirada", "S");
        
        } else {
        
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect("pages/comum/login.faces");
        }
    }
    
    public void fecharDlgExpired() {
        
        sessaoExpirada = "X";
        
        RequestContext.getCurrentInstance().update(":formEXP:outExpired");
        RequestContext.getCurrentInstance().execute("PF('expiredSession').hide();"); 
    }
    
    public void recoverDataFromSession() {
        usuarioLogado = (UsuarioBean) FacesContext
            .getCurrentInstance().getExternalContext().getSessionMap()
            .get("obj_usuario");
        
        sistemasUsuarioLogado = (List<Sistema>) FacesContext
            .getCurrentInstance().getExternalContext().getSessionMap()
            .get("perms_usuario_sis");
        
        permsUsuarioLogado = (List<Permissoes>) FacesContext
            .getCurrentInstance().getExternalContext().getSessionMap()
            .get("perms_usuario");
    }
    
    public boolean verificarPermComp(String codigo, Integer idSistema) {
        boolean valida = false;       
        for(Permissoes perms : permsUsuarioLogado) {            
            if(perms.getIdSistemaFunc() != null && perms.getCodigoFuncao() != null) {
                if(perms.getIdSistemaFunc().equals(idSistema) 
                    && perms.getCodigoFuncao().equals(codigo)) {               
                    valida = true;
                }  
            }                     
        }
        return valida;
    }
    
    public String redirecionar(String url) {
        gerarMenus(sistemaLogado);
        return url;
    }
    
    public void recSistemaLogado(Sistema sistema) {
        sistemaLogado = sistema;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
            .put("sistema_logado", sistema);
    }
    
    public String verificarBolTab(boolean ativo, String tipo) {
        if(ativo == true) {
            return "../../imgs/status_green.png";
        } else if(ativo == false && tipo.equals("injetado")) {
            return "../../imgs/status_yellow.png";
        } else {
            return "../../imgs/status_red.png";
        }
    }
    
    /* Menu dinâmico
    -----------------------------------------------------------------------------*/    
    private void limparMenuModel() {
        menuModel = null;
        menuModel = new DefaultMenuModel();
    }
    
    public void gerarMenus(Sistema sistema) {       
        limparMenuModel();
        List<DefaultSubMenu> menuPai = new ArrayList<>();
        List<DefaultSubMenu> submenu = new ArrayList<>();
        List<DefaultMenuItem> menuItem = new ArrayList<>();         
        List<DefaultSubMenu> menuItemAssSubmenu = new ArrayList<>();
        List<DefaultSubMenu> submenuAssSubMenuPai = new ArrayList<>();
        List<DefaultSubMenu> menusAssociados = new ArrayList<>();
        
        // Gerar menu início.
        DefaultMenuItem item1 = new DefaultMenuItem();
        item1.setValue("Início");
        item1.setUrl(sistema.getUrl().replace("?faces-redirect=true", ""));
        menuModel.addElement(item1);
        
        for(Permissoes p : permsUsuarioLogado) {         
            // Gerar menu pai.
            if((p.getMenu().getIndice() == null) && (p.getMenu().getUrl() == null) 
                && (p.getMenu().getTipo().equals("menuPai"))
                && (p.getIdSistema().equals(sistema.getId())) 
                && (p.getMenu().isAtivo() == true)) {           
                
                DefaultSubMenu mp = new DefaultSubMenu();
                mp.setLabel(p.getMenu().getDescricao());
                mp.setIcon(p.getMenu().getCodigo());
                menuPai.add(mp);
            }
            
            // Gerar submenu.
            if((p.getMenu().getIndice() != null) && (p.getMenu().getUrl() == null) 
                && (p.getMenu().getTipo().equals("submenu"))
                && (p.getIdSistema().equals(sistema.getId())) 
                && (p.getMenu().isAtivo() == true)) {
                
                DefaultSubMenu sb = new DefaultSubMenu();
                sb.setLabel(p.getMenu().getDescricao());
                sb.setIcon(p.getMenu().getCodigo());
                sb.setId(p.getMenu().getIndice());
                submenu.add(sb);
            }
            
            // Gerar menu item com url.
            if((p.getMenu().getIndice() != null) && (p.getMenu().getUrl() != null) 
                && (p.getMenu().getTipo().equals("menuItem")) 
                && (p.getIdSistema().equals(sistema.getId())) 
                && (p.getMenu().isAtivo() == true)) {
                
                Menu mi = p.getMenu();
                
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(mi.getDescricao());
                item.setUrl(mi.getUrl());
                item.setIcon(p.getMenu().getCodigo());
                item.setId(mi.getIndice());
                menuItem.add(item);
            }
            
            // Gerar menu item com action/onclick.
            if((p.getMenu().getIndice() != null) && (p.getMenu().getUrl() == null) 
                && (p.getMenu().getTipo().equals("menuItemRel"))
                && (p.getIdSistema().equals(sistema.getId())) 
                && (p.getMenu().isAtivo() == true)) {
                
                Menu mi = p.getMenu();
                
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(mi.getDescricao());
                
                if(mi.getAction() != null) {
                    item.setCommand(mi.getAction());
                    item.setAjax(false);
                } else {
                    item.setOnclick(mi.getOnclick());
                }
                
                item.setIcon(p.getMenu().getCodigo());
                item.setId(mi.getIndice());
                menuItem.add(item);
            }
        }
        
        // Associar menu item com submenu.    
        for(DefaultSubMenu sb : submenu) { 
            DefaultSubMenu submenuAux = sb;
            
            for(DefaultMenuItem mi : menuItem) {
                if(sb.getIcon().equals(mi.getId())) {
                    mi.setIcon(null);
                    submenuAux.addElement(mi);
                }
            }
            menuItemAssSubmenu.add(submenuAux);
        }
        
        // Associar submenu com menu pai.
        for(DefaultSubMenu mp : menuPai) { 
            DefaultSubMenu menuPaiAux = mp;
            
            for(DefaultSubMenu sb : menuItemAssSubmenu) {
                if(mp.getIcon().equals(sb.getId())) {
                    sb.setIcon(null);
                    menuPaiAux.addElement(sb);
                }
            }
            submenuAssSubMenuPai.add(menuPaiAux);
        }
        
        // Associar menu item com menu pai.    
        for(DefaultSubMenu sa : submenuAssSubMenuPai) { 
            DefaultSubMenu menuPaiAux = sa;
            
            for(DefaultMenuItem mi : menuItem) {
                if(sa.getIcon().equals(mi.getId())) {
                    mi.setIcon(null);
                    menuPaiAux.addElement(mi);
                }
            }
            menuPaiAux.setIcon(null);
            menusAssociados.add(menuPaiAux);
        }
                
        // Preencher menu model.
        for(DefaultSubMenu menuAss : menusAssociados) {
            menuModel.addElement(menuAss);
        }
        
        // Gerar menu fale conosco.        
        DefaultMenuItem item2 = new DefaultMenuItem();
        item2.setValue("Fale Conosco");
        //item2.setUrl("/pages/comum/contato.faces");
        item2.setUrl(sistema.getUrl().replace("?faces-redirect=true", ""));
        menuModel.addElement(item2);
        
        // Gerar menu sistemas.
        DefaultMenuItem item3 = new DefaultMenuItem();
        item3.setValue("Sistemas");
        item3.setUrl("/pages/comum/selecaoSistema.faces");
        menuModel.addElement(item3);
        
        // Gerar menu sair.
        DefaultMenuItem item4 = new DefaultMenuItem();
        item4.setValue("Sair");
        item4.setCommand("#{MBUsuarios.logout()}");
        menuModel.addElement(item4);
    }


    public void getCodUser(ActionEvent e) {
        editausuario = new UsuarioBean();
        this.editausuario = (UsuarioBean) id_tupla.getRowData();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
            .put("obj_cod_user", editausuario);
    }

    public void setarCodusuario() {
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
            .put("obj_cod_user", editausuario);
    }

    public void idleListener() {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Your session is closed", 
            "You have been idle for at least 5 seconds"));
    }

    public void activeListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_WARN, "Welcome Back", "That's a long coffee break!"));
    }

    public void verificaUserCadastrado(FacesContext context, UIComponent toValidate, 
        Object value) throws ProjetoException {
        
        IUsuarioDAO icdao = new UsuarioDAO();
        String cpf = (String) value;
        String isExist = icdao.verificaUserCadastrado(cpf);
        if (isExist == "S") {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage(
                    " Usuario ja Cadastrado para este CPF!");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }

    public void verificaLoginCadastrado(FacesContext context, UIComponent toValidate, 
        Object value) throws ProjetoException {
        
        IUsuarioDAO icdao = new UsuarioDAO();
        String login = (String) value;

        String isExist = icdao.verificaLoginCadastrado(login);

        if (isExist == "S") {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage(" Login já em uso");
            context.addMessage(toValidate.getClientId(context), message);

        }
    }

    public void validateSenha(FacesContext context, UIComponent toValidate, Object value) {

        String confirmaSenha = (String) value;
        String senha = (String) this.senhaInput.getLocalValue();

        if (senha != null) {
            if (!confirmaSenha.equals(senha)) {
                ((UIInput) toValidate).setValid(false);

                FacesMessage message = new FacesMessage(
                        " As senhas digitadas diferem!");
                context.addMessage(toValidate.getClientId(context), message);
            }
        }
    }

    @SuppressWarnings("static-access")
    public String ultimoDiaMes(Integer Ano, Integer Mes) {
        Calendar cal = new GregorianCalendar(Ano, Mes - 1, 1);
        return toString().valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    public String acess() throws ProjetoException, IOException {

        String url = "";
        UsuarioDAO udao = new UsuarioDAO();
        user = null;

        user = udao.login(usuario);

        if(user == null) {
        		FacesContext.getCurrentInstance().getExternalContext().redirect("login.faces?erro='erro'");
        } else {

            

            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("obj_usuario", user);

            nomeSetor = DescSetor;
            permissaoInteressado = permissaoProcessos;

            
            
            // ACL =============================================================
                        
            List<Sistema> sistemas = udao.carregarSistemasUsuario(user);
            
            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("perms_usuario_sis", sistemas);
            
            List<Permissoes> permissoes = udao.carregarPermissoes(user);
            
            sistemaLogado.setDescricao("Sem Sistema");
            sistemaLogado.setSigla("Sem Sistema");
            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("sistema_logado", sistemaLogado);
            
            for(Sistema s : sistemas) {
                Menu m = new Menu();
                m.setDescricao("Principal");
                m.setUrl(s.getUrl());
                m.setTipo("injetado");
                Permissoes perms = new Permissoes();
                perms.setMenu(m);
                permissoes.add(perms);
            }
            
            for(Sistema s : sistemas) {
                Menu m = new Menu();
                m.setDescricao("Fale Conosco");
                m.setUrl(s.getUrl());
                m.setTipo("injetado");
                Permissoes perms = new Permissoes();
                perms.setMenu(m);
                permissoes.add(perms);
            }
            
            /*Permissoes perms = new Permissoes();
            Menu m2 = new Menu();
            m2.setDescricao("Fale Conosco");
            m2.setUrl("/pages/comum/contato.faces");
            m2.setTipo("injetado");
            perms.setMenu(m2);
            permissoes.add(perms);*/
            
            Permissoes perms2 = new Permissoes();            
            Menu m3 = new Menu();
            m3.setDescricao("Primeiro Acesso");
            m3.setUrl("/pages/comum/primeiroAcesso.faces");
            m3.setTipo("injetado");
            perms2.setMenu(m3);
            permissoes.add(perms2);
            
            FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("perms_usuario", permissoes);
            
            HttpSession session = SessionUtil.getSession();
            session.setAttribute("User", user.getLogin());
                        
            recoverDataFromSession();
            
            /*if(usuarioLogado.getIdPerfil() == 1) {
                url = "/pages/comum/selecaoSistema.faces?faces-redirect=true";
            } else {   
                for(Sistema sis : sistemas) {
                    if(sis.getId() == 1) {
                        sistemaLogado = sis;
                    } else {
                        
                    }
                }
                url = "/pages/clin/principal.faces?faces-redirect=true";
            }*/
            url = "/pages/comum/home.faces?faces-redirect=true";
        }
        return url;
    }

    public String pegandoNome() {

        UsuarioBean user_session = (UsuarioBean) FacesContext
            .getCurrentInstance().getExternalContext().getSessionMap()
            .get("obj_usuario");
        return user_session.getNome();
    }


    public String logout() {
        SessionUtil.getSession().invalidate();
        return "/pages/comum/login.faces?faces-redirect=true";
    }

    public List getListaUsuarioSecretaria() throws ProjetoException {
        if (listaUserSecretaria == null) {
            IUsuarioDAO icdao = new UsuarioDAO();
            listaUserSecretaria = icdao.buscaUsuariosSecretaria();
        }
        return listaUserSecretaria;
    }

    public void alterSenha() throws ProjetoException {

        IUsuarioDAO icdao = new UsuarioDAO();
        icdao.updateEditSenha(editausuario);
    }

    public void alterUsuario() throws ProjetoException {

        IUsuarioDAO icdao = new UsuarioDAO();
        boolean resultado = false;
        resultado = icdao.updateEditUsuario(editausuario);
        if (resultado == true) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Usuário alterado com sucesso"));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha na alteração dos dados do usuário!"));
        }
    }

    public void record() throws ProjetoException {

        IUsuarioDAO icdao = new UsuarioDAO();
        icdao.newUser(novousuario);
        novousuario = new UsuarioBean();
    }

    public void desativarusuario() throws ProjetoException {

        IUsuarioDAO icdao = new UsuarioDAO();
        icdao.desativarUser();
        listaUserSecretaria = null;
    }

    public void ativarusuario() throws ProjetoException {
        boolean pode_ativar = false;
        IUsuarioDAO icdao = new UsuarioDAO();

        // pode_ativar = icdao.existeUsuarioAtivo(editausuario.getCpf());
        if (pode_ativar) {
            icdao.ativarUser();
            listaUserSecretaria = null;
        } else {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(
                "Ativação não permitida! Usuário já possui cadastro ativo!"));
        }
    }

    public String alteraSenha() throws ProjetoException {

        IUsuarioDAO icdao = new UsuarioDAO();

        String alteracao = icdao.alteraSenha(editausuario);

        if (alteracao == "success") {

            UsuarioBean user_session = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_usuario");
            HttpSession session = SessionUtil.getSession();
            session.setAttribute("User", user_session.getLogin());
            return "main.faces?faces-redirect=true";
        } else {

            return "login_failure";
        }
    }

    public void verificaUltimoAdm(FacesContext context, UIComponent toValidate,
            Object value) throws ProjetoException {
        IUsuarioDAO icdao = new UsuarioDAO();
        String alteracao = String.valueOf(value);

        Integer I = icdao.verificaUltimoAdm(editausuario);

        if ((I == 1)) {
            if (alteracao.equals("false")) {
                ((UIInput) toValidate).setValid(false);
                FacesMessage message = new FacesMessage(
                        " Operacao nao permitida! ultimo Administrador do Sistema");
                context.addMessage(toValidate.getClientId(context), message);
            }
        }
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public UIData getId_tupla() {
        return id_tupla;
    }

    public void setId_tupla(UIData id_tupla) {
        this.id_tupla = id_tupla;
    }

    public UsuarioBean getUser() {
        return user;
    }

    public void setUser(UsuarioBean user) {
        this.user = user;
    }

    public UIInput getSenhaInput() {
        return senhaInput;
    }

    public static String getPermissaoProcessos() {
        return permissaoProcessos;
    }

    public static void setPermissaoProcessos(String permissaoProcessos) {
        UsuarioController.permissaoProcessos = permissaoProcessos;
    }

    public void setSenhaInput(UIInput senhaInput) {
        this.senhaInput = senhaInput;
    }

    public Object getNomeUserLogado() {
        return nomeUserLogado;
    }

    public void setNomeUserLogado(Object nomeUserLogado) {
        this.nomeUserLogado = nomeUserLogado;
    }
    
    public Object getMenuAdm() {
        return menuAdm;
    }

    public void setMenuAdm(Object menuAdm) {
        this.menuAdm = menuAdm;
    }

    public Object getPermissaoInteressado() {
        return permissaoInteressado;
    }

    public void setPermissaoInteressado(Object permissaoInteressado) {
        this.permissaoInteressado = permissaoInteressado;
    }

    public UsuarioBean getFuncionario() {
        return usuario;
    }

    public UsuarioBean getNovoUsuario() {
        return novousuario;
    }

    public UsuarioBean getEditausuario() {
        return editausuario;
    }

    public void setEditausuario(UsuarioBean editausuario) {
        this.editausuario = editausuario;
    }

    public Object getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(Object nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public Object getObjeto1() {
        return objeto1;
    }

    public void setObjeto1(Object objeto1) {
        this.objeto1 = objeto1;
    }

    public String getDescSetor() {
        return DescSetor;
    }

    public void setDescSetor(String descSetor) {
        DescSetor = descSetor;
    }

    public Object getNomeSecretaria() {
        return nomeSecretaria;
    }

    public void setNomeSecretaria(Object nomeSecretaria) {
        this.nomeSecretaria = nomeSecretaria;
    }

    public String getSessaoExpirada() {
        sessaoExpirada = (String) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("sessao_expirada");
        return sessaoExpirada;
    }

    public void setSessaoExpirada(String sessaoExpirada) {
        this.sessaoExpirada = sessaoExpirada;
    }

    public UsuarioBean getNovousuario() {
        return novousuario;
    }

    public void setNovousuario(UsuarioBean novousuario) {
        this.novousuario = novousuario;
    }

    public List getListaUserSecretaria() {
        return listaUserSecretaria;
    }

    public void setListaUserSecretaria(List listaUserSecretaria) {
        this.listaUserSecretaria = listaUserSecretaria;
    }

    public UsuarioBean getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioBean usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Sistema getSistemaLogado() {
        return sistemaLogado;
    }

    public void setSistemaLogado(Sistema sistemaLogado) {
        this.sistemaLogado = sistemaLogado;
    }

    public List<Sistema> getSistemasUsuarioLogado() {
        return sistemasUsuarioLogado;
    }

    public void setSistemasUsuarioLogado(List<Sistema> sistemasUsuarioLogado) {
        this.sistemasUsuarioLogado = sistemasUsuarioLogado;
    }

    public List<Permissoes> getPermsUsuarioLogado() {
        return permsUsuarioLogado;
    }

    public void setPermsUsuarioLogado(List<Permissoes> permsUsuarioLogado) {
        this.permsUsuarioLogado = permsUsuarioLogado;
    }

    public List<Menu> getListaMenus() {
        return listaMenus;
    }

    public void setListaMenus(List<Menu> listaMenus) {
        this.listaMenus = listaMenus;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }
}