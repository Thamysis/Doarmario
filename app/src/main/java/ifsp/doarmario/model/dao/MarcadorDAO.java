package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.model.vo.Marcador;
import ifsp.doarmario.model.vo.Vestuario;

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
<<<<<<< HEAD

    public List<Marcador> retornaMarcadorPeca(Long idVestuario) {
        List<Marcador> listaMarcadores = new ArrayList<>();
        String sql = "SELECT * FROM " + " marcador "
                + " INNER JOIN marcador_vestuario on marcador_vestuario.id_marcador = marcador.id_marcador "
                + " WHERE marcador_vestuario.id_vestuario =  " + idVestuario
                + ";";
        Log.i("INFO", sql);
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {
            Marcador marcador = new Marcador();

            Long id_marcador = c.getLong(c.getColumnIndex("id_marcador"));
            String descricao_marcador = c.getString(c.getColumnIndex("descricao_marcador"));

            marcador.setId_marcador(id_marcador);
            marcador.setDescricao_marcador(descricao_marcador);

            listaMarcadores.add(marcador);
            Log.i("INFO", marcador.getDescricao_marcador());
        }
        le.close();
        return listaMarcadores;
    }
=======
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349
}