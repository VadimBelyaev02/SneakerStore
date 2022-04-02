package com.vadim.sneakerstore.logging;

import com.vadim.sneakerstore.controller.CustomerController;
import com.vadim.sneakerstore.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class CustomerAspect {

//    private final static Logger logger = LogManager.getLogger(CustomerController.class);
//
//    @Pointcut("execution(* com.vadim.sneakerstore.controller.CustomerController.getCustomer*(..))")
//    private void getCustomer() {}
//
//    @Pointcut("execution(* com.vadim.sneakerstore.controller.CustomerController.getAllCustomers*(..))")
//    private void getAllCustomers() {}
//
//    @Pointcut("execution(* com.vadim.sneakerstore.controller.CustomerController.postCustomer*(..))")
//    private void postCustomer() {}
//
//    @Pointcut("execution(* com.vadim.sneakerstore.controller.CustomerController.putCustomer*(..))")
//    private void putCustomer() {}
//
//    @Pointcut("execution(* com.vadim.sneakerstore.controller.CustomerController.deleteCustomer())")
//    private void deleteCustomer() {}
//
//    @Around("getCustomer()")
//    public Object loggingGetCustomer(ProceedingJoinPoint point) {
//        String mArgs = Arrays.toString(point.getArgs());
//        String mName = point.getSignature().getName();
//        Object mObject = null;
//        try {
//            mObject = point.proceed();
//            logger.info("Logging method: " + mName + " its args: " + mArgs + " its returned value " + mObject);
//            return mObject;
//        } catch (Throwable e) {
//            logger.error("There was an exception");
//            logger.error("Exception message: " + e.getMessage());
//            logger.error("Exception name: " + e.getClass().getName());
//        }
//        return mObject;
//    }
//
//    @Around("getAllCustomers()")
//    public Object loggingGetAllCustomers(ProceedingJoinPoint point) {
//        String mName = point.getSignature().getName();
//        Object mObject = null;
//        try {
//            mObject = point.proceed();
//            logger.info("Logging method: " + mName + " without args, " + " its returned value " + mObject);
//            return mObject;
//        } catch (Throwable e) {
//            logger.error("There was an exception");
//            logger.error("Exception message: " + e.getMessage());
//            logger.error("Exception name: " + e.getClass().getName());
//        }
//        return mObject;
//    }
//
//    @Around("postCustomer()")
//    public Object loggingPostCustomer(ProceedingJoinPoint point) {
//        String mName = point.getSignature().getName();
//        String mArgs = Arrays.toString(point.getArgs());
//        Object mObject = null;
//        try {
//            mObject = point.proceed();
//            logger.info("Logging method: " + mName + " its args: " + mArgs + " its returned value " + mObject);
//            return mObject;
//        } catch (Throwable e) {
//            logger.error("There was an exception");
//            logger.error("Exception message: " + e.getMessage());
//            logger.error("Exception name: " + e.getClass().getName());
//        }
//        return mObject;
//    }
//
//    @Around("putCustomer()")
//    public Object loggingPutCustomer(ProceedingJoinPoint point) {
//        String mName = point.getSignature().getName();
//        String mArgs = Arrays.toString(point.getArgs());
//        Object mObject = null;
//        try {
//            mObject = point.proceed();
//            logger.info("Logging method: " + mName + " its args: " + mArgs + " its returned value " + mObject);
//            return mObject;
//        } catch (Throwable e) {
//            logger.error("There was an exception");
//            logger.error("Exception message: " + e.getMessage());
//            logger.error("Exception name: " + e.getClass().getName());
//        }
//        return mObject;
//    }
//
//    @Around("deleteCustomer()")
//    public Object loggingDeleteCustomer(ProceedingJoinPoint point) {
//        String mName = point.getSignature().getName();
//        String mArgs = Arrays.toString(point.getArgs());
//        Object mObject = null;
//        try {
//            mObject = point.proceed();
//            logger.info("Logging method: " + mName + " its args: " + mArgs + " its returned value " + mObject);
//            return mObject;
//        } catch (Throwable e) {
//            logger.error("There was an exception");
//            logger.error("Exception message: " + e.getMessage());
//            logger.error("Exception name: " + e.getClass().getName());
//        }
//        return mObject;
//    }
}
