package coatocl.exaatocl.student_details;

import java.util.List;

public class ListModelItemSet
{
    int ID;
    String student_name;
    String student_department;
    String  student_semester;
    String  student_project_name;

//    constructor
    public ListModelItemSet(int ID,String student_name,String student_department,String student_semester,String  student_project_name)
    {
        this.ID=ID;
        this.student_name=student_name;
        this.student_department=student_department;
        this.student_semester=student_semester;
        this.student_project_name=student_project_name;
    }


//    only done getter for variable

//    ID
    public int getID() { return ID; }

    //    name
    public String getStudent_name()
    {
        return student_name;
    }

//    department
    public String getStudent_department()
    {
        return student_department;
    }

//    semester
    public String getStudent_semester()
    {
        return student_semester;
    }

//    project_name
    public String getStudent_project_name()
    {
        return student_project_name;
    }

}
