package ru.geekbrains;

import ru.geekbrains.jsf.UserRepr;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class JmsClient {

    public static void main(String[] args) throws IOException, NamingException {
        Context context = createInitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        Destination destination = (Destination) context.lookup("topic/NewUserTopic");

        JMSContext jmsContext = connectionFactory.createContext("jms-user", "password");

        JMSConsumer consumer = jmsContext.createConsumer(destination);

        consumer.setMessageListener(message -> {
            try {
                System.out.println("New message " + message.getBody(UserRepr.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
