package worker.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtStudentID;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtEmail;
    private JTextField txtContact;
    private JTextField txtPassword;
    private JButton btnSubmit;
    private JButton btnCancel;
    private Student student;
    private boolean isEditMode;

    //JDialog 클래스를 상속하고, 다이얼로그 형태의 윈도우를 생성함.
    public StudentForm(Frame parent, Student student, boolean isEditMode) {
        super(parent, true);
        this.student = student;
        this.isEditMode = isEditMode;
        initialize();
    }
// 다이얼로그 창의 제목을 설정하고, 폼 요소들을 배치하고 초기화하는 메서드.
    private void initialize() {
        setTitle(isEditMode ? "학생 수정" : "학생 등록"); // 다이얼로그 창의 제목을 설정
        setBounds(100, 100, 450, 300); // 다이얼로그 창의 위치와 크기를 설정
        getContentPane().setLayout(new GridLayout(7, 2, 10, 10));//다이얼로그 창의 레이아웃을 설정

        JLabel lblStudentID = new JLabel("학번:"); // "학번" 레이블을 생성
        getContentPane().add(lblStudentID);

        txtStudentID = new JTextField(); //텍스트를 입력할 수 있는 텍스트 필드를 생성
        getContentPane().add(txtStudentID); // 위에서 생성한 텍스트 필드를 다이얼로그 창의 내용으로 추가
        txtStudentID.setColumns(10);
        /*
        텍스트 필드의 초기값 설정.
        만약 student 객체가 null이 아니라면, 해당 학생 객체의 학번을 문자열로 변환하여 텍스트 필드에 설정
        그렇지 않으면 빈 문자열 설정
         */
        txtStudentID.setText(student != null ? String.valueOf(student.getStudentID()) : "");
        txtStudentID.setEditable(!isEditMode);

        JLabel lblName = new JLabel("이름:");
        getContentPane().add(lblName);

        txtName = new JTextField();
        getContentPane().add(txtName);
        txtName.setColumns(10);
        txtName.setText(student != null ? student.getName() : "");

        JLabel lblDepartment = new JLabel("학과:");
        getContentPane().add(lblDepartment);

        txtDepartment = new JTextField();
        getContentPane().add(txtDepartment);
        txtDepartment.setColumns(10);
        txtDepartment.setText(student != null ? student.getDepartment() : "");

        JLabel lblEmail = new JLabel("이메일:");
        getContentPane().add(lblEmail);

        txtEmail = new JTextField();
        getContentPane().add(txtEmail);
        txtEmail.setColumns(10);
        txtEmail.setText(student != null ? student.getEmail() : "");

        JLabel lblContact = new JLabel("연락처:");
        getContentPane().add(lblContact);

        txtContact = new JTextField();
        getContentPane().add(txtContact);
        txtContact.setColumns(10);
        txtContact.setText(student != null ? student.getContact() : "");

        JLabel lblPassword = new JLabel("비밀번호:");
        getContentPane().add(lblPassword);

        txtPassword = new JTextField();
        getContentPane().add(txtPassword);
        txtPassword.setColumns(10);
        txtPassword.setText(student != null ? student.getPassword() : "");

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
// 사용자가 입력한 정보를 가져와서 유효성을 검사한 후, 학생 객체를 생성하거나 수정
    private void submit() {
        try {
            int studentID = Integer.parseInt(txtStudentID.getText());
            String name = txtName.getText();
            String department = txtDepartment.getText();
            String email = txtEmail.getText();
            String contact = txtContact.getText();
            String password = txtPassword.getText();
// 만약 필수 입력 필드 중 하나라도 비어있는 경우에는 오류 메시지를 표시
            if (name.isEmpty() || department.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            student = new Student(studentID, name, department, email, contact, password);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 학번을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }
// 생성된 학생 객체를 반환
    public Student getStudent() {
        return student;
    }
}
