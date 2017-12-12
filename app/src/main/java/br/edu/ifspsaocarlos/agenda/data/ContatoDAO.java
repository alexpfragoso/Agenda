package br.edu.ifspsaocarlos.agenda.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.agenda.model.Contato;

/**
 * Created by Alex Fragoso on 11/12/2017.
 */

public class ContatoDAO {

    //VARIAVEIS

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    //METODOS
    //Método para buscar todos os contatos do banco de dados e
    //armazenar em uma lista
    public List<Contato> buscaTodosContatos(){

        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME,SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null, null, null, null, SQLiteHelper.KEY_NAME);

        while(cursor.moveToNext()) {

            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contatos.add(contato);
        }
        cursor.close();
        database.close();
        return contatos;
    }

    //Metodo para buscar apenas um contato especifico

    public List<Contato> buscaContato(String nome){

        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor;

        String[] cols = new String[] {SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME,SQLiteHelper.KEY_FONE,SQLiteHelper.KEY_EMAIL};

        String where = SQLiteHelper.KEY_NAME + " like ?";
        String[] argWhere = new String[]{nome + "%"};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where, argWhere, null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext()){

            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contatos.add(contato);
        }
        cursor.close();
        database.close();

        return contatos;
    }

    //Metodo para salvar um novo contato no banco de dados
    public void salvaContato(Contato c) {

        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());

        if (c.getId() > 0) {
            database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "=" + c.getId(), null);
        } else {
            database.insert(SQLiteHelper.DATABASE_TABLE, null, values);
        }

        database.close();
    }

    //Metodo para apagar um contato do banco de dados
    public void apagaContato(Contato c){

        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "=" + c.getId(),null);
        database.close();

    }



}
