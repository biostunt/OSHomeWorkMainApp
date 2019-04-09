import Controller.JSONController;
import Controller.Log;
import Controller.SODKController;
import Controller.VkController;
import Workers.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AppWorker {
    public AppWorker(){}

    private String      ResFilePath = System.getProperty("user.dir") + "\\bin";
    private String      dataPath = ResFilePath + "\\dat\\";
    private WebDriver   webDriver;

    public void start(){
        rewriteFiles();
        launchExtFiles();
        vkAuthentication();
        runThreads();
    }
    private void rewriteFiles(){
        String[] files = {"header.dat","content.dat","files.dat"};
        String[] statFiles = {"header.json","content.json","files.json"};
        (new SODKController<String>()).rewriteFile(dataPath+files[0]);
        (new SODKController<String>()).rewriteFile(dataPath+files[1]);
        (new SODKController<String>()).rewriteFile(dataPath+files[2]);
        (new JSONController()).rewriteStatementsFile(statFiles);
    }
    private void runThreads(){
        String[] settings = (new JSONController()).getThreadSettings();
        int count = 150;
        new Thread(new ThreadHeader(settings[0],webDriver,count)).start();
        new Thread(new ThreadContent(settings[1],webDriver,count)).start();
        new Thread(new ThreadFiles(settings[2],webDriver,count)).start();
        new Thread(new ThreadClicker(webDriver,count)).start();
        new Thread(new ThreadWaster(settings,count)).start();
    }
    private void vkAuthentication(){
        (new VkController(webDriver)).authentication();
    }
    private void launchExtFiles(){
        StartChromeDriver();
        StartWebDriver();
        StartSemaphoreServer();
        //StartService();
    }
    private void StartService(){
        (new Log()).println("Launching Windows Service");
        openProgram(new File("C:\\wrapper-windows-x86-64-3.5.37-st\\bin\\Run.bat"));
    }
    public void StartSemaphoreServer(){
        (new Log()).println("Lunching SemaphoreServer..");
        openProgram(new File(ResFilePath + "\\res\\SemaphoreServer.exe"));
    }
    private void StartWebDriver(){
        try {
            (new Log()).println("Creating WebDriver..");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            webDriver = new RemoteWebDriver(new URL("http://localhost:9515"), options);
            (new Log()).println("WebDriver created");
        } catch(Exception e){
            (new Log()).println("Can't create WebDriver");
        }
    }
    private void StartChromeDriver() {
        (new Log()).println("Launching ChromeDriver..");
        openProgram(new File(ResFilePath + "\\res\\chromedriver.exe"));
    }
    private void openProgram(File file){
        try {
            Desktop.getDesktop().open(file);
            (new Log()).println(file.getName() + " launched");
        } catch (IOException e) {
            (new Log()).println("can't launch " + file.getName());
        }
    }
}
