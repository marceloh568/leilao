package br.com.leilao.acl.dao;

import br.com.leilao.acl.model.Funcao;
import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ConnectFactory;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo Cunha
 * @since 17/03/2015
 */
public class FuncaoDAO implements Serializable {

    private Connection conexao;

    public Boolean cadastrarFuncao(Funcao funcao) throws ProjetoException {
        
        boolean cadastrou = false;
        
        try {
            conexao = ConnectFactory.getConnection();
            CallableStatement cs = conexao.prepareCall("{ ? = call acl.gravarfuncao(?, ?, ?, ?, ?) }");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, funcao.getDescricao());
            cs.setString(3, funcao.getCodigo());
            cs.setInt(4, funcao.getIdRotina());
            cs.setInt(5, funcao.getIdSistema());
            cs.setBoolean(6, funcao.isAtiva());
            cs.execute();
            
            Integer idRetornoFunc = cs.getInt(1);
            
            String codAux = "FC-" + idRetornoFunc;
            
            String sql = "update acl.funcao set codigo = ? where id = ?";
            
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, codAux);
            stmt.setInt(2, idRetornoFunc);
            stmt.executeUpdate();
            
            cs = conexao.prepareCall("{ ? = call acl.gravarpermissao(?) }");
            cs.registerOutParameter(1, Types.INTEGER);            
            cs.setString(2, funcao.getDescricao());
            cs.execute();
                       
            Integer idRetornoPerm = cs.getInt(1);
            
