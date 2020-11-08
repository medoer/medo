package medo.payment.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.HashMap;
import java.util.Map;

public class Sign {

    private static Map<String, JWTVerifier> VERIFIER_MAP = new HashMap<>();
    private static Map<String, Algorithm> ALGORITHM_MAP = new HashMap<>();

    public static Algorithm getAlgorithm(String signingToken) {
        Algorithm algorithm = ALGORITHM_MAP.get(signingToken);
        if (algorithm == null) {
            synchronized (ALGORITHM_MAP) {
                algorithm = ALGORITHM_MAP.get(signingToken);
                if (algorithm == null) {
                    algorithm = Algorithm.HMAC512(signingToken);
                    ALGORITHM_MAP.put(signingToken, algorithm);
                }
            }
        }
        return algorithm;
    }

    public static DecodedJWT verifyToken(String tokenString, String signingToken) {
        JWTVerifier verifier = VERIFIER_MAP.get(signingToken);
        if (verifier == null) {
            synchronized (VERIFIER_MAP) {
                verifier = VERIFIER_MAP.get(signingToken);
                if (verifier == null) {
                    Algorithm algorithm = Algorithm.HMAC512(signingToken);
                    verifier = JWT.require(algorithm).build();
                    VERIFIER_MAP.put(signingToken, verifier);
                }
            }
        }
        return verifier.verify(tokenString);
    }
}
