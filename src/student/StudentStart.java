package student;

import main.Start;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentStart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btn1;
	private JButton btn2;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;

	public static ProfessorViewStudent professor_view_frame = null;
	public static CourseViewStudent course_view_frame = null;
	public static GradeViewStudent grade_view_frame = null;
	public static CourseEvaluationManagementStudent course_evaluation_management_frame = null;
	public static Start start_frame = null;
	public static TimetableStudent timetable_frame = null;
	private JButton homeButton;
	
	
	
	public StudentStart() {
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
		
		title = new JLabel("학사 관리 시스템");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(285, 33, 251, 55);
		panel.add(title);
		
		btn1 = new JButton("1. 교수 조회");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professor_view_frame = new ProfessorViewStudent();
				professor_view_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn1.setBounds(215, 120, 344, 52);
		panel.add(btn1);
		
		btn2 = new JButton("2. 강의 조회");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				course_view_frame = new CourseViewStudent();
				course_view_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn2.setBounds(215, 189, 344, 52);
		panel.add(btn2);
		
		btn4 = new JButton("3. 성적 조회");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grade_view_frame = new GradeViewStudent();
				grade_view_frame.setVisible(true);
				setVisible(false);
			}
		});
		btn4.setBounds(215, 251, 344, 52);
		panel.add(btn4);
		
		btn5 = new JButton("4. 강의평가 관리");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				course_evaluation_management_frame = new CourseEvaluationManagementStudent();
				course_evaluation_management_frame.setVisible(true);
				setVisible(false);
				
			}
		});
		btn5.setBounds(215, 313, 344, 52);
		panel.add(btn5);
		
		btn6 = new JButton("5. 시간표 조회");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				timetable_frame = new TimetableStudent();
				timetable_frame.setVisible(true);
				setVisible(false);
				
			}
		});
		btn6.setBounds(215, 375, 344, 52);
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
		homeButton.setBounds(24, 10, 85, 29);
		panel.add(homeButton);
		
		
		
	}
}
