package com.romanidze.metrikano.clickhouseservice.services.implementations;

import com.romanidze.metrikano.clickhouseservice.config.kafka.producer.KafkaProducerProperties;
import com.romanidze.metrikano.clickhouseservice.domain.VKUser;
import com.romanidze.metrikano.clickhouseservice.dto.UserRecordDTO;
import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;
import com.romanidze.metrikano.clickhouseservice.mappers.VKUserMapper;
import com.romanidze.metrikano.clickhouseservice.repositories.interfaces.VKUserRepository;
import com.romanidze.metrikano.clickhouseservice.services.interfaces.VKUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class VKUserServiceImpl implements VKUserService {

    private final VKUserRepository vkUserRepository;
    private final VKUserMapper vkUserMapper;
    private final KafkaProducerProperties kafkaProducerProperties;
    private final KafkaTemplate<String, UserRecordDTO> kafkaTemplate;

    @Autowired
    public VKUserServiceImpl(VKUserRepository vkUserRepository, VKUserMapper vkUserMapper,
                             KafkaProducerProperties kafkaProducerProperties,
                             KafkaTemplate<String, UserRecordDTO> kafkaTemplate) {
        this.vkUserRepository = vkUserRepository;
        this.vkUserMapper = vkUserMapper;
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void saveVKUserData(VKUserDTO vkUserDTO) {

        vkUserDTO.setId(UUID.randomUUID().toString());
        VKUser vkUser = this.vkUserMapper.dtoToDomain(vkUserDTO);

        if(vkUser.getUsername() != null){

            UserRecordDTO userRecordDTO = UserRecordDTO.builder()
                                                       .username(vkUser.getUsername())
                                                       .recordID(vkUser.getId())
                                                       .recordType("VK_USER")
                                                       .build();

            this.kafkaTemplate.send(this.kafkaProducerProperties.getTopic(), userRecordDTO);

        }else{
            vkUser.setUsername("");
        }

        this.vkUserRepository.save(vkUser);

    }

    @Override
    public List<VKUserDTO> getAllData() {

        List<VKUser> userData = this.vkUserRepository.findAll();

        return userData.stream()
                       .map(this.vkUserMapper::domainToDTO)
                       .collect(Collectors.toList());
    }

    @Override
    public VKUserDTO getRecordByID(String recordID) {

        VKUser vkUser = this.vkUserRepository.find(recordID);

        return this.vkUserMapper.domainToDTO(vkUser);
    }

    @Override
    public List<VKUserDTO> getRecordsInfo(List<String> recordsIDs) {

        List<VKUser> records = this.vkUserRepository.getRecordsByIDs(recordsIDs);

        return records.stream()
                      .map(this.vkUserMapper::domainToDTO)
                      .collect(Collectors.toList());
    }
}
