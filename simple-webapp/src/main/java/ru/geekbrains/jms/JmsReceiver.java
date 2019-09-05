package ru.geekbrains.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.jsf.UserRepr;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/topic/NewUserTopic"),
})
public class JmsReceiver implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(JmsReceiver.class);

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("New message {}", message.getBody(UserRepr.class));
        } catch (JMSException e) {
            logger.error("Error", e);
        }
    }
}
