from selenium import webdriver

# Input capabilities
caps = {
  "os": "Windows",
  "os_version": "10",
  "browser": "chrome",
  "browser_version": "latest",
  "browserstack.local": "false",
  "browserstack.uploadMedia": ["media://<FILE_HASHED_ID>"],
}

try:
  driver = webdriver.Remote(
    command_executor='https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub',
    desired_capabilities=caps
  )

  driver.get("http://www.fileconvoy.com")
  driver.find_element_by_id("upfile_0").send_keys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>")  #File path in remote machine
  # MEDIA_FOLDER will be depended on file type of uploadMedia passed, values can be "video", "images", "audio"
  driver.execute_script('document.getElementById("readTermsOfUse").click();')
  driver.find_element_by_name("upload_button").submit()
  if "successfully uploaded" in driver.find_element_by_id("TopMessage").text:
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}')
  else:
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}')
except Exception as e:
  driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}')
finally:
  driver.quit()
