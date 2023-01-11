const {Builder, By, Key, until} = require('selenium-webdriver');

let capabilities = {
    'bstack:options': {
        'os': 'OS X',
        'osVersion': 'Big Sur',
        'projectName': 'Sample Test',
        'buildName': 'Sample_test',
        'uploadMedia': ['media://<FILE_HASHED_ID>']
    },
    'browserName': 'IE',
    'browserVersion': '11.0',
};

let driver = new Builder().usingServer('https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub').withCapabilities(capabilities).build();

try {
    driver.get('http://www.fileconvoy.com');
    driver.findElement(By.id('upfile_0')).sendKeys("/Users/test1/Documents/<MEDIA_FOLDER><IMAGE_NAME>.<IMAGE_EXT>");
    driver.executeScript('document.getElementById("readTermsOfUse").click();');
    driver.findElement(By.name('upload_button')).submit();
    if (driver.findElement(By.id('TopMessage')).getText().includes('successfully uploaded')) {
        driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}');
    } else {
        driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}');
    }
} catch (exception) {
    driver.executeScript('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}');
} finally {
    driver.quit();
}
