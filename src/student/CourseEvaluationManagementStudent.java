package student;

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

public class CourseEvaluationManagementStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;

	public static StudentStart start_frame = null;
	public static CourseEvaluationModify modify_frame = null;
	public static CourseEvaluationAdd add_frame = null;

	//해당 강의평가를 수정 또는 삭제할 권한이 있는지 확인하는 메소드
	public boolean checkValid(int id) {
		String sql = "SELECT studentid FROM DB2024_EVALUATION WHERE evaluationid = ?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				//조회한 학생 id가 Student 객체의 id와 동일한지 리턴
				return rs.getInt(1) == Integer.parseInt(Student.getInstance().getStudentId());
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return false;
	}

	//열의 사이즈를 자동으로 조절해주는 메소드
	public void resizeColumnWidth(JTable table) {
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
	
	//강의명과 강의id에 따라 강의평가를 검색하는 메소드
	private void searchEval(String selectedItem, String searchText) {
		String sql = "";

		if (selectedItem.equals("강의명")) {
			//중첩쿼리를 사용해 해당 강의명을 가진 모든 강의평을 뷰에서 조회
			sql = "SELECT * FROM DB2024_EvaluationView WHERE CourseID = (SELECT CourseID FROM DB2024_COURSE WHERE CourseName=?)";
		} else if (selectedItem.equals("강의id")) {
			sql = "SELECT * FROM DB2024_EvaluationView WHERE CourseId=?";
		}

		try (Connection connection = DatabaseConnection.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, searchText);
			ResultSet resultSet = statement.executeQuery();

			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			
			//화면에 데이터를 표시
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//선택한 데이터를 삭제하는 메소드
	private void deleteEval(int id) {
		String deleteQuery = "DELETE FROM DB2024_EVALUATION WHERE evaluationID = ?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement pStmt = conn.prepareStatement(deleteQuery)) {
			
			//트랜젝션 설정을 위해 오토커밋 헤제
			conn.setAutoCommit(false);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
			//커밋
			conn.commit();
			System.out.println(id + " deleted");
			conn.setAutoCommit(true);
		} catch (SQLException se) {
			se.printStackTrace();
			try (Connection conn = DatabaseConnection.getConnection()) {
				if (conn != null) {
					//연결에 문제가 있을 시 -> 롤백
					conn.rollback();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseEvaluationManagementStudent frame = new CourseEvaluationManagementStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CourseEvaluationManagementStudent() {
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

		searchTextField = new JTextField("강의명 or 강의id를 입력하세요");
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
		btnSearch.setBounds(554, 97, 90, 29);
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
		homeButton.setBounds(6, 6, 85, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = {"강의명", "강의id"};
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명");
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(50, 150, 700, 300);
		panel.add(scrollpane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add_frame = new CourseEvaluationAdd();
				add_frame.setVisible(true);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "수정할 항목을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//선택한 행의 강의평가id를 가져옴
				int eval_id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				if (!checkValid(eval_id)) {
					JOptionPane.showMessageDialog(null, "작성하지 않은 강의평을 선택했거나, 데이터베이스 접속에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				modify_frame = new CourseEvaluationModify(eval_id);
				modify_frame.setVisible(true);
			}
		});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int eval_id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				if (!checkValid(eval_id)) {
					JOptionPane.showMessageDialog(null, "작성하지 않은 강의평을 선택했거나, 데이터베이스 접속에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				deleteEval(eval_id);
				searchEval((String) comboBox.getSelectedItem(), searchTextField.getText());
			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);
	}
}
