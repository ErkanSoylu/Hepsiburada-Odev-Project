package com.gauge.webproject.step;

import com.gauge.webproject.base.BaseTest;
import com.gauge.webproject.helper.ElementHelper;
import com.gauge.webproject.helper.StoreHelper;
import com.gauge.webproject.model.ElementInfo;
import com.thoughtworks.gauge.Step;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Dimension;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class StepImplementation extends BaseTest {


  public static int DEFAULT_MAX_ITERATION_COUNT = 150;
  public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

  private static String SAVED_ATTRIBUTE;

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
  public List<WebElement> findElementsByKey(String key) {
    return driver.findElements(ElementHelper.getElementInfoToBy(StoreHelper.INSTANCE.findElementInfoByKey(key)));
  }

  public static WebElement findElement(By by) {
    return driver.findElement(by);
  }
  public static void ClickElement(By by) {
    findElement(by).click();
  }
  public static void sendKeys(By by , String text) {
    findElement(by).sendKeys(text);
  }
  private WebElement findElement(String key) {
    ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
    By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
    WebDriverWait webDriverWait = new WebDriverWait(driver, 0);
    WebElement webElement = webDriverWait
            .until(ExpectedConditions.presenceOfElementLocated(infoParam));
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
            webElement);
    return webElement;
  }

  private List<WebElement> findElements(String key) {
    ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
    By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
    return driver.findElements(infoParam);
  }

  private void clickElement(WebElement element) {
    element.click();
  }

  private void clickElementBy(String key) {
    findElement(key).click();
  }

  private void hoverElement(WebElement element) {
    Actions actions = new Actions(driver);
    actions.moveToElement(element).build().perform();
  }
  @Step("<key> listelenen sayfadan rastgele ürün seç")
  public void selectRandomProduct1(String key){
    List<WebElement> allProducts = findElements(key);
    Random rand = new Random();
    int randomProduct = rand.nextInt(allProducts.size());
    allProducts.get(randomProduct).click();
  }
  private void hoverElementBy(String key) {
    WebElement webElement = findElement(key);
    Actions actions = new Actions(driver);
    actions.moveToElement(webElement).build().perform();
  }

  private void sendKeyESC(String key) {
    findElement(key).sendKeys(Keys.ESCAPE);

  }

  private boolean isDisplayed(WebElement element) {
    return element.isDisplayed();
  }

  private boolean isDisplayedBy(By by) {
    return driver.findElement(by).isDisplayed();
  }

  private String getPageSource() {
    return driver.switchTo().alert().getText();
  }


  public static String getSavedAttribute() {
    return SAVED_ATTRIBUTE;
  }

  public WebElement findElementWithKey(String key) {
    return findElement(key);
  }

  public String getElementText(String key) {
    return findElement(key).getText();
  }

  public String getElementAttributeValue(String key, String attribute) {
    return findElement(key).getAttribute(attribute);
  }

  public void javaScriptClicker(WebDriver driver, WebElement element) {

    JavascriptExecutor jse = ((JavascriptExecutor) driver);
    jse.executeScript("var evt = document.createEvent('MouseEvents');"
            + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
            + "arguments[0].dispatchEvent(evt);", element);
  }

  @Step({"Wait <value> seconds",
          "<int> saniye bekle"})
  public void waitBySeconds(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Step({"Wait <value> milliseconds",
          "<long> milisaniye bekle"})
  public void waitByMilliSeconds(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Step("Tarayiciyi kapat") public void closeTheBrowser() {
    driver.close();
  }

  @Step({"<key> li elementi bul ve değerini <saveKey> saklanan değer ile karşılaştır ve değişiklik olmadığını dogrula",
          "Find element by <key> and compare saved key <saveKey>"})
  public void equalsSaveTextByKeyNotequal(String key, String saveKey) throws InterruptedException {
    Thread.sleep(10000);
    Assert.assertEquals(StoreHelper.INSTANCE.getValue(saveKey), findElement(key).getText());
  }
  @Step({"<key> li elementi bul ve değerini <saveKey> olarak sakla",
          "Find element by <key> and save text <saveKey>"})
  public void saveTextByKey(String key, String saveKey) throws InterruptedException {
    Thread.sleep(1000);
    StoreHelper.INSTANCE.saveValue(saveKey, findElement(key).getText());
    Thread.sleep(2000);
  }

  @Step({"Click to element <key>",
          "Elementine tıkla <key>"})
  public void clickElement(String key) {
    if (!key.equals("")) {
      WebElement element = findElement(key);
      hoverElement(element);
      waitByMilliSeconds(500);
      clickElement(element);
    }
  }

  @Step({"Write value <text> to element <key>",
          "<text> textini elemente yaz <key>"})
  public void sendKeys(String text, String key) {
    if (!key.equals("")) {
      findElement(key).sendKeys(text);
    }
  }
  @Step({"Element <key> var mı kontrol et varsa texti yaz <text>"})
  public void getElementWithKeyIfExistsWithMessage(String key, String message) {
    ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
    By by = ElementHelper.getElementInfoToBy(elementInfo);

    int loopCount = 0;
    while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
      if (driver.findElements(by).size() > 0) {
        sendKeys(message,key);
      }
      loopCount++;
      waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
    }
  }
  private int getRandomNumber(int upperBound) {
    Random rand = new Random();
    return rand.nextInt(upperBound);
  }
  @Step("Menüye götür ve bekle")
  public void HooverMouseAndClick()
  {
    Actions action = new Actions(driver);
    WebElement mainMenu = findElement("menu");
    action.moveToElement(mainMenu).moveToElement(findElement("menu")).click().build().perform();
  }
  @Step("Sepetteki ürünü kontrol et")
  public void controlTheBasketCount() throws InterruptedException {
    Thread.sleep(5000);
   boolean test = isElementPresent(By.xpath("//*[@id=\'short-summary\']/div[1]/div[2]/button"));
    if (test == true) {
      System.out.println("Sepette ürünler mevcut , ürün adedine göre sırasıyla silme işlemi gerçekleştirilecek.");
      List<WebElement> allProducts = findElements("sepetListeleme");
      for(int i=allProducts.size(); i>0; i--){
        Thread.sleep(5000);
        ClickElement(By.linkText("Sil"));
        System.out.println("Sepetten ürün silindi ...");
      }
      Thread.sleep(5000);
      System.out.println("Sepette mevcut ürün kalmadığı için anasayfaya dönülüyor...");
      Thread.sleep(5000);
     clickElement("altLogo");
    }
    else {
      System.out.println("Sepette mevcut ürün olmadığı için anasayfaya dönülüyor...");
      Thread.sleep(5000);
      clickElement("altLogo");
    }
  }
  @Step("<key> alanından rastgele seç")
  public void randomMarkaSelect(String key) throws InterruptedException {
    List<WebElement> allProducts = findElements(key);
    Random rand = new Random();
    int randomProduct = rand.nextInt(allProducts.size());
    allProducts.get(randomProduct).click();
  }
  @Step("<key> alanindan Rastgele bir nesne seç")
  public void randomListSecimi(String key) throws InterruptedException{
    List<WebElement> allProducts = findElements(key);
    Random rand = new Random();
    int randomProduct = rand.nextInt(allProducts.size());
    allProducts.get(randomProduct).click();
  }
  public static int randomNumber(int start, int end) {
    Random rn = new Random();
    int randomNumber = rn.nextInt(end - 1) + start;
    return randomNumber;
  }
  @Step("Kategorinin üzerine götür ve  <category> alt kategoriye tıkla <subcategory>")
  public void randomCategoryClick(String category, String subCategory) throws InterruptedException {
    List<WebElement> categoryList = findElementsByKey(category);
    int randNum = randomNumber(0, categoryList.size());
    WebElement getCategory = categoryList.get(randNum);
    Actions builder = new Actions(driver);
    Actions hoverOverRegistrar = builder.moveToElement(getCategory);
    hoverOverRegistrar.perform();
    Thread.sleep(2000);
    List<WebElement> subCategoryList = findElementsByKey(subCategory);
    int randomSubCategory = randomNumber(0, subCategoryList.size());
    WebElement getSubCategory = subCategoryList.get(randomSubCategory);
    getSubCategory.click();
  }
  @Step("<adini> ve <fiyatini> <csv> dosyasına kaydet")
  public void urunAdiVsFiyati(String urunadi, String urunFiyati, String csv) throws IOException {
    WebElement urunElementi = findElementWithKey(urunadi);
    WebElement fiyatElement = findElementWithKey(urunFiyati);
    csvYazdir(fiyatElement.getText(), urunElementi.getText(), csv);
  }

  public void csvYazdir(String urunFiyati, String urunAdi,String dosyaAdı) throws IOException {
    FileWriter csvWriter = new FileWriter(dosyaAdı);
    csvWriter.append("\"" + urunAdi + "\"");
    csvWriter.append("\n");
    csvWriter.append("\"" + urunFiyati + "\"");
    csvWriter.flush();
    csvWriter.close();
  }
  @Step({"Check if element <key> contains text <expectedText>",
          "<key> elementi <text> değerini içeriyor mu kontrol et"})
  public void checkElementContainsText(String key, String expectedText) {
    Boolean containsText = findElement(key).getText().contains(expectedText);
    Assert.assertTrue("Expected text is not contained", containsText);
  }
  @Step("<tane> Adet seç ve sepete ekle / ardından sepete git ve adeti karşılaştır.")
  public void itemCountAndAddtoBasket(int tane) throws InterruptedException{
    WebElement changeCount = driver.findElement(By.xpath("//*[@id=\"quantity\"]"));
    String str1 = Integer.toString(tane);
    changeCount.clear();
    changeCount.sendKeys(str1);
    clickElement("sepeteEkle");
    Thread.sleep(5000);
    clickElement("alisverisSepetim");
    Thread.sleep(3000);
    String getPNRtext =  findElement(By.xpath("//*[@id=\'short-summary\']/div[1]/p/span")).getText();
    String pnrText = getPNRtext.substring(0, 1);
    Thread.sleep(3000);
    if ( pnrText.equals(str1) ) {
      System.out.println("Ürünün liste adedi ile sepet adedi aynıdır. ");
    }
    else {
      System.out.println("Ürünün liste adedi ile sepet adedi farklıdır. ");
      driver.close();
    }
  }
  @Step("Adedi 2 arttir")
  public void adetArttir() throws InterruptedException {
    Thread.sleep(3000);
    clickElement("adetArttir");
    Thread.sleep(5000);
    clickElement("adetArttir");
    Thread.sleep(10000);
  }
  @Step("ilce yi sec")
  public void bekle() throws InterruptedException {
    WebElement test = findElement("ilceAlani");
    test.click();
    WebElement ilce1 = findElement("adalar");
    ilce1.click();
  }
  @Step("mahalle yi sec")
  public void mahalle() throws InterruptedException {
    WebElement mahalle1 = findElement("mahalleAlani");
    mahalle1.click();
    WebElement mahallesec = findElement("ataturkMahallesi");
    mahallesec.click();
  }
}



