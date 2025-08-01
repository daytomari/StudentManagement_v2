package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@Validated
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
     * 受講生詳細の一覧検索です。
     * 全件検索を行なうので、条件指定は行いません。
     *
     * @return 受講生詳細一覧（全件）
     */
    @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() throws TestException {
        throw new TestException(
                "現在このAPIは利用できません。URLは「studentList」ではなく「students」を利用してください。");
    }

    /**
     * 受講生詳細の検索です。
     * IDに基づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
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
    @Operation(summary = "受講生登録", description = "受講生を登録します。")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います（論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PutMapping("/updateStudent")
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

    @ExceptionHandler(TestException.class)
    public ResponseEntity<String> handleTestException(TestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
