package professor;

/* 교수님은 자기가 담당하는 과목 성적의 입력, 수정, 삭제 가능하다. 
 * 단, 조회는 다 가능하게 구현해주었다.  */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import javax.swing.JTable;
import javax.swing.JComboBox;


public class ProfessorGradeManagement extends JFrame {

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

	public static StartProfessor start_professor_frame=null;
	public static ProfessorGradeRegister grade_register_frame=null;
	public static ProfessorGradeEdit grade_edit_frame=null;
	public static ProfessorGradeDelete grade_delete_frame = null;
	
    private static final String url = "jdbc:mysql://localhost:3306/DB2024team04";
    private static final String username = "";
    private static final String password = "";
  


    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorGradeManagement frame = new ProfessorGradeManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorGradeManagement() {
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
		
		title = new JLabel("성적 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 101, 55);
		panel.add(title);
		
		searchTextField = new JTextField("학번 or 강의id를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //검색을 통해 모든 학생의 성적이 조회 가능하다. 
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				System.out.println(selectedItem);
				System.out.println(searchText);
				searchGrade(selectedItem, searchText);	
				
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);
		
		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_professor_frame = new StartProfessor();
				start_professor_frame.setVisible(true);
			    setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);
		
		comboBox = new JComboBox<>();
		String[] options = {"학번", "강의id"};
		comboBox.setModel(new DefaultComboBoxModel<>(options)); 
		comboBox.setSelectedItem("학번"); // 기본 선택을 학번으로 설정해주었다. 
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

        
        table = new JTable();
        table.setBounds(50, 150, 700, 300);
        panel.add(table);
        
        btnRegister = new JButton("등록");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//등록 버튼 눌렀을 때 
        		grade_register_frame = new ProfessorGradeRegister();
        		grade_register_frame.setVisible(true);
			    setVisible(false);
        	}
        });
        btnRegister.setBounds(537, 457, 75, 29);
        panel.add(btnRegister);
        
        btnModify = new JButton("수정");
        btnModify.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//수정 버튼 눌렀을 때 
        		grade_edit_frame = new ProfessorGradeEdit();
        		grade_edit_frame.setVisible(true);
        		setVisible(false);
        	}
        });
        btnModify.setBounds(605, 457, 75, 29);
        panel.add(btnModify);
        
        btnDelete = new JButton("삭제");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//삭제 버튼 눌렀을 때 
        		grade_delete_frame = new ProfessorGradeDelete();
        		grade_delete_frame.setVisible(true);
        		setVisible(false);
        		
        	}
        });
        btnDelete.setBounds(675, 457, 75, 29);
        panel.add(btnDelete);
		
	}
	
	private void searchGrade(String selectedItem, String searchText) {
        

        String sql = "";

        if (selectedItem.equalsIgnoreCase("학번")) {
            sql = "SELECT StudentId, CourseID, Grade, Semester, Repetition FROM DB2024_Grade WHERE StudentID=?"; 
            // Repetition을 포함시킨 이유는 재수강 여부에 따라 최대 학점이 달라지기 때문이다. 
        } else if (selectedItem.equalsIgnoreCase("강의id")) {
            sql = "SELECT StudentId, CourseID, Grade, Semester, Repetition FROM DB2024_Grade WHERE CourseID=?";
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

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
   
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
