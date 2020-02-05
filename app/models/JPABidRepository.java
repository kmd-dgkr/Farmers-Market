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
public class JPABidRepository implements BidRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPABidRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Bid> add(Bid bid) {
        return supplyAsync(() -> wrap(em -> insert(em, bid)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Bid>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Integer> delete(Long bidid) {
        return supplyAsync(() -> wrap(em -> delete(em, bidid)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Bid insert(EntityManager em, Bid bid) {
        em.persist(bid);
        return bid;
    }

    private Stream<Bid> list(EntityManager em) {
        List<Bid> bids = em.createQuery("select p from Bid p", Bid.class).getResultList();
        return bids.stream();
    }

    private Integer delete(EntityManager em, Long bidid) {
        Bid bid = em.createQuery("select p from Bid p where p.bidid=:bidid", Bid.class).setParameter("bidid",bidid).getSingleResult();
        em.remove(bid);

        return 1;
    }
}
