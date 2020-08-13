package dwx.tech.res.flink.stream.frauddetection;

import java.time.LocalDateTime;

/**
 * 模拟的交易
 *
 * @author dwx
 * @date 2020-08-11
 */
public class Transaction {

	public long accountId;

	public int amount;

	public LocalDateTime timestamp;

	long getAccountId() {
		return accountId;
	}

	void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	int getAmount() {
		return amount;
	}

	void setAmount(int amount) {
		this.amount = amount;
	}

	LocalDateTime getTimestamp() {
		return timestamp;
	}

	void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
