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
    private boolean status;

    public boolean salvar(Categoria categoria) {
        escreve = DbHelper.database.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("descricao_categoria", categoria.getDescricao_categoria());
        cv.put("tipo_categoria", categoria.getTipo_categoria());

        try {
            escreve.insert(DbHelper.TABELA_CATEGORIA, null, cv);
            status = true;
        } catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }

    public ArrayList<Categoria> listar() {
        le = DbHelper.database.getReadableDatabase();

        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " ORDER BY descricao_categoria;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
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
        le = DbHelper.database.getReadableDatabase();

        ArrayList<String> listaTipoCategoria = new ArrayList<>();

        String sql = "SELECT tipo_categoria FROM " + DbHelper.TABELA_CATEGORIA + " GROUP BY tipo_categoria ORDER BY tipo_categoria;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            String tipo_categoria = c.getString( c.getColumnIndex("tipo_categoria") );

            listaTipoCategoria.add(tipo_categoria);
        }
        le.close();
        return listaTipoCategoria;
    }

    public Categoria detalhar(Long id){
        le = DbHelper.database.getReadableDatabase();

        Categoria categoria = new Categoria();
        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " WHERE ID_CATEGORIA =" + id + " ;";
        Cursor c = le.rawQuery(sql, null);
        try {
            c.moveToFirst();

            Long id_categoria = c.getLong(c.getColumnIndex("id_categoria"));
            String descricao_categoria = c.getString(c.getColumnIndex("descricao_categoria"));
            String tipo_categoria = c.getString(c.getColumnIndex("tipo_categoria"));

            categoria.setId_categoria(id_categoria);
            categoria.setDescricao_categoria(descricao_categoria);
            categoria.setTipo_categoria(tipo_categoria);
        }catch (Exception e){
            System.err.print(e );
        }
        le.close();
        return categoria;
    }

    public Categoria detalharByTipo(String tipo_categoria) {
        le = DbHelper.database.getReadableDatabase();
        Categoria categoria = new Categoria();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " WHERE tipo_categoria = '" + tipo_categoria + "' ;";

        try {
            Cursor c = le.rawQuery(sql, null);

            while(c.moveToNext()) {
                c.moveToFirst();

                Long id_categoria = c.getLong( c.getColumnIndex("id_categoria") );
                String descricao_categoria = c.getString( c.getColumnIndex("descricao_categoria") );
                String tipocategoria = c.getString( c.getColumnIndex("tipo_categoria") );

                categoria.setId_categoria(id_categoria);
                categoria.setDescricao_categoria(descricao_categoria);
                categoria.setTipo_categoria(tipocategoria);
            }

        } catch (Exception e) {
            categoria = null;
        } finally {
            le.close();
        }

        return categoria;
    }

    public Categoria detalharById(Long id_categoria) {
        le = DbHelper.database.getReadableDatabase();

        Categoria categoria = new Categoria();
        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + " WHERE id_categoria = " + id_categoria + " ;";

        Cursor c = le.rawQuery(sql, null);
        try {

            c.moveToFirst();

            Long idcategoria = c.getLong( c.getColumnIndex("id_categoria") );
            String descricao_categoria = c.getString( c.getColumnIndex("descricao_categoria") );
            String tipocategoria = c.getString( c.getColumnIndex("tipo_categoria") );

             categoria.setId_categoria(idcategoria);
             categoria.setDescricao_categoria(descricao_categoria);
             categoria.setTipo_categoria(tipocategoria);


        } catch (Exception e) {
            categoria = null;
        } finally {
            le.close();
        }

        return categoria;
    }
}