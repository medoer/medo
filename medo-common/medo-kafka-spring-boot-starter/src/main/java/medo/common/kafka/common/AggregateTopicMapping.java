package medo.common.kafka.common;

public class AggregateTopicMapping {

  public static String aggregateTypeToTopic(String aggregateType) {
    return TopicCleaner.clean(aggregateType);
  }

}
