package xinChePing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class crawCarComment {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//		inputMysql("0", 0, "0");
//		System.exit(0);
		java.sql.Connection con=null;
		String sql;
		String mysqlUrl="";	//add your mysql url
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("succeed to drive mysql");
		con=DriverManager.getConnection(mysqlUrl);
		java.sql.Statement sta=con.createStatement();
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Users/sk/Desktop/x_x URL.txt")), "utf-8"));
		String line;
		while((line=br.readLine())!=null){
//		String url="http://www.xincheping.com/pingce/"+line+".html";				//最新车评网址
		int urlNum=Integer.parseInt(line);
		if(urlNum<931){														//第931页以后的网页形式不同
			String url="http://pingce.xincheping.com/pingce/"+line+"-1.html";		//车评网址
			String html=carHome.crawlcartype.crawl(url);
			Pattern pat=Pattern.compile("(?<=<a class=\\\"last-page\\\" href=\\\"http://pingce.xincheping.com/pingce/"+line+"-).*?(?=.html\\\">末页</a>)");
			Matcher mat=pat.matcher(html);
			mat.find();
			int pageNum=Integer.parseInt(mat.group());		//第一页获取评测页数
//			System.out.println(pageNum);
			Pattern pat1=Pattern.compile("(?<=<h1> 1.).*?(?=</h1>)");		//第一页匹配评测汽车名
			Matcher mat1=pat1.matcher(html);
			mat1.find();
//			System.out.println(mat1.group().replaceAll("(－|-|―|――).*", "").replaceAll("(试车评测|试驾评测)", "")
//					.replaceAll("评测", ""));
			String carName=mat1.group().replaceAll("(－|-|―|――).*", "").replaceAll("(试车评测|试驾评测)", "")
					.replaceAll("评测", "").replaceAll("试驾", "");
			for(int i=1;i<=pageNum;i++){
			if(i==1){
			Pattern pat2=Pattern.compile("(?<=车评概要</h3>).*?(?=</ul>)");			//第一页获取车评概要
			Matcher mat2=pat2.matcher(html);
			mat2.find();
			System.out.println(line+"."+i+":"+mat2.group().replaceAll(" ", "").replaceAll("</li>", "，").replaceAll("<.*?>", ""));
			String text=mat2.group().replaceAll(" ", "").replaceAll("</li>", "，").replaceAll("<.*?>", "");
//			inputMysql(carName,i,text);
			sql="insert into xinChePing(carName,di_1_ye) values('"+carName+"','"+text+"')";
			sta.executeUpdate(sql);
			}
			else {
			StringBuilder sb=new StringBuilder();
			String url1="http://pingce.xincheping.com/pingce/"+line+"-"+i+".html";
			String html1=carHome.crawlcartype.crawl(url1);
			Pattern pat3=Pattern.compile("(?<=<h1>).*?(?=</h1>)");				//第一页以后匹配每一页的车评题目
			Matcher mat3=pat3.matcher(html1);
			mat3.find();
			String outLine=mat3.group().replaceAll(".*-", "").replaceAll(" ", "");
//			sb.append(mat3.group().replaceAll(".*-", "").replaceAll(" ", ""));
//			sb.append(":");
//			System.out.println(i+"."+mat3.group().replaceAll(".*-", "").replaceAll(" ", ""));
			Pattern pat4=Pattern.compile("(?<=<div class=\\\"cp_text\\\" id=\\\"zwnr1\\\">).*(?=<!--车评概要-->)");//第一页以后匹配每一页的评测内容
			Matcher mat4=pat4.matcher(html1);
			mat4.find();
			String text=mat4.group().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll(" ", "")
					.replaceAll("'", "").replaceAll("／", "");
//			sb.append(mat4.group().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll(" ", "")
//					.replaceAll("'", "").replaceAll("／", ""));
//			inputMysql(carName,i,sb.toString());
			sql="update xinChePing set di_"+i+"_ye='"+outLine+":"+text+"' where carName='"+carName+"'";
			sta.executeUpdate(sql);
			System.out.println(line+"."+i+":"+mat4.group().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll(" ", "")
					.replaceAll("'", "").replaceAll("／", ""));
			}
		}
//			System.out.println(line);
			urlSave(line,"C:/Users/sk/Desktop/finish.txt");		//记录已经爬取的网页
		}
		else{
			String url="http://pingce.xincheping.com/pingce/qx"+line+"-1.html";
			String html=carHome.crawlcartype.crawl(url);
			Pattern pat1=Pattern.compile("(?<=<h1>).*?(?=</h1>)");
			Matcher mat1=pat1.matcher(html);
			mat1.find();
			Pattern pat4=Pattern.compile("(?<=<div class=\\\"cp_text\\\" id=\\\"zwnr1\\\">).*(?=<!--微信二维码 start-->)");//第一页以后匹配每一页的评测内容
			Matcher mat4=pat4.matcher(html);
			mat4.find();
			String text=mat4.group().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll(" ", "")
					.replaceAll("'", "").replaceAll("／", "").replaceAll("点击这里或用手机扫描以下二维码，可以收听车评人试驾本车后的真人语音感受哦。", "")
					.replaceAll("【听听车评人怎么(“说”|说)】", "").replaceAll("(撰文、摄影：).*(手机版用户|手机用户)", "")
					.replaceAll("(【新车评).*(敬请知悉。)", "").replaceAll("【新车评网推荐等级】", "").replaceAll("(撰文).*(@新车评)", "");
			sql="insert into newChePing(topic,text) values('"+mat1.group()+"','"+text+"')";
			sta.executeUpdate(sql);
			System.out.println(line);
			urlSave(line,"C:/Users/sk/Desktop/finish.txt");		//记录已经爬取的网页
		}
			}	
	}
	public static void urlSave(String string,String filepath) throws IOException{	//记录已经爬取的网页
		FileWriter fw=new FileWriter(filepath,true);
		PrintWriter pw=new PrintWriter(fw);
		pw.println(string);
		pw.close();
		fw.close();
	}
	
	public static void inputMysql(String carName,int columNum,String value) throws ClassNotFoundException, SQLException{
		java.sql.Connection con=null;
		String sql;
//		String mysqlUrl="jdbc:mysql://192.168.235.36:3306/lime?"+"user=lime&password=lime&useUnicode=true&"
//				+ "CharacterEncoding=UTF8";
		String mysqlUrl="jdbc:mysql://127.0.0.1:3306/baseinformation?"+"user=root&password=553352836w&useUnicode=true&characterEncoding=UTF8";
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("succeed to drive mysql");
		con=DriverManager.getConnection(mysqlUrl);
		java.sql.Statement sta=con.createStatement();
		for(int i=0;i<=40;i++){
		sql="alter table xinChePing add column di_"+i+"_ye text CHARACTER SET utf8";
		sta.executeUpdate(sql);}
//		if(columNum==1){
//		sql="insert into xincheping(carName,di_1_ye) values('"+carName+"','"+value+"')";
//		sta.executeUpdate(sql);		
//		}
//		else{
//		sql="update xincheping set di_"+columNum+"_ye="+value+" where carName='"+carName+"'";
//		sta.executeUpdate(sql);}
	}
}
