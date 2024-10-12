package br.gtcc.gtcc.controller.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gtcc.gtcc.controller.DefaultController;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController extends DefaultController {

 @Autowired
 JWTUtil jwtUtil;

 @Autowired
 UsuarioRepository userRepository;

 @Autowired
 PasswordEncoder passwordEncoder;

 @PostMapping("/login")
 public ResponseEntity<String> login(@RequestParam String login, @RequestParam String senha, HttpServletRequest request) throws JsonProcessingException {
    Usuario user = userRepository.findByLogin(login);

  if (user == null) {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Usuário não encontrado");
  }

  if (passwordEncoder.matches(senha, user.getSenha())) {
   List<GrantedAuthority> listaPermissoes = new ArrayList<>();
   user.getPermissoes().forEach(p -> {
    listaPermissoes.add(new SimpleGrantedAuthority(p));
   });

   Map<String, Object> claims = new HashMap<>();
   
   claims.put("LOGIN", login);
   
   claims.put("PERMISSOES", listaPermissoes);
   String jwttoken = jwtUtil.geraTokenUsuario(login, claims);

   Map<String, Object> retorno = new HashMap<>();
   retorno.put("login", login);
   retorno.put("permissoes", listaPermissoes);
   retorno.put("token", jwttoken);

   retorno.put("elementId", user.getIdUsuario());
 

   ObjectMapper objectMapper = new ObjectMapper();
   String retornoDados = objectMapper.writeValueAsString(retorno);

   return ResponseEntity.ok().body(retornoDados);

  } else {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Senha incorreta");
  }

 }

}
