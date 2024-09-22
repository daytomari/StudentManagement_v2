package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> seachStudentList() {
        // 検索処理
        repository.search();

        // 絞り込みをする。年齢が30代の人のみ抽出する。
        // 抽出したリストをコントローラーに返す

        return repository.search();
    }

    public List<StudentsCourses> seachStudentsCourseList() {
        // 絞り込み検索で「Javaコース」のコース情報のみ抽出する。
        // 抽出したリストをコントローラーに返す

        return repository.searchStudentsCourses();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        // TODO:コース情報登録も行う
    }
}
