package student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfessorViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private ProfessorDAO professorDAO = new ProfessorDAO();

	public static StudentStart start_frame = null;

	public ProfessorViewStudent() {
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

		title = new JLabel("교수 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 129, 55);
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
		btnSearch.setBounds(574, 97, 75, 29);
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
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = {"교수명", "교수id", "학과"};
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("교수명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 300);
		panel.add(pane);
	}

	private void searchProfessors(String searchType, String searchText) {
		List<Professor> professors = professorDAO.searchProfessors(searchType, searchText);
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		model.addColumn("ProfessorID");
		model.addColumn("Name");
		model.addColumn("Department");
		model.addColumn("Email");
		model.addColumn("Phone");

		for (Professor professor : professors) {
			model.addRow(new Object[]{professor.getProfessorID(), professor.getName(), professor.getDepartment(), professor.getEmail(), professor.getPhone()});
		}
	}
}
