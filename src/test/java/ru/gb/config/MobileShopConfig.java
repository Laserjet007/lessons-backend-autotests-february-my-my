package ru.gb.config;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
//создаем еще один конфиг для примера работы с магазином мобилок
@Config.Sources({"file:src/test/resources/shopconfig.properties"})
public interface MobileShopConfig extends Config {
    MobileShopConfig mobileShopConfig = ConfigFactory.create(MobileShopConfig.class);

    String baseURI();
}