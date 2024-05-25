package professor;

import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CourseEvaluationManagemet extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;

	public static StartProfessor start_frame = null;

	private void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 300)
				width = 300;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	private void searchEval(String selectedItem, String searchText) {
		String sql = "";

		if (selectedItem.equals("강의명")) {
			sql = "SELECT * FROM DB2024_EVALUATION WHERE "
					+ "CourseID = (SELECT CourseID From DB2024_COURSE WHERE CourseName=?)";
		} else if (selectedItem.equals("학수번호")) {
			sql = "SELECT * FROM DB2024_EVALUATION WHERE CourseId=?";
		}

		try {
			Connection connection = DatabaseConnection.getConnection();
			if (connection == null) {
				throw new SQLException("Database connection failed");
			}

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, searchText);
			ResultSet resultSet = statement.executeQuery();

			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				model.addColumn(resultSet.getMetaData().getColumnName(i));
			}

			while (resultSet.next()) {
				Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					row[i - 1] = resultSet.getObject(i);
				}
				model.addRow(row);
			}
			resizeColumnWidth(table);

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseEvaluationManagemet frame = new CourseEvaluationManagemet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CourseEvaluationManagemet() {
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

		title = new JLabel("강의평가 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(315, 21, 173, 55);
		panel.add(title);

		searchTextField = new JTextField("강의명 or 학수번호를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchEval(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new StartProfessor();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 85, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = {"강의명", "학수번호"};
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명");
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(50, 150, 700, 300);
		panel.add(scrollpane);
	}
}
