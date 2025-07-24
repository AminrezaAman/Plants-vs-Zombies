
import Plants.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Map.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private AnimationTimer gameUpdate;
    private VBox selectedCardsBox;
    private List<Cart> selectedCards = new ArrayList<>();
    private Pane cardSelectionPane;
    @Override
    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }

    private void startGame(Stage stage){
        stage.setTitle("Plants vs Zombies");
        stage.setFullScreen(true);
        ImageView imageView= new ImageView(new Image(getClass().getResourceAsStream("/Screen/MainMenu.png")));
        ImageView Adventure_0= new ImageView(new Image(getClass().getResourceAsStream("/Screen/aks.png")));
        ImageView Adventure_2= new ImageView(new Image(getClass().getResourceAsStream("/Screen/aks2.png")));

        Button button = new Button("Start");
        imageView.setFitWidth(Sizes.SCREEN_WIDTH);//set background
        imageView.setFitHeight(Sizes.SCREEN_HEIGHT);

        Adventure_0.setLayoutX(Sizes.SCREEN_WIDTH - 810);
        Adventure_0.setLayoutY(120);
        Adventure_0.setFitWidth(440);
        Adventure_0.setFitHeight(293);

        Adventure_2.setLayoutX(725);
        Adventure_2.setLayoutY(280);
        Adventure_2.setFitWidth(380);
        Adventure_2.setFitHeight(380);
        Adventure_2.setRotate(15);

        DropShadow neonEffect = new DropShadow();
        neonEffect.setColor(Color.LIME);
        neonEffect.setRadius(30);
        neonEffect.setSpread(0.5);
        neonEffect.setOffsetX(0);
        neonEffect.setOffsetY(0);
        neonEffect.setInput(null);

        Adventure_0.setOnMouseEntered(e -> Adventure_0.setEffect(neonEffect));
        Adventure_0.setOnMouseExited(e -> Adventure_0.setEffect(null));

        Adventure_2.setOnMouseEntered(e -> Adventure_2.setEffect(neonEffect));
        Adventure_2.setOnMouseExited(e -> Adventure_2.setEffect(null));

        Pane pane = new Pane(imageView , Adventure_0,Adventure_2);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
//        button.setOnAction(event -> {
//            initializeCardSelection();
//            stage.close();
//        });

        Adventure_0.setOnMouseClicked(event -> {
            initializeCardSelection();
            stage.close();
        });
        stage.show();
    }

    public void initializeCardSelection() {
        Stage stage = new Stage();
        Button button = new Button("Start Game");

        Button loading = new Button("load");

        cardSelectionPane = new Pane();
        Scene scene = new Scene(cardSelectionPane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        ImageView imageView= new ImageView(new Image(getClass().getResourceAsStream("/Items/Background/background_5.jpg")));
        imageView.setFitWidth(Sizes.SCREEN_WIDTH);//set background
        imageView.setFitHeight(Sizes.SCREEN_HEIGHT);
        cardSelectionPane.getChildren().add(imageView);

        selectedCardsBox = new VBox(10);



        selectedCardsBox.setLayoutX(20);
        selectedCardsBox.setLayoutY(Sizes.SCREEN_HEIGHT / 6);
        selectedCardsBox.setStyle(
                "-fx-background-color: #C3B091 ;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-padding: 10;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 4);"
        );


        cardSelectionPane.getChildren().addAll(selectedCardsBox);

        ImageView frame = new ImageView(new Image(getClass().getResourceAsStream("/Screen/PanelBackground.png")));
        // frame.setScaleX(1.5);
        // frame.setScaleY(1.5);


        frame.setFitWidth(456);
        frame.setFitHeight(630);
        frame.setLayoutX( (Sizes.SCREEN_WIDTH / 3) );
        frame.setLayoutY(Sizes.SCREEN_HEIGHT / 3);

        double originalWidth = frame.getImage().getWidth();
        double originalHeight = frame.getImage().getHeight();
        double scaledWidth = originalWidth * frame.getScaleX();
        double scaledHeight = originalHeight * frame.getScaleY();
        button.setPrefWidth(190);
        button.setPrefHeight(30);
        Font adventureFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Wonderland_3.ttf"), 30);
        button.setStyle(
                "-fx-background-color: #8B4513;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-padding: 10;" +
                        "-fx-text-fill: white;"+
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 4);"
        );
        button.setFont(adventureFont);
        frame.setLayoutX((Sizes.SCREEN_WIDTH / 2) - (scaledWidth / 2));
        frame.setLayoutY((Sizes.SCREEN_HEIGHT / 2) - (scaledHeight / 2));
        button.setLayoutX(frame.getLayoutX() + (frame.getBoundsInParent().getWidth() / 2) - (button.getPrefWidth() / 2));
        button.setLayoutY(frame.getLayoutY() + frame.getBoundsInParent().getHeight() - 65);


        cardSelectionPane.getChildren().addAll(frame , button ,loading);

        double tempx = (Sizes.SCREEN_WIDTH / 2) - (scaledWidth / 2) + 15;
        double tempy = (Sizes.SCREEN_HEIGHT / 2) - (scaledHeight / 2) + 40;

        double[][] positions = {
                {tempx, tempy}, {tempx + 150, tempy}, {tempx + 300, tempy},
                {tempx, tempy + 100}, {tempx + 150 , tempy + 100}, {tempx + 300, tempy + 100},
                {tempx, tempy + 200}, {tempx + 150 , tempy + 200}, {tempx + 300, tempy + 200},
                {tempx, tempy + 300}
        };
        int k = -1;
        for (CardsType c : CardsType.values()) {
            Cart card = new Cart(c);

            ImageView cardView = card.getCardImageView();
            cardView.setFitWidth(128);
            cardView.setFitHeight(85.3);
            cardView.setLayoutX(positions[++k][0]);
            cardView.setLayoutY(positions[k][1]);


            // cardView.setOnMouseClicked(e -> {
            //     ImageView selectedView = card.getCardImageView();
            //     if (selectedCards.size() < 6  && !card.isAdded()) {
            //         selectedCards.add(card);
            //         card.setAdded(true);
            //         System.out.println("price cart " + card.getPrice());

                    

            //         selectedView.setFitWidth(142);
            //         selectedView.setFitHeight(95);
            //         selectedCardsBox.getChildren().add(selectedView);
            //     }else if (card.isAdded()) {
            //         selectedCards.remove(card);
            //         card.setAdded(false);
            //         selectedCardsBox.getChildren().remove(selectedView);
            //         System.out.println("removed card: ");
            //     }
            // });

            cardView.setOnMouseClicked(e -> {
                if (selectedCards.size() < 6 && !card.isAdded()) {
                selectedCards.add(card);
                card.setAdded(true);
                System.out.println("price cart " + card.getPrice());

                ImageView selectedView = new ImageView(card.getCardImageView().getImage());
                selectedView.setFitWidth(142);
                selectedView.setFitHeight(95);

             selectedView.setOnMouseClicked(ev -> {
              selectedCards.remove(card);
              card.setAdded(false);
              selectedCardsBox.getChildren().remove(selectedView);
              System.out.println("removed card: ");
         });

             selectedCardsBox.getChildren().add(selectedView);
            } else if (card.isAdded()) {
             selectedCards.remove(card);
                card.setAdded(false);

            selectedCardsBox.getChildren().removeIf(node ->
            node instanceof ImageView && ((ImageView) node).getImage().equals(card.getCardImageView().getImage())
            );

            System.out.println("removed card: ");
            }
        });



            cardSelectionPane.getChildren().add(cardView);
        }
        button.setOnAction(event -> {
            if(selectedCards.size() == 6) {
                Game(selectedCards , new SaveLoad(selectedCards));
                stage.close();
            }

        });
        loading.setOnAction(event -> {
            SaveLoad saveLoad;
            File dir = new File("saves");
            File file = new File(dir,  "ni.txt");
            System.out.println(file.getAbsolutePath());
            try (FileInputStream fileOut = new FileInputStream(file);
                 ObjectInputStream objectOut = new ObjectInputStream(fileOut)){

                saveLoad = (SaveLoad) objectOut.readObject();
                objectOut.close();
                fileOut.close();
                System.out.println("loading");
                Game(selectedCards , saveLoad);
                stage.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    private void Game(List<Cart> selectedCards , SaveLoad saveLoad){
        Stage primaryStage = new Stage();

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/Items/Background/Background_0.jpg")));
        background.setFitHeight(Sizes.SCREEN_HEIGHT);
        background.setFitWidth(Sizes.SCREEN_WIDTH);

        ImageView sunCounter = new ImageView(new Image(getClass().getResourceAsStream("/Plants/Sun/sunCounter.png")));
        sunCounter.setFitHeight(90);
        sunCounter.setFitWidth(250);

        Pane pane = new Pane( background );
        pane.getChildren().add(sunCounter);
        GameManager g = new GameManager(pane , saveLoad);

        Label sunLabel = new Label("SunPoints: 0");
        sunLabel.setFont(new Font("Arial", 60));
        sunLabel.setTextFill(Color.BLACK);
        sunLabel.setLayoutX(110);
        sunLabel.setLayoutY(7);
        pane.getChildren().add(sunLabel);


        pane.getChildren().addAll(
            GameManager.getPanePeas() ,
                GameManager.getPanePlantVsZombie()
        );

        Scene scene = new Scene(pane);

        GameManager.setSunPointLabel(sunLabel);

//        g.addPlant(p);
//        g.addPlant(p2);
//        System.out.println(p.getPlantView().getLayoutX());
//        g.addZombie(new ImpZombie(3));
//        g.addPlant(new Peashooter(7 , 4));
//        g.addPlant(new Peashooter(6,3));
//        g.addPlant(new SnowPea(1 , 4));
//        g.addPlant(new WallNut(4 , 4));
//        g.addPlant(new WallNut(7 , 0));
//        g.addPlant(new SunFlower(0 , 0));
//        g.addPlant(new SunFlower(4 , 1));
//        g.addPlant(new CherryBomb(7 , 2));
//        g.addPlant(new Jalapeno(1,0));
//        g.addPlant(new TallNut(4 , 0));
        g.spawnSun();

        gameUpdate = new AnimationTimer() {//game loop
            @Override
            public void handle(long now) {
                g.updateGame();
            }
        };
        gameUpdate.start();
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}