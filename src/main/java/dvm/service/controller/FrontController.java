package dvm.service.controller;

import dvm.service.controller.admin.AdminServiceController;
import dvm.service.controller.authenticaiton.AuthenticationServiceController;
import dvm.service.controller.card.CardServiceController;
import dvm.service.controller.item.ItemServiceController;
import dvm.service.controller.network.RequestFromServiceController;
import dvm.service.controller.network.RequestToServiceController;

public class FrontController {

    private static CardServiceController cardServiceController;
    private static ItemServiceController itemServiceController;
    private static AuthenticationServiceController authenticationServiceController;
    private static AdminServiceController adminServiceController;
    private static RequestToServiceController requestToServiceController;
    private static RequestFromServiceController requestFromServiceController;

    public FrontController() {
        this.cardServiceController = new CardServiceController();
        this.requestToServiceController = new RequestToServiceController();
        this.requestFromServiceController = new RequestFromServiceController();
    }

    public void setItemMode(ItemServiceController itemServiceController) {
        this.itemServiceController = itemServiceController;
    }

    public void setAuthenticationMode(AuthenticationServiceController authenticationServiceController) {
        this.authenticationServiceController = authenticationServiceController;
    }

    public void setAdminMode(AdminServiceController adminServiceController) {
        this.adminServiceController = adminServiceController;
    }

    public static CardServiceController getCardServiceController() {
        return cardServiceController;
    }

    public static ItemServiceController getItemServiceController() {
        return itemServiceController;
    }

    public static AuthenticationServiceController getAuthenticationServiceController() {
        return authenticationServiceController;
    }

    public static AdminServiceController getAdminServiceController() {
        return adminServiceController;
    }

    public static RequestToServiceController getRequestToServiceController() {
        return requestToServiceController;
    }

    public static RequestFromServiceController getRequestFromServiceController() {
        return requestFromServiceController;
    }
}
