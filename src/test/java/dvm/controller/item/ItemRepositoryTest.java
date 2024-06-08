package dvm.controller.item;

import dvm.domain.item.Item;
import dvm.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ItemRepositoryTest {
    @Test
    void itemNameListTest() {
        ItemRepository itemRepository = ItemRepository.getInstance();
        Map<Item, String> map = itemRepository.itemNameList();
        for (Item key : map.keySet()) {
            System.out.println("Key: " + key.getId() + ", Value: " + map.get(key));
        }
    }

    @Test
    void StockTest(){
        ItemRepository itemRepository = ItemRepository.getInstance();

        for (int i =1; i<= 20; i++){
            System.out.println("item : " + itemRepository.findItem(i).getId()+ ", Stock : "  + itemRepository.countItem(i));

        }
    }
}
