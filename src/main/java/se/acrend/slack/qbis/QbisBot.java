package se.acrend.slack.qbis;

import me.ramswaroop.jbot.slackbot.core.Bot;
import me.ramswaroop.jbot.slackbot.core.Controller;
import me.ramswaroop.jbot.slackbot.core.EventType;
import me.ramswaroop.jbot.slackbot.core.SlackService;
import me.ramswaroop.jbot.slackbot.core.models.Event;
import me.ramswaroop.jbot.slackbot.core.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.regex.Matcher;

/**
 *
 */
@Component
public class QbisBot extends Bot {

    @Value("${slackBotToken}")
    private String slackToken;

    @Autowired
    private SlackService slackService;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, new Message("Hej!\nVill du tidrapportera?"));
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = "(\\w+)\\W+(\\d+)h")
    public void onReceiveDM(WebSocketSession session, Event event, Matcher matcher) {
        reply(session, event,
                new Message("Ok! " + matcher.group(2) + " timmar på projekt " + matcher.group(1)));
    }
}
