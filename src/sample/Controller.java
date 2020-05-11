package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    public Button continueButton;
    private Map<Integer, GridPane> map;

    @FXML
    GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map = new HashMap<>();
        int counter = 0;

        for (int blockColumn = 0; blockColumn < 3 ; blockColumn++) {
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                GridPane box = new GridPane();
                box.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");

                setupBlock(box);

                GridPane.setConstraints(box, blockColumn, blockRow);
                gridPane.getChildren().add(box);

                map.put(counter, box);
                counter++;
            }
        }
    }

    private void setupBlock(GridPane box)
    {
        for (int col = 0; col < 3; col++) {
            for (int row = 0 ; row < 3; row++) {
                TextField textField = new TextField("0");
                textField.setStyle("-fx-pref-width: 2em;");
                GridPane.setConstraints(textField, col, row);
                box.getChildren().add(textField);
            }
        }
    }

    private void solveSudoku()
    {
        int [][]arr = new int[9][9];

        int counter = 0;

        for (Node gp : gridPane.getChildren())
        {
            parseGrid(counter, (GridPane)gp, arr);
            counter++;
        }

        print(arr, 9);

        SudokuSolver sudokuSolver = new SudokuSolver(9);
        sudokuSolver.sudokuSolver(arr);
        print(arr, 9);

        showSolution(arr);

    }

    private void parseGrid(int counter, GridPane gridPane, int[][] arr)
    {
        int col = determineCol(counter);
        int row = determineRow(counter);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Node ta = gridPane.getChildren().get(i + j*3);
                TextField a = (TextField)ta;

                int input = Integer.parseInt(a.getText());

                arr[i + col][j + row] = input;
            }

        }
    }

    private int determineCol(int counter)
    {
        int col = 0;

        if (counter < 3)
        {
            if (counter == 1)
                col = 3;
            if (counter == 2)
                col = 6;
        }
        if (counter >= 3 && counter < 6)
        {
            if (counter == 4)
                col = 3;
            if (counter == 5)
                col = 6;
        }

        if (counter > 5)
        {
            if (counter == 7)
                col = 3;
            if (counter == 8)
                col = 6;
        }

        return col;
    }

    private int determineRow(int counter)
    {
        int row = 0;

        if (counter >= 3 && counter < 6)
            row = 3;

        if (counter > 5)
            row = 6;

        return row;
    }

    public void showSolution(int[][] arr)
    {
        int counter = 0;

        for (Node gp : gridPane.getChildren())
        {
            updateGrid(counter, (GridPane)gp, arr);
            counter++;
        }
    }

    public void updateGrid(int counter, GridPane gp, int[][] arr)
    {
        int col = determineCol(counter);
        int row = determineRow(counter);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Node ta = gp.getChildren().get(i + j*3);
                TextField a = (TextField)ta;

                a.setText(String.valueOf(arr[i + col][j + row]));
            }

        }
    }

    public void continueButtonOnClick(ActionEvent event)  {
        solveSudoku();

    }
    //////
    public static void print(int[][] board, int N)
    {
        for (int r = 0; r < N; r++)
        {
            for (int d = 0; d < N; d++)
            {
                System.out.print(board[r][d]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((r + 1) % (int) Math.sqrt(N) == 0)
            {
                System.out.print("");
            }
        }

        System.out.print("\n----------------------\n");
    }
}
