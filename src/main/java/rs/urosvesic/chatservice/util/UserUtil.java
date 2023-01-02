package rs.urosvesic.chatservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {


    public static Jwt getPrincipal(){ return  (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}

    public static String getToken(){ return getPrincipal().getTokenValue(); }

    public static String getBearerToken(){ return "Bearer " + getPrincipal().getTokenValue(); }
    public static String getCurrentUsername(){
        return getPrincipal().getClaimAsString("username");
    }

    public static String getCurrentUserId(){
        return getPrincipal().getClaimAsString("sub");
    }


}
