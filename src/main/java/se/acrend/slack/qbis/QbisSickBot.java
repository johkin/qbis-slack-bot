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
import java.util.regex.Pattern;

/**
 *
 */
@Component
public class QbisSickBot extends Bot {

    @Value("${slackBotToken}")
    private String slackToken;

    @Autowired
    private QbisSetupBot setupBot;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = EventType.DIRECT_MESSAGE,
        pattern = "(sjuk|mår illa|mår dåligt)", next = "bekraftaSjuk")
    public void sjukStart(WebSocketSession session, Event event) {
        if (!setupBot.isConfigured(event.getUserId())) {
            setupBot.startSetup(session, event);
            return;
        }
        startConversation(event, "bekraftaSjuk");
        reply(session, event,
            new Message("Nämen, är du sjuk?"));
    }

    @Controller(events = EventType.DIRECT_MESSAGE,
        next = "sjukHelaDagen")
    public void bekraftaSjuk(WebSocketSession session, Event event) {
        if (event.getText().toLowerCase().contains("nej")) {
            reply(session, event,
                new Message("Ok, då missförstod jag dig!"));
            stopConversation(event);
        } else {
            reply(session, event,
                new Message("Ajdå, har du varit sjuk hela dagen?"));
            nextConversation(event);
        }
    }

    @Controller(events = EventType.DIRECT_MESSAGE,
        next = "sjukTimmar")
    public void sjukHelaDagen(WebSocketSession session, Event event) {
        if (event.getText().toLowerCase().contains("nej")) {
            reply(session, event,
                new Message("Ok, hur många timmar har du varit sjuk?"));
            nextConversation(event);
        } else {
            reply(session, event,
                new Message("Ok, det är noterat. Krya på dig!"));
            stopConversation(event);
        }
    }

    @Controller(events = EventType.DIRECT_MESSAGE)
    public void sjukTimmar(WebSocketSession session, Event event) {
        final Pattern pattern = Pattern.compile("\\D*(\\d+)\\D*");

        final Matcher matcher = pattern.matcher(event.getText());
        if (matcher.matches()) {
            final int timmar = Integer.parseInt(matcher.group(1));

            reply(session, event,
                    new Message("Ok, " + timmar + " timmar är noterat. Krya på dig!"));
            stopConversation(event);
        } else {
            reply(session, event,
                    new Message("Jag förstod inte riktigt, hur många timmar var du sjuk?"));
        }
    }
}
