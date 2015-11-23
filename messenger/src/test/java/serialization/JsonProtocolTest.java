package serialization;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;

public class JsonProtocolTest {
    private Protocol protocol;

    @Before
    public void setup() {
        protocol = new MyProtocol();
    }

    @Test
    public void SerializationTest(){
        String[] strings = new String[1];
        strings[0] = "Hello!";
        MessageBase original = new MessageBase(MessageType.MSG_CHATCREATE, strings);
        byte[] originalMessage = protocol.encode(original);

        MessageBase copy = protocol.decode(originalMessage);

        System.out.println(original.toString());
        System.out.println(copy.toString());

        assertEquals(copy.toString(), original.toString());
    }


}
