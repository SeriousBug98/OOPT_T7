package dvm.service.controller.card;

public class CardServiceController {

    private CardCheck cardCheck = new CardCheck();
    private Payment payment = new Payment();
    private Refund refund = new Refund();

    public boolean proceedPayment(String cardNum, int price) {
        //cardNum은 UI단에서 받아옴

        boolean isVaild = cardCheck.checkCard(cardNum,price);
        if(isVaild==true) payment.proceedPayment(cardNum,price);
        else System.out.println("결제 실패");

        return isVaild;
    }

    public void proceedRefund(String cardNum, int price) {
        boolean isVaild = cardCheck.checkCard(cardNum,price);
        if(isVaild==true) refund.proceedRefund(cardNum,price);
        else System.out.println("환불 실패");


    }
}
