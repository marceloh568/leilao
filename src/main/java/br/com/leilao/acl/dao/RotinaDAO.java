package br.com.leilao.acl.dao;

import br.com.leilao.acl.model.Rotina;
import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.ConnectFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author Marcelo Cunha
 * @since 17/03/2015
 */
public class RotinaDAO implements Serializable {

    private Connection conexao = null;

    public boolean cadastrarRotina(Rotina rotina) throws ProjetoException {
        
        String sql = "insert into acl.rotina (descricao, tipo, id_sistema) "
            + "values (?, ?, ?)";
        
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, "ROT-" + rotina.getDescricao().toUpperCase());
            stmt.setString(2, rotina.getTipo().toUpperCase());
            if(rotina.getIdSistema() == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, rotina.getIdSistema());
            }
            stmt.execute();
            
            conexao.commit();

            return true;
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
    }
    
    public boolean alterarRotina(Rotina rotina) throws ProjetoException {
        
        String sql = "update acl.rotina set descricao = ?, tipo = ?, id_sistema = ? where id = ?";

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, "ROT-" + rotina.getDescricao().toUpperCase());
            stmt.setString(2, rotina.getTipo().toUpperCase());
            if(rotina.getIdSistema() == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, rotina.getIdSistema());
            }
            stmt.setLong(4, rotina.getId());
            stmt.executeUpdate();
            
            conexao.commit();
            
            return true;
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
    }
    
    public boolean excluirRotina(Rotina rotina) throws ProjetoException {
        
        String sql = "delete from acl.rotina where id = ?";
        
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, rotina.getId());
            stmt.execute();
            
            conexao.commit();
            
            return true;
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
    }
    
    public ArrayList<Rotina> buscarRotinaDescSis(String valor, Integer idSistema) throws ProjetoException {
        
        String sql = "select ro.id, ro.descricao, ro.tipo, ro.id_sistema, si.descricao "
            + "as sis_desc from acl.rotina ro join acl.sistema si on si.id = ro.id_sistema "
            + "where ro.descricao like ? and ro.id_sistema = ? order by ro.descricao";
        
        if(idSistema == 0) {
            sql = "select id, descricao, tipo, id_sistema, '' as sis_desc from acl.rotina "
                + "where descricao like ? and id_sistema is null order by descricao";
        }

        ArrayList<Rotina> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, "%" + valor.toUpperCase() + "%");            
            if(idSistema > 0) {
                stmt.setInt(2, idSistema);
            }
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Rotina r = new Rotina();
                r.setId(rs.getLong("id"));
                r.setDescricao(rs.getString("descricao"));
                r.setTipo(rs.getString("tipo"));
                r.setIdSistema(rs.getInt("id_sistema"));
                
                if(r.getIdSistema() == null || r.getIdSistema() == 0) {
                    r.setDescSistema("Nenhum");
                } else {
                    r.setDescSistema(rs.getString("sis_desc"));
                }
                lista.add(r);
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

    public ArrayList<Rotina> listarRotinas() throws ProjetoException {

        String sql = "select ro.id, ro.descricao, ro.tipo, ro.id_sistema, si.descricao "
            + "as sis_desc from acl.rotina ro join acl.sistema si on si.id = ro.id_sistema "
            + "union select id, descricao, tipo, id_sistema, '' as sis_desc from acl.rotina "
            + "where id_sistema is null order by descricao";

        ArrayList<Rotina> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Rotina r = new Rotina();
                r.setId(rs.getLong("id"));
                r.setDescricao(rs.getString("descricao"));
                r.setTipo(rs.getString("tipo"));
                r.setIdSistema(rs.getInt("id_sistema"));
                
                if(r.getIdSistema() == null || r.getIdSistema() == 0) {
                    r.setDescSistema("Comum");
                } else {
                    r.setDescSistema(rs.getString("sis_desc"));
                }
                lista.add(r);
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
    
    public ArrayList<Rotina> listarRotinasSemPerfil(Integer id) throws ProjetoException {

        String sql = "select * from acl.rotina "
            + "where id not in (select r.id from acl.rotina r "
            + "join acl.perm_geral pg on r.id = pg.id_rotina "
            + "join acl.perm_perfil pf on pf.id_permissao = pg.id_permissao "
            + "join acl.perfil p on p.id = pf.id_perfil where p.id = ?) "
            + "order by descricao";

        ArrayList<Rotina> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stm = conexao.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Rotina r = new Rotina();
                r.setId(rs.getLong("id"));
                r.setDescricao(rs.getString("descricao"));
                r.setTipo(rs.getString("tipo"));
                r.setIdSistema(rs.getInt("id_sistema"));
                lista.add(r);
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