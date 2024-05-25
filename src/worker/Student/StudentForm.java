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

    public StudentForm(Frame parent, Student student, boolean isEditMode) {
        super(parent, true);
        this.student = student;
        this.isEditMode = isEditMode;
        initialize();
    }

    private void initialize() {
        setTitle(isEditMode ? "학생 수정" : "학생 등록");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblStudentID = new JLabel("학번:");
        getContentPane().add(lblStudentID);

        txtStudentID = new JTextField();
        getContentPane().add(txtStudentID);
        txtStudentID.setColumns(10);
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

    private void submit() {
        try {
            int studentID = Integer.parseInt(txtStudentID.getText());
            String name = txtName.getText();
            String department = txtDepartment.getText();
            String email = txtEmail.getText();
            String contact = txtContact.getText();
            String password = txtPassword.getText();

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

    public Student getStudent() {
        return student;
    }
}
