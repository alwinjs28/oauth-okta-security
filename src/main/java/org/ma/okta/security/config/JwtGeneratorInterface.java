package org.ma.okta.security.config;

import org.ma.okta.security.model.User;
import java.util.Map;

public interface JwtGeneratorInterface {

    Map<String, String> generateToken(User user);
}
