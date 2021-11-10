package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.List;
import java.util.Optional;

import com.rawsurlife.certificate.models.Package;
import com.rawsurlife.certificate.models.Subscriber;
import com.rawsurlife.certificate.repositories.PackageRepo;
import com.rawsurlife.certificate.repositories.SubscriberRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
@RequestMapping("api/v1/package/")
public class PackageController {
    
    @Autowired
    private PackageRepo repo;

    @Autowired
    private SubscriberRepo repoSubscriber;

    @PostMapping("save")
    public Package savePackage( @RequestBody Package pkg ) {
        return this.repo.save(pkg);
    }

    @PutMapping("update/{id}")
    public Package updatePolicy( @PathVariable( value = "id" ) Long id, @RequestBody Package pkg ) {

        Package pk = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Package with Id " +id+ " does not exist. " ) );
        pk.setCapital( pkg.getCapital() );
        pk.setName(pkg.getName());
        pk.setPremium( pkg.getPremium() );
        pk.setSubscriber( pkg.getSubscriber() );
        
        
        return this.repo.save(pk);
    }


    @GetMapping(value="get/name/{name}")
    public Package getPackageByName(@PathVariable( value = "name" ) String name) {
        
        return this.repo.findByName(name);
        
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePackage( @PathVariable( value = "id" ) Long id ) {

        Package pkg = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Package with Id " +id+ " does not exist. " ) );

        this.repo.delete(pkg);
        return ResponseEntity.ok().build();
    }


    @GetMapping( "list" )
    public List<Package>  getPackages() {
        return repo.findAll() ;
    }


    @GetMapping(value="get/{id}")
    public Package getPackageById(@PathVariable( value = "id" ) Long id) {
        
        return this.repo.findById(id).orElseThrow(
            () -> new RuntimeException("Package with id " +id+ " does not exist." )
        );
        
    }


    @GetMapping( "get/subscriber/" )
    public List<Package>  getPackageBySubscriber( @RequestBody Subscriber subscrib ) {
        return this.repo.findBySubscriber( subscrib ) ;
    }

    @GetMapping( "get/subscriber/{id}" )
    public List<Package>  getPackageBySubscriber( @PathVariable( value = "id" ) Long id )  {
        
        Subscriber subscrib = new Subscriber();
        Optional<Subscriber> opt = this.repoSubscriber.findById(id);
        if ( opt.isPresent() ) {
            subscrib = opt.get();
        } else {
            new RuntimeException(" Souscripteur introuvable. Merci de vérifier! ");
        }
        return this.repo.findBySubscriber( subscrib ) ;
    }
    
    
}
