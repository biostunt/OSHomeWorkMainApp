package Workers;

import Controller.JSONController;
import Controller.SocketExtension;
import Controller.Log;
import org.openqa.selenium.WebDriver;

public abstract class DefaultWorker extends SocketExtension implements Runnable {
    protected WebDriver webDriver;
    protected String ResFilePath = System.getProperty("user.dir") + "\\bin\\dat\\";
    protected String filename;
    protected String filepath;
    protected int maxCount;
    private int maxFreeze;
    protected int currentCount = 0;

    public DefaultWorker(WebDriver webDriver, int maxCount){
        super();
        this.maxCount = maxCount;
        this.maxFreeze = this.maxCount;
        this.webDriver = webDriver;
    }
    public DefaultWorker(WebDriver webDriver,String filename, int maxCount){
        super();
        this.webDriver = webDriver;
        this.filename = filename;
        this.filepath = ResFilePath + filename;
        this.maxCount = maxCount;
        this.maxFreeze = this.maxCount;
    }
    public DefaultWorker(int maxCount){
        super();
        this.maxCount = maxCount;
        this.maxFreeze = this.maxCount;
    }


    public void run(){
        while(currentCount < maxCount){
            take();
            work();
            release();
            System.out.println(filepath + " - " + (currentCount - (maxCount - maxFreeze)));
        }
        (new Log()).println(this.getClass().getName() + " ended!");
        if(filename != null){
            take();
            String file = System.getProperty("user.dir") + "\\bin\\statements\\" + getFileName(filename) + ".json";
            (new JSONController()).setJsonStatement(file,"isFinished",1 + "");
            release();
        }
    }

    protected void work(){
        currentCount++;
    }
    protected boolean isUndefined(String id){
        return "-undefined".equals(id);
    }
    private String getFileName(String filename){
        String key = "";
        for(int i = 0; i < filename.lastIndexOf(".dat");i++){
            key += filename.toCharArray()[i];
        }
        return key;
    }
}
