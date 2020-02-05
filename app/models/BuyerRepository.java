package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

//import com.fasterxml.jackson.databind.JsonNode; //del add

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPABuyerRepository.class)
public interface BuyerRepository {

    CompletionStage<Buyer> add(Buyer buyer);

    CompletionStage<Stream<Buyer>> list();

    CompletionStage<Integer> delete(String name);
    //CompletionStage<JsonNode> delete(); //del add

}
