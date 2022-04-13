package ru.gb.extensions;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({AllureJunit5.class, MobileShopApiTestExtension.class})
public @interface MobileShopApiTest {

}
