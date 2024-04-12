package com.ProjX.projxpersitance.mappers.customMapppers;

import com.ProjX.projxpersitance.entitys.BasicEntity;
import com.ProjX.projxpersitance.enums.IssueTopicStatusEnum;
import com.ProjX.projxpersitance.enums.IssueTopicTypeEnum;
import com.ProjX.projxpersitance.enums.IssueTopicUrgencyEnum;
import com.ProjX.projxpersitance.exceptions.CustomErrorHandler;
import com.ProjX.projxpersitance.exceptions.ExceptionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CustomMappersImpl {

    @StringToEnum
    public static <T extends Enum<T>> T stringToEnum(String name) throws NoSuchMethodException {
        if(StringUtils.hasText(name)){
            List<Class<? extends Enum>> enumList = List.of(IssueTopicStatusEnum.class, IssueTopicTypeEnum.class, IssueTopicUrgencyEnum.class);
            for(Class<? extends Enum> enumClass : enumList){
                Method getNameMethod = enumClass.getMethod("getName");
                Optional<? extends Enum> enumValue = Arrays.stream(enumClass.getEnumConstants()).filter(x->{
                    try {
                       return getNameMethod.invoke(x).equals(name);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }).reduce((x,y)->(x));
                if(enumValue.isPresent()){
                    return (T) enumValue.get();
                }
            }
        }
        return null;
    }

    @EnumToString
    public static String enumToString(Enum enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(!ObjectUtils.isEmpty(enumClass)){
            Method getNameMethod = enumClass.getClass().getMethod("getName");
            return (String) getNameMethod.invoke(enumClass);
        }
        return "";
    }

    @ObjectToString
    public static String objectToString(BasicEntity basicEntity){
        if(!ObjectUtils.isEmpty(basicEntity)){
            return basicEntity.getId();
        }
        return "";
    }

}
