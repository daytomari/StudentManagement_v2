package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StudentsCourses {

    private String id;
    private String studentId;
    private String courseName;
    private Timestamp courseStartAt;
    private Timestamp courseEndAt;


}
