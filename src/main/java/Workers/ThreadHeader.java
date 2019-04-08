package Workers;

import Controller.SODKController;
import Controller.VkController;
import SampleClasses.Header;
import org.openqa.selenium.WebDriver;

public class ThreadHeader extends DefaultWorker {

    public ThreadHeader(String filename, WebDriver webDriver,int maxCount){
        super(webDriver,filename,maxCount);
    }

    protected void work() {
        Header object = (new VkController(webDriver)).getHeader(currentCount);
        if (object != null) {
            if (!isUndefined(object.id)) {
                (new SODKController<Header>()).writeObject(filepath, object);
                currentCount++;
            }
        } else {
            currentCount++;
            maxCount++;
        }
    }
}
