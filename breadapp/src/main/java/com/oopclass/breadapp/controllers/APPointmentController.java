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
import com.oopclass.breadapp.models.APPointment;
import com.oopclass.breadapp.services.impl.APPointmentService;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class APPointmentController implements Initializable {

    @FXML
    private Label AppointmentId;

    @FXML
    private TextField apFullName;

    @FXML
    private TextField apContactNo;

    @FXML
    private TextField apAddress;

    @FXML
    private DatePicker doa;

    @FXML
    private Button addAppointments;

    @FXML
    private Button deleteAppointments;

    

    @FXML
    private TableView<APPointment> apTable;

    @FXML
    private TableColumn<APPointment, Long> colapId;

    @FXML
    private TableColumn<APPointment, String> colapfullname;
    
    @FXML
    private TableColumn<APPointment, String> colcontactno;
    
    @FXML
    private TableColumn<APPointment, String> colapaddress;
    
    @FXML
    private TableColumn<APPointment, LocalDate> coldoa;
    
    @FXML
    private TableColumn<APPointment, Boolean> colEdit;
    private TableColumn<APPointment, Boolean> colCreatedAt;
    
    private TableColumn<APPointment, Boolean> colUpdatedAt;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private APPointmentService AppointmentService;

    private ObservableList<APPointment> AppointmentList = FXCollections.observableArrayList();

    @FXML
    private Button UserPanel;
    @FXML
    private Button CancelPanel;


//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void  CancelPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.CANCELATION );
    }
    
    @FXML
    private void  UserPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.USER);
    }
    @FXML
    private void addAppointments(ActionEvent event) {

//        if (validate("mtfull Name", getFullName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (AppointmentId.getText() == null || "".equals(AppointmentId.getText())) {
                if (true) {
                    APPointment Appointment = new APPointment();
                    Appointment.setApFullName(getFullName());
                    Appointment.setApContactNo(getApContactNo());
                    Appointment.setApAddress(getApAddress());
                    Appointment.setDoa(getDoa());
                    APPointment newAPpointment = AppointmentService.save(Appointment);
                    saveAlert(newAPpointment);
                }

            } else {
                APPointment Appointment = AppointmentService.find(Long.parseLong(AppointmentId.getText()));
                Appointment.setApFullName(getFullName());
                Appointment.setApContactNo(getApContactNo());
                Appointment.setApAddress(getApAddress());
                Appointment.setDoa(getDoa());
                APPointment updatedAPpointment = AppointmentService.update(Appointment);
                updateAlert(updatedAPpointment);
            }

            clearFields();
            loadTarpDetails();
//        }

    }

    @FXML
   private void deleteAppointments(ActionEvent event) {
        List<APPointment> Appointments = apTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            AppointmentService.deleteInBatch(Appointments);
        }

        loadTarpDetails();
    }

    private void clearFields() {
        AppointmentId.setText(null);
        apFullName.clear();
        apContactNo.clear();
        apAddress.clear();
        doa.getEditor().clear();
    }
        

    private void saveAlert(APPointment Appointment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tarp saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Appointment " + Appointment.getApFullName() + " has been created with ID: " + Appointment.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(APPointment Appointment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tarp updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Appointment " + Appointment.getApFullName() + " has been updated.");
        alert.showAndWait();
    }


    public String getFullName() {
        return apFullName.getText();
    }
    
    public String getApContactNo() {
        return apContactNo.getText();
    }
    
      public String getApAddress() {
        return apAddress.getText();
    }
     
     public  LocalDate getDoa() {
        return doa.getValue();
     }
    

    /*
	 *  Set All AppointmentTable column properties
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

        colapId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colapfullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colcontactno.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colapaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldoa.setCellValueFactory(new PropertyValueFactory<>("doa"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<APPointment, Boolean>, TableCell<APPointment, Boolean>> cellFactory
            = new Callback<TableColumn<APPointment, Boolean>, TableCell<APPointment, Boolean>>() {
        @Override
        public TableCell<APPointment, Boolean> call(final TableColumn<APPointment, Boolean> param) {
            final TableCell<APPointment, Boolean> cell = new TableCell<APPointment, Boolean>() {
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
                            APPointment Appointment = getTableView().getItems().get(getIndex());
                            updateTarp(Appointment);
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

                private void updateTarp(APPointment Appointment) {
                    AppointmentId.setText(Long.toString(Appointment.getId()));
                    apFullName.setText(Appointment.getApFullName());
                    apContactNo.setText(Appointment.getApContactNo());
                    apAddress.setText(Appointment.getApAddress());
                    doa.setValue(Appointment.getDoa());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All Appointments to observable list and update table
     */
    private void loadTarpDetails() {
        AppointmentList.clear();
        AppointmentList.addAll(AppointmentService.findAll());

        apTable.setItems(AppointmentList);
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

        apTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all Appointments into table
        loadTarpDetails();
    }

}

