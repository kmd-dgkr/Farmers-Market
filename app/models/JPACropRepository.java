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
public class JPACropRepository implements CropRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPACropRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Crop> add(Crop crop) {
        return supplyAsync(() -> wrap(em -> insert(em, crop)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Crop>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Integer> delete(String name) {
        return supplyAsync(() -> wrap(em -> delete(em, name)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Crop insert(EntityManager em, Crop crop) {
        em.persist(crop);
        return crop;
    }

    private Stream<Crop> list(EntityManager em) {
        List<Crop> crops = em.createQuery("select p from Crop p", Crop.class).getResultList();
        return crops.stream();
    }

    private Integer delete(EntityManager em, String name) {
        Crop crop = em.createQuery("select p from Crop p where p.name=:name", Crop.class).setParameter("name",name).getSingleResult();
        em.remove(crop);

        return 1;
    }
}
