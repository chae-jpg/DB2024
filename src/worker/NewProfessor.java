
package worker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class NewProfessor extends JFrame {

    public NewProfessor() {
        setTitle("교수 등록");

        JLabel title = new JLabel("교수 정보 등록", JLabel.CENTER);
        title.setForeground(new Color(5, 0, 153));
        title.setFont(new Font("굴림", Font.BOLD, 30));

        JButton join = new JButton("교수등록");
        JButton cancel = new JButton("취소");

        JTextField id = new JTextField(10);
        JTextField pw = new JTextField(10);
        JTextField name = new JTextField(10);
        JTextField phone = new JTextField(10);
        JTextField email = new JTextField(10);
        JTextField department = new JTextField(10);

        // Form panels
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        idPanel.add(new JLabel("아이디 : "));
        idPanel.add(id);

        JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pwdPanel.add(new JLabel("비밀번호 : "));
        pwdPanel.add(pw);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        namePanel.add(new JLabel("이    름 : "));
        namePanel.add(name);

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        phonePanel.add(new JLabel("연 락 처 : "));
        phonePanel.add(phone);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        emailPanel.add(new JLabel("이메일 : "));
        emailPanel.add(email);

        JPanel departPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        departPanel.add(new JLabel("학 과: "));
        departPanel.add(department);

        JPanel formPanel = new JPanel(new GridLayout(6, 1));
        formPanel.add(idPanel);
        formPanel.add(pwdPanel);
        formPanel.add(namePanel);
        formPanel.add(phonePanel);
        formPanel.add(emailPanel);
        formPanel.add(departPanel);

        // Button panel
        JPanel panel = new JPanel();
        panel.add(join);
        panel.add(cancel);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(formPanel, BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setBounds(200, 200, 400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Event handling
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Id = id.getText();
                String Pw = pw.getText();
                String Name = name.getText();
                String Phone = phone.getText();
                String Email = email.getText();
                String Department = department.getText();

                try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost/DB2024Team04", "root", "root");
                     PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO DB2024_Professor (ProfessorID, Name, Department, Email, phone, Password) VALUES (?, ?, ?, ?, ?, ?)")) {
                    
                    stmt.setInt(1, Integer.parseInt(Id));
                    stmt.setString(2, Name);
                    stmt.setString(3, Department);
                    stmt.setString(4, Email);
                    stmt.setString(5, Phone);
                    stmt.setString(6, Pw);

                    int i = stmt.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "교수등록완료");
                    } else {
                        JOptionPane.showMessageDialog(null, "교수등록실패");
                    }
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                    JOptionPane.showMessageDialog(null, "SQLException: " + sqle.getMessage());
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new ProfessorManagement();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new NewProfessor();
    }
}





