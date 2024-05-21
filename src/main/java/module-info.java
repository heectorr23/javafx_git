module com.empresa.hito2_programacion_hector {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;


    opens com.empresa.hito2_programacion_hector to javafx.fxml;
    exports com.empresa.hito2_programacion_hector;
}