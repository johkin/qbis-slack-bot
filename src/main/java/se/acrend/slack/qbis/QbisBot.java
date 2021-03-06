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

    @Controller(events = EventType.DIRECT_MENTION)
    public void onReceiveDM(WebSocketSession session, Event event) {

        reply(session, event, new Message("Hej!\nVill du tidrapportera?\nSlacka direkt till mig!"));
    }
//    @Controller(events = {EventType.DIRECT_MESSAGE}, pattern = "hej")
//    public void onReceiveDM(WebSocketSession session, Event event) {
//        reply(session, event, new Message("Hej!\nVill du tidrapportera?"));
//    }
//
//    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = "(\\w+)\\W+(\\d+)h")
//    public void onReceiveDM(WebSocketSession session, Event event, Matcher matcher) {
//        reply(session, event,
//                new Message("Ok! " + matcher.group(2) + " timmar på projekt " + matcher.group(1)));
//    }
}
