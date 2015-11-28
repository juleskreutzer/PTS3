/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import java.io.File;
import javafx.geometry.NodeOrientation;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Jasper Rouwhorst
 */
public class PathImage extends ObjectImage {
    
    public Path path;

    public PathImage(Path path) {
        super(path);
        this.path = path;
        
        File file = null;
        file = new File("src/hackattackfx/resources/Path.png");
        Image image = null;

        switch(path.getDirection()){
            case Up:
                image = new Image(file.toURI().toString(), 20, path.getLength()+20, false, true);
                // 25 = width of the path.png / 2
                setX(path.getEnd().x - (image.getWidth() / 2));
                setY(path.getEnd().y - (image.getWidth() / 2));
                break;
            case Down:
                image = new Image(file.toURI().toString(), 20, path.getLength(), false, true);
                // 25 = width of the path.png / 2
                setX(path.getStart().x - (image.getWidth() / 2));
                setY(path.getStart().y - (image.getWidth() / 2));
                break;
            case Right:
                image = new Image(file.toURI().toString(), path.getLength(), 20, false, true);
                // 25 = width of the path.png / 2
                setX(path.getStart().x - (image.getHeight() / 2));
                setY(path.getStart().y - (image.getHeight() / 2));
                break;
            case Left:
                image = new Image(file.toURI().toString(), path.getLength()+20, 20, false, true);
                // 25 = width of the path.png / 2
                setX(path.getEnd().x - (image.getHeight() / 2));
                setY(path.getEnd().y - (image.getHeight() / 2));
                break;
        }
        
        this.setFitWidth(image.getWidth());
        this.setFitHeight(image.getHeight());
        this.setImage(image);
    }
    
}
