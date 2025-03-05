module vigilia.gui.fx {
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;
    requires org.openapitools.jackson.nullable;
    requires jakarta.annotation;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires javafx.controls;
    requires static lombok;

    opens org.schlunzis.vigilia.gui.fx.model to com.fasterxml.jackson.databind;

    exports org.schlunzis.vigilia.gui.fx;
}