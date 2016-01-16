package carHome;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class divPlace {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		String pro[]={"广州","深圳","佛山","东莞","中山","珠海","江门","肇庆","惠州","汕头","潮州","揭阳","汕尾","湛江","茂名","阳江","韶关","清远",
				"云浮","梅州","河源"};
		Connection con=null;
		String sql;
		String mysqlUrl="";	//add your mysql url
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("成功加载MySQL驱动");
		con=(Connection) DriverManager.getConnection(mysqlUrl);
		java.sql.Statement sta=con.createStatement();	
		ResultSet re;
//		for(int i=0;i<pro.length;i++){
//		sql="create table buyPlace"+i+"(brand longtext,serie longtext,place longtext,comment longtext)";
//		sta.executeUpdate(sql);
//		}
//		System.exit(0);
//		int []record=new int[21];
//		String resultNum = null;
		for(int num=1;num<600121;num++){
			sql="select * from comment_carhome where id ="+num;
			 re=sta.executeQuery(sql);
			 if (re.next()){				//不能在next()前对re进行取值
				 String value=re.getString("place");
			for (int count=0;count<pro.length;count++){
				if(value.contains(pro[count])){
					sql="select * from comment_carhome where id="+num;
					re=sta.executeQuery(sql);
					if(re.next()){
					String brand=re.getString(3);
					String serie=re.getString(4);
					String place=re.getString(6);
					String a=re.getString(8);
					String b=re.getString(9);
					String c=re.getString(10);
					String d=re.getString(11);
					String e=re.getString(12);
					String f=re.getString(13);
					String g=re.getString(14);
					String h=re.getString(15);
					String i=re.getString(16);
					String j=re.getString(17);
					String k=re.getString(18);
					String aa=a+b+c+d+e+f+g+h+i+j+k;
					sql="insert into buyPlace"+count+" (brand,serie,place,comment) values('"+
							brand+"','"+serie+"','"+place+"','"+aa.replaceAll("null;|null", "")+"')";
					sta.executeUpdate(sql);
					numSave(num+" "+value+" num:"+count, "C:/Users/sk/Desktop/record.txt");
//					record[count]++;
					System.out.println("succeed "+num+" "+value+" "+count);
					}
				}
			}
		}
	}
//		for(int j=0;j<record.length;j++){
//			numSave(record[j]+"","C:/Users/sk/Desktop/record.txt");
//			System.out.print(record[j]+" ");
//		}
	}
	public static void numSave(String string,String filepath) throws IOException{	//记录已经过滤的数据
		FileWriter fw=new FileWriter(filepath,true);
		PrintWriter pw=new PrintWriter(fw);
		pw.println(string);
		pw.close();
		fw.close();
	}
}
