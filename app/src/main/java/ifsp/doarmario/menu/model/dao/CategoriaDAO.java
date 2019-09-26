package ifsp.doarmario.menu.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ifsp.doarmario.menu.model.vo.Categoria;

public class CategoriaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private DbHelper helper;
    public CategoriaDAO(Context context){
        helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
        cargaInicial();
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

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " ;";
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
            Log.i("INFO", categoria.getDescricao_categoria()  );
        }
        le.close();
        return listaCategorias;
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

    public void cargaInicial(){
        salvar(new Categoria("parte_baixo", "Saia"));
    }
}
