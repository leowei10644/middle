package mid.b;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class mid02 extends JTable {//功能
	private MyModel myModel;
	private gifdb db;
	private final static String USER = "root"; 
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/iii";
	private Connection conn;
	private ResultSet rs;
	private String[] fieldNames;
	
	public mid02() throws Exception {
		
		db = new gifdb();
		db.queryData("SELECT * FROM t1");
		
		myModel = new MyModel();
		setModel(myModel);
		myModel.setColumnIdentifiers(db.getHeader());
		
	}
	
	public void delRow() {//刪除資料列數值
		myModel.removeRow(getSelectedRow());
		repaint();//更新畫面
	}
	public class gifdb {//資料庫
	public gifdb() throws SQLException {
		Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);
		conn = DriverManager.getConnection(URL, prop);
	}
	
	public void queryData(String sql) throws SQLException{
		Statement stmt = 
			conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery(sql);
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();//
		fieldNames = new String[colCount];
		for (int i=0; i<colCount; i++) {
			fieldNames[i] = rsmd.getColumnName(i+1);//取得指定之資料行的名稱
		}//i從0開始，get進來的從1開始=>所以i+1
	}
	
	public int getRows() {//取得資料行總數
		try {
			rs.last();
			return rs.getRow();
		}catch(Exception e) {
			return 0;
		}
	}
	
	public int getCols() {
		return fieldNames.length;
	}
	
	public String getData(int row, int col) {
		try {
			rs.absolute(row);
			String data = rs.getString(col);
			return data;
		}catch(Exception e) {
			System.out.println(e);
			return "XX";
		}
	}
	
	public String[] getHeader() {
		return fieldNames;
	}
	
	public void updateData(int row, int col, String data) {
		try {
			rs.absolute(row);
			rs.updateString(col, data);
			rs.updateRow();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void delRow(int row) {
		try {
			rs.absolute(row);
			rs.deleteRow();
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}

	
	
	
}
	private class MyModel extends DefaultTableModel {

		@Override
		public int getRowCount() {//呼叫得到行數
			return db.getRows();
		}


		@Override
		public int getColumnCount() {//呼叫得到列數
			return db.getCols();
		}

		@Override
		public Object getValueAt(int row, int column) {
			return db.getData(row+1, column+1);
		}//呼叫喊回行列

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			db.updateData(row+1, column+1, (String)aValue);
		}//呼叫更新

		@Override
		public boolean isCellEditable(int row, int column) {
			return column > 0;//必須更新大於0否則更新失敗
		}

		@Override
		public void removeRow(int row) {
			db.delRow(row+1);//
		}
	}

}

