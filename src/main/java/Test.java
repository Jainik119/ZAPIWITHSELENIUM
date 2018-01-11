import zapi.Zapi;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Test {

    public static void main(String []args) throws Exception {
        new Zapi().getAndUpdateStepResult();
        //new Zapi().getExecution();
        //new Zapi().getAndExecuteCycle();
    }
}
