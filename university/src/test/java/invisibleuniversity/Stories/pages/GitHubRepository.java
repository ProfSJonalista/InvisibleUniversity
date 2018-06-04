package invisibleuniversity.Stories.pages;

import invisibleuniversity.Stories.Helper;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GitHubRepository extends WebDriverPage {
    public GitHubRepository(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        get("https://github.com/ProfSJonalista/InvisibleUniversity");
        manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
    }

    public void click(String linkText) throws InterruptedException {
//        Thread.sleep(3000);
//        WebElement e;
//        if (linkText.equals("invisibleUniversity")) {
//            e = findElement(By.id(Helper.checkIfFolderIsNotEmpty("invisibleUniversity")));
//        } else {
//            e = findElement(By.partialLinkText(linkText));
//        }
//        e.click();

        Thread.sleep(3000);
        WebElement e = findElement(By.partialLinkText(linkText));
        e.click();
    }

    public boolean isClassNotEmpty(String className) {
        try {
            List<WebElement> elements = findElements(By.cssSelector("#LC11 > span.pl-en"));
            for (WebElement e : elements) {
                if (e.getText().toLowerCase().contains(className.toLowerCase())) return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFolderNotEmpty(String id) throws InterruptedException {
        Thread.sleep(3000);
        WebElement e = findElement(By.id(id));
        return e.isDisplayed();
    }
}
