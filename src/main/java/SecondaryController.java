import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * This class contains the functionality of the endscreen
 * 
 * @author Akin Edwards
 * @version 1
 */
public class SecondaryController {
    @FXML
    HBox displayBox;

    String word = wordleController.getWord();
     boolean didWin = wordleController.getWinCheck();

/**
 * 
 * Sets the boxes to the corresponding letter of the random word
 */
    @FXML
    private void initialize(){
        
        int tempCount = 0;
        //goes through each text field sets them to corresponding letter 
         for (Node Text: displayBox.getChildren()){
                TextField tempBox = (TextField)Text;
                tempBox.setText(Character.toString(word.charAt(tempCount)));
                // if the player won change the colour to green
                if (didWin){
                    tempBox.setStyle("-fx-background-color: green");
                }
                //if not change colour to maroon
                else{
                    tempBox.setStyle("-fx-background-color: maroon");
                }
                tempCount +=1;

          }
    }
   
/**
 * Changes the screen to the main wordle game
 *
 */
    @FXML
    private void replayPressed()throws IOException {
        App.setRoot("Wordle");
    }
}