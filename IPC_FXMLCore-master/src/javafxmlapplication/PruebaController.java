package javafxmlapplication;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
public class PruebaController {
    
    @FXML
    private Text name;

    @FXML
    private Text price;

    void setItemText(String text)
    {
        this.name.setText(text);
    }
}

