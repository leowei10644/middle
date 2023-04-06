package mid.b;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class mid03 extends JFrame {
	  private JTextField f1,t2 ,result,t3,t4; 
	  private JLabel t1;
	  private JButton searchbtn;
	 
	  public mid03(){
	  super("搜尋引擎");
	  f1 = new JTextField(20);
	  t1 = new JLabel();
	  t2 = new JTextField("City",15);
	  t3 = new JTextField("Name",15);
	  t4= new JTextField("position",15);
	
	  searchbtn = new JButton();
	  result = new JTextField("ID",15);
	  t1.setText("搜尋ID:");
	  searchbtn.setText("搜尋");
	  JPanel panel = new JPanel(new FlowLayout());
	  JPanel pane1 = new JPanel(new FlowLayout());
	  JPanel pane0 = new JPanel(new FlowLayout());
	  setLayout(new BorderLayout());
	  add(pane1,BorderLayout.CENTER);
	  add(panel,BorderLayout.NORTH);
	  pane1.add(result);
	  pane1.add(t4);
	  panel.add(t1);
	  pane1.add(t3);
	  pane1.add(t2);
	  panel.add(f1);
	  panel.add(searchbtn);
	  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	  setSize(480,300);
	  setVisible(true);
	  
		  searchbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					 try {
					 	String key=f1.getText();
					    String url = "jdbc:mysql://127.0.0.1:3306/iii";
						Properties prop = new Properties();
						prop.setProperty("user", "root");
						prop.setProperty("password", "root");
						Connection conn = DriverManager.getConnection(url, prop);
						String sql = "SELECT * FROM t1 WHERE id LIKE ? ";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, "%" + key + "%");
						ResultSet rs = pstmt.executeQuery();//查詢並且回傳的方法
						while (rs.next()) {//將回傳的一條一條列出
								String v1 = rs.getString("id");
								String v2 = rs.getString("name");
								String v3=rs.getString("city");
								String v4 =rs.getString("position");
								result.setText(v1);
								t2.setText(v3);
								t3.setText(v2);
								t4.setText(v4);
			
						}
					 	}catch(Exception e1) {
							System.out.println(e1);
							}
		}
	  });
  }
}

