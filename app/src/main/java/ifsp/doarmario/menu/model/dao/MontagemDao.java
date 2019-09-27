package ifsp.doarmario.menu.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.menu.model.vo.Montagem;

public class MontagemDao {
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public MontagemDao(Context context) {
        //DbHelper db = new DbHelper(context);
        write = DbHelper.database.getWritableDatabase();
        read = DbHelper.database.getReadableDatabase();

        //Usuario usuarioTeste = new Usuario("yasmin_deodato", "yasmin.deodato.beatriz@gmail.com", "yasmin123");
        //salvar(usuarioTeste);
    }

    public boolean salvar(Montagem montagem) {
        ContentValues cv = new ContentValues();
        cv.put("id_montagem", montagem.getId_montagem());
        cv.put("data_montagem", montagem.getData());
        //cv.put("data_montagem" + montagem.converterData(montagem.getData_montagem()));

        //o segundo parâmetro é para ter uma coluna específica como null
        try {
            write.insert(DbHelper.TABELA_MONTAGEM, null, cv);
        } catch (Exception e) {
            Log.i("", "excecao " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<Montagem> listar() {
        List<Montagem> montagens = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + ";";

        //segundo parâmetro são filtros
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            Montagem montagem = new Montagem();
            montagem.setId_montagem(c.getLong(c.getColumnIndex("id_montagem")));
            //montagem.setData_montagem(montagem.converterDataStr(c.getString(c.getColumnIndex("data_montagem"))));

            montagens.add(montagem);
        }

        return montagens;
    }

    public Montagem detalhar(Long id_montagem) {
        Montagem montagem = new Montagem();

        String sql = "SELECT * FROM " + DbHelper.TABELA_MONTAGEM + " WHERE id_montagem = ?";

        //segundo parâmetro são filtros
        try {
            Cursor c = read.rawQuery(sql, new String[]{id_montagem + ""});

            int colIdMontagem = c.getColumnIndex("id_montagem");
            int colDataMontagem = c.getColumnIndex("data_montagem");

            c.moveToFirst();
            montagem.setId_montagem(c.getLong(colIdMontagem));
            //montagem.setData_montagem(montagem.converterDataStr(c.getString(colDataMontagem)));

            Log.i("", "Montagem listada com sucesso");
        } catch (Exception e) {
            Log.i("", "Exceção " + e.getMessage());
        }

        return montagem;
    }

    public boolean deletar(Montagem montagem) {
        try {
            String[] argumentos = {montagem.getId_montagem() + ""};
            //REMOVER ANTES DA TABEsLA MONTAGEM_VESTUARIO
            //Montagem_VestuarioDao.montagem_VestuarioDao.deletar(montagem.getId_montagem());

            write.delete(DbHelper.TABELA_MONTAGEM, "id_montagem = ?", argumentos );
            Log.i("INFO", "Montagem removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover montagem " + e.getMessage() );
            return false;
        }

        return true;
    }
}
