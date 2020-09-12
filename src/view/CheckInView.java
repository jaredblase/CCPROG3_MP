package view;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Calendar;

public class CheckInView extends Dialog<Pair<String, Calendar>> {
    private final TextField estCodeTextField;
    private final DatePicker datePicker;
    private final Label textFieldLabel;
    private final Label datePickerLabel;

    public CheckInView() {
        super();
        setTitle("Check In");
        estCodeTextField = new TextField();
        datePicker = new DatePicker();
        textFieldLabel = new Label("Establishment code: ");
        datePickerLabel = new Label("Date check in: ");
        init();
    }

    public void init() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(textFieldLabel, 0, 0);
        gridPane.add(estCodeTextField, 1, 0);
        gridPane.add(datePickerLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);

        textFieldLabel.setTranslateY(2);
        textFieldLabel.setFont(new Font(13));
        datePickerLabel.setTranslateY(3);
        datePickerLabel.setFont(new Font(13));

        getDialogPane().setContent(gridPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                LocalDate date = datePicker.getValue();
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                return new Pair<>(estCodeTextField.getText(), calendar);
            }
            return null;
        });
    }
}
