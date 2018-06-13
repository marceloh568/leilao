package br.com.leilao.acl.dao;

import br.com.leilao.acl.model.Menu;
import br.com.leilao.acl.model.Sistema;
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
public class MenuDAO implements Serializable {
    
    private Connection conexao;
    
    public Boolean cadastrar(Menu menu) throws ProjetoException {       
        
        boolean cadastrou = false;
        
        List<Integer> listaId = menu.getListaSistemas();
        
        try {
            conexao = ConnectFactory.getConnection();
            CallableStatement cs = conexao.prepareCall("{ ? = call acl.gravarmenu(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            cs.registerOutParameter(1, Types.INTEGER);            
            cs.setString(2, menu.getDescricao());
            cs.setString(3, menu.getDescPagina());
            cs.setString(4, menu.getDiretorio());
            cs.setString(5, menu.getExtensao());
            cs.setString(6, menu.getCodigo());
            cs.setString(7, menu.getIndice());
            cs.setString(8, menu.getTipo());
            cs.setInt(9, menu.getIdRotina());
            cs.setBoolean(10, menu.isAtivo());
            cs.setString(11, menu.getAction());
            cs.setString(12, menu.getOnclick());
            cs.execute();
            
            Integer idRetornoMenu = cs.getInt(1);
            
            String codAux = "MN-" + idRetornoMenu;

            String sql = "update acl.menu set codigo = ? where id = ?";
            
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, codAux);
            stmt.setInt(2, idRetornoMenu);
            stmt.executeUpdate();            
            
            cs = conexao.prepareCall("{ ? = call acl.gravarpermissao(?) }");
            cs.registerOutParameter(1, Types.INTEGER);            
            cs.setString(2, menu.getDescricao());
            cs.execute();
                       
            Integer idRetornoPerm = cs.getInt(1);
            
            sql = "insert into acl.menu_sistema (id_menu, id_sistema) values (?, ?)";
          
            stmt = conexao.prepareStatement(sql);
            for(Integer idSis : listaId) {
                stmt.setLong(1, idRetornoMenu);
                stmt.setLong(2, idSis);
                stmt.execute();
            }
            
            sql = "insert into acl.perm_geral (id_menu, id_permissao) values (?, ?)";
        
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idRetornoMenu);
            stmt.setInt(2, idRetornoPerm);
            stmt.execute();
   
            conexao.commit();
            
            cadastrou = true;
        } catch(SQLException ex) {
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
    
    public Boolean alterar(Menu menu) throws ProjetoException {       
        
        String sql = "update acl.menu set descricao = ?, desc_pagina = ?, diretorio = ?, "
            + "extensao = ?, codigo = ?, indice = ?, tipo = ?, id_rotina = ?, ativo = ?, "
            + "action_rel = ?, onclick_rel = ? where id = ?";
        
        boolean alterou = false;
        
        List<Integer> listaId = menu.getListaSistemas();
        
        try {            
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);           
            stmt.setString(1, menu.getDescricao());
            stmt.setString(2, menu.getDescPagina());
            stmt.setString(3, menu.getDiretorio());
            stmt.setString(4, menu.getExtensao());
            stmt.setString(5, menu.getCodigo());
            stmt.setString(6, menu.getIndice());
            stmt.setString(7, menu.getTipo());
            stmt.setInt(8, menu.getIdRotina());
            stmt.setBoolean(9, menu.isAtivo());
            stmt.setString(10, menu.getAction());
            stmt.setString(11, menu.getOnclick());
            stmt.setLong(12, menu.getId());
            stmt.executeUpdate();
            
            if(!menu.getListaSistemas().isEmpty()) {
                sql = "delete from acl.menu_sistema where id_menu = ?";

                stmt = conexao.prepareStatement(sql);
                stmt.setLong(1, menu.getId());
                stmt.execute();

                sql = "insert into acl.menu_sistema (id_menu, id_sistema) values (?, ?)";

                stmt = conexao.prepareStatement(sql);
                for(Integer idSis : listaId) {
                    stmt.setLong(1, menu.getId());
                    stmt.setInt(2, idSis);
                    stmt.execute();
                }      
                conexao.commit();
                
                alterou =  true;
            }    
        } catch(SQLException ex) {
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
    
    public boolean excluirMenu(Menu menu) throws ProjetoException {
        
        String sql = "delete from acl.menu where id = ?";
        
        boolean excluiu = false;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, menu.getId());
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
    
    public List<Menu> buscarMenuDesc(String valor) throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, me.id_rotina, "
            + "me.action_rel, me.onclick_rel, ro.descricao as rot_desc from "
            + "acl.menu me join acl.rotina ro on ro.id = me.id_rotina where "
            + "upper(me.descricao) like ? order by me.ativo desc, me.descricao, me.tipo";
             
        List<Menu> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, "%" + valor.toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {            
                Menu m = new Menu();               
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }
                m.setIdRotina(rs.getInt("id_rotina"));               
                m.setIndiceAux(rs.getString("descricao"));
                
                if(rs.getString("action_rel") != null) {
                    m.setAction(rs.getString("action_rel"));
                } else {
                    m.setAction(null);
                }
                
                if(rs.getString("onclick_rel") != null) {
                    m.setOnclick(rs.getString("onclick_rel"));
                } else {
                    m.setOnclick(null);
                }
                
                m.setDescRotina(rs.getString("rot_desc"));
                
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public List<Menu> listarMenuGeral() throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, me.id_rotina, "
            + "me.action_rel, me.onclick_rel, ro.descricao as rot_desc from "
            + "acl.menu me join acl.rotina ro on ro.id = me.id_rotina order by "
            + "me.ativo desc, me.descricao, me.tipo";
             
        List<Menu> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {            
                Menu m = new Menu();               
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }
                m.setIdRotina(rs.getInt("id_rotina"));               
                m.setIndiceAux(rs.getString("descricao"));
                
                if(rs.getString("action_rel") != null) {
                    m.setAction(rs.getString("action_rel"));
                } else {
                    m.setAction(null);
                }
                
                if(rs.getString("onclick_rel") != null) {
                    m.setOnclick(rs.getString("onclick_rel"));
                } else {
                    m.setOnclick(null);
                }
                
                m.setDescRotina(rs.getString("rot_desc"));
                
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenus() throws ProjetoException {

        String sql = "select * from acl.menu order by descricao";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setUrl(rs.getString("url"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public List<Menu> listarMenusPaiSubmenus() throws ProjetoException {

        String sql = "select * from acl.menu where tipo = 'menuPai' or tipo = 'submenu' "
            + "and ativo = true order by descricao, tipo";
             
        List<Menu> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }
                m.setIdRotina(rs.getInt("id_rotina"));               
                m.setIndiceAux(rs.getString("descricao"));
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public List<Menu> listarMenuItem() throws ProjetoException {

        String sql = "select * from acl.menu where tipo = 'menuItem' and ativo = true "
            + "order by descricao";
             
        List<Menu> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setUrl(rs.getString("url"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemComSis() throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.menu me on me.id = pg.id_menu "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where me.tipo = 'menuItem' or me.tipo = 'menuItemRel'";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemSourcerEdit(Integer idPerfil) throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.menu me "
            + "join acl.perm_geral pg on pg.id_menu = me.id "
            + "join acl.permissao pm on pm.id = pg.id_permissao "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema where me.id not in("
            + "select me.id from acl.perm_perfil pp "
            + "join acl.perfil pf on pf.id = pp.id_perfil "
            + "join acl.permissao pm on pm.id = pp.id_permissao "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.menu me on me.id = pg.id_menu "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') and pf.id = ?) "
            + "and (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') order by me.descricao";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemTargetEdit(Integer idPerfil) throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.menu me "
            + "join acl.perm_geral pg on pg.id_menu = me.id "
            + "join acl.permissao pm on pm.id = pg.id_permissao "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "join acl.perm_perfil pp on pp.id_permissao = pg.id_permissao "
            + "join acl.perfil pf on pf.id = pp.id_perfil "
            + "where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') "
            + "and pf.id = ? order by me.descricao;";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuPaiSubmenuComSis() throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, me.ativo, "
            + "me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.permissao pm "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.menu me on me.id = pg.id_menu "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where me.tipo = 'menuPai' or me.tipo = 'submenu'";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
     
    public ArrayList<Menu> listarMenusPorSistema(Integer id) throws ProjetoException {

        String sql = "select ms.id, " +
            "ms.id_menu, " +
            "ms.id_sistema, " +
            "m.descricao " +
            "from acl.menu_sistema ms join acl.menu m " +
            "on m.id = ms.id_menu " +
            "join acl.sistema s on s.id = ms.id_sistema " +
            "where s.id = ? order by m.descricao";
                     
        ArrayList<Menu> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu r = new Menu();
                r.setIdRecSistema(rs.getInt("id"));
                r.setDescricao(rs.getString("descricao"));
                r.setIdSistema(rs.getInt("id_sistema"));
                r.setId(rs.getLong("id_menu"));
                lista.add(r);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Sistema> listarSisAssNaoMenuSource(Long idMenu) throws ProjetoException {

        String sql = "select id, descricao from acl.sistema where id not in "
            + "(select si.id from acl.sistema si "
            + "join acl.menu_sistema ms on ms.id_sistema = si.id "
            + "join acl.menu me on me.id = ms.id_menu "
            + "where me.id = ?)";
             
        ArrayList<Sistema> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idMenu);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                lista.add(s);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Sistema> listarSisAssMenuTarget(Long idMenu) throws ProjetoException {

        String sql = "select si.id, si.descricao from acl.sistema si "
            + "join acl.menu_sistema ms on ms.id_sistema = si.id "
            + "join acl.menu me on me.id = ms.id_menu "
            + "where me.id = ?";
             
        ArrayList<Sistema> lista = new ArrayList();

        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, idMenu);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                lista.add(s);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemSourcerUser(Integer idPerfil) throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.menu me "
            + "join acl.perm_geral pg on pg.id_menu = me.id "
            + "join acl.permissao pm on pm.id = pg.id_permissao "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where me.id not in("
            + "	select me.id from acl.perm_perfil pp "
            + "	join acl.perfil pf on pf.id = pp.id_perfil "
            + "	join acl.permissao pm on pm.id = pp.id_permissao "
            + "	join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "	join acl.menu me on me.id = pg.id_menu "
            + "	join acl.menu_sistema ms on ms.id_menu = me.id "
            + "	join acl.sistema si on si.id = ms.id_sistema "
            + "	where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') and pf.id = ?"
            + ") and (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') order by me.descricao;";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                
                ////System.out.println("codigo: " + rs.getString("codigo"));
                ////System.out.println("indece: " + rs.getString("indice"));
                
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemSourcerEditUser(Integer idPerfil, Integer idUsuario) throws ProjetoException {
        
        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.menu me "
            + "join acl.perm_geral pg on pg.id_menu = me.id "
            + "join acl.permissao pm on pm.id = pg.id_permissao "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where me.id not in("
            + "	select me.id from acl.perm_perfil pp "
            + "	join acl.perfil pf on pf.id = pp.id_perfil "
            + "	join acl.permissao pm on pm.id = pp.id_permissao "
            + "	join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "	join acl.menu me on me.id = pg.id_menu "
            + "	join acl.menu_sistema ms on ms.id_menu = me.id "
            + "	join acl.sistema si on si.id = ms.id_sistema "
            + "	where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') and pf.id = ?"
            + "	union"
            + "	select me.id from acl.perm_usuario pu "
            + "	join acl.permissao pm on pm.id = pu.id_permissao "
            + "	join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "	join acl.menu me on me.id = pg.id_menu "
            + "	join acl.menu_sistema ms on ms.id_menu = me.id "
            + "	join acl.sistema si on si.id = ms.id_sistema "
            + "	where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') and pu.id_usuario = ?"
            + ") and (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') order by me.descricao;";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                
                ////System.out.println("codigo: " + rs.getString("codigo"));
                ////System.out.println("indece: " + rs.getString("indice"));
                
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenuItemTargetEditUser(Integer idUsuario) throws ProjetoException {
        
        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.perm_usuario pu "
            + "join acl.permissao pm on pm.id = pu.id_permissao "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.menu me on me.id = pg.id_menu "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where (me.tipo = 'menuItem' or me.tipo = 'menuItemRel') and pu.id_usuario = ?";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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
    
    public ArrayList<Menu> listarMenusPerfil(Integer idPerfil) throws ProjetoException {

        String sql = "select me.id, me.descricao, me.codigo, me.indice, me.tipo, "
            + "me.ativo, me.diretorio, me.desc_pagina, me.extensao, si.id as id_sis, "
            + "si.descricao as desc_sis, si.sigla as sigla_sis from acl.perm_perfil pp "
            + "join acl.permissao pm on pm.id = pp.id_permissao "
            + "join acl.perm_geral pg on pg.id_permissao = pm.id "
            + "join acl.menu me on me.id = pg.id_menu "
            + "join acl.menu_sistema ms on ms.id_menu = me.id "
            + "join acl.sistema si on si.id = ms.id_sistema "
            + "where pp.id_perfil = ? order by me.descricao";
             
        ArrayList<Menu> lista = new ArrayList();
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {            
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCodigo(rs.getString("codigo"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("ativo"));
                                
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                
                if(rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/" + m.getDescPagina() + m.getExtensao());
                }              
                m.setIndiceAux(rs.getString("codigo"));
                
                m.setIdSistema(rs.getInt("id_sis"));
                m.setDescSistema(rs.getString("desc_sis"));
                m.setSiglaSistema(rs.getString("sigla_sis").toUpperCase());
                lista.add(m);
            }
        } catch(SQLException ex) {
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