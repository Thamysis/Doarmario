package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.model.vo.Montagem_Vestuario;
import ifsp.doarmario.model.vo.Vestuario;

public class Montagem_VestuarioDao {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public boolean salvar(Montagem_Vestuario montagem_vestuario) {
        escreve = DbHelper.database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_montagem", montagem_vestuario.getId_montagem());
        cv.put("id_vestuario", montagem_vestuario.getId_vestuario());

        try {
            escreve.insert(DbHelper.TABELA_MONTAGEM_VESTUARIO, null, cv);
            status = true;
        } catch (Exception e) {
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }

    public ArrayList<Long> listar(Long id_montagem) {
        le = DbHelper.database.getReadableDatabase();
        ArrayList<Long> id_vestuarios = new ArrayList<Long>();

        //Obter lista de Vestuarios que fazem parte de uma montagem específica
        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM_VESTUARIO + " WHERE id_montagem = ?";

        try {
            Cursor c = le.rawQuery(sql, new String[]{id_montagem + ""});
            //c.moveToFirst();
            while(c.moveToNext()) {
                int colIdVestuario = c.getColumnIndex("id_vestuario");
                id_vestuarios.add(c.getLong(colIdVestuario));
            }
        } catch (Exception e) {
            System.err.print("O erro está aqui:" + e);
            id_vestuarios = null;
        } finally {
            le.close();
        }

        return id_vestuarios;
    }

    public boolean deletar(Long id_montagem) {
        escreve = DbHelper.database.getWritableDatabase();
        try {
            String[] argumentos = {id_montagem + ""};
            escreve.delete(DbHelper.TABELA_MONTAGEM_VESTUARIO, "id_montagem = ?", argumentos );
            status = true;
        }catch (Exception e){
            status = false;
        } finally {
            escreve.close();
        }
        return status;
    }

}
