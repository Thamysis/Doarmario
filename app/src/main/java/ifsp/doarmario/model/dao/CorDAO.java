package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ifsp.doarmario.model.vo.Cor;

public class CorDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public boolean salvar(Cor cor) {
        escreve = DbHelper.database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("descricao_cor", cor.getDescricao_cor());

        try {
            escreve.insert(DbHelper.TABELA_COR, null, cv);
            status = true;
        } catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }

    public ArrayList<Cor> listar() {
        le = DbHelper.database.getReadableDatabase();
        ArrayList<Cor> listaCores = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_COR + " ORDER BY descricao_cor;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            Cor cor = new Cor();

            Long id_cor = c.getLong( c.getColumnIndex("id_cor") );
            String descricao_cor = c.getString( c.getColumnIndex("descricao_cor") );

            cor.setId_cor( id_cor );
            cor.setDescricao_cor( descricao_cor);

            listaCores.add( cor );
        }

        le.close();
        return listaCores;
    }

    public Cor detalhar(Long id){
        le = DbHelper.database.getReadableDatabase();

        Cor cor = new Cor();
        String sql = "SELECT * FROM " + DbHelper.TABELA_COR + " WHERE ID_COR =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);

        try {
            c.moveToFirst();
            Long id_cor = c.getLong( c.getColumnIndex("id_cor") );
            String descricao_cor = c.getString( c.getColumnIndex("descricao_cor") );

            cor.setId_cor( id_cor );
            cor.setDescricao_cor( descricao_cor);
        } catch (Exception e) {

            cor = null;
        } finally {
            le.close();
        }

        return cor;
    }
}
