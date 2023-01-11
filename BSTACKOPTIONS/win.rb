require 'rubygems'
require 'selenium-webdriver'

# Input capabilities
capabilities = {
	'bstack:options' => {
		"os" => "Windows",
		"osVersion" => "10",
		"projectName" => "Sample Test",
		"buildName" => "Sample_test",
		"uploadMedia" => ["media://<FILE_HASHED_ID>"]
	},
	"browserName" => "IE",
	"browserVersion" => "11.0",
}

begin
  driver = Selenium::WebDriver.for(
    :remote,
    :url => "https://YOUR_USERNAME:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub",
    :capabilities => capabilities
  )
  driver.file_detector = lambda do |args|
    str = args.first.to_s
    str if File.exist?(str)
  end
  driver.navigate.to "http://www.fileconvoy.com"
  driver.find_element(:id, "upfile_0").send_keys("C:\\Users\\hello\\Documents\\<MEDIA_FOLDER>\\<IMAGE_NAME>.<IMAGE_EXT>")  #File path in remote machine
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
