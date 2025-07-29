package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before(){
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){ // 元の名前：受講生詳細の一覧検索_全件検索が動作すること
        // searchStudentListのテスト

        // 事前準備
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();

        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        // 実行
        sut.searchStudentList();

        //検証
        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

        // 後処理
        // ここでDBをもとに戻すなど
    }

    @Test
    void 受講生詳細検索_IDに紐づく受講生情報とコース情報が取得できること(){
        // 課題27 searchStudentのテスト

        // 事前データ
        String studentId = "student-001";
        Student mockStudent = new Student();
        mockStudent.setId(studentId);

        StudentCourse course1 = new StudentCourse();
        StudentCourse course2 = new StudentCourse();
        List<StudentCourse> mockCourseList = Arrays.asList(course1, course2);

        //モック定義
        when(repository.searchStudent(studentId)).thenReturn(mockStudent);
        when(repository.searchStudentCourse(studentId)).thenReturn(mockCourseList);

        // 実行
        StudentDetail result = sut.searchStudent(studentId);

        // 検証
        verify(repository, times(1)).searchStudent(studentId);
        verify(repository, times(1)).searchStudentCourse(studentId);

        assertEquals(mockStudent, result.getStudent());
        assertEquals(mockCourseList, result.getStudentCourseList());

    }
}