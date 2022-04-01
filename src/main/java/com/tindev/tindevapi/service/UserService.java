package com.tindev.tindevapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tindev.tindevapi.dto.address.AddressDTO;
import com.tindev.tindevapi.dto.personInfo.PersonInfoDTO;
import com.tindev.tindevapi.dto.user.UserCreateDTO;
import com.tindev.tindevapi.dto.user.UserDTO;
import com.tindev.tindevapi.dto.user.UserDTOCompleto;
import com.tindev.tindevapi.entities.AddressEntity;
import com.tindev.tindevapi.entities.PersonInfoEntity;
import com.tindev.tindevapi.entities.UserEntity;
import com.tindev.tindevapi.exceptions.RegraDeNegocioException;
import com.tindev.tindevapi.repository.AddressRepository;
import com.tindev.tindevapi.repository.PersonInfoRepository;
import com.tindev.tindevapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PersonInfoRepository personInfoRepository;
    private final ObjectMapper objectMapper;

    public List<UserDTO> listUsers(Integer id) throws RegraDeNegocioException {
        if (id != null) {
            userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
            return userRepository.findById(id).stream().map(userEntity ->
                            objectMapper.convertValue(userEntity, UserDTO.class))
                    .collect(Collectors.toList());
        }
        log.info("Calling the list user method");
        return userRepository.findAll().stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) throws Exception {
        log.info("Calling the Create user method");
        UserEntity userEntity = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        AddressEntity addressEntity = addressRepository.findById(userEntity.getAddressId())
                .orElseThrow(() -> new Exception("Address not found"));
        PersonInfoEntity personInfoEntity = personInfoRepository.findById(userEntity.getPersoInfoId())
                .orElseThrow(() -> new Exception("Person info not found"));
        userEntity.setAddress(addressEntity);
        userEntity.setAddressId(addressEntity.getIdAddress());
        userEntity.setPersonInfoEntity(personInfoEntity);
        userEntity.setPersoInfoId(personInfoEntity.getIdPersonInfo());
        return objectMapper.convertValue(userRepository.save(userEntity), UserDTO.class);
    }

    public UserDTO updateUser(Integer id, UserCreateDTO userUpdated) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        UserEntity userEntity = userRepository.getById(id);
        userEntity.setPersoInfoId(userUpdated.getPersoInfoId());
        userEntity.setAddressId(userUpdated.getAddressId());
        userEntity.setGender(userUpdated.getGender());
        userEntity.setPassword(userUpdated.getPassword());
        userEntity.setUsername(userUpdated.getUsername());
        userEntity.setProgLangs(userUpdated.getProgLangs());
        userEntity.setPref(userUpdated.getPref());
        return objectMapper.convertValue((userRepository.save(userEntity)), UserDTO.class);
    }

    public void deleteUser(Integer id) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        userRepository.deleteById(id);
    }

    public UserDTO getUserById(Integer id) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        return objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).convertValue((userRepository.findById(id)), UserDTO.class);
    }

    public List<UserDTOCompleto> listLikesOfTheUserById(Integer id) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        return userRepository.listLikesById(id).stream()
                .map(this::getUserComplete).toList();
    }

    public List<UserDTOCompleto> listReceivedLikesOfTheUserById(Integer id) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        return userRepository.listReceivedLikesById(id).stream()
                .map(this::getUserComplete).toList();
    }

    public List<UserDTOCompleto> listUserDTOComplete(Integer id) throws RegraDeNegocioException {
        if (id == null) {
            return new ArrayList<>(userRepository.findAll().stream().map(this::getUserComplete).toList());
        } else {
            userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
            return new ArrayList<>(userRepository.findAllById(Collections.singleton(id)).stream().map(this::getUserComplete).toList());
        }
    }

    private UserDTOCompleto getUserComplete(UserEntity userEntity) {
        UserDTOCompleto userDTOCompleto = objectMapper.convertValue(userEntity, UserDTOCompleto.class);
        userDTOCompleto.setAddressDTO(objectMapper.convertValue(userEntity.getAddress(), AddressDTO.class));
        userDTOCompleto.setPersonInfoDTO(objectMapper.convertValue(userEntity.getPersonInfoEntity(), PersonInfoDTO.class));
        return userDTOCompleto;
    }

    public List<UserDTOCompleto> listMatchesOfTheUser (Integer id) throws RegraDeNegocioException {
        userRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        return userRepository.listMatchesByUserId(id).stream()
                .map(this::getUserComplete).toList();

    }
}




