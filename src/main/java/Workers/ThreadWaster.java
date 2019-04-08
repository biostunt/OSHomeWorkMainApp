package Workers;

import Controller.Log;

import java.io.FileReader;
import java.util.Random;

public class ThreadWaster extends DefaultWorker {
    private String[] files;

    public ThreadWaster(String[] files,int maxCount){
        super(maxCount);
        this.files = files;
    }
    protected void work(){
        String path = System.getProperty("user.dir") + "\\bin\\dat\\";
    for(String file : files){
        try{
            FileReader fr = new FileReader(path + file);
            fr.close();
        } catch (Exception e){
            (new Log()).println("Can't work with file + " + file + " at ThreadWaster");
        }
    }
    currentCount++;
    try{
        Thread.sleep((new Random()).nextInt(1500));
    } catch (Exception e){
        (new Log()).println("ThreadWaster - sleep error");
    }
    }

}
