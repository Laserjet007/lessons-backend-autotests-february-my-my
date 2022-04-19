package ru.gb.endpoints.shop_mobail;
//создаём абстрактный класс, от которого будем наследоваться. (классы пейджи которые будут выполнять определенные задачи для endpoint)
public abstract class BaseEndpoint {
    String endpoint = this.getClass().getAnnotation(Endpoint.class).value(); //создаем строчку endpoint инициализируя классом аннотаций (забираем TYPE из аннотации). указываем что у класса будет аннотация с помощью ревлекшина можно забирать аннотации
}
