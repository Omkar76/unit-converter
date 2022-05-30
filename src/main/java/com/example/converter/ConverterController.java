package com.example.converter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

public class ConverterController {
    @FXML
    protected VBox vBox;
    @FXML
    protected ComboBox<QuantityDTO> quantityTypeCheckBox;

    @FXML
    protected ComboBox<QuantityDTO.Unit> unitAComboBox;

    @FXML
    protected ComboBox<QuantityDTO.Unit> unitBComboBox;

    @FXML
    protected TextField textFieldA;

    @FXML
    protected TextField textFieldB;

    private static final DecimalFormat formatter = new DecimalFormat("0.################");

    @FXML
    public void initialize() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader("src/main/resources/com/example/converter/conversion.json"));
        Gson gson = new Gson();
        QuantityDTO[] quantities = gson.fromJson(jsonReader, QuantityDTO[].class);        // Select first quantity type. Default selection is empty
        quantityTypeCheckBox.setStyle("-fx-font-size: 15");
        quantityTypeCheckBox.getItems().addAll(quantities);
        quantityTypeCheckBox.getSelectionModel().select(0);
        quantityTypeCheckBox.fireEvent(new ActionEvent());
    }

    public void onQuantitySelected() {
        QuantityDTO currentQuantityType = quantityTypeCheckBox.getSelectionModel().getSelectedItem();

        Set<Map.Entry<String, String>> units = currentQuantityType.units.entrySet();

        unitAComboBox.getItems().clear();
        for (Map.Entry<String, String> u:
             units) {
            QuantityDTO.Unit unit = new QuantityDTO.Unit(u);
            unitAComboBox.getItems().add(unit);
        }

        unitAComboBox.getSelectionModel().select(0);

        unitBComboBox.getItems().clear();
        for (Map.Entry<String, String> u:
                units) {
            QuantityDTO.Unit unit = new QuantityDTO.Unit(u);
            unitBComboBox.getItems().add(unit);
        }

        unitBComboBox.getSelectionModel().select(0);
    }

    public void updateTextFieldB() {
        if(textFieldA.getText().trim().isEmpty()) return;

        QuantityDTO.Unit srcUnit = unitAComboBox.getSelectionModel().getSelectedItem();
        QuantityDTO.Unit destUnit = unitBComboBox.getSelectionModel().getSelectedItem();

        if(srcUnit == null || destUnit==null)return;
        if(srcUnit.name.equals(destUnit.name)){
            textFieldB.setText(textFieldA.getText());
            return;
        }
        BigDecimal value = new BigDecimal(textFieldA.getText());

        String result = formatter.format(value.divide(srcUnit.conversionFactor, 32, RoundingMode.HALF_UP)
                .multiply(destUnit.conversionFactor));


        textFieldB.setText(result);
    }
    public void updateTextFieldA() {
        if(textFieldB.getText().trim().isEmpty()) return;

        QuantityDTO.Unit srcUnit = unitBComboBox.getSelectionModel().getSelectedItem();
        QuantityDTO.Unit destUnit = unitAComboBox.getSelectionModel().getSelectedItem();

        if(srcUnit.name.equals(destUnit.name)){
            textFieldA.setText(textFieldB.getText());
            return;
        }
        BigDecimal value = new BigDecimal(textFieldB.getText());

        String result = formatter.format(value.divide(srcUnit.conversionFactor, 32, RoundingMode.UP)
                .multiply(destUnit.conversionFactor));


        textFieldA.setText(result);
    }
}