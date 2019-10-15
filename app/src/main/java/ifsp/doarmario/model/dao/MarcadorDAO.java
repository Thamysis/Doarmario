package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ifsp.doarmario.model.vo.Marcador;

public class MarcadorDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    DbHelper helper;
    public MarcadorDAO(Context context){
        helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
    }

    public boolean salvar(Marcador marcador) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_marcador", marcador.getDescricao_marcador());

        try {
            escreve.insert(DbHelper.TABELA_MARCADOR, null, cv);
            Log.i("INFO", "Marcador salvo com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar marcador!" + e.getMessage()) ;
            return false;
        }
        escreve.close();
        return true;
    }

    public ArrayList<Marcador> listar() {
        le = helper.getReadableDatabase();
        ArrayList<Marcador> listaMarcadores = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MARCADOR + " ORDER BY descricao_marcador;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Marcador marcador = new Marcador();

            Long id_marcador = c.getLong( c.getColumnIndex("id_marcador") );
            String descricao_marcador = c.getString( c.getColumnIndex("descricao_marcador") );

            marcador.setId_marcador( id_marcador );
            marcador.setDescricao_marcador( descricao_marcador);

            listaMarcadores.add( marcador );
        }
        le.close();
        return listaMarcadores;
    }

    public boolean atualizar(Marcador marcador) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_marcador", marcador.getDescricao_marcador() );

        try {
            String[] args = {marcador.getId_marcador().toString()};
            escreve.update(DbHelper.TABELA_MARCADOR, cv, "id_marcador=?", args );
            Log.i("INFO", "Marcador atualizado com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar marcador! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;
    }

    public boolean deletar(Marcador marcador) {
        try {
            String[] args = { marcador.getId_marcador().toString() };
            escreve.delete(DbHelper.TABELA_MARCADOR, "id_marcador=?", args );
            Log.i("INFO", "Marcador removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover marcador! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;

    }

    public Marcador detalhar (Long id){
        le = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DbHelper.TABELA_MARCADOR + " WHERE ID_MARCADOR =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Marcador marcador = new Marcador();

            Long id_marcador = c.getLong( c.getColumnIndex("id_marcador") );
            String descricao_marcador = c.getString( c.getColumnIndex("descricao_marcador") );

            marcador.setId_marcador( id_marcador );
            marcador.setDescricao_marcador( descricao_marcador);

            return marcador;
        }
        le.close();
        return null;
    }
}