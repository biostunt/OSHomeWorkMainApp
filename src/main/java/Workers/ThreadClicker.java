package Workers;

import Controller.VkController;
import org.openqa.selenium.WebDriver;

public class ThreadClicker extends DefaultWorker{

    public ThreadClicker(WebDriver webDriver,int maxCount){
        super(webDriver, maxCount);
    }


    public void run(){
        while (currentCount < maxCount)
            work();
    }

    protected void work(){
        try{
            (new VkController(webDriver)).appendFeedWall();
            Thread.sleep(3000);
        } catch (Exception e){
        } finally {
            currentCount++;
        }
    }
}
