package jm.task.core.jdbc;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl serv = new UserServiceImpl();

        //Создание таблицы User(ов)
        serv.createUsersTable();

        //Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )

        serv.saveUser("Kolya", "Petuhov", (byte)25);
        serv.saveUser("Vasya", "Vasiljev", (byte)30);
        serv.saveUser("Petya", "Petrov", (byte)33);
        serv.saveUser("Ivan", "Ivanov", (byte)41);

        //Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        for (User user: serv.getAllUsers()) {
            System.out.println(user);
        }

        // Удаление юзверя по id
        serv.removeUserById(2);

        //Очистка таблицы User(ов)
        serv.cleanUsersTable();

        //Удаление таблицы
        serv.dropUsersTable();
    }
}
