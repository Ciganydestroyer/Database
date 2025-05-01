package Methods;

import java.util.HashMap;
import java.util.Map;

public class MyUtils {
    public static Map<String, String> getStringStringMap(char[] body) {
        String jsonBody = new String(body);

        int substring1 = 0, substring2 = 0, substring3 = 0, substring4 = 0;
        Map<String,String> map = new HashMap<>();

        for (int i = 0; i < body.length; i++) {
            if (body[i] == '"' && substring1 == 0) {
                substring1 = i;
            } else if (body[i] == '"' && substring1 != 0 && substring2 == 0) {
                substring2 = i;
            } else if (body[i] == '"' && substring1 != 0 && substring2 != 0 && substring3 == 0) {
                substring3 = i;
            } else if (body[i] == '"' && substring1 != 0 && substring2 != 0 && substring3 != 0) {
                substring4 = i;
            }

            if (substring1 != 0 && substring2 != 0 && substring3 != 0 && substring4 != 0) {
                map.put(
                        jsonBody.substring(substring1 + 1, substring2),
                        jsonBody.substring(substring3 + 1, substring4)
                );
                substring1 = 0;
                substring2 = 0;
                substring3 = 0;
                substring4 = 0;
            }
        }

        return map;
    }
}