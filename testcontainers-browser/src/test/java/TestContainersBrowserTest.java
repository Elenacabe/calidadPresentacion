import java.io.File;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainersBrowserTest {

    static {
        new File("target/videos").mkdirs();
    }

    @Container
    public BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(
                BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
                new File("target/videos")
            );

    @Test
    void testHttpCat404Direct() throws Exception {
        // Crear el WebDriver manualmente usando la URL remota
        WebDriver driver = new RemoteWebDriver(
            new URL(container.getSeleniumAddress().toString()),
            new ChromeOptions()
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://http.cat/status/404");
        WebElement img404 = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//img[contains(@src,'404')]")
        ));
        assertTrue(img404.isDisplayed(), "La imagen del gato 404 debe estar visible");
        driver.quit();
    }
}
