package org.nirvana.pulsar.config;

import org.nirvana.pulsar.message.MyMsg;

/**
 * @author Nirvana
 */
public class PulsarTopic {
    /**
     * 类型结尾的类名称
     */
    public static final String CLASS_END_NAME = "_CLASS";

    public static final String MY_TOPIC = "my-topic";
    public static final Class<?> MY_TOPIC_CLASS = byte[].class;

    public static final String STR_TOPIC = "str-topic";
    public static final Class<?> STR_TOPIC_CLASS = String.class;

    public static final String MSG_TOPIC = "msg-topic";
    public static final Class<?> MSG_TOPIC_CLASS = MyMsg.class;
}
