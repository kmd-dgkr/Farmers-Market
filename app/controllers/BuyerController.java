package controllers;

import models.Buyer;
import models.BuyerRepository;
import play.Logger;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;
import static play.libs.Json.fromJson;


import java.util.*;
/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link play.libs.concurrent.HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class BuyerController extends Controller {

    private final FormFactory formFactory;
    private final BuyerRepository buyerRepository;
    private final HttpExecutionContext ec;

    @Inject
    public BuyerController(FormFactory formFactory, BuyerRepository buyerRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.buyerRepository = buyerRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.buyerindex.render());
    }

    public CompletionStage<Result> addBuyer() {
        Buyer buyer = formFactory.form(Buyer.class).bindFromRequest().get();
        return buyerRepository.add(buyer).thenApplyAsync(p -> {
            return redirect(routes.BuyerController.index());
        }, ec.current());
    }

    public CompletionStage<Result> addBuyerJson() {
        JsonNode js = request().body().asJson();
        Buyer buyer = fromJson(js, Buyer.class);
        return buyerRepository.add(buyer).thenApplyAsync(p -> {
            //return redirect(routes.PersonController.index());
            return ok("Insert successful.");
        }, ec.current());
    }

    public CompletionStage<Result> getBuyers() {
        return buyerRepository.list().thenApplyAsync(buyerStream -> {
            return ok(toJson(buyerStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> deleteBuyer(String name) {
        Logger.warn(name);
        return buyerRepository.delete(name).thenApplyAsync(buyerStream -> {
            return ok("Delete successful");
        }, ec.current());
    }

}
