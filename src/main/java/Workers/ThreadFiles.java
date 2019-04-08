package Workers;


import Controller.SODKController;
import Controller.VkController;
import SampleClasses.Files;
import org.openqa.selenium.WebDriver;

public class ThreadFiles extends DefaultWorker {

    public ThreadFiles(String filename, WebDriver webDriver,int maxCount){
        super(webDriver,filename,maxCount);
    }

    protected void work(){
        Files object = (new VkController(webDriver)).getFiles(currentCount);
        if (object != null) {
            if (!isUndefined(object.id)) {
                (new SODKController<Files>()).writeObject(filepath, object);
                currentCount++;
            }
        } else {
            currentCount++;
            maxCount++;
        }
    }

}
