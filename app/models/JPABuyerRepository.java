package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class JPABuyerRepository implements BuyerRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPABuyerRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Buyer> add(Buyer buyer) {
        return supplyAsync(() -> wrap(em -> insert(em, buyer)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Buyer>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Integer> delete(String name) {
        return supplyAsync(() -> wrap(em -> delete(em, name)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Buyer insert(EntityManager em, Buyer buyer) {
        em.persist(buyer);
        return buyer;
    }

    private Stream<Buyer> list(EntityManager em) {
        List<Buyer> buyers = em.createQuery("select p from Buyer p", Buyer.class).getResultList();
        return buyers.stream();
    }

    private Integer delete(EntityManager em, String name) {
        Buyer buyer = em.createQuery("select p from Buyer p where p.name=:name", Buyer.class).setParameter("name",name).getSingleResult();
        em.remove(buyer);

        return 1;
    }
}
