package com.senti.serivce.Impl;

import com.senti.serivce.Database;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Service
public class DatabaseImpl implements Database {

    private static DatabaseImpl userdao=new DatabaseImpl();
    private Connection conn = null;
    PreparedStatement statement = null;

    public static DatabaseImpl getInstance()
    {
        return userdao;
    }

    //连接数据库
    public void connSQL() {
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&useSSL=false";

        String username = "root";
        String password = "973348"; // 加载驱动程序以连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection( url,username, password );
        }
        //捕获加载驱动程序异常
        catch ( ClassNotFoundException cnfex ) {
            System.err.println(
                    "装载 JDBC/ODBC 驱动程序失败。" );
            cnfex.printStackTrace();
        }
        //捕获连接数据库异常
        catch ( SQLException sqlex ) {
            System.err.println( "无法连接数据库" );
            sqlex.printStackTrace();
        }
    }

    //关闭数据库
    public void deconnSQL() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.out.println("关闭数据库问题 ：");
            e.printStackTrace();
        }
    }

    //select语句
    public ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /*更新数据库*/
    public boolean update(String sql) {
        try {
//            connSQL();
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
//            deconnSQL();
            return true;
        }catch (Exception e) {
            System.out.println("数据已存在或数据存入数据库错误");
        }
        return false;
    }

    //通过输入的网址来获取derby数据库中数据
    public ArrayList<ArrayList<String>> getDerbyComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from firsttable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Derby数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getDroolsComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from droolstable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Drools数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getGroovyComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from groovytable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Groovy数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getIspnComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from ispntable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Ispn数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getMngComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from mngtable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Mng数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getPigComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from pigtable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取Pig数据失败");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getJbseamComment(int index){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from jbseamtable where issueNo = '"+index+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取JBSEAM数据失败");
        }
        return result;
    }

    //此处的url格式如https://github.com/TheAlgorithms/Java/issues/727)
    public ArrayList<ArrayList<String>> getGithubComment(String issueWeb){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from githubtable where url = '"+issueWeb+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(rs.getString("url"));
                temp.add(rs.getString("issueName"));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(rs.getString("type"));
                temp.add(Integer.toString(rs.getInt("Number")));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();
        }catch(Exception e) {
            System.out.println("获取github数据失败");
        }
        return result;
    }

    //type格式为https://github.com/TheAlgorithms/Java,获取相同issue的评论
    public ArrayList<ArrayList<String>> getSameTypeIssue(String type){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from gitissue where type = '"+type+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(rs.getString("type"));
                temp.add(rs.getString("issueWeb"));
                result.add(temp);
            }
            deconnSQL();
        }catch(Exception e) {
            System.out.println("获取github相同issue失败");
        }
        return result;
    }

    //name为DERBY等等
    public ArrayList<ArrayList<String>> getAllComment(String name){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql=null;
            if(name.equals("DERBY")) {
                sql="select * from firsttable;";
            }if(name.equals("DROOLS")) {
                sql="select * from droolstable;";
            }if(name.equals("GROOVY")) {
                sql="select * from groovytable;";
            }if(name.equals("ISPN")) {
                sql="select * from ispntable;";
            }if(name.equals("MNG")) {
                sql="select * from mngtable;";
            }if(name.equals("PIG")) {
                sql="select * from pigtable;";
            }if(name.equals("JBSEAM")) {
                sql="select * from jbseamtable;";
            }
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();

        }catch(Exception e) {
            System.out.println("获取"+name+"数据失败");
        }
        return result;
    }

    //输入网址的所有issues
    public ArrayList<ArrayList<String>> getAllGitComment(String type){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="select * from githubtable where type ='"+type+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add(rs.getString("url"));
                temp.add(rs.getString("issueName"));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add(rs.getString("type"));
                temp.add(Integer.toString(rs.getInt("Number")));
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();
        }catch(Exception e) {
            System.out.println("获取对应类型的github issue失败");
        }
        return result;
    }

    /*返回Derby等数据库单个issue内按照评论顺序的评论*/
    public ArrayList<ArrayList<String>> getLIn_order(String name, int issueNo){
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        try {
            connSQL();
            String sql="";
            if(name.equals("DERBY")) {
                sql="select * from firsttable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("DROOLS")) {
                sql="select * from droolstable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("GROOVY")) {
                sql="select * from groovytable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("ISPN")) {
                sql="select * from ispntable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("MNG")) {
                sql="select * from mngtable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("PIG")) {
                sql="select * from pigtable where issueNo='"+issueNo+"' order by datetime;";
            }else if(name.equals("JBSEAM")) {
                sql="select * from jbseamtable where issueNo='"+issueNo+"' order by datetime;";
            }
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> temp=new ArrayList<String>();
                temp.add("tianchong");//用于占位置，使得Derby等数据库中人名等信息的位置与GitHub一致
                temp.add(Integer.toString(rs.getInt("issueNo")));
                temp.add(rs.getString("personName"));
                temp.add(rs.getString("datetime"));
                temp.add(rs.getString("comment"));
                temp.add("tianchong");
                temp.add("tianchong");
                temp.add(Integer.toString(rs.getInt("activeScore")));
                temp.add(Integer.toString(rs.getInt("negativeScore")));
                result.add(temp);
            }
            deconnSQL();
        }catch (Exception e) {
            System.out.println(name+"时间排序失败");
        }
        return result;
    }


    public ArrayList<String> getUser(String name){
        ArrayList<String> result= new ArrayList<String>();
        try{
            connSQL();
            String sql="select * from user where name ='"+name+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                result.add(String.valueOf(rs.getInt("userid")));
                result.add(rs.getString("name"));
                result.add(rs.getString("password"));
            }
            deconnSQL();
        }catch(Exception e){
            System.out.println("获取用户信息失败");
            e.printStackTrace();
        }
        return result;
    }

    public boolean updatehistory(int userid, String url, String time){
        String sql="insert into history values('"+userid+"', '"+url+"', '"+time+"')";//存入history表中
        if(update(sql))
            return true;
        return false;
    }

    public boolean updateYear(int userid, String url, String time, String yeartop, int number, String description){
        String sql="insert into historyofyear values('"+userid+"', '"+url+"', '"+time+"', '"+yeartop+"', '"+number+"', '"+description+"')";//存入historyofyear表中
        System.out.println(sql);
        if(update(sql))
            return true;
        return false;
    }

    public boolean updateMonth(int userid, String url, String time, String monthtop, int number, String description){
        String sql="insert into historyofmonth values('"+userid+"', '"+url+"', '"+time+"', '"+monthtop+"', '"+number+"', '"+description+"')";//存入historyofyear表中
        if(update(sql))
            return true;
        return false;
    }

    //查找历史记录
    public ArrayList<ArrayList<String>> getHistory(int userid){
        ArrayList<ArrayList<String>> res= new ArrayList<ArrayList<String>>();
        try{
            connSQL();
            String sql="select * from history where userid ='"+userid+"' order by time;";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> result=new ArrayList<String>();
                result.add(String.valueOf(rs.getInt("userid")));
                result.add(rs.getString("url"));
                result.add(rs.getString("time"));
                res.add(result);
            }
            deconnSQL();
        }catch(Exception e){
            System.out.println("获取历史搜索记录失败");
            e.printStackTrace();
        }
        return res;
    }

    //查找历史年度
    public ArrayList<ArrayList<String>> getHistoryYear(int userid, String url, String time){
        ArrayList<ArrayList<String>> res= new ArrayList<ArrayList<String>>();
        try{
            connSQL();
            String sql="select * from historyofyear where userid ='"+userid+"' and url='"+url+"' and time='"+time+"' order by number;";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> result=new ArrayList<String>();
                result.add(String.valueOf(rs.getInt("userid")));
                result.add(rs.getString("url"));
                result.add(rs.getString("time"));
                result.add(rs.getString("yeartop"));
                result.add(rs.getString("number"));
                result.add(rs.getString("description"));
                res.add(result);
            }
            deconnSQL();
        }catch(Exception e){
            System.out.println("获取历史年度失败");
            e.printStackTrace();
        }
        return res;
    }

    //查找历史月度
    public ArrayList<ArrayList<String>> getHistoryMonth(int userid, String url, String time){
        ArrayList<ArrayList<String>> res= new ArrayList<ArrayList<String>>();
        try{
            connSQL();
            String sql="select * from historyofmonth where userid ='"+userid+"' and url='"+url+"' and time='"+time+"' order by number;";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> result=new ArrayList<String>();
                result.add(String.valueOf(rs.getInt("userid")));
                result.add(rs.getString("url"));
                result.add(rs.getString("time"));
                result.add(rs.getString("monthtop"));
                result.add(rs.getString("number"));
                result.add(rs.getString("description"));
                res.add(result);
            }
            deconnSQL();
        }catch(Exception e){
            System.out.println("获取历史月度失败");
            e.printStackTrace();
        }
        return res;
    }

    //查找月度图评论
    public ArrayList<String> getHistoryComment(int userid, String url, String time){
        ArrayList<String> res=new ArrayList<String>();
        try{
            connSQL();
            String sql="select * from historyofchart where userid ='"+userid+"' and url='"+url+"' and time='"+time+"';";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                res.add(rs.getString("chartcomment"));
            }
            deconnSQL();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("月度图评论失败");
        }
        return res;
    }

    public boolean insertHistoryComment(int userid, String url, String time, String comment, String commenttime){
        String sql="insert into historyofchart values('"+userid+"', '"+url+"', '"+time+"', '"+comment+"', '"+commenttime+"')";//存入historyofchart表中
        if(update(sql))
            return true;
        return false;
    }

    public boolean updateHistoryComment(int userid, String url, String time, String comment, String commenttime){
        String sql="update historyofchart set chartcomment='"+comment+"', commenttime='"+commenttime+"' where userid='"+userid+"' and url='"+url+"' and time='"+time+"';";
        if(update(sql))
            return true;
        return false;
    }

    //查找近期记录
    public ArrayList<ArrayList<String>> getRecent(int userid){
        ArrayList<ArrayList<String>> res= new ArrayList<ArrayList<String>>();
        try{
            connSQL();
            String sql="select * from historyofchart where userid ='"+userid+"' order by commenttime desc;";
            ResultSet rs=selectSQL(sql);
            while(rs.next()) {
                ArrayList<String> result=new ArrayList<String>();
                result.add(String.valueOf(rs.getInt("userid")));
                result.add(rs.getString("url"));
                result.add(rs.getString("time"));
                result.add(rs.getString("commenttime"));
                res.add(result);
            }
            deconnSQL();
        }catch(Exception e){
            System.out.println("获取近期项目失败");
            e.printStackTrace();
        }
        return res;
    }
}




