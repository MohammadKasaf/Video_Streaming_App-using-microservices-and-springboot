package com.movieCatalog;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class MovieInfoController {

    @Autowired
    private MovieInfoRepository repository;

    //save video
    @PostMapping("/movie-info/saveMovie")
    public ResponseEntity<?> saveVideo(@RequestBody MovieInfo movie) {
        try {
            // Save the movie and return a 201 CREATED response with the saved entity
            MovieInfo savedMovie = repository.save(movie);
            return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return a 500 INTERNAL SERVER ERROR response with the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //save All movies
    @PostMapping("/movie-info/saveMovies")
    public ResponseEntity<?> saveVideos(@RequestBody List<MovieInfo> movies) {

        try {
            // Save the list of movies and return a 201 CREATED response with the saved entities
            List<MovieInfo> savedMovies = repository.saveAll(movies);
            return new ResponseEntity<>(savedMovies, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return a 500 INTERNAL SERVER ERROR response with the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie-info/getMovieById")
    public ResponseEntity<?> getMovie(@RequestParam("id")Long id){

        try{
            MovieInfo movie=repository.findById(id).get();
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movie-info/getAllMovies")
    public ResponseEntity<?> getAllMovies(){

        try{
            List<MovieInfo> movies=repository.findAll();
            return new ResponseEntity<>(movies,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //delete all movies
    @DeleteMapping("/movie-info/deleteMovies")
    public ResponseEntity<?> deleteAllMovies(){

        try{
            repository.deleteAll();
            return new ResponseEntity<>("All movies have been deleted successfully", HttpStatus.OK);
        }
        catch(Exception e){
          return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //delete single movie by id
    @DeleteMapping("/movie-info/deleteMovieById")
    public ResponseEntity<?> deleteMovieById(@RequestParam("id") Long id){

        try{
            MovieInfo movie=repository.findById(id).get();
            repository.delete(movie);
            return new ResponseEntity<>("Movie with id "+id+" has been deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/movie-info/find-path-by-id/{movieInfoId}")
    public String findPathById(@PathVariable Long movieInfId){

        var videoInfoOptional=repository.findById(movieInfId);
        return videoInfoOptional.map(MovieInfo::getPath).orElse(null);
    }


}
