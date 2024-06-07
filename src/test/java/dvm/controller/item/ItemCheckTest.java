//package dvm.controller.item;
//
//import dvm.service.controller.FrontController;
//import dvm.service.controller.ItemPool;
//import dvm.service.controller.item.ItemServiceController;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class ItemCheckTest {
//
//    ItemServiceController itemServiceController;
//
//    @BeforeEach
//    public void beforeEach() {
//        ItemPool itemPool = new ItemPool();
//        FrontController frontController = new FrontController();
//        frontController.setItemMode(itemPool.getController("CHECK"));
//        itemServiceController = frontController.getItemServiceController();
//    }
//
//    @Test
//    void process() {
//        //given
//        int id = 1;
//        int count = 3;
//
//        //when
//        boolean isChecked = (boolean) itemServiceController.process(id, count);
//
//        //then
//        Assertions.assertTrue(isChecked);
//    }
//}
