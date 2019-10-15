package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ifsp.doarmario.model.vo.Categoria;

public class CategoriaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private DbHelper helper;
    public CategoriaDAO(Context context){
        helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
    }

    public boolean salvar(Categoria categoria) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_categoria", categoria.getDescricao_categoria());
        cv.put("tipo_categoria", categoria.getTipo_categoria());

        try {
            escreve.insert(DbHelper.TABELA_CATEGORIA, null, cv);
            escreve.close();
            Log.i("INFO", "Categoria salva com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar categoria!" + e.getMessage()) ;
            return false;
        }
        return true;
    }

    public ArrayList<Categoria> listar() {
        le = helper.getReadableDatabase();
        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " ORDER BY descricao_categoria;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Categoria categoria = new Categoria();

            Long id_categoria = c.getLong( c.getColumnIndex("id_categoria") );
            String descricao_categoria = c.getString( c.getColumnIndex("descricao_categoria") );
            String tipo_categoria = c.getString( c.getColumnIndex("tipo_categoria") );

            categoria.setId_categoria( id_categoria );
            categoria.setDescricao_categoria(descricao_categoria);
            categoria.setTipo_categoria(tipo_categoria);

            listaCategorias.add( categoria );
        }
        le.close();
        return listaCategorias;
    }

    public ArrayList<String> listarTipoCategoria() {
        le = helper.getReadableDatabase();
        ArrayList<String> listaTipoCategoria = new ArrayList<>();

        String sql = "SELECT tipo_categoria FROM " + DbHelper.TABELA_CATEGORIA + " GROUP BY tipo_categoria ORDER BY tipo_categoria;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            String tipo_categoria = c.getString( c.getColumnIndex("tipo_categoria") );

            listaTipoCategoria.add( tipo_categoria );
        }
        le.close();
        return listaTipoCategoria;
    }

    public boolean atualizar(Categoria categoria) {
        ContentValues cv = new ContentValues();
        cv.put("descricao_categoria", categoria.getDescricao_categoria() );
        cv.put("tipo_categoria", categoria.getTipo_categoria() );
        try {
            String[] args = {categoria.getId_categoria().toString()};
            escreve.update(DbHelper.TABELA_CATEGORIA, cv, "id_categoria=?", args );
            Log.i("INFO", "Categoria atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar categoria! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;
    }

    public boolean deletar(Categoria categoria) {
        try {
            String[] args = { categoria.getId_categoria().toString() };
            escreve.delete(DbHelper.TABELA_CATEGORIA, "id_categoria=?", args );
            Log.i("INFO", "Categoria removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover categoria! " + e.getMessage() );
            return false;
        }
        escreve.close();
        return true;

    }

    public Categoria detalhar ( Long id){
        le = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " WHERE ID_CATEGORIA =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Categoria categoria = new Categoria();

            Long id_categoria = c.getLong( c.getColumnIndex("id_categoria") );
            String descricao_categoria = c.getString( c.getColumnIndex("descricao_categoria") );

            categoria.setId_categoria( id_categoria );
            categoria.setDescricao_categoria( descricao_categoria);

            return categoria;
        }
        le.close();
        return null;
    }
}
