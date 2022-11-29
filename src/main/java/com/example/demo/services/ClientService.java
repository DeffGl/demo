package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.models.ClientAccount;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.util.exceptions.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    public Client getClient(int id){
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.orElseThrow(ClientNotFoundException::new);
    }

    public List<ClientAccount> getAccounts(int id){
        return getClient(id).getAccounts();
    }

    @Transactional
    public void createClient(Client client){
        client.setSecretWord(passwordEncoder.encode(client.getSecretWord()));
        clientRepository.save(client);
    }
}
