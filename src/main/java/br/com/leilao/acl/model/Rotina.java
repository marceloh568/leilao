package br.com.leilao.acl.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marcelo Cunha
 * @since 17/03/2015
 */
public class Rotina implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String descricao;
    private String tipo;
    private Integer idSistema;
    
    private String descSistema;
    
    private List<Menu> listaMenusAss;
    private List<Funcao> listaFuncoesAss;

    public Rotina() {
        
    }

    public Rotina(Long id, String descricao, String tipo, Integer idSistema, String descSistema) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.idSistema = idSistema;
        this.descSistema = descSistema;
    }

    public Rotina(String descricao, String tipo, List<Menu> listaMenusAss) {
        this.descricao = descricao;
        this.tipo = tipo;
        this.listaMenusAss = listaMenusAss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getDescSistema() {
        return descSistema;
    }

    public void setDescSistema(String descSistema) {
        this.descSistema = descSistema;
    }

    public List<Menu> getListaMenusAss() {
        return listaMenusAss;
    }

    public void setListaMenusAss(List<Menu> listaMenusAss) {
        this.listaMenusAss = listaMenusAss;
    }

    public List<Funcao> getListaFuncoesAss() {
        return listaFuncoesAss;
    }

    public void setListaFuncoesAss(List<Funcao> listaFuncoesAss) {
        this.listaFuncoesAss = listaFuncoesAss;
    }
}