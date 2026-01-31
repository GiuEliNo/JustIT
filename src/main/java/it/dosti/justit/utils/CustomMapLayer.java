package it.dosti.justit.utils;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class CustomMapLayer extends MapLayer {




    private final MapPoint point;
    private final Node marker;

    public CustomMapLayer(MapPoint point){
        this.point= point;

        SVGPath pin = new SVGPath();
        pin.setContent("M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z");
        pin.setFill(Color.RED);
        pin.setStroke(Color.DARKRED);
        this.marker = pin;

        this.getChildren().add(marker);
    }


    @Override
    protected void layoutLayer() {

        Point2D mapPoint2d = this.getMapPoint(point.getLatitude(), point.getLongitude());

        marker.setTranslateX(mapPoint2d.getX());
        marker.setTranslateY(mapPoint2d.getY());
        }
}
