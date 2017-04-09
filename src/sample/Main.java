package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.commons.lang3.ArrayUtils;

import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.StringJoiner;

import static sample.MathUtils.evaluate;


public class Main extends Application {

    public static final int DELETE_KEY = 127;
    public static final int ESC_KEY = 27;
    public static final int ENTER_KEY = 13;
    public static final String INVERSE = "1/x";
    public static final String SIN = "Sin";
    public static final String COS = "Cos";
    public static final String TAN = "Tan";
    public static final String SQRT = "sqrt";
    public static final String POWER_OF_2 = "^2";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calculator");
        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(5); //horizontal gap in pixels => that's what you are asking for
        gridPane.setVgap(5); //vertical gap in pixels
        gridPane.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        Button button0 = new Button("0");
        Button button1 = new Button("1");
        Button button2 = new Button("2");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");
        Button button6 = new Button("6");
        Button button7 = new Button("7");
        Button button8 = new Button("8");
        Button button9 = new Button("9");
        Button buttonDot = new Button(".");
        Button buttonMinus = new Button("-");
        Button buttonPlus = new Button("+");
        Button buttonDivision = new Button("/");
        Button buttonMultiplication = new Button("*");
        Button buttonEqual = new Button("=");
        Button buttonFraction = new Button(INVERSE);
        Button buttonPercent = new Button("%");
        Button buttonRadical = new Button(SQRT);
        Button buttonChangePlusOrMinus = new Button("+/-");
        Button buttonClear = new Button("C");
        Button buttonBackspace = new Button("<-");
        Button buttonSin = new Button(SIN);
        Button buttonCos = new Button(COS);
        Button buttonTan = new Button(TAN);
        Button buttonOpenParentheses = new Button("(");
        Button buttonCloseParentheses = new Button(")");
        Button buttonPower = new Button(POWER_OF_2);

        Font textFont = Font.font("Verdana", FontWeight.BOLD, 18);
        textField.setPrefHeight(48);
        textField.setFont(textFont);
        textField.setAlignment(Pos.CENTER_RIGHT);
//        textField.setStyle("-fx-display-caret: false;");
        GridPane.setConstraints(textField,0,0,5,1);

        GridPane.setConstraints(buttonBackspace,0,1);
        GridPane.setConstraints(buttonClear,1,1);
        GridPane.setConstraints(buttonPercent,2,1);
        GridPane.setConstraints(buttonChangePlusOrMinus,3,1);
        GridPane.setConstraints(buttonMultiplication,4,1);

        GridPane.setConstraints(button1,0,2);
        GridPane.setConstraints(button2,1,2);
        GridPane.setConstraints(button3,2,2);
        GridPane.setConstraints(button4,3,2);
        GridPane.setConstraints(buttonDivision,4,2);

        GridPane.setConstraints(button5,0,3);
        GridPane.setConstraints(button6,1,3);
        GridPane.setConstraints(button7,2,3);
        GridPane.setConstraints(button8,3,3);
        GridPane.setConstraints(buttonMinus,4,3);

        GridPane.setConstraints(button0,0,4,2,1);
        GridPane.setConstraints(button9,2,4);
        GridPane.setConstraints(buttonDot,3,4);
        GridPane.setConstraints(buttonPlus,4,4);

        GridPane.setConstraints(buttonOpenParentheses,0,5);
        GridPane.setConstraints(buttonCloseParentheses,1,5);
        GridPane.setConstraints(buttonRadical,2,5);
        GridPane.setConstraints(buttonFraction,3,5);
        GridPane.setConstraints(buttonEqual,4,5,1,2);

