package br.com.leilao.acl.control;

import br.com.leilao.acl.dao.RotinaDAO;
import br.com.leilao.acl.model.Rotina;
import br.com.leilao.comum.exception.ProjetoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Marcelo Cunha
 * @since 17/03/2015
 */
@ManagedBean
@ViewScoped
public class RotinaMB implements Serializable {

    private Rotina rotina;
    private List<Rotina> listaRotinas;
    private String sistemaSelecionado;
    
    private String valorBusca;
    private String sisBusca;

    public RotinaMB() {
        rotina = new Rotina();
        listaRotinas = new ArrayList<>();
        listaRotinas = null;
        sistemaSelecionado = "0";
        valorBusca = "";
        sisBusca = "0";
    }

    public void cadastrarRotina() throws ProjetoException {

        if(Integer.parseInt(sistemaSelecionado) != 0) {
            rotina.setIdSistema(Integer.parseInt(sistemaSelecionado));
        } else {
            rotina.setIdSistema(null);
        }
        
        RotinaDAO rdao = new RotinaDAO();
        boolean cadastrou = rdao.cadastrarRotina(rotina);

        if(cadastrou == true) {
            
            listaRotinas = null;
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Rotina cadastrada com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            RequestContext.getCurrentInstance().execute("PF('dlgCadRotina').hide();");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Ocorreu um erro durante o cadastro!", "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void alterarRotina() throws ProjetoException {
          
        if(Integer.parseInt(sistemaSelecionado) != 0) {
            rotina.setIdSistema(Integer.parseInt(sistemaSelecionado));
        } else {
            rotina.setIdSistema(null);
        }
        
        RotinaDAO rdao = new RotinaDAO();
        boolean alterou = rdao.alterarRotina(rotina);

        if(alterou == true) {
            
            listaRotinas = null;
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Rotina alterada com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            RequestContext.getCurrentInstance().execute("PF('dlgAltRotina').hide();");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Ocorreu um erro durante a alteração!", "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }       
    }

    public void excluirRotina() throws ProjetoException {
        
        RotinaDAO rdao = new RotinaDAO();       
        boolean excluiu = rdao.excluirRotina(rotina);

        if(excluiu == true) {
            
            listaRotinas = null;
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Rotina excluida com sucesso!", "Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            RequestContext.getCurrentInstance().execute("PF('dlgExcRotina').hide();");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Ocorreu um erro durante a exclusão!", "Erro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }       
    }
    
    public void buscarRotina() throws ProjetoException {
        RotinaDAO rdao = new RotinaDAO();
        List<Rotina> listaAux = rdao.buscarRotinaDescSis(valorBusca, Integer.parseInt(sisBusca));
        
        if(listaAux != null && listaAux.size() > 0) {
            listaRotinas = null;
            listaRotinas = listaAux;
        } else {
            listaRotinas = null;
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Rotina não encontrada!", "Aviso");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
    }
    
    public void recDescricao() {
        String aux = rotina.getDescricao().replaceFirst("ROT-", "");
        rotina.setDescricao(aux);
    }

    public void limparDados() {
        rotina = new Rotina();
        sisBusca = "0";
        sistemaSelecionado = "0";
    }
    
    public void limparBusca() {
        valorBusca = "";
        sisBusca = "0";
        listaRotinas = null;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    public List<Rotina> getListaRotinas() throws ProjetoException {
        if (listaRotinas == null) {
            RotinaDAO rdao = new RotinaDAO();
            listaRotinas = rdao.listarRotinas();
        }
        return listaRotinas;
    }

    public void setListaRotinas(List<Rotina> listaRotinas) {
        this.listaRotinas = listaRotinas;
    }

    public String getSistemaSelecionado() {
        return sistemaSelecionado;
    }

    public void setSistemaSelecionado(String sistemaSelecionado) {
        this.sistemaSelecionado = sistemaSelecionado;
    }

    public String getValorBusca() {
        return valorBusca;
    }

    public void setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
    }

    public String getSisBusca() {
        return sisBusca;
    }

    public void setSisBusca(String sisBusca) {
        this.sisBusca = sisBusca;
    }
}