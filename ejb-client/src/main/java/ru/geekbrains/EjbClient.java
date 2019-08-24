package ru.geekbrains;

import ru.geekbrains.jsf.UserServiceRemoteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class EjbClient {

    public static void main(String[] args) throws IOException, NamingException {
        Context context = createInitialContext();

        UserServiceRemoteBean usr = (UserServiceRemoteBean) context.lookup("ejb:/simple-webapp//UserServiceBean!ru.geekbrains.jsf.UserServiceRemoteBean");
        usr.getAllUsers()
                .forEach(u -> System.out.printf("%d\t%s%n", u.getId(), u.getLogin()));

    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
