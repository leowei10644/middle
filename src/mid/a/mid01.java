package mid.a;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mid.b.BCrypt;
import mid.b.mid02;
import mid.b.mid03;
import mid.b.mid04;

public class mid01  extends JFrame{
	
	private final static String USER = "root"; 
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/iii";
	private static Connection conn;
	private JLabel acout,pwod;
    private JTextField JTname;
    private JPasswordField JTpwd;
    private JButton JBacc,JBexit,JBlog,JBdel,jdelrow,test,text2,adddata;
    private mid02 m02;
    private mid03 m03;
    private mid04 m04; 
		
	
    
    
    
    public mid01() throws Exception{
        super("Login-System");
        acout = new JLabel("Account：");//设置Label和按钮名
        pwod = new JLabel("Passwd：");
        JTname = new JTextField(6);
        JTpwd = new JPasswordField(6);
        JBacc = new JButton("Login");
        JBlog = new JButton("Reg");
        JBexit = new JButton("Exic");
        JBdel=new JButton("	Del acount");
        test=new JButton("Search");
        text2=new JButton("Delrow");
        adddata=new JButton("Sum");
        JPanel top =new JPanel(new FlowLayout());
        JPanel del =new JPanel(new FlowLayout());
        setLayout(new BorderLayout());
        add(top,BorderLayout.NORTH);
        add(del,BorderLayout.SOUTH);
        del.add(test);
        del.add(text2);
        del.add(adddata);
        top.add(acout,BorderLayout.EAST);
        top.add(JTname,BorderLayout.EAST);
        top.add(pwod,BorderLayout.EAST);
        top.add(JTpwd,BorderLayout.EAST);
        top.add(JBacc);
        top.add(JBexit);
        top.add(JBlog);
        top.add(JBdel);
        getContentPane().setBackground(Color.DARK_GRAY);
		setSize(600, 350);
    	setLocation(300, 300);
    	setVisible(true);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    	init();
    	
    }
    	private void init() {
    		adddata.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
					m04=new mid04();
					JScrollPane jpw=new JScrollPane(m04);
					add(jpw,BorderLayout.CENTER);	
					setVisible(true);
			    	setDefaultCloseOperation(EXIT_ON_CLOSE);
					
					}catch(Exception e1) {
						System.out.println(e1);
					}
				}
    		
			});
    		
    	text2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
						m02.delRow();
			}
		});
 
        JBacc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String accoun=JTname.getText();
					if(log(accoun)) {
						JOptionPane.showMessageDialog(null,accoun+"登入成功，歡迎回來!!");
						
						m02=new mid02();
						JScrollPane jsp = new JScrollPane(m02);//卷軸面板
						add(jsp, BorderLayout.CENTER);
				    	setVisible(true);
				    	setDefaultCloseOperation(EXIT_ON_CLOSE);
						}else {
						JOptionPane.showMessageDialog(null,"帳號輸入錯誤，請再試一次");
						}
				}catch (Exception e1) {
					System.out.println(e);				
					}
			}
		});
		

        JBexit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(null,"確定要離開嗎?");
                System.exit(0);
            }
        });
        
        JBlog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String accoun=JTname.getText();
					String pwd=JTpwd.getText();
					if(checkaccount(accoun, pwd)) {
						if(register(accoun, pwd)) {	
							if(accoun.length()==0 || pwd.length()==0) {
								JOptionPane.showMessageDialog(null,"不可為空白!!");
							}else{
								JOptionPane.showMessageDialog(null,"註冊成功");

							}
						}else {
							JOptionPane.showMessageDialog(null,"註冊失敗");
						}
					}else {
						JOptionPane.showMessageDialog(null,"帳號已存在");
					}
				}catch(Exception e1) {
					System.out.println(e1);
				}
			}
		});
        
        JBdel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String accoun=JTname.getText();
				String pwd=JTpwd.getText();
					if(del(accoun,pwd)){
						JOptionPane.showMessageDialog(null,"帳號註銷成功");
					}else {
					JOptionPane.showMessageDialog(null,"帳號註銷失敗");
					}
				}catch(Exception e2){
					System.out.println(e2);
				}
			}
		});
        test.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				m03=new mid03();
			}
		});
  }  	        
	private boolean log(String acc) throws Exception {
		boolean ret=false;
        Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);	
        conn = DriverManager.getConnection(URL,prop);
		String sql="SELECT count(*) count FROM leo WHERE account = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, acc);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		ret=rs.getInt("count") ==1;
		pstmt.close();
		return ret;
	}	
	
	private boolean checkaccount(String acc,String pwd) throws Exception {
		boolean ret=false;
        Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);	
        conn = DriverManager.getConnection(URL,prop);
		String sql="SELECT count(*) count FROM leo WHERE account = ?  ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, acc);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		ret=rs.getInt("count") ==0;
		pstmt.close();
		return ret;
	}	
	
	private static boolean register(String acc,String pwd) throws Exception {
		boolean isOK = false;//不能=0否則不能建新帳號
		Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);	
        conn = DriverManager.getConnection(URL,prop);
		String sql="INSERT INTO leo (account,password) VALUES (?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, acc);
		pstmt.setString(2, BCrypt.hashpw(pwd, BCrypt.gensalt()));
		//加密
		isOK = pstmt.executeUpdate() != 0;
		pstmt.close();
		return isOK;
	}
	private boolean del(String acc,String pwd) throws Exception{
		boolean ret =false;
		Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);	
        conn = DriverManager.getConnection(URL,prop);
        String sql="delete from leo  WHERE account = ?or password=? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, acc);
        pstmt.setString(2,BCrypt.hashpw(pwd, BCrypt.gensalt()));
        ret=pstmt.executeUpdate() !=0;
        pstmt.close();
		return ret;
	}
	
	public static void main (String []args){
		try {
			new mid01();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	