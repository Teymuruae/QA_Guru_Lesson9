package parameterezed;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class ParamTest {

    @BeforeEach
    void setUp() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://magizoo.ru/";
    }

    @CsvSource(value = {
            "О компании, О компании | Интернет-зоомагазин MAGIZOO.RU",
            "Доставка, Условия доставки",
            "Оплата, Условия оплаты"
    })
    @ParameterizedTest(name = "тестирование раздела {0}")
    void magiZooHeadButtonsTest(String razdel, String result) {

        Selenide.open("");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//ul//li//a").findBy(Condition.text(razdel)).click();
        $("h1").shouldHave(Condition.text(result));
    }

    static Stream<Arguments> sideMenuTest() {
        return Stream.of(
                Arguments.of(List.of("Сухие корма", "Лечебные корма", "Консервы", "Лакомства"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "тестирование отображения разделов в боковом меню")
    void sideMenuTest(List<String> result) {

        Selenide.open("catalog/tovary_dlya_sobak/");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//ul[@class = 'aside-menu__list']/li/a")
                .filter(Condition.visible).shouldHave(CollectionCondition.containExactTextsCaseSensitive(result));

    }

    @ValueSource(strings = {
            "Для кошек",
            "Для собак",
            "Для птиц",
            "Для рыб",
            "Для грызунов",
            "Товар недели",
            "Товар дня"
    }
    )
    @ParameterizedTest(name = "тестирование кнопки {0} на странице акций")
    void promotionTest(String razdel) {

        Selenide.open("discounts/action/");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//div[@class = 'tags  info-block__tags ']/a").findBy(Condition.text(razdel)).click();
    }

}
