package dvm.service.controller.authenticaiton;

import dvm.domain.authentication.AuthenticationCodeRepository;

public class AuthenticationCodeFind implements AuthenticationServiceController<Boolean> {

    private AuthenticationCodeRepository authenticationCodeRepository = AuthenticationCodeRepository.getInstance();

    @Override
    public Boolean process(String authenticationCode) {
        return authenticationCodeRepository.findAuthenticationCode(authenticationCode);
    }
}
