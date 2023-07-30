import alerts.WindowAlert;
import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import elements.PaymentForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import page.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static page.OpenPage.closePage;
import static page.OpenPage.loginPage;

@ExtendWith(SoftAssertsExtension.class)
public class MainPageTest {
    private final BasePage basePage = new BasePage();
    private final WindowAlert windowAlert = new WindowAlert();


    @BeforeEach
    void beforeAll() {
        loginPage();
    }


    @DisplayName("Проверка локализации заголовков и информации на странице")
    @Test
    void localizationAssertions() {
        Configuration.assertionMode = AssertionMode.SOFT;
        basePage.checkHeaderEqual("Путешествие дня")
                .checkHeaderCityEqual("Марракеш")
                .checkDescriptionEqual("Сказочный Восток")
                .checkMilesOnCardEqual("33 360 миль на карту")
                .checkCashBackEqual("До 7% на остаток по счёту")
                .checkPriceEqual("Всего 45 000 руб.!");
        int i = 1;
        Configuration.assertionMode = AssertionMode.STRICT;
    }

    @DisplayName("Проверка локализации формы оплаты")
    @Test
    void localizationPaymentForm() {
        PaymentForm paymentForm = basePage.buyButtonClick();
        paymentForm.checkPaymentHeaderVisible()
                .cardNumberField().closest("div").shouldBe(visible, text("Номер карты"));
        paymentForm.expiredMonthField().closest("div").shouldBe(visible, text("Месяц"));
        paymentForm.expiredYearField().closest("div").shouldBe(visible, text("Год"));
        paymentForm.ownerField().closest("div").shouldBe(visible, text("Владелец"));
        paymentForm.cvcField().closest("div").shouldBe(visible, text("CVC/CVV"));
    }

    @DisplayName("Отправка данных, при которых банк одобрит операцию")
    @Test
    void checkSendPaymentFromCardAndAlert() {
        PaymentForm.sendPaymentForm(BasePage::buyButtonClick, "4444444444444441", "08", "23", "SERGEY RUMYANOV", "333");
        windowAlert.isVisibleAlert("Успешно");
    }

    @DisplayName("Отправка данных, при которых банк отклонит операцию")
    @Test
    void checkSendPaymentFromCreditAndAlert() {
        PaymentForm.sendPaymentForm(BasePage::buyingCreditButtonClick, "4444444444444444", "09", "24", "VASILIY OSTROVSKIY", "666");
        windowAlert.isVisibleAlert("Ошибка");
    }

    @DisplayName("Проверка ввода некорректных данных")
    @Test
    void checkSendIncorrectPaymentForm() {
        PaymentForm paymentForm = new PaymentForm("Оплата по карте");
        PaymentForm.sendPaymentForm(BasePage::buyButtonClick, "0", "08", "23", "0", "333");
          paymentForm.cardError(); //ошибка сейчас не находит элемент
    }


    @AfterEach
    void afterEach() {
        closePage();
    }

//CardSupplier.buyCardSupplier(basePage)
}
