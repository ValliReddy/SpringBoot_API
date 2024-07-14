package com.example.controller;

import com.example.entity.Load;
import com.example.repository.LoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loads")
public class LoadController {

    @Autowired
    private LoadRepository loadRepository;

    @GetMapping("/{id}")
    public Load getLoad(@PathVariable Long id) {
        return loadRepository.findById(id).orElse(null);
    }

    
    @DeleteMapping("/{id}")
    public String deleteLoad(@PathVariable Long id) {
        if (loadRepository.existsById(id)) {
            loadRepository.deleteById(id);
            return "Load with ID " + id + " deleted successfully.";
        } else {
            return "Load with ID " + id + " not found.";
        }
    }
    
    @PostMapping
    public Load createLoad(@RequestBody Load load) {
        return loadRepository.save(load);
    }

    @PutMapping("/{id}")
    public Load updateLoad(@PathVariable Long id, @RequestBody Load updatedLoad) {
        return loadRepository.findById(id).map(load -> {
            load.setLoadingPoint(updatedLoad.getLoadingPoint());
            load.setUnloadingPoint(updatedLoad.getUnloadingPoint());
            load.setProductType(updatedLoad.getProductType());
            load.setTruckType(updatedLoad.getTruckType());
            load.setNoOfTrucks(updatedLoad.getNoOfTrucks());
            load.setWeight(updatedLoad.getWeight());
            load.setComment(updatedLoad.getComment());
            load.setDate(updatedLoad.getDate());
            return loadRepository.save(load);
        }).orElseGet(() -> {
            updatedLoad.setId(id);
            return loadRepository.save(updatedLoad);
        });
    }
}
