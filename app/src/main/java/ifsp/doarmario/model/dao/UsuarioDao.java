package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.model.vo.Montagem;
import ifsp.doarmario.model.vo.Usuario;

public class UsuarioDao {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public static UsuarioDao usuarioDao;

    public boolean deletar(Usuario usuario) {
        escreve = DbHelper.database.getWritableDatabase();

        try {
            String[] args = { usuario.getNome_usuario() };

            escreve.delete(DbHelper.TABELA_VESTUARIO, "nome_usuario=?", args);
            escreve.delete(DbHelper.TABELA_USUARIO, "nome_usuario=?", args);

            status = true;
        } catch (Exception e){
            Log.i("AAA", e.getMessage());
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }
    public boolean salvar(Usuario usuario) {
        escreve = DbHelper.database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome_usuario", usuario.getNome_usuario());
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());

        try {
            escreve.insert(DbHelper.TABELA_USUARIO, null, cv);
            status = true;
        } catch (Exception e) {
            status = false;
        }

        return status;
    }

    public List<Usuario> listar() {
        le = DbHelper.database.getReadableDatabase();
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + ";";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setNome_usuario(c.getString(c.getColumnIndex("nome_usuario")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));

            usuarios.add(usuario);
        }

        le.close();
        return usuarios;
    }

    public boolean verificarUsuario(String nome_usuarioRec) {
        le = DbHelper.database.getReadableDatabase();

        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE nome_usuario = ?";

        try {
            Cursor c = le.rawQuery(sql, new String[]{nome_usuarioRec});

            int colNome_usuario = c.getColumnIndex("nome_usuario");

            c.moveToFirst();
            if(!c.getString(colNome_usuario).isEmpty()){
                status = true;
            }
        } catch (Exception e) {
           status = false;
        } finally {
            le.close();
        }

        return status;
    }

    public Usuario detalhar(String nome_usuarioRec) {
        le = DbHelper.database.getReadableDatabase();
        Usuario usuario = new Usuario();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE nome_usuario = ?";

        try {
            Cursor c = le.rawQuery(sql, new String[]{nome_usuarioRec});

            int colNome_usuario = c.getColumnIndex("nome_usuario");
            int colEmail = c.getColumnIndex("email");
            int colSenha = c.getColumnIndex("senha");

            c.moveToFirst();
            usuario.setNome_usuario(c.getString(colNome_usuario));
            usuario.setEmail(c.getString(colEmail));
            usuario.setSenha(c.getString(colSenha));
        } catch (Exception e) {
            usuario = null;
        } finally {
            le.close();
        }

        return usuario;
    }

    public boolean atualizar(Usuario usuario) {
        escreve = DbHelper.database.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());

        try {
            String[] argumentos = {usuario.getNome_usuario()};
            escreve.update(DbHelper.TABELA_USUARIO, cv, "nome_usuario=?", argumentos);
            status = true;
        } catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }


    public boolean login(String email) {
        le = DbHelper.database.getReadableDatabase();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE email = ?";
        String verifica;
        boolean existe = false;

        try {
            Cursor c = le.rawQuery(sql, new String[]{email});

            int colEmail = c.getColumnIndex("email");
            c.moveToFirst();
            verifica = c.getString(colEmail);

            if (!verifica.equals(null)) {
                existe = true;
            }
        } catch (Exception e) {
            existe = false;
        } finally {
            le.close();
        }

        return existe;
    }

    public String verificaSenha(String email, String senha){
        le = DbHelper.database.getReadableDatabase();
        String nome_usuario = "";
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE email = ? AND senha = ?";

        try {
            Cursor c = le.rawQuery(sql, new String[]{email, senha});
            int colNomeUsuario = c.getColumnIndex("nome_usuario");

            if(c.moveToFirst()){
                nome_usuario = c.getString(colNomeUsuario);
            }
        } catch (Exception e) {
            nome_usuario = null;
        } finally {
            le.close();
        }

        return nome_usuario;
    }
}
