package com.example.demo.controller;

import com.example.demo.entity.Movements;
import com.example.demo.service.MovementsService;
import com.example.demo.structure.MovementsStructure;
import com.example.demo.structure.ReportMovementsStructure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovementsController {

    private MovementsService movementsService;

    public MovementsController(MovementsService movementsService) {
        super();
        this.movementsService = movementsService;
    }

    // build create movements REST API
    @PostMapping()
    public ResponseEntity<String> saveMovements(@RequestBody MovementsStructure movementsStructure) {
        return new ResponseEntity<String>(movementsService.saveMovements(movementsStructure), HttpStatus.CREATED);
    }

    // build get all movements REST API
    @GetMapping
    public List<Movements> getAllMovements() {
        return movementsService.getAllMovements();
    }

    // build get movements by id REST API
    // http://localhost:8080/api/movements/1
    @GetMapping("/user")
    public ResponseEntity<JSONArray> getMovementsByUser(@RequestBody ReportMovementsStructure reportMovementsStructure) {
        return new ResponseEntity<JSONArray>(movementsService.getMovementsByUser(reportMovementsStructure), HttpStatus.OK);
    }

    // build update movements REST API
    // http://localhost:8080/api/movements/1
    @PutMapping("{id}")
    public ResponseEntity<Movements> updateMovements(@PathVariable("id") long id, @RequestBody Movements movements) {
        return new ResponseEntity<Movements>(movementsService.updateMovements(movements, id), HttpStatus.OK);
    }

    // build delete movements REST API
    // http://localhost:8080/api/movements/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovements(@PathVariable("id") long id) {

        // delete movement from DB
        movementsService.deleteMovements(id);

        return new ResponseEntity<String>("Movement deleted successfully!.", HttpStatus.OK);
    }

}
