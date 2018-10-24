package test.jdk.enum_test;

/**
 * Created by 张少昆 on 2018/1/12.
 */
public enum TemplateEnum {
    SOURCE_FILE("IDEAL-CAP", "source_file.template"),
    SOURCE_CARRYFILE("IDEAL-CARRY-FILE", "source_carry_file.template"),
    SOURCE_KAFKA_V8("IDEAL-KAFKA-V8", "source_kafka_v8.template"),
    SOURCE_KAFKA_V10("IDEAL-KAFKA-V10", "source_kafka_v10.template"),
    SOURCE_DATABASE("IDEAL-DATABASE", "source_database.template"),
    SOURCE_AVRO("IDEAL-AVRO", "source_avro.template"),
    SOURCE_HBASE("IDEAL-HBASE", "source_hbase.template"),
    SINK_FILE("IDEAL-FILE-SINK", "sink_file.template"),
    SINK_KAFKA("IDEAL-KAFKA", "sink_kafka.template"),
    SINK_DATABASE("IDEAL-DATABASE-SINK", "sink_database.template"),
    SINK_HBASE("IDEAL-HBASE-SINK", "sink_hbase.template");

    private final String typeName;
    private final String templateName;

    private TemplateEnum(String typeName, String templateName) {
        this.typeName = typeName;
        this.templateName = templateName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getTemplateName() {
        return this.templateName;
    }
}