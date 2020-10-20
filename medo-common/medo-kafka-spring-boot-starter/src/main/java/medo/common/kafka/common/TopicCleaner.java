package medo.common.kafka.common;

public class TopicCleaner {

    public static String clean(String topic) {
        return topic.replace("$", "_DLR_");
    }
}
