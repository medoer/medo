package medo.framework.saga.orchestration;

import java.util.function.Function;

public interface StartingHandler<Data> extends Function<Data, SagaActions> {}
