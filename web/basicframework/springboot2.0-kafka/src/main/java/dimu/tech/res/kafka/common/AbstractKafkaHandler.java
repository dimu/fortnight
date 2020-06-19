package dimu.tech.res.kafka.common;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public abstract  class AbstractKafkaHandler<T> {

    private boolean stopOrPause;

    private String containerId;

    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public abstract boolean process(T t) throws CaptureException, NoSuchAlgorithmException;

    public AbstractKafkaHandler(boolean stopOrPause, String containerId, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.stopOrPause = stopOrPause;
        this.containerId = containerId;
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    void processorHandler(T t) {
        try {
            process(t);
        } catch (CaptureException e) {
            MessageListenerContainer messageListener = kafkaListenerEndpointRegistry.getListenerContainer(containerId);
            if (stopOrPause) {
                messageListener.stop();
            } else {
                messageListener.pause();
                while(true) {
                    try {
                        if (process(t)) {
                            break;
                        }
                        TimeUnit.MILLISECONDS.wait(500);
                    } catch (CaptureException captureException) {
                        captureException.printStackTrace();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                messageListener.resume();

            }
        }
    }

}
