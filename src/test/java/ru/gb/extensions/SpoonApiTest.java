package ru.gb.extensions;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//пишем аннотацию для просмотра джавой методов, которые есть в том или ином классе (используется везде)

@Target({ElementType.TYPE, ElementType.METHOD})                                            //пишем над чем будет использоваться аннотация (тестовый метод над классом тестовым)
@Retention(RetentionPolicy.RUNTIME)                                                        //пишем где мы будем работать
@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class})                             //указываем экстеншен
public @interface SpoonApiTest {

}
