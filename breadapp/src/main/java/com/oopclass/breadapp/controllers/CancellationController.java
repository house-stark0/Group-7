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
import com.oopclass.breadapp.models.Cancellation;
import com.oopclass.breadapp.services.impl.CancellationService;
import com.oopclass.breadapp.views.FxmlView;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;


@Controller
public class CancellationController implements Initializable {

    @FXML
    private Label cancellationId;
    
    @FXML
    private TextField cancellationFullName;
    
    @FXML
    private TextField cancellationProductId;
    
    @FXML
    private TextField cancellationReason;
    
    @FXML
    private RadioButton rbYes;
    
    @FXML
    private RadioButton rbNo;
    
    @FXML
    private Button addCancellation;
    
    @FXML
    private Button UserPanel;
    @FXML
    private Button AppointmentPanel;

    @FXML
    private Button reset;

    @FXML
    private Button deleteCancellation;

    @FXML
    private TableView<Cancellation> dataTable;

    @FXML
    private TableColumn<Cancellation, Long> colCancellationId;
    @FXML
    private TableColumn<Cancellation, String> colCancellationFullName;
    @FXML
    private TableColumn<Cancellation, String> colCancellationProductId;
    @FXML
    private TableColumn<Cancellation, String> colUrder;
    @FXML
    private TableColumn<Cancellation, String> colReason;
    @FXML
    private TableColumn<Cancellation, Boolean> colEdit;
    @FXML
    private TableColumn<Cancellation, Boolean> colCreatedAt; 
    @FXML
    private TableColumn<Cancellation, Boolean> colUpdatedAt;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CancellationService cancellationService;

    private ObservableList<Cancellation> cancellationList = FXCollections.observableArrayList();


    
//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    private void  AppointmentPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.TRANSACTION);
    }
    
    @FXML
    private void  UserPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.CUSTOMER);
    }
    
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    

    @FXML
    void addCancellation(ActionEvent event) {

        if (validate("Cancellation First Name", getCancellationFullName(), "([a-zA-Z]{3,30}\\s*)+")
            && validate("Cancellation Product Id", getCancellationProductId(), "^[0-9]*$")
            && validate("Cancellation Reason", getCancellationReason(), "([a-zA-Z]{3,30}\\s*)+")) {
            
            if (cancellationId.getText() == null || "".equals(cancellationId.getText())) {
                if (true) {
                    Cancellation cancellation = new Cancellation();
                    cancellation.setCancellationFullName(getCancellationFullName());
                    cancellation.setCancellationProductId(getCancellationProductId());
                    cancellation.setCancellationReason(getCancellationReason());
                    cancellation.setUrder(getUrder());
                    cancellation.setCreatedAt();
                    cancellation.setUpdatedAt();
                    
                    Cancellation newCancellation = cancellationService.save(cancellation);
                    saveAlert(newCancellation);
                }

            } else {
                Cancellation cancellation = cancellationService.find(Long.parseLong(cancellationId.getText()));
                cancellation.setCancellationFullName(getCancellationFullName());
                cancellation.setCancellationProductId(getCancellationProductId());
                cancellation.setCancellationReason(getCancellationReason());
                cancellation.setUrder(getUrder());
                cancellation.setUpdatedAt();
                    
                Cancellation updatedCancellation = cancellationService.update(cancellation);
                updateAlert(updatedCancellation);
            }

            clearFields();
            loadCancellationDetails();
        }

    }

    @FXML
   private void deleteCancellation(ActionEvent event) {
        List<Cancellation> cancellations = dataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            cancellationService.deleteInBatch(cancellations);
        }

        loadCancellationDetails();
    }

    private void clearFields() {
        cancellationId.setText(null);
        cancellationFullName.clear();
        cancellationProductId.clear();
        cancellationReason.clear();
        rbYes.setSelected(true);
        rbNo.setSelected(false);
       
    }
        

    private void saveAlert(Cancellation cancellation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cancellation saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancellation " + cancellation.getCancellationFullName() + " of " + cancellation.getCancellationProductId()+ " with the reason of "+ cancellation.getCancellationReason()+ " has been created and \n" + getUrderTitle(cancellation.getUrder()) + " id is " + cancellation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Cancellation cancellation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cancellation updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancellation " + cancellation.getCancellationFullName() + " of " + cancellation.getCancellationProductId()+ " with the reason of "+ cancellation.getCancellationReason()+ getUrderTitle(cancellation.getUrder()) + " has been updated.");
        alert.showAndWait();
    }
private String getUrderTitle(String Urder) {
        return (Urder.equals("Yes")) ? "Yes" : "No";
    }
     public String getUrder() {
        return rbYes.isSelected() ? "Yes" : "No";
    }

     
    public String getCancellationFullName() {
        return cancellationFullName.getText();
    }
    
    public String getCancellationProductId() {
        return cancellationProductId.getText();
    }
    
    public String getCancellationReason() {
        return cancellationReason.getText();
    }
    
      


    /*
	 *  Set All cancellationTable column properties
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

        colCancellationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCancellationFullName.setCellValueFactory(new PropertyValueFactory<>("cancellationFullName"));
        colCancellationProductId.setCellValueFactory(new PropertyValueFactory<>("cancellationProductId"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("cancellationReason"));
        colUrder.setCellValueFactory(new PropertyValueFactory<>("urder"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<Cancellation, Boolean>, TableCell<Cancellation, Boolean>> cellFactory
            = new Callback<TableColumn<Cancellation, Boolean>, TableCell<Cancellation, Boolean>>() {
        @Override
        public TableCell<Cancellation, Boolean> call(final TableColumn<Cancellation, Boolean> param) {
            final TableCell<Cancellation, Boolean> cell = new TableCell<Cancellation, Boolean>() {
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
                            Cancellation cancellation = getTableView().getItems().get(getIndex());
                            updateCancellation(cancellation);
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

                private void updateCancellation(Cancellation cancellation) {
                    cancellationId.setText(Long.toString(cancellation.getId()));
                    cancellationFullName.setText(cancellation.getCancellationFullName());
                    cancellationProductId.setText(cancellation.getCancellationProductId());
                   cancellationReason.setText(cancellation.getCancellationReason());
                   if (cancellation.getUrder().equals("Yes")) {
                        rbYes.setSelected(true);
                    } else {
                        rbNo.setSelected(true);
                    }
                }
                
                
               
            };
            return cell;
        }
    };

    /*
	 *  Add All cancellations to observable list and update table
     */
    private void loadCancellationDetails() {
        cancellationList.clear();
        cancellationList.addAll(cancellationService.findAll());

        dataTable.setItems(cancellationList);
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

        // Add all cancellations into table
        loadCancellationDetails();
    }
}

