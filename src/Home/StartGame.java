package Home;

import Home.Fruits.Banana;
import Home.Fruits.Lemon;
import Home.Fruits.Pineapple;
import Home.Fruits.Watermelon;
import Model.FJButtons;
import Model.FJSubScene;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartGame{
    public AnchorPane gamePane;
    public Scene gameScene;
    public Stage gameStage;

    private ImageView lifeImage;
    
    private Stage menuStage;
    
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    public final static String LIFEIMAGE = "Home/resources/life.png";
    
    private final String PAUSE_BACKGROUND_IMAGE = "Model/resources/panel.png";

    private final String BACKGROUND_IMAGE = "Home/resources/back.jpg";
    private final String FONT_PATH = "src/Model/resources/kenvector_future.ttf";
    public boolean isGameStopped = false;

    Random randomPositionGenerator = new Random();
    Circle circle1 = new Circle(1000, 1000, 5);

    Particle splash = new Particle();



    BoxBlur bb = new BoxBlur();
    MotionBlur bb1 = new MotionBlur();

    Fruit lemon = new Lemon();
    Fruit watermelon = new Watermelon();
    Fruit banana = new Banana();
    Fruit pineapple = new Pineapple();


    public int totalLife = 10;
    public int totalScore = 0;

    public int secondPassed = 0;
    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date nowDate = new Date();

    public double firstCoordinateX = 0,firstCoordinateY=0;
    public double lastCoordinateX = 0,lastCoordinateY=0;
    public double firsandLastCoordsDistance = 0;

    HomePage Home;

    Bomb boom = new Boom();

    Text score = new Text();
    Text gameOverText = new Text();
    Text lifeCount = new Text();
    Text howMuchPlayed = new Text();
   
    private FJButtons play = new FJButtons("CONTINUE");
    private FJButtons mainMenu = new FJButtons("MAIN MENU");
    private FJButtons retry = new FJButtons("PLAY AGAIN");
    private FJSubScene pauseSub;
    private FJSubScene gameOverSub;
    private boolean isHidden = true;

    private AnimationTimer gameTimer;
    public Timeline timeline1;
            
    public StartGame(){
        System.gc();
        initializeStage();


        createGameLoop();
        createCircle();
        circleMove();
        createScoreText();
        createLifeImage();
        createLifeText();
        createFruits();
        createBombs();
        moveFruits();
        moveBombs();
        stopGame();
        createPauseSub();
        createButtoninPauseSub();
        howMuchPlayed();
        createHowMuchPlayedText();
        
    }
    
    
    public void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 800, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(image));
        gameStage.setTitle("Fruit Ninja");
        gameStage.getIcons().add(new Image("Home/resources/watermelon.png"));
        gameStage.setResizable(false);
    }
    
    private void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                circleMove();
                showScore();
                showLife();
                
                sliceFruit();
                slideBomb();
                moveSliceds();
                
                gameOver();
                showHowMuchPlayedText();
                updateTotalLifeandtotalScore();


            }
        };
        gameTimer.start();

    }
    
    public void circleMove() {
        gameScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                circle1.setVisible(true);
                circle1.setCenterX(event.getX());
                circle1.setCenterY(event.getY());
                gameScene.setCursor(Cursor.NONE);

            }

        });

        gameScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {


            }
        });

        gameScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle1.setVisible(false);
                gameScene.setCursor(Cursor.DEFAULT);
                circle1.setCenterX(1000);
                circle1.setCenterY(1000);

            }
        });
    }
    
    private void showScore() {
        score.setText("Score: " + (totalScore));
    }
    
    private void showLife() {
        lifeCount.setText("x "+(totalLife));
    }
    
    private void sliceFruit()
    {

        lemon.slice(circle1,gamePane);
        watermelon.slice(circle1,gamePane);
        banana.slice(circle1,gamePane);
        pineapple.slice(circle1,gamePane);
    }
    
    private void slideBomb()
    {
        boom.slice(circle1,gamePane);
    }
   
    private void moveSliceds()
    {
        lemon.moveSliced();
        watermelon.moveSliced();
        banana.moveSliced();
        pineapple.moveSliced();
    }
    
    public void gameOver()
    {
        
        if(boom.isGameOver == true || totalLife == 0)
        {
            gameOverEvents();
            Timeline timeline = new Timeline();

            KeyFrame kf = new KeyFrame(Duration.millis(3000),e ->{
                createGameOverSub();
            });
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(1);
            timeline.play();
            
            Home = new HomePage();
            Home.LastScore(secondPassed,totalScore,dateformat.format(nowDate), "CurrenUser");
        

        }
        
        retry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline1.stop();

                goToPlayAgain();

            }
        });
        
    }
    
    public void gameOverEvents()
    {

        isGameStopped = true;
        watermelon.stopGame(isGameStopped);
        lemon.stopGame(isGameStopped);
        banana.stopGame(isGameStopped);
        pineapple.stopGame(isGameStopped);
        boom.stopGame(isGameStopped);
        gameTimer.stop();
    }
    
    public void createGameOverSub()
    {
        gameOverSub = new FJSubScene();
        gamePane.getChildren().add(gameOverSub);
        BackgroundImage image = new BackgroundImage(new Image(PAUSE_BACKGROUND_IMAGE,400,300,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        AnchorPane root2 = (AnchorPane) gameOverSub.getRoot();
        root2.setBackground(new Background(image));
        gameOverSub.setWidth(400);
        gameOverSub.setHeight(400);

        gameOverSub.setLayoutX(gameOverSub.getLayoutX()-950);

        try {
            gameOverText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 45));
        } catch (FileNotFoundException e) {
            gameOverText.setFont(Font.font("Verdana", 45));
        }
        gameOverText.setLayoutX(70);
        gameOverText.setLayoutY(55);
        gameOverText.setFill(Color.BLACK.brighter());
        gameOverText.setText("GAME OVER");
        gameOverSub.getPane().getChildren().add(gameOverText);
        gameOverSub.getPane().getChildren().add(mainMenu);
        gameOverSub.getPane().getChildren().add(retry);

        mainMenu.setLayoutX(105);
        mainMenu.setLayoutY(175);

        retry.setLayoutX(105);
        retry.setLayoutY(90);
    }
    
    private void goToPlayAgain()
    {

        gamePane.getChildren().clear();

        gameStage.hide();
        gameStage.close();


        Platform.runLater(() -> new StartGame().createNewGame( new Stage()));
        System.gc();
    }
    
    private void showHowMuchPlayedText()
    {
        howMuchPlayed.setText(secondPassed+"");
    }
    
    private void updateTotalLifeandtotalScore()
    {
        totalLife = lemon.life+watermelon.life+banana.life+pineapple.life-9;
        totalScore = lemon.totalScore+watermelon.totalScore+banana.totalScore+pineapple.totalScore;
    }
    
    private void createCircle() {
        circle1.setVisible(false);
        circle1.setFill(Color.WHITE);
        bb.setWidth(7);
        bb.setHeight(7);
        bb.setIterations(3);

        bb1.setRadius(35.5);
        bb1.setAngle(135);

        circle1.setVisible(true);
        circle1.setEffect(bb1);

        gamePane.getChildren().add(circle1);
    }
    
    
    private void createScoreText() {
        try {
            score.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            score.setFont(Font.font("Verdana", 25));
        }
        score.setLayoutX(455);
        score.setLayoutY(42);
        score.setFill(Color.ORANGE);
        gamePane.getChildren().add(score);
    }
    
    private void createLifeText()
    {
        try {
            lifeCount.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            lifeCount.setFont(Font.font("Verdana", 25));
        }
        lifeCount.setLayoutX(80);
        lifeCount.setLayoutY(45);
        lifeCount.setFill(Color.ORANGE);
        gamePane.getChildren().add(lifeCount);
    }
    
    private void createLifeImage()
    {
        lifeImage = new ImageView(LIFEIMAGE);
        lifeImage.setLayoutX(15);
        lifeImage.setLayoutY(15);


        gamePane.getChildren().add(lifeImage);

        FadeTransition fadeTransition =
                new FadeTransition(Duration.millis(1000),lifeImage);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.5f);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);

        fadeTransition.play();

    }
    
    private void createFruits()
    {

        lemon.createFruit(gamePane);
        lemon.createSlicedFruit(gamePane);

        watermelon.createFruit(gamePane);
        watermelon.createSlicedFruit(gamePane);

        banana.createFruit(gamePane);
        banana.createSlicedFruit(gamePane);

        pineapple.createFruit(gamePane);
        pineapple.createSlicedFruit(gamePane);

    }
    private void createBombs()
    {
        boom.createBomb(gamePane);
    }

    private void moveFruits()
    {

        lemon.moveFruit(isGameStopped);
        watermelon.moveFruit(isGameStopped);
        banana.moveFruit(isGameStopped);
        pineapple.moveFruit(isGameStopped);
    }
    private void moveBombs()
    {
        boom.moveBomb();
    }
    
    
    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }
    
    private void stopGame()
    {
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isGameStopped = false;
                watermelon.stopGame(isGameStopped);
                lemon.stopGame(isGameStopped);
                banana.stopGame(isGameStopped);
                pineapple.stopGame(isGameStopped);
                boom.stopGame(isGameStopped);
                gameTimer.start();
                timeline1.play();
                movePauseSub();
            }
        });

        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline1.stop();



                goToMainMenu();

            }
        });
        
    }
 
    private void movePauseSub()
    {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(pauseSub);
        if(isHidden){
            transition.setToX(-950);
            isHidden = false;
        }
        else
        {
            transition.setToX(0);
            isHidden = true;
        }



        transition.play();
    }
    
    private void goToMainMenu()
    {

        
        gameStage.close();

        Home = new HomePage();
        Home.mainStage.show();
        System.gc();

    }
    
    private void createPauseSub()
    {
         pauseSub = new FJSubScene();
        gamePane.getChildren().add(pauseSub);
        BackgroundImage image = new BackgroundImage(new Image(PAUSE_BACKGROUND_IMAGE,400,200,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        AnchorPane root2 = (AnchorPane) pauseSub.getRoot();
        root2.setBackground(new Background(image));
        pauseSub.setWidth(400);
        pauseSub.setHeight(200);

    }
    
   private void createButtoninPauseSub()
    {
        play.setLayoutX(105);
        play.setLayoutY(20);
        pauseSub.getPane().getChildren().add(play);

        mainMenu.setLayoutX(105);
        mainMenu.setLayoutY(105);
        pauseSub.getPane().getChildren().add(mainMenu);
    }
    public int couunt=0;
    private void howMuchPlayed()
    {
        timeline1 = new Timeline();
        for (int i = 1; i < 100; i++) {
            couunt++;
            KeyFrame kf = new KeyFrame(Duration.millis(i*1000), event -> {
                secondPassed++;

            });
            timeline1.getKeyFrames().add(kf);

        }
        timeline1.play();
    }
    
    private void createHowMuchPlayedText()
    {
        try {
            howMuchPlayed.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            howMuchPlayed.setFont(Font.font("Verdana", 25));
        }
        howMuchPlayed.setLayoutX(550);
        howMuchPlayed.setLayoutY(790);
        howMuchPlayed.setFill(Color.ORANGE);

        gamePane.getChildren().add(howMuchPlayed);
    }
}
