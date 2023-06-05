package com.fiap.lanchonete.adapter.driver;


import com.fiap.lanchonete.core.applications.services.ClienteService;
import com.fiap.lanchonete.core.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
//    @GetMapping("/{id}")
//    public ResponseEntity<Cliente> obterClientePorId(@PathVariable("id") int id) {
//        // Lógica para obter o cliente pelo ID
//        // Aqui você pode chamar um serviço ou acessar um banco de dados
//        Cliente cliente = // Obtenha o cliente pelo ID
//
//        if (cliente != null) {
//            return ResponseEntity.ok(cliente);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        // Lógica para criar o cliente
        // Aqui você pode chamar um serviço ou acessar um banco de dados
        clienteService.cadastrarCliente(cliente);

        // Exemplo simples: apenas retornar o cliente recebido
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Cliente> atualizarCliente(@PathVariable("id") int id, @RequestBody Cliente cliente) {
//        // Lógica para atualizar o cliente pelo ID
//        // Aqui você pode chamar um serviço ou acessar um banco de dados
//
//        // Exemplo simples: apenas retornar o cliente atualizado
//        return ResponseEntity.ok(cliente);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirCliente(@PathVariable("id") int id) {
//        // Lógica para excluir o cliente pelo ID
//        // Aqui você pode chamar um serviço ou acessar um banco de dados
//
//        // Exemplo simples: retornar uma resposta vazia com status 204 (No Content)
//        return ResponseEntity.noContent().build();
//    }
}
