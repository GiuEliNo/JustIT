package it.dosti.justit.ui.navigation;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class CustomMapLayer extends MapLayer {

    private final ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();

    public CustomMapLayer() {}

    public void addPoint(MapPoint point, Node icon) {
        points.add(new Pair<>(point, icon));
        this.getChildren().add(icon);
        this.markDirty();
    }

    @Override
    protected void layoutLayer() {
        for(Pair<MapPoint, Node> candidate : points) {
            MapPoint point = new MapPoint(41.865480, 12.547843);
            Node icon = new Circle(5, Color.RED);
            Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
            icon.setLayoutX(mapPoint.getX());
            icon.setLayoutY(mapPoint.getY());
            icon.setVisible(true);
        }
    }

}
