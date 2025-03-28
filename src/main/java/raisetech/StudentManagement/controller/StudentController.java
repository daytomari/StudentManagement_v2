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

@RestController //Rest化 ※Controller→ResController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.seachStudentsCourseList();
        return converter.convertStudentDetails(students, studentsCourses);
    }

    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable String id) {
        return service.searchStudent(id);
    }

//    @GetMapping("/studentsCourseList")
//    public List<StudentsCourses> getStudentsCourseList() {
//        return service.seachStudentsCourseList();
//    }

//    @GetMapping("/newStudent") //Rest化に伴いPOSTMAN登録後に削除
//    public String newStudent(Model model) {
//        StudentDetail studentDetail = new StudentDetail();
//        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
//        model.addAttribute("studentDetail", studentDetail);
//        return "registerStudent";
//    }

    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

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
