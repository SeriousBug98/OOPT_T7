package dvm.service.controller.card;

public class CardServiceController {

    private CardCheck cardCheck = new CardCheck();
    private Payment payment = new Payment();
    private Refund refund = new Refund();

    public void proceedPayment(String cardNum, int price) {

        boolean isVaild = cardCheck.checkCard(cardNum,price);
        if(isVaild==true) payment.proceedPayment(cardNum,price);
        else System.out.println("결제 실패");

    }

    public void proceedRefund(String cardNum, int price) {

    }
}
