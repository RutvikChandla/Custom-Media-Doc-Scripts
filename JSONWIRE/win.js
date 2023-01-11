const {Builder, By, Key, until} = require('selenium-webdriver');

async function example() {
  const cap = {
    'browserName': 'chrome',
    'browserstack.local':'false',
    'browserstack.uploadMedia':["media://<FILE_HASHED_ID>"],
    'browser_version': 'latest',
    'os':'Windows',
    'os_version':'10'
  }
  let driver = await new Builder().usingServer('https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub').
    withCapabilities(cap).build();
  try {
    await driver.get('http://www.fileconvoy.com');
    await driver.findElement(By.id('upfile_0')).sendKeys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>");
    await driver.executeScript("document.getElementById('readTermsOfUse').click();");
    await driver.findElement(By.name("upload_button")).submit();
    let ele = await driver.findElement(By.id("TopMessage")).getText();
    if (ele.includes('successfully uploaded')) {
      await driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}');
    } else {
      await driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}');
    }
  } catch (error) {
    await driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}');
  } finally {
    await driver.quit();
  }
}
example();
