using OpenQA.Selenium;
using OpenQA.Selenium.IE;
using Newtonsoft.Json;
using System;

var capabilities = new DesiredCapabilities();
capabilities.SetCapability("bstack:options", JsonConvert.SerializeObject(new
    {
        os = "Windows",
        osVersion = "10",
        projectName = "Sample Test",
        buildName = "Sample_test",
        uploadMedia = new[] { "media://<FILE_HASHED_ID>" }
    }));
capabilities.SetCapability("browserName", "IE");
capabilities.SetCapability("browserVersion", "11.0");

IWebDriver driver = new RemoteWebDriver(new Uri("https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub"), capabilities);

try
{
    driver.Navigate().GoToUrl("http://www.fileconvoy.com");
    IWebElement uploadElement = driver.FindElement(By.Id("upfile_0"));
    uploadElement.SendKeys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>");
    IJavaScriptExecutor jse = (IJavaScriptExecutor)driver;
    jse.ExecuteScript("document.getElementById('readTermsOfUse').click();");
    driver.FindElement(By.Name("upload_button")).Submit();
    IWebElement topMessage = driver.FindElement(By.Id("TopMessage"));
    if (topMessage.Text.Contains("successfully uploaded"))
    {
        jse.ExecuteScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}');
    }
    else
    {
        jse.ExecuteScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}');
    }
}
catch (Exception e)
{
    IJavaScriptExecutor jse = (IJavaScriptExecutor)driver;
    jse.ExecuteScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}');
}
finally
{
    driver.Quit();
}
