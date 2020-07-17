package com.bridgelabz.exception;

public class ParkingLotServiceException extends Exception {
    public enum ExceptionType {
        PARKING_LOT_IS_FULL,NO_SUCH_A_VEHICLE,INVALID_VEHICLE;
    }
    public ExceptionType exceptionType;

    public ParkingLotServiceException(ExceptionType exceptionType,String massage){
        super(massage);
        this.exceptionType = exceptionType;
    }

}
