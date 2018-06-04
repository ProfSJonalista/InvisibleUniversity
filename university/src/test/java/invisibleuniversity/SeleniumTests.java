package invisibleuniversity;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.openqa.selenium.support.ui.Select;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
@Ignore
public class SeleniumTests {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAllert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Blargh\\School\\TAU\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://automationpractice.com/index.php/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void invalidRegisterTest() throws Exception {
        driver.get(baseUrl);
        signIn();
        createAccount("Shoot@MyFace.com");
        if (checkIfErrorExist("#create_account_error")) {
            createAccount("Shoot@Me");

            if (checkIfErrorExist("#create_account_error")) {
                create();
            }
        } else {
            create();
        }
        register();

        if(checkIfErrorExist("#center_column > div")){
            String alertMessage = driver.findElement(By.cssSelector("#center_column > div")).getText();
            if(alertMessage.contains("firstname") || alertMessage.contains("lastname") || alertMessage.contains("passwd")){
                fillPersonalInformation("Facy", "McShootFace", "Admin 1", "3 January 1984");
            }

            if(alertMessage.contains("addres1") || alertMessage.contains("city")){
                fillAddress("Bakery Street 5", "Iowa City", "Iowa", "00000", "000000000", "address");
            }
        }

        register();

        assertEquals(true, driver.findElement(By.linkText("Sign out")).isDisplayed());

        Thread.sleep(3000);
        signOut();
    }

    private void create() {
        createAccount("Shoot@MeRightNow.com");
        fillForm("Mr", "", "McShootFace", "Admin 1", "3 January 1984",
                "Bakery Street 5", "", "Iowa", "00000", "", "address");
    }

    private boolean checkIfErrorExist(String cssSelector) throws Exception {
        Thread.sleep(3000);
        return driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
    }
    private void signIn() {
        driver.findElement(By.linkText("Sign in")).click();
    }

    private void createAccount(String email) {
        driver.findElement(By.id("email_create")).clear();
        driver.findElement(By.id("email_create")).sendKeys(email);
        driver.findElement(By.id("SubmitCreate")).click();
    }

    private void fillForm(String title, String firstName, String lastName, String password, String dateOfBirth,
                          String address, String city, String state, String postalCode, String mobilePhone, String addressAlias) {
        fillGender(title);
        fillPersonalInformation(firstName, lastName, password, dateOfBirth);
        fillAddress(address, city, state, postalCode, mobilePhone, addressAlias);
    }

    private void fillGender(String title) {
        switch (title) {
            case "Mr":
                driver.findElement(By.id("id_gender1")).click();
                break;
            case "Mrs":
                driver.findElement(By.id("id_gender2")).click();
                break;
            default:
                break;
        }
    }

    private void fillPersonalInformation(String firstName, String lastName, String password, String dateOfBirth) {
        driver.findElement(By.id("customer_firstname")).clear();
        driver.findElement(By.id("customer_firstname")).sendKeys(firstName);

        driver.findElement(By.id("customer_lastname")).clear();
        driver.findElement(By.id("customer_lastname")).sendKeys(lastName);

        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys(password);

        String[] splited = dateOfBirth.split("\\s+");

        new Select(driver.findElement(By.id("days"))).selectByValue(splited[0]);
        new Select(driver.findElement(By.id("months"))).selectByVisibleText(splited[1] + " ");
        new Select(driver.findElement(By.id("years"))).selectByValue(splited[2]);
    }

    private void fillAddress(String address, String city, String state, String postalCode, String mobilePhone, String addressAlias) {
        driver.findElement(By.id("address1")).clear();
        driver.findElement(By.id("address1")).sendKeys(address);

        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys(city);

        new Select(driver.findElement(By.id("id_state"))).selectByVisibleText(state);

        driver.findElement(By.id("postcode")).clear();
        driver.findElement(By.id("postcode")).sendKeys(postalCode);

        driver.findElement(By.id("phone_mobile")).clear();
        driver.findElement(By.id("phone_mobile")).sendKeys(mobilePhone);

        driver.findElement(By.id("alias")).clear();
        driver.findElement(By.id("alias")).sendKeys(addressAlias);
    }

    private void register() {
        driver.findElement(By.id("submitAccount")).click();
    }

    private void signOut(){
        driver.findElement(By.linkText("Sign out")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
