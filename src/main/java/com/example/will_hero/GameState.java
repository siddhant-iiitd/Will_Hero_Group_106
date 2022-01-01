package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameState {
    private final Game game;

    Random rand = new Random();

    private ArrayList<GameObjects> gameObjects = new ArrayList<>();
    private ArrayList<Enemies> enemies = new ArrayList<>();
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<TNT> tnts = new ArrayList<>();
    private ArrayList<Chests> chests = new ArrayList<>();
    private ArrayList<Weapons> weapons = new ArrayList<>();
    private int steps = 0;
    private int coins = 0;
    private boolean hasEnded;
    private boolean hasRevived;

    private long lastClicked = 0;
    public double toMoveFrameX = 0;


    //FXML Objects
    protected static AnchorPane gamePane;
    private Hero hero;
    private Text scoreBoard;
    private Text coinBoard;

    public GameState(Game game) {
        this.game = game;
    }

    public void addALevel(){
        Island island = addIsland();
        Bounds islandBounds = getBoundswrtPane(island.getPlatformNode());
        int chance = rand.nextInt(10);
        if (chance == 9) {
            Chests chest = addChests();
            chest.node.setLayoutX(islandBounds.getCenterX() - (islandBounds.getWidth()/2));
            chest.node.setLayoutY(islandBounds.getMinY() - chest.HEIGHT);
        }
        if (chance == 9) {
            //TNTS
        }
        else {
            int maxEnemies = (int) (island.WIDTH / 40);
            int count = Math.min(rand.nextInt(maxEnemies), 3);
            double gap = island.WIDTH / count;
            for (int i = 0; i < count; i++) {
                Enemies enemy = addEnemy();
                enemy.node.setLayoutY(islandBounds.getMinY() - enemy.HEIGHT);
                enemy.node.setLayoutX(islandBounds.getMinX() + 10 + gap*i);
            }
        }
    }

    public Hero addHero(){
        ImageView heroNode = imageViewLoader(Hero.path);
        Hero h = new Hero(heroNode, this.game);
        gamePane.getChildren().add(heroNode);
        gameObjects.add(h);
        hero = h;
        return h;
    }

    public Chests addChests(){
        Chests c = Chests.addChests(); //getting the chest
        gamePane.getChildren().add(c.node); // gamepane --> nodes
        gameObjects.add(c);
        chests.add(c);
        return c;
    }


    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public Hero getHero() {
        return this.hero;
    }

    public Enemies addEnemy(){
        ImageView enemyNode = imageViewLoader(RedOrc.path);
        RedOrc e = new RedOrc(enemyNode);
        gamePane.getChildren().add(enemyNode);
        enemies.add(e);
        addGameObject(e);
        return e;
    }

    // method which runs for each frame in animation timer
    public void updateState(long now) {
//        System.out.println(now);
        updateScoreBoard();
        updateCoinBoard();

        gamePane.setOnMouseClicked(event -> {
            hero.setToMoveX(hero.getToMoveX() + 120);
            toMoveFrameX += Hero.forwardX;
            this.lastClicked = now;
            this.steps += 1;
            Weapons newWeapon = hero.getCurrWeapon();
            if (newWeapon != null) {
                Node node = newWeapon.getNode();
                node.setLayoutX(hero.node.getLayoutX());
                node.setLayoutY(hero.node.getLayoutY());
                gamePane.getChildren().add(node);
                weapons.add(newWeapon);
                gameObjects.add(newWeapon);
            }
        });

        hero.onIsland = false;
        if (checkCollisionWithIslands()){
            hero.jump(now);
        }


        checkCollisionWithChests();
            //chest node ki image change karni hai to open chest
            //ImageView openChestNode = GameState.imageViewLoader("/com/example/will_hero/assets/ChestOpen.png");
//            Image image = new Image("/com/example/will_hero/assets/ChestOpen.png");
//
//
//            openChestNode.setImage(image);


        checkCollisionWithEnemies();
        checkWeaponAttack();
        moveWeapons();
        moveFrameBack(now, hero.getSpeedX());
        hero.moveFrameWise();

        //movement of the frame
        for (Enemies e : enemies){
            e.onIsland = false;
            e.node.toFront();
        }

        checkEnemyCollisionWithIslands(now);
        for (Enemies e : enemies){
            e.moveFrameWise();
        }
        updateWeapons();
        updateEnemies();

        //set hero to front
        hero.node.toFront();
    }
    private void updateEnemies(){
        ArrayList<Enemies> toRemove = new ArrayList<>();
        for (Enemies e: enemies) {
            if (e.isKilled){
                toRemove.add(e);
            }
        }
        for (Enemies e: toRemove){
            removeObject(e);
        }
    }
    private void updateWeapons(){
        ArrayList<Weapons> toRemove = new ArrayList<>();
        for (Weapons w:weapons) {
            if (w.toMoveX <= 0 || w.killedSomeone) {
                toRemove.add(w);
            }
        }
        for (Weapons w1 : toRemove) {
            removeObject(w1);
        }
    }
    private void moveWeapons(){
        for (Weapons w: weapons){
            w.moveByFrame();
        }
    }
    private void checkWeaponAttack(){
        for (Weapons w: weapons){
            for (Enemies e : enemies) {
                if (w.intersectsWithEnemy(e)){
                    e.killed();
                }
            }
        }
    }
    public void endGame(){
        game.pauseGame();
    }
    private void checkCollisionWithEnemies(){
        for (Enemies e : enemies){
            if (e.isColliding(hero)){
                endGame();
            }
        }
    }

    public void updateCoinBoard(){
        coinBoard.setText(String.valueOf(coins));
    }

    public void updateScoreBoard(){
        scoreBoard.setText(String.valueOf(steps));
    }

    public void moveFrameBack(long now, double x){
        if (toMoveFrameX < 0) {
            return;
        }
        if (!(toMoveFrameX > Hero.forwardX || (now - lastClicked > 150000000))) {
            return;
        }
        double toMove = Math.min(x, toMoveFrameX);
        toMoveFrameX -= toMove;
        for (GameObjects object : gameObjects) {
            Node node = object.getNode();
            node.setLayoutX(node.getLayoutX() - toMove);
        }
    }

    private void checkEnemyCollisionWithIslands(long now){
        for (Island i : islands) {
            for (Enemies e : enemies) {

                if (i.collidingEnemy(e)) {
                    e.jump(now);
                }
            }
        }
    }

    //checking if hero is on any of the islands
    private boolean checkCollisionWithIslands(){
        for(Island i : islands) {
            if (i.isColliding(hero)){
                return true;
            }
        }
        return false;
    }

    //checking if hero has opened any of the chests
    private boolean checkCollisionWithChests(){
        for(Chests k: chests){
            if(k.isColliding(hero)){
                ImageView chestNode = (ImageView) k.getNode();

                //Image image = new Image("/com/example/will_hero/assets/ChestOpen.png");
                //Image image = new Image("src/main/resources/com/example/will_hero/assets/ChestOpen.png");
                chestNode.setImage(Chests.openChest.getImage());
                //chestNode.getImage()

                return true;

            }
        }
        return false;
    }

    // helper function to get the bounds of any node with respect to the scene pane
    public static Bounds getBoundswrtPane(Node node) {
        return gamePane.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
    }

    //Helper function to add the object to the gameObjects list too. Must call this function whenever
    //adding any object such as island to the specific lists
    private void addGameObject(GameObjects object){
        gameObjects.add(object);
    }

    //function to add a given island to the islands list
    public Island addIsland(Island island) {
        island.getNode().setLayoutY(170 - rand.nextInt(30));
        if (islands.size() < 1) {
            island.getNode().setLayoutX(5);
        }
        else {
            Island prevIsland = islands.get(islands.size() -1 );
            island.getNode().setLayoutX(prevIsland.getNode().getLayoutX() + rand.nextInt(100) + 75 + prevIsland.WIDTH);
        }
        islands.add(island);

        gamePane.getChildren().add(island.getNode());
        if (islands.size() > 7) {

            Island removedIsland = islands.get(0);
            this.removeObject(removedIsland);
        }
        addGameObject(island);
        return island;
    }

    //function to add random new island to islands list
    public Island addIsland(){
        return addIsland(Island.createIsland());
    }

    //function to remove a gameObject from all lists containing that object
    public void removeObject(GameObjects object){
        gamePane.getChildren().remove(object.getNode());
        if (object instanceof Enemies) {
            enemies.remove(object);
        }
        else if (object instanceof Island) {
            islands.remove(object);
        }
        else if (object instanceof Chests) {
            chests.remove(object);
        }
        else if (object instanceof TNT) {
            tnts.remove(object);
        }
        else if (object instanceof Weapons) {
            weapons.remove(object);
        }

        gameObjects.remove(object);
    }

    //function for moving all objects in the game backwards by x in time t
    public void moveSceneBackwards(double x, double t){
        for (GameObjects object : gameObjects) {
            Node node = object.getNode();
            TranslateTransition transition = new TranslateTransition(Duration.millis(t), node);
            transition.setByX(-x);
            transition.play();
        }
    }


    //function for initial setup of some required static gui component nodes
    public void setupFXMLNodes(AnchorPane anchorPane, Text scoreBoard, Text coinBoard) {

        this.scoreBoard = scoreBoard;
        this.coinBoard = coinBoard;
        this.gamePane = anchorPane;
    }

    public void disableGamePane(){
        gamePane.setOnMouseClicked(event -> {
            //nothing
        });
    }
    //helper static function to load a group from fxml file with given path
    public static Group groupLoader(String path) {
        AnchorPane root = null;
        try {
            root = FXMLLoader.<AnchorPane>load(WillHeroApplication.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Group group = (Group) root.getChildren().get(0);
        return group;
    }

    //helper function to load imageview from fxml file with given path
    public static ImageView imageViewLoader(String path) {
        AnchorPane root = null;
        try {
            root = FXMLLoader.<AnchorPane>load(WillHeroApplication.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView image = (ImageView) root.getChildren().get(0);
        return image;
    }
}
