package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import ifsp.doarmario.model.vo.Marcador_Vestuario;

public class Marcador_VestuarioDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    DbHelper helper;

    public Marcador_VestuarioDAO(Context context) {
        helper = new DbHelper(context);
        escreve = helper.getWritableDatabase();
        le = helper.getReadableDatabase();
    }

    public boolean salvar(Marcador_Vestuario marcador_vestuario) {
        ContentValues cv = new ContentValues();
        cv.put("id_marcador", marcador_vestuario.getId_marcador());
        cv.put("id_vestuario", marcador_vestuario.getId_vestuario());

        try {
            escreve.insert(DbHelper.TABELA_MARCADOR_VESTUARIO, null, cv);
        } catch (Exception e) {
            Log.i("INFO", " Exceção: " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<Marcador_Vestuario> listarMarcadores(Long id_vestuario) {
        List<Marcador_Vestuario> lista_marcador_vestuarios = new ArrayList<>();

        //Obter lista de marcadores com o id_vestuario = ao recebido
        String sql = "SELECT * FROM " + DbHelper.TABELA_MARCADOR_VESTUARIO + " WHERE id_vestuario = ?";

        try {
            Cursor c = le.rawQuery(sql, new String[]{ id_vestuario + ""});

            c.moveToFirst();

            //percorrer a listagem
            while (c.moveToNext()) {
                Marcador_Vestuario marcador_vestuario = new Marcador_Vestuario();
                int colIdMarcador = c.getInt(c.getColumnIndex("id_marcador"));
                int colIdVestuario = c.getInt(c.getColumnIndex("id_vestuario"));

                marcador_vestuario.setId_marcador(c.getLong(colIdMarcador));
                marcador_vestuario.setId_vestuario(c.getLong(colIdVestuario));

                lista_marcador_vestuarios.add(marcador_vestuario);
            }

            Log.i("INFO", "Montagem_Vestuario listada com sucesso");
        } catch (Exception e) {
            Log.i("INFO", "Exceção " + e.getMessage());
        }

        return lista_marcador_vestuarios;
    }

    public List<Marcador_Vestuario> listarMarcador_Vestuario () {
        List<Marcador_Vestuario> list_marcador_vestuario = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MARCADOR_VESTUARIO + ";";
        //segundo parâmetro são filtros
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {
            Marcador_Vestuario marcador_vestuario = new Marcador_Vestuario();
            marcador_vestuario.setId_marcador(c.getLong(c.getColumnIndex("id_marcador")));
            marcador_vestuario.setId_vestuario(c.getLong(c.getColumnIndex("id_vestuario")));

            list_marcador_vestuario.add(marcador_vestuario);
        }

        return list_marcador_vestuario;
    }

}
