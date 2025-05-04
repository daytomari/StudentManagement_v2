package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@RestController //Rest化 ※Controller→ResController
public class StudentController {

    private StudentService service;

    /**
     * コンストラクタ
     *
     * @param service 受講性サービス
     */
    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行なうので、条件指定は行いません。
     *
     * @return 受講生一覧（全件）
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     * 受講生検索です。
     * IDに基づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable String id) {
        return service.searchStudent(id);
    }

//    @GetMapping("/studentsCourseList")
//    public List<StudentsCourses> getStudentsCourseList() {
//        return service.seachStudentsCourseList();
//    }

//    @GetMapping("/newStudent") //第38回 Rest化に伴いPOSTMAN登録後に削除
//    public String newStudent(Model model) {
//        StudentDetail studentDetail = new StudentDetail();
//        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
//        model.addAttribute("studentDetail", studentDetail);
//        return "registerStudent";
//    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います（論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }

//    @PostMapping("/updateStudent")
//    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
//        if (result.hasErrors()) {
//            return "updateStudent";
//        }
//        service.updateStudent(studentDetail);
//        return "redirect:/studentList";
//    }

}
