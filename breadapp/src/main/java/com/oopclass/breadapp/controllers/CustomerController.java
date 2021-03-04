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
import com.oopclass.breadapp.models.Customer;
import com.oopclass.breadapp.services.impl.CustomerService;
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
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class CustomerController implements Initializable {


    @FXML
    private TextField customerFirstName;

    @FXML
    private TextField customerLastName;

    @FXML
    private TextField contactNo;

    @FXML
    private TextField address;


    @FXML
    private TableView<Customer> customerTable;


    @FXML
    private TableColumn<Customer, String> colCustomerFirstName;

    @FXML
    private TableColumn<Customer, String> colCustomerLastName;

    @FXML
    private TableColumn<Customer, String> colContactNo;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, Boolean> colEdit;
    
    @FXML
    private TableColumn<Customer, Boolean> colCreatedAt;
    
    @FXML
    private TableColumn<Customer, Boolean> colUpdatedAt;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CustomerService customerService;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    @FXML
    private Button AppointmentPanel;
    @FXML
    private Button CancelPanel;
    @FXML
    private Button saveCustomer;
    @FXML
    private Button reset;
    @FXML
    private Button deleteCustomer;
    @FXML
    private Label customerId;
    @FXML
    private TableColumn<Customer, Long> colCustomerId;


//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    

    @FXML
    private void saveCustomer(ActionEvent event) {

        if (validate("Customer First Name", getCustomerFirstName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Customer Last Name", getCustomerLastName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Contact No", getContactNo(), "(^(09|\\+639)\\d{9}$)+")
                && validate("Address", getAddress(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (customerId.getText() == null || "".equals(customerId.getText())) {
                if (true) {

                    Customer customer = new Customer();
                    customer.setCustomerFirstName(getCustomerFirstName());
                    customer.setCustomerLastName(getCustomerLastName());
                    customer.setContactNo(getContactNo());
                    customer.setAddress(getAddress());
                    customer.setCreatedAt();
                    customer.setUpdatedAt();

                    Customer newCustomer = customerService.save(customer);

                    saveAlert(newCustomer);
                }

            } else {
                Customer customer = customerService.find(Long.parseLong(customerId.getText()));
                customer.setCustomerFirstName(getCustomerFirstName());
                customer.setCustomerLastName(getCustomerLastName());
                customer.setContactNo(getContactNo());
                customer.setAddress(getAddress());
                customer.setUpdatedAt();
                Customer updatedCustomer = customerService.update(customer);
                updateAlert(updatedCustomer);
            }

            clearFields();
            loadCustomerDetails();
        }

    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        List<Customer> customers = customerTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            customerService.deleteInBatch(customers);
        }

        loadCustomerDetails();
    }

    private void clearFields() {
        customerId.setText(null);
        customerFirstName.clear();
        customerLastName.clear();
        contactNo.clear();
        address.clear();
    }

    private void saveAlert(Customer customer) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The customer " + customer.getCustomerFirstName() + " " + customer.getCustomerLastName() + " " + customer.getContactNo() + " " + customer.getAddress() + " has been created and \n" + " id is " + customer.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Customer customer) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The customer " + customer.getCustomerFirstName() + " " + customer.getCustomerLastName() + " " + customer.getContactNo() + " " + customer.getAddress() + " has been updated.");
        alert.showAndWait();
    }

    public String getCustomerFirstName() {
        return customerFirstName.getText();
    }

    public String getCustomerLastName() {
        return customerLastName.getText();
    }

    public String getContactNo() {
        return contactNo.getText();
    }

    public String getAddress() {
        return address.getText();
    }


    /*
	 *  Set All customerTable column properties
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

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerFirstName.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
        colCustomerLastName.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> cellFactory
            = new Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>>() {
        @Override
        public TableCell<Customer, Boolean> call(final TableColumn<Customer, Boolean> param) {
            final TableCell<Customer, Boolean> cell = new TableCell<Customer, Boolean>() {
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
                            Customer customer = getTableView().getItems().get(getIndex());
                            updateCustomer(customer);
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

                private void updateCustomer(Customer customer) {
                    customerId.setText(Long.toString(customer.getId()));
                    customerFirstName.setText(customer.getCustomerFirstName());
                    customerLastName.setText(customer.getCustomerLastName());
                    contactNo.setText(customer.getContactNo());
                    address.setText(customer.getAddress());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All customers to observable list and update table
     */
    private void loadCustomerDetails() {
        customerList.clear();
        customerList.addAll(customerService.findAll());

        customerTable.setItems(customerList);
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

        customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all customers into table
        loadCustomerDetails();
    }

    @FXML
    private void CancelPanel(ActionEvent event) 
    {
        stageManager.switchScene(FxmlView.CANCELLATION);
    }

    @FXML
    private void AppointmentPanel(ActionEvent event){
        stageManager.switchScene(FxmlView.TRANSACTION);
    }

    

}