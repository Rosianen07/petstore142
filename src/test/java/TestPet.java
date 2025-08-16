// 0 - nome do pacote

// 1 - bibliotecas

import static io.restassured.RestAssured.given; // função given
// Classe de verificadores do Hamcrest
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;





// 2 - classe
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ativa a ordenação
public class TestPet {
    // 2.1 atributos
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/pet";
    static int petId = 439296301;
    String petName = "Snoopy";
    String categoryName = "cachorro";
    String tagName = "vacinado";
    String[] status = {"available","sold"};

    // 2.2 funções e métodos
    // 2.2.1 funções e métodos comuns / uteis

    // Função de leitura de Json
    public static String lerArquivoJson(String arquivoJson) throws IOException{ 
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));  
    }

    // 2.2.2 métodos de teste
    @Test @Order(1)
    public void testPostPet() throws IOException{
        // Configura

        // carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        
        // começa o teste via REST-assured

        given()                                 // Dado que
            .contentType(ct)                    // o tipo do conteúdo é 
            .log().all()                        // mostre tudo na ida
            .body(jsonBody)                     // envie o corpo da requisição
        // Executa    
        .when()                                 // Quando
            .post(uriPet)                       // Chamamos o endpoint fazendo post
        // Valida
        .then()                                 // Então
            .log().all()                        // mostre tudo na volta
            .statusCode(200) // verifique se o status code é 200
            .body("name", is(petName))    // verifica se o nome é Snoopy
            .body("id", is(petId))         // verifique o código do pet
            .body("category.name", is(categoryName)) // se é cachorro
            .body("tags[0].name", is(tagName))  // se está vacinado
        ; // fim do given

    }

    @Test @Order(2)
    public void testGetPet(){
        // Configura
        // Entrada e Saidas definidas no nível da classe     
         
        given()
            .contentType(ct)
            .log().all()
            
            // quando é get ou delete não tem body
        // Executa
        .when()
            .get(uriPet + "/" + petId)  // montar o endpoint da URI/<petId>
        // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))    // verifica se o nome é Snoopy
            .body("id", is(petId))         // verifique o código do pet
            .body("category.name", is(categoryName)) // se é cachorro
            .body("tags[0].name", is(tagName))  // se está vacinado

        ; // fim do given
    }
    
    @Test @Order(3)
    public void testPutPet() throws IOException{
        // Configura
        String jsonBody = lerArquivoJson("src/test/resources/json/pet2.json");

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        // Executa
        .when()
            .put(uriPet)
        // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))    // verifica se o nome é Snoopy
            .body("id", is(petId))         // verifique o código do pet
            .body("category.name", is(categoryName)) // se é cachorro
            .body("tags[0].name", is(tagName))  // se está vacinado
            .body("status", is(status[1])) // status do pet na loja
        ;
    }

    @Test @Order(4)
    public void testDeletePet(){
        // Configura --> Dados de entrada e saída no começo da Classe

        given()
            .contentType(ct)
            .log().all()
        // Executa
        .when()
            .delete(uriPet + "/" + petId)
        // Valida
        .then()
            .log().all()
            .statusCode(200) // se comunicou e processou
            .body("code", is(200))   // se apagou
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(petId)))
        ;
    }
}