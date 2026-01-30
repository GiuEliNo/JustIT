package it.dosti.justit.DAO;

import it.dosti.justit.model.Coordinates;

import java.util.concurrent.CompletableFuture;

public interface CoordinatesDAO {


    CompletableFuture<Coordinates> getCoordinates(String address);

}
