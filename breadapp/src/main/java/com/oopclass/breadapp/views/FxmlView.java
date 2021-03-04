package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    CUSTOMER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("customer.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/customer.fxml";
        }
    }, TRANSACTION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("transaction.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/transaction.fxml";
        }
    }, CANCELLATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cancellation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/cancellation.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
