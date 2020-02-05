package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

//import com.fasterxml.jackson.databind.JsonNode; //del add

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPABidRepository.class)
public interface BidRepository {

    CompletionStage<Bid> add(Bid bid);

    CompletionStage<Stream<Bid>> list();

    CompletionStage<Integer> delete(Long bidid);
    //CompletionStage<JsonNode> delete(); //del add

}
