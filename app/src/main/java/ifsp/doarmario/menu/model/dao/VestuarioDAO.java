package ifsp.doarmario.menu.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ifsp.doarmario.menu.model.vo.Vestuario;

public class VestuarioDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    Context context;

    public VestuarioDAO(Context context){
        DbHelper helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
        this.context = context;
    }

    public boolean salvar(Vestuario vestuario) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_vestuario", vestuario.getDescricao_vestuario());
        cv.put("imagem_vestuario", vestuario.getImagem_vestuario());
        cv.put("status_doacao", vestuario.getStatus_doacao());
        cv.put("id_cor", vestuario.getId_cor());
        cv.put("id_categoria", vestuario.getId_categoria());
        cv.put("id_marcador", vestuario.getId_marcador());
        cv.put("nome_usuario", vestuario.getNome_usuario());
        try {
            escreve.insert(DbHelper.TABELA_VESTUARIO, null, cv);
            Log.i("INFO", "Vestuario salvo com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar vestuario!" + e.getMessage()) ;
            return false;
        }
        escreve.close();
        return true;
    }
    public boolean atualizar(Vestuario vestuario) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_vestuario", vestuario.getDescricao_vestuario() );
        cv.put("id_cor", vestuario.getId_cor());
        cv.put("id_categoria",vestuario.getId_categoria());
        cv.put("id_marcador", vestuario.getId_marcador());


        try {
            String[] args = {vestuario.getId_vestuario().toString()};
            escreve.update(DbHelper.TABELA_VESTUARIO, cv, "id_vestuario=?", args );
            Log.i("INFO", "Vestuário atualizado com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar vestuário " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;
    }
    public boolean atualizarDoada(Vestuario vestuario) {

        ContentValues cv = new ContentValues();
        cv.put("status_doacao", vestuario.getStatus_doacao());

        try {
            String[] args = {vestuario.getId_vestuario().toString()};
            escreve.update(DbHelper.TABELA_VESTUARIO, cv, "id_vestuario=?", args );
            Log.i("INFO", "Vestuário atualizado com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar vestuário " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;
    }
    public boolean deletar(Vestuario vestuario) {
        try {
            String[] args = { vestuario.getId_vestuario().toString() };
            escreve.delete(DbHelper.TABELA_VESTUARIO, "id_vestuario=?", args );
            Log.i("INFO", "Vestuário removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover vestuário " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;

    }
    public List<Vestuario> listar(String usuario) {
        List<Vestuario> listaVestuarios = new ArrayList<>();
        //String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE nome_usuario = " + usuario + ";";
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE nome_usuario = '" + usuario + "';";

        Log.i("", sql);
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);


            listaVestuarios.add( vestuario );
            Log.i("INFO", vestuario.getDescricao_vestuario() );
        }
        le.close();
        return listaVestuarios;
    }

    public List<Vestuario> listarDoadas(String usuario) {
        List<Vestuario> listaVestuarios = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE status_doacao = 'd' AND nome_usuario = '" + usuario  + "' ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);


            listaVestuarios.add( vestuario );
            Log.i("INFO", vestuario.getDescricao_vestuario() );
        }
        le.close();
        return listaVestuarios;
    }
    public List<Vestuario> listarNaoUtilizadas() {
        List<Vestuario> listaVestuarios = new ArrayList<>();

        Date d = new Date();

        Calendar cal = new GregorianCalendar();

        cal.setTime(d);
        Toast.makeText(context,
                "" + cal,
                Toast.LENGTH_SHORT).show();


       // String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " ;";
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " WHERE status_doacao != 'd' "

                +" ;";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);


            listaVestuarios.add( vestuario );
            Log.i("INFO", vestuario.getDescricao_vestuario() );
        }
        le.close();
        return listaVestuarios;
    }

    public Vestuario detalhar(Long idVestuario) {
        Vestuario vestuario = new Vestuario();

        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE id_vestuario = " + idVestuario + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext() ){
            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);

            Log.i("INFO", vestuario.getDescricao_vestuario() );
        }
        le.close();
        return vestuario;
    }
}