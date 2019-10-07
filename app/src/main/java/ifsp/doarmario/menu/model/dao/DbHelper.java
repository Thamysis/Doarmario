package ifsp.doarmario.menu.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DB_NAME = "DB_DOARMARIO";
    public static String TABELA_USUARIO = "usuario";
    public static String TABELA_VESTUARIO = "vestuario";
    public static String TABELA_CATEGORIA = "categoria";
    public static String TABELA_COR = "cor";
    public static String TABELA_MARCADOR = "marcador";
    public static String TABELA_MARCADOR_VESTUARIO = "marcador_vestuario";
    public static String TABELA_MONTAGEM = "montagem";
    public static String TABELA_MONTAGEM_VESTUARIO = "montagem_vestuario";

    public static DbHelper database;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criação das tabelas
        String sql_create_Usuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_USUARIO
                + "("
                + "nome_usuario VARCHAR(20) PRIMARY KEY, "
                + "email VARCHAR(100) NOT NULL, "
                + "senha VARCHAR(100) NOT NULL"
                + ");";

        String sql_create_Cor = "CREATE TABLE IF NOT EXISTS "
                + TABELA_COR
                + "( "
                + "id_cor INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_cor TEXT NOT NULL "
                + ");";
        String sql_create_Categoria = "CREATE TABLE IF NOT EXISTS "
                + TABELA_CATEGORIA
                + "( "
                + "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_categoria TEXT NOT NULL, "
                + "tipo_categoria TEXT NOT NULL "
                + ");";
        String sql_create_Marcador = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MARCADOR
                + "( "
                + "id_marcador INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_marcador TEXT NOT NULL "
                + ");";

        String sql_create_Vestuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_VESTUARIO
                + "( "
                + "id_vestuario INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_vestuario TEXT NOT NULL, "
                + "imagem_vestuario TEXT NOT NULL,"
                + "status_doacao TEXT NOT NULL,"
                + "id_cor INTENGER,"
                + "id_categoria INTENGER,"
                + "id_marcador INTENGER,"
                + "nome_usuario TEXT NOT NULL"
                + ");";

        String sql_create_Marcador_Vestuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MARCADOR_VESTUARIO
                + "("
                + "id_marcador INTEGER NOT NULL,"
                + "id_vestuario INTEGER NOT NULL,"
                + "FOREIGN KEY(id_marcador) REFERENCES marcador(id_marcador),"
                + "FOREIGN KEY(id_vestuario) REFERENCES vestuario(id_vestuario)"
                + ");";

        String sql_create_Montagem = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MONTAGEM
                + "("
                + "id_montagem INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "data_montagem TEXT NOT NULL"
                + ");";

        String sql_create_Montagem_Vestuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MONTAGEM_VESTUARIO
                + " ("
                + "id_montagem INTEGER NOT NULL, "
                + "id_vestuario INTEGER NOT NULL,"
                + "FOREIGN KEY(id_montagem) REFERENCES montagem(id_montagem),"
                + "FOREIGN KEY(id_vestuario) REFERENCES vestuario(id_vestuario)"
                + ");";

        try {
            db.execSQL(sql_create_Usuario);
            db.execSQL(sql_create_Categoria);
            db.execSQL(sql_create_Cor);
            db.execSQL(sql_create_Marcador);
            db.execSQL(sql_create_Vestuario);
            db.execSQL(sql_create_Marcador_Vestuario);
            db.execSQL(sql_create_Montagem);
            db.execSQL(sql_create_Montagem_Vestuario);

            Log.i("", "Tabelas criadas com sucesso!");
        } catch (Exception e) {
            Log.i("excecao", "excecao" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_Usuario = "DROP TABLE IF EXISTS " + TABELA_USUARIO + " ;" ;
        String drop_Vestuario = "DROP TABLE IF EXISTS " + TABELA_VESTUARIO + " ;" ;
        String drop_Categoria = "DROP TABLE IF EXISTS " + TABELA_CATEGORIA + " ;" ;
        String drop_Cor = "DROP TABLE IF EXISTS " + TABELA_COR + " ;" ;
        String drop_Marcador = "DROP TABLE IF EXISTS " + TABELA_MARCADOR + " ;" ;
        String drop_Marcador_Vestuario = "DROP TABLE IF EXISTS " + TABELA_MARCADOR_VESTUARIO + " ;" ;
        String drop_Montagem = "DROP TABLE IF EXISTS " + TABELA_MONTAGEM + " ;" ;
        String drop_Montagem_Vestuario = "DROP TABLE IF EXISTS " + TABELA_MONTAGEM_VESTUARIO + " ;" ;

        try {
            db.execSQL(drop_Usuario);
            db.execSQL(drop_Vestuario);
            db.execSQL(drop_Categoria);
            db.execSQL(drop_Cor);
            db.execSQL(drop_Marcador);
            db.execSQL(drop_Marcador_Vestuario);
            db.execSQL(drop_Montagem);
            db.execSQL(drop_Montagem_Vestuario);

            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage() );
        }
    }
}
