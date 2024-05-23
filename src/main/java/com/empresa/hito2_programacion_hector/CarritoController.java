package com.empresa.hito2_programacion_hector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CarritoController {
    @FXML
    private TableView<Pieza> selectedPiecesTableView;
    @FXML
    private TableColumn<Pieza, String> selectedIdColumn;
    @FXML
    private TableColumn<Pieza, String> selectedNameColumn;
    @FXML
    private TableColumn<Pieza, String> selectedNumeroSerieColumn;
    @FXML
    private TableColumn<Pieza, String> selectedFabricanteColumn;
    @FXML
    private TableColumn<Pieza, String> selectedDescripcionColumn;
    @FXML
    private TableColumn<Pieza, String> selectedMarcaVehiculoColumn;
    @FXML
    private TableColumn<Pieza, String> selectedModeloVehiculoColumn;
    @FXML
    private ListView<Pieza> carritoListView;
    @FXML
    private Button loadProductsButton;
    @FXML
    private Button comprarButton;
    @FXML
    private Button atrasButton;

    @FXML
    public void initialize() {
        selectedIdColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        selectedNameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        selectedNumeroSerieColumn.setCellValueFactory(new PropertyValueFactory<>("numero_serie"));
        selectedFabricanteColumn.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        selectedDescripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        selectedMarcaVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("marcaVehiculo"));
        selectedModeloVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("modeloVehiculo"));

        loadProducts();

        atrasButton.setOnAction(event -> volverAtras());
        comprarButton.setOnAction(event -> comprarProductos());
    }

    private void loadProducts() {
        selectedPiecesTableView.getItems().clear();
        selectedPiecesTableView.getItems().addAll(Carrito.piezasSeleccionadas);
        selectedPiecesTableView.refresh(); // Refresh the TableView
    }

    private void comprarProductos() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Compra realizada");
        alert.setHeaderText(null);
        alert.setContentText("Gracias por realizar la compra");
        alert.showAndWait();
    }

    private void volverAtras() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/app-view.fxml")));
            Stage stage = (Stage) atrasButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}