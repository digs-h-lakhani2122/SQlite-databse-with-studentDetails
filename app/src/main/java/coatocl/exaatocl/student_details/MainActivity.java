package coatocl.exaatocl.student_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    indicate database name
    static String DATABASE_NAME = "Student_Details";
    //    indicate table name
    String TABLE_NAME = "Student";
    SQLiteDatabase database;

    EditText name, project;
    TextView view_details;
    Spinner department, semester;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        make instance for all
        name = findViewById(R.id.name);
        project = findViewById(R.id.project);
        view_details = findViewById(R.id.view_detail);
        button = findViewById(R.id.button);
        department = findViewById(R.id.department);
        semester = findViewById(R.id.semester);

//        moved data for database
        button.setOnClickListener(this);
        view_details.setOnClickListener(this);

//        spinner adapter for department
        ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Department, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        department.setAdapter(arrayAdapter);

//        spinner adapter for semester
        ArrayAdapter<?> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.Semester, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        semester.setAdapter(arrayAdapter1);

//        database open or on create method
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

//have to call data from database
        createStudent();
//        addStudent();

    }



    //    make other method to check whether data filled up or not
    private boolean checked(String sname, String sproject)
    {

        if(sname.isEmpty())
        {
            name.setError("Filled up.");
            return false;
        }
        else if (sproject.isEmpty())
        {
            project.setError("Filled up.");
            return false;
        }
        return true;
    }

    @Override
    public void onClick (View v)
    {

        switch (v.getId()) {
            case R.id.button:
                addStudent();
                break;
                
            case R.id.view_detail:
                startActivity(new Intent(this, listview.class));
                break;
        }
    }

    private void createStudent()

    {

//        compulsory give table name whenever we create table bcz table name & database name are different
//        one database has many tables.
//        id must be not null or u have to indicate this at autoincrement or else u have to indicate this at
//
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id INTEGER CONSTRAINT student_pk PRIMARY KEY AUTOINCREMENT,sname varchar(100) NOT NULL," +
                "pname varchar(100) NOT NULL,adddepartment varchar(100) NOT NULL,addsemester varchar(100) NOT NULL ) ");

    }



    private void addStudent() {

        String sname = name.getText().toString();
        String sproject = project.getText().toString();
        String sdepartment = department.getSelectedItem().toString();
        String ssemester = semester.getSelectedItem().toString();

        if (checked(sname, sproject)) {
//            SQLite for insert
            String sql = "INSERT INTO Student" + "(sname,adddepartment,addsemester,pname)" + "VALUES" + "(?,?,?,?);";
            database.execSQL(sql, new String[]{sname, sdepartment, ssemester, sproject});
            Toast.makeText(this, "DATA GONE DATABASE", Toast.LENGTH_LONG).show();
    }

}
}

