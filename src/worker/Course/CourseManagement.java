package worker.Course;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearchCourse;
	private JButton btnSearchListOfCourse;
	private JButton homeButton;

	public static WorkerStart start_frame = null;
	public static CourseView course_view = null;
	public static StudentsByCourseView studentbycourseview = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseManagement frame = new CourseManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CourseManagement() {
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

		title = new JLabel("강의 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(334, 33, 116, 55);
		panel.add(title);

		btnSearchCourse = new JButton("1. 강의 조회");
		btnSearchCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 강의 조회로 이동
				course_view = new CourseView();
				course_view.setVisible(true);
				setVisible(false);
			}
		});
		btnSearchCourse.setBounds(241, 160, 305, 63);
		panel.add(btnSearchCourse);

		btnSearchListOfCourse = new JButton("2. 강의 별 수강자 명단 조회");
		btnSearchListOfCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 강의 별 수강자 명단 조회로 이동
				studentbycourseview = new StudentsByCourseView();
				studentbycourseview.setVisible(true);
				setVisible(false);
			}
		});
		btnSearchListOfCourse.setBounds(241, 274, 305, 63);
		panel.add(btnSearchListOfCourse);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new WorkerStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);
	}
}
