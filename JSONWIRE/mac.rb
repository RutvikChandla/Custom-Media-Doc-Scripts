

require 'rubygems'
require 'selenium-webdriver'

# Input capabilities
caps = Selenium::WebDriver::Remote::Capabilities.new
caps["os"] = "OS X"
caps["os_version"] = "Big Sur"
caps["browser"] = "chrome"
caps["browser_version"] = "latest"
caps["browserstack.local"] = "false"
caps["browserstack.uploadMedia"] = ["media://<FILE_HASHED_ID>"]

begin
  driver = Selenium::WebDriver.for(
    :remote,
    :url => "https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub",
    :capabilities => caps
  )
  driver.navigate.to "http://www.fileconvoy.com"
  driver.find_element(:id, "upfile_0").send_keys("/Users/test1/Documents/<MEDIA_FOLDER><IMAGE_NAME>.<IMAGE_EXT>")  #File path in remote machine
  # MEDIA_FOLDER will be depended on file type of uploadMedia passed, values can be "video", "images", "audio"
  driver.execute_script('document.getElementById("readTermsOfUse").click();')
  driver.find_element(:name, "upload_button").submit
  if driver.find_element(:id, 'TopMessage').text.include? 'successfully uploaded'
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed","reason": "File upload successful"}}')
  else
    driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "File upload failed"}}')
  end
rescue => exception
  driver.execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed","reason": "Something wrong with script"}}')
ensure
  driver.quit
end
