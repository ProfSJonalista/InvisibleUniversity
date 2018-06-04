package invisibleuniversity.Stories;

import invisibleuniversity.Stories.pages.GitHubRepository;
import org.jbehave.web.selenium.WebDriverProvider;

public class Pages {

    private WebDriverProvider driverProvider;
    private GitHubRepository gitHubRepository;

    public Pages(WebDriverProvider driverProvider) {
        super();
        this.driverProvider = driverProvider;
    }

    public GitHubRepository gitHubRepository() {
        if (gitHubRepository == null) {
            gitHubRepository = new GitHubRepository(driverProvider);
        }
        return gitHubRepository;
    }
}