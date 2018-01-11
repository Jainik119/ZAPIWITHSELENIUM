package zapi;

import java.net.URI;

/**
 * Created by Jainik Bakaraniya on 12/29/17.
 */
public interface JwtGenerator {

    String generateJWT(String requestMethod, URI uri, int jwtExpiryWindowSeconds);
}
