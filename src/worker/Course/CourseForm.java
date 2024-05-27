package worker.Course;

import worker.Course.Course;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 강의 등록 및 수정을 위한 폼을 제공하는 클래스
public class CourseForm extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtCourseID;
    private JTextField txtCourseName;
    private JTextField txtClassroom;
    private JTextField txtCredit;
    private JTextField txtSemester;
    private JTextField txtDay;
    private JTextField txtTime;
    private JTextField txtProfessorID;
    private JButton btnSubmit;
    private JButton btnCancel;
    private Course course;
    private boolean isEditMode;

    // 생성자: 부모 프레임, 강의 객체, 수정 모드 여부를 인자로 받음
    public CourseForm(Frame parent, Course course, boolean isEditMode) {
        super(parent, true);
        this.course = course;
        this.isEditMode = isEditMode;
        initialize();
    }

    // 폼 초기화 메서드
    private void initialize() {
        setTitle(isEditMode ? "강의 수정" : "강의 등록"); // 다이얼로그 제목 설정
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new GridLayout(10, 2, 10, 10)); // 그리드 레이아웃 설정

        // 강의 ID 입력 필드 설정
        JLabel lblCourseID = new JLabel("강의 ID:");
        getContentPane().add(lblCourseID);

        txtCourseID = new JTextField();
        getContentPane().add(txtCourseID);
        txtCourseID.setColumns(10);
        txtCourseID.setText(course != null ? String.valueOf(course.getCourseID()) : "");
        txtCourseID.setEditable(!isEditMode); // 수정 모드일 경우 강의 ID는 수정 불가

        // 강의명 입력 필드 설정
        JLabel lblCourseName = new JLabel("강의명:");
        getContentPane().add(lblCourseName);

        txtCourseName = new JTextField();
        getContentPane().add(txtCourseName);
        txtCourseName.setColumns(10);
        txtCourseName.setText(course != null ? course.getCourseName() : "");

        // 강의실 입력 필드 설정
        JLabel lblClassroom = new JLabel("강의실:");
        getContentPane().add(lblClassroom);

        txtClassroom = new JTextField();
        getContentPane().add(txtClassroom);
        txtClassroom.setColumns(10);
        txtClassroom.setText(course != null ? course.getClassroom() : "");

        // 학점 입력 필드 설정
        JLabel lblCredit = new JLabel("학점:");
        getContentPane().add(lblCredit);

        txtCredit = new JTextField();
        getContentPane().add(txtCredit);
        txtCredit.setColumns(10);
        txtCredit.setText(course != null ? String.valueOf(course.getCredit()) : "");

        // 학기 입력 필드 설정
        JLabel lblSemester = new JLabel("학기:");
        getContentPane().add(lblSemester);

        txtSemester = new JTextField();
        getContentPane().add(txtSemester);
        txtSemester.setColumns(10);
        txtSemester.setText(course != null ? course.getSemester() : "");

        // 요일 입력 필드 설정
        JLabel lblDay = new JLabel("요일:");
        getContentPane().add(lblDay);

        txtDay = new JTextField();
        getContentPane().add(txtDay);
        txtDay.setColumns(10);
        txtDay.setText(course != null ? course.getDay() : "");

        // 시간 입력 필드 설정
        JLabel lblTime = new JLabel("시간:");
        getContentPane().add(lblTime);

        txtTime = new JTextField();
        getContentPane().add(txtTime);
        txtTime.setColumns(10);
        txtTime.setText(course != null ? course.getTime() : "");

        // 교수 ID 입력 필드 설정
        JLabel lblProfessorID = new JLabel("교수 ID:");
        getContentPane().add(lblProfessorID);

        txtProfessorID = new JTextField();
        getContentPane().add(txtProfessorID);
        txtProfessorID.setColumns(10);
        txtProfessorID.setText(course != null ? String.valueOf(course.getProfessorID()) : "");

        // 등록/수정 버튼 설정
        btnSubmit = new JButton(isEditMode ? "수정" : "등록");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit(); // 버튼 클릭 시 제출 메서드 호출
            }
        });
        getContentPane().add(btnSubmit);

        // 취소 버튼 설정
        btnCancel = new JButton("취소");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 버튼 클릭 시 다이얼로그 닫기
            }
        });
        getContentPane().add(btnCancel);

        pack(); // 다이얼로그 크기를 내용물에 맞게 조정
        setLocationRelativeTo(getParent()); // 부모 프레임을 기준으로 중앙에 위치
    }

    // 제출 메서드: 입력값을 받아 강의 객체 생성 및 다이얼로그 닫기
    private void submit() {
        try {
            int courseID = Integer.parseInt(txtCourseID.getText());
            String courseName = txtCourseName.getText();
            String classroom = txtClassroom.getText();
            int credit = Integer.parseInt(txtCredit.getText());
            String semester = txtSemester.getText();
            String day = txtDay.getText();
            String time = txtTime.getText();
            int professorID = Integer.parseInt(txtProfessorID.getText());

            // 모든 필드가 입력되었는지 확인
            if (courseName.isEmpty() || classroom.isEmpty() || semester.isEmpty() || day.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 강의 객체 생성
            course = new Course(courseID, courseName, classroom, credit, semester, day, time, professorID);
            dispose(); // 다이얼로그 닫기
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 값을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 강의 객체를 반환하는 메서드
    public Course getCourse() {
        return course;
    }
}
