package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

//import com.fasterxml.jackson.databind.JsonNode; //del add

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPersonRepository.class)
public interface PersonRepository {

    CompletionStage<Person> add(Person person);

    CompletionStage<Stream<Person>> list();

    CompletionStage<Integer> delete(String name);
    //CompletionStage<JsonNode> delete(); //del add

}
