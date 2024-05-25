package worker.Course;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseView extends JFrame {

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
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;
	public static WorkerStart start_frame = null;
	private CourseDAO courseDAO;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseView frame = new CourseView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CourseView() {
		courseDAO = new CourseDAO();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
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
		title.setBounds(318, 24, 121, 55);
		panel.add(title);

		searchTextField = new JTextField("강의명 or 학수번호 or 교수명을 입력하세요");
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
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new WorkerStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 75, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "강의명", "학수번호", "교수" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "CourseID", "CourseName", "Classroom", "Credit", "Semester", "Day", "Time", "ProfessorID" }
		);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCourseForm(null);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int courseID = (int) tableModel.getValueAt(selectedRow, 0);
					Course course = courseDAO.getCoursesByID(courseID).get(0);
					openCourseForm(course);
				} else {
					JOptionPane.showMessageDialog(panel, "수정할 강의를 선택하세요.", "수정 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int courseID = (int) tableModel.getValueAt(selectedRow, 0);
					courseDAO.deleteCourse(courseID);
					displayCourses(courseDAO.getCoursesByName("")); // 모든 강의를 다시 로드
				} else {
					JOptionPane.showMessageDialog(panel, "삭제할 강의를 선택하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);

		// 초기 데이터 로드
		displayCourses(courseDAO.getCoursesByName("")); // 모든 강의를 로드
	}

	private void searchCourses(String criteria, String value) {
		List<Course> courses;
		if (criteria.equals("강의명")) {
			courses = courseDAO.getCoursesByName(value);
		} else if (criteria.equals("학수번호")) {
			int courseID = Integer.parseInt(value);
			courses = courseDAO.getCoursesByID(courseID);
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

	private void openCourseForm(Course course) {
		boolean isEditMode = (course != null);
		CourseForm courseForm = new CourseForm(this, course, isEditMode);
		courseForm.setVisible(true);

		Course result = courseForm.getCourse();
		if (result != null) {
			if (isEditMode) {
				courseDAO.updateCourse(result);
			} else {
				courseDAO.addCourse(result);
			}
			displayCourses(courseDAO.getCoursesByName("")); // 모든 강의를 다시 로드
		}
	}
}
