package java8.functionalinterface;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FunctionalInterfaceTest {

	@Test
	public void checkTest() {
		// 是否是新建交易
		ITrade newTradeCheck = (Trade t) -> {
			return t.getStatus().equals("new");
		};

		// 是否是大额交易
		ITrade bigTradeCheck = (t) -> t.getQuantity() > 10000;
		
		List<Trade> listCollection = new ArrayList<Trade>();
	}

	/**
	 * description: 过滤筛选操作
	 * @param tradeLambda 
	 * @param trades
	 * @return
	 * @time: 2017年4月28日 上午8:42:14
	 */
	private List<Trade> filterTrades(ITrade tradeLambda, List<Trade> trades) {
		List<Trade> newTrades = new ArrayList<>();

		for (Trade trade : trades) {
			if (tradeLambda.check(trade)) {
				newTrades.add(trade);
			}
		}
		return newTrades;
	}
}
