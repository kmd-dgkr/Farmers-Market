package controllers;

import models.Bid;
import models.BidRepository;
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
public class BidController extends Controller {

    private final FormFactory formFactory;
    private final BidRepository bidRepository;
    private final HttpExecutionContext ec;

    @Inject
    public BidController(FormFactory formFactory, BidRepository bidRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.bidRepository = bidRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.bidindex.render());
    }

    public CompletionStage<Result> addBid() {
        Bid bid = formFactory.form(Bid.class).bindFromRequest().get();
        return bidRepository.add(bid).thenApplyAsync(p -> {
            return redirect(routes.BidController.index());
        }, ec.current());
    }

    public CompletionStage<Result> addBidJson() {
        JsonNode js = request().body().asJson();
        Bid bid = fromJson(js, Bid.class);
        return bidRepository.add(bid).thenApplyAsync(p -> {
            //return redirect(routes.PersonController.index());
            return ok("Insert successful.");
        }, ec.current());
    }

    public CompletionStage<Result> getBids() {
        return bidRepository.list().thenApplyAsync(bidStream -> {
            return ok(toJson(bidStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> deleteBid(Long bidid) {
        Logger.warn(bidid.toString());
        return bidRepository.delete(bidid).thenApplyAsync(bidStream -> {
            return ok("Delete successful");
        }, ec.current());
    }

}
