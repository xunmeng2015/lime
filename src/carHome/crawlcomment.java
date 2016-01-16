package carHome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 
 * 爬取汽车之家评论
 * 
 * 
 */

public class crawlcomment{
public static void main(String[] args) throws Exception{
	java.sql.Connection conn=null;
	String sql;
	String mysqlUrl="";//add your msyql url;
	Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
    System.out.println("成功加载MySQL驱动程序");
    conn = DriverManager.getConnection(mysqlUrl);
    java.sql.Statement stmt = conn.createStatement();
    sql = "create table comment_carHome(level longtext CHARACTER SET utf8,brand longtext CHARACTER SET utf8,serie longtext CHARACTER SET utf8,model longtext CHARACTER SET utf8,place longtext CHARACTER SET utf8,time longtext CHARACTER SET utf8,zui_man_yi longtext CHARACTER SET utf8,"
    		+ "zui_bu_man_yi longtext CHARACTER SET utf8,kong_jian longtext CHARACTER SET utf8,dong_li longtext CHARACTER SET utf8,cao_kong longtext CHARACTER SET utf8,"
    		+ "you_hao longtext CHARACTER SET utf8,shu_shi_xing longtext CHARACTER SET utf8,wai_guan longtext CHARACTER SET utf8,nei_shi longtext CHARACTER SET utf8,"
    		+ "xing_jia_bi longtext CHARACTER SET utf8,other longtext CHARACTER SET utf8,tu_cao longtext CHARACTER SET utf8)";
    stmt.executeUpdate(sql);

	
	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Users/sk/Desktop/url1.txt")),"utf-8"));
	String line;
	while ((line=br.readLine())!=null){
	String url="http://k.autohome.com.cn/"+line+"/index_1.html";
		String html=crawl(url);

		Pattern pat=Pattern.compile("(?<=<span class=\\\"fn-right c999\\\">).*?(?=<\\/span>)");//匹配口碑数目，若为0则跳转
		Matcher mat=pat.matcher(html);
		
		if (mat.find()){
			
			System.out.println(mat.group());
			int num=Integer.parseInt(mat.group().replace("共有", "").replace("条口碑", ""));
			
			if (num!=0){
				int pageNum=(num/15)+1;
				String str3,str4,str5,str6,str7,str8,str9,str44,str55;
				String str10,str11,str12,str13,str14,str15,str16;
				for (int i=1;i<=pageNum;i++){
					String url1="http://k.autohome.com.cn/"+line+"/index_"+i+".html";
					String html1=crawl(url1);
				Pattern pat1=Pattern.compile("(?<=当前位置：<a href=\\\"http:\\/\\/www.autohome.com.cn\\/\\\" >首页<\\/a>).*?(?=<a href=\\\""
						+ "http:\\/\\/www.autohome.com.cn\\/\\d*\\/\\\" >)");						//匹配汽车所属类型
				Pattern pat2=Pattern.compile("(?<=<div class=\\\"choose-con mt-10\\\">).*?(?=<div class=\\\"allcont border-b-solid\\\">)");//匹配评论结构体
				Pattern pat33=pat.compile("(?<=<a target=\\\"_self\\\" href=\\\"http:\\/\\/www.autohome.com.cn\\/"+line+"\\/\\\">).*?(?=-)");//匹配所属汽车品牌
				Pattern pat3=Pattern.compile("(?<=<a href=\\\"\\/"+line+"\\\" target=\\\"_blank\\\">).*?(?=<\\/a>)");//匹配购买车名
				Pattern pat4=Pattern.compile("(?<=<span class=\\\'font-arial\\\'>).*?(?=<\\/span>)");//匹配购买款式
				Pattern pat44=Pattern.compile("(?<=<dd class=\\\"c333\\\">).*?(?=&nbsp)");//匹配购买地点
				Pattern pat55=Pattern.compile("(?<=<dt>购买时间<\\/dt>).*?(?=<\\/dd>)");//匹配购买时间
				Pattern pat5=Pattern.compile("(?<=【最满意的一点】).*?(?=<br\\/>【最不满意的一点】)");//匹配最满意的一点评论
				Pattern pat6=Pattern.compile("(?<=【最不满意的一点】).*?(?=<br\\/>【)");//匹配最不满意的一点评论
				Pattern pat7=Pattern.compile("(?<=【空间】).*?(?=<br\\/>【)");//匹配空间评论
				Pattern pat8=Pattern.compile("(?<=【动力】).*?(?=<br\\/>【)");//匹配动力评论
				Pattern pat9=Pattern.compile("(?<=【操控】).*?(?=<br\\/>【)");//匹配操控评论
				Pattern pat10=Pattern.compile("(?<=【油耗】).*?(?=<br/>【)");//匹配油耗评论
				Pattern pat11=Pattern.compile("(?<=【舒适性】).*?(?=<br\\/>【)");//匹配舒适性评论
				Pattern pat12=Pattern.compile("(?<=【外观】).*?(?=<br\\/>【)");//匹配外观评论
				Pattern pat13=Pattern.compile("(?<=【内饰】).*?(?=<br\\/>【)");//匹配内饰外观
				Pattern pat14=Pattern.compile("(?<=【性价比】).*?(?=<br\\/>)");//匹配性价比评论
				Pattern pat15=Pattern.compile("(?<=【其它描述】).*?(?=<\\/div>)");//匹配其他描述评论
				Pattern pat16=Pattern.compile("(?<=【吐槽】).*?(?=<\\/div>)");//匹配吐槽评论
				
				Matcher mat1=pat1.matcher(html1);
				mat1.find();
				Matcher mat33=pat33.matcher(html1);
				mat33.find();
				Matcher mat2=pat2.matcher(html1);
				while(mat2.find()){
					try {
						
					String str=mat2.group();
					Matcher mat3=pat3.matcher(str);			
					Matcher mat4=pat4.matcher(str);
					Matcher mat44=pat44.matcher(str);
					Matcher mat55=pat55.matcher(str);
					Matcher mat5=pat5.matcher(str);
					Matcher mat6=pat6.matcher(str);
					Matcher mat7=pat7.matcher(str);
					Matcher mat8=pat8.matcher(str);
					Matcher mat9=pat9.matcher(str);
					Matcher mat10=pat10.matcher(str);
					Matcher mat11=pat11.matcher(str);
					Matcher mat12=pat12.matcher(str);
					Matcher mat13=pat13.matcher(str);
					Matcher mat14=pat14.matcher(str);
					Matcher mat15=pat15.matcher(str);
					Matcher mat16=pat16.matcher(str);
					
					if(mat3.find())		str3=mat3.group().replaceAll("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str3=null;
					if(mat4.find()) 	str4=mat4.group().replaceAll("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str4=null;
					if(mat44.find()) 	str44=mat44.group().replace(" ", "").replaceAll("'", "").replaceAll("／", "");
					else str44=null;
					if(mat55.find()) 	str55=mat55.group().replaceAll("<.*>", "").replace(" ", "").replaceAll("'", "").replaceAll("／", "");
					else str55=null;
					if(mat5.find())		str5=mat5.group().replaceAll("<br\\/>", "");
					else str5=null;
					if(mat6.find())		str6=mat6.group().replaceAll("<.*>", "").replaceAll("<br\\/>", "").replaceAll("'", "").replaceAll("／", "");
					else str6=null;
					if(mat7.find())		str7=mat7.group().replaceAll("<br\\/>", "").replaceAll("'", "").replaceAll("／", "");
					else str7=null;
					if(mat8.find())		str8=mat8.group().replaceAll("<br\\/>", "").replaceAll("'", "").replaceAll("／", "");
					else str8=null;
					if(mat9.find())		str9=mat9.group().replaceAll("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str9=null;
					if(mat10.find())		str10=mat10.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str10=null;
					if(mat11.find())		str11=mat11.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str11=null;
					if(mat12.find())		str12=mat12.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str12=null;
					if(mat13.find())		str13=mat13.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str13=null;
					if(mat14.find())		str14=mat14.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str14=null;
					if(mat15.find())		str15=mat15.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str15=null;
					if(mat16.find())		str16=mat16.group().replace("<br/>", "").replaceAll("'", "").replaceAll("／", "");
					else str16=null;
				sql="insert into comment_carHome(level,brand,serie,model,place,time,"
						+ "zui_man_yi,zui_bu_man_yi,kong_jian,dong_li,cao_kong,you_hao,"
						+ "shu_shi_xing,wai_guan,nei_shi,xing_jia_bi,other,tu_cao) values('"+mat1.group().replaceAll("&nbsp;&gt;&nbsp;", "").replace("</a>", "").replaceAll("<.*>", "")+"','"
								+ mat33.group()+"','"+str3+"','"+str4+"','"+str44+"','"+str55+"','"+str5+"','"+str6+"','"+str7+"','"+str8+"','"
								+str9+"','"+str10+"','"+str11+"','"+str12+"','"+str13+"','"+str14+"','"+str15+"','"+str16+"')";
			stmt.executeUpdate(sql);
					} catch (Exception e) {
						continue;
					}
				}
				System.out.println(line+":"+i+"/"+pageNum);
			}
			}
		}
		System.out.println(line);
		}
//		FileWriter fw=new FileWriter("C:/Users/sk/Desktop/url1.txt",true);
//		PrintWriter pw=new PrintWriter(fw);
//		pw.println(j);
//		pw.close();
//		fw.close();
//	}
//	}
}


public static String crawl(String urlString) throws IOException{			//获取网页源码
	URL url=new URL(urlString);
	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	StringBuilder sb=new StringBuilder();
//	int code=new Integer(conn.getResponseCode());
//	System.out.println(code);
//	if (code==200){
	BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"gb2312"));
	String line;
	while ((line=br.readLine())!=null){
		sb.append(line);
	}
//	}
//	else sb.append("error");
	return sb.toString();
}


public static int crawlcode(String urlString) throws IOException{			//获取网页返回码
	URL url=new URL(urlString);
	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	int code=new Integer(conn.getResponseCode());
	System.out.println(code);
	return code;
}
}