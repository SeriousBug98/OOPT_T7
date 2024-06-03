package dvm.domain.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    // 나중에 이 item리스트랑 stock을 어디서 초기화해야할까 생각
    private static final List<Item> items = new ArrayList<>();
    private static Map<Item, Integer> stock = new HashMap<>();
    private static final ItemRepository instance = new ItemRepository();

    public static ItemRepository getInstance() {return instance;}

    private ItemRepository(){
        // 나중에 우리 자판기에서 판매 안하는 음료는 price를 0으로 초기화
        for (int i = 0; i < 20; i++) {
            Item item = new Item(i+1,1000);
            items.add(item);
            stock.put(item,10);
        }
        //우리 자판기에서 판매하는 음료 번호를 저장
        List<Integer> ourItemList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));

        // stock을 순회하면서 ourItemList에 없는 키의 값을 0으로 설정
        for (Map.Entry<Item, Integer> entry : stock.entrySet()) {
            if (!ourItemList.contains(entry.getKey().getId())) {
                stock.put(entry.getKey(), 0);
            }
        }
    }

    public int countItem(int itemId) {
        Item item = items.get(itemId-1);
        return stock.get(item);
    }

    public void updateItemStock(int itemId, int itemNum) {
        Item item = items.get(itemId-1);
        int nowItemNum = stock.get(item);
        stock.put(item, nowItemNum + itemNum); // 관리자가 입력한 수만큼 있는 재고에 더하는 방식
    }

    public Item findItem(int itemId) {
        return items.get(itemId-1);
    }

    public void updateItemPrice(int itemId, int itemPrice) {
        items.set(itemId-1, new Item(itemId, itemPrice));
    }

    public Map<Item, String> itemNameList() {
        List<String> itemNameList
                = new ArrayList<>(Arrays.asList("콜라", "사이다"));//나중에 더 추가
        Map<Item, String> itemWithName = new HashMap<>();
        for (int i = 0; i < items.size(); i++){
            itemWithName.put(items.get(i), itemNameList.get(i));
        }
        return itemWithName;
    }
}
