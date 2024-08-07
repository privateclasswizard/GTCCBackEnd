package br.gtcc.gtcc.controller.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.util.UtilController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@RequestMapping("coordenacao/tcc/v1/professor")
public class ProfessorController {

    @Autowired
    public UserInterface<Users, String> usersInterface;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {

        Optional<Users> foundUsers = Optional.ofNullable(usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional(foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário encontrado", "Usuário não econtrado");
   
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunos() {
   
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAlunos());
        return UtilController.buildResponseFromOptional(list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de alunos", "Lista vazia");
   
    }

    @GetMapping("/professores")
    public ResponseEntity<Object> getAllProfessores() {
    
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getProfessores());
        return UtilController.buildResponseFromOptional(list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de professores", "Lista vazia");
    
    }

    @PostMapping("/aluno")
    public ResponseEntity<Object> createAluno(@RequestBody Users users) {

        Optional<Users> createdUser = Optional.of(usersInterface.createdAluno(users));
        return UtilController.buildResponseFromOptional( createdUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno criado com sucesso", "Erro ao criar aluno");
    
    }

    @DeleteMapping("/aluno/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable String id){
    
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteAluno(id));  
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno deletado com sucesso", "Erro ao deletar aluno");

    }
    
    @PutMapping("/aluno/{id}")
    public ResponseEntity<Object> updateAluno(@RequestBody(required = true) Users users ,@PathVariable("id") String id){
        
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateAluno(users , id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno alterado com sucesso", "Erro ao alterar aluno");                              
    
    }
    
    @GetMapping("/alunofree/")
    public ResponseEntity<Object> getAlunosSemTcc(){
        
        Optional<List<Users>> listAlunosSemTcc = Optional.ofNullable(usersInterface.getAlunosSemTcc());
        return UtilController.buildResponseFromOptional( listAlunosSemTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Alunos sem tcc", "Lista Vazia");
    
    }
}