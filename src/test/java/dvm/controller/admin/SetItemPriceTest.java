//package dvm.controller.admin;
//
//import dvm.domain.item.ItemRepository;
//import dvm.service.controller.AdminPool;
//import dvm.service.controller.FrontController;
//import dvm.service.controller.admin.AdminServiceController;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class SetItemPriceTest {
//
//    AdminServiceController adminServiceController;
//
//    @BeforeEach
//    public void beforeEach() {
//        AdminPool adminPool = new AdminPool();
//        FrontController frontController = new FrontController();
//        frontController.setAdminMode(adminPool.getController("PRICE"));
//        adminServiceController = frontController.getAdminServiceController();
//    }
//
//    @Test
//    void process() {
//        //given
//        ItemRepository itemRepository = ItemRepository.getInstance();
//        int id = 1;
//        int price = itemRepository.findItem(id).getPrice();
//
//        //when
//        int newPrice = 4000;
//        adminServiceController.process(id,newPrice);
//
//        //then
//        Assertions.assertNotEquals(price,newPrice);
//        Assertions.assertEquals(newPrice,itemRepository.findItem(id).getPrice());
//    }
//}
