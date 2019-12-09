package ifsp.doarmario.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.model.vo.Montagem;

public class MontagemDao {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private boolean status;

    public boolean salvar(Montagem montagem) {
        escreve = DbHelper.database.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("data_montagem", montagem.getData() );

        try {
            escreve.insert(DbHelper.TABELA_MONTAGEM, null, cv);
            status = true;
        } catch (Exception e) {
            status = false;
        } finally {
            escreve.close();
        }

        return status;
    }

    public List<Montagem> listar() {
        le = DbHelper.database.getReadableDatabase();
        List<Montagem> montagens = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + ";";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {
            Montagem montagem = new Montagem();
            montagem.setId_montagem(c.getLong(c.getColumnIndex("id_montagem")));
            montagem.setData_montagem(c.getString(c.getColumnIndex("data_montagem")));

            montagens.add(montagem);
        }

        le.close();
        return montagens;
    }

    public Montagem getUltimaMontagem() {
        le = DbHelper.database.getReadableDatabase();
        Montagem montagem = new Montagem();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + ";";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {
            montagem.setId_montagem(c.getLong(c.getColumnIndex("id_montagem")));
            montagem.setData_montagem(c.getString(c.getColumnIndex("data_montagem")));
        }

        le.close();
        return montagem;
    }

    public ArrayList<Montagem> listar_data(String data, String usuario) {
        le = DbHelper.database.getReadableDatabase();

        ArrayList<Montagem> montagens = new ArrayList<>();

        String sql = "" +
                "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + ", " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Montagem_vestuario on Vestuario.id_vestuario = Montagem_vestuario.id_vestuario "
                + " WHERE nome_usuario = '" + usuario + "' and data_montagem LIKE '%" + data + "%';";

        try {
            Cursor c = le.rawQuery(sql, null);

            //c.moveToFirst();
            while (c.moveToNext()) {
                Montagem montagem = new Montagem();

                montagem.setId_montagem(c.getLong(c.getColumnIndex("id_montagem")));
                montagem.setData_montagem(c.getString(c.getColumnIndex("data_montagem")));

                montagens.add(montagem);
            }
        } catch (Exception e) {
            System.err.print(e);
            montagens = null;
        } finally {
            le.close();
        }

        return montagens;
    }

    public ArrayList<Montagem> listar_usuario(String usuario) {
        le = DbHelper.database.getReadableDatabase();

        ArrayList<Montagem> montagens = new ArrayList<>();

        String sql = "" +
                "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + ", " + DbHelper.TABELA_VESTUARIO
                + " INNER JOIN Montagem_vestuario on Vestuario.id_vestuario = Montagem_vestuario.id_vestuario "
                + " INNER JOIN Montagem_vestuario on Montagem.id_montagem = Montagem_vestuario.id_montagem "
                + " WHERE nome_usuario = '" + usuario + "';";

        try {
            Cursor c = le.rawQuery(sql, null);

            //c.moveToFirst();
            while (c.moveToNext()) {
                Montagem montagem = new Montagem();

                montagem.setId_montagem(c.getLong(c.getColumnIndex("id_montagem")));
                montagem.setData_montagem(c.getString(c.getColumnIndex("data_montagem")));

                montagens.add(montagem);
                Log.i("montagem", "id montagem = " + montagem.getId_montagem());
            }
        } catch (Exception e) {
            System.err.print(e);
            montagens = null;
        } finally {
            le.close();
        }

        return montagens;
    }
}
