package am.jms.receivers;

import am.jms.messages.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class.getName());

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receive(Email email) {
        LOGGER.info("received email {}", email);
    }
}
