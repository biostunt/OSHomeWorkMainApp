package Controller;

import SampleClasses.Content;
import SampleClasses.Files;
import SampleClasses.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VkController {


    private WebDriver   webDriver;
    public  boolean     isLogged = false;

    public VkController(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public void authentication(){
        webDriver.get("https://vk.com");
        String[] user = (new JSONController()).getUserSettings();
        webDriver.findElement(By.id("index_email")).sendKeys(user[0]);
        webDriver.findElement(By.id("index_pass")).sendKeys(user[1]);
        webDriver.findElement(By.id("index_login_button")).click();
        if(isLoggedOn()){
            (new Log()).println("WebDriver Successful entered to account");
        }
        else
            (new Log()).println("WebDriver Didn't entered to account");
    }
    private boolean isLoggedOn(){
        try{
            Thread.sleep(3000);
            webDriver.findElement(By.id("stories_feed_wrap"));
            isLogged = true;
            return true;
        } catch(Exception e){
            isLogged = false;
            return false;
        }
    }
    public void appendFeedWall(){

        webDriver.findElement(By.id("show_more_link")).click();
    }
    public Header getHeader(int index){
        Header header = new Header();
        try{
            WebElement webElement = webDriver.
                    findElement(By.id("feed_rows")).
                    findElements(By.xpath("div[@class='feed_row ']")).
                    get(index).
                    findElement(By.xpath("div[@class='_post post page_block deep_active']"));
            header.author = getAuthor(webElement);
            header.dataPostId = getDataPostId(webElement);
            header.href = getAdress(webElement);
            header.id = getId(webElement);
        } catch(IndexOutOfBoundsException e){
            Header header1 = new Header();
            header1.id = "-undefined";
            return header1;
        } catch (Exception e){
            return null;
        }
        return header;
    }
    public Content getContent(int index){
        Content content = new Content();
        try{
            WebElement webElement = webDriver.
                    findElement(By.id("feed_rows")).
                    findElements(By.xpath("div[@class='feed_row ']")).
                    get(index).
                    findElement(By.xpath("div[@class='_post post page_block deep_active']"));
            content.id = getId(webElement);
            content.text = getText(webElement);
        } catch(IndexOutOfBoundsException e){
            Content content1 = new Content();
            content1.id = "-undefined";
            return content1;
        } catch (Exception e){
            return null;
        }
        return content;
    }
    public Files getFiles(int index){
        Files files = new Files();
        try{
            WebElement webElement = webDriver.
                    findElement(By.id("feed_rows")).
                    findElements(By.xpath("div[@class='feed_row ']")).
                    get(index).
                    findElement(By.xpath("div[@class='_post post page_block deep_active']"));
            files.id = getId(webElement);
            files.url = getPictures(webElement);
        } catch (IndexOutOfBoundsException e){
            Files files1 = new Files();
            files1.id = "-undefined";
            return files1;
        } catch(Exception e){
            return null;
        }
        return files;
    }

    private String getId(WebElement webElement){

        return  webElement.getAttribute("id");
    }
    private String getDataPostId(WebElement webElement){

        return webElement.getAttribute("data-post-id");
    }
    private String getAuthor(WebElement webElement){

        return webElement.findElement(By.className("author")).getText();
    }
    private String getAdress(WebElement webElement){
        return webElement.findElement(By.className("post_image"))
                .getAttribute("href");
    }
    private String getText(WebElement webElement){
        String text = "";
        try{
            text = webElement.findElement(By.className("wall_post_text")).getText();
        }catch (Exception e){
        }
        return text;
    }
    private String[] getPictures(WebElement webElement){
        WebElement element;
        String[] array = null;
        try{
            element = webElement.findElement(By.className("wall_text")).findElement(By.xpath("div/div[1]"));
            if(element.getAttribute("class").equals("page_post_sized_thumbs  clear_fix")){
                List<WebElement> list = element.findElements(By.xpath("a"));
                array = new String[list.size()];
                for(int i = 0; i < list.size(); i++)
                    array[i] = injectUrl(list.get(i).getAttribute("style"));
            }
        } catch (Exception e){
        }
        return array;
    }
    private String injectUrl(String style){
        StringBuilder str = new StringBuilder("");
        for (int i = style.lastIndexOf("url(") + 5; i < style.lastIndexOf(")")-1; i++)
            str.append(style.toCharArray()[i]);
        return str.toString();
    }

}
