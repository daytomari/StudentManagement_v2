package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生情報の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

    private StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
        return converter.convertStudentDetails(studentList, studentsCoursesList);
    }

    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
        return new StudentDetail(student, studentsCourses);
    }

// 第38回 リファクタリングの際に削除
//    public List<StudentsCourses> seachStudentsCourseList() {
//        // 絞り込み検索で「Javaコース」のコース情報のみ抽出する。
//        // 抽出したリストをコントローラーに返す
//        return repository.searchStudentsCoursesList();
//    }

    /**
     * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
            studentsCourse.setStudentId(studentDetail.getStudent().getId());
            studentsCourse.setCourseStartAt(LocalDateTime.now());
            studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourses(studentsCourse);
        }
        return studentDetail;
    }

    /**
     * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
     *
     * @param studentDetail 受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(studentsCourse);
        }
    }
}
