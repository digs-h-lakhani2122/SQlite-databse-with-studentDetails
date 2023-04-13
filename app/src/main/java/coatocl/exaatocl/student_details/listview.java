package coatocl.exaatocl.student_details;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listview extends AppCompatActivity
{
    List<ListModelItemSet>ListDataItem;
    SQLiteDatabase database;
    ListView listView;
    AdapterListView adapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView=findViewById(R.id.listview);

        ListDataItem=new ArrayList<>();

        database = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        showStudentsFromDatabase();

    }

    private void showStudentsFromDatabase()
    {
//        student indicates table name
        Cursor cursorStudent=database.rawQuery("SELECT*FROM Student",null);
        if(cursorStudent.moveToFirst())
        {
            do {
//                    ListDataItem.add((new ListModelItemSet(
                ListDataItem.add(new ListModelItemSet(
                        cursorStudent.getInt(0),
                        cursorStudent.getString(1),
                        cursorStudent.getString(2),
                        cursorStudent.getString(3),
                        cursorStudent.getString(4)
                ));
            }
                while (cursorStudent.moveToNext());
        }
        cursorStudent.close();


        adapterListView =new AdapterListView(this,R.layout.partoflistview,ListDataItem,database);
        listView.setAdapter(adapterListView);

    }
}
