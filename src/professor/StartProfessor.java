package professor;

import main.Start;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartProfessor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel title;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton homeButton;
    public static ProfessorStudentManagement student_management_frame = null;
    public static ProfessorManagement professor_management_frame = null;
    public static ProfessorStudentsByCourse student_by_course_frame = null;
    public static ProfessorGradeManagement grade_management_frame = null;
    public static TimetableProfessor timetable_frame = null;
    public static CourseEvaluationManagemet eval_frame = null;
    public static Start start_frame = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StartProfessor frame = new StartProfessor();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StartProfessor() {
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

        title = new JLabel("교수 학사 관리 시스템");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
        title.setBounds(246, 35, 279, 55);
        panel.add(title);

        btn1 = new JButton("1. 학생 정보 조회");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                student_management_frame = new ProfessorStudentManagement();
                student_management_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn1.setBounds(215, 123, 344, 52);
        panel.add(btn1);

        btn2 = new JButton("2. 교수 정보 조회");
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                professor_management_frame = new ProfessorManagement();
                professor_management_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn2.setBounds(215, 184, 344, 52);
        panel.add(btn2);

        btn3 = new JButton("3. 강의 수강자 명단 조회");
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                student_by_course_frame = new ProfessorStudentsByCourse();
                student_by_course_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn3.setBounds(215, 245, 344, 52);
        panel.add(btn3);

        btn4 = new JButton("4. 성적 관리 ");
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grade_management_frame = new ProfessorGradeManagement();
                grade_management_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn4.setBounds(215, 306, 344, 52);
        panel.add(btn4);

        btn5 = new JButton("6. 시간표 조회");
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timetable_frame = new TimetableProfessor();
                timetable_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn5.setBounds(215, 428, 344, 52);
        panel.add(btn5);

        btn6 = new JButton("5. 강의평가 조회");
        btn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eval_frame = new CourseEvaluationManagemet();
                eval_frame.setVisible(true);
                setVisible(false);
            }
        });
        btn6.setBounds(215, 367, 344, 52);
        panel.add(btn6);

        homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Professor.getInstance().makeNull(); // null로 만들어서 로그아웃하기
                start_frame = new Start();
                start_frame.setVisible(true);
                setVisible(false);
            }
        });
        homeButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        homeButton.setBounds(10, 10, 85, 29);
        panel.add(homeButton);
    }
}
