package student;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.sql.*;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class CourseEvaluationManagementStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;
	private JButton btnRegister;
    private JButton btnModify;
    private JButton btnDelete;

	public static StudentStart start_frame=null;
	public static CourseEvaluationModify modify_frame = null;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/DB2024team04";
	//Database credentials
	// MySQL 계정과 암호 입력
	static final String USER = "root";
	static final String PASS = "root";
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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

	/**
	 * Create the frame.
	 */
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
		
		searchTextField = new JTextField("강의명 or 학수번호를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				System.out.println(selectedItem);
				System.out.println(searchText);
				searchEval(selectedItem, searchText);
				
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
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
		String[] options = {"강의명", "학수번호"};
		comboBox.setModel(new DefaultComboBoxModel<>(options)); 
		comboBox.setSelectedItem("강의명"); // 기본 선택
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
        		//등록 버튼 눌렀을 때 
        		
        	}
        });
        btnRegister.setBounds(537, 457, 75, 29);
        panel.add(btnRegister);
        
        btnModify = new JButton("수정");
        btnModify.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//수정 버튼 눌렀을 때 
        		int row = table.getSelectedRow();
        		int eval_id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
        		modify_frame = new CourseEvaluationModify(eval_id);
        		modify_frame.setVisible(true);
        		
        	}
        });
        btnModify.setBounds(605, 457, 75, 29);
        panel.add(btnModify);
        
        btnDelete = new JButton("삭제");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//삭제 버튼 눌렀을 때 
        		
        		
        		
        		
        	}
        });
        btnDelete.setBounds(675, 457, 75, 29);
        panel.add(btnDelete);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 439, 581, -306);
        panel.add(scrollPane);
	}
}
