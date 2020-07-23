package Model.Interface;

import Exception.FieldDoesNotExistException;



public interface Filterable {

    String getField(String fieldName) throws FieldDoesNotExistException;
}