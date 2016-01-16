package carHome;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class crawlcartype{
public static void main(String[] args) throws Exception{

//	String html=getHtml(url);
//	System.out.println(html);
//	Pattern pat=Pattern.compile("(?<=<dd>).*?(?=<\\/dd>)");
//	Matcher mat=pat.matcher(html);
//	while (mat.find()){
//		System.out.println(mat.group());
//		String str=mat.group();
//		Pattern pat1=Pattern.compile("(?<=div class=\\\"h3-tit\\\">).*?(?=<\\/div>)");
//		Matcher mat1=pat1.matcher(str);
//		Pattern pat2=Pattern.compile("(?<=levelsource=000000000_0&pvareaid=101594\\\">).*?(?=<\\/a>)");
//		Matcher mat2=pat2.matcher(str);
//		while (mat1.find()){
//		while (mat2.find()){
//			System.out.println(mat2.group());
//			i++;
//			sql="insert into carType(carName,Type) values('"+mat1.group()+"','"+mat2.group()+"')";
//			stmt.executeUpdate(sql); 
//		}
//	}
//		Pattern pat3=Pattern.compile("(?<=levelsource=000000000_0&pvareaid=101594\\\" class=\\\"greylink\\\">).*?(?=<\\/a>)");
//		Matcher mat3=pat3.matcher(str);
//		while(mat3.find()){
//			System.out.println(mat3.group());
//			i++;
//			sql="insert into carType(carName,Type) values('"+mat1.group()+"','"+mat3.group()+"')";
//			stmt.executeUpdate(sql);
//		}
//	}
//	}
//	c++;
//	}
//	System.out.println(i);
}


public static String crawl(String urlString) throws IOException{
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


public static int crawlcode(String urlString) throws IOException{
	URL url=new URL(urlString);
	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	int code=new Integer(conn.getResponseCode());
//	System.out.println(code);
	return code;
}
}
