#language: ru
@вход
Функционал: вход
  Структура сценария: вход
    Если открываю ссылку "http://localhost:8080/"
    То вижу страницу "Авторизация"
    И заполняю поля:
      | Логин  | <логин>  |
      | Пароль | <пароль> |
    Также жму кнопку "войти"
    То вижу страницу "Главная страница"
    Примеры:
      | логин | пароль |
      | user  | user   |
      | user1  | user  |

#  Сценарий: выход
#    Если вижу страницу "Главная страница"
#    И жму кнопку "войти"
#    То вижу страницу "Авторизация"