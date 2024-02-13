
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This contains the main logic behind the wordle game
 * 
 * @author Akin Edwards
 * @version 1
 */
public class wordleController {
    //A bunch of fxml constructors
    @FXML
    Label Title;
    @FXML
    TextField B1;
    @FXML
    TextField B2;
    @FXML
    TextField B3;
    @FXML
    TextField B4;
    @FXML
    TextField B5;
    @FXML
    HBox textBox;
    @FXML
    HBox textBox1;
    @FXML
    HBox textBox2;
    @FXML
    HBox textBox3;
    @FXML
    HBox textBox4;
    @FXML
    HBox textBox5;
  
    
   

    static Random rand = new Random();
    static String[] words = {"hello","fouls","broke","sting","pelts","zoned","pairs","happy","learn","light","house","aroma","brief","earth"};
    static String randomWord = words[rand.nextInt(words.length)];
    
    String[] guessInfo = new String[10];
    int guesses = 0;
    private String word = "";
    List<HBox> textBoxList = new ArrayList<HBox>();
    int samePos;
    boolean gameEnd = false;
    static boolean winCheck = false;

    /**
     *adds textboxes to list of hboxes
     * 
     */
    @FXML
    public void initialize(){
        System.out.println(randomWord);
        //adds the hboxes to a list
        textBoxList.add(textBox1);
        textBoxList.add(textBox2);
        textBoxList.add(textBox3);
        textBoxList.add(textBox4);
        textBoxList.add(textBox5); 
    }
    

    /**
     * if any key is pressed
     * gets the letters typed into the textBox
     * checks for delete pressed
     * adds letters to string to create word
     * @param key pressed
     */
    @FXML
    public void keyPressed(KeyEvent event) throws IOException{
        
       //get the scene from the app
        Scene scene = App.getScene();
         word = "";
     
        //this chunk detects which block I am currently on
        Node whoCalled = (Node)event.getSource();
        String id = whoCalled.getId();
        String text = ((TextField)scene.lookup("#" + id)).getText();
        int numberBox = Character.getNumericValue(id.charAt(1));

        //checks if something is in the current block 
        if (text.length() >0){
            ((TextField)scene.lookup("#" + id)).setEditable(false);
        }

        //checks for backspace key
        if (event.getCode() == KeyCode.BACK_SPACE){

            
            //checks if theres nothing in the current text field and not on the first one
            if ((text.length() == 0) && (numberBox != 1)){

                //changes focus to textfield before this one
                 ((TextField)scene.lookup("#B" + (numberBox-1))).requestFocus();
                 //makes textfield before editable
                 ((TextField)scene.lookup("#B" + (numberBox-1))).setEditable(true);
                 //sets text to nothing
                 ((TextField)scene.lookup("#B" + (numberBox-1))).setText("");
                 
            }
            if (numberBox == 5){
                
                ((TextField)scene.lookup("#B" + (numberBox))).setText("");
            }
            
            
        }

        //if not backspace key
       else{
           //loop through the textfields in the hbox
            for (Node Text: textBox.getChildren()){
                //temporary variable for the text fields
                TextField tempText = (TextField)Text;
           
                
                //checks if theres a letter in the box
                if (tempText.getText().length() >0){
                    char letter = tempText.getText().charAt(0);
                    //checks if spacebar is pressed
                    if (letter == ' '){
                        tempText.setText("");
                    }
                    //if not spacebar
                    else{
                        //adds the letters together to create a word
                        word = word + letter;
                        
                        
                    }
                

                    //make the text field uneditable
                    if(numberBox != 5){
                        tempText.setEditable(false);  
                 }
                }
                //if theres nothing in the text field
                else{
                    //if current textfield is not the last one move to next box and make editable
                    if (numberBox != 5){
                        ((TextField)scene.lookup("#B" + (numberBox+1))).requestFocus();
                        tempText.setEditable(true);
                    }
                    break;
                }
                    
                    
        
            }
        }
        

    }
    /**
     * if the FXML button was pressed
     * Checks for win or loss
     * Changes colours of blocks according to position and if they are in the word
     * changes boxes to corresponding letters
     */
     @FXML
     public void buttonPressed() throws IOException, InterruptedException{
         //if there is a letter in each text field

         int samePos = 0;
        if (word.length() == 5){
                   
            //counter 
            int tempCount = 0;
            //get the list of textFields
            for (Node Text: textBoxList.get(guesses).getChildren()){
                        
                //temporary textfield
                TextField tempBox = (TextField)Text;
                //change the display box to the corresponding letter
                tempBox.setText(Character.toString(word.charAt(tempCount)));
                         
                //checks each letter in the word
                for (int i= 0; i<word.length();i++){

                    //checks if the letter is in the word
                    if (randomWord.charAt(i)==(word.charAt(tempCount))){

                        //checks if the letters are in the same position
                        if (i==tempCount){
                                   
                            tempBox.setStyle("-fx-background-color: green");
                                  
                            samePos+=1;
                                    
                                     

                            break;
                        }   
                                //if in the word but not same position
                        else{
                            //int count = randomWord.length() - randomWord.replaceAll(Character.toString(word.charAt(i)),"").length();
                                   
                            tempBox.setStyle("-fx-background-color: yellow");
                                    
                        }

                    }
                }
                      tempCount+=1;
            }

            //checks if the player won
            if ((samePos == 5) && (word.equals(randomWord))) {
                        
                Title.setTextFill(Color.GREEN);
                winCheck = true;
                gameEnd = true;

            }
            
            guesses +=1;
               
            word ="";
            int editableCount = 1;
            //resets everything after each button press so more guesses can be made
            for (Node Text: textBox.getChildren()){
                TextField tempText = (TextField)Text;

                //only set the first box to editiable initially
                if (editableCount == 1){
                    tempText.setEditable(true);
                    }

                tempText.setText("");
                textBox.getChildren().get(0).requestFocus();

                Node thisBox = textBox.getChildren().get(editableCount);
                TextField tempBox= (TextField)thisBox;
                tempBox.setEditable(false);
                     
                if (editableCount < 4){
                    editableCount +=1;
                    }
                     
            }
                
                //checks if the player used all their guesses
                if (guesses == 5){
                    winCheck = false;
                    gameEnd = true;
                }
                
                 



            // changes to the endscreen after checking for conditions
            if (gameEnd == true){
                App.setRoot("endScreen");
                    
            }
        }
        
        
        
        
     }
     /**
     * Returns the random word
     * 
     * @return The string of the random word
     */
      public static String getWord(){

         return  randomWord;
      }
     /**
     * Returns if the player won or not
     * 
     * @return The boolean value of winCheck
     */
      public static boolean getWinCheck(){
          return winCheck;
      }

}
   

