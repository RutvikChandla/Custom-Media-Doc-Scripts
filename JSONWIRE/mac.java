

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class FileUpload {
    public static void main(String[] args) throws Exception {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("os", "OS X");
        caps.setCapability("os_version", "Big Sur");
        caps.setCapability("browser", "chrome");
        caps.setCapability("browser_version", "latest");
        caps.setCapability("browserstack.local", "false");
        caps.setCapability("browserstack.uploadMedia", "media://<FILE_HASHED_ID>");

        WebDriver driver = new RemoteWebDriver(new URL("https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub"), caps);
        try {
            driver.get("http://www.fileconvoy.com");
            driver.findElement(By.id("upfile_0")).sendKeys("/Users/test1/Documents/<MEDIA_FOLDER><IMAGE_NAME>.<IMAGE_EXT>");  //File path in remote machine
            // MEDIA_FOLDER will be depended on file type of uploadMedia passed, values can be "video", "images", "audio"
            driver.executeScript("document.getElementById('readTermsOfUse').click();");
            driver.findElement(By.name("upload_button")).submit();
            String ele = driver.findElement(By.id("TopMessage")).getText();
            if (ele.contains("successfully uploaded")) {
                driver.executeScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'passed','reason': 'File upload successful'}}");
            } else {
                driver.executeScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'failed','reason': 'File upload failed'}}");
            }
        } catch (Exception e) {
            driver.executeScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'failed','reason': 'Something wrong with script'}}");
        } finally {
            driver.quit();
        }
    }
}
