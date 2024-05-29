package worker.Grade;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.DatabaseConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/*
 직원은 학생의 성적 조회만 가능 
 (등록, 수정, 삭제 불가) 
 */

public class GradeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel title;
    private JButton btnSearch;
    private JTextField searchTextField;
    private JButton homeButton;
    private JComboBox comboBox;
    private JTable table;

    public static WorkerStart start_frame = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GradeManagement frame = new GradeManagement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GradeManagement() {
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
            public void actionPerformed(ActionEvent e) { // 검색을 통해 모든 학생의 성적이 조회 가능하다.
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
                start_frame = new WorkerStart();
                start_frame.setVisible(true);
                setVisible(false);
            }
        });
        homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeButton.setBounds(6, 6, 63, 29);
        panel.add(homeButton);

        comboBox = new JComboBox<>();
        String[] options = { "학번", "강의id" };
        comboBox.setModel(new DefaultComboBoxModel<>(options));
        comboBox.setSelectedItem("학번"); // 기본 선택을 학번으로 설정해주었다.
        comboBox.setBounds(101, 98, 101, 27);
        panel.add(comboBox);

        table = new JTable();
        table.setBounds(50, 150, 700, 300);
        panel.add(table);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(50, 150, 700, 300);
        panel.add(pane);
    }

    private void searchGrade(String selectedItem, String searchText) {

        String sql = "";

        // 선택 item에 따라 다르게 sql 쿼리 작성해주었습니다.
        // worker는 DB2024_Grade에 대한 모든 정보를 확인할 수 있습니다.
        if (selectedItem.equals("학번")) { // 선택한 게 학번이라면
            sql = "SELECT * FROM DB2024_Grade WHERE StudentId=?";
        } else if (selectedItem.equals("강의id")) { // 선택한게 강의id라면
            sql = "SELECT * FROM DB2024_Grade WHERE CourseId=?";
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DatabaseConnection.getConnection();
            if (connection == null) {
                throw new SQLException("Database connection failed");
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, searchText); // ?의 첫 번째를 searchText로 설정해주었습니다.
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