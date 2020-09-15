package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.Calendar;

public class ReportView extends Dialog<Calendar> {
    private final DatePicker datePicker;

    public ReportView() {
        super();
        setTitle("Report Positive");
        datePicker = new DatePicker();
        init();
    }

    public void init() {
        HBox hBox = new HBox();
        Label label = new Label("Date tested positive: \n(DD/MM/YYY)");
        label.setTranslateY(3);
        label.setFont(new Font(13));
        hBox.getChildren().addAll(label, datePicker);
        hBox.setSpacing(3);
        getDialogPane().setContent(hBox);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, e -> {
            LocalDate date = datePicker.getValue();
            Calendar.Builder builder = new Calendar.Builder();
            builder.setLenient(false);
            try {
                builder.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
            } catch (Exception exception) {
                e.consume();
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                LocalDate date = datePicker.getValue();
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                return calendar;
            }
            return null;
        });
    }
}