            sql = "insert into acl.perm_geral (id_funcao, id_permissao) values (?, ?)";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idRetornoFunc);
            stmt.setLong(2, idRetornoPerm);
            stmt.execute();
            
            conexao.commit();

            cadastrou = true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return cadastrou;
    }

    public Boolean alterarFuncao(Funcao funcao) throws ProjetoException {
        
        String sql = "update acl.funcao set descricao = ?, id_rotina = ?, "
            + "id_sistema = ?, ativa = ? where id = ?";
        
        boolean alterou = false;
        
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcao.getDescricao());
            stmt.setInt(2, funcao.getIdRotina());
            stmt.setInt(3, funcao.getIdSistema());
            stmt.setBoolean(4, funcao.isAtiva());
            stmt.setLong(5, funcao.getId());
            stmt.executeUpdate();
                
            conexao.commit();

            alterou = true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return alterou;
    }
    
    public boolean excluirFuncao(Funcao funcao) throws ProjetoException {
        
        String sql = "delete from acl.funcao where id = ?";
        
        boolean excluiu = false;
        
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, funcao.getId());
            stmt.execute();
            
            conexao.commit();
            
            excluiu = true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return excluiu;
    }
    
    public List<Funcao> listarFuncoes() throws ProjetoException {

        String sql = "select fun.id, fun.descricao, fun.codigo, fun.id_rotina, "
            + "fun.id_sistema, fun.ativa, rot.id as rot_id, rot.descricao as rot_desc, "
            + "sis.descricao as sis_desc from acl.funcao fun join acl.rotina rot "
            + "on rot.id = fun.id_rotina join acl.sistema sis on sis.id = fun.id_sistema "
            + "order by fun.ativa desc, fun.descricao, sis.descricao";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setCodigo(rs.getString("codigo"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setAtiva(rs.getBoolean("ativa"));
                
                f.setDescRotina(rs.getString("rot_desc"));
                f.setIdSistema(rs.getInt("id_sistema"));
                f.setDescSistema(rs.getString("sis_desc"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncoesComSisRot() throws ProjetoException {

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis, ro.descricao as desc_rotina "
            + "from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "join acl.rotina ro on ro.id = fu.id_rotina";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncoesSourceEdit(Integer idPerfil) throws ProjetoException {

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, "
            + "si.id as id_sis, si.descricao as desc_sis, si.sigla as sigla_sis, "
            + "ro.descricao as desc_rotina from acl.funcao fu "
            + "join acl.perm_geral pg on pg.id_funcao = fu.id "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "join acl.rotina ro on ro.id = fu.id_rotina "
            + "where fu.id not in (select fu.id from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.perm_perfil pp on pp.id_permissao = pm.id "
            + "join acl.perfil pf on pf.id = pp.id_perfil "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "where pp.id_perfil = ?)";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncoesTargetEdit(Integer idPerfil) throws ProjetoException {

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, "
            + "si.id as id_sis, si.descricao as desc_sis, si.sigla as sigla_sis, "
            + "ro.descricao as desc_rotina from acl.funcao fu join acl.perm_geral pg "
            + "on pg.id_funcao = fu.id join acl.permissao pm on pm.id = pg.id_permissao "
            + "join acl.perm_perfil pp on pp.id_permissao = pm.id join acl.perfil pf on "
            + "pf.id = pp.id_perfil join acl.sistema si on si.id = fu.id_sistema join "
            + "acl.rotina ro on ro.id = fu.id_rotina where pf.id = ?";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncaoItemSourcerUser(Integer idPerfil) throws ProjetoException {

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis, ro.descricao as desc_rotina "
            + "from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "join acl.rotina ro on ro.id = fu.id_rotina "
            + "where fu.id not in ("
            + "	select fu.id from acl.perm_perfil pp "
            + "	join acl.perfil pf on pf.id = pp.id_perfil "
            + "	join acl.permissao pm on pm.id = pp.id_permissao "
            + "	join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "	join acl.funcao fu on fu.id = pg.id_funcao "
            + "	join acl.sistema si on si.id = fu.id_sistema where pf.id = ?"
            + ") order by fu.descricao;";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncaosPerfil(Integer idPerfil) throws ProjetoException {

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis, ro.descricao as desc_rotina "
            + "from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.perm_perfil pp on pp.id_permissao = pg.id_permissao "
            + "join acl.perfil pf on pf.id = pp.id_perfil "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "join acl.rotina ro on ro.id = fu.id_rotina where pf.id = ? "
            + "order by fu.descricao;";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncaoItemSourcerUserEdit(Integer idPerfil, Integer idUsuario) throws ProjetoException {
        
        

        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, "
            + "si.id as id_sis, si.descricao as desc_sis, si.sigla as sigla_sis, "
            + "ro.descricao as desc_rotina from acl.funcao fu "
            + "join acl.perm_geral pg on pg.id_funcao = fu.id "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "join acl.rotina ro on ro.id = fu.id_rotina "
            + "where fu.id not in ( "
            + "select fu.id from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.perm_perfil pp on pp.id_permissao = pm.id "
            + "join acl.perfil pf on pf.id = pp.id_perfil "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "where pp.id_perfil = ? "
            + "union "
            + "select fu.id from acl.perm_usuario pu "
            + "join acl.permissao pm on pm.id = pu.id_permissao "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "where pu.id_usuario = ? "
            + ") order by fu.descricao;";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
    
    public List<Funcao> listarFuncaoItemTargetUserEdit(Integer idUsuario) throws ProjetoException {

        
        
        String sql = "select fu.id, fu.descricao, fu.codigo, fu.id_rotina, fu.ativa, "
            + "si.id as id_sis, si.descricao as desc_sis, si.sigla as sigla_sis, "
            + "ro.descricao as desc_rotina from acl.perm_usuario pu "
            + "join acl.permissao pm on pm.id = pu.id_permissao "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.funcao fu on fu.id = pg.id_funcao "
            + "join acl.rotina ro on ro.id = fu.id_rotina "
            + "join acl.sistema si on si.id = fu.id_sistema "
            + "where pu.id_usuario = ? order by fu.descricao;";

        List<Funcao> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcao f = new Funcao();
                f.setId(rs.getLong("id"));
                f.setDescricao(rs.getString("descricao"));
                f.setAtiva(rs.getBoolean("ativa"));
                f.setIdRotina(rs.getInt("id_rotina"));
                f.setCodigo(rs.getString("codigo"));
                f.setDescRotina(rs.getString("desc_rotina"));
                
                f.setIdSistema(rs.getInt("id_sis"));
                f.setDescSistema(rs.getString("desc_sis"));
                f.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return lista;
    }
}