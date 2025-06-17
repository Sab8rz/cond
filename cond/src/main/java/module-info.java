module com.example.javafxhomework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires lombok;
    requires org.hibernate.orm.core;

    opens com.example.javafxhomework to javafx.fxml;
    opens com.example.javafxhomework.models;
    exports com.example.javafxhomework;
}