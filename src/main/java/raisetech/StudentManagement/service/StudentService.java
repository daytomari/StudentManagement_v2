package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.search();
    }

    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(studentsCourses);
        return studentDetail;
    }

    public List<StudentsCourses> seachStudentsCourseList() {
        // 絞り込み検索で「Javaコース」のコース情報のみ抽出する。
        // 抽出したリストをコントローラーに返す
        return repository.searchStudentsCoursesList();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        // TODO:コース情報登録も行う
        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
            studentsCourse.setStudentId(studentDetail.getStudent().getId());
            studentsCourse.setCourseStartAt(LocalDateTime.now());
            studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourses(studentsCourse);
        }
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(studentsCourse);
        }
    }
}
