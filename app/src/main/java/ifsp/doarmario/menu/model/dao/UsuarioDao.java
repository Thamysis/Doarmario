package ifsp.doarmario.menu.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.menu.model.vo.Usuario;

//Nessa classe estão contidas informações sobre o Login e o usuario
public class UsuarioDao {
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public static UsuarioDao usuarioDao;

    public UsuarioDao(Context context) {
        //DbHelper db = new DbHelper(context);
        write = DbHelper.database.getWritableDatabase();
        read = DbHelper.database.getReadableDatabase();

        //Usuario usuarioTeste = new Usuario("yasmin_deodato", "yasmin.deodato.beatriz@gmail.com", "yasmin123");
        //salvar(usuarioTeste);
    }

    public boolean salvar(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("nome_usuario", usuario.getNome_usuario());
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());

        //o segundo parâmetro é para ter uma coluna específica como null
        try {
            write.insert(DbHelper.TABELA_USUARIO, null, cv);
        } catch (Exception e) {
            Log.i("", "excecao " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + ";";
        //segundo parâmetro são filtros
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setNome_usuario(c.getString(c.getColumnIndex("nome_usuario")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));

            usuarios.add(usuario);
        }

        return usuarios;
    }

    public boolean verificarUsuario(String nome_usuarioRec) {
        boolean existencia = false;
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE nome_usuario = ?";

        try {
            Cursor c = read.rawQuery(sql, new String[]{nome_usuarioRec});

            int colNome_usuario = c.getColumnIndex("nome_usuario");

            c.moveToFirst();
            if(!c.getString(colNome_usuario).isEmpty()){
                existencia = true;
            }
        } catch (Exception e) {
            //Log.i("", "Exceção " + e.getMessage());
        }

        return existencia;
    }

    public Usuario detalhar(String nome_usuarioRec) {
        Usuario usuario = new Usuario();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE nome_usuario = ?";

        //segundo parâmetro são filtros
        try {
            Cursor c = read.rawQuery(sql, new String[]{nome_usuarioRec});

            int colNome_usuario = c.getColumnIndex("nome_usuario");
            int colEmail = c.getColumnIndex("email");
            int colSenha = c.getColumnIndex("senha");

            c.moveToFirst();
            usuario.setNome_usuario(c.getString(colNome_usuario));
            usuario.setEmail(c.getString(colEmail));
            usuario.setSenha(c.getString(colSenha));

            Log.i("", "Usuario listado com sucesso");
        } catch (Exception e) {
            Log.i("", "Exceção " + e.getMessage());
        }

        return usuario;
    }

    public boolean atualizar(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());

        try {
            String[] argumentos = {usuario.getNome_usuario().toString()};
            write.update(DbHelper.TABELA_USUARIO, cv, "nome_usuario=?", argumentos);
            Log.i("INFO", "Usuario atualizado com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar usuario " + e.getMessage() );
            return false;
        }

        return true;
    }

    public boolean deletar(Usuario usuario) {
        try {
            String[] argumentos = {usuario.getNome_usuario().toString() };
            write.delete(DbHelper.TABELA_USUARIO, "nome_usuario=?", argumentos );
            Log.i("INFO", "Usuario removido com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover usuario " + e.getMessage() );
            return false;
        }

        return true;
    }
    public boolean login(String email) {
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE email = ?";
        String verifica;
        boolean existe = false;
        try {
            Cursor c = read.rawQuery(sql, new String[]{email});

            int colEmail = c.getColumnIndex("email");
            c.moveToFirst();
            verifica = c.getString(colEmail);

            if (!verifica.equals(null)) {
                existe = true;
                return existe;
            }
        } catch (Exception e) {
            return existe;
        }

        return existe;
    }

    public String verificaSenha(String email, String senha){
        String nome_usuario = null;
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIO + " WHERE email = ? AND senha = ?";

        try {
            Cursor c = read.rawQuery(sql, new String[]{email, senha});
            int colNomeUsuario = c.getColumnIndex("nome_usuario");

            if(c.moveToFirst()){
                nome_usuario = c.getString(colNomeUsuario);
                return nome_usuario;
            }
        } catch (Exception e) { }

        return nome_usuario;
    }

    public void atualizarSenha(String email, String senha) {
        ContentValues values = new ContentValues();
        values.put("senha", senha);
        write.update(DbHelper.TABELA_USUARIO, values, "email = ?", new String[]{email});
    }


}
