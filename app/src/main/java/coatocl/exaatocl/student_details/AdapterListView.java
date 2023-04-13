package coatocl.exaatocl.student_details;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterListView extends ArrayAdapter<ListModelItemSet> {

    List<ListModelItemSet>ListDataItem;
    SQLiteDatabase database;
    Context context;
    int resource;

    //    constructor
    public AdapterListView( Context context, int resource,List<ListModelItemSet>ListDataItem, SQLiteDatabase database)
    {
        super(context, resource, ListDataItem);
        this.context=context;
        this.ListDataItem=ListDataItem;
        this.resource=resource;
        this.database = database;
    }


//    override method for getting data from partoflistview to set for listmodeldata
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(resource,null,false);


//        here instance take from partoflistview
        TextView Name=view.findViewById(R.id.name);
        TextView Department=view.findViewById(R.id.department);
        TextView Semester=view.findViewById(R.id.semester);
        TextView Project_name=view.findViewById(R.id.project_name);
        Button Insert=view.findViewById(R.id.b1);
        Button Delete=view.findViewById(R.id.b2);

//        set value from main activity that data at particular place in sequence
        ListModelItemSet listmodelitemSet =ListDataItem.get(position);


        Name.setText(listmodelitemSet.student_name);
        Department.setText(listmodelitemSet.student_department);
//        add "+" whenever set int to any where
        Semester.setText(listmodelitemSet.student_semester);
        Project_name.setText((listmodelitemSet.student_project_name));


//    for delete button to delete whole data of that particular part from database
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog .Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("DELETE DATA");
                builder.setMessage("R you sure to delete this data?");
                builder.setPositiveButton("YES", (dialog, which) -> {

                        String sql="DELETE FROM Student WHERE id=?";
                        database.execSQL(sql,new  Integer[]{listmodelitemSet.getID()});
                    reloadStudentsFromDatabase();
                });
            builder.setNegativeButton("NO", (dialog, which) -> {

            });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });


        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                updateStudent(listmodelitemSet);
            }
        });

       return view;
    }

//    for update our listview's item
    private void updateStudent(ListModelItemSet listmodel)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.edit,null);
        builder.setView(view);

           EditText eName=view.findViewById(R.id.ename);
           Spinner eDepartment=view.findViewById(R.id.edepartment);
           Spinner eSemester=view.findViewById(R.id.esemester);
           EditText eProject_name=view.findViewById(R.id.eproject);
           TextView ok=view.findViewById(R.id.eview_detail);

           eName.setText(listmodel.student_name);
           eProject_name.setText(listmodel.student_project_name);

           AlertDialog dialog=builder.create();
           dialog.show();


           ok.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String ssname = eName.getText().toString();
                   String ssproject = eProject_name.getText().toString();
                   String ssdepartment = eDepartment.getSelectedItem().toString();
                   String sssemester = eSemester.getSelectedItem().toString();

                   if(ssname.isEmpty())
                   {
                       eName.setError("Filled up.");
                       return;
                   }
                   else if (ssproject.isEmpty())
                   {
                       eProject_name.setError("Filled up.");
                       return;
                   }

//            SQLite for update

                   String sql = "UPDATE Student SET sname = ?,"+ "adddepartment = ?," + "addsemester= ?," + "pname=?" + " WHERE id = ?;";

//                        Log.d("CheckSQl",""+listmodel.getID());

                       database.execSQL(sql, new String[]{ssname, ssdepartment, ssproject,sssemester, String.valueOf(listmodel.getID())});;
                       Toast.makeText(context,"Updated DATA GONE to DATABASE",Toast.LENGTH_LONG).show();
                   reloadStudentsFromDatabase();
                   dialog.dismiss();
               }
           });
    }

    private void reloadStudentsFromDatabase() {

//        Student indicates table name
        Cursor cursorStudent = database.rawQuery("SELECT*FROM Student", null);
        if (cursorStudent.moveToFirst()) {
            ListDataItem.clear();
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
        notifyDataSetChanged();
    }

}



