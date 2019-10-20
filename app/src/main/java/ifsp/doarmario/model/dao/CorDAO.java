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
    private DbHelper helper;
    public CorDAO(Context context){
        helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
    }

    public boolean salvar(Cor cor) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_cor", cor.getDescricao_cor());

        try {
            escreve.insert(DbHelper.TABELA_COR, null, cv);
            escreve.close();
            Log.i("INFO", "Cor salva com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar cor!" + e.getMessage()) ;
            return false;
        }
        return true;
    }
    public ArrayList<Cor> listar() {
        le = helper.getReadableDatabase();
        ArrayList<Cor> listaCores = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_COR + " ORDER BY descricao_cor;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
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
        le = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DbHelper.TABELA_COR + " WHERE ID_COR =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Cor cor = new Cor();

            Long id_cor = c.getLong( c.getColumnIndex("id_cor") );
            String descricao_cor = c.getString( c.getColumnIndex("descricao_cor") );

            cor.setId_cor( id_cor );
            cor.setDescricao_cor( descricao_cor);

            return cor;
        }
        le.close();
        return null;
    }
    public boolean atualizar(Cor cor) {

        ContentValues cv = new ContentValues();
        cv.put("descricao_cor", cor.getDescricao_cor() );

        try {
            String[] args = {cor.getId_cor().toString()};
            escreve.update(DbHelper.TABELA_COR, cv, "id_cor=?", args );
            Log.i("INFO", "Cor atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar cor! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;
    }

    public boolean deletar(Cor cor) {
        try {
            String[] args = { cor.getId_cor().toString() };
            escreve.delete(DbHelper.TABELA_COR, "id_cor=?", args );
            Log.i("INFO", "Cor removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover cor! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;

    }
<<<<<<< HEAD

=======
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349
}
