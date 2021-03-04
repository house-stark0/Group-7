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
import com.oopclass.breadapp.models.Transaction;
import com.oopclass.breadapp.services.impl.TransactionService;
import com.oopclass.breadapp.views.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
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
public class TransactionController implements Initializable {

    @FXML
    private Label transactionId;
  
    @FXML
    private DatePicker doa;
    
    @FXML
    private TableView<Transaction> apTable;

    @FXML
    private TableColumn<Transaction, Long> colapId;
    
    @FXML
    private TableColumn<Transaction, LocalDate> coldoa;
    
    @FXML
    private TableColumn<Transaction, Boolean> colEdit;
    
    @FXML
    private TableColumn<Transaction, Boolean> colCreatedAt;
    
    @FXML
    private TableColumn<Transaction, Boolean> colUpdatedAt;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private TransactionService transacationService;

    private ObservableList<Transaction> transacationList = FXCollections.observableArrayList();

    @FXML
    private Button UserPanel;
    @FXML
    private Button CancelPanel;
    @FXML
    private Button addtransactions;
    @FXML
    private Button deletetransactions;
    @FXML
    private Button reset;
    @FXML
    private TextField apFullName;
    @FXML
    private TextField apAddress;
    @FXML
    private TextField apContactNo;
    @FXML
    private TextField apRevision;
    @FXML
    private RadioButton rbAccepted;
    @FXML
    private RadioButton rbDeclined;
    @FXML
    private TableColumn<Transaction, String> colFullname;
    @FXML
    private TableColumn<Transaction, String> colAddress;
    @FXML
    private TableColumn<Transaction, String> colContactNo;
    @FXML
    private TableColumn<Transaction, String> colRevision;
    @FXML
    private TableColumn<Transaction, String> colStatus;




//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void  CancelPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.CANCELLATION );
    }
    
    @FXML
    private void  UserPanel(ActionEvent event){
            stageManager.switchScene(FxmlView.CUSTOMER);
    }
    @FXML
    private void addtransactions(ActionEvent event) {

        if  ( validate("Customer Full Name", getApFullName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Customer Revision", getApRevision(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Contact No", getApContactNo(), "(^(09|\\+639)\\d{9}$)+")
                && validate("Address", getApAddress(), "([a-zA-Z]{3,30}\\s*)+")) {
 

            if (transactionId.getText() == null || "".equals(transactionId.getText())) {
                if (true) {
                    Transaction transacation = new Transaction();
                    
                    transacation.setApFullName(getApFullName());
                    transacation.setApRevision(getApRevision());
                    transacation.setApContactNo(getApContactNo());
                    transacation.setApAddress(getApAddress());
                    transacation.setDoa(getDoa());
                    transacation.setCreatedAt();
                    transacation.setUpdatedAt();
                    transacation.setStatus(getStatus());
                    
                    Transaction newTransaction = transacationService.save(transacation);
                    saveAlert(newTransaction);
                }

            } else {
                Transaction transacation = transacationService.find(Long.parseLong(transactionId.getText()));
                
                transacation.setApFullName(getApFullName());
                    transacation.setApRevision(getApRevision());
                    transacation.setApContactNo(getApContactNo());
                    transacation.setApAddress(getApAddress());
                    transacation.setDoa(getDoa());
                    transacation.setUpdatedAt();
                    transacation.setStatus(getStatus());
                    
                Transaction updatedTransaction = transacationService.update(transacation);
                updateAlert(updatedTransaction);
            }

            clearFields();
            loadTransactionDetails();
        }

    }

    @FXML
   private void deletetransactions(ActionEvent event) {
        List<Transaction> transacations = apTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            transacationService.deleteInBatch(transacations);
        }

        loadTransactionDetails();
    }

    private void clearFields() {
        transactionId.setText(null);
        
        apFullName.clear();
        apRevision.clear();
        apContactNo.clear();
        apAddress.clear();
        doa.getEditor().clear();
        rbAccepted.setSelected(true);
        rbDeclined.setSelected(false);
    }
        

    private void saveAlert(Transaction transacation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Transaction saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The transacation of" + transacation.getApFullName() + " " + transacation.getApContactNo() + " " + transacation.getApAddress() + " " + transacation.getApRevision() + " " + getStatusTitle(transacation.getStatus()) + "has been created and \n" + " id is " + transacation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Transaction transacation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Transaction updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The transacation "  + transacation.getApFullName() + " " + transacation.getApContactNo() + " " + transacation.getApAddress() + " " + transacation.getApRevision() + " " + getStatusTitle(transacation.getStatus()) + " has been updated.");
        alert.showAndWait();
    }

private String getStatusTitle(String Status) {
        return (Status.equals("Accepted")) ? "Accepted" : "Declined";
    }
    
    public String getApFullName() {
        return apFullName.getText();
    }
    public String getApRevision() {
        return apRevision.getText();
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
    public String getStatus() {
        return rbAccepted.isSelected() ? "Accepted" : "Declined";
    }

    /*
	 *  Set All transacationTable column properties
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
        colRevision.setCellValueFactory(new PropertyValueFactory<>("apRevision"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("apAddress"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("apContactNo"));
        colFullname.setCellValueFactory(new PropertyValueFactory<>("apFullName"));
        coldoa.setCellValueFactory(new PropertyValueFactory<>("doa"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<Transaction, Boolean>, TableCell<Transaction, Boolean>> cellFactory
            = new Callback<TableColumn<Transaction, Boolean>, TableCell<Transaction, Boolean>>() {
        @Override
        public TableCell<Transaction, Boolean> call(final TableColumn<Transaction, Boolean> param) {
            final TableCell<Transaction, Boolean> cell = new TableCell<Transaction, Boolean>() {
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
                            Transaction transacation = getTableView().getItems().get(getIndex());
                            updateTransaction(transacation);
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

                private void updateTransaction(Transaction transacation) {
                    transactionId.setText(Long.toString(transacation.getId()));
                    apRevision.setText(transacation.getApRevision());
                    apAddress.setText(transacation.getApAddress());
                    apContactNo.setText(transacation.getApContactNo());
                    apFullName.setText(transacation.getApFullName());
                    doa.setValue(transacation.getDoa());
                    if (transacation.getStatus().equals("Accepted")) {
                        rbAccepted.setSelected(true);
                    } else {
                        rbDeclined.setSelected(true);
                    }
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All transacations to observable list and update table
     */
    private void loadTransactionDetails() {
        transacationList.clear();
        transacationList.addAll(transacationService.findAll());

        apTable.setItems(transacationList);
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

        // Add all transacations into table
        loadTransactionDetails();
    }

}

