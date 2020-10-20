package medo.framework.saga.orchestration;

import medo.common.core.json.JSONMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SagaDataSerde {
    private static Logger logger = LoggerFactory.getLogger(SagaDataSerde.class);

    public static <Data> SerializedSagaData serializeSagaData(Data sagaData) {
        return new SerializedSagaData(sagaData.getClass().getName(), JSONMapper.toJSON(sagaData));
    }

    public static <Data> Data deserializeSagaData(SerializedSagaData serializedSagaData) {
        Class<?> clasz = null;
        try {
            clasz =
                    Thread.currentThread()
                            .getContextClassLoader()
                            .loadClass(serializedSagaData.getSagaDataType());
        } catch (ClassNotFoundException e) {
            logger.error("Class not found", e);
            throw new RuntimeException("Class not found", e);
        }
        Object x = JSONMapper.fromJSON(serializedSagaData.getSagaDataJSON(), clasz);
        return (Data) x;
    }
}
