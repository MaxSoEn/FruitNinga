
package fruitninga;

import javafx.application.Application;
import javafx.stage.Stage;

import Home.HomePage;

public class FruitNinga extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        HomePage home = new HomePage();
               
        primaryStage = home.getMainStage();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
