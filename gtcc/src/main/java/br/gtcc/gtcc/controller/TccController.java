package br.gtcc.gtcc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.Console;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_COORDENADOR")
@RequestMapping("coordenacao/tcc/v1")
public class TccController {
  
    @SuppressWarnings("rawtypes")
    @Autowired
    private TccInterface tccInterface; 
 
    @PostMapping("/tcc")
    public ResponseEntity<Object> createTcc(@RequestBody Tcc tcc) {
        Console.log("TESte  obj " + tcc);
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) tccInterface.createTcc(tcc));
        
        if (createdTcc.isPresent()) { 
      
            return ResponseEntity.status(HttpStatus.CREATED).body("Tcc criado com sucesso");
      
        } else {
      
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar Tcc");
      
        }
    }

    @PutMapping("/tcc/{id}")
    public ResponseEntity<Object> updateTcc(@PathVariable("id") String id, @RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> updatedTcc = Optional.ofNullable((Tcc) tccInterface.updateTCC(tcc, id));
      
        if (updatedTcc.isPresent()) {
      
            return ResponseEntity.status(HttpStatus.CREATED).body("Tcc alterado com sucesso");
      
        } else {
      
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tcc não encontrado");
      
        }
    }

    @DeleteMapping("/tcc/{id}")
    public ResponseEntity<Object> deleteTcc(@PathVariable("id") String id) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> deletedTcc = Optional.ofNullable((Tcc) tccInterface.deleteTCC(id));
    
        if (deletedTcc.isPresent()) {
    
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tcc deletado com sucesso");
    
        } else {
    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tcc não encontrado");
    
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/tccs")
    public ResponseEntity<Object> getAllTccs() {
        try {
            List<Optional<Tcc>> tccs = ( List<Optional<Tcc>>) tccInterface.getAllTCC();
            if (!tccs.isEmpty()) {
                return new ResponseEntity<>(tccs, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tcc/{id}")
    public ResponseEntity<Object> getTccById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")

        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCC(id) ) ;

        if (foundTcc.isPresent()) {
        
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tcc/")
    public ResponseEntity<Object> getTccByTitle(@RequestParam("title") String title) {

        @SuppressWarnings("unchecked")

        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCCByTitle(title) ) ;

        if (foundTcc.isPresent()) {
        
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tcc não encontrado");
        }
    }

    @GetMapping("/tcc/sem-apresentacoes")
    public ResponseEntity<Object> getTccSemApresentation() {

        @SuppressWarnings("unchecked")

        Optional<List<Tcc>> foundTcc = Optional.ofNullable( (List<Tcc>) tccInterface.getTccSemApresentacao() ) ;

        if (foundTcc.isPresent()) {
        
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tcc não encontrado");
        }
    }
}
