package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    }, APPOINTMENT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("appointment.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/appointment.fxml";
        }
    }, CANCELATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cancelation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/cancelation.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
