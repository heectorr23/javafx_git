package com.empresa.hito2_programacion_hector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AdminController {

    @FXML
    private Button buttonGoToAppView;
    @FXML
    private TableView<Pieza> adminPiecesTableView;
    @FXML
    private TableColumn<Pieza, String> adminIdColumn;
    @FXML
    private TableColumn<Pieza, String> adminNameColumn;
    @FXML
    private TableColumn<Pieza, String> adminNumeroSerieColumn;
    @FXML
    private TableColumn<Pieza, String> adminFabricanteColumn;
    @FXML
    private TableColumn<Pieza, String> adminDescripcionColumn;
    @FXML
    private TableColumn<Pieza, String> adminMarcaVehiculoColumn;
    @FXML
    private TableColumn<Pieza, String> adminModeloVehiculoColumn;
    @FXML
    private TableColumn<Pieza, Integer> adminAnoFabricacionColumn;
    @FXML
    private Button buttonModificarPieza;
    @FXML
    private Button buttonEliminarPieza;
    @FXML
    private Button buttonAnadirPieza;

    private DatabaseManagerApp dbManager;

    public AdminController() {
        dbManager = new DatabaseManagerApp();
    }

    @FXML
    public void initialize() {
        adminIdColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        adminNumeroSerieColumn.setCellValueFactory(new PropertyValueFactory<>("numero_serie"));
        adminFabricanteColumn.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        adminDescripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        adminMarcaVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("marcaVehiculo"));
        adminModeloVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("modeloVehiculo"));
        buttonGoToAppView.setOnAction(e -> goToAppView());
        showPieces();
        buttonAnadirPieza.setOnAction(e -> anadirPieza());
        buttonEliminarPieza.setOnAction(e -> eliminarPieza());
        buttonModificarPieza.setOnAction(e -> modificarPieza());
    }
    private void anadirPieza() {
        // Crear un nuevo diálogo
        Dialog<Pieza> dialog = new Dialog<>();
        dialog.setTitle("Añadir Pieza");

        // Configurar los botones del diálogo
        ButtonType addButton = new ButtonType("Añadir", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Crear los campos de texto para los detalles de la pieza
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");
        TextField numeroSerieField = new TextField();
        numeroSerieField.setPromptText("Número de Serie");
        TextField fabricanteField = new TextField();
        fabricanteField.setPromptText("Fabricante");
        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripción");
        TextField marcaVehiculoField = new TextField();
        marcaVehiculoField.setPromptText("Marca del Vehículo");
        TextField modeloVehiculoField = new TextField();
        modeloVehiculoField.setPromptText("Modelo del Vehículo");

        // Agregar los campos de texto al diálogo
        GridPane grid = new GridPane();
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Número de Serie:"), 0, 2);
        grid.add(numeroSerieField, 1, 2);
        grid.add(new Label("Fabricante:"), 0, 3);
        grid.add(fabricanteField, 1, 3);
        grid.add(new Label("Descripción:"), 0, 4);
        grid.add(descripcionField, 1, 4);
        grid.add(new Label("Marca del Vehículo:"), 0, 5);
        grid.add(marcaVehiculoField, 1, 5);
        grid.add(new Label("Modelo del Vehículo:"), 0, 6);
        grid.add(modeloVehiculoField, 1, 6);
        dialog.getDialogPane().setContent(grid);

        // Convertir los resultados del diálogo a una nueva instancia de Pieza cuando el botón Añadir se presiona
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Pieza(idField.getText(), nameField.getText(), numeroSerieField.getText(), fabricanteField.getText(), descripcionField.getText(), marcaVehiculoField.getText(), modeloVehiculoField.getText());
            }
            return null;
        });

        // Mostrar el diálogo y esperar a que el usuario cierre el diálogo
        Optional<Pieza> result = dialog.showAndWait();

        // Añadir la nueva pieza a la base de datos y actualizar la tabla si el usuario presionó el botón Añadir
        result.ifPresent(pieza -> {
            dbManager.addPiece(pieza);
            showPieces();
        });
    }
    private void modificarPieza() {
        // Obtener la pieza seleccionada en la tabla
        Pieza selectedPiece = adminPiecesTableView.getSelectionModel().getSelectedItem();

        if (selectedPiece != null) {
            // Crear un nuevo diálogo
            Dialog<Pieza> dialog = new Dialog<>();
            dialog.setTitle("Modificar Pieza");

            // Configurar los botones del diálogo
            ButtonType modifyButton = new ButtonType("Modificar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(modifyButton, ButtonType.CANCEL);

            // Crear los campos de texto para los detalles de la pieza
            TextField idField = new TextField(String.valueOf(selectedPiece.get_id()));
            TextField nameField = new TextField(selectedPiece.getNombre());
            TextField numeroSerieField = new TextField(selectedPiece.getNumero_serie());
            TextField fabricanteField = new TextField(selectedPiece.getFabricante());
            TextField descripcionField = new TextField(selectedPiece.getDescripcion());
            TextField marcaVehiculoField = new TextField(selectedPiece.getMarcaVehiculo());
            TextField modeloVehiculoField = new TextField(selectedPiece.getModeloVehiculo());

            // Agregar los campos de texto al diálogo
            GridPane grid = new GridPane();
            grid.add(new Label("ID:"), 0, 0);
            grid.add(idField, 1, 0);
            grid.add(new Label("Nombre:"), 0, 1);
            grid.add(nameField, 1, 1);
            grid.add(new Label("Número de Serie:"), 0, 2);
            grid.add(numeroSerieField, 1, 2);
            grid.add(new Label("Fabricante:"), 0, 3);
            grid.add(fabricanteField, 1, 3);
            grid.add(new Label("Descripción:"), 0, 4);
            grid.add(descripcionField, 1, 4);
            grid.add(new Label("Marca del Vehículo:"), 0, 5);
            grid.add(marcaVehiculoField, 1, 5);
            grid.add(new Label("Modelo del Vehículo:"), 0, 6);
            grid.add(modeloVehiculoField, 1, 6);
            dialog.getDialogPane().setContent(grid);

            // Convertir los resultados del diálogo a una nueva instancia de Pieza cuando el botón Modificar se presiona
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == modifyButton) {
                    return new Pieza(idField.getText(), nameField.getText(), numeroSerieField.getText(), fabricanteField.getText(), descripcionField.getText(), marcaVehiculoField.getText(), modeloVehiculoField.getText());
                }
                return null;
            });

            // Mostrar el diálogo y esperar a que el usuario cierre el diálogo
            Optional<Pieza> result = dialog.showAndWait();

            // Modificar la pieza en la base de datos y actualizar la tabla si el usuario presionó el botón Modificar
            result.ifPresent(pieza -> {
                dbManager.modifyPiece(idField.getText(), pieza);
                showPieces();
            });
        }
    }

    private void eliminarPieza() {
        // Obtener la pieza seleccionada en la tabla
        Pieza selectedPiece = adminPiecesTableView.getSelectionModel().getSelectedItem();

        if (selectedPiece != null) {
            // Crear un nuevo diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar pieza");
            alert.setContentText("¿Estás seguro de que quieres eliminar la pieza seleccionada?");

            // Mostrar el diálogo y esperar a que el usuario cierre el diálogo
            Optional<ButtonType> result = alert.showAndWait();

            // Eliminar la pieza de la base de datos y actualizar la tabla si el usuario presionó el botón OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dbManager.removePiece(selectedPiece.get_id());
                showPieces();
            }
        }
    }


    private void showPieces() {
        List<Pieza> pieces = dbManager.getAllPieces();
        adminPiecesTableView.getItems().setAll(pieces);
    }

    private void goToAppView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("app-view.fxml")));
            Stage stage = (Stage) buttonGoToAppView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
