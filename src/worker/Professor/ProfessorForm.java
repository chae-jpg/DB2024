package worker.Professor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorForm extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtProfessorID;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtPassword;
    private JButton btnSubmit;
    private JButton btnCancel;
    private Professor professor;
    private boolean isEditMode;

    public ProfessorForm(Frame parent, Professor professor, boolean isEditMode) {
        super(parent, true);
        this.professor = professor;
        this.isEditMode = isEditMode;
        initialize();
    }

    private void initialize() {
        setTitle(isEditMode ? "교수 수정" : "교수 등록");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblProfessorID = new JLabel("교수 ID:");
        getContentPane().add(lblProfessorID);

        txtProfessorID = new JTextField();
        getContentPane().add(txtProfessorID);
        txtProfessorID.setColumns(10);
        txtProfessorID.setText(professor != null ? String.valueOf(professor.getProfessorID()) : "");
        txtProfessorID.setEditable(!isEditMode);

        JLabel lblName = new JLabel("이름:");
        getContentPane().add(lblName);

        txtName = new JTextField();
        getContentPane().add(txtName);
        txtName.setColumns(10);
        txtName.setText(professor != null ? professor.getName() : "");

        JLabel lblDepartment = new JLabel("학과:");
        getContentPane().add(lblDepartment);

        txtDepartment = new JTextField();
        getContentPane().add(txtDepartment);
        txtDepartment.setColumns(10);
        txtDepartment.setText(professor != null ? professor.getDepartment() : "");

        JLabel lblEmail = new JLabel("이메일:");
        getContentPane().add(lblEmail);

        txtEmail = new JTextField();
        getContentPane().add(txtEmail);
        txtEmail.setColumns(10);
        txtEmail.setText(professor != null ? professor.getEmail() : "");

        JLabel lblPhone = new JLabel("전화번호:");
        getContentPane().add(lblPhone);

        txtPhone = new JTextField();
        getContentPane().add(txtPhone);
        txtPhone.setColumns(10);
        txtPhone.setText(professor != null ? professor.getPhone() : "");

        JLabel lblPassword = new JLabel("비밀번호:");
        getContentPane().add(lblPassword);

        txtPassword = new JTextField();
        getContentPane().add(txtPassword);
        txtPassword.setColumns(10);
        txtPassword.setText(professor != null ? professor.getPassword() : "");

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
            int professorID = Integer.parseInt(txtProfessorID.getText());
            String name = txtName.getText();
            String department = txtDepartment.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String password = txtPassword.getText();

            if (name.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            professor = new Professor(professorID, name, department, email, phone, password);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 교수 ID를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Professor getProfessor() {
        return professor;
    }
}
