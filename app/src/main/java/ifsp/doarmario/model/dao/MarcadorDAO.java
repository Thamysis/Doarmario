package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import ifsp.doarmario.model.vo.Marcador;

public class MarcadorDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public boolean salvar(Marcador marcador) {
        escreve = DbHelper.database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao_marcador", marcador.getDescricao_marcador());

        try {
            escreve.insert(DbHelper.TABELA_MARCADOR, null, cv);
            status = true;
        }catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }

    public ArrayList<Marcador> listar() {
        le = DbHelper.database.getReadableDatabase();
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

    public Marcador detalhar(Long id){
        le = DbHelper.database.getReadableDatabase();

        Marcador marcador = new Marcador();
        String sql = "SELECT * FROM " + DbHelper.TABELA_MARCADOR + " WHERE ID_MARCADOR =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);

        try {
            c.moveToFirst();

            Long id_marcador = c.getLong( c.getColumnIndex("id_marcador") );
            String descricao_marcador = c.getString( c.getColumnIndex("descricao_marcador") );

            marcador.setId_marcador( id_marcador );
            marcador.setDescricao_marcador( descricao_marcador);
        } catch (Exception e) {
            marcador = null;
        } finally {
            le.close();
        }

        return marcador;
    }
}