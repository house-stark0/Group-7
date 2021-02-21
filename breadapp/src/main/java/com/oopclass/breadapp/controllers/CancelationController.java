package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.Cancelation;
import com.oopclass.breadapp.models.Cancelation;
import com.oopclass.breadapp.services.impl.CancelationService;
import com.oopclass.breadapp.views.FxmlView;
import javafx.application.Platform;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


@Controller
public class CancelationController implements Initializable {

    @FXML
    private Label cancelationId;
    
    @FXML
    private TextField customerName;

    @FXML
    private TextField description;
    
    @FXML
    private Button addCancelation;
    

    @FXML
    private Button deleteCancelation;

    @FXML
    private TableView<Cancelation> dataTable;

    @FXML
    private TableColumn<Cancelation, Long> colID;

    @FXML
    private TableColumn<Cancelation, String> colCustomer;

    @FXML
    private TableColumn<Cancelation, String> colDescription;

    @FXML
    private TableColumn<Cancelation, Boolean> colEdit;
    private TableColumn<Cancelation, Boolean> colCreatedAt; 
    private TableColumn<Cancelation, Boolean> colUpdatedAt;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CancelationService cancelationService;

    private ObservableList<Cancelation> cancelationList = FXCollections.observableArrayList();

    @FXML
    private Button UserPanel;
    @FXML
    private Button AppointmentPanel;

    @FXML
    private Button reset;

    
//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    private void  AppointmentPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.APPOINTMENT);
    }
    
    @FXML
    private void  UserPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.USER);
    }
    
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    

    @FXML
    void addCancelation(ActionEvent event) {

        if (validate("Customer First Name", getCustomerName(), "([a-zA-Z]{3,30}\\s*)+")
            && validate("Customer Description", getDescription(), "([a-zA-Z]{3,30}\\s*)+")) {
            if (cancelationId.getText() == null || "".equals(cancelationId.getText())) {
                if (true) {
                    Cancelation cancelation = new Cancelation();
                    cancelation.setCustomerName(getCustomerName());
                    Cancelation newCancelation = cancelationService.save(cancelation);
                    saveAlert(newCancelation);
                }

            } else {
                Cancelation cancelation = cancelationService.find(Long.parseLong(cancelationId.getText()));
                cancelation.setCustomerName(getCustomerName());
                cancelation.setDescription(getDescription());
                Cancelation updatedCancelation = cancelationService.update(cancelation);
                updateAlert(updatedCancelation);
            }

            clearFields();
            loadTarpsDetails();
        }

    }

    @FXML
   private void deleteCancelation(ActionEvent event) {
        List<Cancelation> cancelations = dataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            cancelationService.deleteInBatch(cancelations);
        }

        loadTarpsDetails();
    }

    private void clearFields() {
        cancelationId.setText(null);
        customerName.clear();
        description.clear();
       
    }
        

    private void saveAlert(Cancelation cancelation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tarps saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancelation " + cancelation.getCustomerName() + " has been created with ID: " + cancelation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Cancelation cancelation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tarps updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancelation " + cancelation.getCustomerName() + " has been updated.");
        alert.showAndWait();
    }

    
    public String getCustomerName() {
        return customerName.getText();
    }
    
    public String getDescription() {
        return description.getText();
    }
    
      


    /*
	 *  Set All cancelationTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<Cancelation, Boolean>, TableCell<Cancelation, Boolean>> cellFactory
            = new Callback<TableColumn<Cancelation, Boolean>, TableCell<Cancelation, Boolean>>() {
        @Override
        public TableCell<Cancelation, Boolean> call(final TableColumn<Cancelation, Boolean> param) {
            final TableCell<Cancelation, Boolean> cell = new TableCell<Cancelation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Cancelation cancelation = getTableView().getItems().get(getIndex());
                            updateTarps(cancelation);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateTarps(Cancelation cancelation) {
                    cancelationId.setText(Long.toString(cancelation.getId()));
                    customerName.setText(cancelation.getCustomerName());
                   description.setText(cancelation.getDescription());
                }
                
               
            };
            return cell;
        }
    };

    /*
	 *  Add All cancelations to observable list and update table
     */
    private void loadTarpsDetails() {
        cancelationList.clear();
        cancelationList.addAll(cancelationService.findAll());

        dataTable.setItems(cancelationList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all cancelations into table
        loadTarpsDetails();
    }
}

