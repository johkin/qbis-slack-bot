package se.acrend.slack.qbis;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.SlackService;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import se.acrend.slack.qbis.entity.QbisUser;
import se.acrend.slack.qbis.repository.QbisUserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Component
public class QbisSetupBot extends Bot {

    @Value("${slackBotToken}")
    private String slackToken;

    @Autowired
    private QbisUserRepository qbisUserRepository;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    public boolean isConfigured(String slackId){
        QbisUser user = qbisUserRepository.findBySlackId(slackId);
        return user != null;
    }

    @Controller(events = EventType.DIRECT_MESSAGE,
            pattern = "konfigurera")
    public void startSetup(WebSocketSession session, Event event) {
        QbisUser user = qbisUserRepository.findBySlackId(event.getUserId());
        if (user == null) {
            reply(session, event,
                    new Message("Du verkar vara ny här, jag behöver veta lite mer om dig!"));
            reply(session, event,
                    new Message("Vilket företag använder du i QBis?"));
//            startConversation(event, "");
        } else {

        }
    }



}
