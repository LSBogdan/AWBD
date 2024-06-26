package com.ProjX.projxcore;

import com.ProjX.projxpersitance.enums.IssueTopicStatusEnum;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ProjX.projxcore", "com.ProjX.projxpersitance"})
@EntityScan("com.ProjX.projxpersitance.entitys")
//@EnableConfigServer
public class ProjxCoreApplication {

	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		SpringApplication.run(ProjxCoreApplication.class, args);
	}

}
