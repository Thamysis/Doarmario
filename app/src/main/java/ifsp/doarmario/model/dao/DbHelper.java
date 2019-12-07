package ifsp.doarmario.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

import ifsp.doarmario.R;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DB_NAME = "DB_DOARMARIO";
    public static String TABELA_USUARIO = "usuario";
    public static String TABELA_VESTUARIO = "vestuario";
    public static String TABELA_CATEGORIA = "categoria";
    public static String TABELA_COR = "cor";
    public static String TABELA_MARCADOR = "marcador";
    public static String TABELA_MONTAGEM = "montagem";
    public static String TABELA_MONTAGEM_VESTUARIO = "montagem_vestuario";
    public static Context context;
    public static DbHelper database;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criação das tabelas
        //tabela usuário
        String sql_create_Usuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_USUARIO
                + "("
                + "nome_usuario VARCHAR(20) PRIMARY KEY, "
                + "email VARCHAR(100) NOT NULL, "
                + "senha VARCHAR(100) NOT NULL"
                + ");";
        //tabela cor
        String sql_create_Cor = "CREATE TABLE IF NOT EXISTS "
                + TABELA_COR
                + "( "
                + "id_cor INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_cor TEXT NOT NULL "
                + ");";
        //tabela categoria
        String sql_create_Categoria = "CREATE TABLE IF NOT EXISTS "
                + TABELA_CATEGORIA
                + "( "
                + "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_categoria TEXT NOT NULL, "
                + "tipo_categoria TEXT NOT NULL "
                + ");";
        //tabela marcador
        String sql_create_Marcador = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MARCADOR
                + "( "
                + "id_marcador INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_marcador TEXT NOT NULL "
                + ");";
        //tabela vestuario
        String sql_create_Vestuario = "CREATE TABLE IF NOT EXISTS "
                + TABELA_VESTUARIO
                + "( "
                + "id_vestuario INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descricao_vestuario TEXT NOT NULL, "
                + "imagem_vestuario TEXT NOT NULL,"
                + "status_doacao TEXT NOT NULL,"
                + "id_cor INTEGER,"
                + "id_categoria INTEGER,"
                + "id_marcador INTEGER,"
                + "nome_usuario TEXT NOT NULL,"
                + "FOREIGN KEY(id_categoria) REFERENCES categoria(id_categoria),"
                + "FOREIGN KEY(id_cor) REFERENCES categoria(id_cor),"
                + "FOREIGN KEY(id_marcador) REFERENCES categoria(id_marcador)"
                + ");";
        //tabela montagem
        String sql_create_Montagem = "CREATE TABLE IF NOT EXISTS "
                + TABELA_MONTAGEM
                + "("
                + "id_montagem INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "data_montagem TEXT NOT NULL"
                + ");";
        //tabela montagem_vestuario
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
            db.execSQL(sql_create_Montagem);
            db.execSQL(sql_create_Montagem_Vestuario);

            Log.i("INFO_DB", "Tabelas criadas com sucesso!");
        } catch (Exception e) {
            Log.i("INFO_DB", "Erro ao criar tabelas" + e.getMessage());
        }

        cargaInicial(db);
    }

    private static void cargaInicial(SQLiteDatabase db){
        String insert = "";

        try {
            //COR
            String[] cores = context.getResources().getStringArray(R.array.cor);
            for (String cor : cores) {
                insert = " INSERT INTO " + DbHelper.TABELA_COR
                        + " ( " + " descricao_cor " + " ) "
                        + " VALUES " + "('" + cor + "');";
                db.execSQL(insert);
            }

            //MARCADORES
            String[] marcadores = context.getResources().getStringArray(R.array.marcador);
            for (String marcador : marcadores) {
                insert = " INSERT INTO " + DbHelper.TABELA_MARCADOR
                        + " ( " + " descricao_marcador " + " ) "
                        + " VALUES " + "('" + marcador + "');";;
                db.execSQL(insert);
            }

            //CATEGORIAS
            String[] tipos = {"roupa_de_cima",  "roupa_de_baixo", "calcado", "peca_unica", "acessorio"};

            HashMap<String, String[]> categorias = new HashMap<>();
            categorias.put("roupa_de_cima", context.getResources().getStringArray(R.array.roupa_de_cima));
            categorias.put("roupa_de_baixo", context.getResources().getStringArray(R.array.roupa_de_baixo));
            categorias.put("calcado", context.getResources().getStringArray(R.array.calcado));
            categorias.put("peca_unica", context.getResources().getStringArray(R.array.peca_unica));
            categorias.put("acessorio", context.getResources().getStringArray(R.array.acessorio));


            for(String tipo: tipos) {
                String[] itens = categorias.get(tipo);
                for (String item : itens) {
                    insert = " INSERT INTO " + DbHelper.TABELA_CATEGORIA
                            + " ( " + " descricao_categoria, tipo_categoria " + " ) "
                            + " VALUES " + "('" + item + "', '" + tipo + "');";
                    db.execSQL(insert);
                }
            }

            Log.i("INFO_DB", "Inserções realizadas com sucesso!");
        } catch (Exception e) {
            Log.i("INFO_DB", "Erro ao realizar inserções." + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_Usuario = "DROP TABLE IF EXISTS " + TABELA_USUARIO + " ;" ;
        String drop_Vestuario = "DROP TABLE IF EXISTS " + TABELA_VESTUARIO + " ;" ;
        String drop_Categoria = "DROP TABLE IF EXISTS " + TABELA_CATEGORIA + " ;" ;
        String drop_Cor = "DROP TABLE IF EXISTS " + TABELA_COR + " ;" ;
        String drop_Marcador = "DROP TABLE IF EXISTS " + TABELA_MARCADOR + " ;" ;
        String drop_Montagem = "DROP TABLE IF EXISTS " + TABELA_MONTAGEM + " ;" ;
        String drop_Montagem_Vestuario = "DROP TABLE IF EXISTS " + TABELA_MONTAGEM_VESTUARIO + " ;" ;

        try {
            db.execSQL(drop_Usuario);
            db.execSQL(drop_Vestuario);
            db.execSQL(drop_Categoria);
            db.execSQL(drop_Cor);
            db.execSQL(drop_Marcador);
            db.execSQL(drop_Montagem);
            db.execSQL(drop_Montagem_Vestuario);

            onCreate(db);
            Log.i("INFO_DB", "Sucesso ao atualizar App" );
        }catch (Exception e){
            Log.i("INFO_DB", "Erro ao atualizar App" + e.getMessage() );
        }
    }
}