package student;

import student.StudentStart;
import worker.Course.Course;
import worker.Course.CourseDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	public static StudentStart start_frame = null;
	private CourseDAO courseDAO;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseViewStudent frame = new CourseViewStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CourseViewStudent() {
		courseDAO = new CourseDAO();
		initialize();
		loadAllCourses(); // Load all courses when the frame is initialized
	}

	private void initialize() {
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

		title = new JLabel("강의 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 118, 55);
		panel.add(title);

		searchTextField = new JTextField("강의명 or 강의id or 교수명을 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchCourses(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 93, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new StudentStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 81, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "강의명", "강의id", "교수" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "CourseID", "CourseName", "Classroom", "Credit", "Semester", "Day", "Time", "ProfessorID" });
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);
	}

	private void loadAllCourses() {
		List<Course> courses = courseDAO.getAllCourses();
		displayCourses(courses);
	}

	private void searchCourses(String criteria, String value) {
		List<Course> courses;
		if (criteria.equals("강의명")) {
			courses = courseDAO.getCoursesByName(value);
		} else if (criteria.equals("강의id")) {
			courses = courseDAO.getCoursesByID(Integer.parseInt(value));
		} else {
			courses = courseDAO.getCoursesByProfessor(value);
		}
		displayCourses(courses);
	}

	private void displayCourses(List<Course> courses) {
		tableModel.setRowCount(0); // Clear existing rows
		for (Course course : courses) {
			tableModel.addRow(new Object[] {
					course.getCourseID(), course.getCourseName(), course.getClassroom(), course.getCredit(),
					course.getSemester(), course.getDay(), course.getTime(), course.getProfessorID()
			});
		}
	}

}
