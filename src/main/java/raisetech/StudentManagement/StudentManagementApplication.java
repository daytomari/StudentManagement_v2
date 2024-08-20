package raisetech.StudentManagement;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	private String name = "Enami Kouji";
	private String age = "37";

	//private Map<String,String> student = new HashMap<>～をいれてみてもいい

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		return student.getName() + " " + student.getAge() + "歳";
	}


	@PostMapping("/student")
	public void registerStudent(String name, int age) {
		repository.registerStudent(name, age);
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public  void updateStudentName(String name) {
		this.name = name;
	}

}

// GET POST
// GETは取得する、リクエストの結果を受け取る
// POSTは情報を与える、渡す
// curl http://localhost:8080/hello
// curl -X POST http://localhost:8080/hello
// → エラー405 POSTメソッド見当たらない