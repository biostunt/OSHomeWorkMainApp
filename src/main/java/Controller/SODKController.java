package Controller;

import java.io.*;
import java.util.ArrayList;

public class SODKController<T> {
    private int sleepNotifier = 500;
    public SODKController(){}
    public T getObject(String filepath,int pos){
        T object = null;
        try{
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream in =  new ObjectInputStream(fis);
            ArrayList<T> arrayList = (ArrayList<T>) in.readObject();
            object = arrayList.get(pos);
            Thread.sleep(sleepNotifier);
            fis.close();
            in.close();
        }catch (Exception e){
            System.out.println("Error while getting object. Path:" + filepath);
        }
        return object;
    }
    private ArrayList<T> getAllObject(String filepath){
        ArrayList<T> arrayList = new ArrayList<T>();
        try{
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream in =  new ObjectInputStream(fis);
            arrayList = (ArrayList<T>) in.readObject();
            Thread.sleep(sleepNotifier);
            fis.close();
            in.close();

        } catch (FileNotFoundException e){
            System.out.println("File didnt Found. Path:" + filepath);
        } catch (EOFException e){
            System.out.println("Unexpected EOF. Path:" + filepath);
        } catch (Exception e){
            System.out.println("Error while getting allObjects. Path:" + filepath);
        }
        return arrayList;
    }
    public void writeObject(String filepath,T object){
        ArrayList<T> arrayList = getAllObject(filepath);
        arrayList.add(object);
        writeObjectToFile(filepath,arrayList);
    }
    public void writeObjectToFile(String filepath,ArrayList<T> arrayList){
        try{
            FileOutputStream fout = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(arrayList);
            Thread.sleep(sleepNotifier);
            fout.close();
            oos.close();
        } catch (Exception e){
            System.out.println("Error while writing ArrayList into file");
        }
    }
    public int getArraySize(String filepath){
        return getAllObject(filepath).size();
    }
    public void rewriteFile(String filepath){
        try{
            FileOutputStream fout = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(new ArrayList<T>());
            Thread.sleep(sleepNotifier);
            fout.close();
            out.close();
        } catch (Exception e){
            (new Log()).println("can't rewrite " + filepath);
        }
    }
}
