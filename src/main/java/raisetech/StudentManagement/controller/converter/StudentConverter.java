package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生詳細を受講生と受講生コース情報、もしくはその逆の返還を行うコンバーターです。
 */
@Component
public class StudentConverter {

    /**
     * 受講生紐づく受講生コース情報をマッピングする。
     * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
     *
     * @param studentList 受講生一覧
     * @param studentCourseList 受講生コース情報のリスト
     * @return 受講生報歳情報のリスト
     */
    public List<StudentDetail> convertStudentDetails(List<Student> studentList,
                                                     List<StudentCourse> studentCourseList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        studentList.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
                    .filter(studentCourses -> student.getId().equals(studentCourses.getStudentId()))
                    .collect(Collectors.toList());
            studentDetail.setStudentCourseList(convertStudentCourseList);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }
}
