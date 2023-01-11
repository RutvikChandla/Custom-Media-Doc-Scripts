using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

class FileUpload
{
    static void Main(string[] args)
    {
        var caps = new DesiredCapabilities();
        caps.SetCapability("os", "Windows");
        caps.SetCapability("os_version", "10");
        caps.SetCapability("browser", "chrome");
        caps.SetCapability("browser_version", "latest");
        caps.SetCapability("browserstack.local", "false");
        caps.SetCapability("browserstack.uploadMedia", "media://<FILE_HASHED_ID>");
        IWebDriver driver = new RemoteWebDriver(new Uri("https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub"), caps);
        try
        {
            driver.Navigate().GoToUrl("http://www.fileconvoy.com");
            driver.FindElement(By.Id("upfile_0")).SendKeys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>");
            driver.ExecuteScript("document.getElementById('readTermsOfUse').click();");
            driver.FindElement(By.Name("upload_button")).Submit();
            string ele = driver.FindElement(By.Id("TopMessage")).Text;
            if (ele.Contains("successfully uploaded"))
            {
                driver.ExecuteScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'passed','reason': 'File upload successful'}}");
            }
            else
            {
                driver.ExecuteScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'failed','reason': 'File upload failed'}}");
            }
        }
        catch (Exception e)
        {
            driver.ExecuteScript("browserstack_executor: {'action': 'setSessionStatus', 'arguments': {'status':'failed','reason': 'Something wrong with script'}}");
        }
        finally
        {
            driver.Quit();
        }
    }
}
