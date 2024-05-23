package com.empresa.hito2_programacion_hector;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AppController {
    @FXML
    private Button enviarCarritoButton;
    @FXML
    private TextArea piezasTextArea;
    @FXML
    private Button inicioButton;
    @FXML
    private TableView<Pieza> piecesTableView;
    @FXML
    private TableColumn<Pieza, String> idColumn;
    @FXML
    private TableColumn<Pieza, String> nameColumn;
    @FXML
    private TableColumn<Pieza, String> numeroSerieColumn;
    @FXML
    private TableColumn<Pieza, String> fabricanteColumn;
    @FXML
    private TableColumn<Pieza, String> descripcionColumn;
    @FXML
    private TableColumn<Pieza, String> marcaVehiculoColumn;
    @FXML
    private TableColumn<Pieza, String> modeloVehiculoColumn;
    @FXML
    private Button administrarButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button applyFilterButton;
    private FilteredList<Pieza> filteredData;
    private DatabaseManagerApp dbManager;

    public AppController() {
        dbManager = new DatabaseManagerApp();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        numeroSerieColumn.setCellValueFactory(new PropertyValueFactory<>("numero_serie"));
        fabricanteColumn.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        marcaVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("marcaVehiculo"));
        modeloVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("modeloVehiculo"));
        inicioButton.setOnAction(event -> volverInicio());
        piecesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        piecesTableView.setRowFactory(tv -> {
            TableRow<Pieza> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Pieza selectedPiece = row.getItem();
                    Carrito.piezasSeleccionadas.add(selectedPiece);
                    piezasTextArea.appendText(selectedPiece.getNombre() + "\n"); // Add selected product to TextArea
                }
            });
            return row;
        });
        enviarCarritoButton.setOnAction(event -> {
            ObservableList<Pieza> selectedPieces = piecesTableView.getSelectionModel().getSelectedItems();
            if (!selectedPieces.isEmpty()) {
                Carrito.piezasSeleccionadas.addAll(selectedPieces);
                System.out.println("Productos seleccionados: " + Carrito.piezasSeleccionadas); // Print selected products
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/carrito-view.fxml")));
                    Stage stage = (Stage) enviarCarritoButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, selecciona al menos un producto antes de ir al carrito.");
                alert.showAndWait();
            }
        });
        administrarButton.setOnAction(event -> abrirPanelAdmin());
        showPieces();
        filteredData = new FilteredList<>(piecesTableView.getItems(), p -> true);

        // 2. Set the filter Predicate whenever the button is clicked.
        applyFilterButton.setOnAction(event -> {
            String filterText = searchField.getText();
            if (filterText != null && !filterText.isEmpty()) {
                filteredData.setPredicate(pieza -> {
                    // Compare piece name with filter text.
                    String lowerCaseFilter = filterText.toLowerCase();

                    if (pieza.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches piece name.
                    }
                    return false; // Does not match.
                });
            } else {
                // If filter text is empty, display all pieces.
                filteredData.setPredicate(p -> true);
            }
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Pieza> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(piecesTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        piecesTableView.setItems(sortedData);
    }

    @FXML
    private void showPieces() {
        List<Pieza> pieces = dbManager.getAllPieces();
        piecesTableView.getItems().setAll(pieces);
    }
    private void volverInicio() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/inicio-view.fxml")));
            Stage stage = (Stage) inicioButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void abrirPanelAdmin() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/admin-view.fxml")));
            Stage stage = (Stage) administrarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}