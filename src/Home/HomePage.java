package Home;

import Model.FJButtons;
import Model.FJSubScene;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class HomePage {
    
    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private final AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage; 
   
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;

    private static String Ownduration;
    private static int Ownscore;
    private static String Owndate;
    
    private FJSubScene creditsSubScene;
    private FJSubScene scoreSubScene;
    private FJSubScene ownScoresSubScene;
    private FJSubScene startGameScene;

    private FJSubScene sceneToHide;

    Text topScoreText = new Text();
    Text topScoreTextforOwnSubScene = new Text();
    Text scoreFirst = new Text();
    Text scoreSecond = new Text();
    Text scoreThird = new Text();
    Text scoreFourth = new Text();
    Text scoreFifth = new Text();
    Text duration = new Text();
    Text date = new Text();
    
    //Text[] usernameScore = new Text[5];
    ArrayList<Text> highScoresUsernameText = new ArrayList<>();
    ArrayList<Text> highScoresScoresText = new ArrayList<>();
    ArrayList<Text> highScoresDurationsText = new ArrayList<>();
    ArrayList<Text> highScoresDateText = new ArrayList<>();


    ArrayList<Text> ownScoresText = new ArrayList<>();
    ArrayList<Text> ownDurationsText = new ArrayList<>();
    ArrayList<Text> ownDateText = new ArrayList<>();


    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> curdata = new ArrayList<>();
    ArrayList<String> scoreList = new ArrayList<>();

    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date date1 = new Date();
    
    Text CFirst = new Text();
    Text CSecond = new Text();
    Text CThird = new Text();
    
    List<Button> menuButtons;
    public HomePage(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.setTitle("Fruit Ninga");
        mainStage.getIcons().add(new Image("Home/resources/watermelon.png"));
        createLogo();
        createSubScenes();
        createButtons();
        createBackground();
        
        
    }
    
    private void showSubScene(FJSubScene subScene)
    {
        
        if(sceneToHide != null)
        {
            sceneToHide.moveSubScene();
        }
        
        subScene.moveSubScene();
        sceneToHide = subScene;
       
    }

    private void createSubScenes()
    {

        creditsSubScene = new FJSubScene();
        mainPane.getChildren().add(creditsSubScene);
        creditsSubScene.getPane().getChildren().addAll(CFirst,CSecond,CThird);
        createCreditsTexts();
        
        scoreSubScene = new FJSubScene();
        mainPane.getChildren().add(scoreSubScene);
        scoreSubScene.getPane().getChildren().addAll(scoreFirst,scoreSecond,scoreThird,scoreFourth,scoreFifth,duration,date,topScoreText);

        ownScoresSubScene = new FJSubScene();
        mainPane.getChildren().add(ownScoresSubScene);
        FJButtons ownScoresButton = new FJButtons("Own Scores");
        scoreSubScene.getPane().getChildren().add(ownScoresButton);
        ownScoresSubScene.getPane().getChildren().add(topScoreTextforOwnSubScene);
        
        ownScoresButton.setLayoutX(212);
        ownScoresButton.setLayoutY(340);
        
        setHighScores();
        showScoresinSubscene();
        setOwnScores();
        showHighScoresinHighScoreSubScene();
        showOwnScoresinOwnSubScene();

        
        ownScoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                showSubScene(ownScoresSubScene);
            }
        });
        
        createStartGameSubScene();

    }
   
    private void createCreditsTexts() {
        CFirst.setText("Electrical Engineering");CFirst.setLayoutX(80);CFirst.setLayoutY(100);
        CSecond.setText("Second Year");CSecond.setLayoutX(80);CSecond.setLayoutY(200);
        CThird.setText("Zagazig University");CThird.setLayoutX(80);CThird.setLayoutY(300);

        try {
            CFirst.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
            CSecond.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
            CThird.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
        } catch (FileNotFoundException e) {
            CFirst.setFont(Font.font("Verdana", 50));
            CSecond.setFont(Font.font("Verdana", 50));
            CThird.setFont(Font.font("Verdana", 50));
        }

    }

    private void showScoresinSubscene()
    {
        scoreFirst.setLayoutX(100);scoreFirst.setLayoutY(100);
        scoreSecond.setLayoutX(100);scoreSecond.setLayoutY(150);
        scoreThird.setLayoutX(100);scoreThird.setLayoutY(200);
        scoreFourth.setLayoutX(100);scoreFourth.setLayoutY(250);
        scoreFifth.setLayoutX(100);scoreFifth.setLayoutY(300);

        duration.setLayoutX(100);duration.setLayoutY(50);
        date.setLayoutX(150);date.setLayoutY(50);

        try {
            topScoreText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
            topScoreTextforOwnSubScene.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
            scoreFirst.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreSecond.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreThird.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreFourth.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreFifth.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        } catch (FileNotFoundException e) {
            topScoreText.setFont(Font.font("Verdana", 25));
            topScoreTextforOwnSubScene.setFont(Font.font("Verdana", 25));
            scoreFirst.setFont(Font.font("Verdana", 20));
            scoreSecond.setFont(Font.font("Verdana", 20));
            scoreThird.setFont(Font.font("Verdana", 20));
            scoreFourth.setFont(Font.font("Verdana", 20));
            scoreFifth.setFont(Font.font("Verdana", 20));
        }
        topScoreText.setText("USERNAME     SCORE     DURATION       DATE");
        topScoreText.setLayoutX(40);
        topScoreText.setLayoutY(45);
        topScoreText.setUnderline(true);
        topScoreText.setFill(Color.ORANGE);

        topScoreTextforOwnSubScene.setText("SCORE          DURATION            DATE");
        topScoreTextforOwnSubScene.setLayoutX(115);
        topScoreTextforOwnSubScene.setLayoutY(45);
        topScoreTextforOwnSubScene.setUnderline(true);
        topScoreTextforOwnSubScene.setFill(Color.ORANGE);




    }

    private void showHighScoresinHighScoreSubScene()
    {
        for (int i = 0; i <highScoresUsernameText.size() ; i++) {
            try{
            scoreSubScene.getPane().getChildren().add(highScoresUsernameText.get(i));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                highScoresUsernameText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            } catch (FileNotFoundException e) {
                highScoresUsernameText.get(i).setFont(Font.font("Verdana", 20));

            }
            highScoresUsernameText.get(i).setLayoutX(70);
            highScoresUsernameText.get(i).setLayoutY(100+(i*50));
        }
    }

    private void showOwnScoresinOwnSubScene()
    {
        for (int i = 0; i <ownScoresText.size() ; i++) {
            ownScoresSubScene.getPane().getChildren().add(ownScoresText.get(i));
            ownScoresSubScene.getPane().getChildren().add(ownDurationsText.get(i));
            ownScoresSubScene.getPane().getChildren().add(ownDateText.get(i));

            try {

                ownScoresText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                ownDurationsText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                ownDateText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            } catch (FileNotFoundException e) {
                ownScoresText.get(i).setFont(Font.font("Verdana", 20));
                ownDurationsText.get(i).setFont(Font.font("Verdana", 20));
                ownDateText.get(i).setFont(Font.font("Verdana", 20));

            }
            ownScoresText.get(i).setLayoutX(135);ownScoresText.get(i).setLayoutY(100+(i*50));
            ownDurationsText.get(i).setLayoutX(280);ownDurationsText.get(i).setLayoutY(100+(i*50));
            ownDateText.get(i).setLayoutX(395);ownDateText.get(i).setLayoutY(100+(i*50));

        }
    }

    private void setHighScores()
    {
        
        // Frist Player
        String scoree;
        String durationScore;
        String dateScore;
        String username;
        if(Ownscore > 5000){
            username = "CurrentUser";
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            dateScore = "2022/05/21 05:20";
            durationScore = "300";        
            scoree = "5000";
            username = "ahmed";
        }
        highScoresScoresText.add(new Text(scoree));
        highScoresDurationsText.add(new Text(durationScore));
        highScoresDateText.add(new Text(dateScore));
        highScoresUsernameText.add(new Text(username));

        data.add("                      "+scoree+"            "+durationScore+"             "+dateScore);

        // Second Player
        if(Ownscore > 4000 && Ownscore <= 5000){
            username = "CurrentUser";
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            dateScore = "2022/05/17 13:40";
            durationScore = "150";
            scoree = "4000";
            username = "Aya";
        }
        highScoresScoresText.add(new Text(scoree));
        highScoresDurationsText.add(new Text(durationScore));
        highScoresDateText.add(new Text(dateScore));
        highScoresUsernameText.add(new Text(username));

        data.add("                      "+scoree+"            "+durationScore+"             "+dateScore);
 
        // Third Player
        if(Ownscore > 2500 && Ownscore <= 4000){
            username = "CurrentUser";
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            dateScore = "2022/05/18 15:10";
            durationScore = "34";
            scoree = "2500";
            username = "Omnia";
        }
        highScoresScoresText.add(new Text(scoree));
        highScoresDurationsText.add(new Text(durationScore));
        highScoresDateText.add(new Text(dateScore));
        highScoresUsernameText.add(new Text(username));

        data.add("                      "+scoree+"            "+durationScore+"             "+dateScore);

        
        // Forth Player
        if(Ownscore > 1500 && Ownscore <= 2500){
            username = "CurrentUser";
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            dateScore = "2022/05/12 12:40";
            durationScore = "54";
            scoree = "1500";
            username = "Ali";
        }
        highScoresScoresText.add(new Text(scoree));
        highScoresDurationsText.add(new Text(durationScore));
        highScoresDateText.add(new Text(dateScore));
        highScoresUsernameText.add(new Text(username));

        data.add("                      "+scoree+"            "+durationScore+"             "+dateScore);

        // Fifth Player
        if(Ownscore > 1000 && Ownscore <= 1500){
            username = "CurrentUser";
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            dateScore = "2022/05/15 14:23";
            durationScore = "90";
            scoree = "1000";
            username = "Maha";
        }
        highScoresScoresText.add(new Text(scoree));
        highScoresDurationsText.add(new Text(durationScore));
        highScoresDateText.add(new Text(dateScore));
        highScoresUsernameText.add(new Text(username));

        data.add("                      "+scoree+"            "+durationScore+"             "+dateScore);

        String score1,score2,score3,score4,score5;
        score1 = data.get(0);
        score2 = data.get(1);
        score3 = data.get(2);
        score4 = data.get(3);
        score5 = data.get(4);
        scoreFirst.setText(score1);
        scoreSecond.setText(score2);
        scoreThird.setText(score3);
        scoreFourth.setText(score4);
        scoreFifth.setText(score5);
    }

    private void setOwnScores()
    {
        int i = 0;
        
        String scoree;
        String durationScore; 
        String dateScore;
        String username;
        
        username = "CurrentUser";
        System.out.print("Current Score");
        System.out.println(Ownscore);
        //First Score
        if(Ownscore > 200){
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            scoree = "200";
            durationScore = "67";
            dateScore = "2022/4/27 10:20";
        }
        ownScoresText.add(new Text(scoree));
        ownDurationsText.add(new Text(durationScore));
        ownDateText.add(new Text(dateScore));
        
        data.add("                          "+scoree+"                    "+durationScore+"              "+dateScore);
        i++;
        
        //Second Score
        if(Ownscore > 149 && Ownscore <= 200){
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            scoree = "149";
            durationScore = "34";
            dateScore = "2022/4/25 14:15";
        }
        ownScoresText.add(new Text(scoree));
        ownDurationsText.add(new Text(durationScore));
        ownDateText.add(new Text(dateScore));
                
        data.add("                          "+scoree+"                    "+durationScore+"              "+dateScore);
        i++;
        
        //Third Score
        if(Ownscore > 70 && Ownscore <= 149){
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            scoree = "70";
            durationScore = "16";
            dateScore = "2022/05/20 15:32";
        }
        ownScoresText.add(new Text(scoree));
        ownDurationsText.add(new Text(durationScore));
        ownDateText.add(new Text(dateScore));
        
        data.add("                          "+scoree+"                    "+durationScore+"              "+dateScore);
        i++;
        
        //Forth Score
        if(Ownscore > 66 && Ownscore <= 70){
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            scoree = "66";
            durationScore = "32";
            dateScore = "2022/05/16 18:14";
        }
        ownScoresText.add(new Text(scoree));
        ownDurationsText.add(new Text(durationScore));
        ownDateText.add(new Text(dateScore));
        
  
        data.add("                          "+scoree+"                    "+durationScore+"              "+dateScore);
        i++;
        
        //Fifth Score
        if(Ownscore > 50 && Ownscore <= 66){
            scoree = Integer.toString(Ownscore);
            durationScore = Ownduration;
            dateScore = Owndate;
        }else{
            scoree = "50";
            durationScore = "12";
            dateScore = "2022/05/10 16:02";
        }
        
        ownScoresText.add(new Text(scoree));
        ownDurationsText.add(new Text(durationScore));
        ownDateText.add(new Text(dateScore));
        
        data.add("                          "+scoree+"                    "+durationScore+"              "+dateScore);
        i++;
        
        String score1,score2,score3,score4,score5;
        score1 = data.get(0);
        score2 = data.get(1);
        score3 = data.get(2);
        score4 = data.get(3);
        score5 = data.get(4);
        scoreFirst.setText(score1);
        scoreSecond.setText(score2);
        scoreThird.setText(score3);
        scoreFourth.setText(score4);
        scoreFifth.setText(score5);
        
    }

    private void createStartGameSubScene() {
        startGameScene = new FJSubScene();
        mainPane.getChildren().add(startGameScene);

        Button button1 = new Button();
        button1.setPrefHeight(50);
        button1.setPrefWidth(100);
        button1.setLayoutX(100);
        button1.setLayoutY(100);


        startGameScene.getPane().getChildren().add(button1);

    }
    
     public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(FJButtons button)
    {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }
    
    
    private void createButtons() {
        createStartButton();
        createScoresButton();
        //createHelpButton();
        createCreditsButton();
        createExitButton();

    }

    private void createStartButton()
    {
        FJButtons startButton = new FJButtons("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newGame();
            }
        });
    }

    public void newGame()
    {   mainStage.close();
        StartGame gameManager = new StartGame();
        gameManager.createNewGame(mainStage);
    }

    private void createScoresButton()
    {
        FJButtons scoresButton = new FJButtons("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);

                showHighScoresinHighScoreSubScene();
            }
        });
    }

    private void createCreditsButton()
    {
        FJButtons creditsButton = new FJButtons("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton()
    {
        FJButtons exitButton = new FJButtons("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }
    private void createBackground() {
        Image backgroundImage = new Image("Home/resources/tile.png", 1024, 768, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo()
    {
        ImageView logo = new ImageView("Home/resources/logo.png");
        logo.setLayoutX(330);
        logo.setLayoutY(40);


        mainPane.getChildren().add(logo);
    }
    
    public void LastScore(int duration,int score,String date,String Username){
        Ownduration = Integer.toString(duration);
        Ownscore = score;
        Owndate = date;
        createSubScenes();
        
    }
    
   
}
