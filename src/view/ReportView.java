package view;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
        Label label = new Label("Date tested positive: ");
        label.setTranslateY(3);
        label.setFont(new Font(13));
        hBox.getChildren().addAll(label, datePicker);
        hBox.setSpacing(3);
        getDialogPane().setContent(hBox);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

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