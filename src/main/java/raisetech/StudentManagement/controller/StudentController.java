package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.seachStudentList();
    List<StudentsCourses> studentsCourses = service.seachStudentsCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    for(Student student :students) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudet(student);

      List<StudentsCourses> convertStudentCourses = new ArrayList<>();
      for(StudentsCourses studentCourses : studentsCourses) {
        if(student.getId().equals(studentCourses.getStudentId())) {
          convertStudentCourses.add(studentCourses);
        }
      }
      studentDetail.setStudentsCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.seachStudentsCourseList();
  }

}
