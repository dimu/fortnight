package java8.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

/**
 * description: JavaScript引擎Nashorn
 *
 * @version 2017年5月3日 下午3:20:42
 * @see
 * modify content------------author------------date
 */
public class NashornTest {
	
	@Test
	public void nashornTest() {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

		String name = "Mahesh";
		Integer result = null;

		try {
			nashorn.eval("print('" + name + "')");
			result = (Integer) nashorn.eval("10 + 2");
		} catch (ScriptException e) {
			System.out.println("Error executing script: " + e.getMessage());
		}

		System.out.println(result.toString());
	}
	
}
