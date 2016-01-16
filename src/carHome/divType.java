package carHome;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class divType {
public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
//	Connection con=null;
//	ResultSet rs;
//	String mysqlUrl="";
//	Class.forName("com.mysql.jdbc.Driver");
//	System.out.println("加载mysql驱动成功");
//	con=(Connection) DriverManager.getConnection(mysqlUrl);
//	Statement sta=(Statement) con.createStatement();
//	String sql;Connection con=null;
	Connection con=null;
	String sql;
	String mysqlUrl="";//add your mysql url
	Class.forName("com.mysql.jdbc.Driver");
	System.out.println("成功加载MySQL驱动");
	con=(Connection) DriverManager.getConnection(mysqlUrl);
	java.sql.Statement sta=con.createStatement();	
	ResultSet rs;
	String []carType={"传祺","广汽丰田","广汽本田","菲亚特","广汽三菱","吉奥","比亚迪"};
//	for(int i=0;i<carType.length;i++){
//		sql="create table divCarType"+i+"(id INT NOT NULL AUTO_INCREMENT,brand longtext,serie longtext,place longtext,time longtext,comment longtext,PRIMARY KEY(id))";
//		sta.executeUpdate(sql);
//		}
//	System.exit(0);
	for(int num=404721;num<=600121;num++){
		sql="select * from comment_carhome where id ="+num;
		 rs=sta.executeQuery(sql);
		 if (rs.next()){				//不能在next()前对rs进行取值
			 String value=rs.getString("brand");
		for (int count=0;count<carType.length;count++){
			if(value.contains(carType[count])){
				sql="select * from comment_carhome where id="+num;
				rs=sta.executeQuery(sql);
				if(rs.next()){
				String brand=rs.getString(3);
				String serie=rs.getString(4);
				String place=rs.getString(6);
				String time=rs.getString(7);
				String a=rs.getString(8);
				String b=rs.getString(9);
				String c=rs.getString(10);
				String d=rs.getString(11);
				String e=rs.getString(12);
				String f=rs.getString(13);
				String g=rs.getString(14);
				String h=rs.getString(15);
				String i=rs.getString(16);
				String j=rs.getString(17);
				String k=rs.getString(18);
				String aa=a+b+c+d+e+f+g+h+i+j+k;
				sql="insert into divCarType"+count+" (brand,serie,place,time,comment) values('"+
						brand+"','"+serie+"','"+place+"','"+time+"','"+aa.replaceAll("null;|null", "")+"')";
				sta.executeUpdate(sql);
				numSave(num+" "+value+" num:"+count, "C:/Users/sk/Desktop/record.txt");
//				rscord[count]++;
				System.out.println("succeed "+num+" "+value+" "+count);
				}
			}
		}
	}
}
}
public static void numSave(String string,String filepath) throws IOException{	//记录已经过滤的数据
	FileWriter fw=new FileWriter(filepath,true);
	PrintWriter pw=new PrintWriter(fw);
	pw.println(string);
	pw.close();
	fw.close();
}
}
