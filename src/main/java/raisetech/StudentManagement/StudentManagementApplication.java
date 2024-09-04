package raisetech.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.search();
	}

	@GetMapping("/studentsCourseList")
	public List<StudentsCourses> getStudentsCourseList() {
		return repository.searchStudentsCourses();
	}




}

// GET POST
// GETは取得する、リクエストの結果を受け取る
// POSTは情報を与える、渡す
// curl http://localhost:8080/hello
// curl -X POST http://localhost:8080/hello
// → エラー405 POSTメソッド見当たらない