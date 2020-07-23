package com.bridgelabz.parkinglot.exception;

public class ParkingLotServiceException extends Exception {
    public enum ExceptionType {
        PARKING_LOT_IS_FULL,NO_SUCH_A_VEHICLE,INVALID_VEHICLE,VEHICLE_ALREADY_PRESENT
    }
    public ExceptionType exceptionType;

    public ParkingLotServiceException(ExceptionType exceptionType,String massage){
        super(massage);
        this.exceptionType = exceptionType;
    }

}
