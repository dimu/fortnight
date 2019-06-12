package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ListMapTest {

	/**
	 * description: 测试如何删除List<Map>数据
	 * @time: 2017年5月9日 上午10:07:58
	 */
	@Test
	public void testListMapDelete() {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 123);
		listMap.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("key2", 13);
		listMap.add(map1);
		listMap.remove(0);
//		listMap.remove(1);
		listMap.removeAll(Arrays.asList(map1,map));
		System.out.println(listMap.size());
	}
}
