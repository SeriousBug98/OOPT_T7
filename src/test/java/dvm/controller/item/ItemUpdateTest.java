package dvm.controller.item;

import dvm.domain.item.ItemRepository;
import dvm.service.controller.FrontController;
import dvm.service.controller.ItemPool;
import dvm.service.controller.item.ItemServiceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemUpdateTest {

    ItemServiceController itemServiceController;

    @BeforeEach
    public void beforeEach() {
        ItemPool itemPool = new ItemPool();
        FrontController frontController = new FrontController();
        frontController.setItemMode(itemPool.getController("UPDATE"));
        itemServiceController = frontController.getItemServiceController();
    }

    @Test
    void process() {
        //given
        ItemRepository itemRepository = ItemRepository.getInstance();
        int id = 1;
        int count = itemRepository.countItem(id);
        int updateCount = -3;
        int expectedCount = count + updateCount;

        //when
        itemServiceController.process(id, updateCount);

        //then
        Assertions.assertEquals(expectedCount, itemRepository.countItem(id));
    }
}
