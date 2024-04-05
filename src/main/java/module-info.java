module com.example.nlrs_main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires edu.stanford.nlp.corenlp;
    exports com.example.nlrs_main;
    opens com.example.nlrs_main to javafx.fxml;
}