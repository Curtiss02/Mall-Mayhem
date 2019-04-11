package Model;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.*;

public class Level {

    private BufferedImage backgroundLayer;
    private BufferedImage collisionLayer;
    private BufferedImage topLayer;
    private List<TileSet> tilesets;
    private int tileWidth;
    private int levelWidth;
    private int levelHeight;


    public Level(){

    }

    public void loadTileset(){

    }
    public void loadXML(String xmlFile) {

/*
        if(tileSet == null) {
            System.out.println("No Tileset loaded");
        }
        else{
*/
        try {

                //Read in map data XML file
                File inputFile = new File(xmlFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                Element map = (Element)doc.getElementsByTagName("map").item(0);

                tileWidth = Integer.valueOf(map.getAttribute("tilewidth"));
                levelWidth = Integer.valueOf(map.getAttribute("width"));
                levelHeight = Integer.valueOf(map.getAttribute("height"));

                NodeList tilesetNodes = doc.getElementsByTagName("tileset");
                tilesets = new ArrayList<TileSet>();


                for(int i = 0; i < tilesetNodes.getLength(); i++){
                    System.out.println("Accessing" + i);
                    Node tempNode = tilesetNodes.item(0);
                    Element tilesetNode = (Element)tilesetNodes.item(i);



                    Element image =  (Element)tilesetNode.getElementsByTagName("image").item(0);




                    int imageWidth = Integer.valueOf(image.getAttribute("width"));
                    int imageHeight = Integer.valueOf(image.getAttributes().getNamedItem("height").getNodeValue());





                    int firstGID = Integer.valueOf(tilesetNode.getAttribute("firstgid"));
                    String tilesetName = tilesetNode.getAttribute("name");
                    int tileWidth = Integer.valueOf(tilesetNode.getAttribute("tilewidth"));
                    int tileHeight = Integer.valueOf(tilesetNode.getAttribute("tileheight"));
                    String imageSrc = tilesetNode.getAttribute("source");

                    TileSet currentTileset = new TileSet(imageWidth, imageHeight, firstGID, tilesetName, tileWidth, tileHeight, imageSrc);
                    tilesets.add(currentTileset);
                }

                /*

                   xml = new XML(e.target.data);
                   mapWidth = xml.attribute("width");
                   mapHeight = xml.attribute("height");
                   tileWidth = xml.attribute("tilewidth");
                   tileHeight = xml.attribute("tileheight");
                   var xmlCounter:uint = 0;

                   for each (var tileset:XML in xml.tileset) {
                      var imageWidth:uint = xml.tileset.image.attribute("width")[xmlCounter];
                      var imageHeight:uint = xml.tileset.image.attribute("height")[xmlCounter];
                      var firstGid:uint = xml.tileset.attribute("firstgid")[xmlCounter];
                      var tilesetName:String = xml.tileset.attribute("name")[xmlCounter];
                      var tilesetTileWidth:uint = xml.tileset.attribute("tilewidth")[xmlCounter];
                      var tilesetTileHeight:uint = xml.tileset.attribute("tileheight")[xmlCounter];
                      var tilesetImagePath:String = xml.tileset.image.attribute("source")[xmlCounter];
                      tileSets.push(new TileSet(firstGid, tilesetName, tilesetTileWidth, tilesetTileHeight, tilesetImagePath, imageWidth, imageHeight));
                      xmlCounter++;
                   }
                   totalTileSets = xmlCounter;
                 */


            } catch (Exception e) {
                e.printStackTrace();
            }
            }
    //}

}



