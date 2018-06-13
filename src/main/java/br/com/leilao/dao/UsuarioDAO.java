package br.com.leilao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.leilao.acl.model.Menu;
import br.com.leilao.acl.model.Permissoes;
import br.com.leilao.acl.model.Sistema;
import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.UsuarioBean;

public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public UsuarioBean login(UsuarioBean usuario) throws ProjetoException {

        String sql = " select u.codfunc, u.nome, u.cpf, "
                + "u.adm, pf.id as idperfil, pf.descricao as descperfil "
                + "  from leilao.func u "
                + "left join acl.perfil pf on pf.id = u.id_perfil "
                + "where (u.cpf = ?) and (u.senha = ?) and (u.ativo = 'S')";

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getCpf().replaceAll("[^0-9]", ""));
            ps.setString(2, usuario.getSenha());

            ResultSet rs = ps.executeQuery();
System.out.println("1");
            UsuarioBean us = null;

            while (rs.next()) {
            	System.out.println("2");
                us = new UsuarioBean();
                us.setCodfunc(rs.getInt("codfunc"));
                us.setNome(rs.getString("nome"));
                us.setCpf(rs.getString("cpf"));
                us.setAdm(rs.getBoolean("adm"));
                us.setIdPerfil(rs.getInt("idperfil"));
                us.setDescPerfil(rs.getString("descperfil"));

                return us;
            }
        } catch (Exception ex) {
            throw new ProjetoException(ex);
        } finally {
            try {
                conexao.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
            }
        }
        return null;
    }

    public void desativarUser() throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        try {
            String sql = "update prot2.usuarios set ativo=?"
                    + "where id_usuario=?";
            UsuarioBean coduser = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_cod_user");
            System.out.println("cod user para desativar" + coduser);
            ps = con.prepareStatement(sql);
            ps.setString(1, "N");
            // ps.setInt(2, coduser.getCodigo());
            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
        }
    }

    public void ativarUser() throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        try {
            String sql = "update prot2.usuarios set ativo=?"
                    + "where id_usuario=?";
            UsuarioBean coduser = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_cod_user");

            ps = con.prepareStatement(sql);
            ps.setString(1, "S");
            // ps.setInt(2, coduser.getCodigo());
            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
        }

    }

    public boolean updateEditSenha(UsuarioBean U) throws ProjetoException {

        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();

        try {

            String sql = "update prot2.usuarios set senha=?"
                    + "where id_usuario=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, U.getSenha().toUpperCase());
            // ps.setInt(2, U.getCodigo());
            ps.executeUpdate();
            con.commit();
            ps.close();
            return true;
        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }

    }

    public boolean existeUsuarioAtivo(String cpf) throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        boolean resultado = true;
        try {

            String sql = "select cpf from prot2.usuarios where cpf=? and ativo='S'";

            ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado = false;
            }
            System.out.println("resultado" + resultado);
            return resultado;
        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }

    }

    public boolean updateEditUsuario(UsuarioBean U) throws ProjetoException {

        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        boolean retorno = false;
        try {
            String sql = "update prot2.usuarios set  descusuario=?,email=?,administrador=?"
                    + " where id_usuario=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, U.getNome().toUpperCase());
            // ps.setString(2, U.getEmail().toUpperCase());
            // ps.setBoolean(3, U.isAdministrador());
            // ps.setInt(4, U.getCodigo());
            ps.executeUpdate();
            con.commit();
            ps.close();
            retorno = true;
            return retorno;

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }

    }

    public UsuarioBean newUser(UsuarioBean b) throws ProjetoException {

        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        UsuarioBean user_session = (UsuarioBean) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get("obj_usuario");
        Calendar cal = Calendar.getInstance();
        Timestamp dateandtime = new java.sql.Timestamp(cal.getTimeInMillis());

        try {
            String sql = "insert into prot2.usuarios (descusuario, codsecretaria, login, senha, datacriacao, ativo, administrador, email, log_user, cpf,primeiroacesso)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, b.getNome().toUpperCase().trim());
            // ps.setInt(2, user_session.getUser_secretaria());
            ps.setString(3, b.getLogin().toUpperCase().trim());
            ps.setString(4, b.getSenha().toUpperCase().trim());
            ps.setTimestamp(5, dateandtime);
            ps.setString(6, "S");
            // ps.setBoolean(7, b.isAdministrador());
            // ps.setString(8, b.getEmail().toLowerCase().trim());
            // ps.setInt(9, user_session.getCodigo());
            // ps.setString(10, b.getCpf().trim());
            // ps.setBoolean(11, b.isPrimeiroAcesso());
            ps.executeUpdate();
            con.commit();

            return b;

        } catch (Exception sqle) {
            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }
    }

    public List buscaUsuariosSecretaria() throws ProjetoException {
        System.out.println("entrou no dao buscaUsuariosSecretaria");
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        UsuarioBean user_session = (UsuarioBean) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get("obj_usuario");
        try {
            String sql = "select * from prot2.usuarios where usuarios.codsecretaria=? order by descusuario";

            ps = con.prepareStatement(sql);
            // ps.setInt(1, user_session.getUser_secretaria());
            ResultSet rs = ps.executeQuery();
            UsuarioBean n = new UsuarioBean();
            List colecao = new ArrayList();

            while (rs.next()) {
                n = new UsuarioBean();
                // n.setCodigo(rs.getInt(1));
                n.setNome(rs.getString(2));
                n.setSenha(rs.getString(7));

                n.setLogin(rs.getString(10));
                // n.setAdministrador((rs.getBoolean(11)));
                // n.setEmail(rs.getString(12));
                // n.setCpf(rs.getString(3));
                colecao.add(n);
            }

            return colecao;

        } catch (Exception sqle) {
            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
        }
    }

    public UsuarioBean consultaUsuario() throws ProjetoException {

        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();

        try {
            String sql = "select usuarios.codusuario, usuarios.codsetor, usuarios.ativo"
                    + "from prot2.usuarios where (usuarios.codusuario =?)";
            Object cod_user = (Object) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap()
                    .get("obj_codusuario_combo");

            String Str_cod_user = (String) cod_user;
            int Int_cod_user = Integer.parseInt(Str_cod_user);
            ps = con.prepareStatement(sql);
            ps.setInt(1, Int_cod_user);
            ResultSet rs = ps.executeQuery();

            UsuarioBean b = null;

            while (rs.next()) {

                b = new UsuarioBean();
                // b.setUser_setor(rs.getInt(2));
                // b.setCodigo(rs.getInt(1));

            }
            return b;
        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
            // fecharconexao();

        }
    }

    public String verificaUserCadastrado(String cpf) throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        String isExist = "N";

        try {
            String sql = "select * from prot2.usuarios where cpf=? and ativo=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, "S");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                isExist = "S";

            }

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }
        return isExist;
    }

    public String verificaLoginCadastrado(String login) throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        String isExist = "N";

        try {
            String sql = "select * from prot2.usuarios where login=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, login.toUpperCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                isExist = "S";
            }

        } catch (Exception sqle) {
            throw new ProjetoException(sqle);
        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }
        return isExist;
    }

    public String alteraSenha(UsuarioBean u) throws ProjetoException {
        PreparedStatement ps = null;

        Connection con = ConnectFactory.getConnection();
        try {
            String sql = "update prot2.usuarios set senha=?,primeiroacesso=?"
                    + "where id_usuario=?";
            UsuarioBean user_session = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_usuario");
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getSenha().toUpperCase());
            ps.setBoolean(2, false);
            // ps.setInt(3, user_session.getCodigo());
            ps.executeUpdate();
            con.commit();
            ps.close();
            return "success";

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
        }

    }

    public Integer verificaUltimoAdm(UsuarioBean U) throws ProjetoException {

        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        Integer Count = 0;

        try {

            String sql = "select * from prot2.usuarios where codsecretaria=? and administrador=?";
            UsuarioBean user_session = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_usuario");

            ps = con.prepareStatement(sql);
            // ps.setInt(1, user_session.getUser_secretaria());
            ps.setBoolean(2, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Count = Count + 1;

            }

        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }

        }
        return Count;
    }

    public List<SelectItem> consultaAnoAberturaProc() throws ProjetoException {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();

        try {

            String sql = "select distinct (date_part('year',datacriacao)) as ano "
                    + "from prot2.processos where codsecreorigem=?";

            UsuarioBean user_session = (UsuarioBean) FacesContext
                    .getCurrentInstance().getExternalContext().getSessionMap()
                    .get("obj_usuario");

            ps = con.prepareStatement(sql);
            // ps.setInt(1, user_session.getUser_secretaria());
            ResultSet rs = ps.executeQuery();

            List<SelectItem> collecAnoProcessos = new ArrayList<SelectItem>();

            while (rs.next()) {

                collecAnoProcessos.add(new SelectItem(rs.getInt(1), String
                        .valueOf(rs.getInt(1))));
            }
            return collecAnoProcessos;
        } catch (Exception sqle) {

            throw new ProjetoException(sqle);

        } finally {
            try {
                con.close();
            } catch (Exception sqlc) {
                sqlc.printStackTrace();
                System.exit(1);
                // TODO: handle exception
            }
        }
    }

    public List<Sistema> carregarSistemasUsuario(UsuarioBean usuario) throws ProjetoException {

        String sql = "select si.id, si.descricao, si.sigla, si.url, si.imagem, "
                + "si.versao, si.ativo from acl.perm_sistema ps "
                + "join acl.sistema si on si.id = ps.id_sistema "
                + "join leilao.func fuc on fuc.codfunc = ps.id_usuario "
                + "where fuc.codfunc = ? and si.ativo = true order by si.descricao";

        List<Sistema> listaSistemas = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, usuario.getCodfunc());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                s.setSigla(rs.getString("sigla"));
                s.setUrl(rs.getString("url") + "?faces-redirect=true");
                s.setImagem(rs.getString("imagem"));
                s.setVersao(rs.getString("versao"));
                s.setAtivo(rs.getBoolean("ativo"));
                listaSistemas.add(s);
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
        return listaSistemas;
    }

    public List<Permissoes> carregarPermissoes(UsuarioBean u) throws ProjetoException {

        String sql = "select us.cpf, si.id as sid, si.descricao as sdesc, pf.descricao pfdesc, "
                + "pm.id as pmid, pm.descricao as pmdesc, 0 as funid, '' as fundesc, '' as funcodigo, "
                + " false as funativa, 0 as funidsis, me.id as meid, me.descricao as medesc, me.desc_pagina, "
                + "me.diretorio, me.extensao, me.codigo as cod_menu, me.indice, me.tipo, me.ativo as meativo, "
                + "me.action_rel, me.onclick_rel, ro.descricao as rodesc, ro.id as roid from acl.perm_perfil pp "
                + "join leilao.func us on us.id_perfil = pp.id_perfil "
                + "join acl.perfil pf on pf.id = pp.id_perfil "
                + "join acl.permissao pm on pm.id = pp.id_permissao "
                + "join acl.perm_geral pg on pg.id_permissao = pm.id "
                + "join acl.menu me on me.id = pg.id_menu "
                + "join acl.menu_sistema ms on ms.id_menu = me.id "
                + "join acl.rotina ro on me.id_rotina = ro.id "
                + "join acl.sistema si on si.id = ms.id_sistema where us.codfunc = ? and me.ativo = true "
                + "union select us.cpf, si.id as sid, si.descricao as sdesc, pf.descricao pfdesc, "
                + "pm.id pmid, pm.descricao as pmdesc, fun.id as funid, fun.descricao as fundesc, "
                + "fun.codigo as funcodigo, fun.ativa as funativa, fun.id_sistema as fusidsis, 0 as meid, "
                + "'' as medesc, '', '', '', '' as cod_menu, '', case when '' = '' then 'funcao' end, "
                + "false as meativo, '', '', ro.descricao as rodesc, ro.id as roid from acl.perm_perfil pp "
                + "join leilao.func us on us.id_perfil = pp.id_perfil "
                + "join acl.perfil pf on pf.id = pp.id_perfil "
                + "join acl.permissao pm on pm.id = pp.id_permissao "
                + "join acl.perm_geral pg on pg.id_permissao = pm.id "
                + "join acl.funcao fun on fun.id = pg.id_funcao "
                + "join acl.rotina ro on fun.id_rotina = ro.id "
                + "join acl.sistema si on si.id = fun.id_sistema where us.codfunc = ? and fun.ativa = true "
                + "union select us.cpf, si.id as sid, si.descricao as sdesc, '' as pfdesc, "
                + "pm.id as pmid, pm.descricao as pmdesc, 0 as funid, '' as fundesc, '' as funcodigo, "
                + "false as funativa, 0 as funidsis, me.id as meid, me.descricao as medesc, me.desc_pagina, "
                + "me.diretorio, me.extensao, me.codigo as cod_menu, me.indice, me.tipo, me.ativo as meativo, "
                + "me.action_rel, me.onclick_rel, ro.descricao as rodesc, ro.id as roid from acl.perm_usuario pu "
                + "join leilao.func us on us.codfunc = pu.id_usuario "
                + "join acl.permissao pm on pm.id = pu.id_permissao "
                + "join acl.perm_geral pg on pg.id_permissao = pm.id "
                + "join acl.menu me on me.id = pg.id_menu "
                + "join acl.menu_sistema ms on ms.id_menu = me.id "
                + "join acl.rotina ro on me.id_rotina = ro.id "
                + "join acl.sistema si on si.id = ms.id_sistema where us.codfunc = ? and me.ativo = true "
                + "union select us.cpf, si.id as sid, si.descricao as sdesc, '', "
                + "pm.id pmid, pm.descricao as pmdesc, fun.id as funid, fun.descricao as fundesc, "
                + "fun.codigo as funcodigo, fun.ativa as funativa, fun.id_sistema as funidsis, 0 as meid, "
                + "'' as medesc, '', '', '', '' as cod_menu, '', case when '' = '' then 'funcao' end, "
                + "false as meativo, '', '', ro.descricao as rodesc, ro.id as roid from acl.perm_usuario pu "
                + "join leilao.func us on us.codfunc = pu.id_usuario "
                + "join acl.permissao pm on pm.id = pu.id_permissao "
                + "join acl.perm_geral pg on pg.id_permissao = pm.id "
                + "join acl.funcao fun on fun.id = pg.id_funcao "
                + "join acl.rotina ro on fun.id_rotina = ro.id "
                + "join acl.sistema si on si.id = fun.id_sistema where us.codfunc = ? and fun.ativa = true "
                + "order by pmdesc, sid";

        List<Permissoes> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, u.getCodfunc());
            stmt.setLong(2, u.getCodfunc());
            stmt.setLong(3, u.getCodfunc());
            stmt.setLong(4, u.getCodfunc());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getLong("meid"));
                m.setDescricao(rs.getString("medesc"));
                m.setCodigo(rs.getString("cod_menu"));
                m.setIndice(rs.getString("indice"));
                m.setTipo(rs.getString("tipo"));
                m.setAtivo(rs.getBoolean("meativo"));
                m.setDiretorio(rs.getString("diretorio"));
                m.setDescPagina(rs.getString("desc_pagina"));
                m.setExtensao(rs.getString("extensao"));
                m.setAction(rs.getString("action_rel"));
                m.setOnclick(rs.getString("onclick_rel"));

                if (rs.getString("tipo").equals("menuItem")) {
                    m.setUrl("/pages/" + m.getDiretorio() + "/"
                            + m.getDescPagina() + m.getExtensao());
                }

                if (rs.getString("funativa").equals("t")) {
                    m.setAtivo(true);
                }

                Permissoes p = new Permissoes();
                p.setIdSistema(rs.getInt("sid"));
                p.setDescSistema(rs.getString("sdesc"));
                p.setDescPerfil(rs.getString("pfdesc"));
                p.setIdPermissao(rs.getInt("pmid"));
                p.setDescPermissao(rs.getString("pmdesc"));
                p.setMenu(m);

                p.setCodigoFuncao(rs.getString("funcodigo"));
                p.setFuncaoAtiva(rs.getBoolean("funativa"));
                p.setIdSistemaFunc(rs.getInt("funidsis"));

                p.setIdRotina(rs.getInt("roid"));
                p.setDescRotina(rs.getString("rodesc"));

                lista.add(p);
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

    public List<Sistema> recListaSisSoucerCad() throws ProjetoException {

        String sql = "select id, descricao from acl.sistema "
            + "where ativo = true order by descricao";

        List<Sistema> listaSistemas = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                listaSistemas.add(s);
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
        return listaSistemas;
    }

    public List<Sistema> recListaSisSoucer(Long id) throws ProjetoException {

        String sql = "select id, descricao from acl.sistema "
            + "where id not in(select si.id from acl.perm_sistema ps "
            + "join acl.sistema si on si.id = ps.id_sistema "
            + "join leilao.func us on us.codfunc = ps.id_usuario "
            + "where us.codfunc = ?) and ativo = true order by descricao";

        List<Sistema> listaSistemas = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                listaSistemas.add(s);
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
        return listaSistemas;
    }

    public List<Sistema> recListaSisTarget(Long id) throws ProjetoException {

        String sql = "select si.id, si.descricao from acl.perm_sistema ps "
            + "join acl.sistema si on si.id = ps.id_sistema "
            + "join leilao.func us on us.codfunc = ps.id_usuario "
            + "where us.codfunc = ? and us.ativo = 'S' order by si.descricao";

        List<Sistema> listaSistemas = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConnectFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sistema s = new Sistema();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                listaSistemas.add(s);
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
        return listaSistemas;
    }
}