import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import custom.TestDataHandling as handleTestData

// Get data from data test
String sheetName = 'Login'
String locatorExcel = 'Login.xlsx'
List<HashMap> listHashMapAccount = handleTestData.readTestData(locatorExcel, sheetName, true)
int rowCount = listHashMapAccount.size()

// Looping the data test and call all action steps
for (HashMap hashTransfer in listHashMapAccount[0..<rowCount]) {
	String username = hashTransfer.get('Username')
	String password = hashTransfer.get('Password')
	String action = hashTransfer.get('Action')
	
	WebUI.callTestCase(findTestCase("Test Cases/Action/TC_Open Web Browser"), [:])
	WebUI.callTestCase(findTestCase("Test Cases/Action/TC_Verify Login Screen"),[:])
	WebUI.callTestCase(findTestCase("Test Cases/Action/TC_Handle Login Form"), hashTransfer)
	if (action == 'Positive') {
		WebUI.callTestCase(findTestCase("Test Cases/Action/TC_Verify Dashboard Screen"), [:])
	} else if (action == 'Negative'){
		WebUI.callTestCase(findTestCase("Test Cases/Action/TC_Verify Login Error"), hashTransfer)
	}
	WebUI.closeBrowser()
}