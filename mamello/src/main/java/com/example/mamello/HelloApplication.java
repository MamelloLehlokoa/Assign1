package com.example.mamello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    private static final String[] imagePaths = {
            "/img1.jpg",
            "/img2.jpg",
            "/img5.jpg",

    };

    private ImageView fullSizeImageView;
    private int currentIndex = -1;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = createThumbnailGrid();
        BorderPane root = new BorderPane(gridPane);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/gallery.css").toExternalForm());

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createThumbnailGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < imagePaths.length; i++) {
            ImageView thumbnailImageView = createThumbnailImageView(imagePaths[i]);
            int row = i / 3;
            int col = i % 3;
            gridPane.add(thumbnailImageView, col, row);
        }

        return gridPane;
    }

    private ImageView createThumbnailImageView(String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("thumbnail-image-view");
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setOnMouseClicked(event -> showFullSizeImage(imagePath));
        return imageView;
    }

    private void showFullSizeImage(String imagePath) {
        Stage fullSizeStage = new Stage();
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        fullSizeImageView = new ImageView(image);

        Button backButton = new Button("Back to Thumbnails");
        backButton.setOnAction(event -> fullSizeStage.close());

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> navigate(-1));

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> navigate(1));

        StackPane.setAlignment(backButton, javafx.geometry.Pos.TOP_LEFT);
        StackPane.setAlignment(prevButton, javafx.geometry.Pos.BOTTOM_LEFT);
        StackPane.setAlignment(nextButton, javafx.geometry.Pos.BOTTOM_RIGHT);

        StackPane stackPane = new StackPane(fullSizeImageView, backButton, prevButton, nextButton);
        stackPane.getStyleClass().add("full-size-pane");

        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/styles/gallery.css").toExternalForm());

        fullSizeStage.setScene(scene);
        fullSizeStage.show();
    }

    private void navigate(int step) {
        currentIndex += step;
        if (currentIndex < 0) {
            currentIndex = imagePaths.length - 1;
        } else if (currentIndex >= imagePaths.length) {
            currentIndex = 0;
        }
        showFullSizeImage(imagePaths[currentIndex]);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


