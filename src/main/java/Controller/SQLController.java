package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLController {
    private final String DBPath = "jdbc:h2:/" + System.getProperty("user.dir") + "\\bin\\db\\FeedWall";
    private Connection connection;
    private boolean isOpened = false;
    private Statement statement;

    public SQLController(){}
    public void connect(){
        try{
            connection = DriverManager.getConnection(DBPath);
            statement = connection.createStatement();
            isOpened = true;
            System.out.println("connected to DataBase");
        } catch (SQLException e){
            System.out.println("Can't connect to DataBase");
        }
    }
    public void close(){
        try{
            if(isOpened){
                statement.close();
                connection.close();
                System.out.println("connection closed");
            } else
                System.out.println("DataBase already closed");
        } catch (SQLException e){
            System.out.println("Cant Close connection to DataBase");
        }

    }
    public void ClearDataFromTables(){
        sendToDB("TRUNCATE TABLE header");
        sendToDB("TRUNCATE TABLE files");
        sendToDB("TRUNCATE TABLE content");
        System.out.println("DataBase cleared.");
    }
    public void addToTable(String table, ArrayList<String> columns, ArrayList<String> text){
        StringBuilder execution = new StringBuilder();
        execution.append("INSERT INTO " + table);
        execution.append(getColumns(columns));
        execution.append("VALUES");
        execution.append(getValues(cleanValues(text)));
        sendToDB(execution.toString());
    }
    private ArrayList<String> cleanValues(ArrayList<String> values){
        try{
            ArrayList<String> arrayList = new ArrayList<String>();
            if(values.size() == 0)
                return values;
            for(String value : values){
                if(!value.equals("")){
                    char[] buf = value.toCharArray();
                    String str = "";
                    for(int i = 0; i < buf.length; i++){
                        if(buf[i] != '\'')
                            str += buf[i];
                        else
                            str += "`";
                    }
                    arrayList.add(str);
                } else{
                    arrayList.add(value);
                }

            }
            return arrayList;
        } catch (Exception e){
            return values;
        }
    }
    private String getValues(ArrayList<String> text){
        String key = " (";
        for(String str : text){
            key += "'" + str + "'" + ",";
        }
        String new_key = "";
        for(int i =0; i < key.toCharArray().length-1; i++){
            new_key += key.toCharArray()[i];
        }
        new_key += ") ";
        return new_key;
    }
    private String getColumns(ArrayList<String> columns){
        String key = " (";
        for(String str : columns){
            key += str + ",";
        }
        String new_key = "";
        for(int i =0; i < key.toCharArray().length-1; i++){
            new_key += key.toCharArray()[i];
        }
        new_key += ") ";
        return new_key;
    }
    private void sendToDB(String execution){
        try{
            if(!execution.equals(null))
                statement.execute(execution);
        } catch (SQLException e){}
    }

}
