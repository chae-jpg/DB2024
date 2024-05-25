package worker.Professor;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfessorManagement extends JFrame {

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
	private ProfessorDAO professorDAO;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorManagement frame = new ProfessorManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorManagement() {
		professorDAO = new ProfessorDAO();

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

		title = new JLabel("교수 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 116, 55);
		panel.add(title);

		searchTextField = new JTextField("교수명 or 교수id or 학과를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchProfessors(selectedItem, searchText);
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
		homeButton.setBounds(6, 6, 81, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "교수명", "교수id", "학과" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("교수명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "ProfessorID", "Name", "Department", "Email", "Phone", "Password" }
		);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openProfessorForm(null);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int professorID = (int) tableModel.getValueAt(selectedRow, 0);
					Professor professor = professorDAO.getProfessorsById(professorID).get(0);
					openProfessorForm(professor);
				} else {
					JOptionPane.showMessageDialog(panel, "수정할 교수를 선택하세요.", "수정 오류", JOptionPane.ERROR_MESSAGE);
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
					int professorID = (int) tableModel.getValueAt(selectedRow, 0);
					professorDAO.deleteProfessor(professorID);
					displayProfessors(professorDAO.getProfessorsByName("")); // 모든 교수를 다시 로드
				} else {
					JOptionPane.showMessageDialog(panel, "삭제할 교수를 선택하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);

		displayProfessors(professorDAO.getProfessorsByName(""));
	}

	private void searchProfessors(String criteria, String value) {
		List<Professor> professors;
		if (criteria.equals("교수명")) {
			professors = professorDAO.getProfessorsByName(value);
		} else if (criteria.equals("교수id")) {
			int professorID = Integer.parseInt(value);
			professors = professorDAO.getProfessorsById(professorID);
		} else {
			professors = professorDAO.getProfessorsByDepartment(value);
		}
		displayProfessors(professors);
	}

	private void displayProfessors(List<Professor> professors) {
		tableModel.setRowCount(0); // Clear existing rows
		for (Professor professor : professors) {
			tableModel.addRow(new Object[] {
					professor.getProfessorID(), professor.getName(), professor.getDepartment(), professor.getEmail(),
					professor.getPhone(), professor.getPassword()
			});
		}
	}

	private void openProfessorForm(Professor professor) {
		boolean isEditMode = (professor != null);
		ProfessorForm professorForm = new ProfessorForm(this, professor, isEditMode);
		professorForm.setVisible(true);

		Professor result = professorForm.getProfessor();
		if (result != null) {
			if (isEditMode) {
				professorDAO.updateProfessor(result);
			} else {
				professorDAO.addProfessor(result);
			}
			displayProfessors(professorDAO.getProfessorsByName("")); // 모든 교수를 다시 로드
		}
	}
}
