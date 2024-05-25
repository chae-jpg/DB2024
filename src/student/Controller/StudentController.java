package student.Controller;


import student.DAO.QueryingDAO;
import student.DAO.UpdatingDAO;
import student.domain.StudentDomain;

import java.util.List;


public class StudentController {
    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;


    public StudentController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    // 학생 조회
    public String getStudent() {
        List<StudentDomain> students = queryingDAO.findStudent();
        // JSON 형식의 문자열로 반환
        return "{\"students\": " + students.toString() + "}";
    }
    // 학생 추가
    public Long addStudent(StudentDomain student) throws Exception {
        return updatingDAO.insertStudent(student);
    }

    // 학생 삭제
    public void deleteStudent(Long id) throws Exception {
        updatingDAO.deleteStudent(id);
    }


}

