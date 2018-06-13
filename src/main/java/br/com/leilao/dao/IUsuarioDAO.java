package br.com.leilao.dao;

import java.util.*;

import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.UsuarioBean;





public interface IUsuarioDAO {

	public abstract UsuarioBean login(UsuarioBean e) throws ProjetoException;
	public abstract UsuarioBean consultaUsuario() throws ProjetoException;
	public  abstract List buscaUsuariosSecretaria() throws ProjetoException;
	public abstract UsuarioBean newUser(UsuarioBean b) throws ProjetoException;	
	public void desativarUser() throws ProjetoException;
	public void ativarUser() throws ProjetoException;
	public boolean updateEditSenha(UsuarioBean U) throws ProjetoException;
	public boolean updateEditUsuario(UsuarioBean U) throws ProjetoException;
	public String verificaUserCadastrado(String s)	throws ProjetoException ;
	public String alteraSenha(UsuarioBean u) throws ProjetoException;
	public String verificaLoginCadastrado(String login)	throws ProjetoException;	
	public Integer verificaUltimoAdm(UsuarioBean U) throws ProjetoException;
	public List<SelectItem> consultaAnoAberturaProc() throws ProjetoException ;
	public boolean existeUsuarioAtivo(String cpf ) throws ProjetoException ;
	
//	
}
