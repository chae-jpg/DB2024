package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import professor.ProfessorLogin;
import student.StudentLogin;
import worker.WorkerLogin;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class Start extends JFrame{
    private JPanel contentPane;
    private JFrame frame;
    private JPanel panel;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    public static StudentLogin student_login_frame = null;
    public static WorkerLogin worker_login_frame = null;
    public static ProfessorLogin professor_login_frame = null;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Start window = new Start();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Start() {
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

        JLabel title = new JLabel("학사 관리 시스템");
        title.setBounds(278, 63, 212, 55);
        title.setFont(new Font("Dialog", Font.PLAIN, 27));
        panel.add(title);

        btn1 = new JButton("학생");
        btn1.setBounds(94, 231, 131, 55);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                student_login_frame = new StudentLogin();
                student_login_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn1.setFont(new Font("굴림", Font.PLAIN, 22));
        panel.add(btn1);

        btn2 = new JButton("교수");
        btn2.setBounds(319, 231, 131, 55);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                professor_login_frame = new ProfessorLogin();
                professor_login_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn2.setFont(new Font("굴림", Font.PLAIN, 22));
        panel.add(btn2);

        btn3 = new JButton("직원");
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                worker_login_frame = new WorkerLogin();
                worker_login_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn3.setBounds(544, 231, 131, 55);
        btn3.setFont(new Font("굴림", Font.PLAIN, 22));
        panel.add(btn3);

        // 데이터베이스 연결 테스트
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("데이터베이스 연결 성공!");
        } else {
            System.out.println("데이터베이스 연결 실패!");
        }
    }
}
