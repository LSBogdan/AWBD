package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.IssueTopicChangeCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicChanges;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.exceptions.CustomErrorHandler;
import com.ProjX.projxpersitance.exceptions.ExceptionEnum;
import com.ProjX.projxpersitance.mappers.IssueTopicChangesMapper;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import com.ProjX.projxpersitance.repository.IssueTopicChangesJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicDetailsJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

@RequiredArgsConstructor
@Service
public class IssueTopicChangesService {

    private final IssueTopicJPARepository issueTopicJPARepository;
    private final IssueTopicDetailsJPARepository issueTopicDetailsJPARepository;
    private final IssueTopicChangesJPARepository issueTopicChangesJPARepository;
    private final IssueTopicChangesMapper issueTopicChangesMapper;
    private static final Logger logger = LoggerFactory.getLogger(IssueTopicChangesService.class);


    public void changeIssueTopic(IssueTopicChangeCreation issueTopicChangeCreation) throws NoSuchFieldException, IllegalAccessException {
        IssueTopic issueTopic = null;
        IssueTopicDetails issueTopicDetails = null;
        if(StringUtils.hasText(issueTopicChangeCreation.getIssueTopicId())){
            Optional<IssueTopic> optionalIssueTopic = issueTopicJPARepository.findById(issueTopicChangeCreation.getIssueTopicId());
            if(optionalIssueTopic.isPresent()){
                issueTopic = optionalIssueTopic.get();
            }
        }
        if(StringUtils.hasText(issueTopicChangeCreation.getIssueTopicDetailsId())){
            Optional<IssueTopicDetails> optionalIssueTopicDetails = issueTopicDetailsJPARepository.findById(issueTopicChangeCreation.getIssueTopicDetailsId());
            if(optionalIssueTopicDetails.isPresent()){
                issueTopicDetails = optionalIssueTopicDetails.get();
            }
        }
        Map<String, String> changes = issueTopicChangeCreation.getChanges();

        if (changes != null) {
            for (Map.Entry<String, String> entry : changes.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                issueTopicChangesJPARepository.save(
                        IssueTopicChanges.builder()
                                .changeType(key)
                                .changeDetails(value)
                                .oldValue(getOldDataValue(issueTopicChangeCreation.getIssueTopicId(), issueTopicChangeCreation.getIssueTopicDetailsId(), key))
                                .issueTopic(isFieldInIssueTopic(key, issueTopicChangeCreation.getIssueTopicId()))
                                .issueTopicDetails(isFieldInIssueTopicDetails(key, issueTopicChangeCreation.getIssueTopicDetailsId()))
                                .build()
                );
                logger.info("Changes logged into database");
                if(issueTopic!=null){
                    setFieldValue(issueTopic, key, value);
                }
                if(issueTopicDetails!=null){
                    setFieldValue(issueTopicDetails,key,value);
                }
            }
        }
        if(issueTopic!=null){
            issueTopicJPARepository.save(issueTopic);
        }
        if(issueTopicDetails!=null){
            issueTopicDetailsJPARepository.save(issueTopicDetails);

        }
    }

    String getOldDataValue(String issueTopicId, String issueTopicDetailsId, String fieldName) {
        if(StringUtils.hasText(issueTopicId) && Arrays.stream(IssueTopic.class.getDeclaredFields()).filter(x->x.getName().equals(fieldName)).count() !=0){
            Optional<IssueTopic> issueTopic =issueTopicJPARepository.findById(issueTopicId);
            if(issueTopic.isEmpty()){
                throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
            }
            try{
                Field field = IssueTopic.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return  field.get(issueTopic.get()).toString();
            }
            catch (NoSuchFieldException e){
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if(StringUtils.hasText(issueTopicDetailsId) && Arrays.stream(IssueTopicDetails.class.getDeclaredFields()).filter(x->x.getName().equals(fieldName)).count() !=0){
            Optional<IssueTopicDetails> issueTopicDetails =issueTopicDetailsJPARepository.findById(issueTopicDetailsId);
            if(issueTopicDetails.isEmpty()){
                throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
            }
            try{
                Field field =IssueTopicDetails.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return  field.get(issueTopicDetails.get()).toString();
            }
            catch (NoSuchFieldException e){
                logger.error(e.getMessage());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    IssueTopic isFieldInIssueTopic(String fieldName, String id){
        if(!StringUtils.hasText(fieldName)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        if(Arrays.stream(IssueTopic.class.getDeclaredFields()).map(Field::getName).anyMatch(x->x.equals(fieldName))){
            if(!StringUtils.hasText(id)){
                throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
            }
            Optional<IssueTopic> issueTopic = issueTopicJPARepository.findById(id);
            if(issueTopic.isEmpty())
            {
                throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
            }
            return issueTopic.get();
        }
        return null;
    }

    IssueTopicDetails isFieldInIssueTopicDetails(String fieldName, String id){
        if(!StringUtils.hasText(fieldName)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        if(Arrays.stream(IssueTopicDetails.class.getDeclaredFields()).map(Field::getName).anyMatch(x->x.equals(fieldName))){
            if(!StringUtils.hasText(id)){
                throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
            }
            Optional<IssueTopicDetails> issueTopic = issueTopicDetailsJPARepository.findById(id);
            if(issueTopic.isEmpty())
            {
                throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
            }
            return issueTopic.get();
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, Object newValue) {
        if (object == null) {
            return;
        }
        Class<?> clazz = object.getClass();
        Field field;
        try{
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                field.set(object, Long.valueOf(newValue.toString()));
            } else
            if(field.getType().isEnum()){
                field.set(object, CustomMappersImpl.stringToEnum(newValue.toString()));
            }else{
                field.set(object, newValue);
            }
        }
        catch (NoSuchFieldException e){
            return;
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IssueTopicChangeInfo> changesIssueTopic(String id){
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        Optional<IssueTopic> issueTopic = issueTopicJPARepository.findById(id);
        Optional<IssueTopicDetails> issueTopicDetails = issueTopicDetailsJPARepository.findById(id);
        if(issueTopic.isEmpty() && issueTopicDetails.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        if(issueTopic.isPresent()){
            List<IssueTopicChanges> issueTopicChanges = issueTopicChangesJPARepository.findIssueTopicChangesByIssueTopic(issueTopic.get());
            if(!CollectionUtils.isEmpty(issueTopicChanges)){
                return  issueTopicChanges.stream().map(issueTopicChangesMapper::toDto).toList();
            }
        }
        if(issueTopicDetails.isPresent()){
            List<IssueTopicChanges> issueTopicDetailsChanges = issueTopicChangesJPARepository.findIssueTopicChangesByIssueTopicDetails(issueTopicDetails.get());
            if(!CollectionUtils.isEmpty(issueTopicDetailsChanges)){
                return  issueTopicDetailsChanges.stream().map(issueTopicChangesMapper::toDto).toList();
            }
        }
       return null;
    }



}