        GridPane.setConstraints(buttonSin,0,6);
        GridPane.setConstraints(buttonCos,1,6);
        GridPane.setConstraints(buttonTan,2,6);
        GridPane.setConstraints(buttonPower,3,6);


//        GridPane.setFillWidth(button0, true);
        Button[] buttons = new Button[]{buttonBackspace,buttonClear,
                buttonPercent,buttonChangePlusOrMinus,buttonMultiplication,button1,
                button2,button3,button4,button5,button6,button7,
                button8,button9,button0,buttonDivision,buttonMinus
                ,buttonDot,buttonPlus,buttonOpenParentheses,
                buttonCloseParentheses,buttonRadical,buttonFraction,buttonEqual,
                buttonSin,buttonCos,buttonTan,buttonPower};
        List<Button> calculateButtons = Arrays.asList(
                buttonRadical,buttonFraction,buttonPower,buttonSin,buttonCos,buttonTan
        );
        int buttonLength = buttons.length;
        String[] buttonsValues = new String[buttonLength];
        for (int index = 0; index < buttonLength; index++)
        {
            Button button = buttons[index];
            buttonsValues[index] = button.getText();
            button.setMinWidth(button==button0 ? 100 : 50);
            button.setPadding(new Insets(5, 5, 5, 5));
        }
        buttonBackspace.setOnAction((ActionEvent e) ->{
            String text = textField.getText();
            if((text!=null) && (!text.isEmpty())){
                textField.setText(text.substring(0,text.length()-1));
            }
        });
        buttonClear.setOnAction((ActionEvent actionEvent)->{
            textField.setText("");
        });
        buttonEqual.setOnAction((ActionEvent actionEvent)->{
            boolean hasError = false;
            textField.getStyleClass().remove("error");
            String text = textField.getText();
            if((text!=null) && (!text.isEmpty())){
                String textResult = "0";
                try {
                    textResult = evaluate(text.trim());
                } catch (ScriptException e) {
                    hasError = true;
                    textField.getStyleClass().add("error");
                }
                if(hasError){
                    textField.setText(text);
                    textField.positionCaret(text.length());
                }else {
                    textField.setText(textResult);
                    textField.positionCaret(textResult.length());
                }

            }
        });
        for (Button calculateButton : calculateButtons)
        {
            calculateButton.setOnAction((ActionEvent actionEvent)->{
                buttonEqual.fire();
                try {
                    String result = textField.getText();
                    String buttonPressed = calculateButton.getText();
                    System.out.println("buttonPressed : "+buttonPressed);
                    switch (buttonPressed){
                        case INVERSE:
                            result = evaluate("1/"+ result);
                            break;
                        case POWER_OF_2:
                            result = evaluate("Math.pow("+result+",2)");
                            break;
                        case SIN:
                            result = evaluate("Math.sin("+result+")");
                            break;
                        case COS:
                            result = evaluate("Math.cos("+result+")");
                            break;
                        case TAN:
                            result = evaluate("Math.tan("+result+")");
                            break;
                        case SQRT:
                            result = evaluate("Math.sqrt("+result+")");
                            break;
                    }
                    textField.setText(result);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Button button : buttons)
        {
            if(button==buttonBackspace || button==buttonClear || button==buttonEqual || calculateButtons.contains(button)){
                continue;
            }
            button.setOnAction((ActionEvent e) ->
            {
                String text = textField.getText();
                textField.setText(text+button.getText());
            });
        }
        textField.addEventFilter(KeyEvent.KEY_TYPED,(event)->{
            String text  = event.getCharacter();
            int keyValue = (int)text.toCharArray()[0];
            System.out.println("Key Value "+keyValue);
            if(!ArrayUtils.contains(buttonsValues,text)){
                event.consume();
            }
            if(keyValue==ENTER_KEY){
                buttonEqual.fire();
            }
            else if(keyValue==DELETE_KEY||keyValue==ESC_KEY){
                textField.setText("");
            }
        });
        buttonEqual.setMinHeight(60);
        gridPane.getChildren().addAll(textField);
        gridPane.getChildren().addAll(buttons);

        Scene scene = new Scene(gridPane, 268, 275);
        scene.getStylesheets().add(
                getClass().getClassLoader().getResource("error.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
