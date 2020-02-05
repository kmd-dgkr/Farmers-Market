package controllers;

import models.Crop;
import models.CropRepository;
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
public class CropController extends Controller {

    private final FormFactory formFactory;
    private final CropRepository cropRepository;
    private final HttpExecutionContext ec;

    @Inject
    public CropController(FormFactory formFactory, CropRepository cropRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.cropRepository = cropRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.cropindex.render());
    }

    public CompletionStage<Result> addCrop() {
        Crop crop = formFactory.form(Crop.class).bindFromRequest().get();
        return cropRepository.add(crop).thenApplyAsync(p -> {
            return redirect(routes.CropController.index());
        }, ec.current());
    }

    public CompletionStage<Result> addCropJson() {
        JsonNode js = request().body().asJson();
        Crop crop = fromJson(js, Crop.class);
        return cropRepository.add(crop).thenApplyAsync(p -> {
            //return redirect(routes.PersonController.index());
            return ok("Insert successful.");
        }, ec.current());
    }

    public CompletionStage<Result> getCrops() {
        return cropRepository.list().thenApplyAsync(cropStream -> {
            return ok(toJson(cropStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> deleteCrop(String name) {
        Logger.warn(name);
        return cropRepository.delete(name).thenApplyAsync(cropStream -> {
            return ok("Delete successful");
        }, ec.current());
    }

}
