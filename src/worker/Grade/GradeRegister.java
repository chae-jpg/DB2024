package worker.Grade;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GradeRegister extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel title;
    private JLabel textGrade;
    private JRadioButton rdbtnAplus;
    private JRadioButton rdbtnA0;
    private JRadioButton rdbtnAminus;
    private JRadioButton rdbtnBplus;
    private JRadioButton rdbtnB0;
    private JRadioButton rdbtnBminus;
    private JRadioButton rdbtnCplus;
    private JRadioButton rdbtnC0;
    private JRadioButton rdbtnCminus;
    private JRadioButton rdbtF;
    private JRadioButton rdbtnP;
    private JRadioButton rdbtnNP;
    private JLabel textSemester;
    private JLabel textCourse;
    private JLabel textStudent;
    private JTextField textFieldSemester;
    private JTextField textFieldCourse;
    private JTextField textFieldStudent;
    private JButton homeButton;
    private JButton btnRegister;

    public static GradeManagement grade_management_frame = null;
    public static WorkerStart start_frame = null;

    private static final String url = "jdbc:mysql://localhost:3306/DB2024";
    private static final String username = "";
    private static final String password = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GradeRegister frame = new GradeRegister();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GradeRegister() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 543);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBounds(12, 6, 771, 492);
        contentPane.add(panel);
        panel.setLayout(null);

        title = new JLabel("성적 등록 ");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
        title.setBounds(332, 24, 131, 55);
        panel.add(title);

        textGrade = new JLabel("성적");
        textGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        textGrade.setBounds(67, 106, 61, 55);
        panel.add(textGrade);

        ButtonGroup bg = new ButtonGroup();

        rdbtnAplus = new JRadioButton("A+");
        rdbtnAplus.setBounds(136, 122, 49, 23);
        bg.add(rdbtnAplus);
        panel.add(rdbtnAplus);

        rdbtnA0 = new JRadioButton("A0");
        rdbtnA0.setBounds(188, 122, 49, 23);
        bg.add(rdbtnA0);
        panel.add(rdbtnA0);

        rdbtnAminus = new JRadioButton("A-");
        rdbtnAminus.setBounds(240, 122, 49, 23);
        bg.add(rdbtnAminus);
        panel.add(rdbtnAminus);

        rdbtnBplus = new JRadioButton("B+");
        rdbtnBplus.setBounds(292, 122, 49, 23);
        bg.add(rdbtnBplus);
        panel.add(rdbtnBplus);

        rdbtnB0 = new JRadioButton("B0");
        rdbtnB0.setBounds(344, 122, 49, 23);
        bg.add(rdbtnB0);
        panel.add(rdbtnB0);

        rdbtnBminus = new JRadioButton("B-");
        rdbtnBminus.setBounds(396, 122, 49, 23);
        bg.add(rdbtnBminus);
        panel.add(rdbtnBminus);

        rdbtnCplus = new JRadioButton("C+");
        rdbtnCplus.setBounds(448, 122, 49, 23);
        bg.add(rdbtnCplus);
        panel.add(rdbtnCplus);

        rdbtnC0 = new JRadioButton("C0");
        rdbtnC0.setBounds(500, 122, 49, 23);
        bg.add(rdbtnC0);
        panel.add(rdbtnC0);

        rdbtnCminus = new JRadioButton("C-");
        rdbtnCminus.setBounds(552, 122, 49, 23);
        bg.add(rdbtnCminus);
        panel.add(rdbtnCminus);

        rdbtF = new JRadioButton("F");
        rdbtF.setBounds(604, 122, 39, 23);
        bg.add(rdbtF);
        panel.add(rdbtF);

        rdbtnP = new JRadioButton("P");
        rdbtnP.setBounds(646, 122, 49, 23);
        bg.add(rdbtnP);
        panel.add(rdbtnP);

        rdbtnNP = new JRadioButton("NP");
        rdbtnNP.setBounds(698, 122, 61, 23);
        bg.add(rdbtnNP);
        panel.add(rdbtnNP);

        textSemester = new JLabel("학기");
        textSemester.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        textSemester.setBounds(67, 191, 61, 55);
        panel.add(textSemester);

        textCourse = new JLabel("강의");
        textCourse.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        textCourse.setBounds(67, 281, 61, 55);
        panel.add(textCourse);

        textStudent = new JLabel("학생");
        textStudent.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        textStudent.setBounds(67, 380, 61, 55);
        panel.add(textStudent);

        textFieldSemester = new JTextField();
        textFieldSemester.setBounds(252, 200, 281, 43);
        panel.add(textFieldSemester);
        textFieldSemester.setColumns(10);

        textFieldCourse = new JTextField();
        textFieldCourse.setColumns(10);
        textFieldCourse.setBounds(252, 290, 281, 43);
        panel.add(textFieldCourse);

        textFieldStudent = new JTextField();
        textFieldStudent.setColumns(10);
        textFieldStudent.setBounds(252, 392, 281, 43);
        panel.add(textFieldStudent);

        btnRegister = new JButton("확인");

        homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                start_frame = new WorkerStart();
                start_frame.setVisible(true);
                setVisible(false);
            }
        });

        homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeButton.setBounds(6, 6, 79, 29);
        panel.add(homeButton);

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedrb = null;
                if (rdbtnAplus.isSelected()) {
                    selectedrb = rdbtnAplus.getActionCommand();
                } else if (rdbtnA0.isSelected()) {
                    selectedrb = rdbtnA0.getActionCommand();
                } else if (rdbtnAminus.isSelected()) {
                    selectedrb = rdbtnAminus.getActionCommand();
                } else if (rdbtnBplus.isSelected()) {
                    selectedrb = rdbtnBplus.getActionCommand();
                } else if (rdbtnB0.isSelected()) {
                    selectedrb = rdbtnB0.getActionCommand();
                } else if (rdbtnBminus.isSelected()) {
                    selectedrb = rdbtnBminus.getActionCommand();
                } else if (rdbtnCplus.isSelected()) {
                    selectedrb = rdbtnCplus.getActionCommand();
                } else if (rdbtnC0.isSelected()) {
                    selectedrb = rdbtnC0.getActionCommand();
                } else if (rdbtnCminus.isSelected()) {
                    selectedrb = rdbtnCminus.getActionCommand();
                } else if (rdbtF.isSelected()) {
                    selectedrb = rdbtF.getActionCommand();
                } else if (rdbtnP.isSelected()) {
                    selectedrb = rdbtnP.getActionCommand();
                } else if (rdbtnNP.isSelected()) {
                    selectedrb = rdbtnNP.getActionCommand();
                }

                String searchedSemester = textFieldSemester.getText(); // 학기 가져오기 (ex 1-1, 1-2, 2-1..)
                String searchedCourse = textFieldCourse.getText(); // 강의 가져오기 (강의명 Or 강의id)
                String searchedStudent = textFieldStudent.getText(); // 학생 가져오기 (학생명 Or 학생id)

                System.out.println(selectedrb);
                System.out.println(searchedSemester);
                System.out.println(searchedCourse);
                System.out.println(searchedStudent);

                registerGrade(selectedrb, searchedSemester, searchedCourse, searchedStudent);
            }
        });
        btnRegister.setBounds(653, 457, 117, 29);
        panel.add(btnRegister);

    }

    private void registerGrade(String selectedrb, String semester, String searchedCourse, String searchedStudent) {

        String sql = "";
        int CourseID = 0;
        try {
            CourseID = Integer.parseInt(searchedCourse); // integer형으로 바뀐다면, 즉 강의명이 아닌 강의id라면
        } catch (NumberFormatException e) { // 강의명을 입력으로 받아왔다면
            try {
                sql = "SELECT CourseID FROM DB2024_COURSES WHERE CourseName=?"; // sql문 만들어서 courseid 찾아오기

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, searchedCourse);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    CourseID = resultSet.getInt("CourseID");
                    System.out.println("CourseID: " + CourseID);
                } else {

                    System.out.println("결과가 없습니다.");
                }

            } catch (ClassNotFoundException | SQLException ec1) {
                ec1.printStackTrace();
            }
        }

        int StudentID = 0;

        try {
            StudentID = Integer.parseInt(searchedStudent); // integer형으로 바뀐다면, 즉 학생 이름이 아닌 학생id라면
        } catch (NumberFormatException e) { // 학생이름으로 입력 받아왔다면
            try {
                sql = "SELECT StudentID FROM DB2024_STUDENTS WHERE Name=?"; // sql문 만들어서 학생id 찾아오기

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, searchedStudent);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    StudentID = resultSet.getInt("StudentID");
                    System.out.println("StudentID: " + StudentID);
                } else {

                    System.out.println("결과가 없습니다.");
                }

            } catch (ClassNotFoundException | SQLException ec2) {
                ec2.printStackTrace();
            }
        }
        // 아래는 등록과정
        try {
            sql = "INSERT INTO DB2024_GRADES(StudentId, CourseId, Grade, Semester) VALUES (?, ?, ?, ?)";

            // 재수강여부확인 (등록을 실행하기 전 먼저 studentid, courseid로 이미 데이터가 존재하는지 확인 후 넣기)
            String sql_re = "SELECT count(*) FROM DB2024_GRADES WHERE StudentId=? AND CourseId=?";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);

                PreparedStatement statement_re = connection.prepareStatement(sql_re);
                statement_re.setInt(1, StudentID);
                statement_re.setInt(2, CourseID);

                ResultSet resultSet_re = statement_re.executeQuery();

                if (resultSet_re.next()) {
                    int count = resultSet_re.getInt(1);
                    if (count > 0) {
                        sql = "INSERT INTO DB2024_GRADES(StudentId, CourseId, Grade, Semester, Repetition) VALUES (?, ?, ?, ?, TRUE)";
                        System.out.println(StudentID + " 학생은 재수강을 했습니다.");
                    }
                }

                resultSet_re.close();
                statement_re.close();
                connection.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, StudentID);
            statement.setInt(2, CourseID);
            statement.setString(3, selectedrb);
            statement.setString(4, semester);

            int rowsAffected = statement.executeUpdate();

            statement.close();
            connection.close();
            System.out.println(rowsAffected + "개의 행이 성공적으로 추가되었습니다.");

            grade_management_frame = new GradeManagement();
            grade_management_frame.setVisible(true);
            setVisible(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}