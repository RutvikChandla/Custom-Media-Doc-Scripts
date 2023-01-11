from selenium import webdriver

capabilities = {
    'bstack:options': {
        'os': 'Windows',
        'osVersion': '10',
        'projectName': 'Sample Test',
        'buildName': 'Sample_test',
        'uploadMedia': ['media://<FILE_HASHED_ID>']
    },
    'browserName': 'IE',
    'browserVersion': '11.0',
}

driver = webdriver.Remote(
    command_executor='https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub',
    desired_capabilities=capabilities
)

try:
    driver.get("http://www.fileconvoy.com")
    driver.find_element_by_id("upfile_0").send_keys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>")
    driver.execute_script("document.getElementById('readTermsOfUse').click();")
    driver.find_element_by_name("upload_button").submit()
    if "successfully uploaded" in driver.find_element_by_id("TopMessage").text:
        driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}')
    else:
        driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}')
except:
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}')
finally:
    driver.quit()
