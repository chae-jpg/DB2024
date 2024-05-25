package worker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import javax.swing.*;

import professor.StartProfessor;

public class UpdateProfessor extends JFrame {

    public UpdateProfessor(String sql) {
        setTitle("교수 정보 수정");

        JLabel title = new JLabel("교수 정보 수정", JLabel.CENTER);
        title.setForeground(new Color(5, 0, 153));
        title.setFont(new Font("굴림", Font.BOLD, 30));

        JButton join = new JButton("정보 수정");
        JButton cancel = new JButton("취소");
        
        int myId=0;
        String myName="";
       	String myDepart="";
       	String myEmail="";
       	String myPhone="";
       	String myPw="";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team04", "root", "root");
				 Statement stmt = conn.createStatement();
				 )
				 {
       	ResultSet rset = stmt.executeQuery(sql);
       	
       	if(rset.next()) {
       	myId= rset.getInt(1);
       	myName = rset.getString(2);
       	myDepart = rset.getString(3);
       	myEmail = rset.getString(4);
       	myPhone = rset.getString(5);
       	myPw = rset.getString(6);
       	}
       	
       	
				 
				 
				 
				 }
				 catch (SQLException sqle) {
				 System.out.println("SQLException : " + sqle);
				 }
		


        JTextField id = new JTextField(10);
        JTextField pw = new JTextField(10);
        JTextField name = new JTextField(10);
        JTextField phone = new JTextField(10);
        JTextField email = new JTextField(10);
        JTextField department = new JTextField(10);
        
        id.setText(Integer.toString(myId));
        id.setEnabled(false);
		 name.setText(myName);
		 department.setText(myDepart);
		 email.setText(myEmail);
		 phone.setText(myPhone);
		 pw.setText(myPw);
        
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
                int numId = Integer.parseInt(Id);
                String Pw = pw.getText();
                String Name = name.getText();
                String Phone = phone.getText();
                String Email = email.getText();
                String Department = department.getText();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team04", "root", "root");
       				 Statement stmt = conn.createStatement();
       				 )
       				 {
               	String query = String.format("update DB2024_professor set Name='%s',Department='%s',Email='%s',Phone='%s',Password='%s' where ProfessorID=%d",Name,Department,Email,Phone,Pw,numId);
               	int i=stmt.executeUpdate(query);
               	if(i>0) JOptionPane.showMessageDialog(null, "교수 정보 수정 완료");
               	
       				 
       				 
       				 }
       				 catch (SQLException sqle) {
       				 System.out.println("SQLException : " + sqle);
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


