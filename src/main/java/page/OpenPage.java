package page;

import Config.BaseConfig;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class OpenPage {
      @Step("Открыть страницу приложения")
    public static void loginPage() {

        open(BaseConfig.getUrl());
        Configuration.timeout = 10000;
    }

    /**
     * Закрывает текущий экземпляр браузера
     */
    public static void closePage() {
        Selenide.closeWebDriver();
    }






}
