package ru.gb.extensions;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//тут находятся аннотации (@) которые включают в себя некоторые extensions
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({AllureJunit5.class, MobileShopApiTestExtension.class, CommonApiTestExtension.class})   //CommonApiTestExtension - ставим по дефолту отнаследиться для аутинфикациина сайте (тут находятся аннотации (@) которые включают в себя некоторые extensions)
public @interface MobileShopApiTest {

}
