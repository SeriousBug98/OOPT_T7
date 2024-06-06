package dvm.controller.authentication;

import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.service.controller.network.RequestToServiceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationCodeCreateTest {

    RequestToServiceController requestToServiceController =  new RequestToServiceController();

    @Test
    void testRandomStringLength() {
        //given
        AuthenticationCodeRepository authenticationCodeRepository = requestToServiceController.getAuthenticationCodeRepository();
        int length = 10;
        //when
        String actualString = authenticationCodeRepository.createAuthenticationCode();
        //then
        Assertions.assertEquals(length, actualString.length());
    }

    @Test
    void testRandomStringContents() {
        //given
        AuthenticationCodeRepository authenticationCodeRepository = requestToServiceController.getAuthenticationCodeRepository();
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //when
        String actualString = authenticationCodeRepository.createAuthenticationCode();
        //then
        for (char c : actualString.toCharArray()) {
            Assertions.assertTrue(validCharacters.indexOf(c) != -1);
        }
    }

    @Test
    void testRandomStringsAreUnique() {
        //given
        AuthenticationCodeRepository authenticationCodeRepository = requestToServiceController.getAuthenticationCodeRepository();
        //when
        String actualString1 = authenticationCodeRepository.createAuthenticationCode();
        String actualString2 = authenticationCodeRepository.createAuthenticationCode();
        //then
        Assertions.assertNotEquals(actualString1,actualString2);
    }
}
