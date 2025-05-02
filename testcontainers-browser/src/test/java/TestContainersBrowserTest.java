import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
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
    void testHttpCat404Direct() {
        WebDriver driver = container.getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Navega directamente a la página del gato 404
        driver.get("https://http.cat/status/404");
        
        // Espera a que la imagen esté visible
        WebElement img404 = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//img[contains(@src,'404')]")
        ));
        
        // Verifica que la imagen está visible
        assertTrue(img404.isDisplayed(), "La imagen del gato 404 debe estar visible");
        
        // Cierra el navegador
        driver.quit();
    }
}
