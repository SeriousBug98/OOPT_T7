package dvm.service.controller.card;

public class CardServiceController {

    private CardCheck cardCheck = new CardCheck();
    private Payment payment = new Payment();
    private Refund refund = new Refund();

    public boolean isValidCard(String cardNum) {
        return cardCheck.checkCardNum(cardNum); // 카드 정보만 확인
    }

    public boolean isBalanceSufficient(String cardNum, int price) {
        return cardCheck.checkCardBalance(cardNum, price); // 카드 잔액 확인
    }

    public boolean requestPayment(String cardNum, int price) {
        // cardNum은 UI단에서 받아옴
        boolean isCardValid = isValidCard(cardNum);
        boolean isBalanceSufficient = isCardValid && isBalanceSufficient(cardNum, price);

        if (isBalanceSufficient) {
            payment.sendPayment(cardNum, price);
        } else {
            System.out.println("결제 실패");
        }

        return isBalanceSufficient;
    }

    public void proceedRefund(String cardNum, int price) {
        boolean isCardValid = isValidCard(cardNum);
        boolean isBalanceSufficient = isCardValid && isBalanceSufficient(cardNum, price);

        if (isBalanceSufficient) {
            refund.proceedRefund(cardNum, price);
        } else {
            System.out.println("환불 실패");
        }
    }
}
