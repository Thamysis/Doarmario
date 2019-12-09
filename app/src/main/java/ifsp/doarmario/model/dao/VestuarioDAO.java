package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import ifsp.doarmario.model.vo.Vestuario;

public class VestuarioDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public boolean salvar(Vestuario vestuario) {
        escreve = DbHelper.database.getWritableDatabase();

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
            status = true;
        } catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }
    public Long idUtlimoVestuario(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        Long id_vestuario = null;

        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE nome_usuario = '" + usuario + "';";

        Cursor c = le.rawQuery(sql, null);

        c.moveToLast();
        id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );

        le.close();
        return id_vestuario;
    }
    public boolean atualizar(Vestuario vestuario) {
        escreve = DbHelper.database.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("descricao_vestuario", vestuario.getDescricao_vestuario() );
        cv.put("id_cor", vestuario.getId_cor());
        cv.put("id_categoria",vestuario.getId_categoria());
        cv.put("id_marcador",vestuario.getId_marcador());

        try {
            String[] args = {vestuario.getId_vestuario().toString()};
            escreve.update(DbHelper.TABELA_VESTUARIO, cv, "id_vestuario=?", args );
            status = true;
        }catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }
    public boolean atualizarDoada(Vestuario vestuario) {
        escreve = DbHelper.database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status_doacao", vestuario.getStatus_doacao());

        try {
            String[] args = {vestuario.getId_vestuario().toString()};
            escreve.update(DbHelper.TABELA_VESTUARIO, cv, "id_vestuario=?", args );
            Log.i("INFO", "Vestuário atualizado com sucesso!");
            escreve.close();
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar vestuário " + e.getMessage() );
            escreve.close();
            return false;
        }
        return true;
    }
    public boolean deletar(Vestuario vestuario) {
        escreve = DbHelper.database.getWritableDatabase();

        try {
            String[] args = { vestuario.getId_vestuario().toString() };
            escreve.delete(DbHelper.TABELA_VESTUARIO, "id_vestuario=?", args );
            status = true;
        }catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }
    public List<Vestuario> listar(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE nome_usuario = '" + usuario + "';";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);

            listaVestuarios.add( vestuario );
        }
        le.close();
        return listaVestuarios;
    }
    //listas por categoria
    public List<Vestuario> listarParteDeCima(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Categoria on Vestuario.id_categoria = Categoria.id_categoria "
                + " WHERE nome_usuario = '" + usuario + "'"
                + " AND tipo_categoria = 'roupa_de_cima'  "
                + " AND NOT status_doacao =  'd'"
                + ";";

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
        }
        le.close();
        return listaVestuarios;
    }
    public List<Vestuario> listarParteDeBaixo(String usuario) {
        le = DbHelper.database.getReadableDatabase();

        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Categoria on Vestuario.id_categoria = Categoria.id_categoria "
                + " WHERE nome_usuario = '" + usuario + "'"
                + " AND tipo_categoria = 'roupa_de_baixo'  "
                + " AND NOT status_doacao =  'd'"
                + ";";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_categoria(id_marcador);
            vestuario.setNome_usuario(nome_usuario);

            listaVestuarios.add( vestuario );
        }
        le.close();
        return listaVestuarios;
    }
    public List<Vestuario> listarSapato(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Categoria on Vestuario.id_categoria = Categoria.id_categoria "
                + " WHERE nome_usuario = '" + usuario + "'"
                + " AND tipo_categoria = 'calcado'  "
                + " AND NOT status_doacao =  'd'"
                + ";";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));

            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_marcador(id_marcador);
            vestuario.setId_categoria(id_categoria);
            vestuario.setNome_usuario(nome_usuario);

            listaVestuarios.add( vestuario );
        }
        le.close();
        return listaVestuarios;
    }
    public List<Vestuario> listarPecaUnica(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Categoria on Vestuario.id_categoria = Categoria.id_categoria "
                + " WHERE nome_usuario = '" + usuario + "'"
                + " AND tipo_categoria = 'peca_unica'  "
                + ";";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));


            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);

            listaVestuarios.add( vestuario );
        }
        le.close();
        return listaVestuarios;
    }
    public List<Vestuario> listarAcesssorios(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Categoria on Vestuario.id_categoria = Categoria.id_categoria "
                + " WHERE nome_usuario = '" + usuario + "'"
                + " AND tipo_categoria = 'acessorio'  "
                + ";";
        Cursor c = le.rawQuery(sql, null);
        Log.i("", sql);

        while ( c.moveToNext() ){
            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));


            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_marcador(id_marcador);
            vestuario.setId_categoria(id_categoria);
            vestuario.setNome_usuario(nome_usuario);
            listaVestuarios.add( vestuario );
            Log.i("INFO", vestuario.getDescricao_vestuario() );
        }
        le.close();
        return listaVestuarios;
    }
    //listas por cor
    //listas por marcadores
    public List<Vestuario> listarDoadas(String usuario) {
        le = DbHelper.database.getReadableDatabase();
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
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));

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
    public List<Vestuario> listarNaoUtilizadas(String usuario) {
        le = DbHelper.database.getReadableDatabase();
        List<Vestuario> listaVestuarios = new ArrayList<>();

//        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
//        Date data = new Date();
//        //03-12-2019
//        String dateFormatado = formataData.format(data);
//        int diaAtual = Integer.parseInt(dateFormatado.charAt(0) + dateFormatado.charAt(1)+"");
//        Log.i("NUTILIZADAS", diaAtual + "");
//        int mesAtual = Integer.parseInt(dateFormatado.charAt(3) + dateFormatado.charAt(4)+"");
//        Log.i("NUTILIZADAS", mesAtual + "");
//        int anoAtual = Integer.parseInt(dateFormatado.charAt(6) + dateFormatado.charAt(7)+ dateFormatado.charAt(8) + dateFormatado.charAt(9)"");
//        Log.i("NUTILIZADAS", anoAtual + "");
//
//        Integer [] a = new Integer[12];
//
//
//        //mes com 28 dias = 2
//        //meses com 30 dias =  4,6,9,11
//        //meses com 31 dias = 1,3,5,7,8,10,12
//
//        if(diaAtual - 30 < 0){
//            mesAtual --;
//            if()
//            diaAtual =
//        }
//
//        Log.i("NUTILI", dateFormatado + "");

//        String sql = "SELECT * FROM" + DbHelper.TABELA_MONTAGEM_VESTUARIO +
//                " INNER JOIN " + DbHelper.TABELA_MONTAGEM +
//                " ON " + DbHelper.TABELA_MONTAGEM_VESTUARIO + ".id_montagem = "+
//                DbHelper.TABELA_MONTAGEM + ".id_montagem" +
//                "where " + DbHelper.TABELA_MONTAGEM ".data_montagem = "
//                ;

       // String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " ;";
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO
                + " WHERE NOT status_doacao = 'd' AND nome_usuario = '" + usuario  + "' "
                + ";" ;

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Vestuario vestuario = new Vestuario();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));


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
        le = DbHelper.database.getReadableDatabase();

        Vestuario vestuario = new Vestuario();
        String sql = "SELECT * FROM " + DbHelper.TABELA_VESTUARIO + " WHERE id_vestuario = " + idVestuario + " ;";
        Cursor c = le.rawQuery(sql, null);

        try {
            c.moveToFirst();

            Long id_vestuario = c.getLong( c.getColumnIndex("id_vestuario") );
            String descricao_vestuario = c.getString( c.getColumnIndex("descricao_vestuario") );
            String imagem_vestuario = c.getString(c.getColumnIndex("imagem_vestuario"));
            String status_doacao = c.getString(c.getColumnIndex("status_doacao"));
            Long id_cor = c.getLong(c.getColumnIndex("id_cor"));
            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String nome_usuario = c.getString(c.getColumnIndex("nome_usuario"));
            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));


            vestuario.setId_vestuario( id_vestuario );
            vestuario.setDescricao_vestuario( descricao_vestuario);
            vestuario.setImagem_vestuario(imagem_vestuario);
            vestuario.setStatus_doacao(status_doacao);
            vestuario.setId_cor(id_cor);
            vestuario.setId_categoria(id_categoria);
            vestuario.setId_marcador(id_marcador);
            vestuario.setNome_usuario(nome_usuario);
        }catch (Exception e){
            vestuario = null;
        }finally {
            le.close();
        }
        return vestuario;
    }


}