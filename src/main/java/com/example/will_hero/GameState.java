package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameState implements Serializable {
    private transient final Game game;
    Random rand = new Random();

    private ArrayList<GameObjects> gameObjects = new ArrayList<>();
    private ArrayList<Enemies> enemies = new ArrayList<>();
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<TNT> tnts = new ArrayList<>();
    private ArrayList<Chests> chests = new ArrayList<>();
    private ArrayList<Weapons> weapons = new ArrayList<>();
    private int steps = 0;
    private int coins = 0; // making this static cuz it also needs to be used in Chests.java
    protected boolean hasEnded;
    protected boolean reachedEnd;
    protected boolean hasRevived;
    protected boolean hasWon;
    private int lastAdd = 0;
    private int heroStart;

    private long lastClicked = 0;
    public double toMoveFrameX = 0;


    //FXML Objects
    protected transient static AnchorPane gamePane;
    private Hero hero;
    private transient Text scoreBoard;
    private transient Text coinBoard;

    public GameState(Game game) {
        this.game = game;
    }

    public int getCoins(){
        return coins;
    }
    public void setCoins(int c){
        coins=c;
    }
    public void addCoins(int c){
        coins+=c;
    }
    public void addALevel(){
        Island island = addIsland();
        Bounds islandBounds = getBoundswrtPane(island.getPlatformNode());
        int chance = rand.nextInt(10);
        if (chance == 9) {
            Chests chest = addChests();
            chest.node.setLayoutX(islandBounds.getCenterX() - (chest.WIDTH/2));
            chest.node.setLayoutY(islandBounds.getMinY() - chest.HEIGHT);
        }
        else if (chance == 8) {
            TNT t = addTNT();
            t.node.setLayoutX(islandBounds.getCenterX() - (t.WIDTH/2));
            t.node.setLayoutY(islandBounds.getMinY() - t.HEIGHT);
            //TNTS
        }
        else {
            int maxEnemies = (int) (island.WIDTH / 100);
            int count = rand.nextInt(Math.min(maxEnemies, 3) + 1);
            double gap = island.WIDTH / count;
            for (int i = 0; i < count; i++) {
                Enemies enemy = addEnemy();
                enemy.node.setLayoutY(islandBounds.getMinY() - enemy.HEIGHT);
                enemy.node.setLayoutX(islandBounds.getMinX() + rand.nextInt(50) + (100 * i)  + 10);
            }
        }
    }

    private void addBossLevel(){
        Node bossNode = imageViewLoader(Boss.path);
        Boss boss = new Boss(bossNode);
        gamePane.getChildren().add(bossNode);
        enemies.add(boss);
        addGameObject(boss);
        Island island = Island.createIsland("AssetFXMLFiles/BossIsland.fxml");
        addIsland(island);
        island.setPlatformNode(island.node);
        island.getNode().setLayoutX(island.getNode().getLayoutX() + 50);
        island.getNode().setLayoutY(300);
        Bounds islandBounds = getBoundswrtPane(island.node);
        boss.node.setLayoutX(islandBounds.getMinX() + 300);
        boss.node.setLayoutY(islandBounds.getMinY() - boss.HEIGHT);
    }

    public void setHeroStart(int pos){
        heroStart = pos;
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

    public TNT addTNT(){
        TNT t = TNT.addTnt();
        gamePane.getChildren().add(t.node);
        gameObjects.add(t);
        tnts.add(t);
        return t;
    }


    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public Hero getHero() {
        return this.hero;
    }

    public Enemies addEnemy(){
        if (rand.nextBoolean()) {
            ImageView enemyNode = imageViewLoader(RedOrc.path);
            RedOrc e = new RedOrc(enemyNode);
            gamePane.getChildren().add(enemyNode);
            enemies.add(e);
            addGameObject(e);
            return e;
        }
        else{
            ImageView enemyNode = imageViewLoader(GreenOrc.path);
            GreenOrc e = new GreenOrc(enemyNode);
            gamePane.getChildren().add(enemyNode);
            enemies.add(e);
            addGameObject(e);
            return e;
        }
    }

    // method which runs for each frame in animation timer
    public void updateState(long now) {
//        System.out.println(now);
        updateScoreBoard();
        updateCoinBoard();
        gamePane.setOnMouseClicked(event -> {
            steps += 1;
            hero.setToMoveX(hero.getToMoveX() + 120);
            toMoveFrameX += Hero.forwardX;
            this.lastClicked = now;
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

        if(checkCollisionWithTNT()){
            System.out.println("Hero is killed by TNT Explosion");


            // change in pic
            // fade transition
            // Wait for 2 sec--> check if hero is in radius--> if yes, hero dies.
            //hero.getNode().setVisible(false);
            //visibility of the hero is set to false on collision w TNTs.
        }

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

//        if ((hero.node.getLayoutX() > islands.get(1).getNode().getLayoutX() + 1000) && (hero.node.getLayoutX() + 2000 < islands.get(islands.size() - 1).getNode().getLayoutX())) {
//            System.out.println("true");
//            addALevel();
//        }
        Bounds firstBounds = getBoundswrtPane(islands.get(1).getPlatformNode());
        Bounds heroBounds = getBoundswrtPane(hero.getNode());
        Bounds lastBounds = getBoundswrtPane(islands.get(islands.size() - 1).getPlatformNode());
        if (!reachedEnd && (heroBounds.getMinX() - 500 > firstBounds.getMinX() && heroBounds.getMinX() + 500 < lastBounds.getMinX())) {
            System.out.println("added level " + steps);
            if (steps >= 90) {
                reachedEnd = true;
                addBossLevel();
            }
            else {
                addALevel();
            }
        }

        updateHero();
        //set hero to front
        hero.node.toFront();
        if (hasEnded){
            if (hasWon) {
                winGame();
            }
            else {
                endGame();
            }
        }
    }

    public void updateHero(){
        if (hero.node.getLayoutY() > 500) {
            hasEnded = true;
        }
    }

    private void updateEnemies(){
        ArrayList<Enemies> toRemove = new ArrayList<>();
        for (Enemies e: enemies) {
            if (e.node.getLayoutY() > 500) {
                e.isKilled = true;
            }
            if (e.isKilled){
                coins += 5;
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
                    if (e instanceof Boss) {
                        Boss boss = (Boss) e;
                        boss.hit();
                        if (boss.isKilled) {
                            hasWon = true;
                            hasEnded = true;
                        }
                    }
                    else{
                        e.killed();
                    }
                }
            }
        }
    }

    public void endGame(){
        game.loseGame();
    }

    public void winGame(){
        game.winGame();
    }

    private void checkCollisionWithEnemies(){
        for (Enemies e : enemies){
            if (e.isColliding(hero)){
                hasEnded = true;
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
                k.setOpened();

                ImageView chestNode = (ImageView) k.getNode();
                //Image image = new Image("/com/example/will_hero/assets/ChestOpen.png");
                //Image image = new Image("src/main/resources/com/example/will_hero/assets/ChestOpen.png");
                k.open(this);
                chestNode.setImage(Chests.openChest.getImage());
                //GameState.coins=GameState.coins +10;
                //chestNode.getImage()

                return true;

            }
        }
        return false;
    }

    private boolean checkCollisionWithTNT(){
        for(TNT t: tnts){
            if(t.isColliding(hero)){
                ImageView tntNode = (ImageView) t.getNode();

                tntNode.setImage(TNT.explodingTNT.getImage());
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
        if (islands.size() > 10) {

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
