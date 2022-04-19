package ru.gb.endpoints.shop_mobail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)                       //указываем над чем будет аннотация
@Target({ElementType.TYPE})                               //указываем тип(для данного примера необязательно)

public @interface Endpoint {
    String value();

}
