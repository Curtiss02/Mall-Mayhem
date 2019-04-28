package Model;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.*;



public class Map {

    private List<BufferedImage> backgroundLayers;
    private List<BufferedImage> collisionLayers;
    private List<BufferedImage> topLayers;
    private List<TileSet> tilesets;
    private List<Rectangle> collisions;
    private List<Rectangle> nextLevelCollision;
    private List<Rectangle> prevLevelCollisions;
    private List<Rectangle> spawnAreaCollisions;

    private int tileWidth;
    private int mapWidth;
    private int mapHeight;

    private int[][] backgroundTileData;
    private int[][] collisionTileData;
    private int[][] topTileData;
    private int[][] nextLevelTileData;
    private int[][] prevLevelTileData;

    private int[][] spawnAreaTileData;

    public Map(String xmlFile) {
        backgroundLayers = new ArrayList<BufferedImage>();
        collisionLayers = new ArrayList<BufferedImage>();
        topLayers = new ArrayList<BufferedImage>();
        collisions = new ArrayList<Rectangle>();
        nextLevelCollision = new ArrayList<Rectangle>();
        prevLevelCollisions = new ArrayList<Rectangle>();
        spawnAreaCollisions = new ArrayList<Rectangle>();

        loadXML(xmlFile);
    }

    public void loadTileset() {

    }

    private void loadXML(String xmlFile) {


        try {

            //Read in map data XML file
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            Element map = (Element) doc.getElementsByTagName("map").item(0);

            tileWidth = Integer.valueOf(map.getAttribute("tilewidth"));
            mapWidth = Integer.valueOf(map.getAttribute("width"));
            mapHeight = Integer.valueOf(map.getAttribute("height"));



            NodeList tilesetNodes = doc.getElementsByTagName("tileset");
            tilesets = new ArrayList<TileSet>();


            for (int i = 0; i < tilesetNodes.getLength(); i++) {

                Node tempNode = tilesetNodes.item(0);
                Element tilesetNode = (Element) tilesetNodes.item(i);


                Element image = (Element) tilesetNode.getElementsByTagName("image").item(0);


                int imageWidth = Integer.valueOf(image.getAttribute("width"));
                int imageHeight = Integer.valueOf(image.getAttributes().getNamedItem("height").getNodeValue());
                String imageSrc = image.getAttribute("source");


                int firstGID = Integer.valueOf(tilesetNode.getAttribute("firstgid"));

                String tilesetName = tilesetNode.getAttribute("name");

                int tileWidth = Integer.valueOf(tilesetNode.getAttribute("tilewidth"));
                int tileHeight = Integer.valueOf(tilesetNode.getAttribute("tileheight"));


                TileSet currentTileset = new TileSet(imageWidth, imageHeight, firstGID, tilesetName, tileWidth, tileHeight, imageSrc);
                tilesets.add(currentTileset);
            }


            NodeList layers = map.getElementsByTagName("layer");

            for (int i = 0; i < layers.getLength(); i++) {
                Element currentLayer = (Element) layers.item(i);
                String layerName = currentLayer.getAttribute("name");

                BufferedImage currentImage = new BufferedImage(mapWidth * tileWidth, mapHeight * tileWidth, BufferedImage.TYPE_INT_ARGB);
                int[][] tileData;

                //Parse each of the tiles in the layer out of XML ad into an arrayList
                NodeList tiles = currentLayer.getElementsByTagName("tile");
                List<Integer> linearTiles = new ArrayList<Integer>();

                for (int tileNum = 0; tileNum < tiles.getLength(); tileNum++) {
                    //Get current tile ID and convert to integer provided th value is not null
                    //Add the current tile ID to a linear list
                    String gid = ((Element) (tiles.item(tileNum))).getAttribute("gid");
                    if (!gid.isEmpty()) {
                        linearTiles.add(tileNum, Integer.valueOf(gid));
                    } else {
                        linearTiles.add(tileNum, 0);
                    }

                }

                tileData = new int[mapWidth][];
                for (int tileX = 0; tileX < mapWidth; tileX++) {

                    tileData[tileX] = new int[mapHeight];



                    for (int tileY = 0; tileY < mapHeight; tileY++) {
                        tileData[tileX][tileY] = linearTiles.get((tileX + (tileY * mapWidth)));

                    }


                }

                //Draw the fucking image please god i hope this works or im gonna cry

                Graphics gIMG = currentImage.getGraphics();
                for (int tileX = 0; tileX < mapWidth; tileX++) {

                    for (int tileY = 0; tileY < mapHeight; tileY++) {

                        int GID = tileData[tileX][tileY];

                        //NO need to draw anything if the current tile is blank
                        if (GID == 0) continue;
                        TileSet currentTileset = tilesets.get(0);
                        for (TileSet testSet : tilesets) {
                            if (GID >= testSet.getFirstGID() - 1 ) {
                                currentTileset = testSet;

                            }
                        }
                        GID -= currentTileset.getFirstGID() - 1;
                        int destX = tileX * tileWidth;
                        int destY = tileY * tileWidth;
                        //Draw the tile

                        gIMG.drawImage(currentTileset.getTile(GID), destX, destY, null);


                    }
                }

                switch (layerName) {
                    case "Background":
                        backgroundLayers.add(currentImage);
                        backgroundTileData = tileData;
                        break;
                    case "Collision":
                        collisionLayers.add(currentImage);
                        collisionTileData = tileData;
                        createCollisionData(collisions, collisionTileData);
                        break;
                    case "Top":
                        topLayers.add(currentImage);
                        topTileData = tileData;
                        break;
                    case "NextLevel":
                        nextLevelTileData = tileData;
                        createCollisionData(nextLevelCollision, nextLevelTileData);
                        break;
                    case "PrevLevel":
                        prevLevelTileData = tileData;
                        createCollisionData(prevLevelCollisions, prevLevelTileData);
                        break;
                    case "SpawnArea":
                        spawnAreaTileData = tileData;
                        createCollisionData(spawnAreaCollisions, spawnAreaTileData);
                    default:
                        break;
                }


                gIMG.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void buildLayer(BufferedImage layerImage, int[][] layerData){

    }

    private void createCollisionData(List<Rectangle> collisionList, int[][] tileData){

        for(int tileX = 0; tileX < mapWidth; tileX++){
            for(int tileY = 0; tileY < mapHeight; tileY++){
                int GID = tileData[tileX][tileY];
                if(GID > 0){

                    int destX = tileX * tileWidth;
                    int destY = tileY * tileWidth;
                    collisionList.add(new Rectangle(destX, destY, tileWidth, tileWidth));
                }

            }
        }
    }

    public void drawBackground(Graphics2D g){
        for(BufferedImage backgroundLayer : backgroundLayers){
            g.drawImage(backgroundLayer, 0 , 0, null);
        }
        for(BufferedImage collisionLayer : collisionLayers){
            g.drawImage(collisionLayer, 0, 0, null);
        }

    }
    public void drawTop(Graphics2D g){
        for(BufferedImage topLayer : topLayers){
            g.drawImage(topLayer, 0, 0, null);
        }

    }

    public List<Rectangle> getCollisions() {
        return collisions;
    }

    public List<Rectangle> getNextLevelCollision() {
        return nextLevelCollision;
    }

    public List<Rectangle> getPrevLevelCollisions(){
        return prevLevelCollisions;
    }

    public List<Rectangle> getSpawnAreaCollisions() {
        return spawnAreaCollisions;
    }
}



