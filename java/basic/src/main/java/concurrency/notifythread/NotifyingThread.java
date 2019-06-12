package concurrency.notifythread;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 定义一个通知类，所有需要主动通知的类都实现此方法，只暴露了业务逻辑方法doRun给子类
 * @author dwx
 *
 */
public abstract class NotifyingThread extends Thread {
	private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

	public final void addListener(final ThreadCompleteListener listener) {
		listeners.add(listener);
	}

	public final void removeListener(final ThreadCompleteListener listener) {
		listeners.remove(listener);
	}

	private final void notifyListeners() {
		for (ThreadCompleteListener listener : listeners) {
			listener.notifyOfThreadComplete(this);
		}
	}

	@Override
	public final void run() {
		try {
			doRun();
		} finally {
			notifyListeners();
		}
	}

	public abstract void doRun();
}
