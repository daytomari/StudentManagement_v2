package raisetech.StudentManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    private Student student;
    private List<StudentCourse> studentCourseList;

}
