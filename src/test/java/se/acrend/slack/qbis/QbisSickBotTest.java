package se.acrend.slack.qbis;

import me.ramswaroop.jbot.core.slack.SlackService;
import me.ramswaroop.jbot.core.slack.models.Event;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.Assert.*;

/**
 *
 */
public class QbisSickBotTest {

    @InjectMocks
    private QbisSickBot bot;
    @Mock
    private WebSocketSession session;
    @Mock
    private SlackService slackService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSjukTimmar() throws Exception {
        Event event = new Event();
        event.setText("jag har varit sjuk i 4 timmar!");

        bot.sjukTimmar(session, event);

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);
        Mockito.verify(session).sendMessage(messageCaptor.capture());

        assertEquals("{\"id\":0,\"type\":\"message\",\"text\":\"Ok, 4 timmar är noterat. Krya på dig!\"}",
                messageCaptor.getValue().getPayload());

    }

}