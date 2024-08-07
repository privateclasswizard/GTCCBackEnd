package br.gtcc.gtcc.controller.security;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.util.UtilController;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_ADMIN")
@RequestMapping("coordenacao/tcc/v1")
public class UsersController {
    
    @SuppressWarnings("rawtypes")
    @Autowired
    public UserInterface usersInterface;

    @Autowired
    PasswordEncoder passwordEncoder;
        
    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody(required = true) Users users){
        
        @SuppressWarnings("unchecked")
        Optional<Users> createdUsers = Optional.ofNullable((Users) usersInterface.createUsers(users));
        return UtilController.buildResponseFromOptional( createdUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário criado com sucesso", "Erro ao criar usuarios");

    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable String id){
    
        @SuppressWarnings("unchecked")
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteUsers(id));  
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário deletado com sucesso", "Erro ao deletar usuarios");

    }
    
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Object> updateUsers(@RequestBody(required = true) Users users ,@PathVariable("id") String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateUsers(users , id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário alterado com sucesso", "Erro ao alterado usuarios");

    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<Object> getAllUsers(){
        
       @SuppressWarnings("unchecked")
       Optional<List<Users>> list = Optional.ofNullable((List<Users>) usersInterface.getAllUsers());
       return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de usuários", "Lista Vazia");
       
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> foundUsers = Optional.ofNullable( (Users) usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional( foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário encontrado", "Usuário não econtrado");        

    }
    
}
