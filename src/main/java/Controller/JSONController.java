package Controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

public class JSONController{
    public JSONController(){}
    public String[] getUserSettings(){
        String[] user = new String[2];
        JSONObject jsonObject = getJsonObjectFromFile(
                System.getProperty("user.dir") + "\\bin\\settings\\keys.json");
        user[0] = jsonObject.get("login").toString();
        user[1] = jsonObject.get("password").toString();
        return user;
    }
    public String[] getThreadSettings(){
        JSONArray jsonArray = getJsonArrayFromFile(
                System.getProperty("user.dir") + "\\bin\\settings\\ThreadSettings.json");
        String[] settings = new String[jsonArray.size()];
        for(int i = 0; i < jsonArray.size();i++){
            settings[i] = ((JSONObject) jsonArray.get(i)).get("file").toString();
        }
        return settings;
    }
    public String getJsonStatement(String filepath, String statement){
        return getJsonStatement(getJsonObjectFromFile(filepath),statement);
    }
    public String getJsonStatement(JSONObject jsonObject, String statement){
        for(Object object : jsonObject.entrySet()){
            Map.Entry<String,String> entry = (Map.Entry<String, String>) object;
            if(entry.getKey().equals(statement))
                return entry.getValue();
        }
        System.out.println("JSONObject Has no " + statement + " statement.");
        return null;
    }
    public void setJsonStatement(String filepath,String statement, String value){
        JSONObject newObject = new JSONObject();
        JSONObject jsonObject = getJsonObjectFromFile(filepath);
        for(Object object : jsonObject.entrySet()){
            Map.Entry<String,String> entry = (Map.Entry<String, String>) object;
            if(entry.getKey().equals(statement))
                newObject.put(entry.getKey(),value);
            else
                newObject.put(entry.getKey(),entry.getValue());
        }
        writeJsonObjectToFile(filepath,newObject);
    }
    public void writeJsonObjectToFile(String filepath,JSONObject jsonObject){
        File file = new File(filepath);
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toString());
            fw.close();
        } catch (Exception e){
            System.out.println("Error while writing JSONObject to File. Path: " + filepath);
        }
    }
    public void writeJsonArrayToFile(String filepath,JSONArray jsonArray){
        File file = new File(filepath);
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(jsonArray.toString());
            fw.close();
        } catch (Exception e){
            System.out.println("Error while writing JSONArray to File. Path: " + filepath);
        }
    }
    public JSONObject getJsonObjectFromFile(String filepath){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = (JSONObject) new JSONParser().parse(readFile(filepath));
        } catch (ParseException e){
            System.out.println("Parsing Exception. Path: " + filepath);
        } finally {
            return jsonObject;
        }
    }
    public JSONArray getJsonArrayFromFile(String filepath){
        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray =(JSONArray) new JSONParser().parse(readFile(filepath));
        } catch (ParseException e){
            System.out.println("Parsing Exception. Path: " + filepath);
        } finally {
            return jsonArray;
        }
    }
    private String readFile(String filepath){
        String content = "";
        try{
            FileInputStream inFile = new FileInputStream(filepath);
            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            content = new String(str);
        } catch(FileNotFoundException e){
            System.out.println("File didnt found. Path: " + filepath);
        } catch (IOException e){
            System.out.println("IOException. Path: " + filepath);
        } finally {
            return content;
        }
    }
    public void rewriteStatementsFile(String[] files){
        String filepath = System.getProperty("user.dir") + "\\bin\\statements\\";
        for(int i = 0; i < files.length; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isFinished",0);
            writeJsonObjectToFile(filepath + files[i],jsonObject);
        }
    }
}
