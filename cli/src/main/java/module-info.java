module vigilia.cli {
    requires static lombok;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;
    requires org.openapitools.jackson.nullable;
    requires jakarta.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.cli;
    requires java.logging;

    opens org.schlunzis.vigilia.cli.model to com.fasterxml.jackson.databind;

    exports org.schlunzis.vigilia.cli;
}