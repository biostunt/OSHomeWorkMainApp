package Controller;

import java.io.*;
import java.util.ArrayList;

public class SODKController<T> {
    public SODKController(){}
    public T getObject(String filepath,int pos){
        T object = null;
        try{
            ObjectInputStream in =  new ObjectInputStream(
                    new FileInputStream(filepath));
            ArrayList<T> arrayList = (ArrayList<T>) in.readObject();
            object = arrayList.get(pos);
            in.close();
        }catch (Exception e){
            System.out.println("Error while getting object. Path:" + filepath);
        }
        return object;
    }
    private ArrayList<T> getAllObject(String filepath){
        ArrayList<T> arrayList = new ArrayList<T>();
        try{
            ObjectInputStream in =  new ObjectInputStream(new FileInputStream(filepath));
            arrayList = (ArrayList<T>) in.readObject();
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

    }
    public void writeObjectToFile(String filepath,ArrayList<T> arrayList){
        try{
            FileOutputStream fout = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(arrayList);
        } catch (Exception e){
            System.out.println("Error while writing ArrayList into file");
        }
    }
    public int getArraySize(String filepath){
        return getAllObject(filepath).size();
    }
    public void rewriteFile(String filepath){
        try{
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(filepath));
            out.writeObject(new ArrayList<T>());
            out.close();
        } catch (Exception e){
            (new Log()).println("can't rewrite " + filepath);
        }
    }
}
