package ProductStoryTest.pages;

import ProductStoryTest.context.TestContext;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class MainPage extends BasePage {


    public MainPage(TestContext context) {
        super(context);
    }

    @FindBy(css = ".list-group-item#cat")
    public WebElement categoriesButton;

    @FindBy(css = "#itemc[onclick=\"byCat('phone')\"]")
    public WebElement sortItemPhone;

    @FindBy(css = "#itemc[onclick=\"byCat('notebook')\"]")
    public WebElement sortItemNotebook;

    @FindBy(css = "#itemc[onclick=\"byCat('monitor')\"]")
    public WebElement sortItemMonitor;

    @FindBy(css = "#nameofuser")
    public WebElement welcomeText;

    @FindBy(css = "#cartur")
    public WebElement goToCart;

    @FindBy(css = ".page-link#next2")
    public WebElement nextPageButton;

    @FindBy(css = "a.hrefch")
    public List<WebElement> articlesCards;

    @Step("Переход в корзину")
    public CartPage getGoToCart() {
        goToCart.click();
        context.wait.until(ExpectedConditions.visibilityOfAllElements(new CartPage(context).rowsTableCartHeadless));
        return new CartPage(context);
    }

    @Step("Получение текста приветствия")
    public String getWelcomeText() {
        return welcomeText.getText();
    }

    @Step("Получение количества товаров")
    public int amountArticle() {
        return articlesCards.size();
    }

    @Step("Фильтрация отображения товаров по группам")
    public MainPage sortingArticles(String nameArticles) {

        switch (nameArticles.toLowerCase()) {
            case "phone" -> sortItemPhone.click();
            case "monitor" -> sortItemMonitor.click();
            case "notebook" -> sortItemNotebook.click();
            default -> throw new IllegalStateException("Unexpected value: " + nameArticles.toLowerCase());
        }
        context.wait.until(ExpectedConditions.stalenessOf(articlesCards.get(1)));
        return new MainPage(context);
    }

    @Step("Подсчет количества товаров на нескольких страницах")
    public int amountArticleAll() {
        int amountAll = amountArticle();
        System.out.println("amountAll = " + amountAll);
        while (nextPageButton.isDisplayed()) {
            getNextPageButton();
            context.wait.until(ExpectedConditions.stalenessOf(articlesCards.getLast()));
            amountAll = amountAll + amountArticle();
        }
        return amountAll;
    }

    @Step("Нажатие кнопки перехода на следующую страницу")
    public void getNextPageButton() {
        nextPageButton.click();
    }

    @Step("Быстрый переход в корзину")
    public CartPage getGoToCartFast() {
        goToCart.click();
        return new CartPage(context);
    }

}
