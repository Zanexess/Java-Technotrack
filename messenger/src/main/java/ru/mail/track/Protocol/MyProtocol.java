package ru.mail.track.Protocol;

import ru.mail.track.Messeges.MessageBase;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class MyProtocol implements Protocol {
    public MessageBase decode(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = new String(bytes, "UTF-8");
            MessageBase msg = mapper.readValue(json, MessageBase.class);
            return msg;
        }  catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encode(MessageBase msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonMsg = mapper.writeValueAsString(msg);
            return jsonMsg.getBytes("UTF-8");
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
