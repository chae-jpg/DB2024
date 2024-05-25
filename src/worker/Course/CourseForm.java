package worker.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public CourseForm(Frame parent, Course course, boolean isEditMode) {
        super(parent, true);
        this.course = course;
        this.isEditMode = isEditMode;
        initialize();
    }

    private void initialize() {
        setTitle(isEditMode ? "강의 수정" : "강의 등록");
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new GridLayout(10, 2, 10, 10));

        JLabel lblCourseID = new JLabel("강의 ID:");
        getContentPane().add(lblCourseID);

        txtCourseID = new JTextField();
        getContentPane().add(txtCourseID);
        txtCourseID.setColumns(10);
        txtCourseID.setText(course != null ? String.valueOf(course.getCourseID()) : "");
        txtCourseID.setEditable(!isEditMode);

        JLabel lblCourseName = new JLabel("강의명:");
        getContentPane().add(lblCourseName);

        txtCourseName = new JTextField();
        getContentPane().add(txtCourseName);
        txtCourseName.setColumns(10);
        txtCourseName.setText(course != null ? course.getCourseName() : "");

        JLabel lblClassroom = new JLabel("강의실:");
        getContentPane().add(lblClassroom);

        txtClassroom = new JTextField();
        getContentPane().add(txtClassroom);
        txtClassroom.setColumns(10);
        txtClassroom.setText(course != null ? course.getClassroom() : "");

        JLabel lblCredit = new JLabel("학점:");
        getContentPane().add(lblCredit);

        txtCredit = new JTextField();
        getContentPane().add(txtCredit);
        txtCredit.setColumns(10);
        txtCredit.setText(course != null ? String.valueOf(course.getCredit()) : "");

        JLabel lblSemester = new JLabel("학기:");
        getContentPane().add(lblSemester);

        txtSemester = new JTextField();
        getContentPane().add(txtSemester);
        txtSemester.setColumns(10);
        txtSemester.setText(course != null ? course.getSemester() : "");

        JLabel lblDay = new JLabel("요일:");
        getContentPane().add(lblDay);

        txtDay = new JTextField();
        getContentPane().add(txtDay);
        txtDay.setColumns(10);
        txtDay.setText(course != null ? course.getDay() : "");

        JLabel lblTime = new JLabel("시간:");
        getContentPane().add(lblTime);

        txtTime = new JTextField();
        getContentPane().add(txtTime);
        txtTime.setColumns(10);
        txtTime.setText(course != null ? course.getTime() : "");

        JLabel lblProfessorID = new JLabel("교수 ID:");
        getContentPane().add(lblProfessorID);

        txtProfessorID = new JTextField();
        getContentPane().add(txtProfessorID);
        txtProfessorID.setColumns(10);
        txtProfessorID.setText(course != null ? String.valueOf(course.getProfessorID()) : "");

        btnSubmit = new JButton(isEditMode ? "수정" : "등록");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        getContentPane().add(btnSubmit);

        btnCancel = new JButton("취소");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnCancel);

        pack();
        setLocationRelativeTo(getParent());
    }

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

            if (courseName.isEmpty() || classroom.isEmpty() || semester.isEmpty() || day.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            course = new Course(courseID, courseName, classroom, credit, semester, day, time, professorID);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 값을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Course getCourse() {
        return course;
    }
}
