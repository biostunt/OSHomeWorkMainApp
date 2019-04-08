package Workers;

import Controller.SODKController;
import Controller.VkController;
import SampleClasses.Content;
import org.openqa.selenium.WebDriver;

public class ThreadContent extends DefaultWorker {

    public ThreadContent(String filename, WebDriver webDriver,int maxCount){
        super(webDriver,filename,maxCount);
    }

    protected void work(){
        Content object = (new VkController(webDriver)).getContent(currentCount);
        if (object != null) {
            if (!isUndefined(object.id)) {
                (new SODKController<Content>()).writeObject(filepath, object);
                currentCount++;
            }
        } else {
            currentCount++;
            maxCount++;
        }
    }

}
