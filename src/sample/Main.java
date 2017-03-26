package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import static sample.MathUtils.evaluate;


public class Main extends Application {

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
        Button buttonFraction = new Button("1/x");
        Button buttonPercent = new Button("%");
        Button buttonRadical = new Button("sqrt");
        Button buttonChangePlusOrMinus = new Button("+/-");
        Button buttonClear = new Button("C");
        Button buttonBackspace = new Button("<-");
        Button buttonSin = new Button("Sin");
        Button buttonCos = new Button("Cos");
        Button buttonTan = new Button("Tan");
        Button buttonOpenParentheses = new Button("(");
        Button buttonCloseParentheses = new Button(")");
        Button buttonPower = new Button("^");

        Font textFont = Font.font("Verdana", FontWeight.BOLD, 18);
        textField.setPrefHeight(48);
        textField.setFont(textFont);
        textField.setAlignment(Pos.CENTER_RIGHT);

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
                buttonPercent,buttonRadical,buttonFraction,buttonSin,buttonCos,buttonTan,buttonEqual
        );
        for (Button button : buttons)
        {
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
            String text = textField.getText();
            if((text!=null) && (!text.isEmpty())){
                double result = evaluate(text.trim());
                String textResult = String.valueOf(result);
                if(textResult.contains(".")){
                    String[] tokens = textResult.split("\\.");
                    long decimalPart = Long.parseLong(tokens[1]);
                    if(decimalPart < 1){
                        textResult = tokens[0];
                    }
                }
                textField.setText(textResult);
            }
        });
        for (Button button : buttons)
        {
            if(button==buttonBackspace || button==buttonClear || calculateButtons.contains(button)){
                continue;
            }
            button.setOnAction((ActionEvent e) ->
            {
                String text = textField.getText();
                textField.setText(text+button.getText());
            });
        }
        buttonEqual.setMinHeight(60);
        gridPane.getChildren().addAll(textField);
        gridPane.getChildren().addAll(buttons);

        Scene scene = new Scene(gridPane, 268, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
