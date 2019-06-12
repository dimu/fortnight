package jackson;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONTest {
	
	@Test
	public void JSONTest() throws IOException {
		String msg = "{\"id\"=123, \"age\"=18}";
		String msg1 = "{\"id\":123, \"age\":18}";
		System.out.println(net.sf.json.JSONObject.fromObject(msg));
		System.out.println(com.alibaba.fastjson.JSONObject.parseObject(msg1));
		System.out.println();
//		System.out.println(com.alibaba.fastjson.JSONObject.parseObject(msg));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(msg1);
		if (node.has("id")) {
			System.out.println(node.get("id").asText());
		}
		
		
	}

}
