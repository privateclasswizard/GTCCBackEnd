package br.gtcc.gtcc.services.impl.neo4j;

import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.spec.TccInterface;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsersRepository usersRepository;

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public Tcc createTcc(Tcc tcc) {
       
        String idAluno = tcc.getIdAluno();

        String idOrientador = tcc.getIdOrientador();

        if((idAluno != " " || idAluno != null) && (idOrientador != " " || idOrientador != null) ){
            
            Boolean existsAluno = this.usersRepository.existsById(idAluno);
            Boolean existsOrientador = this.usersRepository.existsById(idOrientador); 

            if ((tcc.getId() != null || tcc.getId() != " ") && (existsAluno == true && existsOrientador == true)) {
                
                Users aluno = this.usersRepository.findById(idAluno).get();
                Users orientador = this.usersRepository.findById(idOrientador).get();

                tcc.setAluno(aluno);
                tcc.setOrientador(orientador);

                EnumSet<UserType> userTypeCoordenador = EnumSet.of(UserType.COORDENADOR);
                EnumSet<UserType> userTypeProfessor = EnumSet.of(UserType.PROFESSOR);
                
                boolean isCoordenador = orientador.getUserType().equals(userTypeCoordenador);
                boolean isProfessor = orientador.getUserType().equals(userTypeProfessor);
                
                if(isCoordenador == true || isProfessor == true){
                    
                    if((aluno.getTccsGerenciados().size() == 0)){

                        orientador.getTccsGerenciados().add(tcc);  
                        aluno.getTccsGerenciados().add(tcc);

                        this.usersRepository.save(orientador);
                        this.usersRepository.save(aluno);
                    
                    }else{

                        return null;
                    
                    }

                } else {
                
                    return null;
                }
                
                return tccRepository.save(tcc);

            }

        }
 
        return null;
    }

    @Override
    public Tcc updateTCC(Tcc tcc, String id) {

        if (tcc != null && id != null) {

            Tcc existingTcc = getTCC(id);
            
            if (existingTcc != null) {

                existingTcc.setTitle(tcc.getTitle());
                existingTcc.setTheme(tcc.getTheme());
                existingTcc.setCurse(tcc.getCurse());
                existingTcc.setDateOfApresentation(tcc.getDateOfApresentation());

                if(tcc.getAluno() != null && tcc.getOrientador() != null){

                    if(tcc.getIdAluno() != null && tcc.getIdOrientador() != null){

                        Users orientador = this.usersRepository.findById(tcc.getIdOrientador()).get();
                        Users aluno = this.usersRepository.findById(tcc.getIdAluno()).get();

                        if( orientador != null && aluno != null){

                            EnumSet<UserType> userTypeCoordenador = EnumSet.of(UserType.COORDENADOR);
                            EnumSet<UserType> userTypeProfessor = EnumSet.of(UserType.PROFESSOR);
                            
                            boolean isCoordenador = orientador.getUserType().equals(userTypeCoordenador);
                            boolean isProfessor = orientador.getUserType().equals(userTypeProfessor);
            
                            if(isCoordenador == true || isProfessor == true){

                                orientador.getTccsGerenciados().add(existingTcc);
                                aluno.getTccsGerenciados().add(existingTcc);
                                existingTcc.setIdOrientador(tcc.getIdOrientador());
                                existingTcc.setIdAluno(tcc.getIdAluno());
                                existingTcc.setAluno(tcc.getAluno());
                                existingTcc.setOrientador(tcc.getOrientador());

                                this.usersRepository.save(orientador);
                                this.usersRepository.save(aluno);

                            } else {

                                return null;
                            
                            }

                        } else {
                            
                            return null;
                       
                        }

                    } else {

                        return null;

                    }
                    
                }
                
                return tccRepository.save(existingTcc);

            } else {
                //throw new IllegalArgumentException("Tcc não encontrado para o ID fornecido: " + tcc.getId());
                return null;
            
            }
        } else {
            //throw new IllegalArgumentException("O Tcc fornecido é inválido ou não possui um ID.");
            return null;

        }
    }

    @SuppressWarnings("unused")
    @Override
    public Tcc deleteTCC(String id) {
        
        if( id != " " || id != null || id != "")  {

            Tcc delTcc = this.getTCC(id);
            if (delTcc != null) {    

                this.tccRepository.deleteById(id);
                return delTcc;

            } else {
                
                //throw new IllegalArgumentException("O Tcc fornecido é inválido ou não existe.");
                return null;
            
            }
        }
        
        return null;
    }

    @Override
    public List<Tcc> getAllTCC() {
        
        if(this.tccRepository.count() > 0){

            return tccRepository.findAll();
    
        }

        return null;
    }

    @SuppressWarnings("unused")
    @Override
    public Tcc getTCC(String id) {
        if(id != null || id != " "){

            return this.tccRepository.existsById(id)==true? tccRepository.findById(id).get() : null;
        }
        return null;
    }

    
}