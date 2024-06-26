/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.neo4j.cypherdsl.core.renderer.Dialect;
// import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;

/**
 *
 * @author mrbee
 * 
 * Classe de Configuração do banco de dados com o NitriteDB
 */

@Order(0)
@Configuration
public class DBConfig {
    
    //neo4j DB
//    @Bean("neo4j");
//    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
//        return org.neo4j.cypherdsl.core.renderer.Configuration
//        .newConfig()
//        .withDialect(Dialect.NEO4J_5).build();
//    }

}
