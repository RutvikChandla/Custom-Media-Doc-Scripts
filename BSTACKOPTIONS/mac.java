import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("bstack:options", new JSONObject()
    .put("os", "OS X")
    .put("osVersion", "Big Sur")
    .put("projectName", "Sample Test")
    .put("buildName", "Sample_test")
    .put("uploadMedia", new JSONArray().put("media://<FILE_HASHED_ID>"))
);
capabilities.setCapability("browserName", "IE");
capabilities.setCapability("browserVersion", "11.0");

WebDriver driver = new InternetExplorerDriver(new URL("https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub"), capabilities);

try {
    driver.get("http://www.fileconvoy.com");
    WebElement uploadElement = driver.findElement(By.id("upfile_0"));
    uploadElement.sendKeys("/Users/test1/Documents/<MEDIA_FOLDER><IMAGE_NAME>.<IMAGE_EXT>");
    ((JavascriptExecutor) driver).executeScript("document.getElementById('readTermsOfUse').click();");
    driver.findElement(By.name("upload_button")).submit();
    WebElement topMessage = driver.findElement(By.id("TopMessage"));
    if (topMessage.getText().contains("successfully uploaded")) {
        ((JavascriptExecutor) driver).executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}');
    } else {
        ((JavascriptExecutor) driver).executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}');
    }
} catch (Exception e) {
    ((JavascriptExecutor) driver).executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}');
} finally {
    driver.quit();
}
