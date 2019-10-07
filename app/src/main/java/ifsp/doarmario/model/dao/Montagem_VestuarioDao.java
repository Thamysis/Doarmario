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
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public static Montagem_VestuarioDao montagem_VestuarioDao;

    public Montagem_VestuarioDao(Context context) {
        write = DbHelper.database.getWritableDatabase();
        read = DbHelper.database.getReadableDatabase();
    }

    public boolean salvar(Montagem_Vestuario montagem_vestuario) {
        ContentValues cv = new ContentValues();
        cv.put("id_montagem", montagem_vestuario.getId_montagem());
        cv.put("id_vestuario", montagem_vestuario.getId_vestuario());

        //o segundo parâmetro é para ter uma coluna específica como null
        try {
            write.insert(DbHelper.TABELA_MONTAGEM, null, cv);
        } catch (Exception e) {
            Log.i("", "excecao " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<Montagem_Vestuario> listar(Long id_montagem) {
        List<Montagem_Vestuario> montagem_vestuarios = new ArrayList<>();

        //OBTER LISTA DE MONTAGEM_VESTUARIO
        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM_VESTUARIO + " WHERE id_montagem = ?";

        try {
            Cursor c = read.rawQuery(sql, new String[]{id_montagem + ""});

            c.moveToFirst();

            //percorrer a listagem
            while (c.moveToNext()) {
                Montagem_Vestuario montagem_vestuario = new Montagem_Vestuario();
                int colIdMontagem = c.getInt(c.getColumnIndex("id_montagem"));
                int colIdVestuario = c.getInt(c.getColumnIndex("id_vestuario"));

                montagem_vestuario.setId_montagem(c.getLong(colIdMontagem));
                montagem_vestuario.setId_vestuario(c.getLong(colIdVestuario));

                montagem_vestuarios.add(montagem_vestuario);
            }

            Log.i("", "Montagem_Vestuario listada com sucesso");
        } catch (Exception e) {
            Log.i("", "Exceção " + e.getMessage());
        }

        return montagem_vestuarios;
    }

    public List<Vestuario> listarVestuarios(List<Montagem_Vestuario> list_montagem_vestuario) {
        List<Vestuario> vestuarios = new ArrayList<>();

        //PERCORRER LISTA DE MONTAGEM_VESTUARIO
        //BUSCAR CADA VESTUARIO DE ACORDO COM SEU RESPECTIVO ID
        //RETORNAR LISTA COM TODOS VESTUARIO DA MONTAGEM

        return vestuarios;
    }

    public boolean deletar(Long id_montagem) {
        try {
            String[] argumentos = {id_montagem + ""};
            write.delete(DbHelper.TABELA_MONTAGEM_VESTUARIO, "id_montagem = ?", argumentos );
            Log.i("INFO", "Montagem_Vestuario removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover montagem_vestuario" + e.getMessage() );
            return false;
        }

        return true;
    }

}
