package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchStudentsCourses();

    @Update("UPDATE student SET age = #{name} WHERE name = #{name}")
    void updateStudent(String name, int age);

    @Delete("DELETE FROM student WHERE name = #{name}")
    void deleteStudent(String name);

}
