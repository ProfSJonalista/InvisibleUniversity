package invisibleuniversity.Stories;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SiteSteps {
    private final Pages pages;

    public SiteSteps(Pages pages) {
        this.pages = pages;
    }

    @Given("user is in repository")
    public void userIsOnRepositoryPage(){
        pages.gitHubRepository().open();
    }

    @Given("user is in university folder and src folder is available")
    public void checkIfUserIsInUniversityFolder() throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty("university");
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @Given("user is in src folder and main folder is available")
    public void checkIfUserIsInSrcFolder() throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty("src");
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @Given("user is in main folder and java/invisibleUniversity folder is available")
    public void checkIfUserIsInMainFolder() throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty("main");
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @Given("user is in java/invisibleUniversity folder and domain folder is available")
    public void checkIfUserIsInJavInvisibleUniversityFolder() throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty("invisibleUniversity");
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @Given("user is in domain folder and Creator class is available")
    public void checkIfUserIsInDomainFolder() throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty("domain");
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @When("user clicks the $folderName folder")
    public void whenUserClicksFolder(String folderName) throws InterruptedException {
        pages.gitHubRepository().click(folderName);
    }

    @When("user clicks the $className class")
    public void openClass(String className) throws InterruptedException {
        pages.gitHubRepository().click(className);
    }

    @Then("the $folderName folder is opened and it's not empty")
    public void checkIfFolderIsNotEmpty(String folderName) throws InterruptedException {
        String id = Helper.checkIfFolderIsNotEmpty(folderName);
        assertTrue(pages.gitHubRepository().isFolderNotEmpty(id));
    }

    @Then("$className class should open and should not be empty")
    public void checkIfClassIsNotEmpty(String className){
        assertTrue(pages.gitHubRepository().isClassNotEmpty(className));
    }
}
