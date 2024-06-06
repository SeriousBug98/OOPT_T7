package dvm.domain.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthenticationCodeRepository {

    // Map으로 하면 더 편하긴 한데 그냥 List로 해야겠지?
    private static List<String> codes = new ArrayList<>();
    private static final AuthenticationCodeRepository instance = new AuthenticationCodeRepository();

    public static AuthenticationCodeRepository getInstance() {return instance;}

    private AuthenticationCodeRepository() {
    }

    // 리턴 값을 int로 할 수 있으면 index값 리턴
    public boolean findAuthenticationCode(String authenticationCode) {
        int index = codes.indexOf(authenticationCode);
        if (index==-1) return false;
        else return true;
    }

    public void saveAuthenticationCode(String authenticationCode) {
        codes.add(authenticationCode);
    }

    public String createAuthenticationCode() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int stringLength = 10;
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
}
