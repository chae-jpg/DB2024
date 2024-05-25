package worker;

import main.Start;
import worker.Course.CourseManagement;
import worker.Course.StudentCoursesView;
import worker.Evaluation.CourseEvaluationManagement;
import worker.Grade.GradeManagement;
import worker.Professor.ProfessorManagement;
import worker.Student.StudentManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkerStart extends JFrame {

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

	public static StudentManagement student_management_frame = null;
	public static ProfessorManagement professor_management_frame = null;
	public static CourseManagement course_management_frame = null;
	public static GradeManagement grade_management_frame = null;
	public static CourseEvaluationManagement course_evaluation_management_frame = null;
	public static StudentCoursesView student_courses_view_frame = null;
	public static Start start_frame = null;
	private JButton homeButton;

	public WorkerStart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(12, 6, 771, 492);
		contentPane.add(panel);
		panel.setLayout(null);

		title = new JLabel("학사 관리 시스템");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(287, 30, 219, 55);
		panel.add(title);

		btn1 = new JButton("1. 학생 관리");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student_management_frame = new StudentManagement();
				student_management_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn1.setBounds(224, 120, 344, 52);
		panel.add(btn1);

		btn2 = new JButton("2. 교수 관리");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professor_management_frame = new ProfessorManagement();
				professor_management_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn2.setBounds(224, 184, 344, 52);
		panel.add(btn2);

		btn3 = new JButton("3. 강의 관리");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				course_management_frame = new CourseManagement();
				course_management_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn3.setBounds(224, 248, 344, 52);
		panel.add(btn3);

		btn4 = new JButton("4. 성적 관리");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grade_management_frame = new GradeManagement();
				grade_management_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn4.setBounds(224, 312, 344, 52);
		panel.add(btn4);

		btn5 = new JButton("5. 강의평가 관리");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				course_evaluation_management_frame = new CourseEvaluationManagement();
				course_evaluation_management_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn5.setBounds(224, 376, 344, 52);
		panel.add(btn5);

		btn6 = new JButton("6. 학생 별 수강 과목 조회");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student_courses_view_frame = new StudentCoursesView();
				student_courses_view_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn6.setBounds(224, 440, 344, 52);
		panel.add(btn6);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new Start();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		homeButton.setBounds(27, 30, 85, 29);
		panel.add(homeButton);
	}
}
