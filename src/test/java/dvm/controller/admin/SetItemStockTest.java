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
//class SetItemStockTest {
//
//    AdminServiceController adminServiceController;
//
//    @BeforeEach
//    public void beforeEach() {
//        AdminPool adminPool = new AdminPool();
//        FrontController frontController = new FrontController();
//        frontController.setAdminMode(adminPool.getController("STOCK"));
//        adminServiceController = frontController.getAdminServiceController();
//    }
//
//
//    @Test
//    void process() {
//        //given
//        ItemRepository itemRepository = ItemRepository.getInstance();
//        int id = 1;
//        int stock = itemRepository.countItem(id);
//        int updateStock = 3;
//        int expectedStock = stock + updateStock;
//
//        //when
//        adminServiceController.process(id,updateStock);
//
//        //then
//        Assertions.assertEquals(expectedStock, itemRepository.countItem(id));
//    }
//}
